package estoque.estoque.controller;

import estoque.estoque.dto.CompanyDTO;
import estoque.estoque.service.CompanyService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@CrossOrigin(origins = "*")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public CompanyDTO create(@RequestBody CompanyDTO dto, Authentication authentication) {
        return companyService.createWithUserValidation(dto, authentication);
    }

    @GetMapping
    public List<CompanyDTO> listAll(Authentication authentication) {
        return companyService.listOnlyUserCompany(authentication);
    }

    @PutMapping("/{id}")
    public CompanyDTO update(@PathVariable Long id,
                             @RequestBody CompanyDTO dto,
                             Authentication authentication) {
        return companyService.updateWithUserValidation(id, dto, authentication);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication authentication) {
        companyService.deleteWithUserValidation(id, authentication);
    }
}
