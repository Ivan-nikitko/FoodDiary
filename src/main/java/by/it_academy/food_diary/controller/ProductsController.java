package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.service.ProductService;
import by.it_academy.food_diary.service.api.IProductService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/product")
public class ProductsController {

    private final IProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> index(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "2") int size,
                                               @RequestParam(required = false) String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<Product> products = productService.getAll(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        try {
            Product product = productService.get(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Product product) {
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody Product product,
                                    @PathVariable("id") Long id,
                                    @PathVariable("dt_update") String dtUpdate) {
        try {
            product.setUpdateDate(LocalDateTime.parse(dtUpdate));
            productService.update(product, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}/dt_update/{dt_update}")
    public ResponseEntity<?> delete(@RequestBody Product product,
                                    @PathVariable("id") Long id,
                                    @PathVariable("dt_update") String dt_update) {
        try {
            product.setUpdateDate(LocalDateTime.parse(dt_update));
            productService.delete(product,id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
