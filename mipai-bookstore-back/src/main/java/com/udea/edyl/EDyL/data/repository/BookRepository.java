package com.udea.edyl.EDyL.data.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.udea.edyl.EDyL.data.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT book FROM Book book WHERE book.category = :category")
    List<Book> findAllByCategory(@Param("category") String category);
}
