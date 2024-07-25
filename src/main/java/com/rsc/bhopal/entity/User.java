package com.rsc.bhopal.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "RCS_TS_USERS")
public class User {
  
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ADDED_AT")	
	private Date addedAt;
	
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;
	
	@OneToMany(mappedBy = "user")
	@Cascade(CascadeType.ALL)
	private List<UserRole> roles;
	
}
