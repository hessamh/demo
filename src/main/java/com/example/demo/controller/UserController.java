package com.example.demo.controller;

import com.example.demo.dto.UserResponse;
import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.PageResponse;
import com.example.demo.dto.UserWithOrdersDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }
    @GetMapping("/with-orders")
    public List<UserWithOrdersDto> allWithOrders() {
        return userService.allWithOrders();
    }

    @GetMapping("/list")
    public PageResponse<User> list(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false, name = "sort") String sort
    ) {
        Page<User> page = userService.list(pageable);
        return PageResponse.of(page, sort);
    }
}
