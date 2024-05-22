package com.udea.edyl.EDyL.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookQuantity {
    private Long bookId;
    private Integer quantity;
}
