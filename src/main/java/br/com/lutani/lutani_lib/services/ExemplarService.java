package br.com.lutani.lutani_lib.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.lutani.lutani_lib.dtos.ExemplarRequestDTO;
import br.com.lutani.lutani_lib.dtos.ExemplarResponseDTO;
import br.com.lutani.lutani_lib.dtos.LivroResumidoDTO;
import br.com.lutani.lutani_lib.dtos.UsuarioResumidoDTO;
import br.com.lutani.lutani_lib.entities.Exemplar;
import br.com.lutani.lutani_lib.entities.Livro;
import br.com.lutani.lutani_lib.enums.StatusExemplar;
import br.com.lutani.lutani_lib.repositories.ExemplarRepository;
import br.com.lutani.lutani_lib.repositories.LivroRepository;
import jakarta.transaction.Transactional;

@Service
public class ExemplarService {

    private final ExemplarRepository exemplarRepository;
    private final LivroRepository livroRepository;

    public ExemplarService(ExemplarRepository exemplarRepository, LivroRepository livroRepository) {
        this.exemplarRepository = exemplarRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public ExemplarResponseDTO criarExemplar(ExemplarRequestDTO requestDTO) {
        if (exemplarRepository.existsByCodigoExemplar(requestDTO.codigoExemplar())) {
            throw new RuntimeException("Já existe um exemplar com este código.");
        }

        Livro livro = livroRepository.findById(requestDTO.livroId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado para associar ao exemplar."));

        Exemplar novoExemplar = toEntity(requestDTO, livro);
        Exemplar exemplarSalvo = exemplarRepository.saveAndFlush(novoExemplar);
        return toResponseDTO(exemplarSalvo);
    }

    public List<ExemplarResponseDTO> listarTodos() {
        return exemplarRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

     public ExemplarResponseDTO buscarPorId(UUID id) {
        Exemplar exemplar = exemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado com o ID: " + id));
        return toResponseDTO(exemplar);
    }

    @Transactional
    public ExemplarResponseDTO atualizarExemplar(UUID id, ExemplarRequestDTO requestDTO) {
        Exemplar exemplarExistente = exemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado com o ID: " + id));

        exemplarExistente.setStatus(StatusExemplar.valueOf(requestDTO.status()));

        Exemplar exemplarAtualizado = exemplarRepository.saveAndFlush(exemplarExistente);
        return toResponseDTO(exemplarAtualizado);
    }

    @Transactional
    public void descartarExemplar(UUID id) {
        Exemplar exemplar = exemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado com o ID: " + id));
        
        exemplar.setStatus(StatusExemplar.DESCARTADO);
        exemplarRepository.saveAndFlush(exemplar);
    }

    private Exemplar toEntity(ExemplarRequestDTO dto, Livro livro) {
        Exemplar exemplar = new Exemplar();
        exemplar.setLivro(livro);
        exemplar.setCodigoExemplar(dto.codigoExemplar());
        exemplar.setStatus(StatusExemplar.valueOf(dto.status()));
        return exemplar;
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
