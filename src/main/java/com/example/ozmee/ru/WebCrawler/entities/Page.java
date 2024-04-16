package com.example.ozmee.ru.WebCrawler.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Objects;

@ToString
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class  Page {
    final static int maxLengthName = 300;
    final static int maxLengthUrl = 700;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name", length = maxLengthName)
    String name;

    @Column(name = "url", length = maxLengthUrl)
    String url;

    boolean isFile;

    boolean isFitIn;

    @Override
    public boolean equals(Object o){
        Page page = (Page)o;

        return name.equals(page.getName()) && url.equals(page.getUrl()) && isFile == page.isFile();
    }

    public static int getMaxLengthName(){
        return maxLengthName;
    }

    public static int getMaxLengthUrl(){
        return maxLengthUrl;
    }
}
