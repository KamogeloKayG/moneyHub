package com.moneyhub.MoneyHub.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneyhub.MoneyHub.DTO.TransactionDTO;
import com.moneyhub.MoneyHub.Entity.Transaction;
import com.moneyhub.MoneyHub.Entity.User;
import com.moneyhub.MoneyHub.Enum.TransactionType;
import com.moneyhub.MoneyHub.Repository.TransactionRepository;
import com.moneyhub.MoneyHub.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TransactionService {
	
	@Autowired
	TransactionRepository transRepo;
	
	@Autowired
	UserRepository userRepo;
	
	public TransactionDTO findById(Long id){
		Transaction transaction = transRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Transaction not found"));
		
		return convertToDto(transaction);
	}
	
	public List<TransactionDTO> findAll(){
		List<Transaction> transactions = (List<Transaction>) transRepo.findAll();
		return transactions.stream()
				.map(this::convertToDto)
				.toList();
	}
	
	public List<TransactionDTO> findAllByUser(Long userid){
		User user = userRepo.findById(userid)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		List<Transaction> transactions = transRepo.findByUserOrderByDateCreatedDesc(user);
			return transactions.stream()
					.map(this::convertToDto)
					.toList();
	}
	
	public TransactionDTO createTransaction(TransactionDTO transactiondto, Long userId) {
		User user = userRepo.findById(userId).
				orElseThrow(() -> new RuntimeException("User not found"));
		
		Transaction toSave = new Transaction();
		toSave.setType(transactiondto.getType());
		toSave.setCategory(transactiondto.getCategory());
		toSave.setFrequency(transactiondto.getFrequency());
		toSave.setAmount(transactiondto.getAmount());
		toSave.setNotes(transactiondto.getNotes());
		toSave.setUser(user);
		
		Transaction saved = transRepo.save(toSave);
		
		return convertToDto(saved);
		
	}
	
	public TransactionDTO updateTransaction(Long id, TransactionDTO transactiondto, Long userId) {
		Transaction transaction = transRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("Transaction not found"));
		
		if(transaction.getUser().getId().equals(userId)) {
			transaction.setType(transactiondto.getType());
			transaction.setCategory(transactiondto.getCategory());
			transaction.setFrequency(transactiondto.getFrequency());
			transaction.setAmount(transactiondto.getAmount());
			transaction.setNotes(transactiondto.getNotes());
		}else {
			throw new RuntimeException("Access Denied");
		}
		Transaction updated = transRepo.save(transaction);
		
		return convertToDto(updated);
		
	}
	
	public List<TransactionDTO> findByUserAndType(Long userId, TransactionType type){
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		List<Transaction> transactions = transRepo.findByUserAndTypeOrderByDateCreatedDesc(user, type);
		
		return transactions.stream()
				.map(this::convertToDto)
				.toList();
	}
	
	public BigDecimal getTotalIncomeByUser(Long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		return transRepo.getTotalIncomeByUser(user);
	}
	
	public BigDecimal getTotalExpenseByUser(Long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		return transRepo.getTotalExpenseByUser(user);
	}
	
	public List<TransactionDTO> findByUserAndDateBetweenOrderByDateDesc(Long userId, LocalDateTime startDate, LocalDateTime endDate){
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		List<Transaction> transactions = transRepo.findByUserAndDateCreatedBetweenOrderByDateCreatedDesc(user, startDate, endDate);
		
		return transactions.stream()
				.map(this::convertToDto)
				.toList();
	}
	
	
	private TransactionDTO convertToDto(Transaction entity) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setCategory(entity.getCategory());
        dto.setFrequency(entity.getFrequency());
        dto.setAmount(entity.getAmount());
        dto.setNotes(entity.getNotes());
        dto.setUserId(entity.getUser().getId());
        dto.setDateCreated(entity.getDateCreated());
        dto.setDateUpdated(entity.getDateUpdated());
        return dto;
    }
	
	
	
	
}
