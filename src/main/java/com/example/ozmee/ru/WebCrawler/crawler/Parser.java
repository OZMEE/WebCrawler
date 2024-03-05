package com.example.ozmee.ru.WebCrawler.crawler;

import com.example.ozmee.ru.WebCrawler.entities.Page;
import com.example.ozmee.ru.WebCrawler.util.PageValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Parser {
    PageValidator pageValidator;

    Page convertUrlToPage(String url){
        String name = "";
        try {
            name = Jsoup.connect(url).get().title();
        } catch (Exception e){
            return null;
        }
        return Page.builder().name(name).url(url).isFile(pageValidator.isFile(url)).build();
    }

    Queue<String> parse(String url){
        Queue<String> list = new LinkedList<>();
        try {
            URL xmlUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) xmlUrl.openConnection();
            long length = connection.getContentLengthLong();

            if(length > 10_000){
                list.add(url);
                return list;
            }
            //connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if(line.strip().startsWith("<loc>") && line.endsWith("</loc>")){
                    String x = line.strip().substring(5, line.strip().length()-6);
                    list.add(x);
                }

            }

            reader.close();
            connection.disconnect();
        } catch (IOException e) {
             //TODO log
        }
        return list;
    }

    List<Page> convertListUrlToListPage(Collection<String> urls) {
        List<Page> pages = new ArrayList<>();
        for(String url : urls){
            String name;
            try {
                name = Jsoup.connect(url).get().title();
            } catch (Exception e){
                name = url;
            }
            if(Objects.equals(name, "")){
                name = url;
            }

            Page page = Page.builder().name(name).url(url).isFile(pageValidator.isFile(url)).build();

            pages.add(page);
        }

        return pages;
    }

    String getRootUrl(String url){
        StringBuilder str = new StringBuilder();
        url = url.substring(8);
        for(char c : url.toCharArray()){
            if(c == '/'){
                break;
            }
            str.append(c);
        }

        str.append("/robots.txt");

        return "https://" + str.toString();
    }

    List<String> parseRobotsFile(String url){
        List<String> list = new ArrayList<>();
        url = url.toLowerCase();
        try {
            URL txtUrl = new URL(url);

            BufferedReader reader = new BufferedReader(new InputStreamReader(txtUrl.openStream()));

            String line;

            while((line = reader.readLine()) != null){
                line = line.toLowerCase();
                if(line.startsWith("user-agent:") && line.contains("*")){
                    break;
                }
            }
            boolean isFound = false;
            while ((line = reader.readLine()) != null) {
                line = line.toLowerCase();
                if(line.startsWith("sitemap:")){
                    isFound = true;
                    list.add(line.substring(9));
                } else if(isFound){
                    break;
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
