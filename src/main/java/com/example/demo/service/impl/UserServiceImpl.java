package com.example.demo.service.impl;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserWithOrdersDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest req) {
        var user = new User();
        user.setName(req.getName());
        var saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getName());
    }

    @Override
    public Page<User> list(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<UserWithOrdersDto> allWithOrders() {
        return userRepository.findAllWithOrders().stream().map(u ->
                new UserWithOrdersDto(
                        u.getId(),
                        u.getName(),
                        u.getOrders().stream()
                                .map(o -> new UserWithOrdersDto.OrderSummaryDto(o.getId(), o.getProduct(), o.getTotal()))
                                .toList()
                )
        ).toList();
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
