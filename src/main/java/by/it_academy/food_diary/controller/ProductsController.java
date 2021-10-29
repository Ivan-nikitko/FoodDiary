package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.service.ProductDAO;
import by.it_academy.food_diary.service.api.IProductDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final IProductDAO productDAO;

    public ProductsController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping
    public ResponseEntity<List<Product>> index() {
        List<Product> products = productDAO.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> show(@PathVariable("id") int id) {
        Product product = productDAO.getById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> create(@RequestBody Product product) {
        return new ResponseEntity<>(productDAO.add(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Product> update(@RequestBody Product product, @PathVariable("id") int id) {
        Product updatedProduct = productDAO.update(product, id);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") int id) {
        return new ResponseEntity<>(productDAO.delete(id), HttpStatus.OK);
    }
}
