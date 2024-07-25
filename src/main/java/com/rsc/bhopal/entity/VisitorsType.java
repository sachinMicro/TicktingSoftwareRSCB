package com.rsc.bhopal.entity;

import java.util.List;

import com.rsc.bhopal.enums.VisitorsTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "RCS_TS_VISITOR_TYPE_MASTER")
public class VisitorsType {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(name = "VISITOR_NAME")
	private String name; 
	
	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	private VisitorsTypeEnum type; 
	
	@Column(name = "MIN_MEMBERS")
	private Integer minMembers; 
	
	@Column(name = "FIXED_MEMBERS")
    private Integer fixedMembers;
    
	@OneToMany(mappedBy = "visitorsType")
	@Transient
	private List<TicketsRatesMaster> rate;

	public VisitorsType(String name,VisitorsTypeEnum type,int minMembers,int fixedMembers ){
		 this.name=name;
		 this.type=type;
		 this.minMembers=minMembers;
		 this.fixedMembers=fixedMembers;	 
	}
}
