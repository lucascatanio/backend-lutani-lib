CREATE TABLE IF NOT EXISTS generos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) UNIQUE NOT NULL
);

ALTER TABLE livros ADD COLUMN IF NOT EXISTS genero_id INT;

DO $$
BEGIN
    IF EXISTS(SELECT 1 FROM information_schema.columns WHERE table_name='livros' AND column_name='genero') THEN
        INSERT INTO generos (nome)
        SELECT DISTINCT genero FROM livros WHERE genero IS NOT NULL
        ON CONFLICT (nome) DO NOTHING;

        UPDATE livros
        SET genero_id = (SELECT id FROM generos WHERE generos.nome = livros.genero)
        WHERE livros.genero IS NOT NULL;

        ALTER TABLE livros DROP COLUMN genero;
    END IF;
END $$;

ALTER TABLE livros DROP CONSTRAINT IF EXISTS fk_livro_genero;
ALTER TABLE livros ADD CONSTRAINT fk_livro_genero FOREIGN KEY (genero_id) REFERENCES generos(id);