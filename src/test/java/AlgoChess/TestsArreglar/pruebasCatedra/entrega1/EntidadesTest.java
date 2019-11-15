package AlgoChess.pruebasCatedra.entrega1;

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

import static AlgoChess.engine.Constantes.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EntidadesTest {

    /*SE PUEDE MOVER UNIDAD EN TODAS LAS DIRECCIONES*/
    @Test
    public void Test00UnidadSeMueveArriba() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion = new Faccion();
        Tablero tablero = new Tablero(faccion,faccion);
        Soldado soldado = new Soldado();
        soldado.setFaccion(faccion);

        Posicion origen = new Posicion(5,5);
        Posicion destino = new Posicion(4,5);

        tablero.colocarEntidad(soldado,origen);
        Vacio vacio = (Vacio) tablero.obtenerCasillero(destino);

        soldado.moverA(tablero,vacio,faccion);

        assertTrue(soldado.getPosicion().esIgual(destino));

    }

    @Test
    public void Test01UnidadSeMueveAbajo() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion = new Faccion();
        Tablero tablero = new Tablero(faccion,faccion);
        Soldado soldado = new Soldado();
        soldado.setFaccion(faccion);

        Posicion origen = new Posicion(5,5);
        Posicion destino = new Posicion(6,5);

        tablero.colocarEntidad(soldado,origen);
        Vacio vacio = (Vacio) tablero.obtenerCasillero(destino);

        soldado.moverA(tablero,vacio,faccion);

        assertTrue(soldado.getPosicion().esIgual(destino));
    }

    @Test
    public void Test02UnidadSeMueveDerecha() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion = new Faccion();
        Tablero tablero = new Tablero(faccion,faccion);
        Soldado soldado = new Soldado();
        soldado.setFaccion(faccion);

        Posicion origen = new Posicion(5,5);
        Posicion destino = new Posicion(5,6);

        tablero.colocarEntidad(soldado,origen);
        Vacio vacio = (Vacio) tablero.obtenerCasillero(destino);

        soldado.moverA(tablero,vacio,faccion);

        assertTrue(soldado.getPosicion().esIgual(destino));
    }

    @Test
    public void Test03UnidadSeMueveIzquierda() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion = new Faccion();
        Tablero tablero = new Tablero(faccion,faccion);
        Soldado soldado = new Soldado();
        soldado.setFaccion(faccion);

        Posicion origen = new Posicion(5,5);
        Posicion destino = new Posicion(5,4);

        tablero.colocarEntidad(soldado,origen);
        Vacio vacio = (Vacio) tablero.obtenerCasillero(destino);

        soldado.moverA(tablero,vacio,faccion);

        assertTrue(soldado.getPosicion().esIgual(destino));
    }

    /*NO SE PUEDE MOVER UNIDAD A CASILLERO OCUPADO*/
    @Test
    public void Test04UnidadNoSePuedeMoverACasilleroOcupado(){
        /*Test no necesario ya que una unidad siempre se mueve a un casillero Vacio*/
        /*No acepta casilleros ocupados como parametro*/
    }

    @Test
    public void Test05SoldadoAtacaAPiezaEnemigaEnCasilleroEnemigoYSeLeRestaLaVidaCorrespondiente() throws NoMePuedesColocarUnaEntidadEnemigaException, UnAmigoNoMePuedeAtacarException, EntidadSeMurioException, YoNoPuedoAtacarException, YoNoPuedoSerDaniadaException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Soldado soldadoAtacante = new Soldado();
        Soldado soldadoAtacado = new Soldado();
        soldadoAtacante.setFaccion(faccion1);
        soldadoAtacado.setFaccion(faccion2);

        tablero.colocarEntidad(soldadoAtacante, new Posicion(9,8));
        tablero.colocarEntidad(soldadoAtacado,new Posicion(10,9));

        Ocupado casilleroAtacado = (Ocupado) tablero.obtenerCasillero(new Posicion(10,9));

        soldadoAtacante.atacar(casilleroAtacado,tablero,faccion1);

        assertTrue(soldadoAtacado.tenesEstaVida(90));

    }

    @Test
    public void Test06SoldadoAtacaPiezaEnemigaEnCasilleroAliadoYSeLeRestaLaVidaCorrespondiente() throws NoMePuedesColocarUnaEntidadEnemigaException, UnAmigoNoMePuedeAtacarException, EntidadSeMurioException, YoNoPuedoAtacarException, YoNoPuedoSerDaniadaException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Soldado soldadoAtacante = new Soldado();
        Soldado soldadoAtacado = new Soldado();
        soldadoAtacante.setFaccion(faccion1);
        soldadoAtacado.setFaccion(faccion2);

        tablero.colocarEntidad(soldadoAtacante, new Posicion(9,8));
        tablero.colocarEntidad(soldadoAtacado,new Posicion(10,9));

        Vacio destinoC = (Vacio) tablero.obtenerCasillero(new Posicion(9,9));
        soldadoAtacado.moverA(tablero,destinoC,faccion2);

        Ocupado casilleroAtacado = (Ocupado) tablero.obtenerCasillero(new Posicion(9,9));

        soldadoAtacante.atacar(casilleroAtacado,tablero,faccion1);

        double danioEsperado = 100 - (10+10*0.05);

        assertTrue(soldadoAtacado.tenesEstaVida(danioEsperado));
    }

    /*SOLDADO NO PUEDE ATACAR PIEZA ALIADA*/
    @Test
    public void Test07SoldadoNoPuedeAtacarPiezaAliada() throws NoMePuedesColocarUnaEntidadEnemigaException{
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Soldado soldadoAtacante = new Soldado();
        Soldado soldadoAtacado = new Soldado();
        soldadoAtacante.setFaccion(faccion1);
        soldadoAtacado.setFaccion(faccion1);

        tablero.colocarEntidad(soldadoAtacante, new Posicion(9,8));
        tablero.colocarEntidad(soldadoAtacado,new Posicion(9,9));


        Ocupado casilleroAtacado = (Ocupado) tablero.obtenerCasillero(new Posicion(9,9));

        assertThrows(UnAmigoNoMePuedeAtacarException.class,()-> soldadoAtacante.atacar(casilleroAtacado,tablero,faccion1));


    }

    /*JINETE ATACA PIEZA ENEMIGA Y SE LE RESTA LA VIDA CORRESPONDIENTE*/
    /*USA ARMA POR DEFECTO*/
    @Test
    public void Test08JineteAtacaAPiezaEnemigaEnCasilleroEnemigoYSeLeRestaLaVidaCorrespondiente() throws NoMePuedesColocarUnaEntidadEnemigaException, UnAmigoNoMePuedeAtacarException, EntidadSeMurioException, YoNoPuedoSerDaniadaException, YoNoPuedoAtacarException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Jinete jineteAtacante = new Jinete();
        Soldado soldadoAtacado = new Soldado();
        jineteAtacante.setFaccion(faccion1);
        soldadoAtacado.setFaccion(faccion2);

        tablero.colocarEntidad(jineteAtacante, new Posicion(9,8));
        tablero.colocarEntidad(soldadoAtacado,new Posicion(10,9));

        Ocupado casilleroAtacado = (Ocupado) tablero.obtenerCasillero(new Posicion(10,9));

        jineteAtacante.atacar(casilleroAtacado,tablero,faccion1);

        assertTrue(soldadoAtacado.tenesEstaVida(SOLDADO_VIDA-DAGA_PODER));
    }

    @Test
    public void Test09JineteAtacaAPiezaEnemigaEnCasilleroAliadoYSeLeRestaLaVidaCorrespondiente() throws NoMePuedesColocarUnaEntidadEnemigaException, UnAmigoNoMePuedeAtacarException, EntidadSeMurioException, YoNoPuedoSerDaniadaException, YoNoPuedoAtacarException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Jinete jineteAtacante = new Jinete();
        Soldado soldadoAtacado = new Soldado();
        jineteAtacante.setFaccion(faccion1);
        soldadoAtacado.setFaccion(faccion2);

        tablero.colocarEntidad(jineteAtacante, new Posicion(9,8));
        tablero.colocarEntidad(soldadoAtacado,new Posicion(10,9));

        Vacio destinoC = (Vacio) tablero.obtenerCasillero(new Posicion(9,9));
        soldadoAtacado.moverA(tablero,destinoC,faccion2);

        Ocupado casilleroAtacado = (Ocupado) tablero.obtenerCasillero(new Posicion(9,9));

        jineteAtacante.atacar(casilleroAtacado,tablero,faccion1);

        double danioEsperado = 100 - (5+5*0.05);

        assertTrue(soldadoAtacado.tenesEstaVida(danioEsperado));
    }

    /*JINETE NO PUEDE ATACAR PIEZA ALIADA*/
    @Test
    public void Test10JinetenoPuedeAtacarPiezaAliada() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Jinete jineteAtacante = new Jinete();
        Soldado soldadoAtacado = new Soldado();
        jineteAtacante.setFaccion(faccion1);
        soldadoAtacado.setFaccion(faccion1);

        tablero.colocarEntidad(jineteAtacante, new Posicion(9,8));
        tablero.colocarEntidad(soldadoAtacado,new Posicion(9,9));


        Ocupado casilleroAtacado = (Ocupado) tablero.obtenerCasillero(new Posicion(9,9));

        assertThrows(UnAmigoNoMePuedeAtacarException.class,()-> jineteAtacante.atacar(casilleroAtacado,tablero,faccion1));

    }

    /*CURANDERO CURA PIEZA ALIADA*/
    @Test
    public void Test11CuranderoCuraPiezaAliada() throws NoMePuedesColocarUnaEntidadEnemigaException, JugadorNoMePuedeDecirQueCure, UnEnemigoNoMePuedeCurarException, YoNoPuedoSerCuradaException, YoNoPuedoCurarException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Curandero curandero = new Curandero();
        Soldado soldadoCurado = new Soldado();
        curandero.setFaccion(faccion1);
        soldadoCurado.setFaccion(faccion1);

        tablero.colocarEntidad(curandero, new Posicion(9,8));
        tablero.colocarEntidad(soldadoCurado,new Posicion(9,9));


        Ocupado casilleroCurado = (Ocupado) tablero.obtenerCasillero(new Posicion(9,9));

        curandero.curar(casilleroCurado,faccion1);

        assertTrue(soldadoCurado.tenesEstaVida(115));

    }

    /*CURANDERO NO PUEDE CURAR PIEZA ENEMIGA*/
    @Test
    public void Test12CuranderoNoPuedeCurarPiezaEnemiga() throws NoMePuedesColocarUnaEntidadEnemigaException {
        Faccion faccion1 = new Faccion();
        Faccion faccion2 = new Faccion();
        Tablero tablero = new Tablero(faccion1,faccion2);

        Curandero curandero = new Curandero();
        Soldado soldadoEnemigo = new Soldado();
        curandero.setFaccion(faccion1);
        soldadoEnemigo.setFaccion(faccion2);

        tablero.colocarEntidad(curandero, new Posicion(9,8));
        tablero.colocarEntidad(soldadoEnemigo,new Posicion(10,8));


        Ocupado casilleroCurado = (Ocupado) tablero.obtenerCasillero(new Posicion(10,8));

        assertThrows(UnEnemigoNoMePuedeCurarException.class,()->curandero.curar(casilleroCurado,faccion1));

    }




}
