package com.example.ozmee.ru.WebCrawler.services;

import com.example.ozmee.ru.WebCrawler.dto.HistoryDTO;
import com.example.ozmee.ru.WebCrawler.entities.Page;
import com.example.ozmee.ru.WebCrawler.repositories.PageRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class PageServiceTest {
    @Mock
    PageRepository pageRepository;
    PageService underTest;

    @BeforeEach
    void setUp(){
        underTest = new PageService(pageRepository);
    }

    @Test
    void searchByUrl() {
        String name = "a";
        underTest.searchByUrl(name);
        ArgumentCaptor<String> usernameCapture = ArgumentCaptor.forClass(String.class);
        verify(pageRepository).searchByUrl(usernameCapture.capture());
        String excepted = usernameCapture.getValue();
        assertThat(name).isEqualTo(excepted);
    }

    @Test
    void save() {
        Page page = Mockito.mock(Page.class);
        underTest.save(page);
        ArgumentCaptor<Page> pageCaptor = ArgumentCaptor.forClass(Page.class);
        verify(pageRepository).save(pageCaptor.capture());

        Page excepted = pageCaptor.getValue();

        assertThat(excepted).isEqualTo(page);
    }

    @Test
    void checkConstraints() {
    }

    @Test
    void saveAllPages() {
        Collection<Page> collection = new ArrayList();
        collection.add(Page.builder().name("Leha").url("/http").build());
        underTest.saveAllPages(collection);
        ArgumentCaptor<Collection> pagesArgumentCaptor = ArgumentCaptor.forClass(Collection.class);
        verify(pageRepository).saveAll(pagesArgumentCaptor.capture());
        Collection<Page> excepted = pagesArgumentCaptor.getValue();
        assertThat(excepted).isEqualTo(collection);
    }

    @Test
    void saveAll() {
    }

    @Test
    void findAll() {
        verify(pageRepository).findAll();
    }
}