
package org.reneyuman.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.reneyuman.system.Principal;


public class ProgramadorController implements Initializable{
    private Principal escenarioPrincipal;
    @FXML Button btnProgramador;
    @FXML Button btnKinal;
    @FXML Label lblNombres;
    @FXML Label lblApellidos;
    @FXML Label lblTitulo;
    @FXML ImageView ImgFoto;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    
    }
    
        @FXML
      private void opciones(ActionEvent event){
          if(event.getSource()==btnProgramador){
              
              lblNombres.setText("Rene Alejandro");
              lblApellidos.setText("Yuman Barco");
              lblTitulo.setText("Programador:");
              ImgFoto.setImage(new Image("/org/reneyuman/images/FotoProgramador.png"));
              
          }

      }
    
      @FXML
    private void Administracion(ActionEvent event){
          if(event.getSource()==btnKinal){
              
              lblNombres.setText("Fundacion Kinal");
              lblApellidos.setText("");
              lblTitulo.setText("Administracion");
              ImgFoto.setImage(new Image("/org/reneyuman/images/LogoRecortado.png"));
              
          }

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
    
    
    
    
}
