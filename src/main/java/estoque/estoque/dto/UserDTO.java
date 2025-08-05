package estoque.estoque.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String nome;
    private String email;
    private String senha;
    private boolean admin;
    private Long companyId; // ← será ignorado no POST /users, mas pode ser usado no PUT
}
