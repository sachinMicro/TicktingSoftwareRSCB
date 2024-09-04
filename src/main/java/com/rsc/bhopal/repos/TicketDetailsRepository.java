package com.rsc.bhopal.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.entity.TicketDetails;
import com.rsc.bhopal.entity.VisitorsType;

@Repository
public interface TicketDetailsRepository extends JpaRepository<TicketDetails, Long> {
	List<TicketDetails> findByIsActive(Boolean isActive);
}
