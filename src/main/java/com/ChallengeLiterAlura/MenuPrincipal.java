package com.ChallengeLiterAlura;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

public class MenuPrincipal {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public MenuPrincipal(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        GutendexApiClient apiClient = new GutendexApiClient();
        ObjectMapper objectMapper = new ObjectMapper();

        while (true) {
            System.out.println("\nMenu Principal");
            System.out.println("1. Buscar livro pelo ID");
            System.out.println("2. Buscar livros por termo de pesquisa");
            System.out.println("3. Listar autores salvos");
            System.out.println("4. Listar autores vivos em um determinado ano");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Digite o ID do livro: ");
                    String bookId = scanner.nextLine();
                    String bookResponse = apiClient.fetchBooksById(bookId);
                    try {
                        BookResponse book = objectMapper.readValue(bookResponse, BookResponse.class);
                        System.out.println("Detalhes do Livro:");
                        System.out.println("ID: " + book.getId());
                        System.out.println("Título: " + book.getTitle());
                        System.out.println("Downloads: " + book.getDownloadCount());
                        // Salvar autor
                        AuthorEntity authorEntity = saveAuthor(book);

                        // Salvar livro
                        BookEntity bookEntity = new BookEntity();
                        bookEntity.setTitle(book.getTitle());
                        bookEntity.setDownloadCount(book.getDownloadCount());
                        bookEntity.setAuthor(authorEntity);
                        bookRepository.save(bookEntity);

                        System.out.println("Livro salvo no banco de dados: " + book.getTitle());

                        // Extract and save author
                        Author author = Author.fromJson(bookResponse);
                        if (author != null) {
                            apiClient.saveAuthorFromResponse(bookResponse);
                            System.out.println("Autor salvo: " + author);
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao processar resposta: " + e.getMessage());
                    }
                    break;

                case "2":
                    System.out.print("Digite o termo de pesquisa: ");
                    String searchTerm = scanner.nextLine();
                    String searchResponse = apiClient.fetchBooks(searchTerm);
                    try {
                        BookSearchResponse searchResults = objectMapper.readValue(searchResponse, BookSearchResponse.class);
                        System.out.println("Resultados da pesquisa:");
                        searchResults.getResults().forEach(book -> {
                            System.out.println("- Título: " + book.getTitle() + " (ID: " + book.getId() + ")");

                            // Extract and save author for each book
                            Author author = Author.fromJson(searchResponse);
                            if (author != null) {
                                apiClient.saveAuthorFromResponse(searchResponse);
                                System.out.println("Autor salvo: " + author);
                            }
                        });
                    } catch (Exception e) {
                        System.out.println("Erro ao processar resposta: " + e.getMessage());
                    }
                    break;

                case "3":
                    System.out.println("Autores salvos:");
                    apiClient.listAuthors().forEach(System.out::println);
                    break;

                case "4":
                    System.out.print("Digite o ano para verificar autores vivos: ");
                    int year = Integer.parseInt(scanner.nextLine());
                    System.out.println("Autores vivos em " + year + ":");
                    apiClient.listLivingAuthorsInYear(year).forEach(System.out::println);
                    break;

                case "5":
                    System.out.println("Saindo do programa. Até logo!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }
    private AuthorEntity saveAuthor(BookResponse book) {
        AuthorEntity authorEntity = new AuthorEntity();
        Author author = book.getAuthors().length > 0 ? book.getAuthors()[0] : null;
        if (author != null) {
            authorEntity.setName(author.getName());
            authorEntity.setBirthYear(author.getBirthYear());
            authorEntity.setDeathYear(author.getDeathYear());
            authorEntity = authorRepository.save(authorEntity);
            System.out.println("Autor salvo no banco de dados: " + author.getName());
        }
        return authorEntity;
    }
}
