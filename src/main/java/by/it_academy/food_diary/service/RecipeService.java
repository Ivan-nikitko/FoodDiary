package by.it_academy.food_diary.service;

import by.it_academy.food_diary.controller.dto.RecipeDto;
import by.it_academy.food_diary.dao.api.IIngredientDao;
import by.it_academy.food_diary.dao.api.IRecipeDao;
import by.it_academy.food_diary.models.Ingredient;
import by.it_academy.food_diary.models.Recipe;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IRecipeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.util.List;

@Service
public class RecipeService implements IRecipeService {
    private final IRecipeDao recipeDao;
    private final IIngredientDao ingredientDao;
    private final UserHolder userHolder;
    private static final String RECIPE_NOT_FOUND = "Recipe not found";

    public RecipeService(IRecipeDao recipeDao, IIngredientDao ingredientDao, UserHolder userHolder) {
        this.recipeDao = recipeDao;
        this.ingredientDao = ingredientDao;
        this.userHolder = userHolder;
    }

    @Override
    public void save(RecipeDto recipeDto)  {
        Recipe recipe = new Recipe();
        recipe.setUserCreator(userHolder.getUser());
        List<Ingredient> ingredients = recipeDto.getIngredients();
        for (Ingredient ingredient : ingredients) {
            ingredient.setCreationDate(recipe.getCreationDate());
            ingredient.setUpdateDate(ingredient.getCreationDate());
            ingredientDao.save(ingredient);
        }
        recipe.setName(recipeDto.getName());
        Recipe savedRecipe = recipeDao.save(recipe);
        recipeDto.setId(savedRecipe.getId());
    }

    @Override
    public Page<Recipe> getAll(Pageable pageable) {
        return recipeDao.findAll(pageable);
    }

    public Page<Recipe> getAll(String name, Pageable pageable) {
        return recipeDao.findRecipeByNameContains(name,pageable);
    }

    @Override
    public Recipe get(Long id) {
        return recipeDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException(RECIPE_NOT_FOUND)
        );
    }

    @Override
    public void update(RecipeDto recipeDto, Long id) {
        Recipe recipeToUpdate = get(id);
        if (recipeToUpdate == null) {
            throw new IllegalArgumentException(RECIPE_NOT_FOUND);
        }
        if (recipeDto.getUpdateDate().isEqual(recipeToUpdate.getUpdateDate())) {
            recipeToUpdate.setName(recipeDto.getName());
            List<Ingredient> ingredients = recipeDto.getIngredients();
            for (Ingredient ingredient : ingredients) {
                ingredientDao.save(ingredient);
            }
            recipeToUpdate.setIngredients(ingredients);
            recipeDao.saveAndFlush(recipeToUpdate);
            recipeDto.setId(id);
        }else {
            throw new OptimisticLockException("Recipe has already been changed");
        }
    }

    @Override
    public void delete(RecipeDto recipeDto,Long id) {
        Recipe dataBaseRecipe = get(id);
        if (dataBaseRecipe == null) {
            throw new IllegalArgumentException(RECIPE_NOT_FOUND);
        }
        if (recipeDto.getUpdateDate().isEqual(dataBaseRecipe.getUpdateDate())) {
            recipeDao.deleteById(id);
            recipeDto.setId(id);
        }else {
            throw new OptimisticLockException("Recipe has already been changed");
        }
    }
}
