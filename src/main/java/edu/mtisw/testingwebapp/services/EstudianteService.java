package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.DetallePagoEntity;
import edu.mtisw.testingwebapp.entities.EstudianteEntity;
import edu.mtisw.testingwebapp.entities.HistorialAcademicoEntity;
import edu.mtisw.testingwebapp.entities.HistorialArancelEntity;
import edu.mtisw.testingwebapp.repositories.EstudianteRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static edu.mtisw.testingwebapp.services.OficinaRRHH.convertirFecha;

@Service
public class EstudianteService {
    @Autowired
    EstudianteRepository estudianteRepository;
    @Autowired
    HistorialArancelService historialArancelService;
    @Autowired
    HistorialAcademicoService historialAcademicoService;
    @Autowired
    DetallePagoService detallePagoService;

    public ArrayList<EstudianteEntity> obtenerEstudiantes() {
        return (ArrayList<EstudianteEntity>) estudianteRepository.findAll();
    }

    public ArrayList<String> obtenerRuts() {
        return (ArrayList<String>) estudianteRepository.findAllRuts();
    }



    public EstudianteEntity manageGuardar(String rut, String nombre, String apellido, String fechaNacimiento, String tipoColegio, String nombreColegio, String AnnoEgreso, String tipoPago, String cuotasPactadas, String notas, String periodoInscripcion){
        EstudianteEntity estudiante = guardarEstudiante(rut, nombre, apellido, fechaNacimiento, tipoColegio, nombreColegio, AnnoEgreso, tipoPago, cuotasPactadas, notas, periodoInscripcion);
        HistorialAcademicoEntity historialAcademico = historialAcademicoService.guardarHistorialAcademico( estudiante.getId(), notas);
        HistorialArancelEntity historialArancel = historialArancelService.guardarHistorialArancel(estudiante.getId(), notas, tipoColegio, AnnoEgreso, tipoPago, cuotasPactadas);
        detallePagoService.guardarDetallesPagos(historialArancel.getId(), historialArancel.getCuotasPactadas(), historialArancel.getMontoTotal());
        return estudiante;
    }



    public EstudianteEntity guardarEstudiante(String rut, String nombre, String apellido, String fechaNacimiento, String tipoColegio, String nombreColegio, String AnnoEgreso, String tipoPago, String cuotasPactadas, String notas, String periodoInscripcion) {
        // Crea un objeto EstudianteEntity y asigna los valores obtenidos de los parámetros
        EstudianteEntity estudiante = new EstudianteEntity();
        estudiante.setRut(rut);
        estudiante.setNombre(nombre);
        estudiante.setApellido(apellido);
        estudiante.setFechaNacimiento(convertirFecha(fechaNacimiento));
        estudiante.setTipoColegio(tipoColegio);
        estudiante.setNombreColegio(nombreColegio);
        estudiante.setAnnoEgreso(convertirFecha(AnnoEgreso));
        estudiante.setPeriodoInscripcion(convertirFecha(periodoInscripcion));
        return estudianteRepository.save(estudiante);
    }

    public Optional<EstudianteEntity> obtenerPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    public Optional<EstudianteEntity>  obtenerPorRut(String rut){
        System.out.println("rut:"+rut);
        return Optional.ofNullable(estudianteRepository.findByRut(rut));}

    public boolean eliminarEstudiante(Long id) {
        try {
            estudianteRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }



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
        Optional<HistorialArancelEntity> historialArancel = historialArancelService.obtenerPorId(estudianteEntities.get(0).getId());
        List<DetallePagoEntity> detallesPagos = detallePagoService.findbynotpagado(historialArancel.get().getId());
        detallePagoService.updateDetallesPagos(detallesPagos, promedio);
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

                    // Busca al estudiante por su RUT en la base de datos
                    Optional<EstudianteEntity> estudiante = obtenerPorRut(rut);


                    if (estudiante.isPresent()) {
                        // Obtén el historial académico del estudiante
                        HistorialAcademicoEntity historialAcademico = historialAcademicoService.obtenerPorEstudianteId(estudiante.get().getId());

                        // Guarda el historial académico actualizado en la base de datos
                        historialAcademicoService.anadirNotaConFecha(historialAcademico.getId(),nota, fechaStr);

                    }
                    // Si el estudiante no se encuentra en la base de datos, simplemente lo omite.

                } catch (DateTimeParseException | NumberFormatException e) {

                    // Maneja los errores de formato de fecha o nota
                    // Puedes imprimir un mensaje de error o tomar otra acción adecuada.
                }
            }
        }
    }





}
