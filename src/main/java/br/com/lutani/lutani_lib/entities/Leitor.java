package br.com.lutani.lutani_lib.entities;

import java.util.UUID;

import br.com.lutani.lutani_lib.enums.StatusLeitor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "leitores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Leitor extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "cpf", nullable = false, length = 20, unique = true)
    private String cpf;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "endereco", columnDefinition = "TEXT")
    private String endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private StatusLeitor status;

    @Lob
    @Column(name = "identidade_dados")
    private byte[] identidadeDados;

    @Column(name = "identidade_tipo", length= 100)
    private String identidadeTipo;

    @Lob
    @Column(name = "comprov_residencia_dados")
    private byte[] comprovResidenciaDados;

    @Column(name = "comprov_residencia_tipo", length = 100)
    private String comprovResidenciaTipo;
}
