package com.food_delivery_app.food_delivery_back_end.modules.order.dto;

import com.food_delivery_app.food_delivery_back_end.constant.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderResponse {
    private Long orderId;
    private Double totalAmount;
    private OrderStatusType status;
    private String paymentUrl;
    private List<OrderDetailResponse> orderDetailResponses;

}
