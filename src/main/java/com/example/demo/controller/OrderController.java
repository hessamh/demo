package com.example.demo.controller;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderResponse;
import com.example.demo.dto.PageResponse;
import com.example.demo.entity.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/api/orders")
@Validated
public class OrderController {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    public OrderController(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public OrderResponse create(@Valid @RequestBody CreateOrderRequest request){
        var user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found."));

        var order = new Order();
        order.setUser(user);
        order.setProduct(request.getProduct());
        order.setQuantity(request.getQuantity());
        order.setTotal(request.getTotal());

        Order savedOrder = orderRepository.save(order);
        return new OrderResponse(
                savedOrder.getId(), user.getId(), savedOrder.getProduct(),
                savedOrder.getQuantity(), savedOrder.getTotal(), savedOrder.getCreatedAt()
        );
    }

    @GetMapping
    public PageResponse<OrderResponse> orderPage(@RequestParam(required = false) @Positive(message = "{common.id.positive}") Long userId,
                                         @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable,
                                         @RequestParam(required = false, name = "sort") String sort
    ){
        Page<Order> page = (userId != null)
                ? orderRepository.findByUserId(userId, pageable)
                : orderRepository.findAll(pageable);

        Page<OrderResponse> dtoPage = page.map(order -> new OrderResponse(
                order.getId(),
                (order.getUser() != null ? order.getUser().getId() : null),
                order.getProduct(),
                order.getQuantity(),
                order.getTotal(),
                order.getCreatedAt()
        ));
        return PageResponse.of(dtoPage, sort);
    }
}
