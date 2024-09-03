package com.rsc.bhopal.repos;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.entity.GeneratedTicket;

@Repository
public interface GeneratedTicketRepository extends JpaRepository<GeneratedTicket, Long>,PagingAndSortingRepository<GeneratedTicket, Long> {
	
    //repository.findWithPageable(new PageRequest(0, 10, Direction.DESC, "id"));
	@Query(value="from GeneratedTicket")
	List<GeneratedTicket> recentRecords(Pageable pageable);
	
}
