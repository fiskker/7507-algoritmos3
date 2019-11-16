package AlgoChess.engine.comandante;

import AlgoChess.engine.entidades.Entidad;
import AlgoChess.engine.interfaces.casillero.Recuadro;
import AlgoChess.engine.interfaces.entidades.PuedeFormarBatallon;
import AlgoChess.engine.posicion.Posicion;
import AlgoChess.engine.tablero.Tablero;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import static AlgoChess.engine.Constantes.TAMANIO_BATALLON;

public class Comandante {
    private Queue<Posicion> cola;
    private HashSet<Posicion> visitados;
    private HashSet<PuedeFormarBatallon> batallon;
    private int tamanioBatallon;
    private Tablero tablero;
    private HashSet<PuedeFormarBatallon> batallonParaLoop;
    private HashSet<Posicion> posicionesReclutas;
    private Posicion instrucciones;
    private HashSet<PuedeFormarBatallon> reclutasYaMovidos;

    public Comandante(Tablero tablero_) {
        cola = new LinkedList<>();
        visitados = new HashSet<>();
        batallon = new HashSet<>();
        tamanioBatallon = TAMANIO_BATALLON;
        tablero = tablero_;
        batallonParaLoop = new HashSet<>();
        posicionesReclutas = new HashSet<>();
    }

    private HashSet<Posicion> generarAdyacentes(Posicion posicion) {
        return posicion.generarPosicionesEnAlcance(1,1);
    }

    /*BFS*/
    public void recluteMisCercanos(PuedeFormarBatallon entidad) {
        batallon.add(entidad);

        cola.add(entidad.getPosicion());

        while (cola.size() != 0 && batallon.size() < tamanioBatallon) {
            Posicion actualPosicion = cola.remove();
            HashSet<Posicion> posiciones = generarAdyacentes(actualPosicion);
            for (Posicion posicion : posiciones) {
                if (!visitados.contains(posicion)) {
                    visitados.add(posicion);
                    tablero.reclutarEntidades(posicion, batallon, cola, entidad);
                }
            }
        }
    }

    private void generarInstrucciones(Recuadro destino, Entidad entidad) {
        int movimientoFila = destino.getPosicion().getFila() - entidad.getPosicion().getFila();
        int movimientoColumna = destino.getPosicion().getColumna() - entidad.getPosicion().getColumna();

        instrucciones = new Posicion(movimientoFila, movimientoColumna);
    }

    private Posicion generarPosicionPotencial(PuedeFormarBatallon recluta, Posicion instrucciones) {
        int movimientoColumna = instrucciones.getColumna();
        int movimientoFila = instrucciones.getFila();
        int posicionColumna = recluta.getPosicion().getColumna();
        int posicionFila = recluta.getPosicion().getFila();


        return new Posicion(posicionFila + movimientoFila, posicionColumna + movimientoColumna);
    }

    private void moverSetup(){
        for (PuedeFormarBatallon unRecluta : batallon) {
            batallonParaLoop.add(unRecluta);
            posicionesReclutas.add(unRecluta.getPosicion());
        }
        reclutasYaMovidos = new HashSet<>();

    }

    private boolean estaEnPosicionesReclutas(Posicion posicionPotencial){
        for (Posicion posicion : posicionesReclutas) { if (posicionPotencial.esIgual(posicion)) {return true; } }
        return false;
    }

    public boolean moverBatallon(Recuadro destino, Entidad entidad) {
        if (batallon.size() != TAMANIO_BATALLON) {return false;}

        moverSetup();
        generarInstrucciones(destino, entidad);

        while (batallon.size() != 0) {
            for (PuedeFormarBatallon recluta : batallonParaLoop) {
                if(reclutasYaMovidos.contains(recluta)) continue;
                Posicion posicionPotencial = generarPosicionPotencial(recluta, instrucciones);
                if (!estaEnPosicionesReclutas(posicionPotencial) ) {
                    Posicion antigua = recluta.getPosicion();
                    batallon.remove(recluta);
                    reclutasYaMovidos.add(recluta);
                    Recuadro casillero = tablero.obtenerCasillero(posicionPotencial);
                    posicionesReclutas.remove(antigua);
                    boolean seMovio = recluta.moverComoRecluta(tablero, casillero);
                    if (seMovio) {tablero.colocarVacio(antigua);}
                }
            }
        }
        return true;
    }

}