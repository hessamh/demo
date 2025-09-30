package com.example.demo.service;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.dto.UserWithOrdersDto;
import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest req);
    Page<User> list(Pageable pageable);
    List<UserWithOrdersDto> allWithOrders();
    User getById(Long id);
}
