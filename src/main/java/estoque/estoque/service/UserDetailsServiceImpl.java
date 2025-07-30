package estoque.estoque.service;

import estoque.estoque.model.User;
import estoque.estoque.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuário não encontrado com o e-mail: " + email
                ));

        // Converte sua entidade User em UserDetails do Spring
        return org.springframework.security.core.userdetails.User.builder()
                .username(u.getEmail())
                .password(u.getSenha()) // já vem criptografada pelo UserService
                .authorities(mapRoles(u.isAdmin()))
                .build();
    }

    /**
     * Mapeia flag de admin para authorities do Spring Security.
     */
    private List<SimpleGrantedAuthority> mapRoles(boolean isAdmin) {
        if (isAdmin) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }
}
