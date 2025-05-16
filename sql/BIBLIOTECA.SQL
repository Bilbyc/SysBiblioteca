CREATE TABLE Aluno (
                       matriculaAluno INT PRIMARY KEY,
                       nome VARCHAR(255) NOT NULL
);

CREATE TABLE Publicacao (
                            codigoPub INT PRIMARY KEY,
                            titulo VARCHAR(255) NOT NULL,
                            ano INT,
                            autor VARCHAR(255),
                            tipo VARCHAR(100) -- Ex: Livro, Revista, Tese
);


CREATE TABLE Emprestimo (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            dataEmprestimo DATE NOT NULL,
                            dataDevolucao DATE,
                            aluno_matricula INT NOT NULL,
                            publicacao_codigo INT NOT NULL,
                            FOREIGN KEY (aluno_matricula) REFERENCES Aluno(matriculaAluno),
                            FOREIGN KEY (publicacao_codigo) REFERENCES Publicacao(codigoPub)
);