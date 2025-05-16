# Projeto Sistema de Biblioteca (JPA e H2)

Este projeto demonstra um sistema de biblioteca simples utilizando Java com JPA (Hibernate como provedor) e o banco de dados em memória H2.

## Alunos
* **Carlos Henrique Dias Bilby - 202307907280**
* **Julio Rodrigues Matos - 202307908172**

## 1. Entidades do Sistema

O modelo de dados é composto pelas seguintes entidades principais:

* **`Aluno`**: Representa um aluno da biblioteca.
    * `matriculaAluno` (int): Identificador único do aluno (Chave Primária).
    * `nome` (String): Nome do aluno.
    * Relacionamento: Um `Aluno` pode ter vários `Emprestimos`.

* **`Publicacao`**: Representa um item publicável da biblioteca (livro, revista, etc.).
    * `codigoPub` (int): Código único da publicação (Chave Primária).
    * `titulo` (String): Título da publicação.
    * `ano` (int): Ano de publicação.
    * `autor` (String): Autor da publicação.
    * `tipo` (String): Tipo da publicação (ex: "Livro", "Revista").
    * Relacionamento: Uma `Publicacao` pode estar em vários `Emprestimos`.

* **`Emprestimo`**: Representa o ato de um aluno emprestar uma publicação.
    * `id` (Long): Identificador único do empréstimo (Chave Primária, auto-gerada).
    * `dataEmprestimo` (Date): Data em que o empréstimo foi realizado.
    * `dataDevolucao` (Date): Data em que a publicação foi devolvida (pode ser nula).
    * Relacionamentos:
        * Um `Emprestimo` está associado a um `Aluno` (Muitos-para-Um).
        * Um `Emprestimo` está associado a uma `Publicacao` (Muitos-para-Um).

## 2. Acessando o Banco de Dados H2 (Durante a Execução)

Quando você executa a classe de teste principal que inicia os servidores H2 (como `EmprestimoDAOTeste.java`), o banco de dados em memória H2 se torna acessível externamente.

**Para acessar o banco ao rodar `EmprestimoDAOTeste.java`:**

1.  **Execute a classe `EmprestimoDAOTeste.java`** em sua IDE.
2.  Observe o console da aplicação. Você verá mensagens indicando os endereços para acesso:
    * **H2 Web Console URL:** `http://localhost:8084` (a porta pode variar se você alterou na classe de teste)
    * **JDBC URL (para ferramentas SQL externas):** `jdbc:h2:tcp://localhost:9094/mem:bibliotecadb`
3.  **Abra o H2 Web Console** no seu navegador usando o URL fornecido.
4.  **Na tela de login do H2 Console:**
    * **Saved Settings:** `Generic H2 (Embedded)` ou similar.
    * **Driver Class:** `org.h2.Driver`
    * **JDBC URL:** `jdbc:h2:mem:bibliotecadb` (este é o nome do banco em memória)
    * **User Name:** `sa`
    * **Password:** (deixe em branco)
5.  Clique em **Connect**.
6.  Você poderá visualizar as tabelas (`ALUNO`, `PUBLICACAO`, `EMPRESTIMO`), seus dados e executar queries SQL.
7.  A aplicação Java permanecerá em execução (aguardando um "Enter" no console) para permitir a inspeção do banco. Pressione "Enter" no console da aplicação Java para finalizá-la e parar os servidores H2.

## 3. Classe de Teste `EmprestimoDAOTeste.java`

A classe `TesteEmprestimoDAO.java` serve para demonstrar e testar as funcionalidades da camada de acesso a dados para a entidade `Emprestimo`.

**Principais Ações da Classe:**

1.  **Inicialização:**
    * Configura e inicia a `EntityManagerFactory` do JPA.
    * Inicia os servidores H2 (Web Console e TCP Server) para permitir a visualização do banco de dados em memória.
    * Limpa as tabelas do banco para garantir um estado inicial consistente para cada execução.

2.  **Criação de Dados de Pré-requisito:**
    * Cria e persiste 5 registros de `Aluno` utilizando `AlunoDAO`.
    * Cria e persiste 5 registros de `Publicacao` utilizando `PublicacaoDAO`.
      Esses registros são usados para criar empréstimos válidos.

3.  **Testes dos Métodos de `EmprestimoDAO`:**
    * **`insert(Emprestimo entity)`**: Insere novos empréstimos associando os alunos e publicações criados anteriormente.
    * **`findById(Long id)`**: Busca empréstimos específicos pelo seu ID.
    * **`findAll()`**: Lista todos os empréstimos existentes no banco.
    * **`update(Emprestimo entity)`**: Modifica dados de um empréstimo existente (ex: data de devolução, aluno ou publicação associada).
    * **`delete(Emprestimo entity)`**: Remove um empréstimo do banco.
    * Os resultados das operações são impressos no console.

4.  **Manutenção e Finalização:**
    * Mantém a aplicação "pausada" (aguardando input do usuário) para que o banco possa ser inspecionado via H2 Console.
    * Ao finalizar, para os servidores H2 e fecha a `EntityManagerFactory`, liberando os recursos.

Esta classe é um exemplo prático de como interagir com o `EmprestimoDAO` e pode ser usada como ponto de partida para testes mais elaborados ou para entender o funcionamento das operações CRUD na entidade `Emprestimo`.
