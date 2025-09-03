package com.moneyhub.MoneyHub.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.moneyhub.MoneyHub.Enum.ReminderFrequency;
import com.moneyhub.MoneyHub.Enum.RepeatFrequency;

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

@Entity(name="savingplan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingPlan {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(nullable = false, length = 100)
    private String title;
	
	@Column(name="goal_amount", nullable = false)
    private BigDecimal goalAmount;
	
    @Column(name = "target_date")
    private LocalDate targetDate;
    

    @Enumerated(EnumType.STRING)
    @Column(name = "saving_frequency")
    private RepeatFrequency savingFrequency;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "reminder_frequency")
    private ReminderFrequency reminderFrequency;
    
    @Column(name = "current_amount", nullable = false)
    private BigDecimal currentAmount = BigDecimal.ZERO;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime dateCreated;

    @Column(name = "updated_at")
    private LocalDateTime dateUpdated;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @PrePersist
    protected void onCreate() {
    	dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
    	dateUpdated = LocalDateTime.now();
    }
    
    public boolean isGoalReached() {
        return currentAmount.compareTo(goalAmount) >= 0;
    }
    
    public BigDecimal getRemainingAmount() {
        return goalAmount.subtract(currentAmount);
    }
    
}
