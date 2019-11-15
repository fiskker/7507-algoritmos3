package AlgoChess.entidades;

import AlgoChess.engine.entidades.Curandero;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

public class CuranderoTest {

    @Test
    public void Test00ConstructorDevuelveCurandero(){
        Curandero curandero = new Curandero();
        assertNotNull(curandero);
    }

    @Test
    public void Test01CuranderoSeClona(){
        Curandero curandero = new Curandero();
        Curandero curandero1 = curandero.clonar();

        assertNotSame(curandero1, curandero);
    }
}
