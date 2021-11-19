package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.controller.dto.LoginDto;
import by.it_academy.food_diary.security.JwtProvider;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class AuthController {

    private final UserService userService;

    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid LoginDto loginDto) {
        userService.save(loginDto);
        return "OK";
    }

    @PostMapping("/auth")
    public String auth(@RequestBody LoginDto loginDto) {
        User userEntity = userService.findByLoginAndPassword(loginDto.getLogin(), loginDto.getPassword());
        String token = "Bearer "+jwtProvider.generateToken(userEntity.getLogin());
        return token;
    }
}