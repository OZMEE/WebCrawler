package com.example.ozmee.ru.WebCrawler.controllers;

import com.example.ozmee.ru.WebCrawler.dto.HistoryDTO;
import com.example.ozmee.ru.WebCrawler.entities.History;
import com.example.ozmee.ru.WebCrawler.services.HistoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class HistoryController {
    HistoryService historyService;
    ModelMapper modelMapper;

    public List<HistoryDTO> getAll(){
        return historyService.getAll().stream().map(this::convertToHistoryDTO).collect(Collectors.toList());
    }


    private HistoryDTO convertToHistoryDTO(History history){
        return modelMapper.map(history, HistoryDTO.class);
    }

}
