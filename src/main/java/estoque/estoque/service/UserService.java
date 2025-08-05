package estoque.estoque.service;

import estoque.estoque.dto.UserDTO;
import estoque.estoque.model.Company;
import estoque.estoque.model.User;
import estoque.estoque.repository.CompanyRepository;
import estoque.estoque.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User create(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já está cadastrado.");
        }
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return repository.save(user);
    }

    public User update(Long id, User user) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        existing.setNome(user.getNome());
        existing.setEmail(user.getEmail());
        existing.setAdmin(user.isAdmin());
        existing.setCompany(user.getCompany());
        existing.setSenha(passwordEncoder.encode(user.getSenha()));

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public User registerAdmin(UserDTO dto) {
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setAdmin(true);
        user.setCompany(null);
        return create(user);
    }

    public User createNonAdminUser(UserDTO dto, String adminEmail) {
        User admin = repository.findByEmail(adminEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado."));

        if (!admin.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas administradores podem criar usuários.");
        }

        Company company = admin.getCompany();
        if (company == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Administrador não está vinculado a uma empresa.");
        }

        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setAdmin(false);
        user.setCompany(company);

        return create(user);
    }

    public User updateWithCompany(Long id, UserDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa não encontrada."));

        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setAdmin(dto.isAdmin());
        user.setCompany(company);

        return update(id, user);
    }
}
