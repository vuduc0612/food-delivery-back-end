package com.food_delivery_app.food_delivery_back_end.modules.order.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food_delivery_app.food_delivery_back_end.constant.AddToCartResultType;
import com.food_delivery_app.food_delivery_back_end.modules.cart.dto.CartDto;
import com.food_delivery_app.food_delivery_back_end.modules.cart.entity.CartItem;
import com.food_delivery_app.food_delivery_back_end.modules.cart.entity.Cart;
import com.food_delivery_app.food_delivery_back_end.modules.order.entity.Order;
import com.food_delivery_app.food_delivery_back_end.modules.order.entity.OrderDetail;
import com.food_delivery_app.food_delivery_back_end.modules.order.response.OrderDetailResponse;
import com.food_delivery_app.food_delivery_back_end.modules.order.response.OrderResponse;
import com.food_delivery_app.food_delivery_back_end.modules.order.service.OrderService;
import com.food_delivery_app.food_delivery_back_end.response.CustomPageResponse;
import com.food_delivery_app.food_delivery_back_end.response.ResponseObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/orders")
@Tag(name = "Orders API", description = "Provides endpoints for orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    //CART
    @PostMapping("/cart")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add to cart", description = "Returns the cart")
    public ResponseEntity<?> addToCart(
            @RequestParam Long dishId,
            @RequestParam Integer quantity,
            @RequestParam(defaultValue = "false") boolean force) {
        //System.out.println("idDish: " + idDish + " quantity: " + quantity);
        AddToCartResultType addToCartResultType = orderService.addToCart(dishId, quantity, force);

        Cart cart = orderService.getCart();

        if (cart == null) {
            System.out.println("Cart is null");
        }
        //System.out.println("Cart is not null");
        // Chuyển Cart thành CartDTO
        CartDto cartDto = new CartDto();
        cartDto.setUserId(cart.getUserId());
        cartDto.setRestaurantId(cart.getRestaurantId());
        cartDto.setItems(cart.getItems());
        cartDto.setTotalAmount(cart.getTotalAmount());
        cartDto.setAddToCartResultType(addToCartResultType);

        if (addToCartResultType == AddToCartResultType.SUCCESS) {
            return ResponseEntity.ok(cartDto);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(cartDto);
        }
    }

    @PutMapping("/cart")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update cart", description = "Returns the cart")
    public ResponseEntity<CartDto> updateCart(
            @RequestParam Long dishId,
            @RequestParam Integer quantity) {
        orderService.updateCart(dishId, quantity);

        Cart cart = orderService.getCart();
        if (cart == null) {
            System.out.println("Cart is null");
        }
        //System.out.println("Cart is not null");
        // Chuyển Cart thành CartDTO
        CartDto cartDto = new CartDto();
        cartDto.setUserId(cart.getUserId());
        cartDto.setRestaurantId(cart.getRestaurantId());
        cartDto.setItems(cart.getItems());
        cartDto.setTotalAmount(cart.getTotalAmount());

        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/cart/dish/{dishId}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Remove from cart", description = "Returns the cart")
    public ResponseEntity<CartDto> removeItemFromCart(
            @PathVariable Long dishId) {
        orderService.removeItem(dishId);

        Cart cart = orderService.getCart();
        if (cart == null) {
            System.out.println("Cart is null");
        }
        //System.out.println("Cart is not null");
        // Chuyển Cart thành CartDTO
        CartDto cartDto = new CartDto();
        cartDto.setUserId(cart.getUserId());
        cartDto.setRestaurantId(cart.getRestaurantId());
        cartDto.setItems(cart.getItems());
        cartDto.setTotalAmount(cart.getTotalAmount());

        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/cart")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Remove from cart", description = "Returns the cart")
    public ResponseEntity<CartDto> removeAllItemFromCart(
            @RequestParam Long dishId) {
        orderService.removeItem(dishId);

        Cart cart = orderService.getCart();
        if (cart == null) {
            System.out.println("Cart is null");
        }
        //System.out.println("Cart is not null");
        // Chuyển Cart thành CartDTO
        CartDto cartDto = new CartDto();
        cartDto.setUserId(cart.getUserId());
        cartDto.setRestaurantId(cart.getRestaurantId());
        cartDto.setItems(cart.getItems());
        cartDto.setTotalAmount(cart.getTotalAmount());

        return ResponseEntity.ok(cartDto);
    }

    @GetMapping("/cart")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get cart", description = "Returns the cart")
    public ResponseEntity<CartDto> getCart() {
        Cart cart = orderService.getCart();
        if (cart == null) {
            System.out.println("Cart is null");
        }
        //System.out.println("Cart is not null");
        // Chuyển Cart thành CartDTO
        CartDto cartDto = new CartDto();
        cartDto.setUserId(cart.getUserId());
        cartDto.setRestaurantId(cart.getRestaurantId());
        cartDto.setItems(cart.getItems());
        cartDto.setTotalAmount(cart.getTotalAmount());

        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/cart/clear")
    @Operation(summary = "Clear cart", description = "Clears the cart")
    public ResponseEntity<String> clearCart() {
        orderService.clearCart();
        return ResponseEntity.ok("Cart cleared");
    }

    @PostMapping("/cart/remove")
    @Operation(summary = "Remove item from cart", description = "Returns the cart")
    public ResponseEntity<String> removeCart() {
        orderService.removeCart();
        return ResponseEntity.ok("Cart removed");
    }

    /*
        -----ORDER----
     */

    //Get all order of user
    @GetMapping("users/{user_id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get all orders", description = "Returns all orders")
    public ResponseEntity<CustomPageResponse<OrderResponse>> getAllOrderOfUser(
            @PathVariable("user_id") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        Page<OrderResponse> orderResponses = orderService.getAllOrderOfUser(userId, page, limit);

        return ResponseEntity.ok(
                CustomPageResponse.<OrderResponse>builder()
                        .message(String.format("Get all orders of user %d successfully", userId))
                        .status(HttpStatus.OK)
                        .data(orderResponses.getContent())
                        .totalPages(orderResponses.getTotalPages())
                        .currentPage(orderResponses.getNumber())
                        .build()
        );
    }

    //Get all order of restaurant
    @GetMapping("restaurants/{restaurant_id}")
    @PreAuthorize("hasRole('RESTAURANT')")
    public ResponseEntity<CustomPageResponse<OrderResponse>> getAllOrderOfRestaurant(
            @PathVariable("restaurant_id") Long restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        Page<OrderResponse> orderResponses = orderService.getAllOrderOfRestaurant(restaurantId, page, limit);

        return ResponseEntity.ok(
                CustomPageResponse.<OrderResponse>builder()
                        .message(String.format("Get all orders of restaurant %d successfully", restaurantId))
                        .status(HttpStatus.OK)
                        .data(orderResponses.getContent())
                        .totalPages(orderResponses.getTotalPages())
                        .currentPage(orderResponses.getNumber())
                        .build()
        );
    }

    @PostMapping("")
    @Operation(summary = "Place order", description = "Returns the order")
    public ResponseEntity<OrderResponse> placeOrder() {
        return ResponseEntity.ok(orderService.placeOrder());
    }

}
