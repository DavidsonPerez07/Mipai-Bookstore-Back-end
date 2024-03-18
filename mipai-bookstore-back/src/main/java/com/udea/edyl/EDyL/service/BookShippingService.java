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
import com.udea.edyl.EDyL.web.dto.BookShippingDto;

@Service
public class BookShippingService {
    BookShippingRepository bookShippingRepo;
    BookOrderRepository bookOrderRepo;
    ModelMapper bookShippingMapper;

    public BookShippingService(BookShippingRepository bookShippingRepo, 
    BookOrderRepository bookOrderRepo, 
    ModelMapper bookShippingMapper) {
        this.bookShippingRepo = bookShippingRepo;
        this.bookShippingMapper = bookShippingMapper;
        this.bookOrderRepo = bookOrderRepo;
    }

    @SuppressWarnings("null")
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

    @SuppressWarnings("null")
    public BookShippingDto getBookShipping(Long bookShippingId) {
        Optional<BookShipping> bookShipping = bookShippingRepo.findById(bookShippingId);

        BookShippingDto bookShippingDto = new BookShippingDto();

        if (bookShipping.isPresent()) {
            bookShippingDto = bookShippingMapper.map(bookShipping, BookShippingDto.class);
        }
        else {
            bookShippingDto = null;
        }

        return bookShippingDto;
    }

    public List<BookShippingDto> getAllBookShippings() {
        List<BookShipping> bookShippings = bookShippingRepo.findAll();

        List<BookShippingDto> bookShippingDtos = new ArrayList<BookShippingDto>();

        for (BookShipping bookShipping : bookShippings) {
            bookShippingDtos.add(bookShippingMapper.map(bookShipping, 
            BookShippingDto.class));
        }

        return bookShippingDtos;
    }

    @SuppressWarnings("null")
    public Boolean deleteBookShipping(Long bookShippingId) {
        Boolean exists = bookShippingRepo.existsById(bookShippingId);

        if (exists) {
            bookShippingRepo.deleteById(bookShippingId);
        }
        else {
            return false;
        }

        return exists;
    }

    @SuppressWarnings("null")
    public Boolean setDelivered(Long bookShippingId) {
        Boolean exists = bookShippingRepo.existsById(bookShippingId);

        if (exists) {
            Optional<BookShipping> entBookShipping = bookShippingRepo.findById(bookShippingId);
            entBookShipping.get().setDelivered(true);

            bookShippingRepo.save(entBookShipping.get());
        }
        else {
            return false;
        }

        return exists;
    }
}
