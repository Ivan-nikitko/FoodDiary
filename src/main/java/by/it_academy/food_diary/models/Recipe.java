package by.it_academy.food_diary.models;

import javax.persistence.*;
import java.util.List;


@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @ManyToMany
    @JoinColumn(name = "products_id")
    private List<Product> products;

}
