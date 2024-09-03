package com.rsc.bhopal.repos;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.entity.TicketBill;

@Repository
public interface TicketBillRepository extends JpaRepository<TicketBill, Long>,PagingAndSortingRepository<TicketBill, Long> {
	
    //repository.findWithPageable(new PageRequest(0, 10, Direction.DESC, "id"));
	@Query(value="from TicketBill")
	List<TicketBill> recentRecords(Pageable pageable);
	
}
