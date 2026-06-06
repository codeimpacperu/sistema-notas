package sistema_notas.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_matricula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleMatricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String curso;

    private String ciclo;

    private Double nota;

    @ManyToOne
    @JoinColumn(name = "matricula_id")
    private Matricula matricula;
}