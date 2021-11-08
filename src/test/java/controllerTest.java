import com.example.aventurasdemarcoyluis.controller.GameController;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.enemies.*;
import com.example.aventurasdemarcoyluis.model.characters.players.Luis;
import com.example.aventurasdemarcoyluis.model.characters.players.Marcos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class controllerTest {
	private GameController c;

	private Marcos marcos;
	private Luis luis;

	private Boo boo;
	private Goomba goomba;
	private Spiny spiny;

	private Boo boo1;
	private Goomba goomba1;
	private Boo boo2;

	private Boo boo3;
	private Goomba goomba2;
	private Spiny spiny1;
	private Spiny spiny2;
	private Boo boo4;

	private Boo boo5;
	private Goomba goomba3;
	private Spiny spiny3;
	private Spiny spiny4;
	private Boo boo6;
	private Goomba goomba4;

	@BeforeEach
	public void setUp() {
		c = new GameController();
		c.setSeed(123456789); //Special seed with 3 different enemies at the beginning
		c.emptyChest();

		//Players
		marcos = new Marcos(33, 9, 25, 5, 1);
		luis = new Luis(25, 10, 22, 6, 1);

		//First battle
		boo = new Boo(9,5,11,8);
		goomba = new Goomba(7, 4,15, 8);
		spiny = new Spiny(8, 4, 5, 8);

		//Second battle
		boo1 = new Boo(9, 5, 11, 8);
		goomba1 = new Goomba(7, 4, 15, 8);
		boo2 = new Boo(9, 5, 11, 8);

		//Third battle
		boo3 = new Boo(9, 5, 11, 8);
		goomba2 = new Goomba(7, 4, 15, 8);
		spiny1 = new Spiny(8, 4, 5, 8);
		spiny2 = new Spiny(8, 4, 5, 8);
		boo4 = new Boo(9, 5, 11, 8);

		//...

		//Fifth battle
		boo5 = new Boo(9, 5, 11, 8);
		goomba3 = new Goomba(7, 4, 15, 8);
		spiny3 = new Spiny(8, 4, 5, 8);
		spiny4 = new Spiny(8, 4, 5, 8);
		boo6 = new Boo(9, 5, 11, 8);
		goomba4 = new Goomba(7, 4, 15, 8);
	}

	@Test
	public void phasesAndWinTest(){
		//Turns are index from 0 (Seen by programmer)
		//To choose a player or an enemy to be attack with index, starts from 1 (Seen by client)
		//Seeing c.getOwner() of the turn is the only way to know whose turn it is (Seen by client)

		/* initialPhase */
		assertEquals(new ArrayList<>(), c.getPlayers());
		assertEquals("{Red Mushroom=0, Honey Syrup=0}", c.getItems());

		c.initialPhase();//Add players of the game and first items

		//Expected list of players of the game
		assertEquals( new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayers());
		assertEquals("{Red Mushroom=3, Honey Syrup=3}", c.getItems());

		/* startBattlePhase */
		assertEquals(new ArrayList<>(), c.getCharactersOfTheTurn());
		assertFalse(c.inBattle());
		assertEquals(-1, c.getTurn());

		c.startBattlePhase();   // Add players and random enemies to the turn
								// and reset life, but not relevant in first battle.

		/* *************************************************************************
		 * Battle 1.
		 **************************************************************************/

		assertTrue(c.inBattle());
		//Expected list of characters in the turn after battle starts
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba, spiny)), c.getCharactersOfTheTurn());

		assertTrue(c.isPlayersTurn());
		assertEquals(0, c.getTurn());
		assertEquals(marcos, c.getOwner());
		assertEquals(luis, c.getNextOwner());

		/* Attack Turn with Players: Round 1 */
		//The player choose the number of the enemy {1,2,3}
		//Marcos and Luis Flipped their turns now. Then, Marcos is in second position
		assertEquals(new ArrayList<>(Arrays.asList(boo, goomba, spiny)), c.getAttackableEnemies());
		c.attackTurn(AttackType.SALTO, 1); //Enemy index 1 -> boo

		marcos.increaseOrDecreaseFp(-1);//Marcos spent 1 FP;
		assertEquals(marcos, c.getPlayers().get(0));//Marcos is the first player

		boo.setHp(4);//Boo got damage: 11 HP -> 4 HP
		assertEquals(boo, c.getEnemiesInTurn().get(0));//First enemy

		assertTrue(c.isPlayersTurn());
		assertEquals(1, c.getTurn());
		assertEquals(boo, c.getNextOwner());

		//The player choose the number of the enemy {1,2,3}
		//Marcos and Luis Flipped their turns now. Then, Luis is in second position
		assertEquals(new ArrayList<>(Arrays.asList(goomba, spiny)), c.getAttackableEnemies());
		c.getAttackableEnemies().get(0).setSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)
		c.attackTurn( AttackType.MARTILLO, 1); //Enemy index 1 -> Goomba
		luis.increaseOrDecreaseFp(-2);//Luis spent 2 FP;
		assertEquals(luis, c.getPlayers().get(1));//Second player

		goomba.setHp(6);//Goomba got damage: 15 HP -> 6 HP
		assertEquals(goomba, c.getEnemiesInTurn().get(1));//Second enemy

		/* Attack Turn with Enemies: Round 1 */
		//First Enemy
		assertFalse(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(0, c.getTurn()); //Now is the turn of the first enemy
		assertEquals(boo, c.getOwner());
		assertEquals(goomba, c.getNextOwner());
		assertEquals(luis, c.getCharactersOfTheTurn().get(1)); //Second position
		c.attackTurn(); // Luis receives the attack (Boo only attack Luis after all)

		luis.setHp(17);//Luis got damage: 22 HP -> 17 HP
		assertEquals(luis, c.getCharactersOfTheTurn().get(1)); //Second position

		//Second Enemy
		assertFalse(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(1, c.getTurn()); //Now is the turn of the second enemy
		assertEquals(goomba, c.getOwner());
		assertEquals(spiny, c.getNextOwner());

		c.setSeed(987654321);//New seed for not repeat patterns
		c.attackTurn(); // Marcos receives the attack

		marcos.setHp(20); //Marcos got damage: 25 HP -> 20 HP
		assertEquals(marcos, c.getPlayers().get(0)); //First position

		//Third Enemy
		assertFalse(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(2, c.getTurn()); //Now is the turn of the third enemy
		assertEquals(spiny, c.getOwner());
		assertEquals(marcos, c.getNextOwner()); //In next turn, players will play

		c.attackTurn(); // Marcos receives the attack
		marcos.setHp(15);//Marcos got damage: 20 HP -> 15 HP

		assertEquals(marcos, c.getPlayers().get(0)); //First position

		/* Use item with Players: Round 2 */
		assertTrue(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(0, c.getTurn()); //Now is the turn of the first player again
		assertEquals(marcos, c.getOwner());
		assertEquals(luis, c.getNextOwner());
		c.useItemTurn(1, c.createRedMushroomItem()); //Marcos apply item to himself, first index (start by 1)
		assertEquals("{Red Mushroom=2, Honey Syrup=3}", c.getItems());
		marcos.setHp(18);
		assertEquals(marcos, c.getPlayers().get(0)); //First player

		/* Pass turn with Players */
		assertTrue(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(1, c.getTurn()); //Now is the turn of the second player
		assertEquals(luis, c.getOwner());
		assertEquals(boo, c.getNextOwner());

		c.passTurn();//You can't pass more than 5 times in a battle
		assertEquals(1, c.getPassTurnTimes());

		/* Attack Turn with Enemies: Round 2 */
		assertFalse(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(0, c.getTurn()); //Now is the turn of the first enemy
		assertEquals(boo, c.getOwner());
		assertEquals(goomba, c.getNextOwner());

		c.attackTurn(); // Luis receives the attack (Boo only attack Luis after all)

		luis.setHp(12);//Luis got damage: 17 HP -> 12 HP
		assertEquals(luis, c.getPlayers().get(1)); //Second position

		assertFalse(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(1, c.getTurn()); //Now is the turn of the second enemy
		assertEquals(goomba, c.getOwner());
		assertEquals(spiny, c.getNextOwner());

		c.attackTurn(); // Marcos receives the attack

		marcos.setHp(13);
		assertEquals(marcos, c.getPlayers().get(0)); //First position

		assertFalse(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(2, c.getTurn()); // Now is the turn of the third enemy
		assertEquals(spiny, c.getOwner());
		assertEquals(marcos, c.getNextOwner());

		c.setSeed(55555); // Luis receives the attack
		c.attackTurn();

		luis.setHp(7);//Luis got damage: 12 HP -> 7 HP
		assertEquals(luis, c.getPlayers().get(1)); //Second position

		/* Attack Turn with Players: Round 3 */

		/* Marcos: 13 HP, Luis: 7 HP, Boo: 4 HP, Goomba: 6 HP, Spiny: 5 HP */
		assertTrue(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(0, c.getTurn()); // Now is the turn of the first player
		assertEquals(marcos, c.getOwner());
		assertEquals(luis, c.getNextOwner());

		assertEquals(new ArrayList<>(Arrays.asList(boo, goomba, spiny)), c.getAttackableEnemies());

		c.getAttackableEnemies().get(2).setSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)
		c.attackTurn( AttackType.MARTILLO, 3); //Enemy index 3 -> spiny 5 HP -> 0 HP
		marcos.increaseOrDecreaseFp(-2); //Marcos spent 2 FP;
		assertEquals(marcos, c.getPlayers().get(0)); //First player

		assertEquals(new ArrayList<>(Arrays.asList(boo, goomba)), c.getEnemiesInTurn());//Third enemy was killed

		assertTrue(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(1, c.getTurn()); // Now is the turn of the second player
		assertEquals(luis, c.getOwner());
		assertEquals(boo, c.getNextOwner());

		assertEquals(new ArrayList<>(List.of(goomba)), c.getAttackableEnemies());

		c.attackTurn( AttackType.SALTO, 1); //Enemy index 1 -> goomba 6 HP -> 0 HP
		luis.increaseOrDecreaseFp(-1); //Luis spent 1 FP;
		assertEquals(luis, c.getPlayers().get(1)); //Second player

		assertEquals(new ArrayList<>(List.of(boo)), c.getEnemiesInTurn());//Second enemy was killed

		/* Last Round for Enemies before end first battle */
		assertFalse(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(0, c.getTurn()); // Now is the turn of the first enemy
		assertEquals(boo, c.getOwner());
		assertEquals(marcos, c.getNextOwner());
		assertEquals(new ArrayList<>(List.of(luis)), c.getAttackablePlayers());

		c.attackTurn();

		luis.setHp(2);
		assertEquals(luis, c.getPlayers().get(1));//Second player

		/* Last Round for Players before end first battle */
		assertTrue(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(0, c.getTurn()); // Now is the turn of the first player
		assertEquals(marcos, c.getOwner());
		assertEquals(luis, c.getNextOwner());
		assertEquals(new ArrayList<>(List.of(boo)), c.getAttackableEnemies());

		assertEquals(0, c.checkWins());//Before win the first battle
		assertEquals("IN PROGRESS", c.inProgressWinOrLose());
		assertEquals("{Red Mushroom=2, Honey Syrup=3}", c.getItems());
		c.setSeed(989);//Before winning battle change seed to get other 3 enemies (Boo, Goomba, Boo)
		c.attackTurn( AttackType.SALTO, 1); //Enemy index 1 -> goomba 6 HP -> 0 HP

		marcos.increaseOrDecreaseFp(-1);//Marcos spent 1 FP;

		/* *************************************************************************
		 * Battle 2.
		 **************************************************************************/

		//Internally the battle ended and a new battle have started
		assertEquals(1, c.checkWins());//After win the first battle
		assertEquals(0, c.getPassTurnTimes()); //The count starts again

		assertEquals("IN PROGRESS", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=3, Honey Syrup=4}", c.getItems());

		marcos.levelUp();
		luis.levelUp();

		marcos.restoreDynamicStats();
		luis.restoreDynamicStats();

		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis)), c.getPlayers());
		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis)), c.getPlayersInTurn());
		assertEquals(new ArrayList<>(Arrays.asList(boo1,goomba1,boo2)), c.getEnemiesInTurn());
		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis,boo1,goomba1,boo2)),
				c.getCharactersOfTheTurn());

		/* Now we can go faster with 2nd, 3d, 4th and 5th battles */
		c.attackTurn(AttackType.SALTO, 1); // Boo1: 11 HP -> 0 HP (Marcos now is stronger)
		marcos.increaseOrDecreaseFp(-1);

		c.attackTurn(AttackType.MARTILLO, 2);//BAD MOVEMENT: Only Goomba in index 1
		assertEquals(0, c.getPassTurnTimes()); //Not count like pass it is a mistake
		// ... In the game this may not happen

		assertEquals(luis, c.getOwner()); // He is still the owner
		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis,goomba1,boo2)),
				c.getCharactersOfTheTurn()); //Goomba doesn't change

		c.getEnemiesInTurn().get(0).setSeed(4444); // MARTILLO misses ( with 0.25 probability )
		c.attackTurn(AttackType.MARTILLO, 1); //CORRECT MOVEMENT: Goomba 15 HP -> 0 HP (not balanced)
		luis.increaseOrDecreaseFp(-2); //Luis spent 2 FP
		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis,goomba1,boo2)), c.getCharactersOfTheTurn());

		assertEquals(goomba1, c.getOwner()); // Goomba1 is the owner
		assertEquals(boo2, c.getNextOwner());

		c.attackTurn(); // Luis: 25 HP -> 21 HP
		luis.setHp(21);

		assertEquals(boo2, c.getOwner()); // Boo2 is the owner
		assertEquals(marcos, c.getNextOwner());

		c.attackTurn(); // Luis: 21 HP -> 16 HP
		luis.setHp(16);

		c.attackTurn(AttackType.SALTO, 1); // Boo1: 11 HP -> 0 HP (Marcos now is stronger)
		marcos.increaseOrDecreaseFp(-1);

		assertEquals(new ArrayList<>(List.of()),c.getAttackableEnemies()); // Luis can't attack last Boo2 enemy

		c.useItemTurn(1, c.createHoneySyrupItem()); //Luis gives mario (First position for client) more FP
		assertEquals("{Red Mushroom=3, Honey Syrup=3}", c.getItems());
		marcos.increaseOrDecreaseFp(3);
		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis, boo2)),c.getCharactersOfTheTurn());

		assertEquals(boo2, c.getOwner()); // Boo2 is the owner
		assertEquals(marcos, c.getNextOwner());

		c.attackTurn(); // Luis: 16 HP -> 11 HP
		luis.setHp(11);

		assertEquals(marcos, c.getOwner());
		c.getAttackableEnemies().get(0).setSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)
		c.attackTurn(AttackType.MARTILLO, 1);
		marcos.increaseOrDecreaseFp(-2); // Marcos lost his points
		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis, boo2)),c.getCharactersOfTheTurn());
		//Boo2 not take damage from MARTILLO or the MARTILLO itself is failed. In this case was the first

		c.passTurn();//Luis pass his turn
		assertEquals(1, c.getPassTurnTimes());

		c.attackTurn();// Luis: 11 HP -> 6 HP
		luis.setHp(6);

		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis, boo2)),c.getCharactersOfTheTurn());
		assertEquals(marcos, c.getOwner());
		assertEquals("IN PROGRESS", c.inProgressWinOrLose());//The game is in progress
		assertEquals(1, c.checkWins());//After win the second battle
		c.setSeed(123123123);// For other 5 random enemies with no correlation with the others
		c.attackTurn(AttackType.SALTO, 1);

		/* *************************************************************************
		 * Battle 3.
		 **************************************************************************/

		assertEquals(2, c.checkWins());//After win the second battle
		assertEquals(0, c.getPassTurnTimes()); //The count starts again
		assertEquals("IN PROGRESS", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=4, Honey Syrup=4}", c.getItems());

		marcos.levelUp();
		luis.levelUp();

		marcos.restoreDynamicStats();
		luis.restoreDynamicStats();

		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo3, goomba2, spiny1, spiny2, boo4)),
				c.getCharactersOfTheTurn());

		/* Better way to go to last fight faster (until now almost all logic was tested) */

		c.endOfBattlePhase();// With condition playersInTurn.size() > 0 -> Players win this battle

		/* *************************************************************************
		 * Battle 4.
		 **************************************************************************/

		assertEquals(3, c.checkWins());//After win the third battle
		assertEquals(0, c.getPassTurnTimes()); //The count starts again
		assertEquals("IN PROGRESS", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=5, Honey Syrup=5}", c.getItems());

		marcos.levelUp();
		luis.levelUp();

		marcos.restoreDynamicStats();
		luis.restoreDynamicStats();

		//Now we only check the minimum requirements
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());
		assertEquals(5, c.getEnemiesInTurn().size()); //Only check size for not making more enemies

		c.endOfBattlePhase();// With condition playersInTurn.size() > 0 -> Players win this battle

		/* *************************************************************************
		 * Battle 5.
		 **************************************************************************/

		assertEquals(4, c.checkWins());//After win the fourth battle
		assertEquals(0, c.getPassTurnTimes()); //The count starts again
		assertEquals("IN PROGRESS", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=6, Honey Syrup=6}", c.getItems());

		marcos.levelUp();
		luis.levelUp();

		marcos.restoreDynamicStats();
		luis.restoreDynamicStats();

		/* The last battle will be carried out to see the evolution at the time of finishing */

		//Now we only check the minimum requirements
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo5, goomba3, spiny3, spiny4, boo6, goomba4)),
				c.getCharactersOfTheTurn());

		/* Players */

		c.getAttackableEnemies().get(3).setSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)
		c.attackTurn(AttackType.MARTILLO, 4);//Spiny4 died
		marcos.increaseOrDecreaseFp(-2);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo5, goomba3, spiny3, boo6, goomba4)),
				c.getCharactersOfTheTurn());

		c.attackTurn(AttackType.SALTO, 2);//Luis got damage from SALTO on spiny3
		luis.increaseOrDecreaseFp(-1);
		luis.setHp(34); //Luis 36 HP -> 34 HP
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo5, goomba3, spiny3, boo6, goomba4)),
				c.getCharactersOfTheTurn());

		/* Enemies */

		assertEquals(boo5, c.getOwner());
		c.attackTurn();
		luis.setHp(30);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		assertEquals(goomba3, c.getOwner());
		c.attackTurn();
		marcos.setHp(38);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		assertEquals(spiny3, c.getOwner());
		c.attackTurn();
		marcos.setHp(34);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		assertEquals(boo6, c.getOwner());
		c.attackTurn();
		luis.setHp(26);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		assertEquals(goomba4, c.getOwner());
		c.attackTurn();
		marcos.setHp(31);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		/* Players */

		c.attackTurn(AttackType.SALTO, 1);//Boo5 died
		marcos.increaseOrDecreaseFp(-1);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, goomba3, spiny3, boo6, goomba4)),
				c.getCharactersOfTheTurn());

		c.getAttackableEnemies().get(2).setSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)
		c.attackTurn(AttackType.MARTILLO, 3);//Goomba4 died
		luis.increaseOrDecreaseFp(-2);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, goomba3, spiny3, boo6)),
				c.getCharactersOfTheTurn());

		/* Enemies (Trying to kill luis) */

		assertEquals(goomba3, c.getOwner());
		c.setSeed(55555); // Luis receives the attack
		c.attackTurn();
		luis.setHp(23);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		assertEquals(spiny3, c.getOwner());
		c.attackTurn();
		luis.setHp(20);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		assertEquals(boo6, c.getOwner());
		c.attackTurn();
		luis.setHp(16);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		/* Players */

		c.passTurn();
		c.passTurn();

		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, goomba3, spiny3, boo6)),
				c.getCharactersOfTheTurn());


		/* Enemies (Trying to kill luis) */

		assertEquals(goomba3, c.getOwner());
		c.setSeed(55555); // Luis receives the attack
		c.attackTurn();
		luis.setHp(13);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		assertEquals(spiny3, c.getOwner());
		c.attackTurn();
		luis.setHp(10);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		assertEquals(boo6, c.getOwner());
		c.attackTurn();
		luis.setHp(6);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		/* Players */

		c.passTurn();
		c.passTurn();
		assertEquals(4, c.getPassTurnTimes());

		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, goomba3, spiny3, boo6)),
				c.getCharactersOfTheTurn());

		/* Enemies (Trying to kill luis) */

		assertEquals(goomba3, c.getOwner());
		c.setSeed(55555); // Luis receives the attack
		c.attackTurn();
		luis.setHp(3);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		assertEquals(spiny3, c.getOwner());
		c.attackTurn(); //Luis was killed
		assertEquals(new ArrayList<>(List.of(marcos)), c.getPlayersInTurn());

		assertEquals(new ArrayList<>(List.of()), c.getAttackablePlayers());
		assertEquals(boo6, c.getOwner());//Boo6 doesn't have players to attack
		c.attackTurn(); //When he tries to attack, only finish his turn (not pass)
		assertEquals(new ArrayList<>(List.of(marcos)), c.getPlayersInTurn()); //Marcos doesn't change

		/* Players */

		assertEquals(marcos, c.getOwner());
		c.attackTurn(AttackType.SALTO, 1);//goomba3 died
		marcos.increaseOrDecreaseFp(-1);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, spiny3, boo6)), c.getCharactersOfTheTurn());

		/* Enemies */

		assertEquals(spiny3, c.getOwner());
		c.attackTurn(); //Luis was killed
		marcos.setHp(27);
		assertEquals(new ArrayList<>(List.of(marcos)), c.getPlayersInTurn());

		assertEquals(new ArrayList<>(List.of()), c.getAttackablePlayers());
		assertEquals(boo6, c.getOwner());//Boo6 doesn't have players to attack
		c.attackTurn(); //When he tries to attack, only finish his turn (not pass)
		assertEquals(new ArrayList<>(List.of(marcos)), c.getPlayersInTurn()); //Marcos doesn't change

		/* Players */

		assertEquals(marcos, c.getOwner());
		c.attackTurn(AttackType.MARTILLO, 1);//Marcos doesn't have enough FP (attacking Spiny3)
		assertEquals(marcos, c.getOwner()); //He is still the owner
		assertEquals(1, marcos.getFp());
		c.attackTurn(AttackType.SALTO, 2);//Marcos has just enough FP to (attacking Boo6)
		marcos.increaseOrDecreaseFp(-1);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, spiny3)), c.getCharactersOfTheTurn());

		/* Enemies */

		assertEquals(spiny3, c.getOwner());
		c.attackTurn(); //Attack Marcos
		marcos.setHp(23);
		assertEquals(new ArrayList<>(List.of(marcos)), c.getPlayersInTurn());

		/* Players */

		assertEquals(marcos, c.getOwner());
		c.useItemTurn(1, c.createHoneySyrupItem());
		marcos.increaseOrDecreaseFp(3);

		/* Enemies */

		assertEquals(spiny3, c.getOwner());
		c.attackTurn(); //Attack Marcos
		marcos.setHp(19);
		assertEquals(new ArrayList<>(List.of(marcos)), c.getPlayersInTurn());

		/* Players */

		c.getAttackableEnemies().get(0).setSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)
		assertEquals(marcos, c.getOwner());
		c.attackTurn(AttackType.MARTILLO, 1);
		marcos.increaseOrDecreaseFp(-2);
		assertEquals(new ArrayList<>(List.of(marcos)), c.getCharactersOfTheTurn());

		/* *************************************************************************
		 * End of Battles.
		 **************************************************************************/

		assertFalse(c.inBattle());
		assertEquals(5, c.checkWins());
		assertEquals("WIN", c.inProgressWinOrLose());
	}

	@Test
	public void phasesAndLoseTest(){ // Shortest
		c.initialPhase();//Add players of the game and first items
		c.setSeed(10101);
		c.startBattlePhase();  //Add players and random enemies to the turn

		/* *************************************************************************
		 * Battle 1.
		 **************************************************************************/

		/* Players */

		c.attackTurn(AttackType.SALTO, 1);//Boo is attacked
		marcos.increaseOrDecreaseFp(-1);
		boo.setHp(4); //Boo: 11 HP -> 4 HP
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba, goomba2)), c.getCharactersOfTheTurn());

		c.getAttackableEnemies().get(1).setSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)
		c.attackTurn(AttackType.MARTILLO, 2);//Goomba2 is attacked
		luis.increaseOrDecreaseFp(-2);
		goomba2.setHp(6); //Goomba2 15 HP -> 6 HP
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba, goomba2)), c.getCharactersOfTheTurn());

		/* Enemies (trying to kill marcos)*/

		assertEquals(boo, c.getOwner());
		c.attackTurn(); // Luis receives the attack
		luis.setHp(17); //Luis: 22 HP -> 17 HP
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		c.setSeed(987654321); //For Goomba attacking marcos
		assertEquals(goomba, c.getOwner());
		c.attackTurn();
		marcos.setHp(20);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		assertEquals(goomba2, c.getOwner());
		c.attackTurn();
		marcos.setHp(15);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		/* Players */

		c.getAttackableEnemies().get(0).setSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)
		c.attackTurn(AttackType.MARTILLO, 1);//Boo is attacked but doges
		marcos.increaseOrDecreaseFp(-2);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba, goomba2)), c.getCharactersOfTheTurn());

		c.getAttackableEnemies().get(0).setSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)
		c.attackTurn(AttackType.MARTILLO, 1);//Goomba2 is attacked
		luis.increaseOrDecreaseFp(-2);
		goomba.setHp(6); //Goomba2 15 HP -> 6 HP
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba, goomba2)), c.getCharactersOfTheTurn());

		/* Enemies (trying to kill marcos) */

		assertEquals(boo, c.getOwner());
		c.attackTurn(); // Luis receives the attack
		luis.setHp(12); //Luis: 17 HP -> 12 HP
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		c.setSeed(987654321); //For Goomba attacking marcos
		assertEquals(goomba, c.getOwner());
		c.attackTurn();
		marcos.setHp(10); //Marcos: 15 HP -> 10 HP
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		assertEquals(goomba2, c.getOwner());
		c.attackTurn();
		marcos.setHp(5);//Marcos: 10 HP -> 5 HP
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		/* Players */

		c.attackTurn(AttackType.SALTO, 2);//Goomba is attacked
		marcos.increaseOrDecreaseFp(-1);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba2)), c.getCharactersOfTheTurn());

		c.useItemTurn(2, c.createHoneySyrupItem()); // Luis use item in himself (not the best movement now)
		luis.increaseOrDecreaseFp(3);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		/* Enemies (trying to kill marcos) */

		assertEquals(boo, c.getOwner());
		c.attackTurn(); // Luis receives the attack
		luis.setHp(7); //Luis: 12 HP -> 7 HP
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayersInTurn());

		assertEquals(goomba2, c.getOwner());
		c.attackTurn();
		assertEquals(new ArrayList<>(List.of(luis)), c.getPlayersInTurn());

		/* Players */

		c.attackTurn(AttackType.SALTO, 1);//Goomba2 die
		luis.increaseOrDecreaseFp(-1);
		assertEquals(new ArrayList<>(Arrays.asList(luis, boo)), c.getCharactersOfTheTurn());

		/* Enemies */

		assertEquals(boo, c.getOwner());
		c.attackTurn(); // Luis receives the attack
		luis.setHp(2); //Luis: 7 HP -> 2 HP
		assertEquals(new ArrayList<>(List.of(luis)), c.getPlayersInTurn());

		/* Players */

		c.attackTurn(AttackType.SALTO, 1);//Not valid index, the turn still (not discount FP)
		assertEquals(new ArrayList<>(Arrays.asList(luis, boo)), c.getCharactersOfTheTurn());
		c.passTurn();

		/* *************************************************************************
		 * Game over.
		 **************************************************************************/

		assertEquals(0, c.checkWins());//Before lose the battle
		assertEquals(1, c.getPassTurnTimes()); //The count starts again
		assertEquals("IN PROGRESS", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=3, Honey Syrup=2}", c.getItems());

		c.attackTurn();

		assertEquals(0, c.checkWins());//After lose the battle
		assertEquals(1, c.getPassTurnTimes()); //Not reset because the game ends
		assertEquals("LOSE", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=3, Honey Syrup=2}", c.getItems());//Not add new items because the game ends
	}

	@Test
	public void passAndLoseTest(){
		c.initialPhase();//Add players of the game and first items
		c.setSeed(10101);
		c.startBattlePhase();  //Add players and random enemies to the turn

		/* *************************************************************************
		 * Battle 1.
		 **************************************************************************/

		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba, goomba2)), c.getCharactersOfTheTurn());

		c.passTurn();
		c.passTurn();

		c.attackTurn();
		c.attackTurn();
		c.attackTurn();

		c.passTurn();
		c.passTurn();

		c.attackTurn();
		c.attackTurn();
		c.attackTurn();

		c.passTurn();//Marcos is the only one alive

		c.attackTurn();
		c.attackTurn();
		c.attackTurn();

		marcos.setHp(10);

		/* *************************************************************************
		 * Game over.
		 **************************************************************************/

		assertEquals(0, c.checkWins());//Before lose the battle
		assertEquals(5, c.getPassTurnTimes()); // 5 passed times
		assertEquals("IN PROGRESS", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=3, Honey Syrup=3}", c.getItems());
		assertEquals(marcos, c.getOwner());

		c.passTurn();

		assertEquals(0, c.checkWins());//After lose the battle
		assertEquals(6, c.getPassTurnTimes()); //6 passed times -> players lose
		assertEquals("LOSE", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=3, Honey Syrup=3}", c.getItems());//Not add new items because the game ends
	}
}