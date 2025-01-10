package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CounsellorEntity;



public interface CounsellorRepo extends JpaRepository<CounsellorEntity, Integer> {

	public CounsellorEntity findByEmailAndPwd(String email, String pwd);
	public CounsellorEntity findByEmail(String email);
}
