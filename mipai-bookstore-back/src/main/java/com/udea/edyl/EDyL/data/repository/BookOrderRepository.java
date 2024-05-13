package com.udea.edyl.EDyL.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udea.edyl.EDyL.data.entity.BookOrder;

public interface BookOrderRepository extends JpaRepository<BookOrder, Long> {
    BookOrder findByUserUserId(Long userId);
}
