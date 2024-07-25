package com.rsc.bhopal.entity;

import com.rsc.bhopal.enums.UserRoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "RCS_TS_USER_ROLES")
public class UserRole {
	  
		@Id
		@Column(name = "ID")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
				
		@Column(name = "ROLE")
		@Enumerated(EnumType.STRING)
		private UserRoleEnum roles;
	
		@ManyToOne
		@JoinColumn(name = "USER_ID",referencedColumnName = "ID")
        private User user;
		
}
