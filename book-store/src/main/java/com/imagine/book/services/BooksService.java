package com.imagine.book.services;

import com.imagine.book.model.entity.Book;

import java.util.List;

public interface BooksService
{
    List<Book> getAllBooks();
    Book getBookByID(Integer id);
    List<Book> searchBook(String keyword);
}
