package com.rsc.bhopal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rsc.bhopal.dtos.LogDTO;
import com.rsc.bhopal.service.LogService;

@Controller
@RequestMapping("/manage")
public class LogsController {

	@Autowired
	private LogService logService;

	@GetMapping(path = "/logs")
	public String showLogTable(java.util.Map<String, Object> attributes) {
		List<LogDTO> LogDTOs = logService.getAllLatestArrangedLogs();
		attributes.put("logs", LogDTOs);
		return "log";
	}
}
