package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Iservice <T,ID> {
    void save(T item);
    Page<T> getAll (Pageable pageable);
    T get(ID id);
    void update(T item, ID id);
    void delete (T item,ID id);
}
