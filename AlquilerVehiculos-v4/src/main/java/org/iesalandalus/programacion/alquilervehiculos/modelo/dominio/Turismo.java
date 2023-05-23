package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Turismo extends Vehiculo {
	private int cilindrada;
	private static final int FACTOR_CILINDRADA = 10;
	
	public Turismo(String marca, String modelo, int cilindrada, String matricula) {
		super(marca,modelo,matricula);
		setCilindrada(cilindrada);
	}

	public Turismo(Turismo turismo) {
		super(turismo);
		this.cilindrada = turismo.getCilindrada();
	}

	public int getCilindrada() {
		return cilindrada;
	}

	private void setCilindrada(int cilindrada) {
		if (cilindrada >= 5000 || cilindrada <= 0) {
			throw new IllegalArgumentException("ERROR: La cilindrada no es correcta.");
		}
		this.cilindrada = cilindrada;
	}
	@Override
	public int getFactorPrecio() {
		return cilindrada/FACTOR_CILINDRADA;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s (%d cc) - %s", getMarca(), getModelo(), getCilindrada(), getMatricula());
	}
	
	
}
