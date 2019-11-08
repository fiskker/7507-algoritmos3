package fiuba.algo3.tp2;

import fiuba.algo3.tp2.excepciones.CasilleroOcupadoException;

public class Ocupado implements Estado{
	private Casillero casillero;

	public Ocupado(Casillero casillero) {
		this.casillero = casillero;
	}

	@Override
	public Estado colocar(Entidad entidad) {
		throw new CasilleroOcupadoException();
	}

	@Override
	public Entidad getEntidad() {
		return casillero.getEntidad();
	}

	@Override
	public Estado moverDesde(Casillero casillero) {
		throw new CasilleroOcupadoException();
	}

}
