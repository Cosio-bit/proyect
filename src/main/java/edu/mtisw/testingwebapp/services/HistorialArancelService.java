package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.HistorialArancelEntity;
import edu.mtisw.testingwebapp.repositories.HistorialArancelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
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
        OficinaRRHH oficinaRRHH = new OficinaRRHH();


        if(Objects.equals(tipoPago.toLowerCase(), "cuotas")){

            historialArancel.setMontoTotal(1500000);
            historialArancel.setMontoTotal(oficinaRRHH.calcularDescuentos( AnnoEgreso, tipoColegio, 1500000));
            historialArancel.setCuotasPagadas(0);
            historialArancel.setTotalPagado(0);
            historialArancel.setSaldoPorPagar(historialArancel.getSaldoPorPagar()- historialArancel.getTotalPagado());


        }
        else{
            historialArancel.setMontoTotal(750000);
            historialArancel.setMontoTotal(oficinaRRHH.calcularDescuentos(AnnoEgreso, tipoColegio, 750000));
            historialArancel.setCuotasPagadas(1);
            historialArancel.setTotalPagado(oficinaRRHH.calcularDescuentos(AnnoEgreso, tipoColegio, 7500000));
            historialArancel.setSaldoPorPagar(0);
        }

        historialArancel.setTipoPago(tipoPago);

        historialArancel.setCuotasPactadas(Integer.parseInt(cuotasPactadas));

        historialArancel.setCuotasRetraso(0);
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


}