package com.rsc.bhopal.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "RCS_TS_TICKET_BILL")
public class TicketBill {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	
	@Column(name = "GENERATED_AT")
	private Date generatedAt;
	
	@Column(name = "TOTAL_BILL")
	private Double price;
	
	@Column(name = "PERSONS")
	private int persons;
	
	@Column(name = "TICKET_PAYLOAD")
	private String ticketPayload;
		
	@ManyToOne
	@JoinColumn(name = "GENERATED_BY",referencedColumnName = "ID")
    private RSCUser generatedBy;

	@JsonIgnore
	@OneToMany(mappedBy = "generatedTicket",fetch = FetchType.LAZY)	
	@Cascade(CascadeType.ALL)
    List<TicketBillRow> billSummary;

	@Override
	public String toString() {
		return "GeneratedTicket [id=" + id + ", generatedAt=" + generatedAt + ", price=" + price + ", generatedBy="
				+ generatedBy + "]";
	}
	
}
