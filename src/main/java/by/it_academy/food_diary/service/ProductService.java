package by.it_academy.food_diary.service;


import by.it_academy.food_diary.dao.api.IProductDao;
import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.service.api.IProductService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
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



    public  Page<Product> getAll(Pageable pageable) {
        return productDAO.findAll(pageable);
    }

    public Product get(Long id) {
        return productDAO.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product not found")
        );
    }

    public void update(Product updatedProduct, Long id) {
        Product productToUpdate = get(id);
        String updatedProductUpdateDate = updatedProduct.getUpdateDate();
        String productToUpdateUpdateDate = productToUpdate.getUpdateDate();
        System.out.println(LocalDateTime.parse(productToUpdateUpdateDate));
        if (!updatedProductUpdateDate.equals(productToUpdateUpdateDate)){
            throw new OptimisticLockException("Product has already been changed");
        }else {
     /*   LocalDateTime updateDate = updatedProduct.getUpdateDate();
        LocalDateTime productToUpdateUpdateDate = productToUpdate.getUpdateDate();
        if(updateDate!=productToUpdateUpdateDate){
          throw new OptimisticLockException("Product has already been changed");
        }*/
            productToUpdate.setName(updatedProduct.getName());
            productToUpdate.setBrand(updatedProduct.getBrand());
            productToUpdate.setCalories(updatedProduct.getCalories());
            productToUpdate.setProtein(updatedProduct.getProtein());
            productToUpdate.setFats(updatedProduct.getFats());
            productToUpdate.setCarbonates((updatedProduct.getCarbonates()));
            productToUpdate.setMeasure(updatedProduct.getMeasure());
            productToUpdate.setUpdateDate(LocalDateTime.now().toString());
            productDAO.saveAndFlush(productToUpdate);
        }
    }

    public void delete(Long id) throws EmptyResultDataAccessException {
        productDAO.deleteById(id);
    }

}
