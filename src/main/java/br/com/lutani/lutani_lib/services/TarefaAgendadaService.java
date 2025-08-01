package br.com.lutani.lutani_lib.services;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.lutani.lutani_lib.entities.Emprestimo;
import br.com.lutani.lutani_lib.enums.StatusEmprestimo;
import br.com.lutani.lutani_lib.repositories.EmprestimoRepository;

@Service
public class TarefaAgendadaService {

    private static final Logger logger = LoggerFactory.getLogger(TarefaAgendadaService.class);

    private final EmprestimoRepository emprestimoRepository;

    public TarefaAgendadaService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void verificarEAtualizarEmprestimosAtrasados() {
        logger.info("Iniciando verificação de empréstimos atrasados...");

        List<Emprestimo> emprestimosParaAtualizar = emprestimoRepository.findEmprestimosAtivosVencidos(Instant.now());

        if (emprestimosParaAtualizar.isEmpty()) {
            logger.info("Nenhum empréstimo atrasado encontrado.");
            return;
        }

        for (Emprestimo emprestimo : emprestimosParaAtualizar) {
            emprestimo.setStatus(StatusEmprestimo.ATRASADO);
        }

        emprestimoRepository.saveAllAndFlush(emprestimosParaAtualizar);

        logger.info("{} empréstimos foram atualizados para o status ATRASADO.", emprestimosParaAtualizar.size());
    }
}
