package com.example.ozmee.ru.WebCrawler.util;

import com.example.ozmee.ru.WebCrawler.entities.History;
import com.example.ozmee.ru.WebCrawler.dto.SearchRequest;
import com.example.ozmee.ru.WebCrawler.services.HistoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SearchRequestValidator implements Validator {
    HistoryService historyService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(History.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SearchRequest searchRequest = (SearchRequest) target;

        if(historyService.findForUrl(searchRequest.getReference()).isPresent()){
            errors.rejectValue("reference", "", "Url with this was crawling");
        }
    }
}
