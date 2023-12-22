package edu.mtisw.testingwebapp.services;


import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;

import edu.mtisw.testingwebapp.repositories.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

//import static edu.mtisw.testingwebapp.services.OficinaRRHH.convertirFecha;

@Service
public class ProfesorService {
    @Autowired
    ProfesorRepository profesorRepository;
    @Autowired
    ProjectorService projectorService;
    @Autowired
    PrestamoService prestamoService;

        @Autowired
        public ProfesorService(ProfesorRepository profesorRepository) {
            this.profesorRepository = profesorRepository;
        }

    public ArrayList<ProfesorEntity> obtenerProfesores() {
        return (ArrayList<ProfesorEntity>) profesorRepository.findAll();
    }
    public ArrayList<String> obtenerRuts() {
        return (ArrayList<String>) profesorRepository.findAllRuts();
    }


    public ProfesorEntity guardarProfesor(String rut, String nombre, String apellido) {
        // Crea un objeto ProfesorEntity y asigna los valores obtenidos de los parámetros
        ProfesorEntity profesor = new ProfesorEntity();
        profesor.setRut(rut);
        profesor.setNombre(nombre);
        profesor.setApellido(apellido);
        profesor.setInfracciones(0);
        profesor.setAtrasos(0);
        return profesorRepository.save(profesor);
    }
    public Optional<ProfesorEntity> obtenerPorId(Long id) {
        return profesorRepository.findById(id);
    }
    public Optional<Optional<ProfesorEntity>>  obtenerPorRut(String rut){
        System.out.println("rut:"+rut);
        return Optional.ofNullable(profesorRepository.findByRut(rut));}

    public void updateInfraccion(List<ProfesorEntity> profesores) {
        for (int i = 0; i != profesores.size(); i++) {
            List<PrestamoEntity> prestamos = prestamoService.obtenerPrestamosPorProjectorID(profesores.get(i).getId().toString());
            int infracciones = 0;
            int atrasos = 0;
            for (int j = 0; j != prestamos.size(); j++) {
                if (prestamos.get(j).getEstadoDanado().equals("si")) {
                    infracciones++;
                }
            }
            profesores.get(i).setAtrasos(atrasos);
            profesores.get(i).setInfracciones(infracciones);
            //si las infracciones son mayores a 3 se borra el profesor
            if (infracciones >= 3) {
                profesorRepository.deleteById(profesores.get(i).getId());
            } else {
                profesorRepository.save(profesores.get(i));
            }
        }
    }



}


