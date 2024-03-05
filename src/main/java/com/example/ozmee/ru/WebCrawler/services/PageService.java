package com.example.ozmee.ru.WebCrawler.services;

import com.example.ozmee.ru.WebCrawler.entities.Page;
import com.example.ozmee.ru.WebCrawler.repositories.PageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PageService {
    PageRepository pageRepository;

    public Optional<Page> searchByUrl(String name){
        return pageRepository.searchByUrl(name);
    }

    @Transactional
    public void save(Page page){
        System.out.println("Сохраняет одну страницу: ");
        pageRepository.save(page);
    }

    @Transactional
    public void saveAll(Collection<Page> pages){
        System.out.println("Сохраняет лист страниц: ");
        pageRepository.saveAll(pages);
    }

    public List<Page> findAll(){
        return pageRepository.findAll();
    }
}
