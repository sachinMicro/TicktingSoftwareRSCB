package com.rsc.bhopal.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.entity.TicketsRatesMaster;

@Repository
public interface TicketsRatesMasterRepository extends JpaRepository<TicketsRatesMaster, Long> {

	@Query(name="GET_TICKET_RATE_BY_GROUP_AND_TICKET",
			value = "select rate from  TicketsRatesMaster rate where rate.ticketType.id =:ticketid and rate.visitorsType.id=:groupId")
	TicketsRatesMaster findByGroupAndTicketIds(long ticketid,long groupId);
	
	@Query(name="GET_TICKET_RATE_BY_GROUP",
			value = "select rate.ticketType.id  from  TicketsRatesMaster rate where rate.visitorsType.id=:groupId")
	List<Long> getTicketsByGroup(long groupId);
	
	
}

