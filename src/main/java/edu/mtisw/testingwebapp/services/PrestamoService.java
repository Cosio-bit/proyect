package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.entities.ProjectorEntity;
import edu.mtisw.testingwebapp.repositories.PrestamoRepository;
import edu.mtisw.testingwebapp.repositories.ProjectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PrestamoService {
    @Autowired
    PrestamoRepository prestamoRepository;
    @Autowired
    ProjectorRepository projectorRepository;
    public ArrayList<PrestamoEntity> obtenerPrestamo(){
        return (ArrayList<PrestamoEntity>) prestamoRepository.findAll();
    }
    public PrestamoEntity guardarPrestamo(LocalDate fechaPrestamo, LocalDate fechaEntrega, LocalDate fechaDevolucion, String estado, Long projectorID, Long profesorID){
        PrestamoEntity prestamo = new PrestamoEntity();
        
        prestamo.setProjectorID(projectorID);
        prestamo.setProfesorID(profesorID);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaEntrega(fechaEntrega);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setEstado(estado);

        return prestamoRepository.save(prestamo);
    }


    public void updateDetallePago(PrestamoEntity optionalDetallePago, double promedio, LocalDate fechaActual){

        Long projectorId = optionalDetallePago.getProjectorID();
        ProjectorEntity projector = projectorRepository.findById(projectorId).orElse(null);
        assert projector != null;

        double originalCuota = projector.getMontoTotal()/projector.getCuotasPactadas();
        if(projector.getTipoPago().equals("cuotas")){
            LocalDate fechaVence = optionalDetallePago.getFechaVencimiento();
            int diferenciaMeses = calcularMesesAtraso(fechaActual, fechaVence);
            int interesActual = projector.getCastigoInteres();
            if(diferenciaMeses>=interesActual){
                projector.setCastigoInteres(diferenciaMeses);
            }
        optionalDetallePago.setMontoPago(calcularArancelInteres(projector.getCastigoInteres(), calcularArancelNotas(promedio, originalCuota)));}
        else{
            projector.setSaldoPorPagar(0);
        }
        projectorRepository.save(projector);
        prestamoRepository.save(optionalDetallePago);
    }
    public void updateDetallesPagos(List<PrestamoEntity> prestamoEntities, double promedio){ //obtener el promedio dada que es el mismo id de profesor ver como hacerlo porque solo profesor tiene todos los repos juntos
        LocalDate fechaActual = LocalDate.now();
        //Long projectorId = prestamoEntities.get(0).getProjectorID();
        //ProjectorEntity projector = projectorRepository.findById(projectorId).orElse(null);

        for(int i=0; i!=prestamoEntities.size();i++){
            updateDetallePago(prestamoEntities.get(i), promedio, fechaActual);
        }
    }// la diferencia se va sumando, ademas de añadir la opcion de los q pagan al contado para retornarles el dinero, diferencia que ganan dadas sus notas
    public List<PrestamoEntity> findbynotpagado(Long histAranID){
        return prestamoRepository.findDetallePagosByNotPagadoAndProjectorID(histAranID);
    }
    public double calcularArancelNotas(double promedio, double arancel) {
        if (promedio >= 950 && promedio <= 1000) {
            arancel = 0.90 * arancel;
        } else if (promedio >= 900 && promedio <= 949) {
            arancel = 0.95 * arancel;
        } else if (promedio >= 850 && promedio <= 899) {
            arancel = 0.98 * arancel;
        }

        return arancel;
    }
    public int calcularMesesAtraso(LocalDate pagoActual, LocalDate fechaVencimiento) {
        // Calcula el período entre la fecha del último pago y la fecha actual
        Period periodo = Period.between(fechaVencimiento, pagoActual);

        // Calcula el total de meses de atraso
        int aniosDiferencia = periodo.getYears();
        int mesesDiferencia = periodo.getMonths();

        return aniosDiferencia * 12 + mesesDiferencia;
    }
    public double calcularArancelInteres(int castigoInteres, double arancel){

        if (castigoInteres ==1 ) {
            arancel = 1.03 * arancel;
        } else if (castigoInteres ==2 ) {
            arancel = 1.06 * arancel;
        } else if (castigoInteres==3) {
            arancel = 1.09 * arancel;
        } else if (castigoInteres>3) {
            arancel = 1.15 * arancel;
        }
        return arancel;
    }
    public void pagar(Long idPago) {
        Optional<PrestamoEntity> optionalDetallePago = prestamoRepository.findById(idPago);

        if (optionalDetallePago.isPresent()) {
            PrestamoEntity prestamo = optionalDetallePago.get();
            prestamo.setPagado(true); // Establece el estado pagado en true
            prestamoRepository.save(prestamo); // Guarda la entidad actualizada

            // Calcula el nuevo monto total para el historial de arancel
            Long projectorId = prestamo.getProjectorID();
            ProjectorEntity projector = projectorRepository.findById(projectorId).orElse(null);

            if (projector != null) {
                double nuevoMonto = projector.getMontoTotal() - prestamo.getMontoPago();
                projector.setUltimoPago(LocalDate.now());
                projector.setSaldoPorPagar(nuevoMonto);
                projector.setCuotasPagadas(projector.getCuotasPagadas()+1);
                projector.setTotalPagado(projector.getTotalPagado()+ prestamo.getMontoPago());
                projectorRepository.save(projector);
            }
        }
    }

    public List<PrestamoEntity> obtenerPorProjectorID(Long id) {
        return prestamoRepository.findByProjectorID(id);}


}