package com.fabiose.spotify.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fabiose.spotify.service.SpotifyService;
import com.fabiose.spotify.domain.Artist;

@RestController
public class SpotifyController {
	
	@Autowired
	private SpotifyService spotifyService;

	@RequestMapping(value = "/getArtist/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<Artist>> getArtist(@PathVariable String name) {			
		String token = spotifyService.getToken().replace("\"", "");
				
		return ResponseEntity.ok(spotifyService.getArtist(name,token));
	}
}
