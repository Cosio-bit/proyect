package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.entities.ProyectorEntity;
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

import static edu.mtisw.testingwebapp.services.OficinaRRHH.convertirFecha;

@Service
public class ProfesorService {
    @Autowired
    ProfesorRepository estudianteRepository;
    @Autowired
    ProyectorService historialArancelService;
    @Autowired
    HistorialAcademicoService historialAcademicoService;
    @Autowired
    PrestamoService detallePagoService;

        @Autowired
        public ProfesorService(ProfesorRepository estudianteRepository) {
            this.estudianteRepository = estudianteRepository;
        }

    public ArrayList<EstudianteEntity> obtenerEstudiantes() {
        return (ArrayList<EstudianteEntity>) estudianteRepository.findAll();
    }
    public ArrayList<String> obtenerRuts() {
        return (ArrayList<String>) estudianteRepository.findAllRuts();
    }
    public EstudianteEntity manageGuardar(String rut, String nombre, String apellido, String fechaNacimiento, String tipoColegio, String nombreColegio, String AnnoEgreso, String tipoPago, String cuotasPactadas, String notas){
            EstudianteEntity profesor = guardarEstudiante(rut, nombre, apellido, fechaNacimiento, tipoColegio, nombreColegio, AnnoEgreso);
            HistorialAcademicoEntity historialAcademico = historialAcademicoService.guardarHistorialAcademico( profesor.getId(), notas);
            ProyectorEntity historialArancel = historialArancelService.guardarHistorialArancel(profesor.getId(), tipoColegio, AnnoEgreso, tipoPago, cuotasPactadas);
            List<PrestamoEntity> detallePagoEntities = detallePagoService.guardarDetallesPagos(historialArancel.getId(), historialArancel.getCuotasPactadas(), historialArancel.getMontoTotal());
            int size = detallePagoEntities.size();
            System.out.println(size);
        return profesor;
    }
    public EstudianteEntity guardarEstudiante(String rut, String nombre, String apellido, String fechaNacimiento, String tipoColegio, String nombreColegio, String AnnoEgreso) {
        // Crea un objeto EstudianteEntity y asigna los valores obtenidos de los parámetros
        EstudianteEntity profesor = new EstudianteEntity();
        profesor.setRut(rut);
        profesor.setNombre(nombre);
        profesor.setApellido(apellido);
        profesor.setFechaNacimiento(convertirFecha(fechaNacimiento));
        profesor.setTipoColegio(tipoColegio);
        profesor.setNombreColegio(nombreColegio);
        profesor.setAnnoEgreso(convertirFecha(AnnoEgreso));
        return estudianteRepository.save(profesor);
    }
    public Optional<EstudianteEntity> obtenerPorId(Long id) {
        return estudianteRepository.findById(id);
    }
    public Optional<EstudianteEntity>  obtenerPorRut(String rut){
        System.out.println("rut:"+rut);
        return Optional.ofNullable(estudianteRepository.findByRut(rut));}
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
    public void cuotaUpdate(List<EstudianteEntity> estudianteEntities){
        HistorialAcademicoEntity historialAcademico = historialAcademicoService.obtenerPorEstudianteId(estudianteEntities.get(0).getId());
        double promedio = historialAcademicoService.calcularPromedioHistorial(historialAcademico);
        Optional<ProyectorEntity> historialArancel = historialArancelService.obtenerPorId(estudianteEntities.get(0).getId());
        if(historialArancel.get().getTipoPago().equals("cuotas")){
        List<PrestamoEntity> detallesPagos = detallePagoService.findbynotpagado(historialArancel.get().getId());
        detallePagoService.updateDetallesPagos(detallesPagos, promedio);}
        else{
            List<PrestamoEntity> detallesPagos = detallePagoService.detallePagoRepository.findAll();
            detallePagoService.updateDetallesPagos(detallesPagos, promedio);}
        }

    public void agregarNotasAHistorial(String nombreArchivo) {
        // Obtén los datos del archivo Excel
        List<List<String>> excelData = ExcelImporterToList(nombreArchivo);
        // Recorre los datos del Excel
        for (List<String> row : excelData) {
            if (row.size() >= 3) { // Asegúrate de que haya al menos tres columnas (RUT, fecha, nota)
                String rut = row.get(0);
                String fechaStr = row.get(1);
                String notaStr = row.get(2);

                try {
                    double nota = Double.parseDouble(notaStr);

                    // Busca al profesor por su RUT en la base de datos
                    Optional<EstudianteEntity> profesor = obtenerPorRut(rut);


                    if (profesor.isPresent()) {
                        // Obtén el historial académico del profesor
                        HistorialAcademicoEntity historialAcademico = historialAcademicoService.obtenerPorEstudianteId(profesor.get().getId());

                        // Guarda el historial académico actualizado en la base de datos
                        historialAcademicoService.anadirNotaConFecha(historialAcademico.getId(),nota, fechaStr);

                    }
                    // Si el profesor no se encuentra en la base de datos, simplemente lo omite.

                } catch (DateTimeParseException | NumberFormatException e) {

                    // Maneja los errores de formato de fecha o nota
                    // Puedes imprimir un mensaje de error o tomar otra acción adecuada.
                }
            }
        }
    }


}
