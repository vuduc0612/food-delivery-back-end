package com.food_delivery_app.food_delivery_back_end.modules.order.service;

import com.food_delivery_app.food_delivery_back_end.modules.order.dto.OrderDto;
import com.food_delivery_app.food_delivery_back_end.modules.order.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderResponse placeOrder();
//    Page<OrderResponse> getAllOrder();
    Page<OrderResponse> getAllOrderOfUser(Long userId, int page, int limit);
    Page<OrderResponse> getAllOrderOfRestaurant(Long restaurantId, int page, int limit);
    OrderResponse getOrder(Long id);
    OrderResponse updateOrder(Long id, OrderDto orderDto);
    void deleteOrder(Long id);
}
