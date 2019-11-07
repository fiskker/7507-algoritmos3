package fiuba.algo3.tp2;

public class Jinete extends Tipo {

	private int vida = 100;
	private int costo = 3;

	public Jinete(Jugador propietario) {
		setPropietario(propietario);
	};

	@Override
	public int getCosto() {
		return this.costo;
	}

    @Override 
    public int getVida() {
        return this.vida;
    }

    private void restarVida(int vida) {
        // Todo: Ver si murio la entidad => hay que sacarlo de la coleccion de entidades del jugador
        this.vida -= vida;
    }

	@Override
	public void restarAJugador() {
		getPropietario().restarPuntos(this.costo);
	}

	@Override
    public void atacar(Casillero destino, int distancia) {
        /*  - 	Si hay al menos un Soldado de Infantería aliado cerca o no hay ningún enemigo cerca, 
        		su arma de ataque es un Arco y Flecha y únicamente puede atacar a enemigos en distancia media..
			- 	Si no hay ningún aliado cercano y hay enemigos cercanos , su arma de ataque es una Espada y 
				únicamente puede atacar a enemigos en distancia corta.
        */
        int danio = CalculadorDanio.danio(this,distancia);
        destino.recibirAtaque(danio, distancia);
     
    }

    @Override 
    public void recibirAtaque(int danio, int distancia) {
        System.out.println("Jinete recibe");
        System.out.println(danio);
        restarVida(danio);
    }

}
