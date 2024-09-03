package com.rsc.bhopal.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.entity.BillSummary;

@Repository
public interface BillSummaryRepository extends JpaRepository<BillSummary, Long> {

}
