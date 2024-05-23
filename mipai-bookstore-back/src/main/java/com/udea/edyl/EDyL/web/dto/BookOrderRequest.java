package com.udea.edyl.EDyL.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookOrderRequest {
    private BookOrderDto bookOrderDto;
    private List<BookQuantity> books;
}
