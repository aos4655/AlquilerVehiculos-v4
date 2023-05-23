package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public enum Accion {

	
	SALIR("Salir") {
		@Override
		public void ejecutar() {
			vista.terminar();
		}
	},
	INSERTAR_CLIENTE("Insertar Cliente") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			// TODO Auto-generated method stub
			vista.insertarCliente();
		}
	},
	INSERTAR_VEHICULO("Insertar Vehiculo") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			// TODO Auto-generated method stub
			vista.insertarVehiculo();
		}
	},
	INSERTAR_ALQUILER("Insertar Alquiler") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			// TODO Auto-generated method stub
			vista.insertarAlquiler();
		}
	},
	BUSCAR_CLIENTE("Buscar Cliente") {
		@Override
		public void ejecutar() {
			// TODO Auto-generated method stub
			vista.buscarCliente();
		}
	},
	BUSCAR_VEHICULO("Buscar Vehiculo") {
		@Override
		public void ejecutar() {
			// TODO Auto-generated method stub
			vista.buscarVehiculo();
		}
	},
	BUSCAR_ALQUILER("Buscar Alquiler") {
		@Override
		public void ejecutar() {
			// TODO Auto-generated method stub
			vista.buscarAlquiler();
		}
	},
	MODIFICAR_CLIENTE("Modificar Cliente") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			// TODO Auto-generated method stub
			vista.modificarCliente();
		}
	},
	DEVOLVER_ALQUILER_VEHICULO("Devolver Alquiler Vehiculo") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			// TODO Auto-generated method stub
			vista.devolverAlquilerVehiculo();
		}
	},
	DEVOLVER_ALQUILER_CLIENTE("Devolver Alquiler Cliente") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			// TODO Auto-generated method stub
			vista.devolverAlquilerCliente();
		}
	},
	BORRAR_CLIENTE("Borrar Cliente") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			// TODO Auto-generated method stub
			vista.borrarCliente();
		}
	},
	BORRAR_VEHICULO("Borrar Vehiculo") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			// TODO Auto-generated method stub
			vista.borrarVehiculo();
		}
	},
	BORRAR_ALQUILER("Borrar Alquiler") {
		@Override
		public void ejecutar() throws OperationNotSupportedException {
			// TODO Auto-generated method stub
			vista.borrarAlquiler();
		}
	},
	LISTAR_CLIENTES("Listar Clientes") {
		@Override
		public void ejecutar() {
			// TODO Auto-generated method stub
			vista.listarClientes();
		}
	},
	LISTAR_VEHICULO("Listar Vehiculo") {
		@Override
		public void ejecutar() {
			// TODO Auto-generated method stub
			vista.listarVehiculo();
		}
	},
	LISTAR_ALQUILERES("Listar Alquileres") {
		@Override
		public void ejecutar() {
			// TODO Auto-generated method stub
			vista.listarAlquileres();
		}
	},
	LISTAR_ALQUILERES_CLIENTE("Listar Alquileres Cliente") {
		@Override
		public void ejecutar() {
			// TODO Auto-generated method stub
			vista.listarAlquileresCliente();
		}
	},
	LISTAR_ALQUILERES_VEHICULO("Listar Alquileres Vehiculo") {
		@Override
		public void ejecutar() {
			// TODO Auto-generated method stub
			vista.listarAlquileresVehiculo();
		}
	},
	MOSTRAR_ESTADISTICAS_MENSUALES("Mostrar Estadisticas Mensuales") {
		@Override
		public void ejecutar() {
			// TODO Auto-generated method stub
			vista.mostrarEstadisticasMensualesTipoVehiculo();
		}
	};
	private static VistaTexto vista;

	private String cadenaAMostrar;

	private static boolean esOrdinalValido(int ordinal) {
		return ordinal < Accion.values().length && ordinal >= 0;
	}
	public static Accion get(int ordinal) {
		Accion op = null;
		if(esOrdinalValido(ordinal)) {
			op = Accion.values()[ordinal];
		}
		else {
			throw new NullPointerException("Error: El ordinal pasado no es valido");
		}
		return op;
	}
	private Accion(String cadenaAMostrar) {
		this.cadenaAMostrar = cadenaAMostrar;
	}
	static void setVista(VistaTexto vista) {
		Accion.vista = vista;
	}
	public abstract void ejecutar() throws OperationNotSupportedException;
	
	@Override
	public String toString() {
		return String.format("%d - %s", ordinal(), cadenaAMostrar);
	}
}
