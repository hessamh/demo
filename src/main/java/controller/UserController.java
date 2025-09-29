package controller;

import dto.UserWithOrdersDto;
import entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/api/users")
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
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userRepository.save(user));
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
