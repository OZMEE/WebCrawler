package com.example.ozmee.ru.WebCrawler.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@ToString
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    String url;

    boolean isFile;

    @Override
    public boolean equals(Object o){
        Page page = (Page)o;

        return Objects.equals(name, page.name) && url.equals(page.url) && isFile == page.isFile;
    }
}
