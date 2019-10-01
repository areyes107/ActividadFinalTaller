package org.pedroarmas.controller;


import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.pedroarmas.bean.Maestro;
import org.pedroarmas.db.Conexion;
import org.pedroarmas.report.GenerarReporte;


public class MaestroController implements Initializable{

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().setValue("Today");
        fecha.getCalendarView().setShowWeeks(false);
        fecha.getStylesheets().add("/org/pedroarmas/resources/DatePicker.css");
        grpFecha.add(fecha, 0, 0);
    }
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList <Maestro> listaMaestro;
    private DatePicker fecha;
    @FXML private GridPane grpFecha;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtSexo;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TableView tblMaestros;
    @FXML private TableColumn colCodigoMaestro;
    @FXML private TableColumn colNombreMaestro;
    @FXML private TableColumn colApellidosMaestro;
    @FXML private TableColumn colFechaNacimiento;
    @FXML private TableColumn colSexo;
    @FXML private TableColumn colTitulo;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colTelefono;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    public void nuevo(){
        switch (tipoDeOperacion){
        case NINGUNO:
            limpiarControles();
            activarControles();
            btnNuevo.setText("Guardar");
            btnEliminar.setText("Cancelar");
            btnReporte.setDisable(true);
            btnEditar.setDisable(true);
            tipoDeOperacion = operaciones.GUARDAR;
            break;
            
        case GUARDAR:
            guardar();
            limpiarControles();
            btnNuevo.setText("Nuevo");
            btnEliminar.setText("Eliminar");
            btnEditar.setDisable(false);
            btnReporte.setDisable(false);
            tipoDeOperacion = operaciones.NINGUNO;
            cargarDatos();
            desactivarControles();
            break;
        }
    }
    
    public void guardar(){
        Maestro registro = new Maestro();
        registro.setNombreMaestro(txtNombre.getText());
        registro.setApellidosMaestro(txtApellidos.getText());
        registro.setFechaNacimiento(fecha.getSelectedDate());
        registro.setSexo(txtSexo.getText());
        registro.setTitulo(txtTitulo.getText());
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarMaestro(?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNombreMaestro());
            procedimiento.setString(2, registro.getApellidosMaestro());
            procedimiento.setDate(3 , new java.sql.Date(registro.getFechaNacimiento().getTime()));
            procedimiento.setString(4, registro.getSexo());
            procedimiento.setString(5, registro.getTitulo());
            procedimiento.setString(6, registro.getDireccion());
            procedimiento.setString(7, registro.getTelefono());
            procedimiento.execute();
            listaMaestro.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
            
    }
    
    
    
    public void eliminar(){
      switch (tipoDeOperacion){
          case GUARDAR:
              desactivarControles();
              limpiarControles();
              btnNuevo.setText("Nuevo");
              btnEliminar.setText("Eliminar");
              btnEditar.setDisable(false);
              btnReporte.setDisable(false);
              tipoDeOperacion = operaciones.NINGUNO;
              break;
          default:
              if(tblMaestros.getSelectionModel().getSelectedItem() != null){
                  int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que quiere eliminar el registro?", "Eliminar Maestro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                  if(respuesta == JOptionPane.YES_OPTION){
                      try{
                          PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarMaestro(?)}");
                          procedimiento.setInt(1, ((Maestro)tblMaestros.getSelectionModel().getSelectedItem()).getCodigoMaestro());
                        procedimiento.execute();
                        listaMaestro.remove(tblMaestros.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                      }catch(Exception e){
                          e.printStackTrace();
                      }
                  }
              }else{
                  JOptionPane.showMessageDialog(null, "Debe seleccionar un registro");
              }
      }
  }
    
    
    
    public void editar(){
      switch(tipoDeOperacion){
          case NINGUNO:
              if (tblMaestros.getSelectionModel().getSelectedItem() != null){
                  btnEditar.setText("Actualizar");
                  btnReporte.setText("Cancelar");
                  btnNuevo.setDisable(true);
                  btnEliminar.setDisable(true);
                  activarControles();
                  tipoDeOperacion = operaciones.ACTUALIZAR;
              }else{
                  JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento");
              }
              break;
          case ACTUALIZAR:
              actualizar();
              desactivarControles();
              limpiarControles();
              btnEditar.setText("Editar");
              btnReporte.setText("Reporte");
              btnNuevo.setDisable(false);
              btnEliminar.setDisable(false);
              tipoDeOperacion = operaciones.NINGUNO;
              cargarDatos();
              break;
      }
  }
    
    public void actualizar(){
      try{
          PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ActualizarMaestro(?,?,?,?,?,?,?,?)}");
          Maestro registro = (Maestro) tblMaestros.getSelectionModel().getSelectedItem();
          registro.setNombreMaestro(txtNombre.getText());
          registro.setApellidosMaestro(txtApellidos.getText());
          registro.setFechaNacimiento(fecha.getSelectedDate());
          registro.setSexo(txtSexo.getText());
          registro.setTitulo(txtTitulo.getText());
          registro.setDireccion(txtDireccion.getText());
          registro.setTelefono(txtTelefono.getText());
          procedimiento.setInt(1, registro.getCodigoMaestro());
          procedimiento.setString(2, registro.getNombreMaestro());
          procedimiento.setString(3, registro.getApellidosMaestro());
          procedimiento.setDate(4, new java.sql.Date(registro.getFechaNacimiento().getTime()));
          procedimiento.setString(5, registro.getSexo());
          procedimiento.setString(6, registro.getTitulo());
          procedimiento.setString(7, registro.getDireccion());
          procedimiento.setString(8, registro.getTelefono());
          procedimiento.execute();
          
          
      }catch(Exception e){
          e.printStackTrace();
      }
  }
    
    
    public void generarReporte(){
        switch(tipoDeOperacion){
            case NINGUNO:
                imprimirReporte();
                limpiarControles();
                break;
                
            case ACTUALIZAR:
                actualizar();
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("codigoMaestro", null);
        GenerarReporte.mostrarReporte("ReporteMaestros.jasper", "Reporte de Maestros", parametros);
        
    }
    
    
    public void desactivarControles(){
        txtNombre.setEditable(false);
        txtApellidos.setEditable(false);
        grpFecha.setDisable(true);
        txtSexo.setEditable(false);
        txtTitulo.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
}
    
    public void activarControles(){
        txtNombre.setEditable(true);
        txtApellidos.setEditable(true);
        grpFecha.setDisable(false);
        txtSexo.setEditable(true);
        txtTitulo.setEditable(true);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
    }
    
    public void limpiarControles(){
        txtNombre.setText("");
        txtApellidos.setText("");
        txtSexo.setText("");
        txtTitulo.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
    }
    
    public ObservableList<Maestro> getMaestros(){
        ArrayList<Maestro> lista = new ArrayList<Maestro>();
       try{
           PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarMaestros()}");
           ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Maestro(resultado.getInt("codigoMaestro"),
                        resultado.getString("nombreMaestro"),
                        resultado.getString("apellidosMaestro"), 
                        resultado.getDate("fechaNacimiento"),
                        resultado.getString("sexo"), 
                        resultado.getString("titulo"), 
                        resultado.getString("Direccion"),
                        resultado.getString("telefono")));
            }
       }catch(Exception e){
           e.printStackTrace();
       }
        return listaMaestro = FXCollections.observableList(lista); 
                }
    
    public void cargarDatos(){
        tblMaestros.setItems(getMaestros());
        colCodigoMaestro.setCellValueFactory(new PropertyValueFactory<Maestro, Integer>("codigoMaestro"));
        colNombreMaestro.setCellValueFactory(new PropertyValueFactory<Maestro, String>("nombreMaestro"));
        colApellidosMaestro.setCellValueFactory(new PropertyValueFactory<Maestro, String>("apellidosMaestro"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<Maestro, Date>("fechaNacimiento"));
        colSexo.setCellValueFactory(new PropertyValueFactory<Maestro, String>("sexo"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Maestro, String>("titulo"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Maestro, String>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Maestro, Integer>("telefono"));

    }
    
    
}
