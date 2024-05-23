package com.udea.edyl.EDyL.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.udea.edyl.EDyL.data.entity.Book;
import com.udea.edyl.EDyL.data.entity.BookOrder;
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

    public BookDto saveBook(BookDto bookDto) throws Exception {
        if (bookDto == null) {
            throw new Exception("Invalid parameter");
        }
        else if (bookDto.getBookName() == null || bookDto.getBookName().isEmpty()) {
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
        else if (bookDto.getEditorial() == null) {
            throw new Exception("Editorial is required");
        }
        else if (bookDto.getBookDescription() == null) {
            throw new Exception("Description is required");
        }
        else if (bookDto.getQuantity() == null) {
            throw new Exception("Quantity is required");
        }
        else if (bookDto.getBookImage() == null) {
            throw new Exception("Image is required");
        }

        Book entBook = bookRepo.save(bookMapper.map(bookDto, Book.class));

        return bookMapper.map(entBook, BookDto.class);
    }

    public BookDto getBook(Long bookId) {
        Optional<Book> book = bookRepo.findById(bookId);

        BookDto bookDto;

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

        List<BookDto> bookDtos = new ArrayList<>();

        for (Book book : books) {
            bookDtos.add(bookMapper.map(book, BookDto.class));
        }

        return bookDtos;
    }

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

    public Boolean editBook(Long bookId, BookDto updatedBook) {
        Boolean exists = bookRepo.existsById(bookId);

        if (exists) {
            Optional<Book> entBook = bookRepo.findById(bookId);
            entBook.get().setBookName(updatedBook.getBookName());
            entBook.get().setAuthor(updatedBook.getAuthor());
            entBook.get().setBookDescription(updatedBook.getBookDescription());
            entBook.get().setPrice(updatedBook.getPrice());
            entBook.get().setCategory(updatedBook.getCategory());
            entBook.get().setQuantity(updatedBook.getQuantity());
            entBook.get().setBookType(updatedBook.getBookType());
            entBook.get().setBookImage(updatedBook.getBookImage());
            entBook.get().setEditorial(updatedBook.getEditorial());

            bookRepo.save(entBook.get());
        }
        else {
            return false;
        }

        return exists;
    }

    public void editQuantity(Long bookId) throws Exception {
        Boolean exists = bookRepo.existsById(bookId);

        if (exists) {
            Optional<Book> entBook = bookRepo.findById(bookId);

            if (entBook.get().getQuantity() >= 1) {
                entBook.get().setQuantity(entBook.get().getQuantity() - 1);
            
                bookRepo.save(entBook.get());
            }
            else {
                throw new Exception("There is not enough books");
            }
        }
    }

    public List<BookDto> getBooksByCategory(String category) {
        List<Book> books = bookRepo.findAllByCategory(category);

        List<BookDto> bookDtos = new ArrayList<>();

        for (Book book : books) {
            bookDtos.add(bookMapper.map(book, BookDto.class));
        }

        return bookDtos;
    }

    public void saveBookOrderInBook(Long bookId, BookOrder bookOrder) throws Exception {
        Boolean exists = bookRepo.existsById(bookId);

        if (exists) {
            Optional<Book> entBook = bookRepo.findById(bookId);
            entBook.get().getBookOrders().add(bookOrder);

            bookRepo.save(entBook.get());
        }
        else {
            throw new Exception("Isn't possible to relate the book order with the book");
        }
    }
}
