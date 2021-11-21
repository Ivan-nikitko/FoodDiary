package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.controller.dto.ProductDto;
import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.service.ProductService;
import by.it_academy.food_diary.service.api.IProductService;
import by.it_academy.food_diary.utils.TimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;


@RestController
@RequestMapping("/api/product")
public class ProductsController {

    private final ProductService productService;
    private final TimeUtil timeUtil;

    public ProductsController(ProductService productService, TimeUtil timeUtil) {
        this.productService = productService;
        this.timeUtil = timeUtil;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> index(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "2") int size,
                                               @RequestParam(required = false) String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<Product> products;
            if (name != null) {
                products = productService.getAll(name, pageable);
            } else {
                products = productService.getAll(pageable);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        try {
            Product product = productService.get(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ProductDto productDto) {
        productService.save(productDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody ProductDto productDto,
                                    @PathVariable("id") Long id,
                                    @PathVariable("dt_update") Long dtUpdate) {
        try {
            productDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
            productService.update(productDto, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/dt_update/{dt_update}")
    public ResponseEntity<?> delete(@RequestBody ProductDto productDto,
                                    @PathVariable("id") Long id,
                                    @PathVariable("dt_update") Long dtUpdate) {
        try {
            productDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
            productService.delete(productDto, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
