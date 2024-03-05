package com.example.ozmee.ru.WebCrawler.services;

import com.example.ozmee.ru.WebCrawler.entities.History;
import com.example.ozmee.ru.WebCrawler.repositories.HistoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {
    HistoryRepository historyRepository;

    public List<History> getAll(){
        return historyRepository.findAll();
    }

    @Transactional
    public void save(History history){
        historyRepository.save(history);
    }

    public Optional<History> findForUrl(String url){
        return historyRepository.findByUrl(url);
    }
}
