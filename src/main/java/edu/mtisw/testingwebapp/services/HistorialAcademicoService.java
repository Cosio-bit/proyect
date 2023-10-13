package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.repositories.HistorialAcademicoRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static edu.mtisw.testingwebapp.services.OficinaRRHH.convertirFecha;
import static edu.mtisw.testingwebapp.services.OficinaRRHH.stringToList;

@Service
public class HistorialAcademicoService {
    @Autowired
    HistorialAcademicoRepository historialAcademicoRepository;

    public ArrayList<HistorialAcademicoEntity> obtenerHistorialAcademicos(){
        return (ArrayList<HistorialAcademicoEntity>) historialAcademicoRepository.findAll();
    }

    public double calcularPromedioHistorial(HistorialAcademicoEntity academico) {
        List<Integer> notas = academico.getNotas();

        if (notas.isEmpty()) {
            return -1.0; // Retorna -1 si no hay notas disponibles.
        }

        double sumatoria = notas.stream().mapToInt(Integer::intValue).sum();
        return sumatoria / notas.size();
    }
    public HistorialAcademicoEntity guardarHistorialAcademico(Long estudianteID, String notas){
        HistorialAcademicoEntity historialAcademico = new HistorialAcademicoEntity();
        historialAcademico.setNotas(stringToList(notas));
        historialAcademico.setPromedioExamenes(calcularPromedioHistorial(historialAcademico));
        historialAcademico.setNroExamenes(historialAcademico.getNotas().size());
        historialAcademico.setEstudianteID(estudianteID);
        return historialAcademicoRepository.save(historialAcademico);
    }

    public Optional<HistorialAcademicoEntity> obtenerPorId(Long id){
        return historialAcademicoRepository.findById(id);
    }

    public HistorialAcademicoEntity obtenerPorEstudianteId(Long id){
        return historialAcademicoRepository.findHistorialAcademicoByEstudianteID(id);
    }


    public boolean eliminarHistorialAcademico(Long id) {
        try{
            historialAcademicoRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }

    public Optional<HistorialAcademicoEntity> anadirNota(Long id, double nuevaNota) {
        // First, check if the entity with the given id exists
        Optional<HistorialAcademicoEntity> optionalHistorial = historialAcademicoRepository.findById(id);

        if (optionalHistorial.isPresent()) {
            // The entity exists, so let's update its notas (grades)
            HistorialAcademicoEntity historialAcademico = optionalHistorial.get();

            // You can choose how you want to update the grades here. For example, if you want to add the nuevaNota to the existing grades:
            List<Integer> currentNotas = historialAcademico.getNotas();

            // Convert nuevaNota to Integer and add it to the list
            currentNotas.add((int) nuevaNota);

            historialAcademico.setNotas(currentNotas);
            historialAcademico.setPromedioExamenes(calcularPromedioHistorial(historialAcademico));

            // Now, save the updated entity back to the repository
            historialAcademicoRepository.save(historialAcademico);

        } else {
            // Handle the case where the entity with the given id does not exist
            // You can throw an exception or take any appropriate action based on your requirements.
        }
        return optionalHistorial;
    }


    public int getMonthFromExcelDate(String excelDate) {
        try {
            // Split the Excel date string by '/'
            String[] parts = excelDate.split("/");

            if (parts.length >= 2) {
                // Extract the month part (parts[1]) and parse it into an integer

                return Integer.parseInt(parts[1]);

            } else {
                // Handle cases where the input doesn't have the expected format
                return -1; // For example, return -1 in case of an error
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Handle parsing errors or array index out of bounds errors
            return -1; // For example, return -1 in case of an error
        }
    }



    public Optional<HistorialAcademicoEntity> anadirNotaConFecha(Long id, double nuevaNota, String fechaEspecifica) {
        Optional<HistorialAcademicoEntity> optionalHistorial = historialAcademicoRepository.findById(id);
        System.out.println(fechaEspecifica);

        int mes = getMonthFromExcelDate(fechaEspecifica);

        System.out.println(mes);

        if (optionalHistorial.isPresent()) {
            HistorialAcademicoEntity historialAcademico = optionalHistorial.get();

            List<Integer> currentNotas = historialAcademico.getNotas();

            // Calcula el índice del mes de la fecha específicas
            int indexDelMes = mes - 1; // Restamos 1 porque los meses se indexan desde 0

            if (indexDelMes < currentNotas.size()) {
                // Si el índice del mes es válido, actualiza la nota en ese mes
                currentNotas.set(indexDelMes, (int) nuevaNota);
            } else {
                // Si el índice del mes está fuera del rango actual, agrega un nuevo mes con la nota
                currentNotas.add((int) nuevaNota);
            }

            historialAcademico.setNotas(currentNotas);
            historialAcademico.setPromedioExamenes(calcularPromedioHistorial(historialAcademico));

            historialAcademicoRepository.save(historialAcademico);
        } else {
            // Maneja el caso en el que la entidad con el ID dado no existe
            // Puedes lanzar una excepción o tomar cualquier acción apropiada según tus requisitos.
        }
        return optionalHistorial;
    }





}