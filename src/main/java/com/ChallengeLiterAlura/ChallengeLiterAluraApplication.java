package com.ChallengeLiterAlura;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiterAluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		GutendexApiClient api = new GutendexApiClient();
		String text = "";
		var res = api.fetchBooks("");
		var objectMapper = new ObjectMapper();
		BookSearchResponse response = objectMapper.readValue(res, BookSearchResponse.class);
		System.out.println(response.getCount());

	}
}
