package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "enquiries_tbl")
@Setter
@Getter
public class EnquiryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enqId;
	private String stuName;
	private String stuPhno;
	private String classMode;
	private String course;
	private String enqStatus;
	
	@ManyToOne
	@JoinColumn(name = "counsellor_id")
	private CounsellorEntity counsellor;
}