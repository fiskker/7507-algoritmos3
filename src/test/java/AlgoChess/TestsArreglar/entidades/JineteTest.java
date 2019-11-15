package AlgoChess.entidades;

import AlgoChess.engine.entidades.Jinete;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

public class JineteTest {
    @Test
    public void Test00ConstructorDevuelveJinete(){
        Jinete jinete = new Jinete();
        assertNotNull(jinete);
    }

    @Test
    public void Test01JineteSeClona(){
        Jinete jinete = new Jinete();
        Jinete jinete1 = jinete.clonar();

        assertNotSame(jinete1, jinete);
    }

}
