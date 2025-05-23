package com.food_delivery_app.food_delivery_back_end.modules.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RestaurantDto {
    private String name;
    private String address;
    private String phone;
    private String description;
}
