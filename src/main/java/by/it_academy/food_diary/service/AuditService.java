package by.it_academy.food_diary.service;

import by.it_academy.food_diary.dao.api.IAuditDao;
import by.it_academy.food_diary.models.Audit;
import by.it_academy.food_diary.service.api.IAuditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    @Override
    public Page<Audit> getAll(Pageable pageable) {
        return auditDao.findAll(pageable);
    }

    @Override
    public Audit get(Long aLong) {
        return null;
    }

    @Override
    public void update(Audit item, Long aLong) {

    }

    @Override
    public void delete(Audit audit,Long aLong) {

    }
}
