
package org.reneyuman.bean;


public class Locales {
    private int codigoLocal;
    private Double saldoFavor;
    private Double saldoContra;
    private int mesesPendientes;
    private boolean disponibilidad;
    private Double valorLocal;
    private Double valorAdministracion;

    public Locales() {
    }

    public Locales(int codigoLocal, Double saldoFavor, Double saldoContra, int mesesPendientes, boolean disponibilidad, Double valorLocal, Double valorAdministracion) {
        this.codigoLocal = codigoLocal;
        this.saldoFavor = saldoFavor;
        this.saldoContra = saldoContra;
        this.mesesPendientes = mesesPendientes;
        this.disponibilidad = disponibilidad;
        this.valorLocal = valorLocal;
        this.valorAdministracion = valorAdministracion;
    }

    public int getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(int codigoLocal) {
        this.codigoLocal = codigoLocal;
    }

    public Double getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(Double saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public Double getSaldoContra() {
        return saldoContra;
    }

    public void setSaldoContra(Double saldoContra) {
        this.saldoContra = saldoContra;
    }

    public int getMesesPendientes() {
        return mesesPendientes;
    }

    public void setMesesPendientes(int mesesPendientes) {
        this.mesesPendientes = mesesPendientes;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    

    public Double getValorLocal() {
        return valorLocal;
    }

    public void setValorLocal(Double valorLocal) {
        this.valorLocal = valorLocal;
    }

    public Double getValorAdministracion() {
        return valorAdministracion;
    }

    public void setValorAdministracion(Double valorAdministracion) {
        this.valorAdministracion = valorAdministracion;
    }

    @Override
    public String toString() {
        return getCodigoLocal() + " | "+"Q."+ getValorLocal()+" | " +isDisponibilidad();
                
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
