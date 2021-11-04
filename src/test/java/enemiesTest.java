
import com.example.aventurasdemarcoyluis.model.characters.enemies.EnemyType;
import com.example.aventurasdemarcoyluis.model.characters.enemies.IEnemy;
import com.example.aventurasdemarcoyluis.model.characters.enemies.Boo;
import com.example.aventurasdemarcoyluis.model.characters.enemies.Goomba;
import com.example.aventurasdemarcoyluis.model.characters.enemies.Spiny;
import com.example.aventurasdemarcoyluis.model.characters.players.IPlayer;
import com.example.aventurasdemarcoyluis.model.characters.players.Marcos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class enemiesTest {

    private IEnemy testGoomba;
    private IEnemy testSpiny;
    private IEnemy testBoo;

    /**
     * The message tests were based on: <a href="https://morioh.com/p/c14998c5c076 ">Unit Testing of System.out.println() with JUnit - Morioh</a>
     */
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        //Where the values will now be printed.
        System.setOut(new PrintStream(outputStreamCaptor));

        //Enemies
        testGoomba = new Goomba(2,4,15,8);
        testSpiny = new Spiny(5,4,18,8);
        testBoo = new Boo(5,8,17,8);
    }

    @AfterEach
    public void tearDown() {
        //Restoring standard output stream.
        System.setOut(standardOut);
    }

    @Test
    public void constructorGoombaTest() { //First message test.
        testGoomba.insult();
        assertEquals("frrr, frrr, it's Gooomba!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void constructorBooTest() { //Second message test.
        testBoo.insult();
        assertEquals("I'm Boo and I'm going to scare you!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void constructorSpinyTest() { //Third message test.
        testSpiny.insult();
        assertEquals("I'm going to prick you. Ouch!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void enemyTypeTest() {
        assertEquals(EnemyType.BOO,testBoo.getType());
        assertEquals(EnemyType.GOOMBA,testGoomba.getType());
        assertEquals(EnemyType.SPINY,testSpiny.getType());

        assertNotEquals(testBoo, testSpiny);
        assertNotEquals(testBoo.hashCode(),testSpiny.hashCode());

        testSpiny.setType(EnemyType.BOO);

        assertNotEquals(testBoo,testSpiny);
        assertNotEquals(testBoo.hashCode(),testSpiny.hashCode());

        testSpiny.setDef(8);

        assertNotEquals(testBoo, testSpiny); //Different class

        IEnemy anotherGoomba = new Goomba(2,3,15,8);

        assertNotEquals(testGoomba, anotherGoomba);
        assertNotEquals(testGoomba.hashCode(),anotherGoomba.hashCode());

        anotherGoomba.setDef(4);

        assertEquals(anotherGoomba,testGoomba);

        IPlayer testMarcos = new Marcos(2,3,15,6,8);

        assertNotEquals(anotherGoomba,testMarcos);

        assertEquals(anotherGoomba,anotherGoomba);
    }


    @Test
    public void toStringBooTest() {
        testBoo.setHp(7);
        System.out.println(testBoo.toString());
        assertEquals(   "Boo{atk=5, def=8, hp=7, maxHp=17, lvl=8}" ,
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void toStringGoombaTest() {
        testGoomba.setMaxHp(4);
        System.out.println(testGoomba.toString());
        assertEquals(   "Goomba{atk=2, def=4, hp=4, maxHp=4, lvl=8}" ,
                outputStreamCaptor.toString().trim());
    }

    @Test
    public void toStringSpinyTest() {
        testSpiny.setMaxHp(-3);
        System.out.println(testSpiny.toString());
        assertEquals(   "Spiny{atk=5, def=4, hp=0, maxHp=0, lvl=8}" ,
                outputStreamCaptor.toString().trim());
    }

}
