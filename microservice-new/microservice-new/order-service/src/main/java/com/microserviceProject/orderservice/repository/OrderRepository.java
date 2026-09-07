package com.microserviceProject.orderservice.repository;

import com.microserviceProject.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order,Long> {



}
