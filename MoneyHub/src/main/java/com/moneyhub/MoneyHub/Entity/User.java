package com.moneyhub.MoneyHub.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="first_name",nullable = false, length=50)
	private String firstName;
	@Column(name="last_name", nullable = false, length=50)
	private String lastName;
	@Column(nullable = false, length=40)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String role;
	
	
	
	
}
