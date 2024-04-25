package com.udea.edyl.EDyL.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.udea.edyl.EDyL.service.BookService;
import com.udea.edyl.EDyL.web.dto.BookDto;

@RestController
@RequestMapping("books")
@CrossOrigin(value = "http://localhost:3000")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;  
    }

    @PostMapping("/save-book")
    public ResponseEntity<?> saveBook(@RequestBody BookDto bookDto) throws Exception {
        if (bookDto == null) {
            return ResponseEntity.badRequest().body("Invalid book data");
        }

        BookDto resp;
        try {
            resp = bookService.saveBook(bookDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/get-book")
    public ResponseEntity<?> getBook(@RequestParam Long bookId) {
        BookDto resp;
        resp = bookService.getBook(bookId);

        if (resp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This book doesn't exist");
        }

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/get-all-books")
    public ResponseEntity<?> getAllBooks() {
        List<BookDto> bookDtos = bookService.getAllBooks();

        return ResponseEntity.ok(bookDtos);
    }

    @DeleteMapping("/delete-book")
    public ResponseEntity<?> deleteBook(@RequestParam Long bookId) {
        Boolean resp;
        resp = bookService.deleteBook(bookId);

        if (!resp) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This book doesn't exist");
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit-book")
    public ResponseEntity<?> editBook(@RequestParam Long bookId, @RequestBody BookDto updatedBook) {
        
        Boolean resp = bookService.editBook(bookId, updatedBook);

        if (!resp) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This book doesn't exist");
        }

        return ResponseEntity.ok().build();
    }
}
