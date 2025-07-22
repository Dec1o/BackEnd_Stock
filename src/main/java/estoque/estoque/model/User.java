package estoque.estoque.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private boolean admin;

    @Column(nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}
