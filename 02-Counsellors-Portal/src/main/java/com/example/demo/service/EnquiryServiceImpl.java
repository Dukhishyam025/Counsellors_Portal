package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.DashboardResponseDTO;
import com.example.demo.DTO.EnqFilterDTO;
import com.example.demo.DTO.EnquiryDTO;
import com.example.demo.entity.CounsellorEntity;
import com.example.demo.entity.EnquiryEntity;
import com.example.demo.repo.CounsellorRepo;
import com.example.demo.repo.EnquiryRepo;


@Service 
public class EnquiryServiceImpl implements EnquiryService {
	
	@Autowired
	private EnquiryRepo enqRepo;
	
	@Autowired
	private CounsellorRepo counsellorRepo;

	@Override
	public DashboardResponseDTO getDashboardInfo(Integer counsellorId) {
		
		List<EnquiryEntity> enqsList = enqRepo.findByCounsellorCounsellorId(counsellorId);
		
		DashboardResponseDTO dto = new DashboardResponseDTO();
		
		//Normal Logic
		/*int openCnt = 0;
		int enrolledCnt = 0;
		int lostEnt = 0;
		
		for(EnquiryEntity entity : enqsList)
		{
			if(entity.getEnqStatus().equals("OPEN")) {
			      openCnt++;
			}else if(entity.getEnqStatus().equals("ENROLLED")) {
				  enrolledCnt++;
			}else if(entity.getEnqStatus().equals("LOST")) {
				  lostEnt++;
			}
		}
		*/
		int openCnt = enqsList.stream().filter(enq ->enq.getEnqStatus().equals("OPEN"))
		                      .collect(Collectors.toList())
		                      .size();
		
		
		int enrolledCnt = enqsList.stream()
				                  .filter(enq -> enq.getEnqStatus().equals("ENROLLED"))
				                  .collect(Collectors.toList())
				                  .size();
		
		
		int lostCnt = enqsList.stream()
                .filter(enq -> enq.getEnqStatus().equals("LOST"))
                .collect(Collectors.toList())
                .size();
		
		dto.setAlEnqCnt(enqsList.size());
		dto.setLostEnqCnt(lostCnt);
		dto.setOpenEnqCnt(openCnt);
		dto.setEnrolledEnqCnt(enrolledCnt);
		  
		return dto  ;
	}
 
	@Override
	public boolean addEnquriy(EnquiryDTO enqDTO, Integer counsellorId) {

		EnquiryEntity entity = new EnquiryEntity();
		BeanUtils.copyProperties(enqDTO, entity);
		
		//setting FK(counsellor_id) to enquiry obj
		Optional<CounsellorEntity> byId = counsellorRepo.findById(counsellorId);
		if(byId.isPresent()) {
			CounsellorEntity counsellor = byId.get();
			entity.setCounsellor(counsellor);
		}
		
		EnquiryEntity save = enqRepo.save(entity);
		
		return save.getEnqId() != null;
	}

	@Override
	public List<EnquiryDTO> getEnquiries(Integer counsellorId) {
		
		List<EnquiryDTO> enqsDtoList = new ArrayList<>();
	
		List<EnquiryEntity> enqList = enqRepo.findByCounsellorCounsellorId(counsellorId);
		
		for(EnquiryEntity entity : enqList) {
			EnquiryDTO dto = new EnquiryDTO();
			BeanUtils.copyProperties(entity, dto);
			enqsDtoList.add(dto);
		}
		
		return enqsDtoList;
	}

	@Override
	public List<EnquiryDTO> getEnquiriesEnq(EnqFilterDTO filterDTO, Integer counsellorId) {

	  EnquiryEntity entity = new EnquiryEntity();
	  if(filterDTO.getClassMode()!=null && !filterDTO.getClassMode().equals("") )
	  {
		  entity.setClassMode(filterDTO.getClassMode());
	  }
	  
	  if(filterDTO.getCourse()!= null && !filterDTO.getCourse().equals("")) {
		  entity.setCourse(filterDTO.getCourse());
	  }
	  
	  if(filterDTO.getEnqStatus()!= null && !filterDTO.getEnqStatus().equals("")) {
		  entity.setEnqStatus(filterDTO.getEnqStatus() );
	  }
	  
	  CounsellorEntity counsellor = new CounsellorEntity();
	  counsellor.setCounsellorId(counsellorId);
	  entity.setCounsellor(counsellor);
	  
	  Example<EnquiryEntity> of = Example.of(entity);
	  
	  List<EnquiryEntity> enqList = enqRepo.findAll(of);
	  
	  List<EnquiryDTO> enqsDtoList = new ArrayList<>();
	  
	  for(EnquiryEntity enq : enqList) {
			EnquiryDTO dto = new EnquiryDTO();
			BeanUtils.copyProperties(enq, dto);
			enqsDtoList.add(dto);
		}
		
		return enqsDtoList;
	}

	@Override
	public EnquiryDTO getEnquriyById(Integer enqId) {

		Optional<EnquiryEntity> byId = enqRepo.findById(enqId);
		
		if(byId.isPresent()) {
		   EnquiryEntity enquiryEntity = byId.get();
		   EnquiryDTO dto = new EnquiryDTO();
		   BeanUtils.copyProperties(enquiryEntity, dto);
		   return dto;
		}
		
		return null;
	}

}
