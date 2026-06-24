package sistema_notas.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "docente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombres;

    private String apellidos;

    @Column(unique = true)
    private String codigo;

    private String correo;

    private String telefono;

    private String especialidad;

    private Boolean estado;

    // ✅ cascade ajustado: ya no incluye REMOVE,
    // para que el borrado del usuario lo controlemos manualmente
    // en DocenteServiceImpl y evitar conflictos de orden con Hibernate.
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}