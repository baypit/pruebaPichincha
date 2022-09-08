package com.bps.ejercicio.controllers;


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
import com.bps.ejercicio.models.Cliente;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteDao clienteDao;
    
    @GetMapping
    public List<Cliente> getClientes() {    	
    	return clienteDao.getClientes();
    }
    
    @PostMapping
    public ResponseEntity<Boolean> modificarClientes(@RequestBody Cliente cliente) {    	
    	 try {
			clienteDao.editar(cliente);
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
		}
    }
    
    @PutMapping
    public ResponseEntity<Boolean> registrarClientes(@RequestBody Cliente cliente) {    	
    	 try {
    		  clienteDao.registrar(cliente);
    		  return ResponseEntity.ok(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.HTTP_VERSION_NOT_SUPPORTED).header("restyp", e.getMessage()).body(null);
		}
    }
    
    
    @RequestMapping(value = "/listarClienteId", method = RequestMethod.GET)
    public Cliente getClientesById(@RequestParam int idCliente) {
    	return clienteDao.getClienteId(idCliente);
    }
   
    
}
