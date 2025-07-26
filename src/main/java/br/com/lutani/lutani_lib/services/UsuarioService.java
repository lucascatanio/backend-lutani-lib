package br.com.lutani.lutani_lib.services;

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

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

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
