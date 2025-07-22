package estoque.estoque.service;

import estoque.estoque.dto.CompanyDTO;
import estoque.estoque.model.Company;
import estoque.estoque.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyDTO create(CompanyDTO dto) {
        if (companyRepository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Empresa já cadastrada.");
        }

        Company company = new Company();
        company.setNome(dto.getNome());

        company = companyRepository.save(company);

        return new CompanyDTO(company.getId(), company.getNome());
    }

    public List<CompanyDTO> listAll() {
        return companyRepository.findAll()
                .stream()
                .map(c -> new CompanyDTO(c.getId(), c.getNome()))
                .collect(Collectors.toList());
    }

    public CompanyDTO update(Long id, CompanyDTO dto) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada."));

        company.setNome(dto.getNome());
        company = companyRepository.save(company);

        return new CompanyDTO(company.getId(), company.getNome());
    }

    public void delete(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new RuntimeException("Empresa não encontrada.");
        }

        companyRepository.deleteById(id);
    }
}
