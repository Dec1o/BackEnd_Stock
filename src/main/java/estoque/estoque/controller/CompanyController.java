package estoque.estoque.controller;

import estoque.estoque.dto.CompanyDTO;
import estoque.estoque.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public CompanyDTO create(@RequestBody CompanyDTO dto) {
        return companyService.create(dto);
    }

    @GetMapping
    public List<CompanyDTO> listAll() {
        return companyService.listAll();
    }

    @PutMapping("/{id}")
    public CompanyDTO update(@PathVariable Long id, @RequestBody CompanyDTO dto) {
        return companyService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        companyService.delete(id);
    }
}
