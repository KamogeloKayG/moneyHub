package com.moneyhub.MoneyHub.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moneyhub.MoneyHub.Entity.SavingPlan;
import com.moneyhub.MoneyHub.Entity.User;

@Repository
public interface SavingPlanRepository extends CrudRepository<SavingPlan, Long>{
	
	public List<SavingPlan> findByUserOrderByDateCreatedDesc(User user);
	public List<SavingPlan> findByUserOrderByTargetDateDesc(User user);

	
	
	
	

}
