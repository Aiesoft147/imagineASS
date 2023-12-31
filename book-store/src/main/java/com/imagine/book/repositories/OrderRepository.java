package com.imagine.book.repositories;

import com.imagine.book.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>
{
    @Query("from Order where userId=:userId")
    List<Order> findByUserId(Integer userId);
}
