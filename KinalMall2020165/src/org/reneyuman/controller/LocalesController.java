
package org.reneyuman.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.reneyuman.bean.Locales;
import org.reneyuman.db.Conexion;
import org.reneyuman.system.Principal;


public class LocalesController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Locales> listaLocales;
    
    
    @FXML TableView tblLocales;
    @FXML TableColumn colCodigoLocal;
    @FXML TableColumn colSaldoFavor;
    @FXML TableColumn colSaldoContra;
    @FXML TableColumn colMesesPendientes;
    @FXML TableColumn colDisponibilidad;
    @FXML TableColumn colValorLocal;
    @FXML TableColumn colValorAdministracion;
    @FXML TextField txtCodigoLocal;
    @FXML TextField txtSaldoFavor;
    @FXML TextField txtSaldoContra;
    @FXML TextField txtMesesPendientes;
    @FXML TextField txtDisponibilidad;
    @FXML TextField txtValorLocal;
    @FXML TextField txtValorAdministracion;
    @FXML Button btnNuevo;
    @FXML Button btnEditar;
    @FXML Button btnEliminar;
    @FXML Button btnReporte;
    @FXML ImageView imgNuevo;
    @FXML ImageView imgEditar;
    @FXML ImageView imgReporte;
    @FXML ImageView imgEliminar;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    
    
    public void cargarDatos(){
        tblLocales.setItems(getLocales());
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("codigoLocal"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Locales,Double>("saldoFavor"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory<Locales, Double>("saldoContra"));
        colMesesPendientes.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("mesesPendientes"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Locales,Boolean>("disponibilidad"));
        colValorLocal.setCellValueFactory(new PropertyValueFactory<Locales,Double>("valorLocal"));
        colValorAdministracion.setCellValueFactory(new PropertyValueFactory<Locales,Double>("valorAdministracion"));
        
    }
    
    
    
    public ObservableList<Locales> getLocales(){
        ArrayList<Locales> lista = new ArrayList<Locales>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarLocal()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Locales(resultado.getInt("codigoLocal"),
                                                resultado.getDouble("saldoFavor"),
                                                resultado.getDouble("saldoContra"),
                                                resultado.getInt("mesesPendientes"),
                                                resultado.getBoolean("disponibilidad"),
                                                resultado.getDouble("valorLocal"),
                                                resultado.getDouble("valorAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
        return listaLocales = FXCollections.observableArrayList(lista);
    }
    
    
    
    
    public void seleccionarElemento(){
    
    if(tblLocales.getSelectionModel().getSelectedItem()!= null){
        txtCodigoLocal.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getCodigoLocal()));
        txtSaldoFavor.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getSaldoFavor()));
        txtSaldoContra.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getSaldoContra()));
        txtMesesPendientes.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getMesesPendientes()));
        txtDisponibilidad.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).isDisponibilidad()));
        txtValorLocal.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getValorLocal()));
        txtValorAdministracion.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getValorAdministracion()));
    }
    } 
    
    
    
    
    
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/reneyuman/images/guardar.png"));
                imgEliminar.setImage(new Image("/org/reneyuman/images/cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/reneyuman/images/Añadir2.png"));
                imgEliminar.setImage(new Image("/org/reneyuman/images/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    /*Guardar*/
    public void guardar(){
        Locales registro = new Locales();
//        registro.setSaldoFavor(Double.valueOf(txtSaldoFavor.getText()));
//        registro.setSaldoContra(Double.valueOf(txtSaldoContra.getText()));
//        registro.setMesesPendientes(Integer.valueOf(txtMesesPendientes.getText()));
        registro.setDisponibilidad(Boolean.valueOf(txtDisponibilidad.getText()));
        registro.setValorLocal(Double.valueOf(txtValorLocal.getText()));
        registro.setValorAdministracion(Double.valueOf(txtValorAdministracion.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarLocales(?,?,?)}");
            procedimiento.setBoolean(1, registro.isDisponibilidad());
            procedimiento.setDouble(2, registro.getValorLocal());
            procedimiento.setDouble(3, registro.getValorAdministracion());
            procedimiento.execute();
            listaLocales.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image("/org/reneyuman/images/Añadir2.png"));
                imgEliminar.setImage(new Image("/org/reneyuman/images/Eliminar.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
                
            default:
                if (tblLocales.getSelectionModel().getSelectedItem()!= null ){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Local", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("CALL sp_EliminarLocal(?)");
                        procedimiento.setInt(1,((Locales)tblLocales.getSelectionModel().getSelectedItem()).getCodigoLocal());
                        procedimiento.execute();
                        listaLocales.remove(tblLocales.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
        }
    }
    
    
    
    public void editar (){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblLocales.getSelectionModel().getSelectedItem()!= null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/reneyuman/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/reneyuman/images/cancelar.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
                
            case ACTUALIZAR:
                actualizar();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/reneyuman/images/Editar.png"));
                imgReporte.setImage(new Image("/org/reneyuman/images/Informe.png"));
                limpiarControles();
                desactivarControles();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarLocales(?,?,?,?)");
            Locales registro = (Locales)tblLocales.getSelectionModel().getSelectedItem();
//            registro.setSaldoFavor(Double.valueOf(txtSaldoFavor.getText()));
//            registro.setSaldoContra(Double.valueOf(txtSaldoContra.getText()));
//            registro.setMesesPendientes(Integer.valueOf(txtMesesPendientes.getText()));
            registro.setDisponibilidad(Boolean.valueOf(txtDisponibilidad.getText()));
            registro.setValorLocal(Double.valueOf(txtValorLocal.getText()));
            registro.setValorAdministracion(Double.valueOf(txtValorAdministracion.getText()));
            
            procedimiento.setInt(1, registro.getCodigoLocal());
//            procedimiento.setDouble(2, registro.getSaldoFavor());
//            procedimiento.setDouble(3, registro.getSaldoContra());
//            procedimiento.setInt(4, registro.getMesesPendientes());
            procedimiento.setBoolean(2, registro.isDisponibilidad());
            procedimiento.setDouble(3, registro.getValorLocal());
            procedimiento.setDouble(4, registro.getValorAdministracion());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void informe(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/reneyuman/images/Editar.png"));
                imgReporte.setImage(new Image("/org/reneyuman/images/Informe.png"));
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
                
            case NINGUNO:
                /*Aqui va el informe pero todavia no esta hecho*/
                JOptionPane.showMessageDialog(null, "Informe");
                break;
        }
    }
    
    
    
    
    
    
    
    
    public void desactivarControles(){
        txtCodigoLocal.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        txtMesesPendientes.setEditable(false);
        txtDisponibilidad.setEditable(false);
        txtValorLocal.setEditable(false);
        txtValorAdministracion.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoLocal.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        txtMesesPendientes.setEditable(false);
        txtDisponibilidad.setEditable(true);
        txtValorLocal.setEditable(true);
        txtValorAdministracion.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoLocal.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear(); 
        txtMesesPendientes.clear();
        txtDisponibilidad.clear();
        txtValorLocal.clear();
        txtDisponibilidad.clear();
        txtValorAdministracion.clear();
    }
    
    
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
        public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    
    
    }
    
        
        
    public void ventanaCuentasPorCobrar(){
    escenarioPrincipal.ventanaCuentasPorCobrar();
    
    
    }
    
}
