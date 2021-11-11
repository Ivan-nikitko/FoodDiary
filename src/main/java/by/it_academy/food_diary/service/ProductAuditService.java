package by.it_academy.food_diary.service;

import by.it_academy.food_diary.models.Audit;
import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IAuditService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class ProductAuditService {

    private final IAuditService auditService;
    private final UserHolder userHolder;

    public ProductAuditService(IAuditService auditService, UserHolder userHolder) {
        this.auditService = auditService;
        this.userHolder = userHolder;
    }

    @After("execution(* by.it_academy.food_diary.service.ProductService.save(..))")
    public void save(JoinPoint joinPoint){
        try {
            Object[] args = joinPoint.getArgs();

            Product arg = (Product) args[0];

            Audit audit = new Audit();
            audit.setCreationDate(arg.getUpdateDate());
            audit.setDescription("Creation product  " + arg.getId());
          //  audit.setUser(userHolder.getAuthentication().); TODO How to get user from getAuthentication()
            audit.setEssenceName("Product");
            audit.setEssenceId(arg.getId());
            auditService.save(audit);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
