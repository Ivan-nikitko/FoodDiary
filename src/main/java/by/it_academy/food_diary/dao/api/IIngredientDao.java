package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIngredientDao extends JpaRepository <Ingredient,Long>{
}
