package estoque.estoque.service;

import estoque.estoque.model.User;
import estoque.estoque.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public User create(User user) {
        // Criptografa a senha no momento do cadastro
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return repository.save(user);
    }

    public User update(Long id, User user) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        existing.setNome(user.getNome());
        existing.setEmail(user.getEmail());
        existing.setAdmin(user.isAdmin());
        existing.setCompany(user.getCompany()); // <-- ESSENCIAL PARA EVITAR erro de integridade

        // Sempre recriptografa senha nova
        existing.setSenha(passwordEncoder.encode(user.getSenha()));

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
