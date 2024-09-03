package com.rsc.bhopal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "RCS_TS_TICKET_BILL_SUMMARY")
public class BillSummary {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "PERSONS")
	private int persons;
	
	@Column(name = "SUM")
	private Float price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RATE_MASTER_ID",referencedColumnName = "ID")
	private TicketsRatesMaster  rate;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_ID",referencedColumnName = "ID")
	private GeneratedTicket generatedTicket;
	
}
