package com.imagine.book.repositories;

import com.imagine.book.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>
{
    @Query("from Book where LOWER(title) like LOWER(CONCAT('%', :keyword, '%')) " +
            "or LOWER(author) like LOWER(CONCAT('%', :keyword, '%')) " +
            "or LOWER(genre) like LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> searchBookByKeyword(String keyword);

    @Modifying
    @Transactional
    @Query("update Book b set b.quantity=:quantity where b.bookId=:bookId")
    void updateBookQuantity(Integer bookId, Integer quantity);
}
