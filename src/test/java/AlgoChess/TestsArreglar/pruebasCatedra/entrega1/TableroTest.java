package AlgoChess.pruebasCatedra.entrega1;


import AlgoChess.engine.entidades.Jinete;
import AlgoChess.engine.entidades.Soldado;
import AlgoChess.engine.facciones.Faccion;
import AlgoChess.engine.interfaces.casillero.InterfazCasilla;
import AlgoChess.engine.posicion.Posicion;
import AlgoChess.engine.tablero.Casillero;
import AlgoChess.engine.tablero.Ocupado;
import AlgoChess.engine.tablero.Tablero;
import AlgoChess.engine.tablero.Vacio;
import AlgoChess.excepciones.CasilleroOcupadoException;
import AlgoChess.excepciones.NoMePuedesColocarUnaEntidadEnemigaException;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TableroTest {

    /*
     * SE PUEDE COLOCAR PIEZA EN CASILLERO
    */
    @Test
    public void Test00SeColocaPiezaEnUnCasileroConMismaFaccionConExito() throws NoMePuedesColocarUnaEntidadEnemigaException {

        Faccion faccion1 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion1);

        Jinete jinete = new Jinete();
        jinete.setFaccion(faccion1);
        Posicion posicion = new Posicion(0,0);

        tablero.colocarEntidad(jinete, posicion);
        InterfazCasilla casillero = tablero.obtenerCasillero(posicion);

        assertTrue(casillero instanceof Ocupado);

    }

    /*
     * NO SE PUEDE COLOCAR PIEZA EN CASILLERO
    */
    @Test
    public void Test01NoSePuedeColocarPiezaAliadaEnCasilleroAliadoOcupado() {
        Faccion faccion1 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion1);

        Jinete jinete = new Jinete();
        Soldado soldado = new Soldado();
        jinete.setFaccion(faccion1);

        Posicion posicionDestino = new Posicion(1,0);

        tablero.cambiarCasillero(new Ocupado(soldado, posicionDestino, faccion1));

        assertThrows(CasilleroOcupadoException.class, () -> tablero.colocarEntidad(jinete,posicionDestino));

    }

    @Test
    public void Test02NoSePuedeColocarPiezaAliadaEnCasilleroEnemigo(){
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Jinete jinete = new Jinete();
        jinete.setFaccion(faccion2);
        Posicion posicion = new Posicion(0,0);

        assertThrows(NoMePuedesColocarUnaEntidadEnemigaException.class, () -> tablero.colocarEntidad(jinete,posicion));

    }

    /*
     * SE CREA EL TABLERO CORRECTAMENTE
    */

    @Test
    public void Test00ConstructorDeTableroNoDevuelveNull(){
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();

        Tablero tablero = new Tablero(faccion1, faccion2);
        assertNotNull(tablero);
    }

    @Test
    public void Test01TableroSeConstruyeVacio(){
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();

        Tablero tablero = new Tablero(faccion1, faccion2);


        for(int fila = 0; fila<20;fila++){
            for (int columna=0; columna<20; columna++ ){
                Posicion posicion = new Posicion(fila,columna);
                assertSame(tablero.obtenerCasillero(posicion).getClass(), Vacio.class);
            }
        }
    }

    @Test
    public void Test02MitadSuperiorDeTableroPerteneceAFaccion1(){
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        for(int fila = 0; fila<10;fila++){
            for (int columna =0; columna<20; columna++ ){
                Vacio vacio = (Vacio) tablero.obtenerCasillero( new Posicion(fila,columna));
                assertSame(vacio.getFaccion(), faccion1);
            }
        }
    }

    @Test
    public void Test03MitadInferiorDeTableroPerteneceAFaccion2(){
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        for(int fila = 10; fila<20;fila++){
            for (int columna =0; columna<20; columna++ ){
                Vacio vacio = (Vacio) tablero.obtenerCasillero( new Posicion(fila,columna));
                assertSame(vacio.getFaccion(), faccion2);
            }
        }
    }
}
