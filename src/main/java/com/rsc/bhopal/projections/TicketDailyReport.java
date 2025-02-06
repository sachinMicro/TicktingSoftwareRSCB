package com.rsc.bhopal.projections;

import java.math.BigInteger;
import java.util.Date;

import com.rsc.bhopal.enums.BillType;
import com.rsc.bhopal.enums.GroupType;

public interface TicketDailyReport {
	public Long getDateSerial();
	public Date getBillDate();
	public Long getId();
	public Double totalSum();
	public Long getBillId();
	public Integer getPersons();
	public Boolean getCancelledStatus();
	public BigInteger getTicketSerial();
	public Double getTotalBill();
	public String getGeneratedBy();
	public Long getRateMasterId();
	public BillType getTicketType();
	public Boolean getIsActive();
	public Float getPrice();
	public Long getParkingDetailsId();
	public Long getTicketId();
	public String getTicketName();
	public Long getVisitorId();
	public String getVisitorName();
	public GroupType getGroupType();
}
