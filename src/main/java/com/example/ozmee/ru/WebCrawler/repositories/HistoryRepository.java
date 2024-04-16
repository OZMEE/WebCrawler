package com.example.ozmee.ru.WebCrawler.repositories;

import com.example.ozmee.ru.WebCrawler.entities.HistoryOfOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryOfOperation, Integer> {
    Optional<HistoryOfOperation> findByUrl(String url);
}
