package AlgoChess.entidades;

import AlgoChess.engine.entidades.Catapulta;
import AlgoChess.engine.entidades.Curandero;
import AlgoChess.engine.entidades.Jinete;
import AlgoChess.engine.entidades.Soldado;
import AlgoChess.engine.facciones.Faccion;
import AlgoChess.engine.posicion.Posicion;
import AlgoChess.engine.tablero.Ocupado;
import AlgoChess.engine.tablero.Tablero;
import AlgoChess.engine.tablero.Vacio;
import AlgoChess.excepciones.*;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SoldadoTest {
    @Test
    public void Test00ConstructorDevuelveSoldado(){
        Soldado soldado = new Soldado();
        assertNotNull(soldado);
    }

    @Test
    public void Test01SoldadoSeClona(){
        Soldado soldado = new Soldado();
        Soldado soldado1 = soldado.clonar();

        assertNotSame(soldado1, soldado);
    }

    @Test
    public void Test02SoldadoEstaVivo(){
        Soldado soldado = new Soldado();
        assertTrue(soldado.tenesEstaVida(100));
    }


    @Test
    public void Test03SoldadoMataSoldado() throws UnAmigoNoMePuedeAtacarException, EntidadSeMurioException, NoMePuedesColocarUnaEntidadEnemigaException, YoNoPuedoAtacarException, YoNoPuedoSerDaniadaException {
        Faccion faccion = new Faccion();
        Faccion faccion1 = new Faccion();
        Tablero tablero = new Tablero(faccion,faccion1);

        Soldado soldadoAtacante = new Soldado();
        Soldado soldadoAtacado = new Soldado();
        soldadoAtacante.setFaccion(faccion);
        soldadoAtacado.setFaccion(faccion1);

        tablero.colocarEntidad(soldadoAtacante, new Posicion(9,8));
        tablero.colocarEntidad(soldadoAtacado,new Posicion(10,8));

        Ocupado ocupadoAtacado = (Ocupado) tablero.obtenerCasillero(new Posicion(10,8));

        for (int i = 0; i<9;  i++){
            soldadoAtacante.atacar(ocupadoAtacado,tablero,faccion);
        }

        assertThrows(EntidadSeMurioException.class, () -> soldadoAtacante.atacar(ocupadoAtacado,tablero,faccion));
    }

    @Test
    public void Test04SoldadoMataJinete() throws UnAmigoNoMePuedeAtacarException, EntidadSeMurioException, NoMePuedesColocarUnaEntidadEnemigaException, YoNoPuedoAtacarException, YoNoPuedoSerDaniadaException {
        Faccion faccion = new Faccion();
        Faccion faccion1 = new Faccion();
        Tablero tablero = new Tablero(faccion,faccion1);

        Soldado soldadoAtacante = new Soldado();
        Jinete jineteAtacado = new Jinete();
        soldadoAtacante.setFaccion(faccion);
        jineteAtacado.setFaccion(faccion1);

        tablero.colocarEntidad(soldadoAtacante, new Posicion(9,8));
        tablero.colocarEntidad(jineteAtacado,new Posicion(10,8));

        Ocupado ocupadoAtacado = (Ocupado) tablero.obtenerCasillero(new Posicion(10,8));

        for (int i = 0; i<9;  i++){
            soldadoAtacante.atacar(ocupadoAtacado,tablero,faccion);
        }

        assertThrows(EntidadSeMurioException.class, () -> soldadoAtacante.atacar(ocupadoAtacado,tablero,faccion));

    }

    @Test
    public void Test05SoldadoMataCurandero() throws UnAmigoNoMePuedeAtacarException, EntidadSeMurioException, NoMePuedesColocarUnaEntidadEnemigaException, YoNoPuedoAtacarException, YoNoPuedoSerDaniadaException {
        Faccion faccion = new Faccion();
        Faccion faccion1 = new Faccion();
        Tablero tablero = new Tablero(faccion,faccion1);

        Soldado soldadoAtacante = new Soldado();
        Curandero curanderoAtacado = new Curandero();
        soldadoAtacante.setFaccion(faccion);
        curanderoAtacado.setFaccion(faccion1);

        tablero.colocarEntidad(soldadoAtacante, new Posicion(9,8));
        tablero.colocarEntidad(curanderoAtacado,new Posicion(10,8));

        Ocupado ocupadoAtacado = (Ocupado) tablero.obtenerCasillero(new Posicion(10,8));

        for (int i = 0; i<7;  i++){
            soldadoAtacante.atacar(ocupadoAtacado,tablero,faccion);
        }

        assertThrows(EntidadSeMurioException.class, () -> soldadoAtacante.atacar(ocupadoAtacado,tablero,faccion));


    }

    @Test
    public void Test06SoldadoMataCatapulta() throws UnAmigoNoMePuedeAtacarException, EntidadSeMurioException, NoMePuedesColocarUnaEntidadEnemigaException, YoNoPuedoAtacarException, YoNoPuedoSerDaniadaException {
        Faccion faccion = new Faccion();
        Faccion faccion1 = new Faccion();
        Tablero tablero = new Tablero(faccion,faccion1);

        Soldado soldadoAtacante = new Soldado();
        Catapulta catapultaAtacado = new Catapulta();
        soldadoAtacante.setFaccion(faccion);
        catapultaAtacado.setFaccion(faccion1);

        tablero.colocarEntidad(soldadoAtacante, new Posicion(9,8));
        tablero.colocarEntidad(catapultaAtacado,new Posicion(10,8));

        Ocupado ocupadoAtacado = (Ocupado) tablero.obtenerCasillero(new Posicion(10,8));

        for (int i = 0; i<4;  i++){
            soldadoAtacante.atacar(ocupadoAtacado,tablero,faccion);
        }

        assertThrows(EntidadSeMurioException.class, () -> soldadoAtacante.atacar(ocupadoAtacado,tablero,faccion));

    }

    @Test
    public void Test07SoldadoSeMueve() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion = new Faccion();
        Tablero tablero = new Tablero(faccion,faccion);
        Soldado soldado = new Soldado();
        soldado.setFaccion(faccion);
        Posicion posOrigen = new Posicion(1,1);

        tablero.colocarEntidad(soldado,posOrigen);


        Posicion posDestino = new Posicion(1,2);
        Vacio casilleroDestino = (Vacio) tablero.obtenerCasillero(posDestino);

        soldado.moverA(tablero,casilleroDestino,faccion);

        boolean posDestinoCoincide = posDestino.esIgual(soldado.getPosicion());


        assertTrue(posDestinoCoincide);

    }

    @Test
    public void Test08MoverSoldadoActualizaCasilleroOcupado() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion = new Faccion();
        Tablero tablero = new Tablero(faccion,faccion);
        Soldado soldado = new Soldado();
        Posicion posOrigen = new Posicion(1,1);
        Posicion posDestino = new Posicion(1,2);

        soldado.setFaccion(faccion);
        tablero.colocarEntidad(soldado,posOrigen);

        Vacio casilleroDestino = (Vacio) tablero.obtenerCasillero(posDestino);
        soldado.moverA(tablero,casilleroDestino,faccion);

        assertEquals(Ocupado.class,tablero.obtenerCasillero(posDestino).getClass());
    }

    @Test
    public void Test09MoverSoldadoActualizaCasileroOcupado() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion = new Faccion();
        Tablero tablero = new Tablero(faccion,faccion);
        Soldado soldado = new Soldado();
        Posicion posOrigen = new Posicion(1,1);
        Posicion posDestino = new Posicion(1,2);

        soldado.setFaccion(faccion);
        tablero.colocarEntidad(soldado,posOrigen);

        Vacio casilleroDestino = (Vacio) tablero.obtenerCasillero(posDestino);
        soldado.moverA(tablero,casilleroDestino,faccion);

        assertEquals(Ocupado.class,tablero.obtenerCasillero(posDestino).getClass());

    }

    /*Soldado no puede atacar soldado de su misma faccion*/
    /**/
}
