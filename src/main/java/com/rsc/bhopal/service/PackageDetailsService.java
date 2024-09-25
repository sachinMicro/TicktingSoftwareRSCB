package com.rsc.bhopal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.stereotype.Service;

import com.rsc.bhopal.dtos.PackageDetailsDTO;
import com.rsc.bhopal.dtos.RSCUserDTO;
import com.rsc.bhopal.entity.PackageDetails;
import com.rsc.bhopal.repos.PackageDetailsRepository;

@Service
public class PackageDetailsService {
	@Autowired
	private PackageDetailsRepository packageDetailsRepository;

	@Autowired
	private RSCUserDetailsService rscUserDetailsService;

	public List<PackageDetailsDTO> getAllPackageDetails() {
		List<PackageDetailsDTO> packageDetailsDTOs = new ArrayList<PackageDetailsDTO>();
		packageDetailsRepository.findAll().forEach(packageDetail -> {
			PackageDetailsDTO packageDetailsDTO = new PackageDetailsDTO();
			BeanUtils.copyProperties(packageDetail, packageDetailsDTO);
			packageDetailsDTO.setAddedBy(packageDetail.getAddedBy().getUsername());
			// packageDetailsDTO.setBillDate(packageDetail.getBillDate());
			// packageDetailsDTO.setAddedBy(packageDetail.getAddedBy());
			packageDetailsDTOs.add(packageDetailsDTO);
		});
		return packageDetailsDTOs;
	}

	public void addPackageDetails(PackageDetailsDTO packageDetailsDTO, String user) {
		PackageDetails packageDetail = new PackageDetails();
		BeanUtils.copyProperties(packageDetailsDTO, packageDetail);
		// packageDetail.setBillDate(packageDetailsDTO.getBillDate());
		// packageDetail.setAddedBy(packageDetailsDTO.getAddedBy());
		// packageDetail.setBillDate(new Date());
		packageDetail.setAddedBy(rscUserDetailsService.getUserByUsername(user));
		packageDetailsRepository.save(packageDetail);
	}

	public void togglePackage(Long id) {
		PackageDetails packageDetails = packageDetailsRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Package not found"));
		packageDetails.setIsActive(!packageDetails.getIsActive());
		packageDetailsRepository.save(packageDetails);
	}
}
