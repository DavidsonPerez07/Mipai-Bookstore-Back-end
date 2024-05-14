package com.udea.edyl.EDyL.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.udea.edyl.EDyL.data.entity.BookShipping;

public interface BookShippingRepository extends JpaRepository<BookShipping, Long> {
    @Query("SELECT bs FROM BookShipping bs WHERE bs.bookOrder = :bookOrderId")
    BookShipping findByBookOrderId(@Param("bookOrderId") Long bookOrderId);
}
