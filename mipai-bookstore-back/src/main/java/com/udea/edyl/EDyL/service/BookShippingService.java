package com.udea.edyl.EDyL.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.udea.edyl.EDyL.data.entity.BookOrder;
import com.udea.edyl.EDyL.data.entity.BookShipping;
import com.udea.edyl.EDyL.data.repository.BookOrderRepository;
import com.udea.edyl.EDyL.data.repository.BookShippingRepository;
import com.udea.edyl.EDyL.web.dto.BookOrderDto;
import com.udea.edyl.EDyL.web.dto.BookShippingDto;

@Service
public class BookShippingService {
    BookShippingRepository bookShippingRepo;
    BookOrderRepository bookOrderRepo;
    ModelMapper bookShippingMapper;
    BookOrderService bookOrderService;

    public BookShippingService(BookShippingRepository bookShippingRepo, 
    BookOrderRepository bookOrderRepo, 
    ModelMapper bookShippingMapper,
    BookOrderService bookOrderService) {
        this.bookShippingRepo = bookShippingRepo;
        this.bookShippingMapper = bookShippingMapper;
        this.bookOrderRepo = bookOrderRepo;
        this.bookOrderService = bookOrderService;
    }

    public BookShippingDto saveBookShipping(BookShippingDto bookShippingDto) throws Exception {
        if (bookShippingDto == null) {
            throw new Exception("Invalid parameter");
        }
        else if (bookShippingDto.getShippingDate() == null) {
            throw new Exception("Shipping date is required");
        }

        Optional<BookOrder> order = bookOrderRepo.findById(bookShippingDto.getBookOrderId());

        if (!order.isPresent()) {
            throw new Exception("The order doesn't exist"); 
        }

        BookShipping entBookShipping = bookShippingRepo.save(bookShippingMapper.map(bookShippingDto, 
        BookShipping.class));
        entBookShipping.setBookOrder(order.get());
        entBookShipping = bookShippingRepo.save(entBookShipping);

        return bookShippingMapper.map(entBookShipping, BookShippingDto.class);
    }

    public BookShippingDto getBookShipping(Long bookShippingId) {
        Optional<BookShipping> bookShipping = bookShippingRepo.findById(bookShippingId);

        BookShippingDto bookShippingDto;

        if (bookShipping.isPresent()) {
            bookShippingDto = bookShippingMapper.map(bookShipping, BookShippingDto.class);
        }
        else {
            bookShippingDto = null;
        }

        return bookShippingDto;
    }

    public List<BookShippingDto> getAllBookShippings() {
        List<BookShippingDto> bookShippingDtos = new ArrayList<>();

        for (BookShipping bookShipping : bookShippingRepo.findAll()) {
            bookShippingDtos.add(bookShippingMapper.map(bookShipping, 
            BookShippingDto.class));
        }

        return bookShippingDtos;
    }

    public Boolean deleteBookShipping(Long bookShippingId) {
        if (existById(bookShippingId)) {
            bookShippingRepo.deleteById(bookShippingId);
        }
        else {
            return false;
        }

        return existById(bookShippingId);
    }

    public Boolean setDelivered(Long bookShippingId) {
        if (existById(bookShippingId)) {
            Optional<BookShipping> entBookShipping = bookShippingRepo.findById(bookShippingId);
            entBookShipping.get().setDelivered(true);

            bookShippingRepo.save(entBookShipping.get());
        }
        else {
            return false;
        }

        return existById(bookShippingId);
    }

    public Boolean existById(Long bookShippingId) {
        return bookShippingRepo.existsById(bookShippingId);
    }

    public List<BookShippingDto> getAllBookShippingsByUser(Long userId) {
        List<BookShippingDto> returnShippings = new ArrayList<>();

        for (BookOrderDto bookOrder : bookOrderService.getAllBookOrdersByUserId(userId)) {
            BookShippingDto bookShipping = bookShippingMapper.map(
                bookShippingRepo.findByBookOrderId(bookOrder.getOrderId()), 
                BookShippingDto.class);

            returnShippings.add(bookShipping);
        }

        return returnShippings;
    }
}
