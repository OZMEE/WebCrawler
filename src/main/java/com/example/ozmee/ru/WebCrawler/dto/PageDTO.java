package com.example.ozmee.ru.WebCrawler.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDTO {
    String name;

    String url;

    boolean isFile;
}
