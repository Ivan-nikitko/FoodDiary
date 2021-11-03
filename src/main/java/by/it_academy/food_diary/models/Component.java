package by.it_academy.food_diary.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Recipe recipe;
    @OneToOne
    private Product product;
    private Double measure;


    private Date creationDate;
    private Date updateDate;
}
