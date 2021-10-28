package by.it_academy.foodDiary.controller;

import by.it_academy.foodDiary.models.Product;
import by.it_academy.foodDiary.service.ProductDAO;
import by.it_academy.foodDiary.service.api.IProductDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductsController {
    private final IProductDAO productDAO;

    public ProductsController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product) {
        return new ResponseEntity<>(productDAO.add(product), HttpStatus.CREATED);
    }
}
