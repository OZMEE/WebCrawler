package com.example.ozmee.ru.WebCrawler.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;

public class Crawler2 {

    Map<Integer, List<String>> map;

    boolean isSearch = false;

    public boolean isSearch(){
        return isSearch;
    }

    public Map<Integer, List<String>> search(int level, String url){
        isSearch = true;
        synchronized (map) {
            crawl(level, url, new HashSet<>());
            isSearch = false;
            return map;
        }
    }

    private void crawl(int level, String url, Set<String> visited){

        map = new HashMap<>();
        for(int i = 0; i < level; i++){
            map.put(i, new ArrayList<>());
        }
        if (level <= 2) {
            Document doc = request(url, visited, level);

            if (doc != null) {
                for (Element link : doc.select("a[href]")) {
                    String next_link = link.absUrl("href");
                    if (!visited.contains(next_link)) {
                            crawl(level++, next_link, visited);
                    }
                }
            }
        }
    }

    private Document request(String url, Set<String> visited, int level){
        try {
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if(con.response().statusCode() == 200){
                map.get(level).add(url);
                System.out.println("Link " + url);
                System.out.println(doc.title());
                visited.add(url);

                return doc;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
