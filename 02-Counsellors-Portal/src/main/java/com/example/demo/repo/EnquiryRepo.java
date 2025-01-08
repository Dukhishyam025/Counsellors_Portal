package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.EnquiryEntity;



public interface EnquiryRepo extends JpaRepository<EnquiryEntity, Integer> {

	//select * from enq_tbl where counsellor_id=:id
	public List<EnquiryEntity> findByCounsellorCounsellorId(Integer counsellorId);
	
}
