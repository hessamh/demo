package controller;

import dto.CreateOrderRequest;
import dto.OrderResponse;
import entity.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import repository.OrderRepository;
import repository.UserRepository;

import java.util.List;

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
    public List<OrderResponse> orderList(@RequestParam(required = false) @Positive(message = "{common.id.positive}") Long userId){
        var orders = (userId != null) ? orderRepository.findByUserId(userId) : orderRepository.findAll();
        return orders.stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        (order.getUser() != null ? order.getUser().getId() : null),
                        order.getProduct(), order.getQuantity(), order.getTotal(), order.getCreatedAt()))
                .toList();
    }
}
