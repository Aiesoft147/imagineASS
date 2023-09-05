package com.imagine.book.repositories;

import com.imagine.book.model.entity.Order;
import com.imagine.book.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>
{
    @Query("from User where username=:username")
    Optional<User> findByUserName(String username);
}
