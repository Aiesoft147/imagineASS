package com.imagine.book.services.impl;

import com.imagine.book.model.entity.Book;
import com.imagine.book.model.entity.Order;
import com.imagine.book.repositories.BookRepository;
import com.imagine.book.repositories.OrderRepository;
import com.imagine.book.repositories.UserRepository;
import com.imagine.book.services.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrdersServiceImpl implements OrdersService
{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<Integer, List<Book>> getPreviousOrders()
    {
        Map<Integer, List<Book>> orderMap = new HashMap<>();
        try
        {
            List<Order> orders = orderRepository.findByUserId(UsersServiceImpl.USER_ID);

            orders.forEach(order -> {
                List<Integer> bookIdList = new ArrayList<>();
                String[] bookIdListAr = order.getBookIdList().split(",");
                for(String bookId: bookIdListAr)
                {
                    bookIdList.add(Integer.parseInt(bookId));
                }

                List<Book> bookList = bookIdList.stream()
                        .map(bookId -> bookRepository.findById(bookId))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList();

                orderMap.put(order.getOrderId(), bookList);
            });
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return orderMap;
    }

    @Override
    public String placeOrder()
    {
        try
        {
            double totalAmount = ShoppingCartServiceImpl.SHOPPING_CART.stream()
                    .map(bookId -> bookRepository.findById(bookId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .mapToDouble(Book::getPrice)
                    .sum();
            log.debug("Total Amount: {}",totalAmount);

            Order order = Order.builder()
                    .bookIdList(ShoppingCartServiceImpl.SHOPPING_CART.stream()
                            .map(Object::toString)
                            .collect(Collectors.joining(",")))
                    .userId(UsersServiceImpl.USER_ID)
                    .build();
            orderRepository.save(order);

            ShoppingCartServiceImpl.SHOPPING_CART.clear();
            log.info("Order placed. Shopping cart cleared.");

            return "Order placed. Total amount to pay: " + totalAmount;
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
