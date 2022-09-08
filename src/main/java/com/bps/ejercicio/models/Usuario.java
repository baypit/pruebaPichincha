package com.bps.ejercicio.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@Table(name = "users")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name="ID")
    private Integer id;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaUltimoLogin() {
        return fechaUltimoLogin;
    }

    public void setFechaUltimoLogin(Date fechaUltimoLogin) {
        this.fechaUltimoLogin = fechaUltimoLogin;
    }

    @Getter @Setter @Column(name="NAME")
    private String nombre;

    @Getter @Setter @Column(name="EMAIL")
    private String email;

    @Getter @Setter @Column(name="PASSWORD")
    private String password;

    @Getter @Setter @Column(name="DATE_CREATE")
    private Date fechaCreacion;

    @Getter @Setter @Column(name="DATE_MODIFIED")
    private Date fechaModificacion;

    @Getter @Setter @Column(name="DATE_LAST_LOGIN")
    private Date fechaUltimoLogin;



}
