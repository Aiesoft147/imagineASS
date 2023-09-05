package com.imagine.book.services;

import com.imagine.book.model.entity.Book;

import java.util.List;
import java.util.Map;

public interface OrdersService
{
    Map<Integer, List<Book>> getPreviousOrders();
    String placeOrder();
}
