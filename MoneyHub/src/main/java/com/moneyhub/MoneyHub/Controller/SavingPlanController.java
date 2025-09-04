package com.moneyhub.MoneyHub.Controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moneyhub.MoneyHub.DTO.SavingPlanDTO;
import com.moneyhub.MoneyHub.Service.SavingPlanService;

@RestController
@RequestMapping("api/savingplans")
@CrossOrigin(origins = "*")
public class SavingPlanController {
	
	@Autowired
	private SavingPlanService planService;
	
	@GetMapping("/{id}")
	public ResponseEntity<SavingPlanDTO> getSavingPlanById(@PathVariable("id") Long id){
		try {
			return new ResponseEntity<>(planService.findById(id), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("user/{userId}")
	public ResponseEntity<SavingPlanDTO> createSavingPlan(@RequestBody SavingPlanDTO savingplanDTO, @PathVariable("userId") Long userId){
		try {
			SavingPlanDTO savedPlan = planService.createSavingPlan(savingplanDTO, userId);
			return new ResponseEntity<>(savedPlan, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}/user/{userId}")
	public ResponseEntity<SavingPlanDTO> updateSavingPlan(@RequestBody SavingPlanDTO toUpdate, @PathVariable("id") Long id, @PathVariable("userId") Long userId){
		try {
			SavingPlanDTO updatedPlan = planService.updateSavingPlan(id, toUpdate, userId);
			return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/admin")
	public ResponseEntity<List<SavingPlanDTO>> getAllPlans(){
		try {
			return new ResponseEntity<>(planService.findAll(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<SavingPlanDTO>> getAllPlansByUser(@PathVariable("userId") Long userId){
		try {
			return new ResponseEntity<>(planService.findByUserOrderBydateCreatedDesc(userId), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("/{id}/user/{userId}")
	public ResponseEntity<Void> deleteSavingPlan(@PathVariable("id") Long id, @PathVariable("userId") Long userId){
		try {
			planService.deleteSavingPlan(id, userId);
			return ResponseEntity.noContent().build();
		}catch(RuntimeException e) {
			if (e.getMessage().equals("Saving Plan not found")) {
                return ResponseEntity.notFound().build();
            } else if (e.getMessage().equals("Access Denied")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}/user/{userId}/addtoGoal")
	public ResponseEntity<Void> addToGoal(@PathVariable("id") Long id, @PathVariable("userId") Long userId, @RequestParam BigDecimal amountToAdd){
		try {
			planService.addToGoal(id, amountToAdd, userId);
			return ResponseEntity.noContent().build();
		}catch(RuntimeException e) {
			if (e.getMessage().equals("Plan not found")) {
                return ResponseEntity.notFound().build();
            } else if (e.getMessage().equals("Access Denied")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            return ResponseEntity.badRequest().build();
		}
	}
	
	
	
	
	
	
}
