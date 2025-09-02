package com.moneyhub.MoneyHub.Controller;

import java.util.List;

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

import com.moneyhub.MoneyHub.DTO.TransactionDTO;
import com.moneyhub.MoneyHub.Enum.TransactionType;
import com.moneyhub.MoneyHub.Service.TransactionService;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {
	
	@Autowired
	TransactionService tranServ;
	
	@GetMapping
	public ResponseEntity<List<TransactionDTO>> getAllTransactions(){
		try {
			List<TransactionDTO> transactions = tranServ.findAll();
			return new ResponseEntity<>(transactions, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<TransactionDTO>> getAllTransactionsByUser(@PathVariable("userId") Long userId){
		try {
			List<TransactionDTO> transactions = tranServ.findAllByUser(userId);
			return new ResponseEntity<>(transactions, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
	}
	
	@PutMapping("/{id}/user/{userId}")
	public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable("id") Long id, TransactionDTO transaction,@PathVariable("userId") Long userId){
		try {
			TransactionDTO updated = tranServ.updateTransaction(id, transaction, userId);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/user/{userId}/type/{type}")
	public ResponseEntity<List<TransactionDTO>> getAllByUserAndType(@PathVariable("userId") Long userId,@PathVariable("type") TransactionType type){
		try {
			List<TransactionDTO> transactions = tranServ.findByUserAndType(userId, type);
			return new ResponseEntity<>(transactions, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping("/{userId}")
	public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transaction, @PathVariable("userId") Long userId) {
		try {
			TransactionDTO savedTransaction = tranServ.createTransaction(transaction, userId);
			return new ResponseEntity<>(transaction, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
}
