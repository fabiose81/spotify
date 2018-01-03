package com.fabiose.spotify.feign;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface SpotifyFeignClient {

	@RequestLine("GET")
	String findAll();
	
	@RequestLine("POST /api/token")
	@Headers("Content-Type: application/x-www-form-urlencoded")
	@Body("grant_type=client_credentials")
	String getToken();
	
	@RequestLine("GET")
	@Headers("Authorization: Bearer {token}")
	String getArtist(@Param("token") String token);

}
