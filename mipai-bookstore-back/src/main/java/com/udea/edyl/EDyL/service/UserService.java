package com.udea.edyl.EDyL.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.udea.edyl.EDyL.data.entity.User;
import com.udea.edyl.EDyL.data.entity.UserType;
import com.udea.edyl.EDyL.data.repository.UserRepository;
import com.udea.edyl.EDyL.web.dto.UserDto;

@Service
public class UserService {
    private UserRepository userRepo;
    private ModelMapper userMapper;

    public UserService(UserRepository userRepo, ModelMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    @SuppressWarnings("null")
    public UserDto saveUser(UserDto userDto) throws Exception {
        if (userDto == null) {
            throw new Exception("Invalid parameter");
        }
        else if (userDto.getUserName() == null || userDto.getUserName().isEmpty()) {
            throw new Exception("Username is required");
        }
        else if (userDto.getLastName() == null || userDto.getLastName().isEmpty()) {
            throw new Exception("Last name is required");
        }
        else if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new Exception("Email is required");
        }
        else if (userDto.getUserPassword() == null || userDto.getUserPassword().isEmpty()) {
            throw new Exception("Password is required");
        }
        else if (userDto.getUserType() == null) {
            throw new Exception("User type is required");
        }

        User entUser = userRepo.save(userMapper.map(userDto, User.class));
        
        return userMapper.map(entUser, UserDto.class);
    }

    @SuppressWarnings("null")
    public UserDto getUser(Long userId) {
        Optional<User> user = userRepo.findById(userId);

        UserDto userDto = new UserDto();

        if (user.isPresent()) {
            userDto = userMapper.map(user.get(), UserDto.class);
        }
        else {
            userDto = null;
        }

        return userDto;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();

        List<UserDto> userDtos = new ArrayList<UserDto>();

        for (User user : users) {
            userDtos.add(userMapper.map(user, UserDto.class));
        }

        return userDtos;
    }

    @SuppressWarnings("null")
    public Boolean deleteUser(Long userId) {
        Boolean exists = userRepo.existsById(userId);

        if (exists) {
            userRepo.deleteById(userId);
        }
        else {
            return false;
        }

        return exists;
    }

    @SuppressWarnings("null")
    public Boolean editUser(Long userId, UserDto updatedUser) {
        Boolean exists = userRepo.existsById(userId);

        if (exists) {
            Optional<User> entUser = userRepo.findById(userId);
            entUser.get().setEmail(updatedUser.getEmail());
            entUser.get().setLastName(updatedUser.getLastName());
            entUser.get().setUserName(updatedUser.getUserName());

            if (entUser.get().getUserType() == UserType.CLIENT) {
                entUser.get().setPhoneNumber(updatedUser.getPhoneNumber());
                entUser.get().setAddress(updatedUser.getAddress());
            }
        }
        else {
            return false;
        }

        return exists;
    }
}
