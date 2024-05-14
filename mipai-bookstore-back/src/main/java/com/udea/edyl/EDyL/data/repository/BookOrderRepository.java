package com.udea.edyl.EDyL.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.udea.edyl.EDyL.data.entity.BookOrder;

public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
    @Query("SELECT bo FROM BookOrder bo WHERE bo.user = :userId")
    List<BookOrder> findAllByUserId(@Param("userId") Long userId);
}
