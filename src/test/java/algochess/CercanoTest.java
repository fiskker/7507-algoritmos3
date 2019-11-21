package algochess;

import algochess.engine.entidades.Jinete;
import algochess.engine.entidades.armas.rangos.Cercano;
import algochess.engine.entidades.armas.rangos.Rango;
import algochess.engine.facciones.Faccion;
import algochess.engine.interfaces.casillero.Recuadro;
import algochess.engine.jugador.Jugador;
import algochess.engine.posicion.Posicion;
import algochess.engine.tablero.Casillero;
import algochess.engine.tablero.Ocupado;
import org.junit.Test;
import static org.junit.Assert.*;

public class CercanoTest {

    @Test
    public void test00CreoUnRangoCercanoYNoEsNull(){

        Rango rangoCercano = new Cercano();

        assertNotNull(rangoCercano);

    }

    @Test
    public void test01CasilleroQueNoEstaEnRangoTiraError(){

        Jinete jinete = new Jinete(new Jugador(Faccion.ALIADOS, "Lucas"), Faccion.ALIADOS);
        Posicion posicion =  new Posicion(20,20);
        Posicion origen = new Posicion(2,2);

        Rango rangoCercano = new Cercano();
        Casillero casillero = new Ocupado(jinete,posicion,Faccion.ALIADOS);
        assertFalse(rangoCercano.casilleroEstaEnRango(casillero, origen));

    }

    @Test
    public void test02CasilleroQueEstaEnRangoNoTiraError(){

        Jinete jinete = new Jinete(new Jugador(Faccion.ALIADOS, "Lucas"), Faccion.ALIADOS);
        Posicion posicion =  new Posicion(3,3);
        Posicion origen = new Posicion(2,2);

        Rango rangoCercano = new Cercano();
        Recuadro casillero = new Ocupado(jinete,posicion,Faccion.ALIADOS);
        assertTrue(rangoCercano.casilleroEstaEnRango(casillero, origen));

    }





}