package com.udea.edyl.EDyL.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginData implements Serializable {
    private String email;
    private String password;
}
