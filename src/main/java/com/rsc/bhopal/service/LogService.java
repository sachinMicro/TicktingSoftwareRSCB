package com.rsc.bhopal.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.rsc.bhopal.dtos.LogDTO;

import com.rsc.bhopal.repos.LogRepository;

@Service
public class LogService {
	@Autowired
	private LogRepository logRepository;

	public List<LogDTO> getAllLogs() {
		List<LogDTO> logDTOs = new ArrayList<LogDTO>();
		logRepository.findAll().forEach(log -> {
			LogDTO logDTO = new LogDTO();
			BeanUtils.copyProperties(log, logDTO);
			logDTOs.add(logDTO);
		});
		return logDTOs;
	}

	public List<LogDTO> getAllLatestArrangedLogs() {
		return logRepository.getAllLogsDateSorted();
	}
}
