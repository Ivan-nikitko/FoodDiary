package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.controller.dto.ProductDto;
import by.it_academy.food_diary.controller.dto.RecipeDto;
import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.models.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRecipeService  {

    void save( RecipeDto recipeDto);
    Page<Recipe> getAll (Pageable pageable);
    Page<Recipe> getAll (String name,Pageable pageable);
    Recipe get(Long id);
    void update(RecipeDto recipeDto, Long id);
    void delete (RecipeDto recipeDto,Long id);

}
