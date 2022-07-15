package org.reneyuman.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.reneyuman.bean.Administracion;
import org.reneyuman.bean.Cargos;
import org.reneyuman.bean.Departamentos;
import org.reneyuman.bean.Empleados;
import org.reneyuman.bean.Horarios;
import org.reneyuman.db.Conexion;
import org.reneyuman.report.GenerarReporte;
import org.reneyuman.system.Principal;

public class EmpleadosController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    public ObservableList<Empleados> listaEmpleado;
    public ObservableList<Departamentos> listaDepartamento;
    public ObservableList<Cargos> listaCargo;
    public ObservableList<Horarios> listaHorario;
    public ObservableList<Administracion> listaAdministracion;
    private DatePicker fechaContratacion;
    
    
    @FXML private TextField txtCodigoEmpleados;
    @FXML private TextField txtNombreEmpleados;
    @FXML private TextField txtApellidoEmpleados;
    @FXML private TextField txtCorreoElectronico;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtSueldo;
    @FXML private ComboBox cmbCodigoDepartamento;
    @FXML private ComboBox cmbCodigoCargos;
    @FXML private ComboBox cmbCodigoHorarios;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodigoEmpleados;
    @FXML private TableColumn colNombreEmpleados;
    @FXML private TableColumn colApellidoEmpleados;
    @FXML private TableColumn colCorreoElectronico;
    @FXML private TableColumn colTelefono;
    @FXML private TableColumn colFechaContratacion;
    @FXML private TableColumn colSueldo;
    @FXML private TableColumn colCodigoDepartamento;
    @FXML private TableColumn colCodigoCargos;
    @FXML private TableColumn colCodigoHorarios;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgReporte;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private GridPane grpFechaContratacion;
            
            
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fechaContratacion = new DatePicker(Locale.ENGLISH);
        fechaContratacion.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaContratacion.getCalendarView().todayButtonTextProperty().set("Today");
        fechaContratacion.getCalendarView().setShowWeeks(false);
        grpFechaContratacion.add(fechaContratacion, 5, 1);
        fechaContratacion.getStylesheets().add("/org/reneyuman/resource/DatePicker.css");
        cargarDatos();
    }

    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleado());
        colCodigoEmpleados.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoEmpleado"));
        colNombreEmpleados.setCellValueFactory(new PropertyValueFactory<Empleados,String>("nombreEmpleado"));
        colApellidoEmpleados.setCellValueFactory(new PropertyValueFactory<Empleados,String>("apellidoEmpleado"));
        colCorreoElectronico.setCellValueFactory(new PropertyValueFactory<Empleados,String>("correoElectronico"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleados,String>("telefonoEmpleado"));
        colFechaContratacion.setCellValueFactory(new PropertyValueFactory<Empleados,Date>("fechaContratacion"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados,Double>("sueldo"));
        colCodigoDepartamento.setCellValueFactory(new PropertyValueFactory<Departamentos,Integer>("codigoDepartamento"));
        colCodigoCargos.setCellValueFactory(new PropertyValueFactory<Cargos,Integer>("codigoCargos"));
        colCodigoHorarios.setCellValueFactory(new PropertyValueFactory<Horarios,Integer>("codigoHorario"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        
        cmbCodigoDepartamento.setItems(getDepartamento());
        cmbCodigoCargos.setItems(getCargo());
        cmbCodigoHorarios.setItems(getHorario());
        cmbCodigoAdministracion.setItems(getAdministracion());
    }
    
    public ObservableList<Empleados> getEmpleado(){
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarEmpleado()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                                                resultado.getString("nombreEmpleado"),
                                                resultado.getString("apellidoEmpleado"),
                                                resultado.getString("correoElectronico"),
                                                resultado.getString("telefonoEmpleado"),
                                                resultado.getDate("fechaContratacion"),
                                                resultado.getDouble("sueldo"),
                                                resultado.getInt("codigoDepartamento"),
                                                resultado.getInt("codigoCargos"),
                                                resultado.getInt("codigoHorario"),
                                                resultado.getInt("codigoAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpleado = FXCollections.observableArrayList(lista);
    }
    
    
    public ObservableList<Departamentos> getDepartamento(){
        ArrayList<Departamentos> lista = new ArrayList<Departamentos>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDepartamentos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Departamentos(resultado.getInt("codigoDepartamento"),
                                           resultado.getString("nombreDepartamento")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaDepartamento = FXCollections.observableArrayList(lista);
    }
     
    public Departamentos buscarDepartamento(int codigoDepartamento){
           Departamentos resultado = null;
           
           try{
               PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ call sp_BuscarDepartamentos (?)}");
               procedimiento.setInt(1, codigoDepartamento);
               ResultSet registro = procedimiento.executeQuery();
               while (registro.next()){
                   resultado = new Departamentos(registro.getInt("codigoDepartamento"),
                           registro.getString("nombreDepartamento") 
                   );
               }
           }catch(Exception e){
               e.printStackTrace();
           }   
                return resultado;
       }         
    
    public ObservableList<Cargos> getCargo(){
        ArrayList<Cargos> lista = new ArrayList<Cargos>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cargos(resultado.getInt("codigoCargos"),
                                    resultado.getString("nombreCargo")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCargo = FXCollections.observableArrayList(lista);
    }
   
    public Cargos buscarCargo(int codigoCargo){
           Cargos resultado = null;
           try{
               PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ call sp_BuscarCargos(?)}");
               procedimiento.setInt(1, codigoCargo);
               ResultSet registro = procedimiento.executeQuery();
               while (registro.next()){
                   resultado = new Cargos(registro.getInt("codigoCargos"),
                           registro.getString("nombreCargo")
                   );
               }
           }catch(Exception e){
               e.printStackTrace();
           }
                return resultado;
       }   
   
    public ObservableList<Horarios> getHorario(){
       ArrayList<Horarios> lista = new ArrayList<Horarios>();
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarHorarios()}");
           ResultSet resultado = procedimiento.executeQuery();
           while (resultado.next()){
               lista.add(new Horarios(resultado.getInt("codigoHorario"),
                                        resultado.getString("horarioEntrada"),
                                        resultado.getString("horarioSalida"),
                                        resultado.getBoolean("lunes"),
                                        resultado.getBoolean("martes"),
                                        resultado.getBoolean("miercoles"),
                                        resultado.getBoolean("jueves"),
                                        resultado.getBoolean("viernes")));    
           }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaHorario = FXCollections.observableArrayList(lista);
    }    
   
    public Horarios buscarHorario(int codigoHorario){
           Horarios resultado = null;
           
           try{
               PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ call sp_BuscarHorarios(?)}");
               procedimiento.setInt(1, codigoHorario);
               ResultSet registro = procedimiento.executeQuery();
               while (registro.next()){
                   resultado = new Horarios(registro.getInt("codigoHorario"),
                           registro.getString("horarioEntrada"),
                           registro.getString("horarioSalida"),
                           registro.getBoolean("lunes"),
                           registro.getBoolean("martes"),
                           registro.getBoolean("miercoles"),
                           registro.getBoolean("jueves"),
                           registro.getBoolean("viernes"));
               } 
           }catch(Exception e){
               e.printStackTrace();
           }  
                   return resultado;
       }         
   
    public ObservableList<Administracion> getAdministracion(){
        ArrayList<Administracion> lista = new ArrayList<Administracion>();
        try {
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
               PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ call sp_BuscarAdministracion (?)}");
               procedimiento.setInt(1, codigoAdministracion);
               ResultSet registro = procedimiento.executeQuery();
               while (registro.next()){
                   resultado = new Administracion(registro.getInt("codigoAdministracion"),
                           registro.getString("direccion"),
                           registro.getString("telefono"));
               }  
           }catch(Exception e){
               e.printStackTrace();
           }    
                   return resultado;
       }
    
   
   
   public void seleccionarElemento(){
       if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
           txtCodigoEmpleados.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
           txtNombreEmpleados.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado()));
           txtApellidoEmpleados.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado()));
           txtCorreoElectronico.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCorreoElectronico()));
           txtTelefono.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoEmpleado()));
           fechaContratacion.selectedDateProperty().set(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getFechaContratacion());
           txtSueldo.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
           cmbCodigoDepartamento.getSelectionModel().select(buscarDepartamento(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
           cmbCodigoCargos.getSelectionModel().select(buscarCargo(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargos()));
           cmbCodigoHorarios.getSelectionModel().select(buscarHorario(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoHorario()));
           cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
       } 
    }
   
   
   
   public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                activarComboBox();
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
        Empleados registro = new Empleados();
        registro.setNombreEmpleado(txtNombreEmpleados.getText());
        registro.setApellidoEmpleado(txtApellidoEmpleados.getText());
        registro.setCorreoElectronico(txtCorreoElectronico.getText());
        registro.setTelefonoEmpleado(txtTelefono.getText());
        registro.setFechaContratacion(fechaContratacion.getSelectedDate());
        registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
        registro.setCodigoDepartamento(((Departamentos)cmbCodigoDepartamento.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
        registro.setCodigoCargos(((Cargos)cmbCodigoCargos.getSelectionModel().getSelectedItem()).getCodigoCargos());
        registro.setCodigoHorario(((Horarios)cmbCodigoHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarEmpleado(?,?,?,?,?,?,?,?,?,?)}");
                procedimiento.setString(1, registro.getNombreEmpleado());
                procedimiento.setString(2, registro.getApellidoEmpleado());
                procedimiento.setString(3, registro.getCorreoElectronico());
                procedimiento.setString(4, registro.getTelefonoEmpleado());
                procedimiento.setDate(5, new java.sql.Date(registro.getFechaContratacion().getTime()));
                procedimiento.setDouble(6, registro.getSueldo());
                procedimiento.setInt(7, registro.getCodigoDepartamento());
                procedimiento.setInt(8, registro.getCodigoCargos());
                procedimiento.setInt(9, registro.getCodigoHorario());
                procedimiento.setInt(10, registro.getCodigoAdministracion());
                procedimiento.execute();
                listaEmpleado.add(registro);
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
                imgNuevo.setImage(new Image("/org/edgarsinay/images/Add.png"));
                imgEliminar.setImage(new Image("/org/edgarsinay/images/Delete.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblEmpleados.getSelectionModel().getSelectedItem()!= null){
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?","Eliminar Empleados", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                       try{
                           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarEmpleado(?)}");
                           procedimiento.setInt(1,((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()); 
                           procedimiento.execute();
                           listaEmpleado.remove(tblEmpleados.getSelectionModel().getSelectedIndex());
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
                if(tblEmpleados.getSelectionModel().getSelectedItem()!= null){
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarEmpleado(?,?,?,?,?,?,?)}");
            Empleados registro = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setNombreEmpleado(txtNombreEmpleados.getText());
            registro.setApellidoEmpleado(txtApellidoEmpleados.getText());
            registro.setCorreoElectronico(txtCorreoElectronico.getText());
            registro.setTelefonoEmpleado(txtTelefono.getText());
            registro.setFechaContratacion(fechaContratacion.getSelectedDate());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombreEmpleado());
            procedimiento.setString(3, registro.getApellidoEmpleado());
            procedimiento.setString(4, registro.getCorreoElectronico());
            procedimiento.setString(5, registro.getTelefonoEmpleado());        
            procedimiento.setDate(6, new java.sql.Date(registro.getFechaContratacion().getTime()));
            procedimiento.setDouble(7, registro.getSueldo());      
            procedimiento.execute();
            cargarDatos();
            tipoDeOperacion = operaciones.NINGUNO;    
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
        int codEmp = ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado();
        parametros.put("codEmp", codEmp);
        GenerarReporte.mostrarReporte("ReporteEmpleado.jasper", "Reporte Empleado", parametros);
    
    }
    
    
    
    public void desactivarControles(){
       txtCodigoEmpleados.setEditable(false);
       txtNombreEmpleados.setEditable(false);
       txtApellidoEmpleados.setEditable(false);
       txtCorreoElectronico.setEditable(false);
       txtTelefono.setEditable(false);
       fechaContratacion.setDisable(true);
       txtSueldo.setEditable(false);
       cmbCodigoDepartamento.setDisable(true);
       cmbCodigoCargos.setDisable(true);
       cmbCodigoHorarios.setDisable(true);
       cmbCodigoAdministracion.setDisable(true);
   }
   
   public void activarControles(){
       txtCodigoEmpleados.setEditable(false);
       txtNombreEmpleados.setEditable(true);
       txtApellidoEmpleados.setEditable(true);
       txtCorreoElectronico.setEditable(true);
       txtTelefono.setEditable(true);
       fechaContratacion.setDisable(false);
       txtSueldo.setEditable(true);
   }
   
   public void activarComboBox(){
       cmbCodigoDepartamento.setDisable(false);
       cmbCodigoCargos.setDisable(false);
       cmbCodigoHorarios.setDisable(false);
       cmbCodigoAdministracion.setDisable(false);  
   }
   
   public void limpiarControles(){
       txtCodigoEmpleados.clear();
       txtNombreEmpleados.clear();
       txtApellidoEmpleados.clear();
       txtCorreoElectronico.clear();
       txtTelefono.clear();
       fechaContratacion.setSelectedDate(null);
       txtSueldo.clear();
         cmbCodigoDepartamento.setValue(null);
       cmbCodigoCargos.setValue(null);
       cmbCodigoHorarios.setValue(null);
       cmbCodigoAdministracion.setValue(null);
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
    
   public void ventanaCargos(){
   escenarioPrincipal.ventanaCargos();
   }
}
