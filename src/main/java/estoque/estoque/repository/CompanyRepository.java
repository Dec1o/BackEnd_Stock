package estoque.estoque.repository;

import estoque.estoque.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByNome(String nome);
}
