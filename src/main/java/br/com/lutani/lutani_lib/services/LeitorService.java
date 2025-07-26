package br.com.lutani.lutani_lib.services;

import org.springframework.stereotype.Service;

import br.com.lutani.lutani_lib.dtos.LeitorResponseDTO;
import br.com.lutani.lutani_lib.dtos.UsuarioResumidoDTO;
import br.com.lutani.lutani_lib.entities.Leitor;
import br.com.lutani.lutani_lib.repositories.LeitorRepository;

@Service
public class LeitorService {

    private final LeitorRepository leitorRepository;

    public LeitorService(LeitorRepository leitorRepository) {
        this.leitorRepository = leitorRepository;
    }

    private LeitorResponseDTO toResponseDTO(Leitor leitor) {
        UsuarioResumidoDTO usrInclusaoDTO = (leitor.getUsrInclusao() != null)
                ? new UsuarioResumidoDTO(leitor.getUsrInclusao().getId(), leitor.getUsrInclusao().getNomeUsuario())
                : null;

        UsuarioResumidoDTO usrAlteracaoDTO = (leitor.getUsrAlteracao() != null)
                ? new UsuarioResumidoDTO(leitor.getUsrAlteracao().getId(), leitor.getUsrAlteracao().getNomeUsuario())
                : null;

        return new LeitorResponseDTO(
                leitor.getId(),
                leitor.getNome(),
                leitor.getCpf(),
                leitor.getEmail(),
                leitor.getTelefone(),
                leitor.getEndereco(),
                leitor.getStatus().toString(),
                leitor.getDtInclusao(),
                leitor.getDtAlteracao(),
                leitor.getIdentidadeTipo(),
                leitor.getComprovResidenciaTipo(),
                usrInclusaoDTO,
                usrAlteracaoDTO
        );
    }
}
