package com.bps.ejercicio.controllers;


import java.util.Date;
import java.util.List;

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

import com.bps.ejercicio.dao.ClienteDao;
import com.bps.ejercicio.dao.CuentaDao;
import com.bps.ejercicio.dao.MovimientoDao;
import com.bps.ejercicio.models.Cliente;
import com.bps.ejercicio.models.Cuenta;
import com.bps.ejercicio.models.Movimiento;

@RestController
@RequestMapping(value = "/cuenta")
public class CuentaController {

    @Autowired
    private CuentaDao cuentaDao;
    
    @Autowired
    private ClienteDao clienteDao;
    
    @Autowired
    private MovimientoDao movimientoDao;
    
    @GetMapping
    public List<Cuenta> getClientes() {    	
    	return cuentaDao.getCuentas();
    }
    
    @PostMapping
    public ResponseEntity<Boolean> modificarCuentas(@RequestBody Cuenta cuenta) {    	
    	 try {
    		 cuentaDao.editar(cuenta);
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
		}
    }
    
    @PutMapping
    public ResponseEntity<Boolean> registrarCuentas(@RequestBody Cuenta cuenta) {    	
    	 try {
    		 if(validarCliente(cuenta.getClienteId()) && validarCuenta(cuenta.getNumero())) {
    			 Cuenta cuentaRegistrada = cuentaDao.registrar(cuenta);
    			 if(cuentaRegistrada != null) {
    				 Movimiento movimientoRegistrar = new Movimiento();
        			 movimientoRegistrar.setNumeroCuenta(cuentaRegistrada.getNumero());
        			 movimientoRegistrar.setSaldo(cuentaRegistrada.getSaldo());
        			 movimientoRegistrar.setTipoMovimiento("Deposito");
        			 movimientoRegistrar.setValor(cuentaRegistrada.getSaldo());
        			 movimientoRegistrar.setFecha(new Date());
        			 movimientoRegistrar.setEstado(true);
        			 movimientoDao.registrar(movimientoRegistrar);
    			 }
    			 
        		 return ResponseEntity.ok(true);
    		 }else {
    			 return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", "Cliente no registrado").body(null);
    		 }
    		 
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
		}
    }
    
    /**
     * Metodo para validar cliente para cuenta
     * @param idCliente
     * @return
     */
    public Boolean validarCliente(Integer idCliente) {
    	Cliente cliente = clienteDao.getClienteId(idCliente);
    	if(cliente!= null)
    		return true;
    	return false;
    }
    
    /**
     * MEtodo para validar existencia de cuenta
     * @param numeroCuenta
     * @return
     */
    public Boolean validarCuenta(Integer numeroCuenta) {
    	Cuenta cuenta = cuentaDao.getCuentaNumero(numeroCuenta);
    	if(cuenta!= null)
    		return false;
    	return true;
    }
    
    
    @RequestMapping(value = "/listarCuentaNumero", method = RequestMethod.GET)
    public Cuenta getCuentaPorNumero(@RequestParam int numeroCuenta) {
    	return cuentaDao.getCuentaNumero(numeroCuenta);
    }
   
    
}
