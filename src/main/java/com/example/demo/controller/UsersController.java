package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Song;
import com.example.demo.entities.Users;
import com.example.demo.services.SongService;
import com.example.demo.services.UsersService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class UsersController {
	@Autowired
	UsersService service;
	
	@Autowired
	SongService songService;
	//signup page
	@PostMapping("/register")
	public String addUsers(@ModelAttribute Users user) 
	//@ModelAttribute Users user binds the request parameters(all input value) to the User object
	{
		//first check user exists or not
		boolean userStatus = service.emailExists(user.getEmail());
		//emailExists method in User service class
		if(userStatus  == false) {
			service.addUser(user);	
		}
		return "login";	
	}
	
	
	//login page
	@PostMapping("/validate")
	public String validate(@RequestParam("email")String email,
			@RequestParam("password") String password, HttpSession session, Model model) {
		
		if(service.validateUser(email,password) == true) {
			String role= service.getRole(email);
			//yhan role find krenge 
			//get role method present in userservice interface
			session.setAttribute("email", email);
			
			if(role.equals("admin")) {
				return "adminHome";
			}else {
				Users user = service.getUser(email);
				boolean userStatus = user.isPremium();
				
				List<Song> songsList = songService.fetchAllSongs();
				model.addAttribute("songs", songsList);
				
				model.addAttribute("isPremium", userStatus);
				
				return "customerHome";
			}
		}else {
			return "login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		return "login";
	}
	
	
	
	
}
