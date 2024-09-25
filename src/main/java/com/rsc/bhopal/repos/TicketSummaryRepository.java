package com.rsc.bhopal.repos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rsc.bhopal.entity.TicketSummary;

public interface TicketSummaryRepository extends JpaRepository<TicketSummary, Long> {
	@Query(value="SELECT \n" + 
				"    (COUNT(rows_.id)) COUNT,\n" + 
				"    ticket_name TICKET,\n" + 
				"    visitor_name GROUP_, \n" + 
				"     SUM(price) TOTAL\n" + 
				"FROM \n" + 
				"    rsc_ts.rsc_ts_ticket_bill_rows rows_\n" + 
				"LEFT JOIN \n" + 
				"    rsc_ts.rsc_ts_ticket_rate_master ON rate_master_id = rsc_ts.rsc_ts_ticket_rate_master.id\n" + 
				"LEFT JOIN \n" + 
				"    rsc_ts.rsc_ts_ticket_master ON ticket_id = rsc_ts.rsc_ts_ticket_master.id\n" + 
				"LEFT JOIN \n" + 
				"    rsc_ts.rsc_ts_visitor_type_master ON visitor_id = rsc_ts.rsc_ts_visitor_type_master.id\n" + 
				"WHERE bill_type <> 'PARKING'\n" + 
				"#AND rows_.bill_id in (select id from rsc_ts_ticket_bill where   DATE_FORMAT(generated_at,'%Y-%m-%d')  = DATE_FORMAT(sysdate(),'%Y-%m-%d'))\n" + 
				"AND rows_.bill_id in (select id from rsc_ts_ticket_bill where   \n" + 
				"#DATE_FORMAT(generated_at,'%Y-%m-%d')  between  (select CAST(DATE_FORMAT(NOW() ,'%Y-%m-01') as DATE)) and  DATE_FORMAT(sysdate(),'%Y-%m-%d'))\n" + 
				"DATE_FORMAT(generated_at,'%Y-%m-%d')  between  (select CAST(DATE_FORMAT(:startDate,'%Y-%m-%d') as DATE)) and  DATE_FORMAT(:endDate,'%Y-%m-%d'))\n" + 
				"GROUP BY\n" + 
				"ticket_name\n" + 
				",visitor_name\n" + 
				"ORDER BY \n" + 
				" TOTAL DESC, ticket_name ASC, visitor_name ASC", nativeQuery = true)
	public List<com.rsc.bhopal.projections.TicketSummary> getTicketSummaryCountByTicketsAndGroups(String startDate,String endDate);
/*
	@Query(name = "GET_BILL_SUMMARY_COUNT",
		value = "SELECT COUNT(bill.id) AS co, rate.ticketType.name, rate.visitorsType.name, SUM(rate.price) FROM TicketSummary bill JOIN bill.ticketRatesMaster rate WHERE rate.billType <> 'PARKING' GROUP BY rate.ticketType.name, rate.visitorsType.name",
		nativeQuery = true)
	public List<com.rsc.bhopal.projections.TicketSummary> getAllBillSummary(String startDate, String endDate);
*/
}
