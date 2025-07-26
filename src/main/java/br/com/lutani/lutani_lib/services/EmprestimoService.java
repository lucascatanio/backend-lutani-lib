package br.com.lutani.lutani_lib.services;

import org.springframework.stereotype.Service;

import br.com.lutani.lutani_lib.dtos.EmprestimoResponseDTO;
import br.com.lutani.lutani_lib.dtos.ExemplarResumidoDTO;
import br.com.lutani.lutani_lib.dtos.LeitorResumidoDTO;
import br.com.lutani.lutani_lib.dtos.UsuarioResumidoDTO;
import br.com.lutani.lutani_lib.entities.Emprestimo;
import br.com.lutani.lutani_lib.repositories.EmprestimoRepository;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
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