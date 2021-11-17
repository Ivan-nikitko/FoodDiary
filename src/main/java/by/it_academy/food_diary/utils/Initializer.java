package by.it_academy.food_diary.utils;

import by.it_academy.food_diary.dao.api.IIngredientDao;
import by.it_academy.food_diary.dao.api.IProductDao;
import by.it_academy.food_diary.dao.api.IRecipeDao;
import by.it_academy.food_diary.dao.api.IUserDao;
import by.it_academy.food_diary.models.Ingredient;
import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.models.Recipe;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.models.api.ERole;
import by.it_academy.food_diary.models.api.EStatus;
import by.it_academy.food_diary.service.api.IProductService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class Initializer {
    private final IProductDao productDao;
    private final IUserDao userDao;
    private final IRecipeDao recipeDao;
    private final IIngredientDao ingredientDao;
    private final PasswordEncoder passwordEncoder;

    public Initializer(IProductService productService,
                       IProductDao productDao,
                       IUserDao userDao,
                       IRecipeDao recipeDao,
                       IIngredientDao ingredientDao, PasswordEncoder passwordEncoder) {
        this.productDao = productDao;
        this.userDao = userDao;
        this.recipeDao = recipeDao;
        this.ingredientDao = ingredientDao;
        this.passwordEncoder = passwordEncoder;
        generateAdmin();
        generateProducts();
        generateRecipes();
    }


    public void generateAdmin() {
        User user = new User();
        user.setName("Администратор Администратович");
        user.setLogin("admin@mail.ru");
        user.setPassword(passwordEncoder.encode("111"));
        user.setRole(ERole.ROLE_ADMIN);
        user.setStatus(EStatus.ACTIVE);
        user.setCreationDate(LocalDateTime.now());
        user.setUpdateDate(user.getCreationDate());
        userDao.save(user);
    }

    public void generateProducts() {

        InputStream inputStream;
        HSSFWorkbook workBook = null;
        try {
            inputStream = new FileInputStream("src/main/java/by/it_academy/food_diary/res/Calorie_table.xls");
            workBook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //разбираем первый лист входного файла на объектную модель
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        //проходим по всему листу
        while (it.hasNext()) {
            Product product = new Product();
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();

            String productName = cells.next().getStringCellValue();
            double proteins = cells.next().getNumericCellValue();
            double fats = cells.next().getNumericCellValue();
            double carbonates = cells.next().getNumericCellValue();
            double calories = cells.next().getNumericCellValue();
            product.setName(productName);
            product.setProtein(proteins);
            product.setFats(fats);
            product.setCarbonates(carbonates);
            product.setCalories(calories);
            product.setMeasure(100);
            product.setUserCreator(userDao.getById(1L));
            productDao.save(product);
        }
    }

    private void generateRecipes() {
        for (int i = 0; i < 50; i++) {
            Recipe recipe = new Recipe();
            StringBuilder recipeName = new StringBuilder();
            List<Ingredient> ingredients = new ArrayList<>();
            for (int j = 0; j < getRandom(2, 5); j++) {
                Ingredient ingredient = generateIngredient();
                String productName = ingredient.getProduct().getName();
                ingredients.add(ingredient);
                recipe.setIngredients(ingredients);
                recipeName.append(productName).append(" ");
            }
            recipe.setName(recipeName.toString());
            recipe.setCreationDate(LocalDateTime.now());
            recipe.setUpdateDate(recipe.getCreationDate());
            recipeDao.save(recipe);
        }
    }

    private Ingredient generateIngredient() {
        int productCount = productDao.findAll().size();
        Ingredient ingredient = new Ingredient();
        Product product = productDao.findById((long) getRandom(1, productCount - 1)).get();
        ingredient.setProduct(product);
        ingredient.setMeasure((double) getRandom(1, 100));
        ingredient.setCreationDate(LocalDateTime.now());
        ingredient.setUpdateDate(ingredient.getCreationDate());
        ingredientDao.save(ingredient);
        return ingredient;
    }

    private int getRandom(int a, int b) {
        return a + (int) (Math.random() * b);
    }
}




