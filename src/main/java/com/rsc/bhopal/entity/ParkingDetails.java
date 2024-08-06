package com.rsc.bhopal.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "RCS_TS_PARKING_DETAILS")
public class ParkingDetails {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ADDED_AT")	
	private Date addedAt;
	
	@Column(name="IS_ACTIVE")
	private Boolean isActive;
	
	@OneToOne(fetch = FetchType.EAGER,mappedBy = "parkingDetails")
	private TicketsRatesMaster rateMaster;
}
