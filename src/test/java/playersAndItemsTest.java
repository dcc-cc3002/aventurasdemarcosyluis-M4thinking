import com.example.aventurasdemarcoyluis.characters.enemiesconfig.IEnemy;
import com.example.aventurasdemarcoyluis.characters.enemiesconfig.enemies.Goomba;
import com.example.aventurasdemarcoyluis.characters.playersconfig.IPlayer;
import com.example.aventurasdemarcoyluis.characters.playersconfig.players.Luis;
import com.example.aventurasdemarcoyluis.characters.playersconfig.players.Marcos;

import com.example.aventurasdemarcoyluis.itemsconfig.IItem;
import com.example.aventurasdemarcoyluis.itemsconfig.items.Star;
import com.example.aventurasdemarcoyluis.itemsconfig.items.RedMushroom;
import com.example.aventurasdemarcoyluis.itemsconfig.items.HoneySyrup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class playersAndItemsTest {

    private IPlayer testMarcos;
    private IPlayer testLuis;

    private IItem testRedMushroom;
    private IItem testHoneySyrup;
    private IItem testStar;

    /**
     * The message tests were based on: <a href="https://morioh.com/p/c14998c5c076 ">Unit Testing of System.out.println() with JUnit - Morioh</a>
     */
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        //Where the values will now be printed.
        System.setOut(new PrintStream(outputStreamCaptor));

        //Players
        testMarcos = new Marcos(11, 9, 7, 5, 3);
        testLuis = new Luis(12, 10, 8, 6, 4);

        //Items
        testRedMushroom = new RedMushroom();
        testHoneySyrup = new HoneySyrup();
        testStar = new Star();
    }

    @AfterEach
    public void tearDown() {
        //Restoring standard output stream.
        System.setOut(standardOut);
    }

    @Test
    public void constructorLuisTest() { //First message test.
        testLuis.typicalPhrase();
        assertEquals("Here we go!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void constructorMarcosTest() { //Second message test.
        testMarcos.typicalPhrase();
        assertEquals("Let's-a go!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void equalsForConstructorTest(){
        //Players are equals if they have all principal stats equals and his direct class

        assertNotEquals(testLuis, testMarcos);

        testLuis.setAtk(testMarcos.getAtk());
        testLuis.setDef(testMarcos.getDef());
        testLuis.setHp(testMarcos.getHp());
        testLuis.setFp(testMarcos.getFp());
        testLuis.setLvl(testMarcos.getLvl());

        assertNotEquals(testLuis, testMarcos); // They have distinct max Hp and Fp.
        assertNotEquals(testLuis.hashCode(),testMarcos.hashCode());

        testLuis.setMaxHp(testMarcos.getMaxHp());
        testLuis.setMaxFp(testMarcos.getMaxFp());

        assertNotEquals(testLuis, testMarcos);
        assertNotEquals(testLuis.hashCode(),testMarcos.hashCode());

        //"Equals" not depends on items
        testMarcos.addItem(testRedMushroom);

        //"Equals" depends on the direct class Marcos/Luis too.
        assertNotEquals(testLuis, testMarcos);
        assertNotEquals(testLuis.hashCode(),testMarcos.hashCode());

        IPlayer anotherMarcos = new Marcos(11, 9, 7, 5, 3);

        //They had an item of difference, but that doesn't matter.
        assertEquals(testMarcos, anotherMarcos);
        assertEquals(testMarcos.hashCode(),anotherMarcos.hashCode());

        anotherMarcos.setHp(5);

        assertNotEquals(testMarcos, anotherMarcos);
        assertNotEquals(testMarcos.hashCode(),anotherMarcos.hashCode());

        assertEquals(anotherMarcos, anotherMarcos);
        assertEquals(anotherMarcos.hashCode(),anotherMarcos.hashCode());

        IEnemy testGoomba = new Goomba(11, 9, 7, 3);

        assertNotEquals(anotherMarcos, testGoomba);
    }

    @Test
    public void statsTest(){ //Testing getters and setters (Except setHp and setFp).

        assertEquals(11, testMarcos.getAtk());
        assertEquals(9, testMarcos.getDef());
        assertEquals(7, testMarcos.getMaxHp());
        assertEquals(7, testMarcos.getHp());    //Hp is equal to Max Hp when the object is constructed.
        assertEquals(5, testMarcos.getMaxFp());
        assertEquals(5, testMarcos.getFp());    //Fp is equal to Max Fp when the object is constructed.
        assertEquals(3, testMarcos.getLvl());

        testMarcos.setAtk(12);
        testMarcos.setDef(10);
        testMarcos.setMaxHp(8);
        testMarcos.setMaxFp(6);
        testMarcos.setLvl(4);

        assertEquals(12, testMarcos.getAtk());
        assertEquals(10, testMarcos.getDef());
        assertEquals(8, testMarcos.getMaxHp());
        assertEquals(7, testMarcos.getHp());    //Remains the same as the constructor.
        assertEquals(6, testMarcos.getMaxFp());
        assertEquals(5, testMarcos.getFp());    //Remains the same as the constructor.
        assertEquals(4, testMarcos.getLvl());
    }

    @Test
    public void restrictionForHpAndKoTest(){

        assertEquals(8, testLuis.getMaxHp());
        assertEquals(8, testLuis.getHp());
        assertFalse(testLuis.isKo());

        testLuis.setHp(-6); //This hp is behind 0, you will get 0 hp.

        assertEquals(0, testLuis.getHp());
        assertTrue(testLuis.isKo());

        testLuis.setHp(20); //This hp exceeds the Max Hp, you will get only the Max Hp.

        assertEquals(8, testLuis.getHp());
        assertFalse(testLuis.isKo());

        testLuis.setMaxHp(4); //Max Hp greater than 0 but less than Hp, you will have Hp = Max Hp.

        assertEquals(4, testLuis.getMaxHp());
        assertEquals(4, testLuis.getHp());
        assertFalse(testLuis.isKo());

        testLuis.setMaxHp(-3); //Max Hp can't be behind 0, you will get 0 Hp and Max Hp.

        assertEquals(0, testLuis.getMaxHp());
        assertEquals(0, testLuis.getHp());
        assertTrue(testLuis.isKo());
    }

    @Test
    public void restrictionForFpTest(){

        assertEquals(6, testLuis.getMaxFp());
        assertEquals(6, testLuis.getFp());

        testLuis.setFp(-6); //This fp is behind 0, you will get 0 fp.

        assertEquals(0, testLuis.getFp());

        testLuis.setFp(20); //This fp exceeds the Max Fp, you will get only the Max Fp.

        assertEquals(6, testLuis.getFp());

        testLuis.setMaxFp(3); //Max Fp greater than 0 but less than Fp, you will have Fp = Max Fp.

        assertEquals(3, testLuis.getMaxFp());
        assertEquals(3, testLuis.getFp());

        testLuis.setMaxFp(-3); //Max Fp can't be behind 0, you will get 0 Fp and Max Fp.

        assertEquals(0, testLuis.getMaxFp());
        assertEquals(0, testLuis.getFp());
    }

    @Test
    public void itemConstructor(){
        assertNotEquals(testStar,testHoneySyrup);
        assertNotEquals(testStar.hashCode(),testHoneySyrup.hashCode());

        assertNotEquals(testHoneySyrup,testRedMushroom);
        assertNotEquals(testHoneySyrup.hashCode(),testRedMushroom.hashCode());

        assertNotEquals(testRedMushroom,testStar);
        assertNotEquals(testRedMushroom.hashCode(),testStar.hashCode());

    }

    @Test
    public void useStarItemTest() {
        //Causes the player who consumes it, enter the invincible state.
        assertEquals(7, testMarcos.getHp());

        testMarcos.addItem(testStar);
        testMarcos.addItem(testStar);//A player can have more than one instance of this item.

        assertFalse(testMarcos.getInvincible());

        testMarcos.selectItem(testStar);//Select once.

        assertTrue(testMarcos.getInvincible());

        testMarcos.setInvincible(false);

        assertFalse(testMarcos.getInvincible());

        testMarcos.selectItem(testStar); //Select twice.

        assertTrue(testMarcos.getInvincible());

        testMarcos.setInvincible(false);

        testMarcos.selectItem(testStar); //Select without having the item.

        assertFalse(testMarcos.getInvincible());
    }

    @Test
    public void useRedMushroomItemTest(){
        //This item heals the character for an amount of 10% of the character's HP.
        assertEquals(7, testMarcos.getMaxHp());

        testMarcos.setHp(1);

        testMarcos.addItem(testRedMushroom);
        testMarcos.addItem(testRedMushroom);//A player can have more than one instance of this item.

        assertEquals(1, testMarcos.getHp());

        testMarcos.selectItem(testRedMushroom);//Select once

        assertEquals(2, testMarcos.getHp());

        testMarcos.setHp(7);

        assertEquals(7, testMarcos.getHp());

        testMarcos.selectItem(testRedMushroom); //Select twice, but now with full hp.

        assertEquals(7, testMarcos.getHp());

        testMarcos.setHp(1);

        testMarcos.selectItem(testRedMushroom); // Select without having the item.

        assertEquals(1, testMarcos.getHp());

    }

    @Test
    public void useHoneySyrupTest(){
        //This item restores the character an amount of 3 FP.
        assertEquals(6, testLuis.getMaxFp());

        testLuis.setFp(1);

        testLuis.addItem(testHoneySyrup);
        testLuis.addItem(testHoneySyrup);//A player can have more than one instance of this item.

        assertEquals(1, testLuis.getFp());

        testLuis.selectItem(testHoneySyrup);//Select once.

        assertEquals(4, testLuis.getFp());

        testLuis.selectItem(testHoneySyrup); //Select twice, but exceeding the maximum, so you will get the max FP.

        assertEquals(6, testLuis.getFp());

        testLuis.setFp(1);

        testLuis.selectItem(testHoneySyrup); // Select without having the item.

        assertEquals(1, testLuis.getFp());
    }

    @Test
    public void toStringLuisTest(){
        testLuis.addItem(testStar);
        testLuis.addItem(testStar);
        testLuis.setHp(-5);

        System.out.println(testLuis.toString());
        assertEquals(   "Luis{atk=12, def=10, hp=0, maxHp=8, lvl=4, fp=6, maxFp=6, invincible=false, " +
                        "armament={Star=2, Red Mushroom=0, Honey Syrup=0}}",
                                outputStreamCaptor.toString().trim());
    }

    @Test
    public void toStringMarcosTest(){
        testMarcos.addItem(testHoneySyrup);
        testMarcos.addItem(testHoneySyrup);
        testMarcos.addItem(testRedMushroom);
        testMarcos.setFp(-6);

        System.out.println(testMarcos.toString());
        assertEquals(   "Marcos{atk=11, def=9, hp=7, maxHp=7, lvl=3, fp=0, maxFp=5, invincible=false, " +
                        "armament={Star=0, Red Mushroom=1, Honey Syrup=2}}",
                                outputStreamCaptor.toString().trim());
    }
}
