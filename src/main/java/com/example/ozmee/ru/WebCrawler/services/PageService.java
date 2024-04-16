package com.example.ozmee.ru.WebCrawler.services;

import com.example.ozmee.ru.WebCrawler.entities.Page;
import com.example.ozmee.ru.WebCrawler.repositories.PageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public void checkConstraints(Collection<Page> pages){
        for(Page page : pages){
            if(page.getName().length() >= Page.getMaxLengthName()){
                page.setName(page.getName().substring(0, Page.getMaxLengthName()));
                page.setFitIn(false);
            }
            if(page.getUrl().length() >= Page.getMaxLengthUrl()){
                page.setUrl(page.getUrl().substring(0, Page.getMaxLengthUrl()));
                page.setFitIn(false);
            }
        }
    }

    @Transactional
    public void saveAllPages(Collection<Page> pages){
        checkConstraints(pages);
        pageRepository.saveAll(pages);
    }

    @Transactional
    public void saveAll(Collection<Page> pages){
        System.out.println("Сохраняет лист страниц: ");
        Collection<Page> pageToSave = new ArrayList<>();
        int index = 1;
        for(Page page : pages) {
            if (index % 1500 == 0) {
                saveAll(pageToSave);
                pageToSave = new ArrayList<>();
            }
            pageToSave.add(page);
            index++;
        }
        if(pageToSave.size() != 0){
            saveAll(pageToSave);
        }
    }

    public List<Page> findAll(){
        return pageRepository.findAll();
    }
}
