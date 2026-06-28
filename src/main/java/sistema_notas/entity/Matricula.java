package sistema_notas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "matricula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    private String estado;

    private String periodo;

    @ManyToOne
    @JoinColumn(name = "alumno_id")
    private Alumno alumno;

    @OneToMany(
            mappedBy = "matricula",
            cascade = CascadeType.ALL
    )
    private List<DetalleMatricula> detalles;
}