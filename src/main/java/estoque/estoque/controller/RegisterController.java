package estoque.estoque.controller;

import estoque.estoque.dto.UserDTO;
import estoque.estoque.model.Company;
import estoque.estoque.model.User;
import estoque.estoque.repository.CompanyRepository;
import estoque.estoque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "*")
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyRepository companyRepository;

    @PostMapping
    public ResponseEntity<User> register(@RequestBody UserDTO dto) {
        // 1) Busca a empresa pelo ID informado
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com ID: " + dto.getCompanyId()));

        // 2) Monta a entidade User
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        user.setAdmin(true);     // Se quiser sempre ADMIN no registro público
        user.setCompany(company); // <— garante que company não seja nulo

        // 3) Salva e retorna 201 Created
        User saved = userService.create(user);
        return ResponseEntity
                .status(201)
                .body(saved);
    }
}
