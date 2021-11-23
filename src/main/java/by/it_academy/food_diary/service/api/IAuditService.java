package by.it_academy.food_diary.service.api;


import by.it_academy.food_diary.models.Audit;

import java.util.List;

public interface IAuditService  {
    void save(Audit audit);
    List<Audit> getAll();
    List<Audit> getAllByUserId(Long id);
    Audit get(Long id);
}
