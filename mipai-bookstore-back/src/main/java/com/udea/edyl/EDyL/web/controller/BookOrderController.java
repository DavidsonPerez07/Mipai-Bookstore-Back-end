package com.udea.edyl.EDyL.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.udea.edyl.EDyL.service.BookOrderService;
import com.udea.edyl.EDyL.web.dto.BookOrderDto;

@RestController
@RequestMapping("book-orders")
public class BookOrderController {
    private BookOrderService bookOrderService;

    public BookOrderController(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @PostMapping("/save-book-order")
    public ResponseEntity<?> saveBookOrder(@RequestBody BookOrderDto bookOrderDto) throws Exception {
        if (bookOrderDto == null) {
            return ResponseEntity.badRequest().body("Invalid book order data"); 
        }

        BookOrderDto resp;
        try {
            resp = bookOrderService.saveBookOrder(bookOrderDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping("/get-book-order")
    public ResponseEntity<?> getBookOrder(@RequestParam Long bookOrderId) {
        BookOrderDto resp = bookOrderService.getBookOrder(bookOrderId);

        if (resp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This order doesn't exist");
        }

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/get-all-book-orders")
    public ResponseEntity<?> getAllBookOrders() {
        List<BookOrderDto> bookOrderDtos = bookOrderService.getAllBookOrders();

        return ResponseEntity.ok(bookOrderDtos);
    }

    @DeleteMapping("/delete-book-order")
    public ResponseEntity<?> deleteBookOrder(@RequestParam Long bookOrderId) {
        Boolean resp = bookOrderService.deleteBookOrder(bookOrderId);

        if (!resp) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This book order doesn't exist");
        }

        return ResponseEntity.ok().build();
    }
}
