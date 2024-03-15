package com.udea.edyl.EDyL.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.udea.edyl.EDyL.data.entity.Address;
import com.udea.edyl.EDyL.data.entity.User;
import com.udea.edyl.EDyL.data.entity.UserType;
import com.udea.edyl.EDyL.data.repository.AddressRepository;
import com.udea.edyl.EDyL.data.repository.UserRepository;
import com.udea.edyl.EDyL.web.dto.UserDto;
import com.udea.edyl.EDyL.web.mapper.AddressMapper;
import com.udea.edyl.EDyL.web.mapper.UserMapper;

@Service
public class UserService {
    private UserRepository userRepo;
    private AddressRepository addressRepo;

    public UserService(UserRepository userRepo, AddressRepository addressRepo) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
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

        User entUser = UserMapper.INSTANCE.userDtoToUser(userDto);
        entUser = userRepo.save(entUser);
        
        return UserMapper.INSTANCE.userToUserDto(entUser);
    }

    @SuppressWarnings("null")
    public UserDto getUser(Long userId) {
        Optional<User> user;
        user = userRepo.findById(userId);

        UserDto userDto = new UserDto();

        if (user.isPresent()) {
            userDto = UserMapper.INSTANCE.userToUserDto(user.get());
        }
        else {
            userDto = null;
        }

        return userDto;
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
                List<Address> newAddresses = AddressMapper.INSTANCE.addressDtosToAddresses(updatedUser.getAddresses());
                addressRepo.saveAll(newAddresses);
                entUser.get().getAddresses().addAll(newAddresses);
                entUser.get().setPhoneNumber(updatedUser.getPhoneNumber());
            }
        }
        else {
            return false;
        }

        return exists;
    }
}
