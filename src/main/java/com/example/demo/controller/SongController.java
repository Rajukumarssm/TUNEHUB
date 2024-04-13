package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.entities.Song;
import com.example.demo.services.SongService;

@Controller
public class SongController {
	@Autowired
	SongService service;
	
	//called when admin click on add song submit button
	@PostMapping("/addSong")
	public String addSong(@ModelAttribute Song song) {
		
		boolean songStatus = service.songExists(song.getName());
		if(songStatus == false) {
			song.setImageLink(song.getImageLink());
			service.addSong(song);
			
		}
		else {
			System.out.println("Song already exists");
			return "SongExist";
		}
		return "adminHome";
		
	}
	
	@GetMapping("/displaySongs")
	public String viewSongs(Model model) {
		//model is used to add songs details comes from databse to controller
		//then fetch to html page by thymeleaf 
		List<Song> songsList = service.fetchAllSongs();
		model.addAttribute("songs", songsList);
		
		return "displaySongs";
	}
	
	//display song to customer
	@GetMapping("/playSongs")
	public String playSongs(Model model) {
		
		boolean premiumUser = false;
		//agar premium hua to display kro
		if(premiumUser == true) {
			List<Song> songsList = service.fetchAllSongs();
			model.addAttribute("songs", songsList);
			return "displaySongs";
		}
		else {
			return "makePayment";//make an html file to display a message only make payment
		}	
	}
	
}
