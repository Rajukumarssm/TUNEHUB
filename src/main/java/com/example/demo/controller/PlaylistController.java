package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.entities.Playlist;
import com.example.demo.entities.Song;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PlaylistController {
	@Autowired
	SongService songService;
	
	@Autowired
	PlaylistService playlistService;
	
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model) {
		//here fetch all song to create playlist 
		List<Song> songList= songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		
		return "createPlaylist";
	}
	
	//called when click on create playlist form submit button 
	@GetMapping("/addPlaylist")
	public String addPlaylist(@ModelAttribute Playlist playlist) {
		playlistService.addPlaylist(playlist);
		
		//updating song table
		List<Song> songList = playlist.getSongs();//it return list of song
		
		for(Song s : songList) {
			s.getPlaylists().add(playlist);//getplaylist in song.java method basically here mapping is done then add playlist
			
			songService.updateSong(s);	//here updatesong is called to update song details ie it add playlist name
			//this method in songservice.java
		}
		
		return "adminHome";
	}
	
	@GetMapping("/displayPlaylists")
	public String viewPlaylists(Model model) {
		
		List<Playlist> allPlaylists= playlistService.fetchAllPlaylists();
		model.addAttribute("allPlaylists", allPlaylists);
		return "displayPlaylists";
	}
	
	
}
