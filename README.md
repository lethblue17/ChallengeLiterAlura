# Projeto Gutendex CLI

Este projeto é uma aplicação de linha de comando (CLI) construída com **Spring Boot** e **PostgreSQL** que consome a API [Gutendex](https://gutendex.com) para buscar informações de livros e salvar esses dados em um banco de dados relacional.

## Funcionalidades

1. **Buscar livro pelo ID:**
    - Realiza uma consulta na API com base no ID do livro e salva os dados do livro e do autor no banco de dados.

2. **Buscar livros por termo de pesquisa:**
    - Busca livros na API com base em um termo fornecido pelo usuário, salvando os resultados no banco de dados.

3. **Listar autores salvos:**
    - Exibe uma lista de todos os autores armazenados no banco de dados.

4. **Listar autores vivos em um determinado ano:**
    - Permite ao usuário informar um ano e retorna os autores que estavam vivos naquele período.

5. **Sair:**
    - Encerra o programa.

## Requisitos

- **Java 17+**
- **Spring Boot 3+**
- **PostgreSQL**
- **Maven**

## API Utilizada
Gutendex API: https://gutendex.com
Endpoints usados:
/books/ para buscar livros por termo de pesquisa.
/books/{id} para buscar livros pelo ID.
## Licença
Este projeto é de código aberto e está disponível sob a licença MIT.







