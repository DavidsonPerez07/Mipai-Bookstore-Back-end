package com.udea.edyl.EDyL.data.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    @Column(nullable = false)
    private String bookName;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String editorial;
    @Column(nullable = false)
    private String bookDescription;
    @Column(nullable = false)
    private Float price;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private BookType bookType;
    @Column(nullable = false)
    private String bookImage;

    @ManyToAny
    @JoinTable(
        name = "book_order_book",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<BookOrder> bookOrders;
}
