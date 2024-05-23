package com.udea.edyl.EDyL.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.udea.edyl.EDyL.data.entity.BookOrder;
import com.udea.edyl.EDyL.data.entity.User;
import com.udea.edyl.EDyL.data.repository.BookOrderRepository;
import com.udea.edyl.EDyL.data.repository.UserRepository;
import com.udea.edyl.EDyL.web.dto.BookDto;
import com.udea.edyl.EDyL.web.dto.BookOrderDto;
import com.udea.edyl.EDyL.web.dto.BookQuantity;

@Service
public class BookOrderService {
    BookOrderRepository bookOrderRepo; 
    UserRepository userRepo;
    ModelMapper bookOrderMapper;
    BookService bookService;

    public BookOrderService(BookOrderRepository bookOrderRepo, UserRepository userRepo, 
    ModelMapper bookOrderMapper, BookService bookService) {
        this.bookOrderRepo = bookOrderRepo;
        this.userRepo = userRepo;
        this.bookOrderMapper = bookOrderMapper;
        this.bookService = bookService;
    }

    public BookOrderDto saveBookOrder(BookOrderDto bookOrderDto, List<BookQuantity> books) throws Exception {
        if (bookOrderDto == null) {
            throw new Exception("Invalid parameter");
        }
        else if (bookOrderDto.getOrderDate() == null) {
            throw new Exception("Date is required");
        }
        else if (bookOrderDto.getOrderValue() == null) {
            throw new Exception("Value is required");
        }

        List<BookDto> bookDtos = new ArrayList<>();

        for (BookQuantity book : books) {
            for (int i = 0; i < book.getQuantity(); i++) {
                BookDto bookDto = bookService.getBook(book.getBookId());
                bookService.editQuantity(book.getBookId());
                bookDtos.add(bookDto);
            }
        }

        bookOrderDto.setBooks(bookDtos);

        Optional<User> user = userRepo.findById(bookOrderDto.getUserId());

        if (!user.isPresent()) {
            throw new Exception("The user doesn't exist");
        }

        BookOrder entBookOrder = bookOrderRepo.save(bookOrderMapper.map(bookOrderDto, 
        BookOrder.class));
        entBookOrder.setUser(user.get());
        entBookOrder = bookOrderRepo.save(entBookOrder);

        return bookOrderMapper.map(entBookOrder, BookOrderDto.class);
    }

    public BookOrderDto getBookOrder(Long bookOrderId) {
        Optional<BookOrder> bookOrder = bookOrderRepo.findById(bookOrderId);

        BookOrderDto bookOrderDto;

        if (bookOrder.isPresent()) {
            bookOrderDto = bookOrderMapper.map(bookOrder, BookOrderDto.class);
        }
        else {
            bookOrderDto = null;
        }

        return bookOrderDto;
    }

    public List<BookOrderDto> getAllBookOrders() {
        List<BookOrder> bookOrders = bookOrderRepo.findAll();

        List<BookOrderDto> bookOrderDtos = new ArrayList<>();

        for (BookOrder bookOrder : bookOrders) {
            bookOrderDtos.add(bookOrderMapper.map(bookOrder, BookOrderDto.class));
        }

        return bookOrderDtos;
    }

    public Boolean deleteBookOrder(Long bookOrderId) {
        Boolean exists = bookOrderRepo.existsById(bookOrderId);

        if (exists) {
            bookOrderRepo.deleteById(bookOrderId);
        }
        else {
            return false;
        }

        return exists;
    }

    public List<BookOrderDto> getAllBookOrdersByUserId(Long userId) {
        List<BookOrder> bookOrders = bookOrderRepo.findAllByUserId(userId);

        List<BookOrderDto> bookOrderDtos = new ArrayList<>();

        for (BookOrder bookOrder : bookOrders) {
            bookOrderDtos.add(bookOrderMapper.map(bookOrder, BookOrderDto.class));
        }

        return bookOrderDtos;
    }
}
