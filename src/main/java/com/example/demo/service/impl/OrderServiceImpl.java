package com.example.demo.service.impl;

import com.example.demo.dto.CreateOrderRequest;
import com.example.demo.dto.OrderResponse;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OrderResponse create(CreateOrderRequest req) {
        var user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var order = new Order();
        order.setUser(user);
        order.setProduct(req.getProduct());
        order.setQuantity(req.getQuantity());
        order.setTotal(req.getTotal());

        var saved = orderRepository.save(order);
        return new OrderResponse(
                saved.getId(), user.getId(), saved.getProduct(),
                saved.getQuantity(), saved.getTotal(), saved.getCreatedAt()
        );
    }

    @Override
    public Page<OrderResponse> list(Long userId, Pageable pageable) {
        var page = (userId != null)
                ? orderRepository.findByUserId(userId, pageable)
                : orderRepository.findAll(pageable);

        return page.map(o -> new OrderResponse(
                o.getId(),
                (o.getUser() != null ? o.getUser().getId() : null),
                o.getProduct(),
                o.getQuantity(),
                o.getTotal(),
                o.getCreatedAt()
        ));
    }
}
