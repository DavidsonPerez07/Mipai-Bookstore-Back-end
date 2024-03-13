package com.udea.edyl.EDyL.web.dto;

import java.io.Serializable;

import com.udea.edyl.EDyL.data.entity.UserType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto implements Serializable {
    private Long userId;
    private String userName;
    private String lastName;
    private String email;
    private String password;
    private UserType userType;
    private String phoneNumber;
}
