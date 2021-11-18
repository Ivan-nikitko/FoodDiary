package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.controller.dto.RecipeDto;
import by.it_academy.food_diary.models.Recipe;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IRecipeService;
import by.it_academy.food_diary.utils.TimeUtil;
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
    private final UserHolder userHolder;
    private final TimeUtil timeUtil;

    public RecipeController(IRecipeService recipeService, UserHolder userHolder, TimeUtil timeUtil) {
        this.recipeService = recipeService;
        this.userHolder = userHolder;
        this.timeUtil = timeUtil;
    }

    @GetMapping
    public ResponseEntity<Page<Recipe>> index(@RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "2") int size,
                                              @RequestParam(required = false) String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<Recipe> recipes;
        if (name != null) {
            recipes = recipeService.getAll(name, pageable);
        } else {
            recipes = recipeService.getAll(pageable);
        }
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        try {
            Recipe recipe = recipeService.get(id);
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody RecipeDto recipeDto) {
        recipeService.save(recipeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody RecipeDto recipeDto,
                                    @PathVariable("id") Long id,
                                    @PathVariable("dt_update") Long dtUpdate) {
        try {
            recipeDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
            recipeService.update(recipeDto, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}/dt_update/{dt_update}")
    public ResponseEntity<?> delete(@RequestBody RecipeDto recipeDto,
                                    @PathVariable("id") Long id,
                                    @PathVariable("dt_update") Long dtUpdate) {
        try {
            recipeDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
            recipeService.delete(recipeDto, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
