package sistema_notas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema_notas.entity.Docente;
import sistema_notas.repository.DocenteRepository;
import sistema_notas.service.DocenteService;

@Service
public class DocenteServiceImpl implements DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Override
    public List<Docente> listarTodos() {

        return docenteRepository.findAll();
    }

    @Override
    public Docente guardar(Docente docente) {

        return docenteRepository.save(docente);
    }

    @Override
    public Docente buscarPorId(Long id) {

        return docenteRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {

        docenteRepository.deleteById(id);
    }

    @Override
    public List<Docente> buscarPorEspecialidad(String especialidad) {

        return docenteRepository.findByEspecialidad(especialidad);
    }

    @Override
    public List<Docente> buscarPorEspecialidadJPQL(String especialidad) {

        return docenteRepository
                .buscarPorEspecialidadJPQL(especialidad);
    }
}