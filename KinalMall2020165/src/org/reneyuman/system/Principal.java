/*
Programador: Rene Alejandro Yuman Barco
carné: 2020165

modificaciones recientes:
16/06/2021: cargar datos y seleccionar datos de Clientes
17/06/2021: Arreglar cosas de la vista de Clientes

 */
package org.reneyuman.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.reneyuman.bean.Proveedor;
import org.reneyuman.controller.AdministracionController;
import org.reneyuman.controller.CargosController;
import org.reneyuman.controller.ClientesController;
import org.reneyuman.controller.CuentaPorPagarController;
import org.reneyuman.controller.CuentasPorCobrarController;
import org.reneyuman.controller.DepartamentosController;
import org.reneyuman.controller.EmpleadosController;
import org.reneyuman.controller.HorariosController;
import org.reneyuman.controller.LocalesController;
import org.reneyuman.controller.LoginController;
import org.reneyuman.controller.MenuPrincipalController;
import org.reneyuman.controller.ProgramadorController;
import org.reneyuman.controller.ProveedorController;
import org.reneyuman.controller.TipoClientesController;
import org.reneyuman.controller.UsuarioController;

/**
 *
 * @author Alejandro
 */
public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/reneyuman/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Mall 2021");
//        escenarioPrincipal.getIcons().add (new Image("/org/reneyuman/images/icono1.png"));
//        Parent root = FXMLLoader.load(getClass().getResource("/org/reneyuman/view/MenuPrincipalView.fxml"));
//        Scene escena = new Scene(root);
//        escenarioPrincipal.setScene(escena);
        ventanaLogin();
        // menuPrincipal();
        // ventanaUsuarios();
        escenarioPrincipal.show();
        
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",600,400);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        
        }
    
    }
    
    public void ventanaProgramador(){
        try{
            ProgramadorController programador = (ProgramadorController)cambiarEscena("DatosPersonalesView.fxml",600,400);
            programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
   
    }
    }
    
    
    
    
    
    public void  ventanaAdministracion(){
    
        try{
            AdministracionController admin = (AdministracionController)cambiarEscena("AdministracionView.fxml",800,499);
            admin.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        
        }
    }
    
        
    public void  ventanaTipoCliente(){
    
        try{
            TipoClientesController Tipo = (TipoClientesController)cambiarEscena("TipoClientesView.fxml",800,499);
            Tipo.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        
        }
    }
    
    
    
    public void  ventanaDepartamentos(){
    
        try{
            DepartamentosController Depa = (DepartamentosController)cambiarEscena("DepartamentosView.fxml",915,499);
            Depa.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        
        }
    }
    
    
    
    public void  ventanaLocales(){
    
        try{
            LocalesController Local = (LocalesController)cambiarEscena("LocalesView.fxml",1200,499);
            Local.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        
        }
    }
    
    public void  ventanaClientes(){
    
        try{
            ClientesController Cliente = (ClientesController)cambiarEscena("ClientesView.fxml",1294,499);
            Cliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        
        }
    }
    
    
    public void ventanaProveedores(){
        try{
        ProveedorController Proveedor = (ProveedorController)cambiarEscena("ProveedorView.fxml",1214,499);
        Proveedor.setEscenarioPrincipal(this);
        
        }catch(Exception e){
            
        e.printStackTrace();
        }
    
    
    }
            
            
    public void ventanaCuentasPorPagar(){
        
    try{
        CuentaPorPagarController CuentasPagar = (CuentaPorPagarController)cambiarEscena("CuentasPorPagarView.fxml",1219,499);
        CuentasPagar.setEscenarioPrincipal(this);
        
        }catch(Exception e){
            
        e.printStackTrace();
        }
    
    }
    
    
    public void ventanaHorarios(){
        
    try{
        HorariosController Horarios = (HorariosController)cambiarEscena("HorariosView.fxml",1115,499);
        Horarios.setEscenarioPrincipal(this);
        
        }catch(Exception e){
            
        e.printStackTrace();
        }
    
    }
    
    public void ventanaUsuarios(){
        
    try{
        UsuarioController Usuario = (UsuarioController)cambiarEscena("UsuarioView.fxml",673,432);
        Usuario.setEscenarioPrincipal(this);
        
        }catch(Exception e){
            
        e.printStackTrace();
        }
    
    }
    
    
    public void ventanaLogin(){
        
    try{
        LoginController Login = (LoginController)cambiarEscena("LoginView.fxml",598,362);
        Login.setEscenarioPrincipal(this);
        
        }catch(Exception e){
            
        e.printStackTrace();
        }
    
    }
   
    
    
    
    public void ventanaCuentasPorCobrar(){
        
    try{
        CuentasPorCobrarController CuentasCobrar = (CuentasPorCobrarController)cambiarEscena("CuentasPorCobrarView.fxml",1294,499);
        CuentasCobrar.setEscenarioPrincipal(this);
        
        }catch(Exception e){
            
        e.printStackTrace();
        }
    
    }
    
    
    
    public void ventanaCargos(){
        
    try{
        CargosController Cargos = (CargosController)cambiarEscena("CargosView.fxml",915,499);
        Cargos.setEscenarioPrincipal(this);
        
        }catch(Exception e){
            
        e.printStackTrace();
        }
    
    }
    
    
    
    
    public void ventanaEmpleados(){
        
    try{
        EmpleadosController Empleados = (EmpleadosController)cambiarEscena("EmpleadosView.fxml",1332,499);
        Empleados.setEscenarioPrincipal(this);
        
        }catch(Exception e){
            
        e.printStackTrace();
        }
    
    }
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
     public Initializable cambiarEscena(String fxml,int ancho, int alto) throws IOException{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    
}


   