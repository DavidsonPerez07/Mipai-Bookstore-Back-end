package com.udea.edyl.EDyL.web.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookShippingDto implements Serializable {
    private Long shippingId;
    private Integer estimatedShippingDays;
    private Date shippingDate;
    private Boolean delivered;
    private BookOrderDto bookOrder;
}
