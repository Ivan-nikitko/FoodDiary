package by.it_academy.food_diary.models;

import by.it_academy.food_diary.models.api.ERole;
import by.it_academy.food_diary.models.api.EStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private ERole role;
    @Column
    private EStatus status;


    @OneToOne
    private User userCreator;
    private LocalDateTime creationDate;
    @Version
    private LocalDateTime updateDate;


    public User() {
        this.creationDate = LocalDateTime.now();
        this.updateDate = creationDate;
        this.status = EStatus.INACTIVE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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


    public User getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }
}
