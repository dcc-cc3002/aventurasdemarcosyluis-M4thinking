import com.aventurasdemarcosyluis.characterstats.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    private Player testMarcos;
    private Player testLuis;

    @BeforeEach
    public void setUp() {
        testMarcos = new Player(10, 4, 10, 9, 12);
        testLuis = new Player(1, 1, 0, 1, 1);
    }

    @Test
    public void constructorTest() {
        assertTrue(testLuis.isKO());
        int atk = testMarcos.getAtk();
        testLuis.setAtk(atk); //By effect of the invariant, atk = 0
        int def = testMarcos.getDef();
        testLuis.setDef(def);
        int fp = testMarcos.getFp();
        testLuis.setFp(fp);
        int lvl = testMarcos.getLvl();
        testLuis.setLvl(lvl);
        int hp = testMarcos.getHp();
        testLuis.setHp(hp);
        assertNotEquals(testLuis, testMarcos);
        testLuis.setAtk(atk);// atk = 10
        assertEquals(testLuis, testMarcos);
    }

    @Test
    public void itemTest() {
        //Test Items;
    }
}
