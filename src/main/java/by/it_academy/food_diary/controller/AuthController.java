package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.controller.dto.LoginDto;
import by.it_academy.food_diary.email.MailSenderTLS;
import by.it_academy.food_diary.security.JwtProvider;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final UserService userService;
    private final MailSenderTLS mailSenderTLS;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, MailSenderTLS mailSenderTLS, JwtProvider jwtProvider) {
        this.userService = userService;
        this.mailSenderTLS = mailSenderTLS;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid LoginDto loginDto) {
        if (userService.findByLogin(loginDto.getLogin()) == null) {
            userService.save(loginDto);
            long id = userService.findByLogin(loginDto.getLogin()).getId();
            mailSenderTLS.send("Activate your account", "http://localhost:8080/activate/" + id, loginDto.getLogin());
            return new ResponseEntity<>("User created", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Login or password are incorrect", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth(@RequestBody LoginDto loginDto) {
        User user = userService.findByLoginAndPassword(loginDto.getLogin(), loginDto.getPassword());
        if (user != null) {
            String token = jwtProvider.generateToken(user.getLogin());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/activate/{id}")
    public ResponseEntity<String> activate(@PathVariable("id") Long id) {
        try {
            userService.activateUser(id);
            return new ResponseEntity<>("User activated", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}