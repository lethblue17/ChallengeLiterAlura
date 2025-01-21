package com.ChallengeLiterAlura;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GutendexApiClient {
    private static final String API_BASE_URL = "https://gutendex.com/books/";
    private final HttpClient httpClient;

    public GutendexApiClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public String fetchBooks(String searchTerm) {
        String queryUrl = API_BASE_URL;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            queryUrl += "?search=" + searchTerm.replace(" ", "%20");
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(queryUrl))
                .header("User-Agent", "GutendexApiClient/1.0")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return String.format("Erro ao consumir a API. Código de status: %d", response.statusCode());
            }
        } catch (Exception e) {
            return "Erro ao conectar com a API: " + e.getMessage();
        }
    }
}