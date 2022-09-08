package com.bps.ejercicio.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "cliente")
public class Cliente {


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CLIENTEID")
    private Integer id;

    @Column(name="NOMBRE")
    private String nombre;
    
    @Column(name="GENERO")
    private String genero;
    
    @Column(name="EDAD")
    private int	edad;
    
     @Column(name="IDENTIFICACION")
    private String identificacion;

     @Column(name="DIRECCION")
     private String direccion;
     
     @Column(name="TELEFONO")
     private String telefono;
     
     @Column(name="CONTRASENA")
     private String contrasena;
     
     @Column(name="ESTADO")
     private Boolean estado;
     
     
     

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	     
}
