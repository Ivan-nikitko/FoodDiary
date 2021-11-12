package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.models.Recipe;
import by.it_academy.food_diary.service.ProductService;
import by.it_academy.food_diary.service.api.IProductService;
import by.it_academy.food_diary.service.api.IRecipeService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final IRecipeService recipeService;

    public RecipeController(IRecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<Page<Recipe>> index(@RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "2") int size,
                                              @RequestParam(required = false) String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<Recipe> recipes = recipeService.getAll(pageable);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        try {
            Recipe recipe = recipeService.get(id);
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Recipe recipe) {
        recipeService.save(recipe);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody Recipe recipe,
                                    @PathVariable("id") Long id,
                                    @PathVariable("dt_update") String dt_update) {
        try {
            recipe.setUpdateDate(LocalDateTime.parse(dt_update));
            recipeService.update(recipe, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}/dt_update/{dt_update}")
    public ResponseEntity<?> delete(@RequestBody Recipe recipe,
                                    @PathVariable("id") Long id,
                                    @PathVariable("dt_update") String dt_update) {
        try {
            recipe.setUpdateDate(LocalDateTime.parse(dt_update));
            recipeService.delete(recipe,id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
