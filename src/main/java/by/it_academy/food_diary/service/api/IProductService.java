package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.models.Product;

import java.util.List;

public interface IProductService {
    void save(Product product);
    List<Product> getAll ();
    Product get(Long id);
    void update(Product updatedProduct, Long id);
    void delete (Long id);
}
