package by.it_academy.food_diary.service;
/*
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.models.api.ERole;
import by.it_academy.food_diary.service.api.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthorizationService {

        private PasswordEncoder passwordEncoder;
        private final UserService userService;

    public UserAuthorizationService(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }


    public void saveUser(User user) {
            user.setRole(ERole.ROLE_USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
        }


        public User findByLoginAndPassword(String login, String password) {
            User user = userService.findByLogin(login);
            if (user != null) {
                if (passwordEncoder.matches(password, user.getPassword())) {
                    return user;
                }
            }
            return null;
        }
    }

*/
