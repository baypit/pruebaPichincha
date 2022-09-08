package com.bps.ejercicio.controllers;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bps.ejercicio.dao.MovimientoDao;
import com.bps.ejercicio.models.Cliente;
import com.bps.ejercicio.models.Movimiento;

@RestController
@RequestMapping(value = "/movimiento")
public class MovimientoController {

    
    @Autowired
    private MovimientoDao movimientoDao;
    
    @GetMapping
    public List<Movimiento> getMovimientos() {    	
    	return movimientoDao.getMovimientos();
    }
    
    public static Double RANGO_MAXIMO_DIARIO = 1000D;
    
    @PostMapping
    public ResponseEntity<Boolean> modificarMovimientos(@RequestBody Movimiento movimiento) {    	
    	 try {
    		 movimientoDao.editar(movimiento);
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
		}
    }
    
    @RequestMapping(value = "/reportes", method = RequestMethod.GET)
    public List<Movimiento> getClientesById(@RequestParam String fechaInicio,@RequestParam String fechaFin, @RequestParam Integer numeroCuenta ) {
    	
    	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); 
    	Date fechaInicioD;
		try {
			fechaInicioD = formato.parse(fechaInicio);
			Date fechaFinD = formato.parse(fechaFin);
			return movimientoDao.getMovimientoPorFecha(fechaInicioD,fechaFinD,numeroCuenta);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return null;
    	
    	
    }
    
    /**
     * REST RegistroMovimiento con validaciones
     * @param movimiento
     * @return
     */
    @PutMapping
    public ResponseEntity<Boolean> registrarMovimiento(@RequestBody Movimiento movimiento) {    	
    	 try {
    		   
    		 if(movimiento.getNumeroCuenta()== null)
    			 return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", "Error").body(null);
    		 if(movimiento.getValor()== null)
    			 return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", "Error").body(null);
    		 if(movimiento.getTipoMovimiento()== null)
    			 return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", "Error").body(null);
    		 
    		 if(movimiento.getTipoMovimiento().equals("Debito")) {
    			 if(validarMontoDebitoCero(movimiento.getNumeroCuenta())) {
    				 if(validarMontoDebitoDiario(adicionarHoraMinutoSegundo(new Date(), 0, 0, 0),
        					 adicionarHoraMinutoSegundo(new Date(), 23,59, 59), movimiento.getNumeroCuenta())){
        				 movimientoDao.registrar(movimiento);
        				 return ResponseEntity.ok(true);
        			 }else {
        				 return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", "Cupo diario Excedido").body(null);
        			 }
    			 }else {
    				 return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", "Saldo no disponible").body(null);
    			 }
    		 }else {
				 movimientoDao.registrar(movimiento);
    		 }
    		 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
		}
    	 return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", "Error").body(null);
    }
    
    /**
     * Metodo que define la hora, minuto y segundo según parametros
     * @param date
     * @param hours
     * @param min
     * @param sec
     * @return
     */
    public Date adicionarHoraMinutoSegundo(Date date, int hours, int min, int sec) {
    	
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.HOUR_OF_DAY, hours);
    	c.set(Calendar.MINUTE, min);
    	c.set(Calendar.SECOND,sec);
    	
        return c.getTime();
    }
    
    /**
     * Metodo para validar monto de debito diario
     * @param fechaInicio
     * @param fechaFin
     * @param numero
     * @return
     */
    public Boolean validarMontoDebitoDiario(Date fechaInicio, Date fechaFin, Integer numero) {
    	Collection<Double> valorDebito = null;
    	Double sumaDebito = 0D;
		List<Movimiento> movimientosFechaActual = movimientoDao.getMovimientoDebitoPorFecha(fechaInicio, fechaFin, numero);
		if(CollectionUtils.isNotEmpty(movimientosFechaActual)) {
			 valorDebito = (Collection<Double>) CollectionUtils.collect(movimientosFechaActual,
					new Transformer<Movimiento, Double>() {
						@Override
						public Double transform(Movimiento input) {
							return input.getValor();
						}
					});
		}
		
		if(CollectionUtils.isNotEmpty(valorDebito)) {
			for (Double movimiento : valorDebito) {
				sumaDebito += movimiento;
			}
		}
		
		if(sumaDebito<RANGO_MAXIMO_DIARIO)
			return true;
		return false;
		
		
	}
    
    /**
     * Metodo para validar si debido es cero
     * @param numeroCuenta
     * @return
     */
    public Boolean validarMontoDebitoCero(Integer numeroCuenta){
    	Movimiento movimientoActual = movimientoDao.getMovimientoActual(numeroCuenta);
    	if(movimientoActual != null && movimientoActual.getSaldo()>0)
    		return true;
    	return false;
    }
    
    
}
