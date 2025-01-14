package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.DTO.CounsellorDTO;
import com.example.demo.DTO.DashboardResponseDTO;
import com.example.demo.service.CounsellorService;
import com.example.demo.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {
	
	@Autowired
	private CounsellorService counsellorService;
	
	@Autowired
	private EnquiryService enqService;

	@GetMapping("/")
	public String index(Model model) {
		
		CounsellorDTO cdto = new CounsellorDTO();
		model.addAttribute("counsellor", cdto);
		
		return "index";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest req,Model model) {
		
		HttpSession session = req.getSession(false);
		session.invalidate();
		
		CounsellorDTO cdto = new CounsellorDTO();
		model.addAttribute("counsellor", cdto);
		
		return "index";
	}
	
	@PostMapping("/login")
	public String handleLogin(HttpServletRequest req, CounsellorDTO counsellor, Model model) {
		
		CounsellorDTO counsellorDTO = counsellorService.login(counsellor);
		
		if(counsellorDTO == null)
		{
			CounsellorDTO cdto = new CounsellorDTO();
			model.addAttribute("counsellor", cdto);
			
			model.addAttribute("emsg", "Invalid Credentails");
		    return "index" ;
		}
		else {
			Integer counsellorId = counsellorDTO.getCounsellorId();
			
			//store counsellorId in http session obj
			HttpSession session = req.getSession(true);
			session.setAttribute("counsellorId", counsellorId);
			
			DashboardResponseDTO dashboardDto = enqService.getDashboardInfo(counsellorId);
			
			model.addAttribute("dashboardDto", dashboardDto);
		}
		
		return "dashboard";
		
	}
	
	@GetMapping("/dashboard")
	public String loadDashboard(HttpServletRequest req,Model model)
	{
		
				HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer)session.getAttribute("counsellorId");
		
		
		session.setAttribute("counsellorId", counsellorId);
		
		DashboardResponseDTO dashboardDto = enqService.getDashboardInfo(counsellorId);
		
		model.addAttribute("dashboardDto", dashboardDto);
		return "dashboard";
	}
		
		@GetMapping("/register")
		public String registerPage(Model model) {
			
			CounsellorDTO cdto = new CounsellorDTO();
			model.addAttribute("counsellor", cdto);
			
			return "register";
		}
		
		
		@PostMapping("/register")
		public String handleRegister(@ModelAttribute("counsellor")CounsellorDTO counsellor, Model model)
		{
			boolean unique = counsellorService.uniqueEmailCheck(counsellor.getEmail());
			
			if(unique) {
				
				boolean register = counsellorService.register(counsellor);
				
				if(register) {
					model.addAttribute("smsg", "Registration Success");
				}else {
					model.addAttribute("emsg", "Registration Failed");
				}
				
			}else {
				model.addAttribute("emsg", "Enter Unique Email");
			}
			return "register";
		}
}
