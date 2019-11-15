package AlgoChess.tablero;


import AlgoChess.engine.entidades.Jinete;
import AlgoChess.engine.facciones.Faccion;
import AlgoChess.engine.interfaces.casillero.InterfazCasilla;
import AlgoChess.engine.posicion.Posicion;
import AlgoChess.engine.tablero.Casillero;
import AlgoChess.engine.tablero.Ocupado;
import AlgoChess.engine.tablero.Tablero;
import AlgoChess.engine.tablero.Vacio;
import AlgoChess.excepciones.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TableroTest {
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

        Vacio vacio = new Vacio(new Posicion(0,0),new Faccion());

        for(int fila = 0; fila<20;fila++){
            for (int columna=0; columna<20; columna++ ){
                Posicion posicion = new Posicion(fila,columna);
                assertSame(tablero.obtenerCasillero(posicion).getClass(), vacio.getClass());
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

    @Test
    public void Test04SeCambiaCasilleroVacioPorOcupado(){
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Jinete jinete = new Jinete();
        Posicion posicion = new Posicion(0,0);
        Ocupado ocupado = new Ocupado(jinete,posicion,faccion1);

        tablero.cambiarCasillero(ocupado);

        InterfazCasilla casillero =tablero.obtenerCasillero(posicion);

        assertSame(casillero.getClass(), ocupado.getClass());
    }

    @Test
    public void Test05ColocarEntidadEnCasilleroVacioLoActualizaConUnoOcupado() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Jinete jinete = new Jinete();
        Posicion posicion = new Posicion(0,0);
        jinete.setFaccion(faccion1);

        tablero.colocarEntidad(jinete, posicion);
        InterfazCasilla casillero = tablero.obtenerCasillero(posicion);

        assertTrue(casillero instanceof Ocupado);

    }

    @Test
    public void Test06ColocarEntidadEnCasilleroOcupadoLanzaExcepcion() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Jinete jinete = new Jinete();
        Posicion posicion = new Posicion(0,0);
        jinete.setFaccion(faccion1);

        tablero.colocarEntidad(jinete, posicion);

        assertThrows(CasilleroOcupadoException.class, () -> tablero.colocarEntidad(jinete,posicion));
    }

    @Test
    public void Test07MoverDesdeCasilleroVacio(){
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Posicion origen = new Posicion(1,1);
        Posicion destino = new Posicion(1,2);

        assertThrows(CasilleroVacioException.class,()-> tablero.moverEntidad(origen,destino,faccion1));
    }

    @Test
    public void Test08MoverHaciaCasilleroOcupado(){
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Jinete jinete = new Jinete();
        jinete.setFaccion(faccion1);
        Posicion origen = new Posicion(1,1);
        Posicion destino = new Posicion(1,2);

        tablero.cambiarCasillero(new Ocupado(jinete, origen,faccion1));
        tablero.cambiarCasillero(new Ocupado(jinete,destino,faccion1));

        assertThrows(CasilleroOcupadoException.class,()-> tablero.moverEntidad(origen,destino,faccion1));
    }

    @Test
    public void Test09MoverseFueraDelTablero(){
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);


        Jinete jinete = new Jinete();
        jinete.setFaccion(faccion1);
        Posicion origen = new Posicion(1,1);
        Posicion destino = new Posicion(-1,3);

        tablero.cambiarCasillero(new Ocupado(jinete, origen,faccion1));

        assertThrows(MoverseMasDeDosCasillerosException.class,()-> tablero.moverEntidad(origen,destino,faccion1));
    }

    @Test
    public void Test10MoverEntidadConDistintaFaccionQueJugador() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);


        Jinete jinete = new Jinete();
        jinete.setFaccion(faccion1);
        Posicion origen = new Posicion(1,1);
        Posicion destino = new Posicion(1,2);

        tablero.colocarEntidad(jinete,origen);

        assertThrows(JugadorNoMePuedeMover.class,()-> tablero.moverEntidad(origen,destino,faccion2));
    }

    @Test
    public void Test11MoverseMasDeDosCasilleros() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);


        Jinete jinete = new Jinete();
        jinete.setFaccion(faccion1);
        Posicion origen = new Posicion(1,1);
        Posicion destino = new Posicion(1,3);

        tablero.colocarEntidad(jinete,origen);

        assertThrows(MoverseMasDeDosCasillerosException.class,()-> tablero.moverEntidad(origen,destino,faccion1));
    }

    @Test
    public void Test12MoverEntidadDejaCasilleroVacio() throws NoMePuedesColocarUnaEntidadEnemigaException, YoNoPuedoMovermeException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);


        Jinete jinete = new Jinete();
        jinete.setFaccion(faccion1);

        Posicion origen = new Posicion(1,1);
        Posicion destino = new Posicion(1,2);

        tablero.colocarEntidad(jinete,origen);
        tablero.moverEntidad(origen,destino,faccion1);

        InterfazCasilla casillero = tablero.obtenerCasillero(origen);

        assertSame(casillero.getClass(), Vacio.class);

    }

    @Test
    public void Test13MoverEntidadDejaCasilleroOcupado() throws NoMePuedesColocarUnaEntidadEnemigaException, YoNoPuedoMovermeException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Jinete jinete = new Jinete();
        jinete.setFaccion(faccion1);

        Posicion origen = new Posicion(1,1);
        Posicion destino = new Posicion(1,2);

        tablero.colocarEntidad(jinete,origen);
        tablero.moverEntidad(origen,destino,faccion1);

        InterfazCasilla casillero = tablero.obtenerCasillero(destino);

        assertSame(casillero.getClass(), Ocupado.class);

    }


}

