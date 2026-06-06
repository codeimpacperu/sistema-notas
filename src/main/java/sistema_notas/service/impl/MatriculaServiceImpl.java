package sistema_notas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistema_notas.entity.DetalleMatricula;
import sistema_notas.entity.Matricula;
import sistema_notas.repository.MatriculaRepository;
import sistema_notas.service.MatriculaService;

@Service
public class MatriculaServiceImpl implements MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Override
    @Transactional
    public Matricula guardarMatricula(Matricula matricula) {

        for (DetalleMatricula detalle : matricula.getDetalles()) {

            detalle.setMatricula(matricula);
        }

        return matriculaRepository.save(matricula);
    }
}