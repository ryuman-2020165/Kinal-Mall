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
import org.reneyuman.bean.Cargos;
import org.reneyuman.db.Conexion;
import org.reneyuman.system.Principal;


public class CargosController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Cargos> listaCargos;
    
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCargos;
    @FXML private TextField txtNombreCargos;
    @FXML private TableView tblCargos;
    @FXML private TableColumn colCodigoCargos;
    @FXML private TableColumn colNombreCargos;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgReporte;
    
    
    
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    
    }
    
    
    
     public void cargarDatos(){
        tblCargos.setItems(getCargos());
        colCodigoCargos.setCellValueFactory(new PropertyValueFactory<Cargos,Integer>("codigoCargos"));
        colNombreCargos.setCellValueFactory(new PropertyValueFactory<Cargos,String>("nombreCargo"));
        
    }
    
    
    
    public ObservableList<Cargos> getCargos(){
        ArrayList<Cargos> lista = new ArrayList<Cargos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cargos(resultado.getInt("codigoCargos"),
                                                resultado.getString("nombreCargo")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
        return listaCargos = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
    if(tblCargos.getSelectionModel().getSelectedItem()!= null){
        txtCodigoCargos.setText(String.valueOf(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargos()));
        txtNombreCargos.setText(((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getNombreCargo());
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
        Cargos registro = new Cargos();
        registro.setNombreCargo(txtNombreCargos.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargos(?)}");
            procedimiento.setString(1, registro.getNombreCargo());
            procedimiento.execute();
            listaCargos.add(registro);
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
                if (tblCargos.getSelectionModel().getSelectedItem()!= null ){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Departamento", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("CALL sp_ELiminarCargos(?)");
                        procedimiento.setInt(1,((Cargos)tblCargos.getSelectionModel().getSelectedItem()).getCodigoCargos());
                        procedimiento.execute();
                        listaCargos.remove(tblCargos.getSelectionModel().getSelectedIndex());
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
                if(tblCargos.getSelectionModel().getSelectedItem()!= null){
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarCargos(?,?)");
            Cargos registro = (Cargos)tblCargos.getSelectionModel().getSelectedItem();
            registro.setNombreCargo(txtNombreCargos.getText());
            procedimiento.setInt(1, registro.getCodigoCargos());
            procedimiento.setString(2, registro.getNombreCargo());
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
                
                JOptionPane.showMessageDialog(null, "Informe");
                break;
        }
    }
    
    
    
    
    
    
    
    
    
    public void desactivarControles(){
        txtCodigoCargos.setEditable(false);
        txtNombreCargos.setEditable(false);
        
    }
    
    public void activarControles(){
        txtCodigoCargos.setEditable(false);
        txtNombreCargos.setEditable(true);
        
    }
    
    public void limpiarControles(){
        txtCodigoCargos.clear();
        txtNombreCargos.clear();
        
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
    
    
    public void ventanaEmpleados(){
    escenarioPrincipal.ventanaEmpleados();
    }
}
