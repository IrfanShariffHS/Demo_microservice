package com.microserviceProject.orderservice.service;

import com.microserviceProject.orderservice.dto.OrderLineItemsRequest;
import com.microserviceProject.orderservice.dto.OrderRequest;
import com.microserviceProject.orderservice.model.Order;
import com.microserviceProject.orderservice.model.OrderLineItems;
import com.microserviceProject.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;


    public void placeOrder(OrderRequest orderRequest){
        Order order= new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList =  orderRequest.getOrderLineItems()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);

        orderRepository.save(order);

    }

    private OrderLineItems mapToDto(OrderLineItemsRequest orderLineItemsRequest){

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsRequest.getPrice());
        orderLineItems.setQuantity(orderLineItemsRequest.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsRequest.getSkuCode());

        return orderLineItems;
    }

}
