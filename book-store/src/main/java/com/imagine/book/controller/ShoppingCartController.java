package com.imagine.book.controller;

import com.imagine.book.exceptions.AuthenticationFailedException;
import com.imagine.book.exceptions.BookNotFoundException;
import com.imagine.book.model.entity.Book;
import com.imagine.book.services.ShoppingCartService;
import com.imagine.book.services.impl.UsersServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
@Slf4j
public class ShoppingCartController
{
    @Autowired
    private ShoppingCartService shoppingCartService;
    private static final String AUTH_MESSAGE = "Unauthorized User access. Please login.";

    @GetMapping(value = "/get-added-books")
    public ResponseEntity<List<Book>> getAddedBooks()
    {
        try
        {
            if(StringUtils.isNotBlank(UsersServiceImpl.AUTH_TOKEN))
                return new ResponseEntity<>(shoppingCartService.getBooksInCart(), HttpStatus.OK);
            else
                throw new AuthenticationFailedException(AUTH_MESSAGE);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/add-book-to-cart")
    public ResponseEntity<String> addBookToCart(@RequestParam Integer bookId)
    {
        try
        {
            if(StringUtils.isNotBlank(UsersServiceImpl.AUTH_TOKEN))
                return new ResponseEntity<>(shoppingCartService.addBookToCart(bookId), HttpStatus.OK);
            else
                throw new AuthenticationFailedException(AUTH_MESSAGE);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "/remove-book-from-cart")
    public ResponseEntity<String> removeBookFromCart(@RequestParam Integer bookId)
    {
        try
        {
            if(StringUtils.isNotBlank(UsersServiceImpl.AUTH_TOKEN))
                return new ResponseEntity<>(shoppingCartService.removeBookFromCart(bookId), HttpStatus.OK);
            else
                throw new AuthenticationFailedException(AUTH_MESSAGE);
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
