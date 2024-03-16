package com.udea.edyl.EDyL.web.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookOrderDto implements Serializable {
    private Long orderId;
    private Float orderValue;
    private Date orderDate;
    private Long userId;
    private List<BookDto> books;
}
