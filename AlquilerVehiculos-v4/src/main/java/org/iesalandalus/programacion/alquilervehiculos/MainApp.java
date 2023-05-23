package org.iesalandalus.programacion.alquilervehiculos;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.ModeloCascada;
import org.iesalandalus.programacion.alquilervehiculos.vista.FactoriaVista;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class MainApp {

	public static void main(String[] args) throws OperationNotSupportedException {
		Modelo modelo = new ModeloCascada(procesarFuenteDatos(args));
		Vista vista = procesarArgumentosVista(args);

		// Vista vista = FactoriaVista.GRAFICA.crear();
		Controlador controlador = new Controlador(modelo, vista);
		controlador.comenzar();
	}

	private static Vista procesarArgumentosVista(String[] args) {
		// TODO Auto-generated method stub
		Vista vista = FactoriaVista.GRAFICA.crear();
		for (String argumento : args) {
			if (argumento.equalsIgnoreCase("-vtexto")) {
				vista = FactoriaVista.TEXTO.crear();
			} else if (argumento.equalsIgnoreCase("-vgrafica")) {
				vista = FactoriaVista.GRAFICA.crear();
			}
		}
		return vista;
	}

	private static FactoriaFuenteDatos procesarFuenteDatos(String[] args) {
		// TODO Auto-generated method stub
		FactoriaFuenteDatos factoria = FactoriaFuenteDatos.FICHEROS;
		for (String argumento : args) {
			if (argumento.equalsIgnoreCase("-fdficheros")) {
				factoria = FactoriaFuenteDatos.FICHEROS;
			} else if (argumento.equalsIgnoreCase("-fdmariadb")) {
				factoria = FactoriaFuenteDatos.MARIADB;
			} else if (argumento.equalsIgnoreCase("-fdmongodb")) {
				factoria = FactoriaFuenteDatos.MONGODB;
			}
		}
		return factoria;
	}

}
