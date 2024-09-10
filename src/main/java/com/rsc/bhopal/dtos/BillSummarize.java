package com.rsc.bhopal.dtos;

import java.util.List;

import lombok.Data;

@Data
public class BillSummarize {
   private List<BillDescription> billDescription;
   private List<ParkingBillDescription> parkingBillDescription;
}
