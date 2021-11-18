package by.it_academy.food_diary.service.api;


import by.it_academy.food_diary.controller.dto.ProductDto;
import by.it_academy.food_diary.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService  {
    void save(ProductDto productDto);
    Page<Product> getAll (Pageable pageable);
    Product get(Long id);
    void update(ProductDto productDto, Long id);
    void delete (ProductDto productDto,Long id);
}
