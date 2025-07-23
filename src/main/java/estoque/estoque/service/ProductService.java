package estoque.estoque.service;

import estoque.estoque.model.Category;
import estoque.estoque.model.Product;
import estoque.estoque.repository.CategoryRepository;
import estoque.estoque.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        Product existing = findById(id);
        existing.setNome(product.getNome());
        existing.setPreco(product.getPreco());
        existing.setCategory(product.getCategory());
        return productRepository.save(existing);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
