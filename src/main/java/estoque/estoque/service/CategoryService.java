package estoque.estoque.service;

import estoque.estoque.dto.CategoryDTO;
import estoque.estoque.model.Category;
import estoque.estoque.model.Company;
import estoque.estoque.repository.CategoryRepository;
import estoque.estoque.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final CompanyRepository companyRepository;

    public CategoryService(CategoryRepository repository, CompanyRepository companyRepository) {
        this.repository = repository;
        this.companyRepository = companyRepository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    public Category create(CategoryDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa não encontrada"));

        Category category = new Category();
        category.setNome(dto.getNome());
        category.setDescricao(dto.getDescricao());
        category.setCompany(company);

        return repository.save(category);
    }

    public Category update(Long id, CategoryDTO dto) {
        Category existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa não encontrada"));

        existing.setNome(dto.getNome());
        existing.setDescricao(dto.getDescricao());
        existing.setCompany(company);

        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
        }
        repository.deleteById(id);
    }
}
