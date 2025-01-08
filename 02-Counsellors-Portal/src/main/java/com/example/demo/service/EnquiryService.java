package com.example.demo.service;

import java.util.List;

import com.example.demo.DTO.DashboardResponseDTO;
import com.example.demo.DTO.EnqFilterDTO;
import com.example.demo.DTO.EnquiryDTO;

public interface EnquiryService {


	public DashboardResponseDTO getDashboardInfo(Integer counsellorId);
	
	public boolean addEnquriy(EnquiryDTO enqDTO, Integer counsellorId);
	
	public List<EnquiryDTO> getEnquiries(Integer counsellorId);
	
	public List<EnquiryDTO> getEnquiriesEnq(EnqFilterDTO filterDTO, Integer counsellorId);
	
	public EnquiryDTO getEnquriyById(Integer enqId);
}
