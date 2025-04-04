package com.food_delivery_app.food_delivery_back_end.modules.restaurant.service;

import com.food_delivery_app.food_delivery_back_end.modules.restaurant.dto.RestaurantDto;
import com.food_delivery_app.food_delivery_back_end.modules.restaurant.entity.Restaurant;
import com.food_delivery_app.food_delivery_back_end.modules.restaurant.response.RestaurantDetailResponse;
import com.food_delivery_app.food_delivery_back_end.modules.restaurant.response.RestaurantResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface RestaurantService {
    Page<RestaurantResponse> getAllRestaurants(int page, int limit);
    RestaurantDto updateRestaurant(Long id, RestaurantDto restaurantDto);
    RestaurantDetailResponse getRestaurant(Long id);
//    RestaurantResponse getRestaurant(Long id);
    void deleteRestaurant(Long id);
    String uploadImage(Long id, MultipartFile file) throws IOException;
}
