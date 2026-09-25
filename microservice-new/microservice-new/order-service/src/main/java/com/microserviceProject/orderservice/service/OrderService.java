package com.microserviceProject.orderservice.service;

import com.microserviceProject.orderservice.dto.InventoryResponse;
import com.microserviceProject.orderservice.dto.OrderLineItemsRequest;
import com.microserviceProject.orderservice.dto.OrderRequest;
import com.microserviceProject.orderservice.model.Order;
import com.microserviceProject.orderservice.model.OrderLineItems;
import com.microserviceProject.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient webClient;


    public void placeOrder(OrderRequest orderRequest){
        Order order= new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList =  orderRequest.getOrderLineItems()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);

        order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        List<String> skuCodes= order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //Call Inventory Service, and place order if product is in stock

       InventoryResponse[] inventoryResponsesArray = webClient.get()
                .uri("http://localhost:8080/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean AllProductStock=Arrays.stream(inventoryResponsesArray)
                .allMatch(InventoryResponse::isInStock);

       if(AllProductStock){
           orderRepository.save(order);
       }
       else{
           throw new IllegalArgumentException("Product is not stock, please try again later");
       }


    }

    private OrderLineItems mapToDto(OrderLineItemsRequest orderLineItemsRequest){

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsRequest.getPrice());
        orderLineItems.setQuantity(orderLineItemsRequest.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsRequest.getSkuCode());

        return orderLineItems;
    }

}
