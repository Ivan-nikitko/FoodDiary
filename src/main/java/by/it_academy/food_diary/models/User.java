package by.it_academy.food_diary.models;

import by.it_academy.food_diary.models.api.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    private String login;
    private String password;
    private ERole role;
    @Column(name = "dateOfBirth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;


    @OneToOne
    private User userCreator;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
