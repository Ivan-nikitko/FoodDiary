package by.it_academy.food_diary.storages;

import by.it_academy.food_diary.models.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductsStorage {

    private static ArrayList<Product> products;

    public ProductsStorage() {
        products = new ArrayList<Product>();
    }

    public List<Product> getAll() {
        return products;
    }

    public int add(Product product) {
        products.add(product);
        return products.size();
    }

    public Product getById(int id) {
        return products.get(id);
    }

    public Boolean delete(int id) {
        try {
            products.remove(id);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
