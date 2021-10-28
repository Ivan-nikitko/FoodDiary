package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.service.ProductDAO;
import by.it_academy.food_diary.service.api.IProductDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductsController {
    private final IProductDAO productDAO;

    public ProductsController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping
    public String show (){
        return "Hello";
    }


    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody Product product) {
        return new ResponseEntity<>(productDAO.add(product), HttpStatus.CREATED);
    }
}
