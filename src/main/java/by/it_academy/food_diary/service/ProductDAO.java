package by.it_academy.food_diary.service;


import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.service.api.IProductDAO;
import by.it_academy.food_diary.storages.ProductsStorage;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductDAO implements IProductDAO {

    private final ProductsStorage productsStorage;

    public ProductDAO(ProductsStorage productsStorage) {
        this.productsStorage = productsStorage;
    }

    public int add(Product product) {
        int id = productsStorage.add(product);
        return id;
    }

    public List<Product> getAll() {
        return productsStorage.getAll();
    }

    public Product getById(int id) {
        return productsStorage.getById(id);
    }

    public Product update(Product updatedProduct, int id) {
        Product productToUpdate = productsStorage.getById(id);
        productToUpdate.setName(updatedProduct.getName());
        productToUpdate.setBrand(updatedProduct.getBrand());
        productToUpdate.setCalories(updatedProduct.getCalories());
        productToUpdate.setProtein(updatedProduct.getProtein());
        return productToUpdate;
    }

    @Override
    public Boolean delete(int id) {
       return productsStorage.delete(id);
    }

}
