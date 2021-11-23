package by.it_academy.food_diary.controller;


import by.it_academy.food_diary.models.Audit;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.models.api.ERole;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IAuditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/audits")
public class AuditController {
    private final IAuditService auditService;
    private final UserHolder userHolder;


    public AuditController(IAuditService auditService, UserHolder userHolder) {
        this.auditService = auditService;
        this.userHolder = userHolder;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        User user = userHolder.getUser();
        List<Audit> audits;
        if (user.getRole().equals(ERole.ROLE_ADMIN)) {
            audits = this.auditService.getAll();
        } else {
            audits = auditService.getAllByUserId(user.getId());
        }
        return new ResponseEntity<>(audits, HttpStatus.OK);
    }
}