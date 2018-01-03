package com.fabiose.spotify.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fabiose.spotify.domain.Artist;
import com.fabiose.spotify.feign.SpotifyFeignClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.okhttp.OkHttpClient;

@Service
public class SpotifyService {

	private SpotifyFeignClient spotifyFeignClient;

	@Value("${client_id}")
	private String clientId;

	@Value("${client_secret}")
	private String clientSecret;

	@Value("${accounts_spotify_url}")
	private String accountsSpotifyUrl;

	@Value("${api_spotify_url}")
	private String apiSpotifyUrl;

	public String getToken() {

		BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(clientId, clientSecret);

		spotifyFeignClient = Feign.builder().client(new OkHttpClient()).requestInterceptor(interceptor)
				.target(SpotifyFeignClient.class, accountsSpotifyUrl);

		String strToken = spotifyFeignClient.getToken();
		JsonParser parser = new JsonParser();
		JsonObject jSonToken = parser.parse(strToken).getAsJsonObject();

		return jSonToken.get("access_token").toString();

	}

	public List<Artist> getArtist(String name, String token) {

		StringBuilder url = new StringBuilder();
		url.append(apiSpotifyUrl);
		url.append("/v1/search?q=");
		url.append(name);
		url.append("&type=artist");

		spotifyFeignClient = Feign.builder().client(new OkHttpClient()).target(SpotifyFeignClient.class,
				url.toString());

		String strArtist = spotifyFeignClient.getArtist(token);

		JsonParser parser = new JsonParser();
		JsonObject jSonArtist = parser.parse(strArtist).getAsJsonObject();
		JsonArray artists = jSonArtist.get("artists").getAsJsonObject().get("items").getAsJsonArray();

		List<Artist> listArtist = new ArrayList<Artist>();

		artists.forEach(a -> {
			JsonObject obj = a.getAsJsonObject();

			Artist artist = new Artist();
			artist.setName(obj.get("name").toString().replace("\"", ""));

			JsonArray images = obj.get("images").getAsJsonArray();

			if (images.size() > 0)
				artist.setUrlImage(images.get(0).getAsJsonObject().get("url").toString());

			listArtist.add(artist);
		});

		return listArtist;

	}
}
