package com.udea.edyl.EDyL.web.mapper;

import java.util.List;

import org.mapstruct.factory.Mappers;

import com.udea.edyl.EDyL.data.entity.BookShipping;
import com.udea.edyl.EDyL.web.dto.BookShippingDto;

public interface BookShippingMapper {
    BookShippingMapper INSTANCE = Mappers.getMapper(BookShippingMapper.class);

    BookShipping bookShippingDtoToBookShipping(BookShippingDto bookShippingDto);

    BookShippingDto bookShippingToBookShippingDto(BookShipping bookShipping);

    List<BookShipping> bookShippingDtosToBookShippings(List <BookShippingDto> bookShippingDtos);
    
    List<BookShippingDto> bookShippingsToBookShippingDtos(List<BookShipping> bookShipping);
}
