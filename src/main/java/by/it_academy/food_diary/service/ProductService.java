package by.it_academy.food_diary.service;


import by.it_academy.food_diary.dao.api.IProductDao;
import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.service.api.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService implements IProductService {

    private final IProductDao productDAO;

    public ProductService(IProductDao productDAO) {
        this.productDAO = productDAO;
    }

    public void save(Product product) {
        productDAO.save(product);
    }

    public List<Product> getAll() {
        return productDAO.findAll();
    }

    public Product get(Long id) {
        return productDAO.findById(id).orElseThrow(
                        () -> new IllegalArgumentException("Person по id не найден")
                );
    }

    public void update(Product updatedProduct, Long id) {
        Product productToUpdate = get(id);
        productToUpdate.setName(updatedProduct.getName());
        productToUpdate.setBrand(updatedProduct.getBrand());
        productToUpdate.setCalories(updatedProduct.getCalories());
        productToUpdate.setProtein(updatedProduct.getProtein());
        productDAO.saveAndFlush(productToUpdate);
    }

    public void delete(Long id) {
        productDAO.deleteById(id);
    }

}
