package com.moneyhub.MoneyHub.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moneyhub.MoneyHub.Entity.User;
import com.moneyhub.MoneyHub.Service.UserService;

@RestController
@RequestMapping(path="/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		try {
			User saved = userService.save(user);
			return new ResponseEntity<>(saved, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id){
		try {
			Optional<User> user = userService.findById(id);
			if(user.isPresent()) {
				return new ResponseEntity<>(user.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateById(@PathVariable("id") long id, @RequestBody User user){
		try {
			Optional<User> savedUser = userService.findById(id);
			if(savedUser.isPresent()) {
				Optional<User> userSaved = userService.updateUser(id,user);
				return new ResponseEntity<>(userSaved.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
}
