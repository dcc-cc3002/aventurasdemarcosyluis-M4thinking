
import com.aventurasdemarcosyluis.characterstats.Enemy;
import com.aventurasdemarcosyluis.types.EnemyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestEnemy {

    private Enemy testGoomba;
    private Enemy testBoo;
    private Enemy testSpiny;

    @BeforeEach
    public void setUp() {
        testGoomba= new Enemy(2,4,15,8, EnemyType.GOOMBA);
        testBoo = new Enemy(5,9,3,8, EnemyType.BOO);
        testSpiny = new Enemy(5,9,3,8, EnemyType.SPINY);
    }

    @Test
    public void constructorTest() {
        assertEquals(EnemyType.BOO,testBoo.getType());
        assertEquals(EnemyType.GOOMBA,testGoomba.getType());
        assertEquals(EnemyType.SPINY,testSpiny.getType());
        assertNotEquals(testBoo, testSpiny);
        testSpiny.setType(EnemyType.BOO);
        assertEquals(testBoo,testSpiny);
    }
}
