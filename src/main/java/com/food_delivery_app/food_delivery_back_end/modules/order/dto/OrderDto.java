package com.food_delivery_app.food_delivery_back_end.modules.order.dto;

import com.food_delivery_app.food_delivery_back_end.constant.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private Long userId;
    private Long restaurantId;
    private OrderStatusType status;
}
