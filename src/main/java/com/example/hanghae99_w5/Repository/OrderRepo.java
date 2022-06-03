package com.example.hanghae99_w5.Repository;

import com.example.hanghae99_w5.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Orders,Long> {
}
