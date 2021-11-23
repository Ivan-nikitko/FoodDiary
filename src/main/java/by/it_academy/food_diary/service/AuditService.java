package by.it_academy.food_diary.service;

import by.it_academy.food_diary.dao.api.IAuditDao;
import by.it_academy.food_diary.models.Audit;
import by.it_academy.food_diary.service.api.IAuditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService implements IAuditService {
    private IAuditDao auditDao;

    public AuditService(IAuditDao auditDao) {
        this.auditDao = auditDao;
    }

    @Override
    public void save(Audit audit) {
        auditDao.save(audit);
    }

    public List<Audit> getAll() {
        return auditDao.findAll();
    }


    public List<Audit> getAllByUserId(Long id) {
        return auditDao.findAllByUserId(id) ;
    }

    @Override
    public Audit get(Long id){
        return auditDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Audit not found"));
    }


}
