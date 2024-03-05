package com.example.ozmee.ru.WebCrawler.util.exc;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class SearchRequestError {
    String msg;

    long timestamp;
}
