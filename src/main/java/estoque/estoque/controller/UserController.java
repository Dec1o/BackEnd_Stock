package estoque.estoque.controller;

import estoque.estoque.dto.UserDTO;
import estoque.estoque.model.Company;
import estoque.estoque.model.User;
import estoque.estoque.repository.CompanyRepository;
import estoque.estoque.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final CompanyRepository companyRepository;

    public UserController(UserService service, CompanyRepository companyRepository) {
        this.service = service;
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @PostMapping
    public User create(@RequestBody UserDTO dto) {
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setAdmin(dto.isAdmin());

        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
            user.setCompany(company);
        } else {
            user.setCompany(null);
        }

        return service.create(user);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody UserDTO dto) {
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setAdmin(dto.isAdmin());

        if (dto.getCompanyId() != null) {
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
            user.setCompany(company);
        } else {
            user.setCompany(null);
        }

        return service.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
