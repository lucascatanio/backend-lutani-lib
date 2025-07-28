package br.com.lutani.lutani_lib.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.lutani.lutani_lib.dtos.UsuarioRequestDTO;
import br.com.lutani.lutani_lib.dtos.UsuarioResponseDTO;
import br.com.lutani.lutani_lib.entities.NivelAcesso;
import br.com.lutani.lutani_lib.entities.Usuario;
import br.com.lutani.lutani_lib.repositories.NivelAcessoRepository;
import br.com.lutani.lutani_lib.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final NivelAcessoRepository nivelAcessoRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, NivelAcessoRepository nivelAcessoRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.nivelAcessoRepository = nivelAcessoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioResponseDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));

        return toDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO requestDTO) {
        if (usuarioRepository.findByNomeUsuario(requestDTO.nomeUsuario()).isPresent()) {
            throw new RuntimeException("Nome de usuário já existe.");
        }

        NivelAcesso nivelAcesso = nivelAcessoRepository.findById(requestDTO.nivelAcessoId())
                .orElseThrow(() -> new RuntimeException("Nivel de acesso não encontrado."));

        String senhaCriptografada = passwordEncoder.encode(requestDTO.senha());

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(requestDTO.nome());
        novoUsuario.setNomeUsuario(requestDTO.nomeUsuario());
        novoUsuario.setSenhaHash(senhaCriptografada);
        novoUsuario.setNivelAcesso(nivelAcesso);

        Usuario usuarioSalvo = usuarioRepository.saveAndFlush(novoUsuario);

        return toDTO(usuarioSalvo);
    }

    private UsuarioResponseDTO toDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getNomeUsuario(),
                new br.com.lutani.lutani_lib.dtos.NivelAcessoResponseDTO(
                        usuario.getNivelAcesso().getId(),
                        usuario.getNivelAcesso().getNome()
                )
        );
    }
}
