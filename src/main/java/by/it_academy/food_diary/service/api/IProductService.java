package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService extends Iservice<Product,Long> {
}
