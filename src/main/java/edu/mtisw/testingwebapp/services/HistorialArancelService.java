package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.HistorialArancelEntity;
import edu.mtisw.testingwebapp.repositories.HistorialArancelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class HistorialArancelService {
    @Autowired
    HistorialArancelRepository historialArancelRepository;
    @Autowired
    DetallePagoService detallePagoService;

    public ArrayList<HistorialArancelEntity> obtenerHistorialArancels(){
        return (ArrayList<HistorialArancelEntity>) historialArancelRepository.findAll();
    }
    public HistorialArancelEntity guardarHistorialArancel(Long estudianteID, String tipoColegio, String AnnoEgreso, String tipoPago, String cuotasPactadas){
        HistorialArancelEntity historialArancel = new HistorialArancelEntity();
        historialArancel.setMontoTotal(1500000);
        historialArancel.setTipoPago(tipoPago);

        OficinaRRHH oficinaRRHH = new OficinaRRHH();
        historialArancel.setMontoTotal(oficinaRRHH.calcularDescuentos( AnnoEgreso, tipoColegio));

        int maxcuotasValue = oficinaRRHH.maxcuotas(tipoColegio);
        int cuotasPactadasValue = Integer.parseInt(cuotasPactadas);
        if (cuotasPactadasValue > maxcuotasValue) {
            historialArancel.setCuotasPactadas(maxcuotasValue);
        } else historialArancel.setCuotasPactadas(Math.max(cuotasPactadasValue, 0));

        historialArancel.setCuotasRetraso(0);
        historialArancel.setCuotasPagadas(0);
        historialArancel.setTotalPagado(0);
        historialArancel.setSaldoPorPagar(historialArancel.getSaldoPorPagar()- historialArancel.getTotalPagado());
        historialArancel.setUltimoPago(LocalDate.now());
        historialArancel.setEstudianteID(estudianteID);
        //historialArancel.setDetallePagos(oficinaRRHH.generarCuotas(historialArancel.getMontoTotal(), cuotasPactadasValue));
        //detallePagoService.guardarDetallesPagos(historialArancel.getDetallePagos());
        return historialArancelRepository.save(historialArancel);
    }
    public Optional<HistorialArancelEntity> anadirPago(Long id, double efectivo) {
        // First, check if the entity with the given id exists
        Optional<HistorialArancelEntity> optionalHistorial = historialArancelRepository.findById(id);
        return optionalHistorial;
    }
    public Optional<HistorialArancelEntity> obtenerPorId(Long id){
        return historialArancelRepository.findById(id);
    }
    public Optional<HistorialArancelEntity> obtenerPorEstudianteId(Long id){
        return historialArancelRepository.findHistorialArancelByEstudianteID(id);
    }
    public boolean eliminarHistorialArancel(Long id) {
        try{
            historialArancelRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }


}