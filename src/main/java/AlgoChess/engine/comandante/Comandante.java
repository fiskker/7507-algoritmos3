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
        HashSet<Posicion> contiguos = new HashSet<>();

        int fila = posicion.getFila();
        int columna = posicion.getColumna();

        //TODO mejorar esto con un loop
        contiguos.add(new Posicion(fila - 1, columna)); //arriba
        contiguos.add(new Posicion(fila + 1, columna)); //abajo
        contiguos.add(new Posicion(fila, columna + 1)); //derecha
        contiguos.add(new Posicion(fila, columna - 1));//izquierda
        contiguos.add(new Posicion(fila - 1, columna + 1)); //arriba+derecha
        contiguos.add(new Posicion(fila - 1, columna - 1));//arriba+izquierda
        contiguos.add(new Posicion(fila + 1, columna + 1));//abajo+derecha
        contiguos.add(new Posicion(fila + 1, columna - 1));//abajo+izquierda

        for(Posicion unaPosicion : contiguos){
            int fila_ = unaPosicion.getFila();
            int columna_ = unaPosicion.getColumna();
            if(fila_ < 0 || fila_ > 19) contiguos.remove(unaPosicion);
            if(columna_ < 0 || columna_ > 19) contiguos.remove(unaPosicion);
        }

        return contiguos;

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

    public boolean moverBatallon(Recuadro destino, Entidad entidad) {
        if (batallon.size() != TAMANIO_BATALLON) {return false;}

        moverSetup();
        generarInstrucciones(destino, entidad);


        while (batallon.size() != 0) {
            for (PuedeFormarBatallon recluta : batallonParaLoop) {
                if(reclutasYaMovidos.contains(recluta)) continue;
                Posicion posicionPotencial = generarPosicionPotencial(recluta, instrucciones);
                boolean estaEnPosicionesReclutas = false;
                for (Posicion posicion : posicionesReclutas) {
                    if (posicionPotencial.esIgual(posicion)) {
                        estaEnPosicionesReclutas = true;
                    }
                }

                if (!estaEnPosicionesReclutas ) {
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
