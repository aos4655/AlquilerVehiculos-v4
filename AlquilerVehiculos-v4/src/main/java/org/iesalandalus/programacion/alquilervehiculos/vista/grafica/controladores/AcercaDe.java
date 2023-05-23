package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AcercaDe extends Controlador {

	@FXML
	private ImageView ivImagen;

	@FXML
	void initialize() {
		String rutaImagen = "/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/recursos/imagenes/imgInfo.png";
		Image imagen = new Image(getClass().getResource(rutaImagen).toExternalForm());
		ivImagen.setImage(imagen);
	}
}
