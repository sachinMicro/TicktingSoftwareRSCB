package com.rsc.bhopal.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rsc.bhopal.entity.TicketBillRow;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TicketBillRowRepository extends JpaRepository<TicketBillRow, Long> {
	/*
	@Query(value = "SELECT\n" +
		"	bill\n" +
		"FROM\n" +
		"	TicketBillRow bill\n" +
		"LEFT JOIN\n" +
		"	bill.generatedTicket\n" +
		"LEFT JOIN\n" +
		"	bill.rate",
		nativeQuery = false)
	*/
	@Query(value = "SELECT bill FROM TicketBillRow bill WHERE bill.rate.billType = \'TICKET\' AND bill.generatedTicket.generatedAt BETWEEN :startDateTime AND :endDateTime", nativeQuery = false)
	public List<TicketBillRow> getTicketBillRowsAtDateTime(Timestamp startDateTime, Timestamp endDateTime);
	/*
	@Query(value = "SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	rsc_ts.rsc_ts_ticket_bill_rows\n" +
				"LEFT JOIN\n" +
				"	rsc_ts.rsc_ts_ticket_bill\n" +
				"ON\n" +
				"	rsc_ts.rsc_ts_ticket_bill_rows.bill_id = rsc_ts.rsc_ts_ticket_bill.id\n" +
				"LEFT JOIN\n" +
				"	rsc_ts.rsc_ts_ticket_rate_master\n" +
				"ON\n" +
				"	rsc_ts.rsc_ts_ticket_bill_rows.rate_master_id = rsc_ts.rsc_ts_ticket_rate_master.id\n" +
				"LEFT JOIN\n" +
				"	rsc_ts.rsc_ts_ticket_master\n" +
				"ON\n" +
				"	rsc_ts.rsc_ts_ticket_rate_master.ticket_id = rsc_ts.rsc_ts_ticket_master.id\n" +
				"LEFT JOIN\n" +
				"	rsc_ts.rsc_ts_visitor_type_master\n" +
				"ON" +
				"	rsc_ts.rsc_ts_ticket_rate_master.visitor_id = rsc_ts.rsc_ts_visitor_type_master.id\n" +
				"LEFT JOIN\n" +
				"	rsc_ts.rsc_ts_users\n" +
				"ON\n" +
				"	rsc_ts.rsc_ts_ticket_bill.generated_by = rsc_ts.rsc_ts_users.id AND rsc_ts.rsc_ts_ticket_rate_master.revised_by\n",
		nativeQuery = true)
	public List<TicketBillRow> getTicketBillRowsAtDateTime(String startDateTime, String endDateTime);
	*/
	@Query(value = "SELECT bill FROM TicketBillRow bill WHERE bill.rate.billType = \'TICKET\'", nativeQuery = false)
	public List<TicketBillRow> getTicketBillRows();
}
