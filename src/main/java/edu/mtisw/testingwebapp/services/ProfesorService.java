package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.repositories.ProfesorRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.format.DateTimeParseException;
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
        return profesorRepository.save(profesor);
    }
    public Optional<ProfesorEntity> obtenerPorId(Long id) {
        return profesorRepository.findById(id);
    }
    public Optional<Optional<ProfesorEntity>>  obtenerPorRut(String rut){
        System.out.println("rut:"+rut);
        return Optional.ofNullable(profesorRepository.findByRut(rut));}



}




  /*



  public void cuotaUpdate(List<ProfesorEntity> profesorEntities){
        List<PrestamoEntity> detallesPagos = prestamoService.findbynotpagado(projector.get().getId());
        prestamoService.updateDetallesPagos(detallesPagos, promedio);}
        else{
            List<PrestamoEntity> detallesPagos = prestamoService.prestamoRepository.findAll();
            prestamoService.updateDetallesPagos(detallesPagos, promedio);}
        
    public List<List<String>> ExcelImporterToList(String nombre) {
        String fileName = nombre +".xlsx"; // Relative path to the file
        List<List<String>> dataList = new ArrayList<>();
        DataFormatter formateador = new DataFormatter();

        try (FileInputStream fis = new FileInputStream(new File(fileName));
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Get the first sheet in the Excel file
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate through rows and columns to read data and store in a list
            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Iterator<Cell> cellIterator = row.cellIterator(); cellIterator.hasNext();) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            rowData.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            rowData.add(formateador.formatCellValue(cell));
                            break;
                        case BOOLEAN:
                            rowData.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        default:
                            rowData.add(""); // Add an empty string for empty cells
                    }
                }
                dataList.add(rowData); // Add the row data to the list
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }
*/