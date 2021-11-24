package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.controller.dto.UserDto;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.service.UserService;
import by.it_academy.food_diary.utils.TimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;
	private final TimeUtil timeUtil;

	public UserController(UserService userService, TimeUtil timeUtil) {
		this.userService = userService;
		this.timeUtil = timeUtil;
	}

	@PutMapping("/{id}/dt_update/{dt_update}")
	public ResponseEntity<?> update(@RequestBody UserDto userDto,
									@PathVariable("id") Long id,
									@PathVariable("dt_update") Long dtUpdate){
		try {
			userDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
			userService.update(userDto, id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (OptimisticLockException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}


	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
									@RequestParam(value = "size", defaultValue = "5") int size){
		Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
		Page<User> users = userService.getAll(pageable);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}




}