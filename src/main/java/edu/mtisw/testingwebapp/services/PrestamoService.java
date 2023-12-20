package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.PrestamoEntity;
import edu.mtisw.testingwebapp.entities.ProyectorEntity;
import edu.mtisw.testingwebapp.repositories.PrestamoRepository;
import edu.mtisw.testingwebapp.repositories.ProyectorRepository;
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
    PrestamoRepository detallePagoRepository;
    @Autowired
    ProyectorRepository historialArancelRepository;
    public ArrayList<PrestamoEntity> obtenerDetallesPagos(){
        return (ArrayList<PrestamoEntity>) detallePagoRepository.findAll();
    }
    public PrestamoEntity guardarDetallePago(Long proyectorID, double cuotasPactadas, double montoTotal, int nroCuota){
        PrestamoEntity prestamo = new PrestamoEntity();
        String tipopago = historialArancelRepository.findById(historialArancelID).get().getTipoPago();
        if(Objects.equals(tipopago, "cuotas")){
            prestamo.setMontoPago(montoTotal/cuotasPactadas);
            prestamo.setPagado(false);}
        else{
            prestamo.setMontoPago((montoTotal)/cuotasPactadas);
            prestamo.setPagado(true);}

        prestamo.setHistorialArancelID(historialArancelID);
        prestamo.setFechaVencimiento(LocalDate.now().plusMonths(nroCuota+1));
        return detallePagoRepository.save(prestamo);
    }
    public List<PrestamoEntity> guardarDetallesPagos(Long historialArancelID,int cuotasPactadas, double montoTotal) {
        List<PrestamoEntity> detallePagoEntities = new ArrayList<>();
        for(int i = 0; i!=cuotasPactadas; i++) {
            // Let's iterate through each 'DetallePagoEntity' in the 'detallesPagos' list and save them.
            detallePagoEntities.add(guardarDetallePago(historialArancelID, cuotasPactadas, montoTotal, i));
            System.out.println(detallePagoEntities.get(i).getId());
        }
        return detallePagoEntities;
    }

    public void updateDetallePago(PrestamoEntity optionalDetallePago, double promedio, LocalDate fechaActual){

        Long historialArancelId = optionalDetallePago.getHistorialArancelID();
        ProyectorEntity historialArancel = historialArancelRepository.findById(historialArancelId).orElse(null);
        assert historialArancel != null;

        double originalCuota = historialArancel.getMontoTotal()/historialArancel.getCuotasPactadas();
        if(historialArancel.getTipoPago().equals("cuotas")){
            LocalDate fechaVence = optionalDetallePago.getFechaVencimiento();
            int diferenciaMeses = calcularMesesAtraso(fechaActual, fechaVence);
            int interesActual = historialArancel.getCastigoInteres();
            if(diferenciaMeses>=interesActual){
                historialArancel.setCastigoInteres(diferenciaMeses);
            }
        optionalDetallePago.setMontoPago(calcularArancelInteres(historialArancel.getCastigoInteres(), calcularArancelNotas(promedio, originalCuota)));}
        else{
            historialArancel.setSaldoPorPagar(0);
        }
        historialArancelRepository.save(historialArancel);
        detallePagoRepository.save(optionalDetallePago);
    }
    public void updateDetallesPagos(List<PrestamoEntity> detallePagoEntities, double promedio){ //obtener el promedio dada que es el mismo id de profesor ver como hacerlo porque solo profesor tiene todos los repos juntos
        LocalDate fechaActual = LocalDate.now();
        //Long historialArancelId = detallePagoEntities.get(0).getHistorialArancelID();
        //HistorialArancelEntity historialArancel = historialArancelRepository.findById(historialArancelId).orElse(null);

        for(int i=0; i!=detallePagoEntities.size();i++){
            updateDetallePago(detallePagoEntities.get(i), promedio, fechaActual);
        }
    }// la diferencia se va sumando, ademas de añadir la opcion de los q pagan al contado para retornarles el dinero, diferencia que ganan dadas sus notas
    public List<PrestamoEntity> findbynotpagado(Long histAranID){
        return detallePagoRepository.findDetallePagosByNotPagadoAndHistorialArancelID(histAranID);
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
        Optional<PrestamoEntity> optionalDetallePago = detallePagoRepository.findById(idPago);

        if (optionalDetallePago.isPresent()) {
            PrestamoEntity prestamo = optionalDetallePago.get();
            prestamo.setPagado(true); // Establece el estado pagado en true
            detallePagoRepository.save(prestamo); // Guarda la entidad actualizada

            // Calcula el nuevo monto total para el historial de arancel
            Long historialArancelId = prestamo.getHistorialArancelID();
            ProyectorEntity historialArancel = historialArancelRepository.findById(historialArancelId).orElse(null);

            if (historialArancel != null) {
                double nuevoMonto = historialArancel.getMontoTotal() - prestamo.getMontoPago();
                historialArancel.setUltimoPago(LocalDate.now());
                historialArancel.setSaldoPorPagar(nuevoMonto);
                historialArancel.setCuotasPagadas(historialArancel.getCuotasPagadas()+1);
                historialArancel.setTotalPagado(historialArancel.getTotalPagado()+ prestamo.getMontoPago());
                historialArancelRepository.save(historialArancel);
            }
        }
    }

    public List<PrestamoEntity> obtenerPorHistorialArancelID(Long id) {
        return detallePagoRepository.findByHistorialArancelID(id);}


}