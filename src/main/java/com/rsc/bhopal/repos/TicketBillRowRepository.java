package com.rsc.bhopal.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.entity.TicketBillRow;

@Repository
public interface TicketBillRowRepository extends JpaRepository<TicketBillRow, Long> {

}
