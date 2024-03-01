package com.example.ozmee.ru.WebCrawler.repositories;

import com.example.ozmee.ru.WebCrawler.entities.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {
    Optional<Page> searchByUrl(String name);

}
