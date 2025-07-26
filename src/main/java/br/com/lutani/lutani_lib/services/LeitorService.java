package br.com.lutani.lutani_lib.services;

import org.springframework.stereotype.Service;

import br.com.lutani.lutani_lib.dtos.LeitorRequestDTO;
import br.com.lutani.lutani_lib.dtos.LeitorResponseDTO;
import br.com.lutani.lutani_lib.dtos.UsuarioResumidoDTO;
import br.com.lutani.lutani_lib.entities.Leitor;
import br.com.lutani.lutani_lib.enums.StatusLeitor;
import br.com.lutani.lutani_lib.repositories.LeitorRepository;
import jakarta.transaction.Transactional;

@Service
public class LeitorService {

    private final LeitorRepository leitorRepository;

    public LeitorService(LeitorRepository leitorRepository) {
        this.leitorRepository = leitorRepository;
    }

    @Transactional
    public LeitorResponseDTO criarLeitor(LeitorRequestDTO requestDTO) {
        if (leitorRepository.existsByCpf(requestDTO.cpf())) {
            throw new RuntimeException("CPF já cadastrado no sistema.");
        }
        if (leitorRepository.existsByEmail(requestDTO.email())) {
            throw new RuntimeException("E-mail já cadastrado no sistema.");
        }

        Leitor novoLeitor = toEntity(requestDTO);

        Leitor leitorSalvo = leitorRepository.saveAndFlush(novoLeitor);

        return toResponseDTO(leitorSalvo);
    }

    private Leitor toEntity(LeitorRequestDTO dto) {
        Leitor leitor = new Leitor();
        leitor.setNome(dto.nome());
        leitor.setCpf(dto.cpf());
        leitor.setEmail(dto.email());
        leitor.setTelefone(dto.telefone());
        leitor.setEndereco(dto.endereco());
        leitor.setStatus(StatusLeitor.ATIVO);
        return leitor;
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
