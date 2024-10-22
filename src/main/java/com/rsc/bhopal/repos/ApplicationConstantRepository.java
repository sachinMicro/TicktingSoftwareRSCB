package com.rsc.bhopal.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.entity.ApplicationConstant;
import com.rsc.bhopal.enums.ApplicationConstantType;

@Repository
public interface ApplicationConstantRepository extends JpaRepository<ApplicationConstant, Long> {
	@Query(name = "GET_CURRENT_TICKET_SERIAL",
		value = "SELECT serial FROM ApplicationConstant serial WHERE serial.type = ApplicationConstantType.TICKET_SERIAL",
		nativeQuery = false)
	public List<ApplicationConstant> getAllTicketsSerial();

	@Query(name = "GET_CURRENT_TICKET_PRINT_COORDINATES",
		value = "SELECT print FROM ApplicationConstant print WHERE print.type = ApplicationConstantType.TICKET_PRINT_COORDINATE",
		nativeQuery = false)
	public List<ApplicationConstant> getAllCurrentPrintCoordinates();
}
