package algochess.engine.juego;

import algochess.engine.jugador.Jugador;
import algochess.engine.tablero.Casillero;
import algochess.engine.tablero.Tablero;
import algochess.engine.vendedordeentidades.VendedorDeEntidades;
import algochess.engine.entidades.Catapulta;
import algochess.engine.entidades.Curandero;
import algochess.engine.entidades.Entidad;
import algochess.engine.entidades.Jinete;
import algochess.engine.entidades.Soldado;
import algochess.engine.facciones.Faccion;
import algochess.engine.posicion.Posicion;
import algochess.engine.juego.Fase;

public class Inicial implements Fase {

	private Entidad entidadSeleccionada;

	public void seleccionarEntidad(Jugador jugador, Faccion faccion, Entidad entidad) {
		entidad.setPropietario(jugador);
		entidad.setFaccion(faccion);
		entidadSeleccionada = entidad;
	}

	public void comprarEntidad(Jugador jugador, VendedorDeEntidades vendedor) {
		try {
			jugador.comprarEntidad(vendedor, entidadSeleccionada);
		} catch (Exception ex) {
			throw ex;
		}
	}

	public void colocarEntidad(Tablero tablero, Jugador jugador, Posicion posicion) {
		try {
			tablero.colocarEntidad(entidadSeleccionada, posicion, jugador);
		} catch (Exception ex) {
			throw ex;
		}
	}

	public void devolverDinero(Jugador jugador) {
		jugador.setDinero(entidadSeleccionada);
	}

	public boolean verificarFinDeFase(Jugador jugadorAliado, Jugador jugadorEnemigo) {
		return (jugadorAliado.noPuedeComprar() && jugadorEnemigo.noPuedeComprar());
	}

	public Entidad obtenerEntidadSeleccionada() {
		return entidadSeleccionada;
	}

	public void liberarEntidadSeleccionada() {
		entidadSeleccionada = null;
	}
	// public boolean cambiarTurno() {
	// 	System.out.println("Cambiando turno...");
	// 	System.out.println(jugadorAliado);
	// 	System.out.println(jugadorEnemigo);
	// 	if(jugadorAliado.noPuedeComprar() && jugadorEnemigo.noPuedeComprar())
	// 		return true;

	// 	jugadorActual = turno.cambiarTurno(jugadorActual);
	// 	faccionActual = turno.popFaccion();
	// 	return false;
	// }
}