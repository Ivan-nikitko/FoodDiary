package by.it_academy.food_diary.models;

import by.it_academy.food_diary.models.api.EMealTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Profile profile;
    @OneToOne
    private Recipe recipe;
    @OneToOne
    private Product product;
    @Column
    private Double measure;
    private EMealTime mealTime;


    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

}

