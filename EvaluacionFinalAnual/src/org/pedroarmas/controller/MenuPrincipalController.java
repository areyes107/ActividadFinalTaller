package org.pedroarmas.controller;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.pedroarmas.sistema.Principal;


public class MenuPrincipalController implements Initializable{

    private Principal escenarioPrincipal;
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    public void setEscenarioPrincipal(Principal EscenarioPrincipal) {
         this.escenarioPrincipal = EscenarioPrincipal;
    }
    public void actionMaestros() throws Exception{
        escenarioPrincipal.cambiarEscena("MaestroView.fxml", 932, 517);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    
    
    
}
