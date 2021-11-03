package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecipeDao extends JpaRepository <Recipe,Long>{
}
