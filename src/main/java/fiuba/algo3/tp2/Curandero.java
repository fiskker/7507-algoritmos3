package fiuba.algo3.tp2;

public class Curandero extends Tipo {

	private int vida = 75;
	private int costo = 2;

	public Curandero(Jugador propietario) {
		setPropietario(propietario);
	}

    @Override 
    public int getVida() {
        return this.vida;
    }

	@Override
	public int getCosto() {
		return this.costo;
	}

    private void restarVida(int vida) {
        // Todo: Ver si murio la entidad => hay que sacarlo de la coleccion de entidades del jugador
        this.vida -= vida;
    }
    
	@Override
	public void restarAJugador() {
		getPropietario().restarPuntos(costo);
	}

	@Override
    public void atacar(Entidad entidadAtacada, int distancia) {
        /*   Puede curar a una unidad Aliada (menos a la Catapulta) en una distancia cercana.
        */
     
    }

    @Override 
    public void recibirDanio(int danio, int distancia) {
        restarVida(danio);
    }

}
