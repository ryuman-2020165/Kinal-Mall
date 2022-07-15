package org.reneyuman.bean;

import java.util.Date;

public class Empleados {
 private int codigoEmpleado;
 private String nombreEmpleado;
 private String apellidoEmpleado;
 private String correoElectronico;
 private String telefonoEmpleado;
 private Date fechaContratacion;
 private double sueldo;
 private int codigoDepartamento;
 private int codigoCargos;
 private int codigoHorario;
 private int codigoAdministracion;

    public Empleados() {
    }

    public Empleados(int codigoEmpleado, String nombreEmpleado, String apellidoEmpleado, String correoElectronico, String telefonoEmpleado, Date fechaContratacion, double sueldo, int codigoDepartamento, int codigoCargos, int codigoHorario, int codigoAdministracion) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.correoElectronico = correoElectronico;
        this.telefonoEmpleado = telefonoEmpleado;
        this.fechaContratacion = fechaContratacion;
        this.sueldo = sueldo;
        this.codigoDepartamento = codigoDepartamento;
        this.codigoCargos = codigoCargos;
        this.codigoHorario = codigoHorario;
        this.codigoAdministracion = codigoAdministracion;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public int getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(int codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public int getCodigoCargos() {
        return codigoCargos;
    }

    public void setCodigoCargos(int codigoCargos) {
        this.codigoCargos = codigoCargos;
    }

    public int getCodigoHorario() {
        return codigoHorario;
    }

    public void setCodigoHorario(int codigoHorario) {
        this.codigoHorario = codigoHorario;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }
 
 
 
 
 
}
