import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.enemies.Boo;
import com.example.aventurasdemarcoyluis.model.characters.enemies.Goomba;
import com.example.aventurasdemarcoyluis.model.characters.enemies.Spiny;
import com.example.aventurasdemarcoyluis.model.characters.players.Luis;
import com.example.aventurasdemarcoyluis.model.characters.players.Marcos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class attackTest {

	private Marcos testMarcos;
	private Luis testLuis;

	private Goomba testGoomba;
	private Spiny testSpiny;
	private Boo testBoo;

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
		testMarcos = new Marcos(11, 9, 11, 5, 3);
		testLuis = new Luis(12, 10, 13, 6, 4);

		//Enemies
		testGoomba = new Goomba(10, 4, 15, 8);
		testSpiny = new Spiny(12, 4, 18, 8);
		testBoo = new Boo(5, 8, 17, 8);
	}

	@AfterEach
	public void tearDown() {
		//Restoring standard output stream.
		System.setOut(standardOut);
	}

	@Test
	public void attackTypesStatsTest() {
		//Add here all new AttackTypes and test them.

		//MARTILLO
		assertEquals(1.5, AttackType.MARTILLO.attackConstant);
		assertEquals(0.25, AttackType.MARTILLO.failProbability);
		assertEquals(2, AttackType.MARTILLO.fpCost);

		//SALTO
		assertEquals(1, AttackType.SALTO.attackConstant);
		assertEquals(0, AttackType.SALTO.failProbability);
		assertEquals(1, AttackType.SALTO.fpCost);

		//Obs: (Rounded int) is notation for (int) (number + 0.5).
	}

	@Test
	public void marcosAttackGoombaTest() {
		//Initial conditions:
		assertEquals(15, testGoomba.getHp());
		assertEquals(5, testMarcos.getFp());
		assertFalse(testMarcos.isKo());

		testMarcos.setMaxFp(100); //To see if the fp is used correctly or there is an overflow

		//Test MARTILLO
		int j1 = 0;
		int failed1 = 0;
		int connected1 = 0;
		while (j1 < 160) { //To be sure that at some point it attacks.
			//(Rounded int) ( K=1.5 * ATK=11 * (LVL=3 / DEF = 4) )  == 12. With 25% probability to fail.
			testMarcos.attack(testGoomba, AttackType.MARTILLO);
			testMarcos.setFp(testMarcos.getFp() + 2);
			if (testGoomba.getHp() == 3) {
				connected1++;
			}
			if (testGoomba.getHp() == 15) {
				failed1++;
			}
			testGoomba.setHp(15);
			j1++;
		}

		//Count the number of times that connect and fail the attack.
		assert connected1 > 60;// ( 60 / ( 60 + 20 ) ) = 0.75
		assert failed1 > 20;// ( 20 / ( 60 + 20 ) ) = 0.25

		assertEquals(5, testMarcos.getFp());

		//Test SALTO
		int j2 = 0;
		int failed2 = 0;
		int connected2 = 0;
		while (j2 < 100) { //To be sure that at some point it attacks.
			//(Rounded int) ( K=1.0 * ATK=11 * (LVL=3 / DEF=4) )  == 8. With 0% probability to fail.
			testMarcos.attack(testGoomba, AttackType.SALTO);
			testMarcos.setFp(testMarcos.getFp() + 1);
			if (testGoomba.getHp() == 7) {
				connected2++;
			}
			if (testGoomba.getHp() == 15) {
				failed2++;
			}
			testGoomba.setHp(15);
			j2++;
		}
		//Count the number of times that connect and fail the attack.
		assert connected2 == 100;
		assert failed2 == 0;

		assertEquals(5, testMarcos.getFp());

		//Without FP
		testMarcos.setFp(0);
		assertFalse(testMarcos.isKo());

		//We test several times to go beyond the effect of the probability of failure (25%).
		for (int i = 0; i < 20; i++) {
			testMarcos.attack(testGoomba, AttackType.MARTILLO);
			assertEquals(15, testGoomba.getHp());//Goomba does not take the attack.
		}

		//We test several times to go beyond the effect of the probability of failure (0%).
		for (int i = 0; i < 20; i++) {
			testMarcos.attack(testGoomba, AttackType.SALTO);
			assertEquals(15, testGoomba.getHp());//Goomba does not take the attack.
		}

		//K.O. Requirements
		testMarcos.setFp(5);

		//if you do this you will lose your atk forever.
		testMarcos.setHp(-5);//This hp is behind 0, you will get 0 hp.

		assertTrue(testMarcos.isKo()); //If you are K.O. you attack with 0 damage.

		//We test several times to go beyond the effect of the probability of failure (25%).
		for (int i = 0; i < 20; i++) { //
			testMarcos.attack(testGoomba, AttackType.MARTILLO);
			testMarcos.setFp(testMarcos.getFp() + 2);
			assertEquals(15, testGoomba.getHp());
		}

		//We test several times to go beyond the effect of the probability of failure (0%).
		for (int i = 0; i < 20; i++) { //
			testMarcos.attack(testGoomba, AttackType.SALTO);
			testMarcos.setFp(testMarcos.getFp() + 1);
			assertEquals(15, testGoomba.getHp());
		}
	}

	@Test
	public void luisAttackGoombaTest() {
		//Initial conditions:
		assertEquals(15, testGoomba.getHp());
		assertEquals(6, testLuis.getFp());
		assertFalse(testMarcos.isKo());

		testLuis.setMaxFp(100); //To see if the fp is used correctly or there is an overflow

		//Test MARTILLO
		int j1 = 0;
		int failed1 = 0;
		int connected1 = 0;
		while (j1 < 160) { //To be sure that at some point it attacks.
			//(Rounded int) ( K=1.5 * ATK=12 * (LVL=4 / DEF = 4) )  == 18. With 25% probability to fail.
			testLuis.attack(testGoomba, AttackType.MARTILLO);
			testLuis.setFp(testLuis.getFp() + 2);
			if (testGoomba.getHp() == 0) {
				connected1++;
			}
			if (testGoomba.getHp() == 15) {
				failed1++;
			}
			testGoomba.setHp(15);
			j1++;
		}

		//Count the number of times that connect and fail the attack.
		assert connected1 > 60;// ( 60 / ( 60 + 20 ) ) = 0.75
		assert failed1 > 20;// ( 20 / ( 60 + 20 ) ) = 0.25

		assertEquals(6, testLuis.getFp());

		//Test SALTO
		int j2 = 0;
		int failed2 = 0;
		int connected2 = 0;
		while (j2 < 100) { //To be sure that at some point it attacks.
			//(Rounded int) ( K=1.0 * ATK=12 * (LVL=4 / DEF=4) )  == 12. With 0% probability to fail.
			testLuis.attack(testGoomba, AttackType.SALTO);
			testLuis.setFp(testLuis.getFp() + 1);
			if (testGoomba.getHp() == 3) {
				connected2++;
			}
			if (testGoomba.getHp() == 15) {
				failed2++;
			}
			testGoomba.setHp(15);
			j2++;
		}
		//Count the number of times that connect and fail the attack.
		assert connected2 == 100;
		assert failed2 == 0;

		assertEquals(6, testLuis.getFp());

		//Without FP
		testLuis.setFp(0);
		assertFalse(testLuis.isKo());

		//We test several times to go beyond the effect of the probability of failure (25%).
		for (int i = 0; i < 20; i++) {
			testLuis.attack(testGoomba, AttackType.MARTILLO);
			assertEquals(15, testGoomba.getHp());//Goomba does not take the attack.
		}

		//We test several times to go beyond the effect of the probability of failure (0%).
		for (int i = 0; i < 20; i++) {
			testLuis.attack(testGoomba, AttackType.SALTO);
			assertEquals(15, testGoomba.getHp());//Goomba does not take the attack.
		}

		//K.O. Requirements
		testLuis.setFp(6);

		//if you do this you will lose your atk forever.
		testLuis.setHp(-5);//This hp is behind 0, you will get 0 hp.

		assertTrue(testLuis.isKo()); //If you are K.O. you attack with 0 damage.

		//We test several times to go beyond the effect of the probability of failure (25%).
		for (int i = 0; i < 20; i++) { //
			testLuis.attack(testGoomba, AttackType.MARTILLO);
			testLuis.setFp(testLuis.getFp() + 2);
			assertEquals(15, testGoomba.getHp());
		}

		//We test several times to go beyond the effect of the probability of failure (0%).
		for (int i = 0; i < 20; i++) { //
			testLuis.attack(testGoomba, AttackType.SALTO);
			testLuis.setFp(testMarcos.getFp() + 1);
			assertEquals(15, testGoomba.getHp());
		}
	}

	@Test
	public void marcosAttackSpinyTest() {
		//Initial conditions:
		assertEquals(18, testSpiny.getHp());
		assertEquals(11, testMarcos.getHp());
		assertEquals(5, testMarcos.getFp());
		assertFalse(testMarcos.isKo());

		testMarcos.setMaxFp(100); //To see if the fp is used correctly or there is an overflow

		//Test MARTILLO
		int j1 = 0;
		int failed1 = 0;
		int connected1 = 0;
		while (j1 < 160) { //To be sure that at some point it attacks.
			//(Rounded int) ( K=1.5 * ATK=11 * (LVL=3 / DEF=4) )  == 12. With 25% probability to fail.
			testMarcos.attack(testSpiny, AttackType.MARTILLO);
			testMarcos.setFp(testMarcos.getFp() + 2);
			if (testSpiny.getHp() == 6) {
				connected1++;
			}
			if (testSpiny.getHp() == 18) {
				failed1++;
			}
			testSpiny.setHp(18);
			j1++;
		}
		//Count the number of times that connect and fail the attack.
		assert connected1 > 60; // ( 60 / ( 60 + 20 ) ) = 0.75
		assert failed1 > 20;  // ( 20 / ( 60 + 20 ) ) = 0.25

		assertEquals(5, testMarcos.getFp());
		assertEquals(11, testMarcos.getHp());

		//Test SALTO
		int j2 = 0;
		int failed2 = 0;
		int connected2 = 0;
		while (j2 < 100) { //To be sure that at some point it attacks.
			//This attack will not be calculated.
			testMarcos.attack(testSpiny, AttackType.SALTO);
			//Spiny does 5% of maximum health damage (Rounded int) (HP=11 - maxHp=11*0.05) == 10.
			assertEquals(10, testMarcos.getHp());
			testMarcos.setFp(testMarcos.getFp() + 1);
			//Hypothetical damage (Rounded int) ( K=1.0 * ATK=11 * (LVL=3 / DEF=4) )  == 8. With 0% probability to fail.
			if (testSpiny.getHp() == 10) {
				connected2++;
			}
			if (testSpiny.getHp() == 18) {
				failed2++;
			}
			testMarcos.setHp(11);
			j2++;
		}
		//Count the number of times that connect and fail the attack.
		assert connected2 == 0; //Spiny is immune to jumping.
		assert failed2 == 100;

		assertEquals(5, testMarcos.getFp());
		assertEquals(18, testSpiny.getHp());

		//Without FP
		testMarcos.setFp(0);
		assertEquals(11, testMarcos.getHp());

		//We test several times to go beyond the effect of the probability of failure (25%).
		for (int i = 0; i < 20; i++) {
			testMarcos.attack(testSpiny, AttackType.MARTILLO);
			assertEquals(18, testSpiny.getHp());//Spiny does not take the attack.
		}

		//We test several times to go beyond the effect of the probability of failure (0%).
		for (int i = 0; i < 20; i++) {
			testMarcos.attack(testSpiny, AttackType.SALTO);
			assertEquals(18, testSpiny.getHp());//Spiny does not take the attack.
			assertEquals(11, testMarcos.getHp());//Marcos does not lose life if he cannot attack.
		}

		//K.O. Requirements
		testMarcos.setFp(5);

		//if you do this you will lose your atk forever.
		testMarcos.setHp(-5);//This hp is behind 0, you will get 0 hp.

		assertTrue(testMarcos.isKo()); //If you are K.O. you attack with 0 damage.

		//We test several times to go beyond the effect of the probability of failure (25%).
		for (int i = 0; i < 20; i++) {
			testMarcos.attack(testSpiny, AttackType.MARTILLO);
			testMarcos.setFp(testMarcos.getFp() + 2);
			assertEquals(18, testSpiny.getHp());
		}

		//We test several times to go beyond the effect of the probability of failure (0%).
		for (int i = 0; i < 20; i++) {
			testMarcos.attack(testSpiny, AttackType.SALTO);
			testMarcos.setFp(testMarcos.getFp() + 1);
			assertEquals(18, testSpiny.getHp()); //Attack with zero damage.
		}
	}

	@Test
	public void luisAttackSpinyTest() {
		assertEquals(18, testSpiny.getHp());
		assertEquals(13, testLuis.getHp());
		assertEquals(6, testLuis.getFp());
		assertFalse(testLuis.isKo());

		testLuis.setMaxFp(100); //To see if the fp is used correctly or there is an overflow

		//Test MARTILLO
		int j1 = 0;
		int failed1 = 0;
		int connected1 = 0;
		while (j1 < 160) { //To be sure that at some point it attacks.
			//(Rounded int) ( K=1.5 * ATK=12 * (LVL=4 / DEF=4) )  == 18. With 25% probability to fail.
			testLuis.attack(testSpiny, AttackType.MARTILLO);
			testLuis.setFp(testLuis.getFp() + 2);
			if (testSpiny.getHp() == 0) {
				connected1++;
			}
			if (testSpiny.getHp() == 18) {
				failed1++;
			}
			testSpiny.setHp(18);
			j1++;
		}
		//Count the number of times that connect and fail the attack.
		assert connected1 > 60; // ( 60 / ( 60 + 20 ) ) = 0.75
		assert failed1 > 20;  // ( 20 / ( 60 + 20 ) ) = 0.25

		assertEquals(6, testLuis.getFp());
		assertEquals(13, testLuis.getHp());

		//Test SALTO
		int j2 = 0;
		int failed2 = 0;
		int connected2 = 0;
		while (j2 < 100) { //To be sure that at some point it attacks.
			//This attack will not be calculated.
			testLuis.attack(testSpiny, AttackType.SALTO);
			//Spiny does 5% of maximum health damage (Rounded int) (HP=13 - maxHp=13*0.05) = 12.
			assertEquals(12, testLuis.getHp());
			testLuis.setFp(testLuis.getFp() + 1);
			//Hypothetical damage (Rounded int) ( K=1.0 * ATK=11 * (LVL=3 / DEF=4) )  == 8. With 0% probability to fail.
			if (testSpiny.getHp() == 10) {
				connected2++;
			}
			if (testSpiny.getHp() == 18) {
				failed2++;
			}
			testLuis.setHp(13);
			j2++;
		}
		//Count the number of times that connect and fail the attack.
		assert connected2 == 0; //Spiny is immune to SALTO.
		assert failed2 == 100;

		assertEquals(6, testLuis.getFp());
		assertEquals(18, testSpiny.getHp());

		//Without FP
		testLuis.setFp(0);
		assertEquals(13, testLuis.getHp());

		//We test several times to go beyond the effect of the probability of failure (25%).
		for (int i = 0; i < 20; i++) {
			testLuis.attack(testSpiny, AttackType.MARTILLO);
			assertEquals(18, testSpiny.getHp());//Spiny does not take the attack.
		}

		//We test several times to go beyond the effect of the probability of failure (0%).
		for (int i = 0; i < 20; i++) {
			testLuis.attack(testSpiny, AttackType.SALTO);
			assertEquals(18, testSpiny.getHp());//Spiny does not take the attack.
			assertEquals(13, testLuis.getHp());//Marcos does not lose life if he cannot attack.
		}

		//K.O. Requirements
		testLuis.setFp(5);

		//if you do this you will lose your atk forever.
		testLuis.setHp(-5);//This hp is behind 0, you will get 0 hp.

		assertTrue(testLuis.isKo()); //If you are K.O. you attack with 0 damage.

		//We test several times to go beyond the effect of the probability of failure (25%).
		for (int i = 0; i < 20; i++) {
			testLuis.attack(testSpiny, AttackType.MARTILLO);
			testLuis.setFp(testLuis.getFp() + 2);
			assertEquals(18, testSpiny.getHp());
		}

		//We test several times to go beyond the effect of the probability of failure (0%).
		for (int i = 0; i < 20; i++) {
			testLuis.attack(testSpiny, AttackType.SALTO);
			testLuis.setFp(testLuis.getFp() + 1);
			assertEquals(18, testSpiny.getHp()); //Attack with zero damage.
		}
	}

	@Test
	public void marcosAttackBooTest() {
		//Initial conditions:
		assertEquals(17, testBoo.getHp());
		assertEquals(11, testMarcos.getHp());
		assertEquals(5, testMarcos.getFp());
		assertFalse(testMarcos.isKo());

		testMarcos.setMaxFp(100); //To see if the fp is used correctly or there is an overflow

		//Test MARTILLO
		int j1 = 0;
		int failed1 = 0;
		int connected1 = 0;
		while (j1 < 100) { //To be sure that at some point it attacks.
			//This attack will not be calculated.
			testMarcos.attack(testBoo, AttackType.MARTILLO);
			//Boo does no damage, when dodging MARTILLO.
			assertEquals(11, testMarcos.getHp());
			testMarcos.setFp(testMarcos.getFp() + 2);
			//Hypothetical damage (Rounded int) ( K=1.5 * ATK=11 * (LVL=3 / DEF=8) )  == 6. With 25% probability to fail.
			if (testBoo.getHp() == 11) {
				connected1++;
			}
			if (testBoo.getHp() == 17) {
				failed1++;
			}
			j1++;
		}
		//Count the number of times that connect and fail the attack.
		assert connected1 == 0; //Boo is immune to MARTILLO.
		assert failed1 == 100;

		assertEquals(5, testMarcos.getFp());
		assertEquals(17, testBoo.getHp());

		//Test SALTO
		int j2 = 0;
		int failed2 = 0;
		int connected2 = 0;
		while (j2 < 100) { //To be sure that at some point it attacks.
			//(Rounded int) ( K=1.0 * ATK=11 * (LVL=3 / DEF=8) )  == 4. With 0% probability to fail.
			testMarcos.attack(testBoo, AttackType.SALTO);
			testMarcos.setFp(testMarcos.getFp() + 1);
			if (testBoo.getHp() == 13) {
				connected2++;
			}
			if (testBoo.getHp() == 17) {
				failed2++;
			}
			testBoo.setHp(17);
			j2++;
		}
		//Count the number of times that connect and fail the attack.
		assert connected2 == 100;
		assert failed2 == 0;

		assertEquals(5, testMarcos.getFp());
		assertEquals(17, testBoo.getHp());


		//Without FP
		testMarcos.setFp(0);
		assertEquals(11, testMarcos.getHp());

		//We test several times to go beyond the effect of the probability of failure (25%).
		for (int i = 0; i < 20; i++) {
			testMarcos.attack(testBoo, AttackType.MARTILLO);
			assertEquals(17, testBoo.getHp());//Spiny does not take the attack.
		}

		//We test several times to go beyond the effect of the probability of failure (0%).
		for (int i = 0; i < 20; i++) {
			testMarcos.attack(testBoo, AttackType.SALTO);
			assertEquals(17, testBoo.getHp());//Boo does not take the attack.
		}

		//K.O. Requirements
		testMarcos.setFp(5);

		//if you do this you will lose your atk forever.
		testMarcos.setHp(-5);//This hp is behind 0, you will get 0 hp.

		assertTrue(testMarcos.isKo()); //If you are K.O. you attack with 0 damage.

		//We test several times to go beyond the effect of the probability of failure (25%).
		for (int i = 0; i < 20; i++) {
			testMarcos.attack(testBoo, AttackType.MARTILLO);
			testMarcos.setFp(testMarcos.getFp() + 2);
			assertEquals(17, testBoo.getHp());
		}

		//We test several times to go beyond the effect of the probability of failure (0%).
		for (int i = 0; i < 20; i++) {
			testMarcos.attack(testBoo, AttackType.SALTO);
			testMarcos.setFp(testMarcos.getFp() + 1);
			assertEquals(17, testBoo.getHp()); //Attack with zero damage.
		}
	}

	@Test
	public void goombaAttackMarcosLuisTest() {
		assertEquals(11, testMarcos.getHp());
		assertEquals(13, testLuis.getHp());

		testGoomba.attack(testMarcos);//(Rounded int) ( K=0.75 * ATK=12 * (LVL=8 / DEF=9) )  == 7.
		testGoomba.attack(testLuis);//(Rounded int) ( K=0.75 * ATK=12 * (LVL=8 / DEF=10) )  == 6.

		assertEquals(4, testMarcos.getHp());
		assertEquals(7, testLuis.getHp());

		testGoomba.setHp(-4);

		//Goomba can't attack if it's K.O.
		assertTrue(testGoomba.isKo());

		testGoomba.attack(testMarcos);
		testGoomba.attack(testLuis);

		assertEquals(4, testMarcos.getHp());
		assertEquals(7, testLuis.getHp());

	}

	@Test
	public void spinyAttackMarcosLuisTest() {
		assertEquals(11, testMarcos.getHp());
		assertEquals(13, testLuis.getHp());

		testSpiny.attack(testMarcos);//(Rounded int) ( K=0.75 * ATK=11 * (LVL=8 / DEF=9) )  == 8.
		testSpiny.attack(testLuis);//(Rounded int) ( K=0.75 * ATK=11 * (LVL=8 / DEF=10) )  == 7.

		assertEquals(3, testMarcos.getHp());
		assertEquals(6, testLuis.getHp());

		testSpiny.setHp(-4);

		assertTrue(testSpiny.isKo());

		//Spiny can't attack if it's K.O.
		testSpiny.attack(testMarcos);
		testSpiny.attack(testLuis);

		assertEquals(3, testMarcos.getHp());
		assertEquals(6, testLuis.getHp());
	}

	@Test
	public void booAttackLuisTest() {
		assertEquals(13, testLuis.getHp());

		testBoo.attack(testLuis);//(Rounded int) ( K=0.75 * ATK=5 * (LVL=8 / DEF=10) )  == 3.

		assertEquals(10, testLuis.getHp());

		testBoo.setHp(-4);

		//Goomba can't attack if it's K.O.
		assertTrue(testBoo.isKo());

		testBoo.attack(testLuis);

		assertEquals(10, testLuis.getHp());
	}

	@Test
	public void luisAttackBooTest(){
		assertEquals(17, testBoo.getHp());

		testLuis.attack(testBoo, AttackType.SALTO);

		testBoo.setSeed(4993); //Task 2 has seed's (such a better way to test)

		assertEquals(17, testBoo.getHp());

		testLuis.attack(testBoo, AttackType.MARTILLO);

		assertEquals(17, testBoo.getHp());

	}

	@Test
	public void booAttackMarcosTest(){
		assertEquals(11, testMarcos.getHp());

		testBoo.attack(testMarcos);

		assertEquals(11, testMarcos.getHp());
	}
}

