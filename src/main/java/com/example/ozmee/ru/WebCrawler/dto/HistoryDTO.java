package com.example.ozmee.ru.WebCrawler.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {
    String url;

    LocalDate date;
}
