package com.imagine.book.services.impl;

import com.imagine.book.exceptions.BookNotFoundException;
import com.imagine.book.model.entity.Book;
import com.imagine.book.repositories.BookRepository;
import com.imagine.book.services.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService
{
    @Autowired
    private BookRepository bookRepository;
    public static final List<Integer> SHOPPING_CART = new ArrayList<>();

    @Override
    public List<Book> getBooksInCart()
    {
        List<Book> bookList = new ArrayList<>();
        try
        {
            bookList = SHOPPING_CART.stream()
                    .map(bookId -> bookRepository.findById(bookId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return bookList;
    }

    @Override
    public String addBookToCart(Integer bookId)
    {
        try
        {
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            if(bookOptional.isPresent())
            {
                Book book = bookOptional.get();
                bookId = book.getBookId();
                SHOPPING_CART.add(bookId);

                Integer quantity = book.getQuantity();
                quantity--;
                log.info("Current quantity for Book ID {} is {}",bookId, quantity);

                bookRepository.updateBookQuantity(bookId, quantity);

                return "Book added to cart successfully.";
            }
            else
                throw new BookNotFoundException("Invalid Book ID.");
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public String removeBookFromCart(Integer bookId)
    {
        try
        {
            Optional<Book> bookOptional = bookRepository.findById(bookId);
            boolean bookIdPresentInCart = SHOPPING_CART.stream()
                    .anyMatch(book -> book.equals(bookId));

            if(bookOptional.isPresent() && bookIdPresentInCart)
            {
                Book book = bookOptional.get();
                SHOPPING_CART.remove(book.getBookId());

                Integer quantity = book.getQuantity();
                quantity++;
                log.info("Current quantity for Book ID {} is {}",bookId, quantity);

                bookRepository.updateBookQuantity(bookId, quantity);
                return "Book removed from cart successfully.";
            }
            else
                throw new BookNotFoundException("Invalid Book ID.");
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
