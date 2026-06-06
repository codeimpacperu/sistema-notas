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
}