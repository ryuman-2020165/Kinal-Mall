package org.reneyuman.bean;


public class Cargos {
    public int codigoCargos;
    public String nombreCargo;

    
    public Cargos() {
    }

    public Cargos(int codigoCargos, String nombreCargo) {
        this.codigoCargos = codigoCargos;
        this.nombreCargo = nombreCargo;
    }

    public int getCodigoCargos() {
        return codigoCargos;
    }

    public void setCodigoCargos(int codigoCargos) {
        this.codigoCargos = codigoCargos;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    
    @Override
    public String toString() {
        return getCodigoCargos()+" | "+ getNombreCargo();
    }
    
    
    
}
