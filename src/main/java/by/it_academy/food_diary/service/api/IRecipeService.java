package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.models.Recipe;

import java.util.List;

public interface IRecipeService {
    void save(Recipe recipe);
    List<Recipe> getAll ();
    Recipe get(Long id);
    void update(Recipe updatedRecipe, Long id);
    void delete (Long id);
}
