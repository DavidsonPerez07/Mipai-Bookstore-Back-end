package com.udea.edyl.EDyL.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udea.edyl.EDyL.data.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
