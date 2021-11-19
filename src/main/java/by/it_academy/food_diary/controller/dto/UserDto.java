package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.api.ERole;
import by.it_academy.food_diary.models.api.EStatus;

import java.time.LocalDateTime;

public class UserDto {

    private String name;
    private String login;
    private String password;
    private ERole role;
    private EStatus status;
    private LocalDateTime updateDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
