package by.it_academy.food_diary.service;

import by.it_academy.food_diary.dao.api.IComponentDao;
import by.it_academy.food_diary.dao.api.IRecipeDao;
import by.it_academy.food_diary.models.Component;
import by.it_academy.food_diary.models.Recipe;
import by.it_academy.food_diary.service.api.IRecipeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeService implements IRecipeService {
    private final IRecipeDao recipeDao;
    private final IComponentDao componentDao;

    public RecipeService(IRecipeDao recipeDao, IComponentDao componentDao) {
        this.recipeDao = recipeDao;
        this.componentDao = componentDao;
    }

    @Override
    public void save(Recipe recipe) {
        List<Component> components = recipe.getComponents();
        recipe.setCreationDate(LocalDateTime.now());
        recipe.setUpdateDate(recipe.getCreationDate());
        for (Component component : components) {
            component.setCreationDate(recipe.getCreationDate());
            component.setUpdateDate(component.getCreationDate());
            componentDao.save(component);
        }
        recipeDao.save(recipe);
    }

    @Override
    public Page<Recipe> getAll(Pageable pageable) {
        return recipeDao.findAll(pageable);
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
    public void delete(Recipe recipe,Long id) {
        recipeDao.deleteById(id);
    }
}
