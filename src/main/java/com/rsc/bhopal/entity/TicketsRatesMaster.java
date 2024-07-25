package com.rsc.bhopal.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "RCS_TS_TICKET_RATE_MASTER")
public class TicketsRatesMaster {	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TICKET_ID",referencedColumnName = "ID")
	private TicketDetails  ticketType;
	
	@ManyToOne
	@JoinColumn(name = "VISITOR_ID",referencedColumnName = "ID")
	private VisitorsType  visitorsType;
	
	@Column(name = "PRICE")
	private Float price;
	
	@Column(name = "REVISION_NO")
	private Integer revisionNo;
	
	@Column(name = "REVISED_AT")
	private Date revisedAt;

	@OneToOne
	@JoinColumn(name = "REVISED_BY",referencedColumnName = "ID")
    private User user;

	
	@Override
	public String toString(){
		return String.format("TicketsRatesMaster{ %s || %s || %d }", ticketType.getName(),visitorsType.getName(),Math.round(price));
	}
}
