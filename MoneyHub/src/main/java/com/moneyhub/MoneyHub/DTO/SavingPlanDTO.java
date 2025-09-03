package com.moneyhub.MoneyHub.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.moneyhub.MoneyHub.Entity.User;
import com.moneyhub.MoneyHub.Enum.ReminderFrequency;
import com.moneyhub.MoneyHub.Enum.RepeatFrequency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavingPlanDTO {
	private Long id;
	private String title;
	private BigDecimal goalAmount;
	private LocalDate targetDate;
	private RepeatFrequency savingFrequency;
	private ReminderFrequency reminderFrequency;
	private BigDecimal currentAmount;
	private Boolean isActive;
	private LocalDateTime dateCreated;
	private LocalDateTime dateUpdated;
	private Long userId;

	
}
