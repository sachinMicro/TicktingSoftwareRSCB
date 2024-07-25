package com.rsc.bhopal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsc.bhopal.dtos.VisitorsTypeDTO;
import com.rsc.bhopal.entity.VisitorsType;
import com.rsc.bhopal.enums.VisitorsTypeEnum;
import com.rsc.bhopal.repos.VisitorTypeRepository;

import jakarta.transaction.Transactional;

@Service
public class VisitorTypeService {	

	@Autowired
	private VisitorTypeRepository visitorTypeRepo;

	
	public VisitorsTypeDTO getGeneralVisitorId() {
		VisitorsTypeDTO visitorDTO = new VisitorsTypeDTO();
		VisitorsType visitorsType = visitorTypeRepo.findByType(VisitorsTypeEnum.GENERAL);
		BeanUtils.copyProperties(visitorsType, visitorDTO);
		return visitorDTO;
	}
	
	public List<VisitorsTypeDTO> getAllVisitorTypes() {
		List<VisitorsTypeDTO> visitorsDTOs = new ArrayList<VisitorsTypeDTO>();
		List<VisitorsType> visitors = visitorTypeRepo.findAll();
		for (VisitorsType visitor : visitors) {
			VisitorsTypeDTO visitorDTO = new VisitorsTypeDTO();
			BeanUtils.copyProperties(visitor, visitorDTO);
			visitorsDTOs.add(visitorDTO);
		}
		return visitorsDTOs;
	}

	@Transactional
	public void addVisitorType() {
		List<VisitorsType> types = Arrays.asList(new VisitorsType("General Visitors", VisitorsTypeEnum.GENERAL, 4, 0),

				new VisitorsType("More than 25 People", VisitorsTypeEnum.GROUP, 25, 0),
				new VisitorsType("School Group", VisitorsTypeEnum.SCHOOL, 0, 0),
				new VisitorsType("Govt.or Municipal School", VisitorsTypeEnum.SCHOOL, 0, 0),

				new VisitorsType("Family Group (Group of 4)", VisitorsTypeEnum.FAMILY, 4, 0),
				new VisitorsType("Family Group (Group of 6)", VisitorsTypeEnum.FAMILY, 6, 0),

				new VisitorsType("Sponsored Visitors", VisitorsTypeEnum.SPONCERED, 0, 0),
				new VisitorsType("Free Entry", VisitorsTypeEnum.OTHER, 0, 0),
				new VisitorsType("Special Case", VisitorsTypeEnum.SPECIAL, 0, 0)

		);
		types.forEach(System.out::println);
		visitorTypeRepo.saveAll(types);
	}

}
