CREATE TABLE nivel_acesso (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    nome VARCHAR(255) NOT NULL,
    nome_usuario VARCHAR(100) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    nivel_acesso_id INT NOT NULL,
    dt_inclusao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    dt_alteracao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_nivel_acesso FOREIGN KEY (nivel_acesso_id) REFERENCES nivel_acesso (id)
);

CREATE TABLE leitores (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    nome VARCHAR(255) NOT NULL,
    cpf VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    endereco TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'ATIVO',
    dt_inclusao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    dt_alteracao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    usr_inclusao_id UUID,
    usr_alteracao_id UUID,
    identidade_dados BYTEA,
    identidade_tipo VARCHAR(100),
    comprov_residencia_dados BYTEA,
    comprov_residencia_tipo VARCHAR(100),
    CONSTRAINT fk_leitor_usr_inclusao FOREIGN KEY (usr_inclusao_id) REFERENCES usuarios (id),
    CONSTRAINT fk_leitor_usr_alteracao FOREIGN KEY (usr_alteracao_id) REFERENCES usuarios (id)
);

CREATE TABLE livros (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    editora VARCHAR(150),
    ano_publicacao INT,
    genero VARCHAR(100),
    dt_inclusao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    dt_alteracao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    usr_inclusao_id UUID,
    usr_alteracao_id UUID,
    CONSTRAINT fk_livro_usr_inclusao FOREIGN KEY (usr_inclusao_id) REFERENCES usuarios (id),
    CONSTRAINT fk_livro_usr_alteracao FOREIGN KEY (usr_alteracao_id) REFERENCES usuarios (id)
);

CREATE TABLE exemplares (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    livro_id UUID NOT NULL,
    codigo_exemplar VARCHAR(100) UNIQUE NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'DISPONIVEL',
    dt_inclusao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    dt_alteracao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    usr_inclusao_id UUID,
    usr_alteracao_id UUID,
    CONSTRAINT fk_exemplar_livro FOREIGN KEY (livro_id) REFERENCES livros (id) ON DELETE RESTRICT,
    CONSTRAINT fk_exemplar_usr_inclusao FOREIGN KEY (usr_inclusao_id) REFERENCES usuarios (id),
    CONSTRAINT fk_exemplar_usr_alteracao FOREIGN KEY (usr_alteracao_id) REFERENCES usuarios (id)
);

CREATE TABLE emprestimos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid (),
    exemplar_id UUID NOT NULL,
    leitor_id UUID NOT NULL,
    dt_emprestimo TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dt_vencimento TIMESTAMP WITH TIME ZONE NOT NULL,
    dt_devolucao TIMESTAMP WITH TIME ZONE,
    status VARCHAR(50) NOT NULL DEFAULT 'ATIVO',
    dt_inclusao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    dt_alteracao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    usr_inclusao_id UUID NOT NULL,
    usr_alteracao_id UUID,
    CONSTRAINT fk_emprestimo_exemplar FOREIGN KEY (exemplar_id) REFERENCES exemplares (id),
    CONSTRAINT fk_emprestimo_leitor FOREIGN KEY (leitor_id) REFERENCES leitores (id),
    CONSTRAINT fk_emprestimo_usr_inclusao FOREIGN KEY (usr_inclusao_id) REFERENCES usuarios (id),
    CONSTRAINT fk_emprestimo_usr_alteracao FOREIGN KEY (usr_alteracao_id) REFERENCES usuarios (id)
);

INSERT INTO nivel_acesso (nome) VALUES ('ADMINISTRADOR'), ('BIBLIOTECARIO');

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX idx_usuarios_nivel_acesso_id ON usuarios(nivel_acesso_id);
CREATE INDEX idx_exemplares_livro_id ON exemplares(livro_id);
CREATE INDEX idx_emprestimos_exemplar_id ON emprestimos(exemplar_id);
CREATE INDEX idx_emprestimos_leitor_id ON emprestimos(leitor_id);
CREATE INDEX idx_emprestimos_usr_inclusao_id ON emprestimos(usr_inclusao_id);
CREATE INDEX idx_livros_titulo ON livros USING gin (titulo gin_trgm_ops);
CREATE INDEX idx_livros_autor ON livros USING gin (autor gin_trgm_ops);
CREATE INDEX idx_leitores_cpf ON leitores(cpf);
CREATE INDEX idx_leitores_nome ON leitores(nome);
CREATE INDEX idx_exemplares_status ON exemplares(status);
CREATE INDEX idx_emprestimos_status ON emprestimos(status);