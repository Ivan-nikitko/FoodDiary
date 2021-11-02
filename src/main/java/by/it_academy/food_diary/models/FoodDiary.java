package by.it_academy.food_diary.models;

import javax.persistence.*;

@Entity
public class FoodDiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User user;
 /*   @Column
    private Recipe recipe;
    @Column
    private Double measure;
    @Column
    private Product product;*/
    //private MealTime mealTime;
}
