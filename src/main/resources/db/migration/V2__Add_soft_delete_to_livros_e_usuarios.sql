ALTER TABLE livros ADD COLUMN deleted_at TIMESTAMP WITH TIME ZONE;
ALTER TABLE livros ADD COLUMN deleted_by_user_id UUID;
ALTER TABLE livros ADD CONSTRAINT fk_livro_deleted_by FOREIGN KEY (deleted_by_user_id) REFERENCES usuarios(id);

ALTER TABLE usuarios ADD COLUMN deleted_at TIMESTAMP WITH TIME ZONE;
ALTER TABLE usuarios ADD COLUMN deleted_by_user_id UUID;
ALTER TABLE usuarios ADD CONSTRAINT fk_usuario_deleted_by FOREIGN KEY (deleted_by_user_id) REFERENCES usuarios(id);