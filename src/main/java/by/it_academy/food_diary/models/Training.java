package by.it_academy.food_diary.models;

import javax.persistence.*;

@Entity
public class Training {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
   private String name;
   private double calories;
   @OneToOne
   private Profile profile;
   

}
