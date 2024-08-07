package com.rsc.bhopal.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.entity.VisitorsType;
import com.rsc.bhopal.enums.VisitorsTypeEnum;

@Repository
public interface VisitorTypeRepository extends JpaRepository<VisitorsType, Long> {

	List<VisitorsType> findByType(VisitorsTypeEnum type);
	
}
