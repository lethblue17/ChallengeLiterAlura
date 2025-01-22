package com.ChallengeLiterAlura;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("birth_year")
    private final Integer birthYear;

    @JsonProperty("death_year")
    private final Integer deathYear;

    public Author(@JsonProperty("name") String name,
                  @JsonProperty("birth_year") Integer birthYear,
                  @JsonProperty("death_year") Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public static Author fromJson(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            JsonNode authorsNode = rootNode.path("authors");
            if (authorsNode.isArray() && authorsNode.size() > 0) {
                JsonNode firstAuthor = authorsNode.get(0);
                String name = firstAuthor.path("name").asText(null);
                Integer birthYear = firstAuthor.path("birth_year").isInt() ? firstAuthor.path("birth_year").asInt() : null;
                Integer deathYear = firstAuthor.path("death_year").isInt() ? firstAuthor.path("death_year").asInt() : null;

                return new Author(name, birthYear, deathYear);
            }
        } catch (Exception e) {
            System.out.println("Erro ao processar JSON do autor: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                '}';
    }
}
