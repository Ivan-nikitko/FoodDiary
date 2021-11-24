package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.controller.dto.validation.annotation.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginDto {
    @Email
    @NotBlank(message = "Email is mandatory")
    private String login;
    @ValidPassword
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
