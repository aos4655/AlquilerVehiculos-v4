package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.Alquileres;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaGrafica extends Vista {

	private VistaGrafica() {}
	private static VistaGrafica instancia;

	public static VistaGrafica getInstancia() {
		if (instancia == null) {
			instancia = new VistaGrafica();
		}
		return instancia;
	}

	@Override
	public void comenzar() throws OperationNotSupportedException {
		// TODO Auto-generated method stub
		LanzadorVentanaPrincipal.comenzar();
		getControlador().terminar();
	}

	@Override
	public void terminar() {
		// TODO Auto-generated method stub
		System.out.println("Adios!");
	}

}
