package com.udea.edyl.EDyL.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.udea.edyl.EDyL.data.entity.Book;
import com.udea.edyl.EDyL.data.entity.BookType;
import com.udea.edyl.EDyL.data.repository.BookRepository;
import com.udea.edyl.EDyL.web.dto.BookDto;

@Service
public class BookService {
    private BookRepository bookRepo;
    private ModelMapper bookMapper;

    public BookService(BookRepository bookRepo, ModelMapper bookMapper) {
        this.bookRepo = bookRepo;
        this.bookMapper = bookMapper;
    }

    @SuppressWarnings("null")
    public BookDto saveBook(BookDto bookDto) throws Exception {
        if (bookDto == null) {
            throw new Exception("Invalid parameter");
        }
        else if (bookDto.getTittle() == null || bookDto.getTittle().isEmpty()) {
            throw new Exception("Tittle is required");
        }
        else if (bookDto.getCategory() == null || bookDto.getCategory().isEmpty()) {
            throw new Exception("Category is required");
        }
        else if (bookDto.getAuthor() == null || bookDto.getAuthor().isEmpty()) {
            throw new Exception("Author is required");
        }
        else if (bookDto.getPrice() == null) {
            throw new Exception("Price is required");
        }
        else if (bookDto.getBookType() == null) {
            throw new Exception("Book type is required");
        }

        Book entBook = bookRepo.save(bookMapper.map(bookDto, Book.class));

        return bookMapper.map(entBook, BookDto.class);
    }

    @SuppressWarnings("null")
    public BookDto getBook(Long bookId) {
        Optional<Book> book = bookRepo.findById(bookId);

        BookDto bookDto = new BookDto();

        if (book.isPresent()) {
            bookDto = bookMapper.map(book.get(), BookDto.class);
        }
        else {
            bookDto = null;
        }

        return bookDto;
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepo.findAll();

        List<BookDto> bookDtos = new ArrayList<BookDto>();

        for (Book book : books) {
            bookDtos.add(bookMapper.map(book, BookDto.class));
        }

        return bookDtos;
    }

    @SuppressWarnings("null")
    public Boolean deleteBook(Long bookId) {
        Boolean exists = bookRepo.existsById(bookId);

        if (exists) {
            bookRepo.deleteById(bookId);
        }
        else {
            return false;
        }

        return exists;
    }

    @SuppressWarnings("null")
    public Boolean editBook(Long bookId, BookDto updatedBook) {
        Boolean exists = bookRepo.existsById(bookId);

        if (exists) {
            Optional<Book> entBook = bookRepo.findById(bookId);
            entBook.get().setAuthor(updatedBook.getAuthor());
            entBook.get().setBookImage(updatedBook.getBookImage());
            entBook.get().setBookType(updatedBook.getBookType());
            entBook.get().setCategory(updatedBook.getCategory());
            entBook.get().setPrice(updatedBook.getPrice());
            entBook.get().setTittle(updatedBook.getTittle());

            if (entBook.get().getBookType() == BookType.PHYSICAL) {
                entBook.get().setEditorial(updatedBook.getEditorial());
                entBook.get().setIsbn(updatedBook.getIsbn());
            }

            bookRepo.save(entBook.get());
        }
        else {
            return false;
        }

        return exists;
    }
}
