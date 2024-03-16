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

@Service
public class BookOrderService {
    BookOrderRepository bookOrderRepo;
    UserRepository userRepo;
    ModelMapper bookOrderMapper;

    public BookOrderService(BookOrderRepository bookOrderRepo, UserRepository userRepo, ModelMapper bookOrderMapper) {
        this.bookOrderRepo = bookOrderRepo;
        this.userRepo = userRepo;
        this.bookOrderMapper = bookOrderMapper;
    }

    @SuppressWarnings("null")
    public BookOrderDto saveBookOrder(BookOrderDto bookOrderDto) throws Exception {
        if (bookOrderDto == null) {
            throw new Exception("Invalid parameter");
        }
        else if (bookOrderDto.getOrderDate() == null) {
            throw new Exception("Date is required");
        }
        
        Float orderValue = 0.0f;

        for (BookDto book : bookOrderDto.getBooks()) {
            orderValue += book.getPrice();
        }

        Optional<User> user = userRepo.findById(bookOrderDto.getUserId());

        if (!user.isPresent()) {
            throw new Exception("The user does't exist");
        }

        BookOrder entBookOrder = bookOrderRepo.save(bookOrderMapper.map(bookOrderDto, BookOrder.class));
        entBookOrder.setOrderValue(orderValue);
        entBookOrder.setUser(user.get());
        entBookOrder = bookOrderRepo.save(entBookOrder);

        return bookOrderMapper.map(entBookOrder, BookOrderDto.class);
    }

    @SuppressWarnings("null")
    public BookOrderDto getBookOrder(Long bookOrderId) {
        Optional<BookOrder> bookOrder = bookOrderRepo.findById(bookOrderId);

        BookOrderDto bookOrderDto = new BookOrderDto();

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

        List<BookOrderDto> bookOrderDtos = new ArrayList<BookOrderDto>();

        for (BookOrder bookOrder : bookOrders) {
            bookOrderDtos.add(bookOrderMapper.map(bookOrder, BookOrderDto.class));
        }

        return bookOrderDtos;
    }
}
