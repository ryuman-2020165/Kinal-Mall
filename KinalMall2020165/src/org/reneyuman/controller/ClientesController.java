
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.reneyuman.bean.Administracion;
import org.reneyuman.bean.Cliente;
import org.reneyuman.bean.Locales;
import org.reneyuman.bean.TipoClientes;
import org.reneyuman.db.Conexion;
import org.reneyuman.report.GenerarReporte;
import org.reneyuman.system.Principal;


public class ClientesController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Cliente> listaCliente;
    private ObservableList<Locales> listaLocal;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<TipoClientes> listaTipoClientes;
    
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCliente;
    @FXML private TextField txtNombresCliente;
    @FXML private TextField txtApellidosCliente;
    @FXML private TextField txtTelefonoCliente;
    @FXML private TextField txtDireccionCliente;
    @FXML private TextField txtEmail;
    @FXML private ComboBox cmbCodigoLocal;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private ComboBox cmbCodigoTipoCliente;
    @FXML private TableView tblClientes;
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colNombresCliente;
    @FXML private TableColumn colApellidosCliente;
    @FXML private TableColumn colTelefonoCliente;
    @FXML private TableColumn colDireccionCliente;
    @FXML private TableColumn colEmail;
    @FXML private TableColumn colCodigoLocal;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoTipoCliente;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos(){
        tblClientes.setItems(getCliente());
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("codigoCliente"));
        colNombresCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombresCliente"));
        colApellidosCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidosCliente"));
        colTelefonoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefonoCliente"));
        colDireccionCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccionCliente"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<Locales, Integer>("codigoLocal"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion, Integer>("codigoAdministracion"));
        colCodigoTipoCliente.setCellValueFactory(new PropertyValueFactory<TipoClientes, Integer>("codigoTipoCliente"));
        cmbCodigoLocal.setItems(getLocal());
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoTipoCliente.setItems(getTipoClientes());
    }
    
    
    public void seleccionarElemento(){
        if(tblClientes.getSelectionModel().getSelectedItem()!= null){
        txtCodigoCliente.setText(String.valueOf(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        txtNombresCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getNombresCliente());
        txtApellidosCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getApellidosCliente());
        txtTelefonoCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
        txtDireccionCliente.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
        txtEmail.setText(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getEmail());
        cmbCodigoLocal.getSelectionModel().select(buscarLocal(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoLocal()));
        cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        cmbCodigoTipoCliente.getSelectionModel().select(buscarTipoClientes(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
    }}
    
    
    
    public ObservableList<Cliente> getCliente(){
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cliente(resultado.getInt("codigoCliente"),
                        resultado.getString("nombresCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("email"),
                        resultado.getInt("codigoLocal"),
                        resultado.getInt("codigoAdministracion"),
                        resultado.getInt("codigoTipoCliente")
                ));
                
            
            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        
        return listaCliente = FXCollections.observableArrayList(lista);
    }
    
    
    
    
    public ObservableList<Locales> getLocal(){
        ArrayList<Locales> lista = new ArrayList<Locales>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{Call sp_ListarLocal()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Locales(resultado.getInt("codigoLocal"),
                        resultado.getDouble("saldoFavor"),
                        resultado.getDouble("saldoContra"),
                        resultado.getInt("mesesPendientes"),
                        resultado.getBoolean("disponibilidad"),
                        resultado.getDouble("valorLocal"),
                        resultado.getDouble("valorAdministracion")
                ));
            }
        }catch(Exception e){
        }
    
        return listaLocal = FXCollections.observableArrayList(lista);
    }
    
    
    public Locales buscarLocal(int codigoLocal){
        Locales resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarLocales(?)}");
            procedimiento.setInt(1, codigoLocal);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Locales(registro.getInt("codigoLocal"),
                             registro.getDouble("saldoFavor"),
                             registro.getDouble("saldoContra"),
                             registro.getInt("mesesPendientes"),
                             registro.getBoolean("disponibilidad"),
                             registro.getDouble("valorLocal"),
                             registro.getDouble("valorAdministracion")
                );
            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    
    
    
    public ObservableList<Administracion> getAdministracion(){
        ArrayList<Administracion> lista = new ArrayList<Administracion>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdministracion()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"),
                                                resultado.getString("direccion"),
                                                resultado.getString("telefono")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }
    
    
    public Administracion buscarAdministracion(int codigoAdministracion){
    Administracion resultado = null;
        try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarAdministracion(?)}");
        procedimiento.setInt(1,codigoAdministracion);
        ResultSet registro = procedimiento.executeQuery();
        while(registro.next()){
            resultado = new Administracion(registro.getInt("codigoAdministracion"),
                            registro.getString("direccion"),
                            registro.getString("telefono")  
            );
        }
        }catch(Exception e){
        e.printStackTrace();
        }
        
        
    return resultado;
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
    
    
    public TipoClientes buscarTipoClientes(int CodigoTipoCliente){
    TipoClientes resultado = null;
        try{
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarTipoCliente(?)}");
        procedimiento.setInt(1,CodigoTipoCliente);
        ResultSet registro = procedimiento.executeQuery();
        while(registro.next()){
            resultado = new TipoClientes(registro.getInt("CodigoTipoCliente"),
                            registro.getString("descripcion")
                            
            );
        }
        }catch(Exception e){
        e.printStackTrace();
        }
        
        
    return resultado;
    }
    
    
    
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                ActivarComboBox();
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
//                DesactivarComboBox();
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
    Cliente registro = new Cliente();
    registro.setNombresCliente(txtNombresCliente.getText());
    registro.setApellidosCliente(txtApellidosCliente.getText());
    registro.setTelefonoCliente(txtTelefonoCliente.getText());
    registro.setDireccionCliente(txtDireccionCliente.getText());
    registro.setEmail(txtEmail.getText());
    registro.setCodigoLocal(((Locales)cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
    registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
    registro.setCodigoTipoCliente(((TipoClientes)cmbCodigoTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
    try{
     PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes(?,?,?,?,?,?,?,?)}");
     procedimiento.setString(1, registro.getNombresCliente());
     procedimiento.setString(2, registro.getApellidosCliente());
     procedimiento.setString(3, registro.getTelefonoCliente());
     procedimiento.setString(4, registro.getDireccionCliente());
     procedimiento.setString(5, registro.getEmail());
     procedimiento.setInt(6, registro.getCodigoLocal());
     procedimiento.setInt(7, registro.getCodigoAdministracion());
     procedimiento.setInt(8, registro.getCodigoTipoCliente());
     procedimiento.execute();
     listaCliente.add(registro);
    }catch(Exception e){
        e.printStackTrace();
        
    }}
    
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
                if(tblClientes.getSelectionModel().getSelectedItem()!= null){
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?","Eliminar Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                       try{
                           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                           procedimiento.setInt(1,((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()); 
                           procedimiento.execute();
                           listaCliente.remove(tblClientes.getSelectionModel().getSelectedIndex());
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
                if(tblClientes.getSelectionModel().getSelectedItem() != null){
                 btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/reneyuman/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/reneyuman/images/cancelar.png"));
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                
                }else{JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
                
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
        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarClientes(?,?,?,?,?,?)}");
        Cliente registro = (Cliente)tblClientes.getSelectionModel().getSelectedItem();
        registro.setNombresCliente(txtNombresCliente.getText());
        registro.setApellidosCliente(txtApellidosCliente.getText());
        registro.setTelefonoCliente(txtTelefonoCliente.getText());
        registro.setDireccionCliente(txtDireccionCliente.getText());
        registro.setEmail(txtEmail.getText());
      
        
        procedimiento.setInt(1, registro.getCodigoCliente());
        procedimiento.setString(2, registro.getNombresCliente());
        procedimiento.setString(3, registro.getApellidosCliente());
        procedimiento.setString(4, registro.getTelefonoCliente());
        procedimiento.setString(5, registro.getDireccionCliente());
        procedimiento.setString(6, registro.getEmail());
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
                imprimirReporte();
                break;
        }
    }
    
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        int codClientes = ((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente();
        parametros.put("codClientes", codClientes);
        GenerarReporte.mostrarReporte("ReporteClientesCompleto.jasper", "Reporte Cliente", parametros);
    
    }
    
    public void desactivarControles(){
        txtCodigoCliente.setEditable(false);
        txtNombresCliente.setEditable(false);
        txtApellidosCliente.setEditable(false);
        txtTelefonoCliente.setEditable(false);
        txtDireccionCliente.setEditable(false);
        txtEmail.setEditable(false);
        cmbCodigoLocal.setDisable(true);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoTipoCliente.setDisable(true);
    }
    
    
    public void activarControles(){
        txtCodigoCliente.setEditable(false);
        txtNombresCliente.setEditable(true);
        txtApellidosCliente.setEditable(true);
        txtTelefonoCliente.setEditable(true);
        txtDireccionCliente.setEditable(true);
        txtEmail.setEditable(true);
        
        
    
    }
    
    
    public void ActivarComboBox(){
        cmbCodigoLocal.setDisable(false);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoTipoCliente.setDisable(false);
    
    
    }
    
    
//    public void DesactivarComboBox(){
//        cmbCodigoLocal.setDisable(true);
//        cmbCodigoAdministracion.setDisable(true);
//        cmbCodigoTipoCliente.setDisable(true);
//    
//    
//    }
    
    public void limpiarControles(){
        txtCodigoCliente.clear();
        txtNombresCliente.clear();
        txtApellidosCliente.clear();
        txtTelefonoCliente.clear();
        txtDireccionCliente.clear();
        txtEmail.clear();
        cmbCodigoLocal.getSelectionModel().clearSelection();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
        cmbCodigoTipoCliente.getSelectionModel().clearSelection();
    
    
        
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
    
    
    public void ventanaTipoClientes(){
        escenarioPrincipal.ventanaTipoCliente();
    
    }
}
