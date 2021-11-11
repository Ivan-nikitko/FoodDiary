package by.it_academy.food_diary.service;

import by.it_academy.food_diary.dao.api.IRecipeDao;
import by.it_academy.food_diary.models.Recipe;
import by.it_academy.food_diary.service.api.IRecipeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeService implements IRecipeService {
    private IRecipeDao recipeDao;

    public RecipeService(IRecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    @Override
    public void save(Recipe recipe) {
        recipeDao.save(recipe);
    }

    @Override
    public Page<Recipe> getAll(Pageable pageable) {
        return null;
    }

    public List<Recipe> getAll() {
        return recipeDao.findAll();
    }

    @Override
    public Recipe get(Long id) {
        return recipeDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Recipe not found")
        );
    }

    @Override
    public void update(Recipe updatedRecipe, Long id) {
        Recipe recipeToUpdate = get(id);
        recipeToUpdate.setName(updatedRecipe.getName());
        recipeToUpdate.setUpdateDate(LocalDateTime.now());
        recipeDao.saveAndFlush(recipeToUpdate);
    }

    @Override
    public void delete(Long id) {
        recipeDao.deleteById(id);
    }
}
