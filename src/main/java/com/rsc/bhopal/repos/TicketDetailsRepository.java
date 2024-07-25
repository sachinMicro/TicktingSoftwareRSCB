package com.rsc.bhopal.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.entity.TicketDetails;

@Repository
public interface TicketDetailsRepository extends JpaRepository<TicketDetails, Long> {

}
