package com.udea.edyl.EDyL.data.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.udea.edyl.EDyL.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {  
    
    @Query("SELECT u.email FROM User u")
    List<String> findEmailBy();

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
}
