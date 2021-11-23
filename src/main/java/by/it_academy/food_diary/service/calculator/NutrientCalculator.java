package by.it_academy.food_diary.service.calculator;

import by.it_academy.food_diary.controller.dto.JournalByDateDto;
import by.it_academy.food_diary.models.Ingredient;
import by.it_academy.food_diary.models.Journal;
import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.models.Recipe;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NutrientCalculator {

    public JournalByDateDto calculateNutrients(List<Journal> journals){
        List<Journal> journalList = new ArrayList<>();
        double sumOfProteins = 0;
        double sumOfFats = 0;
        double sumOfCarbonates = 0;
        double sumOfCalories = 0;
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
        JournalByDateDto journalByDateDto = new JournalByDateDto();
        journalByDateDto.setJournals(journalList);
        journalByDateDto.setSumOfProteins(sumOfProteins);
        journalByDateDto.setSumOfFats(sumOfFats);
        journalByDateDto.setSumOfCarbonates(sumOfCarbonates);
        journalByDateDto.setSumOfCalories(sumOfCalories);
        return journalByDateDto;

    }
}
