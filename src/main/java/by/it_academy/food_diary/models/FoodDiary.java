package by.it_academy.food_diary.models;

import javax.persistence.Entity;

@Entity
public class FoodDiary {

    private Long id;
    //private  User user;
    //private MealTime mealTime;
    private Product product;
    private Recipe recipe;
    private Double measure;
}
