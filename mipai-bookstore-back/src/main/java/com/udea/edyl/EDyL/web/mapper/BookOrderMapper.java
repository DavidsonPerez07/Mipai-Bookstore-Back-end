package com.udea.edyl.EDyL.web.mapper;

import java.util.List;

import org.mapstruct.factory.Mappers;

import com.udea.edyl.EDyL.data.entity.BookOrder;
import com.udea.edyl.EDyL.web.dto.BookOrderDto;

public interface BookOrderMapper {
    BookOrderMapper INSTANCE = Mappers.getMapper(BookOrderMapper.class);

    BookOrder bookOrderDtoTobBookOrder(BookOrderDto bookOrderDto);

    BookOrderDto bookOrderToBookOrderDto(BookOrder bookOrder);

    List<BookOrder> BookOrderDtosToBookOrders(List <BookOrderDto> bookOrderDtos);
    
    List<BookOrderDto> bookOrdersToBookOrderDtos(List<BookOrder> bookOrders);
}
