package com.udea.edyl.EDyL.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udea.edyl.EDyL.data.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
