package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores.VentanaPrincipal;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LanzadorVentanaPrincipal extends Application {
	
	private static final String TITULO = "Vista Ventanas Alquiler de Vehiculos";
    @Override
    public void start(Stage escenarioPrincipal) {
        try {
        	VentanaPrincipal ventanaPrincipal =(VentanaPrincipal) Controladores.get("vistas/VentanaPrincipal.fxml", TITULO, null);
//        	Dialogos.setHojaEstilos(ventanaPrincipal.getEscenario().getRoot().getstylesheets().get(0));
        	ventanaPrincipal.getEscenario().setOnCloseRequest(e -> confirmarSalida(ventanaPrincipal.getEscenario(), e));
            Image icono = new Image(LocalizadorRecursos.class.getResourceAsStream("iconos/logotipo.png"));
            ventanaPrincipal.getEscenario().getIcons().add(icono);
        	ventanaPrincipal.getEscenario().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void confirmarSalida(Stage escenario, WindowEvent e) {
    	if (Dialogos.mostrarDialogoConfirmacion("Salir", "Estas seguro de que quieres salir de la aplicacion?", escenario)) {
			escenario.close();
		}
    	else {
			e.consume();
		}
	}

	public static void comenzar() {
        launch(LanzadorVentanaPrincipal.class);
    }
}
