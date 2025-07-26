package br.com.lutani.lutani_lib.services;

import org.springframework.stereotype.Service;

import br.com.lutani.lutani_lib.dtos.ExemplarResponseDTO;
import br.com.lutani.lutani_lib.dtos.LivroResumidoDTO;
import br.com.lutani.lutani_lib.dtos.UsuarioResumidoDTO;
import br.com.lutani.lutani_lib.entities.Exemplar;
import br.com.lutani.lutani_lib.repositories.ExemplarRepository;

@Service
public class ExemplarService {

    private final ExemplarRepository exemplarRepository;

    public ExemplarService(ExemplarRepository exemplarRepository) {
        this.exemplarRepository = exemplarRepository;
    }

    private ExemplarResponseDTO toResponseDTO(Exemplar exemplar) {
        LivroResumidoDTO livroResumido = new LivroResumidoDTO(
            exemplar.getLivro().getId(),
            exemplar.getLivro().getTitulo()
        );

        UsuarioResumidoDTO usrInclusaoDTO = (exemplar.getUsrInclusao() != null)
            ? new UsuarioResumidoDTO(exemplar.getUsrInclusao().getId(), exemplar.getUsrInclusao().getNomeUsuario())
            : null;

        return new ExemplarResponseDTO(
            exemplar.getId(),
            exemplar.getCodigoExemplar(),
            exemplar.getStatus().toString(),
            livroResumido,
            exemplar.getDtInclusao(),
            usrInclusaoDTO
        );
    }
}