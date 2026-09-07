package com.microserviceProject.inventory_service.service;

import com.microserviceProject.inventory_service.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;


    @Transactional
    public  boolean isInstock(String skuCode){

        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

}
