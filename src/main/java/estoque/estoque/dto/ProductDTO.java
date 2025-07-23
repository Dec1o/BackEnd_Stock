package estoque.estoque.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String nome;
    private Double preco;
    private Long categoryId;
}
