package by.it_academy.food_diary.models;

import by.it_academy.food_diary.models.api.Activity;
import by.it_academy.food_diary.models.api.Sex;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    User user;
    private double height;
    private double weight;
    private Date birthdayDate;
    private Sex sex;
    private Activity activity;

}
