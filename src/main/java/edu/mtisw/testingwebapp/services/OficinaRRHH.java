/* 


package edu.mtisw.testingwebapp.services;
import edu.mtisw.testingwebapp.entities.ProfesorEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class  OficinaRRHH {

   public static LocalDate convertirFecha(String fechaStr) {
   // Define el formato de fecha esperado en la cadena
   DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd");

   try {
      // Intenta parsear la cadena en un objeto LocalDate
       return LocalDate.parse(fechaStr, formato);
   } catch (Exception e) {
      // En caso de error, maneja la excepción apropiadamente
      System.out.println("Error al convertir la fecha: " + e.getMessage());
      return null;
   }
   } // Falta cerrar la llave aquí
   // calcula el salario anual del profesora
   public int calcularAnnoEgreso(ProfesorEntity profesor) {
      // Obtén la fecha actual
      LocalDate fechaEgreso = profesor.getAnnoEgreso();
      LocalDate fechaActual = LocalDate.now(); // Usamos LocalDate.now() para obtener la fecha actual

      // Calcula el período entre la fecha de egreso y la fecha actual
      Period periodo = Period.between(fechaEgreso, fechaActual);

      return periodo.getYears();
   }
   public int calcularAnnoEgreso(LocalDate fechaEgreso) {
   // Obtén la fecha actual
   LocalDate fechaActual = LocalDate.now(); // Usamos LocalDate.now() para obtener la fecha actual

   // Calcula el período entre la fecha de egreso y la fecha actual
   Period periodo = Period.between(fechaEgreso, fechaActual);

   return periodo.getYears();
   }
   public static double calcularArancelNotas(double promedio, double arancel) {
   if (promedio >= 950 && promedio <= 1000) {
      arancel = 0.90 * arancel;
   } else if (promedio >= 900 && promedio <= 949) {
      arancel = 0.95 * arancel;
   } else if (promedio >= 850 && promedio <= 899) {
      arancel = 0.98 * arancel;
   }




   return arancel;
   }
   public double calcularMesesAtraso(LocalDate pagoActual, LocalDate fechaVencimiento) {
   // Calcula el período entre la fecha del último pago y la fecha actual
   Period periodo = Period.between(fechaVencimiento, pagoActual);

   // Calcula el total de meses de atraso
   int aniosDiferencia = periodo.getYears();
   int mesesDiferencia = periodo.getMonths();

   return aniosDiferencia * 12 + mesesDiferencia;
   }
   public double calcularArancelAnnoEgreso(int AnnoEgreso, double arancel) {

      if (AnnoEgreso == 0) {
         arancel = 0.85 * arancel;
      } else if (AnnoEgreso == 1 || AnnoEgreso == 2 ) {
         arancel = 0.92 * arancel;
      } else if (AnnoEgreso == 3 || AnnoEgreso == 4) {
         arancel = 0.96 * arancel;
      } else {
         arancel = 1 * arancel;
      }
      return arancel;
   }
   public double calcularArancelTipoColegio(String tipoColegio, double arancel) {
   if (Objects.equals(tipoColegio, "municipal")) {
      arancel = 0.8 * arancel;
   }
   else if (Objects.equals(tipoColegio, "subencionado")) {
      arancel = 0.9 * arancel;
   }
   else if (Objects.equals(tipoColegio, "privado")) {
      arancel = 1 * arancel;
   }
   return arancel;
   }
   public double calcularPromedio(String todas) {
      List<Integer> notas = stringToList(todas);

      if (notas.isEmpty()) {
         return -1.0; // Retorna -1 si no hay notas disponibles.
      }
      double sumatoria = notas.stream().mapToInt(Integer::intValue).sum();
      return sumatoria / notas.size();
   }
   public double calcularDescuentos( String AnnoEgreso, String tipoColegio, double arancel){

    return calcularArancelAnnoEgreso(calcularAnnoEgreso(convertirFecha(AnnoEgreso)),
           calcularArancelTipoColegio(
                   tipoColegio, arancel));
   }
   ////////////tentative cut up is arancel down is cuotas and maybe payment
   public int maxcuotas(String tipoColegio){
      int numcuotas;
      if (Objects.equals(tipoColegio, "municipal")) {
         numcuotas=10;
         return numcuotas;
      }
      else if (Objects.equals(tipoColegio, "subencionado")) {
         numcuotas=7;
         return numcuotas;
      }
      else if (Objects.equals(tipoColegio, "privado")) {
         numcuotas=4;
         return numcuotas;
      }
      numcuotas=0;
      return numcuotas;
   }
   public int maxcuotas(ProfesorEntity profesor){
   int numcuotas;
   if (Objects.equals(profesor.getTipoColegio(), "municipal")) {
      numcuotas=10;
      return numcuotas;
   }
   else if (Objects.equals(profesor.getTipoColegio(), "subencionado")) {
      numcuotas=7;
      return numcuotas;
   }
   else if (Objects.equals(profesor.getTipoColegio(), "privado")) {
      numcuotas=4;
      return numcuotas;
   }
   numcuotas=0;
   return numcuotas;
   }
   public static List<Integer> stringToList(String input) {
   String[] valores = input.split(",");
   return Arrays.stream(valores)
           .map(Integer::parseInt)
           .collect(Collectors.toList());
   }



   /*

   public List<List<String>> searchByRut(List<List<String>> dataList, String rut) {
      List<List<String>> matchingRows = new ArrayList<>();

      for (List<String> row : dataList) {
         // Check if the first column matches the provided name
         if (!row.isEmpty() && row.get(0).equalsIgnoreCase(rut)) {
            matchingRows.add(row);
         }
      }
      return matchingRows;
   }


   public List<Integer> calculateMonthDifference(List<List<String>> dataList, LocalDate fixedStartDate) {
      List<Integer> monthDifferences = new ArrayList<>();

      for (List<String> row : dataList) {
         if (row.size() > 1) { // Ensure there is at least one column after the first one
            String dateStr = row.get(1); // Assuming the date is in the second column

            try {
               LocalDate dateInRow = LocalDate.parse(dateStr);
               Period period = Period.between(fixedStartDate.withDayOfMonth(1), dateInRow.withDayOfMonth(1));
               int monthsDifference = period.getMonths();

               monthDifferences.add(monthsDifference);
            } catch (DateTimeParseException e) {
               // Handle parsing errors if the date format is invalid
               // You can log the error or take other appropriate action
            }
         }
      }

      return monthDifferences;
   }

   public List<List<String>> searchByDate(List<List<String>> dataList, String targetDate) {
      List<List<String>> matchingRows = new ArrayList<>();

      for (List<String> row : dataList) {
         // Check if the second column (index 1) matches the target date
         if (row.size() > 1 && row.get(1).equalsIgnoreCase(targetDate)) {
            matchingRows.add(row);
         }
      }
      return matchingRows;
   }


   public static int calculateMonthsDifference(LocalDate date1, LocalDate date2) {
      Period period = Period.between(date1, date2);
       return period.getYears() * 12 + period.getMonths();
   }

      public List<DetallePagoEntity> generarCuotas(double arancel, int cuotasDeseadas) {
      List<DetallePagoEntity> cuotasGeneradas = new ArrayList<>();

      // Calcula el monto de cada cuota
      double montoCuota = arancel / cuotasDeseadas;

      // Obtén la fecha actual
      LocalDate fechaActual = LocalDate.now();

      // Genera las cuotas y agrega a la lista
      for (int i = 0; i < cuotasDeseadas; i++) {
         DetallePagoEntity cuota = new DetallePagoEntity();

         // Calcula la fecha de vencimiento (puedes definir tus propias reglas)
         LocalDate fechaVencimiento = fechaActual.plusMonths(i + 1); // Por ejemplo, vencimiento mensual

         cuota.setFechaVencimiento(fechaVencimiento);
         cuota.setMontoPago(montoCuota);
         cuota.setPagado(false); // Inicialmente, la cuota no está pagada

         // Agrega la cuota a la lista
         cuotasGeneradas.add(cuota);
      }

      return cuotasGeneradas;
   }


}
*/
/*
     public double calcularArancelTipoColegio(ProfesorEntity profesor) {
         double arancel = profesor.getProjector().getMontoTotal();
         if (Objects.equals(profesor.getTipoColegio(), "municipal")) {
            arancel = 0.8 * arancel;
         }
         else if (Objects.equals(profesor.getTipoColegio(), "subencionado")) {
            arancel = 0.9 * arancel;
         }
         else if (Objects.equals(profesor.getTipoColegio(), "privado")) {
            arancel = 1 * arancel;
         }
         return arancel;
      }
   public double calcularArancelAnnoEgreso(ProfesorEntity profesor) {
      int AnnoEgreso = calcularAnnoEgreso(profesor);
      double arancel = profesor.getProjector().getMontoTotal();
      if (AnnoEgreso == 0) {
         arancel = 0.85 * arancel;
      } else if (AnnoEgreso == 1 || AnnoEgreso == 2 ) {
         arancel = 0.92 * arancel;
      } else if (AnnoEgreso == 3 || AnnoEgreso == 4) {
         arancel = 0.96 * arancel;
      } else {
         arancel = 1 * arancel;
      }
      return arancel;
   }
   public double calcularArancelNotas(ProfesorEntity profesor) {
      double promedio = calcularPromedio(profesor);
      double arancel = profesor.getProjector().getMontoTotal(); // Asegurémonos de que arancel tenga un valor inicial

      if (promedio >= 950 && promedio <= 1000) {
         arancel = 0.90 * arancel;
      } else if (promedio >= 900 && promedio <= 949) {
         arancel = 0.95 * arancel;
      } else if (promedio >= 850 && promedio <= 899) {
         arancel = 0.98 * arancel;
      }

      return arancel;
   }
   public double calcularPromedio(ProfesorEntity profesor) {
      List<Integer> notas = profesor.getHistorialAcademico().getNotas();

      if (notas.isEmpty()) {
         return -1.0; // Retorna -1 si no hay notas disponibles.
      }
      double sumatoria = notas.stream().mapToInt(Integer::intValue).sum();
      return sumatoria / notas.size();
   }
   public double calcularArancelInteres(ProfesorEntity profesor){
      double arancel= profesor.getProjector().getMontoTotal();
      double mesesAtraso= calcularMesesAtraso(profesor);

      if (mesesAtraso ==1 ) {
         arancel = 1.03 * arancel;
      } else if (mesesAtraso ==2 ) {
         arancel = 1.06 * arancel;
      } else if (mesesAtraso==3) {
         arancel = 1.09 * arancel;
      } else if (mesesAtraso>3) {
         arancel = 1.15 * arancel;
      }
      return arancel;
   }
   public double calcularMesesAtraso(ProfesorEntity profesor) {
      LocalDate ultimoPago = profesor.getProjector().getUltimoPago();
      int ultimaCuotaPagada = profesor.getProjector().getCuotasPagadas();
      LocalDate vencimientoUltimaCuota = profesor.getProjector().getDetallePagos().get(ultimaCuotaPagada).getFechaVencimiento();
      LocalDate fechaActual = LocalDate.now(); // Obtén la fecha actual como un objeto LocalDate

      // Calcula el período entre la fecha del último pago y la fecha actual
      Period periodo = Period.between(vencimientoUltimaCuota, ultimoPago);

      // Calcula el total de meses de atraso
      int aniosDiferencia = periodo.getYears();
      int mesesDiferencia = periodo.getMonths();

      return aniosDiferencia * 12 + mesesDiferencia;
   }
   public double calcularDescuentos(ProfesorEntity profesor){
      profesor.getProjector().setMontoTotal(calcularArancelNotas(profesor));
      profesor.getProjector().setMontoTotal(calcularArancelAnnoEgreso(profesor));
      profesor.getProjector().setMontoTotal(calcularArancelTipoColegio(profesor));

      return profesor.getProjector().getMontoTotal();
   }
   public void pagarCuota(ProfesorEntity profesor){
      int cuotaActual = profesor.getProjector().getCuotasPagadas()+1;
      profesor.getProjector().setCuotasPagadas(cuotaActual);
      DetallePagoEntity prestamo = profesor.getProjector().getDetallePagos().get(cuotaActual);
      prestamo.setPagado(true);
      profesor.getProjector().setTotalPagado(profesor.getProjector().getTotalPagado()+prestamo.getMontoPago());
   }

   public List<String> listarEstadoCuotas(ProfesorEntity profesor) {
      List<DetallePagoEntity> prestamos = profesor.getProjector().getDetallePagos();
      List<String> cuotasStatus = new ArrayList<>();

      for (int i = 0; i < prestamos.size(); i++) {

         if (prestamos.get(i).isPagado()) {
            cuotasStatus.add("Cuota " + (i + 1) + " Pagada");
         } else {
            cuotasStatus.add("Cuota " + (i + 1) + " No Pagada");
         }
      }
      return cuotasStatus;
   }




*/


   /*
   public double calcularArancelInteres(LocalDate pagoActual, LocalDate fechaVencimiento, double arancel){
   double mesesAtraso= calcularMesesAtraso(pagoActual, fechaVencimiento);

   if (mesesAtraso ==1 ) {
      arancel = 1.03 * arancel;
   } else if (mesesAtraso ==2 ) {
      arancel = 1.06 * arancel;
   } else if (mesesAtraso==3) {
      arancel = 1.09 * arancel;
   } else if (mesesAtraso>3) {
      arancel = 1.15 * arancel;
   }
   return arancel;
   }*/


