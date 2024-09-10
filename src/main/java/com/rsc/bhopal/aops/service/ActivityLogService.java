package com.rsc.bhopal.aops.service;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rsc.bhopal.dtos.ActivityLogDTO;
import com.rsc.bhopal.entity.ActivityLog;
import com.rsc.bhopal.repos.ActivityLogRepo;
import com.rsc.bhopal.repos.UserDetailsRepository;
import com.rsc.bhopal.utills.CommonUtills;

@Service
public class ActivityLogService {
	
	@Autowired
	private ActivityLogRepo logRepo;
	
	@Autowired
	private UserDetailsRepository userRepo; 
	
	public void log(ActivityLogDTO dto)  {		
		 ActivityLog log = new ActivityLog();		 
		 BeanUtils.copyProperties(dto, log); 
		 log.setActionAt(new Date()); 
		 log.setActionBy(userRepo.findByUsername(dto.getActionBy()).get());		 
		 try {
			log.setPayload(CommonUtills.convertToJSON(dto.getPayload()));
		 }catch (JsonProcessingException e) {			
			e.printStackTrace();
		 }
		 logRepo.save(log);
	}
	
	
	

}
