package br.com.lutani.lutani_lib.entities;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "nome_usuario", nullable = false, unique = true, length = 100)
    private String nomeUsuario;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @ManyToOne
    @JoinColumn(name = "nivel_acesso_id", nullable = false)
    private NivelAcesso nivelAcesso;

    @CreationTimestamp
    @Column(name = "dt_inclusao", nullable = false, updatable = false)
    private Instant dtInclusao;

    @UpdateTimestamp
    @Column(name = "dt_alteracao", nullable = true)
    private Instant dtAlteracao;
}
