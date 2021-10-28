package by.it_academy.food_diary.service;


import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.service.api.IProductDAO;
import by.it_academy.food_diary.storages.ProductsStorage;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductDAO implements IProductDAO {

    private final ProductsStorage productsStorage;

    public ProductDAO() {
        this.productsStorage = ProductsStorage.getInstance();
    }

    public int add (Product product){
        int id = productsStorage.add(product);
        return id;
    }

    public List<Product> getAll (){
        return productsStorage.getAll();
    }

    public Product getById (int id){
        return productsStorage.getById(id);
    }
}
