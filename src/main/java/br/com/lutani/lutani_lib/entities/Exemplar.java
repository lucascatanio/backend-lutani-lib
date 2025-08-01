package br.com.lutani.lutani_lib.entities;

import java.util.UUID;

import br.com.lutani.lutani_lib.enums.StatusExemplar;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
@Table(name = "exemplares")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exemplar extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @Column(name = "codigo_exemplar", unique = true, nullable = false, length = 100)
    private String codigoExemplar;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusExemplar status;
}
