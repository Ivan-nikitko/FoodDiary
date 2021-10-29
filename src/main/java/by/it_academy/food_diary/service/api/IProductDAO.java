package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.models.Product;

import java.util.List;

public interface IProductDAO {
    int add (Product product);
    List<Product> getAll ();
    Product getById (int id);
    Product update(Product updatedProduct, int id);
    Boolean delete (int id);
}
