package com.udea.edyl.EDyL.web.dto;

import java.io.Serializable;

import com.udea.edyl.EDyL.data.entity.BookType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto implements Serializable {
    private Long bookId;
    private String bookName;
    private String author;
    private String editorial;
    private String bookDescription;
    private Float price;
    private String category;
    private Integer quantity;
    private BookType bookType;
    private byte[] bookImage;
}
