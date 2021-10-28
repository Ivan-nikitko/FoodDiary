package by.it_academy.foodDiary.service;


import by.it_academy.foodDiary.models.Product;
import by.it_academy.foodDiary.service.api.IProductDAO;
import by.it_academy.foodDiary.storages.ProductsStorage;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductDAO implements IProductDAO {

    private final ProductsStorage productsStorage;

    public ProductDAO(ProductsStorage productsStorage) {
        this.productsStorage = productsStorage;
    }

    public void add (Product product){
        productsStorage.add(product);
    }

    public List<Product> getAll (){
        return productsStorage.getAll();
    }

    public Product getById (int id){
        return productsStorage.getById(id);
    }
}
