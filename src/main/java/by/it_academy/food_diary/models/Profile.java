package by.it_academy.food_diary.models;

import by.it_academy.food_diary.models.api.EActivity;
import by.it_academy.food_diary.models.api.EPurpose;
import by.it_academy.food_diary.models.api.ESex;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    User user;
    private double height;
    private double weight;
    private LocalDateTime birthdayDate;
    private ESex sex;
    private EActivity activity;
    private EPurpose purpose;

    @OneToOne
    private User userCreator;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

}
