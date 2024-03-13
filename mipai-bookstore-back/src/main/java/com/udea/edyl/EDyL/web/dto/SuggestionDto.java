package com.udea.edyl.EDyL.web.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SuggestionDto implements Serializable {
    private Long suggestionId;
    private String mesage;
    private UserDto user;
}
