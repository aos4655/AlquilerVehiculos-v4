package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VentanaPrincipal extends Controlador {
	@FXML
    private ImageView ivAlquiler;

    @FXML
    private ImageView ivClientes;

    @FXML
    private ImageView ivVehiculos;
	@FXML
	void IrAlquileres() {
		VentanaAlquiler ventanaAlquiler = (VentanaAlquiler) Controladores.get("vistas/VentanaAlquileres.fxml",
				"Alquileres", getEscenario());
		ventanaAlquiler.getEscenario().setResizable(false);
		ventanaAlquiler.getEscenario().showAndWait();
	}

	@FXML
	void IrClientes() {
		VentanaCliente ventanaCliente = (VentanaCliente) Controladores.get("vistas/VentanaClientes.fxml",
				"Clientes", getEscenario());
		ventanaCliente.getEscenario().setResizable(false);
		ventanaCliente.getEscenario().showAndWait();
	}

	@FXML
	void IrVehiculos() {
		VentanaVehiculo ventanaVehiculo = (VentanaVehiculo) Controladores.get("vistas/VentanaVehiculos.fxml",
				"Vehiculos", getEscenario());
		ventanaVehiculo.getEscenario().setResizable(false);
		ventanaVehiculo.getEscenario().showAndWait();
	}

	@FXML
	void acercaDe() {
		AcercaDe ayuda = (AcercaDe) Controladores.get("vistas/AcercaDe.fxml", "Acerca De", getEscenario());
		ayuda.getEscenario().setResizable(false);
		ayuda.getEscenario().showAndWait();
	}

	@FXML
	void confirmarSalida() {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "Estas seguro de que quieres salir de la aplicacion?",
				getEscenario())) {
			getEscenario().close();
		}
	}

	@FXML
	void mostrarEstadisticas() {
		Estadisticas estadisticas = (Estadisticas) Controladores.get("vistas/Estadisticas.fxml", "Estadisticas Mensuales", getEscenario());
		estadisticas.getEscenario().setResizable(false);
		estadisticas.getEscenario().showAndWait();
	}
	@FXML
    void initialize() {
		String rutaImagenCliente = "/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/recursos/imagenes/cliente.png";
		String rutaImagenVehiculo = "/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/recursos/imagenes/coche.png";
		String rutaImagenAlquilerVehiculo = "/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/recursos/imagenes/alquilerVehiculo.png";
		Image imagenVehiculo = new Image(getClass().getResource(rutaImagenVehiculo).toExternalForm());
		Image imagenCliente = new Image(getClass().getResource(rutaImagenCliente).toExternalForm());
		Image imagenAlquiler = new Image(getClass().getResource(rutaImagenAlquilerVehiculo).toExternalForm());
		ivAlquiler.setImage(imagenAlquiler);
		ivClientes.setImage(imagenCliente);
		ivVehiculos.setImage(imagenVehiculo);
    }
}
