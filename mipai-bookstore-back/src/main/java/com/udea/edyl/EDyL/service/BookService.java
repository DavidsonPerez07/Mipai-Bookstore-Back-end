package com.udea.edyl.EDyL.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.udea.edyl.EDyL.data.entity.Book;
import com.udea.edyl.EDyL.data.entity.BookType;
import com.udea.edyl.EDyL.data.repository.BookRepository;
import com.udea.edyl.EDyL.web.dto.BookDto;
import com.udea.edyl.EDyL.web.mapper.BookMapper;

@Service
public class BookService {
    private BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
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

        Book entBook = BookMapper.INSTANCE.bookDtoToBook(bookDto);
        entBook = bookRepo.save(entBook);

        return BookMapper.INSTANCE.bookToBookDto(entBook);
    }

    @SuppressWarnings("null")
    public BookDto getBook(Long bookId) {
        Optional<Book> book;
        book = bookRepo.findById(bookId);

        BookDto bookDto = new BookDto();

        if (book.isPresent()) {
            bookDto = BookMapper.INSTANCE.bookToBookDto(book.get());
        }
        else {
            bookDto = null;
        }

        return bookDto;
    }

    public List<BookDto> getAllBooks() {
        List<Book> books;
        books = bookRepo.findAll();

        return BookMapper.INSTANCE.BooksToBookDtos(books);
    }

    @SuppressWarnings("null")
    public boolean deleteBook(Long bookId) {
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
