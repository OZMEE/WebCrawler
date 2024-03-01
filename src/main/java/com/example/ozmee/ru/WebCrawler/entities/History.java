package com.example.ozmee.ru.WebCrawler.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String url;

    @Temporal(TemporalType.TIMESTAMP)
    Date date;

    boolean isCompleted;

    boolean isBigResource;
}
