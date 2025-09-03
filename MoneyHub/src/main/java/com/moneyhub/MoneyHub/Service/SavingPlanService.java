package com.moneyhub.MoneyHub.Service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneyhub.MoneyHub.DTO.SavingPlanDTO;
import com.moneyhub.MoneyHub.Entity.SavingPlan;
import com.moneyhub.MoneyHub.Entity.User;
import com.moneyhub.MoneyHub.Repository.SavingPlanRepository;
import com.moneyhub.MoneyHub.Repository.UserRepository;

@Service
public class SavingPlanService {
	
	@Autowired
	private SavingPlanRepository saveRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	public SavingPlanDTO createSavingPlan(SavingPlanDTO dto, Long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		SavingPlan toSave = new SavingPlan();
		toSave.setCurrentAmount(dto.getCurrentAmount());
		toSave.setGoalAmount(dto.getGoalAmount());
		toSave.setIsActive(dto.getIsActive());
		toSave.setReminderFrequency(dto.getReminderFrequency());
		toSave.setSavingFrequency(dto.getSavingFrequency());
		toSave.setTargetDate(dto.getTargetDate());
		toSave.setTitle(dto.getTitle());
		toSave.setUser(user);
		
		SavingPlan saved = saveRepo.save(toSave);
		
		return convertToDTO(saved);
	}
	
	public SavingPlanDTO updateSavingPlan(Long id,SavingPlanDTO dto, Long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		SavingPlan saved = saveRepo.findById(id).
				orElseThrow(() -> new RuntimeException("Plan not found"));
		
		saved.setCurrentAmount(dto.getCurrentAmount());
	    saved.setGoalAmount(dto.getGoalAmount());
	    saved.setIsActive(dto.getIsActive());
	    saved.setReminderFrequency(dto.getReminderFrequency());
	    saved.setSavingFrequency(dto.getSavingFrequency());
	    saved.setTargetDate(dto.getTargetDate());
	    saved.setTitle(dto.getTitle());
	  

	    SavingPlan updated = saveRepo.save(saved);
	    return convertToDTO(updated);
	}
	
	public SavingPlanDTO findById(Long id) {
		SavingPlan plan = saveRepo.findById(id).
				orElseThrow(() -> new RuntimeException("Plan not found"));
		
		return convertToDTO(plan);
	}
	
	public List<SavingPlanDTO> findAll() {
		List<SavingPlan> plans = (List<SavingPlan>) saveRepo.findAll();
		
		
		return plans.stream()
				.map(this::convertToDTO)
				.toList();
	}
	
	public List<SavingPlanDTO> findByUserOrderBydateCreatedDesc(Long userId){
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		List<SavingPlan> plans = saveRepo.findByUserOrderByDateCreatedDesc(user);
		
		return plans.stream()
				.map(this::convertToDTO)
				.toList();
		
	}
	
	public void deleteSavingPlan(Long id, Long userId) {
	    User user = userRepo.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    SavingPlan saved = saveRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Plan not found"));

	    if (!saved.getUser().getId().equals(user.getId())) {
	        throw new RuntimeException("You are not allowed to delete this plan");
	    }

	    saveRepo.deleteById(id);
	}
	

	private SavingPlanDTO convertToDTO(SavingPlan savePlan) {
		SavingPlanDTO dto = new SavingPlanDTO();
		dto.setId(savePlan.getId());
		dto.setCurrentAmount(savePlan.getCurrentAmount());
		dto.setDateCreated(savePlan.getDateCreated());
		dto.setDateUpdated(savePlan.getDateUpdated());
		dto.setGoalAmount(savePlan.getGoalAmount());
		dto.setIsActive(savePlan.getIsActive());
		dto.setReminderFrequency(savePlan.getReminderFrequency());
		dto.setSavingFrequency(savePlan.getSavingFrequency());
		dto.setTargetDate(savePlan.getTargetDate());
		dto.setTitle(savePlan.getTitle());
		dto.setUserId(savePlan.getUser().getId());
		
		return dto;
	}
	
	public void addToGoal(Long id, BigDecimal amountToAdd, Long userId) {
		User user = userRepo.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    SavingPlan saved = saveRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Plan not found"));
	    
	    if(!saved.getUser().getId().equals(user.getId())) {
	    	throw new RuntimeException("You are not allowed to add to goal");
	    }
	    
	    BigDecimal newAmount = saved.getCurrentAmount().add(amountToAdd);
	    
	    if(newAmount.compareTo(saved.getGoalAmount()) < 0) {
	    	saved.setCurrentAmount(newAmount);
	    }else{
	    	saved.setCurrentAmount(saved.getGoalAmount());
	    }
	    
	    
	    saveRepo.save(saved);
	}
	
	
}
