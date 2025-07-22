package estoque.estoque.service;

import estoque.estoque.model.User;
import estoque.estoque.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public User create(User user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return repository.save(user);
    }

    public User update(Long id, User user) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        existing.setNome(user.getNome());
        existing.setEmail(user.getEmail());
        existing.setAdmin(user.isAdmin());
        existing.setCompany(user.getCompany());

        // só altera a senha se ela foi alterada (opcional)
        if (user.getSenha() != null && !user.getSenha().isBlank()) {
            existing.setSenha(passwordEncoder.encode(user.getSenha()));
        }

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
