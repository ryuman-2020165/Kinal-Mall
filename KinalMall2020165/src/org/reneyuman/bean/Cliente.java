package org.reneyuman.bean;


public class Cliente {
    private int CodigoCliente;
    private String nombresCliente;
    private String apellidosCliente;
    private String telefonoCliente;
    private String direccionCliente;
    private String email;
    private int codigoLocal;
    private int codigoAdministracion;
    private int codigoTipoCliente;

    public Cliente() {
    }

    public Cliente(int CodigoCliente, String nombresCliente, String apellidosCliente, String telefonoCliente, String direccionCliente, String email, int codigoLocal, int codigoAdministracion, int codigoTipoCliente) {
        this.CodigoCliente = CodigoCliente;
        this.nombresCliente = nombresCliente;
        this.apellidosCliente = apellidosCliente;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
        this.email = email;
        this.codigoLocal = codigoLocal;
        this.codigoAdministracion = codigoAdministracion;
        this.codigoTipoCliente = codigoTipoCliente;
    }

    public int getCodigoCliente() {
        return CodigoCliente;
    }

    public void setCodigoCliente(int CodigoCliente) {
        this.CodigoCliente = CodigoCliente;
    }

    public String getNombresCliente() {
        return nombresCliente;
    }

    public void setNombresCliente(String nombresCliente) {
        this.nombresCliente = nombresCliente;
    }

    public String getApellidosCliente() {
        return apellidosCliente;
    }

    public void setApellidosCliente(String apellidosCliente) {
        this.apellidosCliente = apellidosCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(int codigoLocal) {
        this.codigoLocal = codigoLocal;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    public int getCodigoTipoCliente() {
        return codigoTipoCliente;
    }

    public void setCodigoTipoCliente(int codigoTipoCliente) {
        this.codigoTipoCliente = codigoTipoCliente;
    }

    

    

    
    
    
    
    
    
}
