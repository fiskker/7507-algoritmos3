package fiuba.algo3.tp2.excepciones;

@SuppressWarnings("serial")
public class TipoNoPuedeCurarException extends RuntimeException {
		   public String toString(){
		     return ("Este tipo de entidad no puede curar") ;
		  }
}