package org.reneyuman.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.reneyuman.bean.TipoClientes;
import org.reneyuman.db.Conexion;
import org.reneyuman.report.GenerarReporte;
import org.reneyuman.system.Principal;


public class TipoClientesController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<TipoClientes> listaTipoClientes;
    
    
    @FXML TableView tblTipoClientes;
    @FXML TableColumn colCodigoTipoClientes;
    @FXML TableColumn colDescripcion;
    @FXML TextField txtCodigoTipoClientes;
    @FXML TextField txtDescripcion;
    @FXML Button btnNuevo;
    @FXML Button btnEliminar;
    @FXML Button btnEditar;
    @FXML Button btnReporte;
    @FXML ImageView imgNuevo;
    @FXML ImageView imgEliminar;
    @FXML ImageView imgEditar;
    @FXML ImageView imgReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        }
    
    
    
    
   
    public void cargarDatos(){
        tblTipoClientes.setItems(getTipoClientes());
        colCodigoTipoClientes.setCellValueFactory(new PropertyValueFactory<TipoClientes,Integer>("codigoTipoCliente"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TipoClientes,String>("descripcion"));
        
    }
    
    
    
    public ObservableList<TipoClientes> getTipoClientes(){
        ArrayList<TipoClientes> lista = new ArrayList<TipoClientes>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarTipoCliente()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TipoClientes(resultado.getInt("codigoTipoCliente"),
                                                resultado.getString("descripcion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
        return listaTipoClientes = FXCollections.observableArrayList(lista);
    }
    
    
    
    
    public void seleccionarElemento(){
    
    if(tblTipoClientes.getSelectionModel().getSelectedItem()!= null){
        txtCodigoTipoClientes.setText(String.valueOf(((TipoClientes)tblTipoClientes.getSelectionModel().getSelectedItem()).getCodigoTipoCliente()));
        txtDescripcion.setText(((TipoClientes)tblTipoClientes.getSelectionModel().getSelectedItem()).getDescripcion());
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
    
    public void guardar(){
        TipoClientes registro = new TipoClientes();
        registro.setDescripcion(txtDescripcion.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoCliente(?)}");
            procedimiento.setString(1, registro.getDescripcion());
            procedimiento.execute();
            listaTipoClientes.add(registro);
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
                if (tblTipoClientes.getSelectionModel().getSelectedItem()!= null ){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Tipo de Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("CALL sp_EliminarTipoCliente(?)");
                        procedimiento.setInt(1,((TipoClientes)tblTipoClientes.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
                        procedimiento.execute();
                        listaTipoClientes.remove(tblTipoClientes.getSelectionModel().getSelectedIndex());
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
    
    
    
    
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblTipoClientes.getSelectionModel().getSelectedItem()!= null){
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarTipoCliente(?,?)");
            TipoClientes registro = (TipoClientes)tblTipoClientes.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcion.getText());
            procedimiento.setInt(1, registro.getCodigoTipoCliente());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void reporte(){
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
               /* reporte */
                JOptionPane.showMessageDialog(null, "Informe");
                break;
        }
    }
    
    
    
    
    
    
    
    public void desactivarControles(){
        txtCodigoTipoClientes.setEditable(false);
        txtDescripcion.setEditable(false);
        
    }
    
    public void activarControles(){
        txtCodigoTipoClientes.setEditable(false);
        txtDescripcion.setEditable(true);
        
    }
    
    public void limpiarControles(){
        txtCodigoTipoClientes.clear();
        txtDescripcion.clear();
        
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

        public void ventanaClientes(){
        escenarioPrincipal.ventanaClientes();
        
        
        }
    
    
}
