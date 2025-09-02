package com.moneyhub.MoneyHub.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.moneyhub.MoneyHub.Enum.RepeatFrequency;
import com.moneyhub.MoneyHub.Enum.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
	private Long id;
    private TransactionType type;
    private String category;
    private RepeatFrequency frequency;
    private BigDecimal amount;
    private String notes;
    private Long userId;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
