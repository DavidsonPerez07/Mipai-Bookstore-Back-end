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
    private String tittle;
    private String category;
    private String author;
    private Float price;
    private BookType bookType;
    private Byte[] bookImage;
    private String isbn;
    private String editorial;
}
