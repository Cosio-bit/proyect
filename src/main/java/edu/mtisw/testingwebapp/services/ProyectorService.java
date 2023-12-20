package edu.mtisw.testingwebapp.services;

import edu.mtisw.testingwebapp.entities.ProyectorEntity;
import edu.mtisw.testingwebapp.repositories.ProyectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProyectorService {
    @Autowired
    ProyectorRepository historialArancelRepository;
    @Autowired
    PrestamoService detallePagoService;

    public ArrayList<ProyectorEntity> obtenerHistorialArancels(){
        return (ArrayList<ProyectorEntity>) historialArancelRepository.findAll();
    }
    public ProyectorEntity guardarHistorialArancel(Long estudianteID, String tipoColegio, String AnnoEgreso, String tipoPago, String cuotasPactadas){
        ProyectorEntity historialArancel = new ProyectorEntity();
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
            historialArancel.setTotalPagado(oficinaRRHH.calcularDescuentos(AnnoEgreso, tipoColegio, 750000));
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
    public Optional<ProyectorEntity> anadirPago(Long id, double efectivo) {
        // First, check if the entity with the given id exists
        Optional<ProyectorEntity> optionalHistorial = historialArancelRepository.findById(id);
        return optionalHistorial;
    }
    public Optional<ProyectorEntity> obtenerPorId(Long id){
        return historialArancelRepository.findById(id);
    }
    public Optional<ProyectorEntity> obtenerPorEstudianteId(Long id){
        return historialArancelRepository.findHistorialArancelByEstudianteID(id);
    }


}