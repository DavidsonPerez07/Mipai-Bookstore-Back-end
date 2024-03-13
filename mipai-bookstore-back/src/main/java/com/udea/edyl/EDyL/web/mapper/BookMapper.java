package com.udea.edyl.EDyL.web.mapper;

import java.util.List;

import org.mapstruct.factory.Mappers;

import com.udea.edyl.EDyL.data.entity.Book;
import com.udea.edyl.EDyL.web.dto.BookDto;

public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book bookDtoToBook(BookDto bookDto);

    BookDto bookToBookDto(Book book);

    List<Book> BookDtosToBooks(List <BookDto> bookDtos);
    
    List<BookDto> BooksToBookDtos(List<Book> books);
}
