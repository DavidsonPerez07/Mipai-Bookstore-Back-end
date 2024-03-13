package com.udea.edyl.EDyL.web.mapper;

import java.util.List;

import org.mapstruct.factory.Mappers;

import com.udea.edyl.EDyL.data.entity.Suggestion;
import com.udea.edyl.EDyL.web.dto.SuggestionDto;

public interface SuggestionMapper {
    SuggestionMapper INSTANCE = Mappers.getMapper(SuggestionMapper.class);

    Suggestion suggestionDtoToSuggestion(SuggestionDto suggestionDto);

    SuggestionDto suggestionToSuggestionDto(Suggestion suggestion);

    List<Suggestion> suggestionDtosToSuggestions(List <SuggestionDto> suggestionDtos);
    
    List<SuggestionDto> suggestionsToSuggestionDtos(List<Suggestion> suggestions);
}
