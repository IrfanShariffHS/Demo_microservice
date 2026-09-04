package com.microserviceProject.inventory_service.repository;

import com.microserviceProject.inventory_service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {



}
