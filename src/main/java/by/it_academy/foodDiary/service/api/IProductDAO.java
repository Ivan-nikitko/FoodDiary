package by.it_academy.foodDiary.service.api;

import by.it_academy.foodDiary.models.Product;

import java.util.List;

public interface IProductDAO {
    void add (Product product);
    List<Product> getAll ();
    Product getById (int id);
}
