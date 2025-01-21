package com.ChallengeLiterAlura;

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
		String text = "A Song of Ice and Fire";
		var res = api.fetchBooks(text);
		System.out.println(res);

	}
}
