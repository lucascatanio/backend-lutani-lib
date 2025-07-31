package br.com.lutani.lutani_lib.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lutani.lutani_lib.dtos.EmprestimoRequestDTO;
import br.com.lutani.lutani_lib.dtos.EmprestimoResponseDTO;
import br.com.lutani.lutani_lib.dtos.ExemplarResumidoDTO;
import br.com.lutani.lutani_lib.dtos.LeitorResumidoDTO;
import br.com.lutani.lutani_lib.dtos.UsuarioResumidoDTO;
import br.com.lutani.lutani_lib.entities.Emprestimo;
import br.com.lutani.lutani_lib.entities.Exemplar;
import br.com.lutani.lutani_lib.entities.Leitor;
import br.com.lutani.lutani_lib.enums.StatusEmprestimo;
import br.com.lutani.lutani_lib.enums.StatusExemplar;
import br.com.lutani.lutani_lib.enums.StatusLeitor;
import br.com.lutani.lutani_lib.repositories.EmprestimoRepository;
import br.com.lutani.lutani_lib.repositories.ExemplarRepository;
import br.com.lutani.lutani_lib.repositories.LeitorRepository;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final LeitorRepository leitorRepository;
    private final ExemplarRepository exemplarRepository;
    private static final int DIAS_PARA_DEVOLUCAO = 14;
    private static final int MAX_EMPRESTIMOS_ATIVOS = 3;
    private static final int DIAS_ANTECEDENCIA_RENOVACAO = 3;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, LeitorRepository leitorRepository, ExemplarRepository exemplarRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.leitorRepository = leitorRepository;
        this.exemplarRepository = exemplarRepository;
    }

    @Transactional
    public EmprestimoResponseDTO realizarEmprestimo(EmprestimoRequestDTO requestDTO) {
        Exemplar exemplar = exemplarRepository.findByCodigoExemplar(requestDTO.codigoExemplar())
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado."));

        if (exemplar.getStatus() != StatusExemplar.DISPONIVEL) {
            throw new RuntimeException("Este exemplar não está disponível para empréstimo.");
        }

        Leitor leitor = leitorRepository.findById(requestDTO.leitorId())
                .orElseThrow(() -> new RuntimeException("Leitor não encontrado."));

        if (leitor.getStatus() != StatusLeitor.ATIVO) {
            throw new RuntimeException("Leitor não está ativo. Status atual: " + leitor.getStatus());
        }

        boolean temPendencias = emprestimoRepository.existsByLeitorIdAndStatus(leitor.getId(), StatusEmprestimo.ATRASADO);
        if (temPendencias) {
            throw new RuntimeException("Leitor possui empréstimos com devolução atrasada.");
        }

        long emprestimosAtivos = emprestimoRepository.countByLeitorIdAndStatus(leitor.getId(), StatusEmprestimo.ATIVO);
        if (emprestimosAtivos >= MAX_EMPRESTIMOS_ATIVOS) {
            throw new RuntimeException("Leitor atingiui o número máximo de empréstimos simultâneos (" + MAX_EMPRESTIMOS_ATIVOS + ").");
        }

        Emprestimo novoEmprestimo = new Emprestimo();
        novoEmprestimo.setExemplar(exemplar);
        novoEmprestimo.setLeitor(leitor);
        novoEmprestimo.setDtEmprestimo(Instant.now());
        novoEmprestimo.setDtVencimento(Instant.now().plus(DIAS_PARA_DEVOLUCAO, ChronoUnit.DAYS));
        novoEmprestimo.setStatus(StatusEmprestimo.ATIVO);

        exemplar.setStatus(StatusExemplar.EMPRESTADO);
        exemplarRepository.save(exemplar);

        Emprestimo emprestimoSalvo = emprestimoRepository.saveAndFlush(novoEmprestimo);

        return toResponseDTO(emprestimoSalvo);
    }

    @Transactional
    public EmprestimoResponseDTO realizarDevolucao(String codigoExemplar) {
        Exemplar exemplar = exemplarRepository.findByCodigoExemplar(codigoExemplar)
                .orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));

        Emprestimo emprestimo = emprestimoRepository.findByExemplarIdAndStatus(exemplar.getId(), StatusEmprestimo.ATIVO)
                .orElseThrow(() -> new RuntimeException("Não há empréstimo ativo para este exemplar"));

        emprestimo.setStatus(StatusEmprestimo.DEVOLVIDO);
        emprestimo.setDtDevolucao(Instant.now());

        exemplar.setStatus(StatusExemplar.DISPONIVEL);

        exemplarRepository.save(exemplar);
        Emprestimo emprestimoSalvo = emprestimoRepository.saveAndFlush(emprestimo);

        return toResponseDTO(emprestimoSalvo);
    }

    @Transactional
    public EmprestimoResponseDTO renovarEmprestimo(UUID emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        if (emprestimo.getStatus() == StatusEmprestimo.DEVOLVIDO) {
            throw new RuntimeException("Não é possível renovar um empréstimo que já foi devolvido.");
        }
        if (emprestimo.getStatus() == StatusEmprestimo.ATRASADO) {
            throw new RuntimeException("Não é possível renovar um empréstimo com a devolução atrasada.");
        }

        long diasParaVencimento = ChronoUnit.DAYS.between(Instant.now(), emprestimo.getDtVencimento());
        if (diasParaVencimento > DIAS_ANTECEDENCIA_RENOVACAO) {
            throw new RuntimeException("A renovação só é permitida " + DIAS_ANTECEDENCIA_RENOVACAO + " dias antes do vencimento.");
        }

        Instant novaDataVencimento = emprestimo.getDtVencimento().plus(DIAS_PARA_DEVOLUCAO, ChronoUnit.DAYS);
        emprestimo.setDtVencimento(novaDataVencimento);

        Emprestimo emprestimoRenovado = emprestimoRepository.saveAndFlush(emprestimo);

        return toResponseDTO(emprestimoRenovado);
    }

    public List<EmprestimoResponseDTO> listarAtivos() {
        List<Emprestimo> emprestimosAtivos = emprestimoRepository.findByStatus(StatusEmprestimo.ATIVO);

        return emprestimosAtivos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EmprestimoResponseDTO> listarAtrasados() {
        List<Emprestimo> emprestimosAtrasados = emprestimoRepository.findByStatus(StatusEmprestimo.ATRASADO);

        return emprestimosAtrasados.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EmprestimoResponseDTO> listarDevolvidos() {
        List<Emprestimo> emprestimosDevolvidos = emprestimoRepository.findByStatus(StatusEmprestimo.DEVOLVIDO);

        return emprestimosDevolvidos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private EmprestimoResponseDTO toResponseDTO(Emprestimo emprestimo) {
        LeitorResumidoDTO leitor = new LeitorResumidoDTO(
                emprestimo.getLeitor().getId(),
                emprestimo.getLeitor().getNome()
        );

        ExemplarResumidoDTO exemplar = new ExemplarResumidoDTO(
                emprestimo.getExemplar().getId(),
                emprestimo.getExemplar().getCodigoExemplar(),
                emprestimo.getExemplar().getLivro().getTitulo()
        );

        UsuarioResumidoDTO usrInclusaoDTO = (emprestimo.getUsrInclusao() != null)
                ? new UsuarioResumidoDTO(emprestimo.getUsrInclusao().getId(), emprestimo.getUsrInclusao().getNomeUsuario())
                : null;

        return new EmprestimoResponseDTO(
                emprestimo.getId(),
                leitor,
                exemplar,
                emprestimo.getStatus().toString(),
                emprestimo.getDtEmprestimo(),
                emprestimo.getDtVencimento(),
                emprestimo.getDtDevolucao(),
                usrInclusaoDTO
        );
    }
}
