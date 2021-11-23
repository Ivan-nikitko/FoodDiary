package by.it_academy.food_diary.service;

import by.it_academy.food_diary.controller.dto.JournalByDateDto;
import by.it_academy.food_diary.controller.dto.JournalDto;
import by.it_academy.food_diary.dao.api.IJournalDao;
import by.it_academy.food_diary.models.Ingredient;
import by.it_academy.food_diary.models.Journal;
import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.models.Recipe;
import by.it_academy.food_diary.service.api.IJournalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JournalService implements IJournalService {

    private final IJournalDao journalDao;

    public JournalService(IJournalDao journalDao) {
        this.journalDao = journalDao;
    }

    @Override
    public void save(JournalDto journalDto) {
        Journal journal = new Journal();
        journal.setProfile(journalDto.getProfile());
        journal.setProduct(journalDto.getProduct());
        journal.setRecipe(journalDto.getRecipe());
        journal.setMeasure(journalDto.getMeasure());
        journal.setMealTime(journalDto.getMealTime());
        Journal savedJournal = journalDao.save(journal);
        journalDto.setId(savedJournal.getId());
    }

    @Override
    public Page<Journal> getAll(Pageable pageable) {
        return journalDao.findAll(pageable);
    }

    @Override
    public Page<Journal> getAllByProfileId(Pageable pageable, Long profileId) {
        return journalDao.findByProfileId(profileId, pageable);
    }

    @Override
    public JournalByDateDto findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id) {
        JournalByDateDto journalByDateDto = new JournalByDateDto();
        List<Journal> journalList = new ArrayList<>();
        double sumOfProteins = 0;
        double sumOfFats = 0;
        double sumOfCarbonates = 0;
        double sumOfCalories = 0;
        List<Journal> journals = journalDao.findAllByCreationDateBetweenAndProfileId(start, end, id);
        for (Journal journal : journals) {
            journalList.add(journal);
            Double measure = journal.getMeasure();
            Recipe recipe = journal.getRecipe();
            Product product = journal.getProduct();
            if (product != null) {
                double productMeasure = product.getMeasure();
                double proteins = product.getProtein() * measure / productMeasure;
                double fats = product.getFats() * measure / productMeasure;
                double carbonates = product.getCarbonates() * measure / productMeasure;
                double calories = product.getCalories() * measure / productMeasure;
                sumOfProteins+=proteins;
                sumOfFats+=fats;
                sumOfCarbonates+=carbonates;
                sumOfCalories += calories;
            }
            if (recipe != null) {
                double recipeMeasure = 0;
                double recipeProteins=0;
                double recipeFats=0;
                double recipeCarbonates=0;
                double recipeCalories = 0;
                List<Ingredient> ingredients = recipe.getIngredients();
                for (Ingredient ingredient : ingredients) {
                    Product ingredientProduct = ingredient.getProduct();
                    double ingredientProductProtein = ingredientProduct.getProtein();
                    double ingredientProductFats = ingredientProduct.getFats();
                    double ingredientProductCarbonates = ingredientProduct.getCarbonates();
                    double ingredientProductCalories = ingredientProduct.getCalories();
                    double ingredientProductMeasure = ingredientProduct.getMeasure();
                    Double ingredientMeasure = ingredient.getMeasure();
                    recipeMeasure += ingredientMeasure;
                    recipeProteins += ingredientProductProtein * ingredientMeasure / ingredientProductMeasure;
                    recipeFats += ingredientProductFats * ingredientMeasure / ingredientProductMeasure;
                    recipeCarbonates += ingredientProductCarbonates * ingredientMeasure / ingredientProductMeasure;
                    recipeCalories += ingredientProductCalories * ingredientMeasure / ingredientProductMeasure;
                    double proteins = recipeProteins * measure / recipeMeasure;
                    double fats = recipeFats * measure / recipeMeasure;
                    double carbonates = recipeCarbonates * measure / recipeMeasure;
                    double calories = recipeCalories * measure / recipeMeasure;
                    sumOfProteins+=proteins;
                    sumOfFats+=fats;
                    sumOfCarbonates+=carbonates;
                    sumOfCalories += calories;
                }
            }
        }
        journalByDateDto.setJournals(journalList);
        journalByDateDto.setSumOfProteins(sumOfProteins);
        journalByDateDto.setSumOfFats(sumOfFats);
        journalByDateDto.setSumOfCarbonates(sumOfCarbonates);
        journalByDateDto.setSumOfCalories(sumOfCalories);
        return journalByDateDto;
    }

    @Override
    public List<Journal> findAllByProfile(Long id) {
        return journalDao.findAllByProfileId(id);
    }


    @Override
    public Journal get(Long id) {
        return journalDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Journal not found"));
    }

    @Override
    public void update(JournalDto journalDto, Long id) {
        Journal journalToUpdate = get(id);
        if (journalDto.getUpdateDate().isEqual(journalToUpdate.getUpdateDate())) {
            journalToUpdate.setProduct(journalDto.getProduct());
            journalToUpdate.setRecipe(journalDto.getRecipe());
            journalToUpdate.setMeasure(journalDto.getMeasure());
            journalToUpdate.setMealTime(journalDto.getMealTime());
            journalDao.saveAndFlush(journalToUpdate);
            journalDto.setId(id);
        } else {
            throw new OptimisticLockException("Journal has already been changed");
        }
    }

    @Override
    public void delete(JournalDto journalDto, Long id) {
        Journal dataBaseJournal = get(id);
        if (dataBaseJournal == null) {
            throw new IllegalArgumentException("Journal not found");
        }
        if (journalDto.getUpdateDate().isEqual(dataBaseJournal.getUpdateDate())) {
            journalDao.deleteById(id);
            journalDto.setId(id);
        } else {
            throw new OptimisticLockException("Journal has already been changed");
        }
    }


}
