package by.it_academy.foodDiary.storages;

import by.it_academy.foodDiary.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsStorage {

    private static ProductsStorage instance;
    private static ArrayList<Product> products;

    private ProductsStorage() {
    }

    public List<Product> getAll() {
        return products;
    }

    public void add (Product product){
        products.add(product);
    }

    public Product getById (int id){
        return products.get(id);
    }

    public static ProductsStorage getInstance() {
        return instance;
    }
}
