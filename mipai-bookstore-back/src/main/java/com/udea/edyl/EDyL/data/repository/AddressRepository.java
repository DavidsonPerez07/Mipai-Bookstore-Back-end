package com.udea.edyl.EDyL.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udea.edyl.EDyL.data.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
