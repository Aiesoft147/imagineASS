package com.imagine.book.services;

import com.imagine.book.model.entity.Book;

import java.util.List;

public interface ShoppingCartService
{
    List<Book> getBooksInCart();
    String addBookToCart(Integer bookId);
    String removeBookFromCart(Integer bookId);
}
