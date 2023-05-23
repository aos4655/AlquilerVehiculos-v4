package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Modificar extends Controlador {

	private boolean cancelado;

	@FXML
    private ComboBox<Cliente> cbClientes;

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

    private Cliente cliente;
    //private boolean puedoModificar = false;
    @FXML
    void modificar() {
    	cancelado = false;
    	getEscenario().close();
		
    }
	public void limpiar() {
		tfDni.setText("");
		tfNombre.setText("");
		tfTelefono.setText("");
		cbClientes.getSelectionModel().clearSelection();	
	}
	@FXML
	void cancelar() {
		cancelado = true;
		getEscenario().close();
	}
	public void actualizar(List<Cliente> clientes) {
		tfDni.setDisable(true);
		cbClientes.setItems(FXCollections.observableArrayList(clientes));
	}

    @FXML
    void clienteElejido() {
    	cliente = cbClientes.getValue();
    	if (cliente != null) {
    		tfDni.setText(cliente.getDni());
        	tfNombre.setText(cliente.getNombre());
        	tfTelefono.setText(cliente.getTelefono());
        	//puedoModificar = true;
		}
    	
    }
    public Cliente getCliente() {
		return cliente;
	}
    public String getTelefono() {
		return tfTelefono.getText();
	}
    public String getNombre() {
		return tfNombre.getText();
	}

}
