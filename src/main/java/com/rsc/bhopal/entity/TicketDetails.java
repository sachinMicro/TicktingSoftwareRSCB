package com.rsc.bhopal.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "RCS_TS_TICKET_MASTER")
public class TicketDetails {
 
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "TICKET_NAME")
	private String name;

	
	@OneToMany(mappedBy = "ticketType",fetch = FetchType.LAZY)
	@JsonIgnore
	@Transient
	private List<TicketsRatesMaster> rate;
	

	public TicketDetails(String name){
		this.name=name;
	}
	
}
