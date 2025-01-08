package com.example.demo.service;

import com.example.demo.DTO.CounsellorDTO;

public interface CounsellorService {


	public CounsellorDTO login(CounsellorDTO counsellorDTO);
	
	// if unique return true else return false
	public boolean uniqueEmailCheck(String email);
	
	public boolean register(CounsellorDTO counsellorDTO);
	 
}
