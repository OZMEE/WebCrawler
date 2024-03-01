package com.example.ozmee.ru.WebCrawler.crawler;

import com.example.ozmee.ru.WebCrawler.entities.Page;
import com.example.ozmee.ru.WebCrawler.exc.NotValidReferenceException;
import com.example.ozmee.ru.WebCrawler.services.PageService;
import com.example.ozmee.ru.WebCrawler.util.PageValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jsoup.Jsoup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Scope("prototype")
public class Crawler {

    final PageService pageService;
    final PageValidator pageValidator;

    boolean isScan = false;

    public boolean isScan(){
        return isScan;
    }

    private static int MAX_DEEP = 2;


    List<String> robotsFile(String url){
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
    //TODO
    String getRoot(String url){
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


    public boolean scan(String url){
        isScan = true;

        if(!pageValidator.isValidWebsite(url)){
            throw new NotValidReferenceException();
        }

        String root = getRoot(url);
        List<String> xmls = robotsFile(root);

        for(String xml : xmls){
            crawl(1, xml);
        }

        isScan = false;
        return true;
    }

    void savePages(Collection<Page> pages){
        System.out.println("Сохранение страниц: " + pages.toString());
        for(Page page : pages){
            pageService.save(page);
        }
    }

    Page convertUrlToPage(String url){
        String name = "";
        try {
            name = Jsoup.connect(url).get().title();
        } catch (Exception e){
            return null;
        }
        return Page.builder().name(name).url(url).isFile(pageValidator.isFile(url)).build();
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

    void crawl(int level, String url){
        if(pageValidator.isValidWebsite(url)){
            Page page = convertUrlToPage(url);
            if(page != null) {
                System.out.println("Сохранение");
                pageService.save(page);
            }
        }

        Queue<String> parsedLinks = parse(url);
        System.out.println("Parsed links: " + parsedLinks.toString());

        if(level == MAX_DEEP){
            pageService.saveAll(convertListUrlToListPage(parsedLinks));
            return;
        }

        List<String> linksToCrawl = distributeToSaveAndCrawl(parsedLinks);
        System.out.println("Links to crawl: " + linksToCrawl);

        if(linksToCrawl.size() == 1 && linksToCrawl.get(0).equals(url)){
            pageService.save(convertUrlToPage(url));
            return;
        }
        for(String link : linksToCrawl){
            crawl(++level, link);
        }
    }

    List<String> distributeToSaveAndCrawl(Queue<String> links){
        List<String> listToSave = new ArrayList<>();
        List<String> listToParse = new ArrayList<>();

        for (String link : links) {
            if (pageValidator.isValidWebsite(link)) {
                listToSave.add(link);
            } else {
                listToParse.add(link);
            }
        }

        List<Page> pagesToSave = convertListUrlToListPage(listToSave);
        pageService.saveAll(pagesToSave);

        return listToParse;
    }

    Queue<String> parse(String url){
        Queue<String> list = new LinkedList<>();
        try {
            URL xmlUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) xmlUrl.openConnection();
            long length = connection.getContentLengthLong();

            if(length > 1000){
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
            e.printStackTrace();
        }
        return list;
    }
}
