package algochess;

import algochess.engine.entidades.Jinete;
import algochess.engine.entidades.armas.rangos.Cercano;
import algochess.engine.entidades.armas.rangos.Rango;
import algochess.engine.facciones.Faccion;
import algochess.engine.tablero.Casillero;
import algochess.engine.jugador.Jugador;
import algochess.engine.posicion.Posicion;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CercanoTest {

	@Test
	public void test00CreoUnRangoCercanoYNoEsNull() {

		Rango rangoCercano = new Cercano();

		assertNotNull(rangoCercano);

	}

	@Test
	public void test01CasilleroQueNoEstaEnRangoTiraError() {

		Jinete jinete = new Jinete(new Jugador(Faccion.ALIADOS, "Lucas"), Faccion.ALIADOS);
		Posicion posicion = new Posicion(20, 20);
		Posicion origen = new Posicion(2, 2);

		Rango rangoCercano = new Cercano();
		Casillero casillero = new Casillero(posicion, Faccion.ALIADOS, jinete);
		assertFalse(rangoCercano.casilleroEstaEnRango(casillero, origen));

	}

	@Test
	public void test02CasilleroQueEstaEnRangoNoTiraError() {

		Jinete jinete = new Jinete(new Jugador(Faccion.ALIADOS, "Lucas"), Faccion.ALIADOS);
		Posicion posicion = new Posicion(3, 3);
		Posicion origen = new Posicion(2, 2);

		Rango rangoCercano = new Cercano();
		Casillero casillero = new Casillero(posicion, Faccion.ALIADOS, jinete);
		assertTrue(rangoCercano.casilleroEstaEnRango(casillero, origen));

	}

}
