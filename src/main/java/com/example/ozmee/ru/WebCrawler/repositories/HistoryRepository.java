package com.example.ozmee.ru.WebCrawler.repositories;

import com.example.ozmee.ru.WebCrawler.entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
}
