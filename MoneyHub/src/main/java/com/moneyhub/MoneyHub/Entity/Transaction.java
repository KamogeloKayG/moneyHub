package com.moneyhub.MoneyHub.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


import com.moneyhub.MoneyHub.Enum.RepeatFrequency;
import com.moneyhub.MoneyHub.Enum.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name= "transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private TransactionType type;
	
	
	@Column(nullable=false)
	private String category;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private RepeatFrequency frequency;
	
	@Column(nullable=false)
	private String notes;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@Column(nullable=false)
	private BigDecimal amount;
	
	@Column(name="date_created", nullable=false)
	private LocalDateTime dateCreated;
	
	@Column(name="date_updated")
	private LocalDateTime dateUpdated;
	
	
    @PrePersist
    protected void onCreate() {
    	dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
    	dateUpdated = LocalDateTime.now();
    }
    
    public static class PredefinedCategories {
        public static final String PETROL = "Petrol";
        public static final String RENT = "Rent";
        public static final String GYM = "Gym";
        public static final String TRAVEL = "Travel";
        public static final String SALARY = "Salary";
        public static final String GROCERIES = "Groceries";
        public static final String ENTERTAINMENT = "Entertainment";
        public static final String SAVINGS = "Savings";
        public static final String TAXI = "Taxi";
        public static final String INTERNET = "Internet";
        
        public static String[] getAllCategories() {
            return new String[]{
                PETROL, RENT, GYM, TRAVEL, SALARY, 
                GROCERIES, ENTERTAINMENT, SAVINGS, TAXI, INTERNET
            };
        }
    }
	
}
