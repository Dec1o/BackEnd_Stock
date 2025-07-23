package estoque.estoque.service;

import estoque.estoque.model.Category;
import estoque.estoque.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }

    public Category create(Category category) {
        return repository.save(category);
    }

    public Category update(Long id, Category category) {
        Category existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        existing.setNome(category.getNome());
        existing.setDescricao(category.getDescricao());
        existing.setCompany(category.getCompany());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
