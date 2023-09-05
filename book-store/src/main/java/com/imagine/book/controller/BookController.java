package com.imagine.book.controller;

import com.imagine.book.exceptions.AuthenticationFailedException;
import com.imagine.book.exceptions.BookNotFoundException;
import com.imagine.book.model.entity.Book;
import com.imagine.book.services.BooksService;
import com.imagine.book.services.impl.UsersServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/books")
@Slf4j
public class BookController
{
    @Autowired
    private BooksService booksService;

    private static final String AUTH_MESSAGE = "Unauthorized User access. Please login.";

    @GetMapping(value = "/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks()
    {
        try
        {
            if(StringUtils.isNotBlank(UsersServiceImpl.AUTH_TOKEN))
                return new ResponseEntity<>(booksService.getAllBooks(), HttpStatus.OK);
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

    @GetMapping(value = "/get-book-by-id")
    public ResponseEntity<String> getBookByID(@RequestParam Integer bookId)
    {
        try
        {
            if(StringUtils.isNotBlank(UsersServiceImpl.AUTH_TOKEN))
            {
                Book book = booksService.getBookByID(bookId);
                if(book != null)
                    return new ResponseEntity<>("Book found: \n"+ book, HttpStatus.OK);
                else
                    throw new BookNotFoundException("No books found with the given ID.");
            }
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
    @GetMapping(value = "/search-book")
    public ResponseEntity<String> searchBook(@RequestParam String keyword)
    {
        try
        {
            if(StringUtils.isNotBlank(UsersServiceImpl.AUTH_TOKEN))
            {
                List<Book> bookList = booksService.searchBook(keyword);
                if(!bookList.isEmpty())
                    return new ResponseEntity<>("Books found: \n"+bookList, HttpStatus.OK);
                else
                    throw new BookNotFoundException("No books found with the given keyword.");
            }
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
