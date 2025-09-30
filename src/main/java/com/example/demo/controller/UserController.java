package com.example.demo.controller;

import com.example.demo.dto.UserResponse;
import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.PageResponse;
import com.example.demo.dto.UserWithOrdersDto;
import com.example.demo.entity.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> allUsers(){
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request){
        var user = new User();
        user.setName(request.getName());
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(new UserResponse(savedUser.getId(), savedUser.getName()));
    }

    @GetMapping("/with-orders")
    public List<UserWithOrdersDto> allWithOrders(){
        return userRepository.findAllWithOrders().stream().map(user ->
                new UserWithOrdersDto(
                        user.getId(),
                        user.getName(),
                        user.getOrders().stream().map(order ->
                                new UserWithOrdersDto.OrderSummaryDto(
                                        order.getId(), order.getProduct(), order.getTotal()
                                )).toList()
                )
        ).toList();
    }

    @GetMapping("/pageList")
    public PageResponse<User> list(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                   @RequestParam(required = false, name = "sort") String sort) {
        Page<User> page = userRepository.findAll(pageable);
        return PageResponse.of(page, sort);
    }
}
