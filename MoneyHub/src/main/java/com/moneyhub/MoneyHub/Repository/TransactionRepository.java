package com.moneyhub.MoneyHub.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneyhub.MoneyHub.Entity.Transaction;
import com.moneyhub.MoneyHub.Entity.User;
import com.moneyhub.MoneyHub.Enum.TransactionType;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long>{
	
	//Find all transactions by user
	public List<Transaction> findByUserOrderByDateCreatedDesc(User user);
	
	// Find by user and type
	public List<Transaction> findByUserAndTypeOrderByDateCreatedDesc(User user, TransactionType type);
	
	// Find transactions by type - INCOME or EXPENSE
	public List<Transaction> findByTypeOrderByDateCreatedDesc(TransactionType type);
	
	// Find transactions by user and category
	public List<Transaction> findByUserAndCategoryOrderByDateCreatedDesc(User user, String category);
	
	// Find transactions by category
	public List<Transaction> findByCategoryOrderByDateCreatedDesc(String category);
	
    
    // Find transactions by date range
    List<Transaction> findByUserAndDateCreatedBetweenOrderByDateCreatedDesc(User user, LocalDateTime startDate, LocalDateTime endDate);
	
	@Query(
		"SELECT COALESCE(SUM(t.amount), 0) "
		+ "FROM transaction t "
		+ "WHERE t.user = :user AND t.type = 'INCOME'"
			)
	public BigDecimal getTotalIncomeByUser(@Param("user") User user);
	
	@Query(
			"SELECT COALESCE(SUM(t.amount), 0) "
			+ "FROM transaction t "
			+ "WHERE t.user = :user AND t.type = 'EXPENSE'"
				)
		public BigDecimal getTotalExpenseByUser(@Param("user") User user);
}
