package controller;

import dto.CreateUserRequest;
import dto.UserResponse;
import dto.UserWithOrdersDto;
import entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;

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
}
