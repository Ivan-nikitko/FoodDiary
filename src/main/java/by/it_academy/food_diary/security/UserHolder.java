package by.it_academy.food_diary.security;

import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.service.api.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {

    private final IUserService userService;

    public UserHolder(IUserService userService) {
        this.userService = userService;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User getUser(){
        String login = getAuthentication().getName();
        return userService.findByLogin(login);
    }
}
