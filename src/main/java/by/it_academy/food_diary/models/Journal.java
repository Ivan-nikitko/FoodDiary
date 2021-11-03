package by.it_academy.food_diary.models;

import javax.persistence.*;

@Entity
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User user;
    @OneToOne
    private Recipe recipe;
    @OneToOne
    private Product product;
    @Column
    private Double measure;
    //private MealTime mealTime;
}

//TODO add creation date update date User-creator
