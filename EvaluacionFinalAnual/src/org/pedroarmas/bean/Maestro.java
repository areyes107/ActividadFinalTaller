package org.pedroarmas.bean;

import java.util.Date;


public class Maestro {
    private int codigoMaestro;
    private String nombreMaestro;
    private String apellidosMaestro;
    private Date fechaNacimiento;
    private String sexo;
    private String titulo;
    private String direccion;
    private String telefono;

    public Maestro() {
    }

    public Maestro(int codigoMaestro, String nombreMaestro, String apellidosMaestro, Date fechaNacimiento, String sexo, String titulo, String direccion, String telefono) {
        this.codigoMaestro = codigoMaestro;
        this.nombreMaestro = nombreMaestro;
        this.apellidosMaestro = apellidosMaestro;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.titulo = titulo;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCodigoMaestro() {
        return codigoMaestro;
    }

    public void setCodigoMaestro(int codigoMaestro) {
        this.codigoMaestro = codigoMaestro;
    }

    public String getNombreMaestro() {
        return nombreMaestro;
    }

    public void setNombreMaestro(String nombreMaestro) {
        this.nombreMaestro = nombreMaestro;
    }

    public String getApellidosMaestro() {
        return apellidosMaestro;
    }

    public void setApellidosMaestro(String apellidosMaestro) {
        this.apellidosMaestro = apellidosMaestro;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
}
