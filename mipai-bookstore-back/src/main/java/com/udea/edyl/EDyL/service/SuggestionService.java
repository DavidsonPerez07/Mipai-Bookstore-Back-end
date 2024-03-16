package com.udea.edyl.EDyL.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.udea.edyl.EDyL.data.entity.Suggestion;
import com.udea.edyl.EDyL.data.entity.User;
import com.udea.edyl.EDyL.data.repository.SuggestionRepository;
import com.udea.edyl.EDyL.data.repository.UserRepository;
import com.udea.edyl.EDyL.web.dto.SuggestionDto;

@Service
public class SuggestionService {
    private SuggestionRepository suggestionRepo;
    private UserRepository userRepo;
    private ModelMapper suggestionMapper;

    public SuggestionService(SuggestionRepository suggestionRepo, UserRepository userRepo, ModelMapper suggestionMapper) {
        this.suggestionRepo = suggestionRepo;
        this.userRepo = userRepo;
        this.suggestionMapper = suggestionMapper;
    }

    @SuppressWarnings("null")
    public SuggestionDto saveSuggetion(SuggestionDto suggestionDto) throws Exception {
        if (suggestionDto == null) {
            throw new Exception("Invalid parameter");
        }
        else if (suggestionDto.getMesage() == null || suggestionDto.getMesage().isEmpty()) {
            throw new Exception("Message is required");
        }
        else if (suggestionDto.getMesage() == null || suggestionDto.getMesage().isEmpty()) {
            throw new Exception("User id is required");
        }

        Optional<User> user = userRepo.findById(suggestionDto.getUserId());

        if (!user.isPresent()) {
            throw new Exception("The user doesn't exist");
        }

        Suggestion entSuggestion = suggestionRepo.save(suggestionMapper.map(suggestionDto, Suggestion.class));
        entSuggestion.setUser(user.get());
        entSuggestion = suggestionRepo.save(entSuggestion);

        return suggestionMapper.map(entSuggestion, SuggestionDto.class);
    }

    @SuppressWarnings("null")
    public SuggestionDto getSuggestion(Long suggestionId) {
        Optional<Suggestion> suggestion = suggestionRepo.findById(suggestionId);

        SuggestionDto suggestionDto = new SuggestionDto();
        
        if (suggestion.isPresent()) {
            suggestionDto = suggestionMapper.map(suggestion, SuggestionDto.class);
        }
        else {
            suggestionDto = null;
        }

        return suggestionDto;
    }

    public List<SuggestionDto> getAllSugestions() {
        List<Suggestion> suggestions = suggestionRepo.findAll();

        List<SuggestionDto> suggestionDtos = new ArrayList<SuggestionDto>();

        for (Suggestion suggestion : suggestions) {
            suggestionDtos.add(suggestionMapper.map(suggestion, SuggestionDto.class));
        }

        return suggestionDtos;
    }

    @SuppressWarnings("null")
    public Boolean deleteSuggestion(Long suggestionId) {
        Boolean exists = suggestionRepo.existsById(suggestionId);

        if (exists) {
            suggestionRepo.deleteById(suggestionId);
        }
        else {
            return false;
        }

        return exists;
    }
}
