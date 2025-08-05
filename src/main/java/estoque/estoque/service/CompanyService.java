package estoque.estoque.service;

import estoque.estoque.dto.CompanyDTO;
import estoque.estoque.model.Company;
import estoque.estoque.model.User;
import estoque.estoque.repository.CompanyRepository;
import estoque.estoque.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    public CompanyDTO createWithUserValidation(CompanyDTO dto, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        if (!user.isAdmin()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas administradores podem criar empresas.");
        }

        if (user.getCompany() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Você já possui uma empresa cadastrada.");
        }

        if (companyRepository.existsByNome(dto.getNome())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Empresa já cadastrada.");
        }

        Company company = new Company();
        company.setNome(dto.getNome());
        company = companyRepository.save(company);

        user.setCompany(company);
        userRepository.save(user);

        return new CompanyDTO(company.getId(), company.getNome());
    }

    public List<CompanyDTO> listOnlyUserCompany(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        if (user.getCompany() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Você ainda não possui uma empresa cadastrada.");
        }

        return List.of(new CompanyDTO(
                user.getCompany().getId(),
                user.getCompany().getNome()
        ));
    }

    public CompanyDTO updateWithUserValidation(Long id, CompanyDTO dto, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        if (user.getCompany() == null || !user.getCompany().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para editar esta empresa.");
        }

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));

        company.setNome(dto.getNome());
        company = companyRepository.save(company);

        return new CompanyDTO(company.getId(), company.getNome());
    }

    public void deleteWithUserValidation(Long id, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        Company userCompany = user.getCompany();
        if (userCompany == null || !userCompany.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para excluir esta empresa.");
        }

        if (!companyRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada.");
        }

        companyRepository.deleteById(id);

        // Desvincula a empresa do usuário
        user.setCompany(null);
        userRepository.save(user);
    }

    private User getAuthenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado."));
    }
}
