package com.rsc.bhopal.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rsc.bhopal.dtos.PackageDetailsDTO;
import com.rsc.bhopal.service.PackageDetailsService;

import java.util.Map;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/manage")
@Slf4j
public class PackageController {
	@Autowired
	private PackageDetailsService packageDetailsService;

	@GetMapping(path = "/package/add")
	public String showPackages(Map<String, Object> attributes) {
		attributes.put("packages", packageDetailsService.getAllPackageDetails());
		return "packages/add";
	}

	@PostMapping(path = "/package/add")
	public String addPackage(@ModelAttribute PackageDetailsDTO packageDetailsDTO, Principal user) {
		packageDetailsService.addPackageDetails(packageDetailsDTO, user.getName());
		return "redirect:/manage/package/add";
	}

	@PostMapping(path = "/package/toggle")
	public String toggleActiveState(@RequestParam("packageId") Long packageId) {
		if (packageId != null)
			packageDetailsService.togglePackage(packageId);
		return "redirect:/manage/package/add";
	}
}
