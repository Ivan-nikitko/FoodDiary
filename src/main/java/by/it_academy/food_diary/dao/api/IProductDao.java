package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductDao extends JpaRepository <Product,Long>{
    Page <Product> findProductsByNameContains(String name, Pageable pageable);
}
