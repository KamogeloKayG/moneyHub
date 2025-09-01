package com.moneyhub.MoneyHub.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneyhub.MoneyHub.Entity.User;
import com.moneyhub.MoneyHub.Repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRep;
	
	
	
	public User save(User user) {
		
		return userRep.save(user);
	}
	
	public Optional<User> findById(long id) {
		Optional<User> user = userRep.findById(id);
		return user;
	}
	
	public void DeleteById(long id) {
		userRep.deleteById(id);
	}
	
	public List<User> findAll(){
		return (List<User>) userRep.findAll();
	}
	
	public Optional<User> updateUser(Long id, User userDetails) {
	    return userRep.findById(id).map(user -> {
	        user.setFirstName(userDetails.getFirstName());
	        user.setLastName(userDetails.getLastName());
	        user.setEmail(userDetails.getEmail());
	        user.setPassword(userDetails.getPassword());
	        user.setRole(userDetails.getRole());
	        return userRep.save(user);
	    });
	}
	
}
