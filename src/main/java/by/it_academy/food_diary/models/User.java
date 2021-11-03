package by.it_academy.food_diary.models;

import by.it_academy.food_diary.models.api.Role;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    private String login;
    private String password;
    private Role role;
}
