package com.ChallengeLiterAlura;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GutendexApiClient {

    private static final String API_BASE_URL = "https://gutendex.com/books/";
    private final HttpClient httpClient;
    private final List<Author> authors;

    public GutendexApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.authors = new ArrayList<>();
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

        return sendRequest(request);
    }

    public String fetchBooksById(String bookId) {
        String queryUrl = API_BASE_URL + bookId + "/";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(queryUrl))
                .header("User-Agent", "GutendexApiClient/1.0")
                .GET()
                .build();

        return sendRequest(request);
    }
    public void saveAuthorFromResponse(String jsonResponse) {
        try {
            Author author = Author.fromJson(jsonResponse);
            if (author != null) {
                authors.add(author);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar autor: " + e.getMessage());
        }
    }

    public List<Author> listAuthors() {
        return new ArrayList<>(authors);
    }

    public List<Author> listLivingAuthorsInYear(int year) {
        List<Author> livingAuthors = new ArrayList<>();
        for (Author author : authors) {
            if ((author.getBirthYear() != null && author.getBirthYear() <= year) &&
                    (author.getDeathYear() == null || author.getDeathYear() > year)) {
                livingAuthors.add(author);
            }
        }
        return livingAuthors;
    }

    private String sendRequest(HttpRequest request) {
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