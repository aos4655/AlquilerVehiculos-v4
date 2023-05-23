package org.iesalandalus.programacion.alquilervehiculos.vista;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;

public abstract class Vista {

	private Controlador controlador;

	public abstract void terminar();

	public abstract void comenzar() throws OperationNotSupportedException ;

	protected Vista() {
		super();
	}

	
	public void setControlador(Controlador controlador) {
		if (controlador == null) {
			throw new NullPointerException("Error: El controlador no puede ser nulo");
		}
		this.controlador = controlador;
	}
	
	public Controlador getControlador() {
		return controlador;
	}

}