package estoque.estoque.controller;

import estoque.estoque.dto.CategoryDTO;
import estoque.estoque.model.Category;
import estoque.estoque.model.Company;
import estoque.estoque.repository.CompanyRepository;
import estoque.estoque.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;
    private final CompanyRepository companyRepository;

    public CategoryController(CategoryService service, CompanyRepository companyRepository) {
        this.service = service;
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public List<Category> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    @PostMapping
    public Category create(@RequestBody CategoryDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Category category = new Category();
        category.setNome(dto.getNome());
        category.setDescricao(dto.getDescricao());
        category.setCompany(company);

        return service.create(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        Category category = new Category();
        category.setNome(dto.getNome());
        category.setDescricao(dto.getDescricao());
        category.setCompany(company);

        return service.update(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
