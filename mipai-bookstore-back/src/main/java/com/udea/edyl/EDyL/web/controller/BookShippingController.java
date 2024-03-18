package com.udea.edyl.EDyL.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udea.edyl.EDyL.service.BookShippingService;
import com.udea.edyl.EDyL.web.dto.BookShippingDto;

@RestController
@RequestMapping("book-shippings")
public class BookShippingController {
    private BookShippingService bookShippingService;

    public BookShippingController(BookShippingService bookShippingService) {
        this.bookShippingService = bookShippingService;
    }

    @PostMapping("/save-book-shipping")
    public ResponseEntity<?> saveBookShipping(@RequestBody BookShippingDto bookShippingDto) 
    throws Exception {
        if (bookShippingDto == null) {
            return ResponseEntity.badRequest().body("Invalid book shipping data");
        }

        BookShippingDto resp;
        try {
            resp = bookShippingService.saveBookShipping(bookShippingDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/get-book-shipping")
    public ResponseEntity<?> getBookShipping(@RequestParam Long bookShippingId) {
        BookShippingDto resp = bookShippingService.getBookShipping(bookShippingId);

        if (resp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("This shipping doesn't exist");
        }

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/get-all-book-shippings")
    public ResponseEntity<?> getAllBookShippings() {
        List<BookShippingDto> bookShippingDtos = bookShippingService.getAllBookShippings();

        return ResponseEntity.ok(bookShippingDtos);
    }

    @PutMapping("/set-book-shipping-delivered")
    public ResponseEntity<?> setDelivered(@RequestParam Long bookShippingId) {
        Boolean resp = bookShippingService.setDelivered(bookShippingId);

        if (!resp) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("This book shipping doesn't exist");
        }

        return ResponseEntity.ok().build();
    }
}
