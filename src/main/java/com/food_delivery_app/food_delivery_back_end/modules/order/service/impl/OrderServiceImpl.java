package com.food_delivery_app.food_delivery_back_end.modules.order.service.impl;

import com.food_delivery_app.food_delivery_back_end.constant.OrderStatusType;
import com.food_delivery_app.food_delivery_back_end.modules.auth.service.AuthService;
import com.food_delivery_app.food_delivery_back_end.modules.cart.service.CartService;
import com.food_delivery_app.food_delivery_back_end.modules.dish.entity.Dish;
import com.food_delivery_app.food_delivery_back_end.modules.dish.repository.DishRepository;
import com.food_delivery_app.food_delivery_back_end.modules.cart.entity.Cart;
import com.food_delivery_app.food_delivery_back_end.modules.cart.entity.CartItem;
import com.food_delivery_app.food_delivery_back_end.modules.order.entity.Order;
import com.food_delivery_app.food_delivery_back_end.modules.order.entity.OrderDetail;
import com.food_delivery_app.food_delivery_back_end.modules.order.repository.OrderDetailRepository;
import com.food_delivery_app.food_delivery_back_end.modules.order.repository.OrderRepository;
import com.food_delivery_app.food_delivery_back_end.modules.order.service.OrderService;
import com.food_delivery_app.food_delivery_back_end.modules.restaurant.entity.Restaurant;
import com.food_delivery_app.food_delivery_back_end.modules.restaurant.repostitory.RestaurantRepository;
import com.food_delivery_app.food_delivery_back_end.modules.user.entity.User;
import com.food_delivery_app.food_delivery_back_end.modules.user.repository.UserRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;



@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final Cart cart;
    private final AuthService authService;
    private final CartService cartService;

    //add dish into the current user's cart
    @Override
    public void addToCart(Long dishId, Integer quantity) {
        cartService.addToCart(authService.getCurrentUser().getId(), dishId, quantity);
    }

    //get the current user's cart
    @Override
    public Cart getCart() {
        return cartService.getCart(authService.getCurrentUser().getId());
    }

    @Override
    public void clearCart() {
        cartService.clearCart(authService.getCurrentUser().getId());
    }

    @Override
    public void removeCart() {
        cartService.removeCart(authService.getCurrentUser().getId());
    }
    //place an order
    @Override
    @Transactional
    public Order placeOrder() {
        User user = authService.getCurrentUser();
        Long userId = user.getId();
        User customer = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartService.getCart(userId);
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot place an order with an empty cart");
        }

        Restaurant restaurant = restaurantRepository.findById(cart.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Order order = new Order();
        order.setUser(customer);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatusType.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(cart.getTotalAmount());
        Order savedOrder = orderRepository.save(order);

        for(CartItem cartItem : cart.getItems()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setDish(dishRepository.findById(cartItem.getIdDish())
                    .orElseThrow(() -> new RuntimeException("Dish not found"))
            );
            orderDetail.setOrder(savedOrder);
            savedOrder.getOrderDetails().add(orderDetail);
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetailRepository.save(orderDetail);

        }
        cartService.clearCart(userId);

        return orderRepository.save(savedOrder);
    }
}
