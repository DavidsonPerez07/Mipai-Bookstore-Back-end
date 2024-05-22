package com.udea.edyl.EDyL.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udea.edyl.EDyL.service.SuggestionService;
import com.udea.edyl.EDyL.web.dto.SuggestionDto;

@RestController
@RequestMapping("suggestions")
@CrossOrigin(value = "http://localhost:3000")
public class SuggestionController {
    private SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @PostMapping("/save-suggestion")
    public ResponseEntity<?> saveSuggestion(@RequestBody SuggestionDto suggestionDto) 
    throws Exception {
        if (suggestionDto == null) {
            return ResponseEntity.badRequest().body("Ivalid suggestion data");
        }

        SuggestionDto resp;
        try {
            resp = suggestionService.saveSuggetion(suggestionDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/get-suggestion")
    public ResponseEntity<?> getSuggestion(@RequestParam Long suggestionId) {
        SuggestionDto resp;
        resp = suggestionService.getSuggestion(suggestionId);

        if (resp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("This suggestions doesn't exist");
        }

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/get-all-suggestions")
    public ResponseEntity<?> getAllSuggestions() {
        List<SuggestionDto> suggestionDtos = suggestionService.getAllSugestions();

        return ResponseEntity.ok(suggestionDtos);
    }

    @DeleteMapping("/delete-suggestion")
    public ResponseEntity<?> deleteSuggestion(@RequestParam Long suggestionId) {
        Boolean resp = suggestionService.deleteSuggestion(suggestionId);

        if (!resp) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("This suggestion doen't exist");
        }

        return ResponseEntity.ok().build();
    }
}
