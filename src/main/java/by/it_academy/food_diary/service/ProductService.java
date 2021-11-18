package by.it_academy.food_diary.service;


import by.it_academy.food_diary.controller.dto.ProductDto;
import by.it_academy.food_diary.dao.api.IProductDao;
import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IProductService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;


@Service
public class ProductService implements IProductService {

    private final IProductDao productDAO;
    private final UserHolder userHolder;

    public ProductService(IProductDao productDAO, UserHolder userHolder) {
        this.productDAO = productDAO;
        this.userHolder = userHolder;
    }


    public void save(ProductDto productDto) {
        Product product = new Product();
        product.setUserCreator(userHolder.getUser());
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setProtein(productDto.getProtein());
        product.setFats(productDto.getFats());
        product.setCarbonates(productDto.getCarbonates());
        product.setMeasure(productDto.getMeasure());
        product.setCalories(product.getCalories());
        productDAO.save(product);
    }

    public Page<Product> getAll(Pageable pageable) {
        return productDAO.findAll(pageable);
    }

    public Page<Product> getAll(String name,Pageable pageable) {
        return productDAO.findProductsByNameContains(name,pageable);
    }

    public Product get(Long id) {
        return productDAO.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product not found")
        );
    }

    public void update(ProductDto productDto, Long id) {
        Product productToUpdate = get(id);

        if (productDto.getUpdateDate().isEqual(productToUpdate.getUpdateDate())) {
            productToUpdate.setName(productDto.getName());
            productToUpdate.setBrand(productDto.getBrand());
            productToUpdate.setProtein(productDto.getProtein());
            productToUpdate.setFats(productDto.getFats());
            productToUpdate.setCarbonates((productDto.getCarbonates()));
            productToUpdate.setMeasure(productDto.getMeasure());
            productToUpdate.setCalories(productDto.getCalories());
            productDAO.saveAndFlush(productToUpdate);
        } else {
            throw new OptimisticLockException("Product has already been changed");
        }
    }

    public void delete(ProductDto productDto, Long id) throws EmptyResultDataAccessException, OptimisticLockException {
        Product dataBaseProduct = get(id);
        if (productDto.getUpdateDate().isEqual(dataBaseProduct.getUpdateDate())) {
            productDAO.deleteById(id);
        } else {
            throw new OptimisticLockException("Product has already been changed");
        }
    }

}
