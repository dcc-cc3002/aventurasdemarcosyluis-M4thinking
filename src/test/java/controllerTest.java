import com.example.aventurasdemarcoyluis.controller.GameController;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.enemies.*;
import com.example.aventurasdemarcoyluis.model.characters.enemies.enemiesfactory.BooFactory;
import com.example.aventurasdemarcoyluis.model.characters.enemies.enemiesfactory.GoombaFactory;
import com.example.aventurasdemarcoyluis.model.characters.enemies.enemiesfactory.SpinyFactory;
import com.example.aventurasdemarcoyluis.model.characters.players.Luis;
import com.example.aventurasdemarcoyluis.model.characters.players.Marcos;
import com.example.aventurasdemarcoyluis.view.GameGUI;
import com.example.aventurasdemarcoyluis.view.viewhandlers.EndGamePhaseHandler;
import com.example.aventurasdemarcoyluis.view.viewhandlers.NewBattlePhaseHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class controllerTest {
	private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
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

	private Boo boo5b4;
	private Goomba goomba3b4;
	private Spiny spiny3b4;
	private Spiny spiny4b4;
	private Boo boo6b4;

	private Boo boo5;
	private Goomba goomba3;
	private Spiny spiny3;
	private Spiny spiny4;
	private Boo boo6;
	private Goomba goomba4;

	private GameGUI view;

	private final NewBattlePhaseHandler newBattlePhaseHandler = new NewBattlePhaseHandler(view);
	private final EndGamePhaseHandler endGamePhaseHandler = new EndGamePhaseHandler(view);

	@BeforeEach
	public void setUp() {
		c = new GameController();
		c.setSeed(123456789); //Special seed with 3 different enemies at the beginning
		c.emptyChest();

		c.setErrorStream(new PrintStream(outputStreamCaptor));

		BooFactory booFactory = new BooFactory(9, 5, 11, 8);
		SpinyFactory spinyFactory = new SpinyFactory(8, 4, 5, 8);
		GoombaFactory goombaFactory = new GoombaFactory(7, 4,15, 8);

		//Players
		marcos = new Marcos(33, 9, 25, 5, 1);
		luis = new Luis(25, 10, 22, 6, 1);

		//First battle
		boo = booFactory.create();
		goomba = goombaFactory.create();
		spiny = spinyFactory.create();

		//Second battle
		boo1 = booFactory.create();
		goomba1 = goombaFactory.create();
		boo2 = booFactory.create();

		//Third battle
		boo3 = booFactory.create();
		goomba2 = goombaFactory.create();
		spiny1 = spinyFactory.create();
		spiny2 = spinyFactory.create();
		boo4 = booFactory.create();

		//Forth battle (named different)
		boo5b4 = booFactory.create();
		goomba3b4 = goombaFactory.create();
		spiny3b4 = spinyFactory.create();
		spiny4b4 = spinyFactory.create();
		boo6b4 = booFactory.create();

		//Fifth battle
		boo5 = booFactory.create();
		goomba3 = goombaFactory.create();
		spiny3 = spinyFactory.create();
		spiny4 = spinyFactory.create();
		boo6 = booFactory.create();
		goomba4 = goombaFactory.create();
	}

	@RepeatedTest(10)
	public void phasesAndWinTest()  {
		//Turns are index from 0 (Seen by programmer)
		//To choose a player or an enemy to be attack with index, starts from 1 (Seen by client)
		//Seeing c.getOwner() of the turn is the only way to know whose turn it is (Seen by client)

		/* Start Phase */

		/* Phases methods are disabled at beginning */
		c.tryToPass();
		assertEquals( "Start Phase", c.getPhase().toString());


		assertEquals( "Start Phase", c.getPhase().toString());
		assertEquals(new ArrayList<>(), c.getPlayers());
		assertEquals(new ArrayList<>(), c.getCharactersOfTheTurn());
		assertEquals("{Red Mushroom=0, Honey Syrup=0}", c.getItems());
		assertEquals(-1, c.getTurn());

		c.tryToStart(); //Start Phase -> Battle Phase -> Wait Phase
		c.tryToStart(); //Nothing happens, this is considered as an error.

		/* Wait Phase */
		assertEquals( "Is your turn", c.getPhase().toString());
		assertEquals( new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayers());
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba, spiny)), c.getCharactersOfTheTurn());
		assertEquals("{Red Mushroom=3, Honey Syrup=3}", c.getItems());
		assertEquals(0, c.getTurn());

		/* *************************************************************************
		 * Battle 1.
		 **************************************************************************/

		assertTrue(c.inBattle());
		assertTrue(c.isPlayersTurn());
		assertEquals(0, c.getTurn());
		assertEquals(marcos, c.getOwner());
		assertEquals(luis, c.getNextOwner());

		/* testing some bad decisions */

		assertEquals(new ArrayList<>(Arrays.asList(boo, goomba, spiny)), c.getAttackableEnemies());

		//You can retract from attacking with tryToWaitItemPhase
		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());
		c.tryToWaitItemPhase();//Wait Attack Phase -> Wait Phase
		assertEquals( "Is your turn", c.getPhase().toString());

		//You can't retract from attacking with tryToUseItem
		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());
		c.tryToUseItem(c.createHoneySyrupItem());//It is understood as an error, you stay in the phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		//Attacking is retroactive itself, you can regret trying again
		c.tryToAttackPhase();//Wait Attack Phase -> Wait Phase
		assertEquals( "Is your turn", c.getPhase().toString());

		//You can to retract from attacking with tryToPass
		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());
		c.tryToPass();//Wait Attack Phase -> Wait Phase
		assertEquals( "Is your turn", c.getPhase().toString());

		//You can retract from WaitItemPhase with tryToAttackPhase
		c.tryToWaitItemPhase();//Wait Phase -> Wait Item Phase
		assertEquals( "Choose an Item", c.getPhase().toString());
		c.tryToAttackPhase();//Wait Item Phase -> Wait Phase
		assertEquals( "Is your turn", c.getPhase().toString());

		//You can't retract from WaitItemPhase with tryToSelectPhase
		c.tryToWaitItemPhase();//Wait Phase -> Wait Item Phase
		assertEquals( "Choose an Item", c.getPhase().toString());
		c.tryToSelectAttack(AttackType.SALTO);//It is understood as an error, you stay in the phase
		assertEquals( "Choose an Item", c.getPhase().toString());

		//WaitItemPhase is retroactive itself, you can regret trying again
		c.tryToWaitItemPhase();//Wait Item Phase -> Wait Phase
		assertEquals( "Is your turn", c.getPhase().toString());

		//You can retract from WaitItemPhase with tryToPass
		c.tryToWaitItemPhase();//Wait Phase -> Wait Item Phase
		assertEquals( "Choose an Item", c.getPhase().toString());
		c.tryToPass();//Wait Item Phase -> Wait Phase
		assertEquals( "Is your turn", c.getPhase().toString());

		//You can't go direct to select attack
		c.tryToSelectAttack(AttackType.SALTO);//It is understood as an error, you stay in the phase
		assertEquals( "Is your turn", c.getPhase().toString());

		//You can't go direct to select enemy
		c.tryToFinishAttackTurn(boo);//It is understood as an error, you stay in the phase
		assertEquals( "Is your turn", c.getPhase().toString());

		//You can't go direct to use item
		c.tryToUseItem(c.createHoneySyrupItem());//It is understood as an error, you stay in the phase
		assertEquals( "Is your turn", c.getPhase().toString());

		//You can't go direct to select ally
		c.tryToFinishItemTurn(0);//It is understood as an error, you stay in the phase
		assertEquals( "Is your turn", c.getPhase().toString());


		/* Attack Turn with Players: Round 1 */

		assertEquals(new ArrayList<>(Arrays.asList(boo, goomba, spiny)), c.getAttackableEnemies());

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO);//Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(boo);////Boo: 11 HP -> 4 HP
		assertEquals( "Is your turn", c.getPhase().toString());

		marcos.increaseOrDecreaseFp(-1);//Marcos spent 1 FP;
		assertEquals(marcos, c.getPlayers().get(0));//Marcos is the first player

		boo.setHp(4);
		assertEquals(boo, c.getEnemiesInTurn().get(0));//First enemy

		assertTrue(c.isPlayersTurn());
		assertEquals(1, c.getTurn());
		assertEquals(boo, c.getNextOwner());

		//Marcos and Luis Flipped their turns now. Then, Luis is in second position
		assertEquals(new ArrayList<>(Arrays.asList(goomba, spiny)), c.getAttackableEnemies());
		c.getAttackableEnemies().get(0).setFailSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)
		c.tryToAttackPhase();
		c.tryToSelectAttack(AttackType.MARTILLO);

		//New seed for enemies attack before finish turn (Boo always attack luis)
		c.getEnemiesInTurn().get(1).setSeed(3545);
		c.getEnemiesInTurn().get(2).setSeed(23434);

		c.tryToFinishAttackTurn(goomba);
		luis.increaseOrDecreaseFp(-2);//Luis spent 2 FP;

		goomba.setHp(6);//Goomba got damage: 15 HP -> 6 HP
		assertEquals(goomba, c.getEnemiesInTurn().get(1));//Second enemy

		/* Enemies attack automatically: Round 1 */

		luis.setHp(17);//Luis got damage: 22 HP -> 17 HP
		marcos.setHp(15);//Marcos got damage: 20 HP -> 15 HP
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayers());

		/* Use item with Players: Round 2 */
		assertTrue(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(0, c.getTurn()); //Now is the turn of the first player again
		assertEquals(marcos, c.getOwner());
		assertEquals(luis, c.getNextOwner());

		c.tryToWaitItemPhase();
		c.tryToUseItem(c.createRedMushroomItem());
		assertEquals( "Choose a non-KO ally", c.getPhase().toString());
		c.tryToFinishItemTurn(0); //Marcos apply item to himself, index starts from 0

		assertEquals("{Red Mushroom=2, Honey Syrup=3}", c.getItems());
		marcos.setHp(18);
		assertEquals(marcos, c.getPlayers().get(0)); //First player

		/* Pass turn with Players */
		assertTrue(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(1, c.getTurn()); //Now is the turn of the second player
		assertEquals(luis, c.getOwner());
		assertEquals(boo, c.getNextOwner());
		c.getEnemiesInTurn().get(2).setSeed(22222);
		c.tryToPass();

		/* Enemies attack automatically: Round 2 */

		luis.setHp(7);
		marcos.setHp(13);

		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayers());

		/* Attack Turn with Players: Round 3 */

		/* Marcos: 13 HP, Luis: 7 HP, Boo: 4 HP, Goomba: 6 HP, Spiny: 5 HP */
		assertTrue(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(0, c.getTurn()); // Now is the turn of the first player
		assertEquals(marcos, c.getOwner());
		assertEquals(luis, c.getNextOwner());

		assertEquals(new ArrayList<>(Arrays.asList(boo, goomba, spiny)), c.getAttackableEnemies());

		c.getAttackableEnemies().get(2).setFailSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.MARTILLO);//Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(spiny);//Spiny 5 HP -> 0 HP
		assertEquals( "Is your turn", c.getPhase().toString());

		marcos.increaseOrDecreaseFp(-2); //Marcos spent 2 FP;
		assertEquals(marcos, c.getPlayers().get(0)); //First player

		assertEquals(new ArrayList<>(Arrays.asList(boo, goomba)), c.getEnemiesInTurn());//Third enemy was killed

		assertTrue(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(1, c.getTurn()); // Now is the turn of the second player
		assertEquals(luis, c.getOwner());
		assertEquals(boo, c.getNextOwner());

		assertEquals(new ArrayList<>(List.of(goomba)), c.getAttackableEnemies());

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(goomba);//Goomba 6 HP -> 0 HP
		assertEquals( "Is your turn", c.getPhase().toString());

		luis.increaseOrDecreaseFp(-1); //Luis spent 1 FP;


		luis.setHp(2);
		assertEquals(luis, c.getPlayers().get(1)); //Second player

		assertEquals(new ArrayList<>(List.of(boo)), c.getEnemiesInTurn());//Second enemy was killed

		/* Last Round for Enemies before end first battle */

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

		marcos.increaseOrDecreaseFp(-1);//Marcos spent 1 FP;

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(boo);//Boo: 4 HP -> 0 HP
		assertEquals( "Is your turn", c.getPhase().toString());


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

		//Marcos

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(boo1);//Boo1: 11 HP -> 0 HP (Marcos now is stronger)
		assertEquals( "Is your turn", c.getPhase().toString());

		marcos.increaseOrDecreaseFp(-1);

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		//Luis bad movement

		c.tryToSelectAttack(AttackType.MARTILLO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(boo1);//BAD MOVEMENT: Only Goomba. Back to Wait Phase. The error was caught
		assertEquals( "Is your turn", c.getPhase().toString());

		assertEquals(0, c.getPassTurnTimes()); //Not count like pass it is a mistake

		assertEquals(luis, c.getOwner()); // He is still the owner
		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis,goomba1,boo2)),
				c.getCharactersOfTheTurn()); //Goomba doesn't change

		//Luis correct movement

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.MARTILLO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.getEnemiesInTurn().get(0).setFailSeed(4444); // MARTILLO misses ( with 0.25 probability )

		c.getEnemiesInTurn().get(0).setSeed(354533);
		c.getEnemiesInTurn().get(1).setSeed(989);

		c.tryToFinishAttackTurn(goomba1);//CORRECT MOVEMENT: Goomba 15 HP -> 15 HP
		assertEquals( "Is your turn", c.getPhase().toString());

		luis.increaseOrDecreaseFp(-2); //Luis spent 2 FP

		/* Enemies turn */

		luis.setHp(16); // Luis: 25 HP -> 21 HP -> 16 HP

		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis,goomba1,boo2)), c.getCharactersOfTheTurn());

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(goomba1);//Goomba1: 11 HP -> 0 HP (Marcos now is stronger)
		assertEquals( "Is your turn", c.getPhase().toString());

		marcos.increaseOrDecreaseFp(-1);

		assertEquals(4, c.getItemQuantity(c.createHoneySyrupItem()));

		assertEquals(luis, c.getOwner());
		assertEquals(new ArrayList<>(List.of()),c.getAttackableEnemies()); // Luis can't attack last Boo2 enemy

		c.tryToWaitItemPhase();
		c.tryToUseItem(c.createHoneySyrupItem());

		c.tryToFinishItemTurn(0);//Luis gives mario (First position for client) more FP

		luis.setHp(11);// Luis: 16 HP -> 11 HP

		assertEquals("{Red Mushroom=3, Honey Syrup=3}", c.getItems());
		marcos.increaseOrDecreaseFp(3);
		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis, boo2)),c.getCharactersOfTheTurn());

		assertEquals(marcos, c.getOwner());
		c.getAttackableEnemies().get(0).setSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.MARTILLO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(boo2);//Boo2: 11 HP -> 11 HP
		//Boo2 not take damage from MARTILLO or the MARTILLO itself is failed. In this case was the first
		assertEquals( "Is your turn", c.getPhase().toString());

		marcos.increaseOrDecreaseFp(-2); // Marcos lost his points

		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis, boo2)),c.getCharactersOfTheTurn());

		c.passTurn();//Luis pass his turn
		assertEquals(1, c.getPassTurnTimes());

		/* Enemies attack */

		luis.setHp(6);

		assertEquals(new ArrayList<>(Arrays.asList(marcos,luis, boo2)),c.getCharactersOfTheTurn());
		assertEquals(marcos, c.getOwner());
		assertEquals("IN PROGRESS", c.inProgressWinOrLose());//The game is in progress
		assertEquals(1, c.checkWins());//After win the second battle
		c.setSeed(123123123);// For other 5 random enemies with no correlation with the others

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(boo2);//Boo2: 11 HP -> 0 HP
		assertEquals( "Is your turn", c.getPhase().toString());

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

		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo5b4, goomba3b4, spiny3b4, spiny4b4, boo6b4)),
				c.getCharactersOfTheTurn());

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
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo5, goomba3, spiny3, spiny4, boo6, goomba4)),c.getCharactersOfTheTurn());

		/* Players */

		//Marcos
		c.getAttackableEnemies().get(3).setFailSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.MARTILLO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(spiny4);//Spiny4 died
		assertEquals( "Is your turn", c.getPhase().toString());

		marcos.increaseOrDecreaseFp(-2);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo5, goomba3, spiny3, boo6, goomba4)),
				c.getCharactersOfTheTurn());

		//Luis

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.getEnemiesInTurn().get(0).setSeed(1);//Luis
		c.getEnemiesInTurn().get(1).setSeed(1);//Marcos
		c.getEnemiesInTurn().get(2).setSeed(1);//Marcos
		c.getEnemiesInTurn().get(3).setSeed(1);//Luis
		c.getEnemiesInTurn().get(4).setSeed(1);//Marcos

		c.tryToFinishAttackTurn(spiny3);//Luis got damage from SALTO on spiny3
		assertEquals( "Is your turn", c.getPhase().toString());
		luis.setHp(34); //Luis 36 HP -> 34 HP
		luis.increaseOrDecreaseFp(-1);

		/* Enemies */

		luis.setHp(26);// 34 HP -> 30 HP x boo5 -> 26 HP x boo6
		marcos.setHp(31);// 41 HP -> 38 HP x goomba3 -> 34 HP x spiny3 -> 31 HP x goomba4

		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo5, goomba3, spiny3, boo6, goomba4)),
				c.getCharactersOfTheTurn());

		/* Players */
		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(boo5);//Boo5 died
		assertEquals( "Is your turn", c.getPhase().toString());

		marcos.increaseOrDecreaseFp(-1);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, goomba3, spiny3, boo6, goomba4)),
				c.getCharactersOfTheTurn());

		c.getAttackableEnemies().get(2).setFailSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.MARTILLO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.getEnemiesInTurn().get(0).setSeed(212121);//Luis
		c.getEnemiesInTurn().get(1).setSeed(4212);//Luis
		c.getEnemiesInTurn().get(2).setSeed(233);//Luis
		c.tryToFinishAttackTurn(goomba4);//Goomba4 died
		assertEquals( "Is your turn", c.getPhase().toString());

		luis.increaseOrDecreaseFp(-2);

		/* Enemies (Trying to kill luis) */

		luis.setHp(16); //26 HP -> 23 HP x goomba3 -> 20 HP x spiny3 -> 16 HP x boo6

		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, goomba3, spiny3, boo6)),
				c.getCharactersOfTheTurn());

		/* Players */

		c.passTurn();
		c.passTurn();

		/* Enemies (Trying to kill luis) */

		luis.setHp(6); //16 HP -> 13 HP x goomba3 -> 10 HP x spiny3 -> 6 HP x boo6
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, goomba3, spiny3, boo6)),
				c.getCharactersOfTheTurn());

		/* Players */

		c.passTurn();
		c.passTurn();
		assertEquals(4, c.getPassTurnTimes());

		// luis: 6 HP -> 3 HP x goomba3 -> 0 HP x spiny3

		assertEquals(new ArrayList<>(Arrays.asList(marcos, goomba3, spiny3, boo6)), c.getCharactersOfTheTurn());

		/* Players */

		assertEquals(marcos, c.getOwner());

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(goomba3);//Goomba3 died
		assertEquals( "Is your turn", c.getPhase().toString());

		assertEquals(1, c.getPlayerStats(0,"Fp"));
		marcos.increaseOrDecreaseFp(-1);

		/* Enemies */

		assertEquals(27,c.getPlayerStats(0,"Hp"));
		marcos.setHp(27);// 31 HP -> 27 HP x spiny3 (Boo6 doesn't have players to attack)

		assertEquals(new ArrayList<>(Arrays.asList(marcos, spiny3, boo6)), c.getCharactersOfTheTurn());

		/* Players */

		//Marcos invalid movement

		assertEquals(marcos, c.getOwner());
		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.MARTILLO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(spiny3); //Marcos doesn't have enough FP (attacking Spiny3). The error was caught
		assertEquals( "Is your turn", c.getPhase().toString());

		assertEquals(marcos, c.getOwner()); //He is still the owner
		assertEquals(1, marcos.getFp());

		assertEquals(marcos, c.getOwner());
		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(boo6); //Marcos has just enough FP to (attacking Boo6)
		assertEquals( "Is your turn", c.getPhase().toString());

		marcos.increaseOrDecreaseFp(-1);

		/* Enemies */

		marcos.setHp(23);// 27 HP -> 23 HP x spiny3
		assertEquals(new ArrayList<>(List.of(marcos)), c.getPlayersInTurn());
		assertEquals(new ArrayList<>(Arrays.asList(marcos, spiny3)), c.getCharactersOfTheTurn());

		/* Players */

		assertEquals(0, c.getPlayers().get(0).getFp());
		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.MARTILLO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(spiny3); //Marcos not has enough FP to (attacking spiny3)
		assertEquals( "Is your turn", c.getPhase().toString());


		assertEquals(marcos, c.getOwner());
		c.tryToWaitItemPhase();
		c.tryToUseItem(c.createHoneySyrupItem());
		c.tryToFinishItemTurn(0); //Marcos apply item to himself, index starts from 0
		marcos.increaseOrDecreaseFp(3);

		/* Enemies */

		marcos.setHp(19);//23 HP -> 19 HP x spiny3
		assertEquals(new ArrayList<>(List.of(marcos)), c.getPlayersInTurn());

		/* Players */

		c.getAttackableEnemies().get(0).setFailSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)
		assertEquals(marcos, c.getOwner());
		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.MARTILLO); //Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(spiny3);//spiny3 died

		marcos.increaseOrDecreaseFp(-2);

		assertEquals(new ArrayList<>(List.of(marcos)), c.getCharactersOfTheTurn());


		/* *************************************************************************
		 * End of Battles.
		 **************************************************************************/

		assertFalse(c.inBattle());
		assertEquals(5, c.checkWins());
		assertEquals("WIN", c.inProgressWinOrLose());
		assertEquals( "Game Over", c.getPhase().toString());

		//You can't go to principal choose phases (less to others)
		c.tryToAttackPhase();
		assertEquals( "Game Over", c.getPhase().toString());
		c.tryToWaitItemPhase();
		assertEquals( "Game Over", c.getPhase().toString());
		c.tryToPass();
		assertEquals( "Game Over", c.getPhase().toString());
	}

	@RepeatedTest(10)
	public void phasesAndLoseTest() { // Shortest
		c.setSeed(10101);
		assertEquals( "Start Phase", c.getPhase().toString());
		c.tryToStart();//Add players of the game and first items
		assertEquals( "Is your turn", c.getPhase().toString());

		/* *************************************************************************
		 * Battle 1.
		 **************************************************************************/

		/* Players */
		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO);//Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(boo);//Boo es attacked
		assertEquals( "Is your turn", c.getPhase().toString());
		marcos.increaseOrDecreaseFp(-1);
		boo.setHp(4); //Boo: 11 HP -> 4 HP

		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba, goomba1)), c.getCharactersOfTheTurn());

		c.getAttackableEnemies().get(1).setFailSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.MARTILLO);//Wait Attack Phase -> Wait Attackable Phase
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.getEnemiesInTurn().get(1).setSeed(232323); //For Goomba attacking marcos
		c.getEnemiesInTurn().get(2).setSeed(232323); //For Goomba1 attacking marcos
		c.tryToFinishAttackTurn(goomba1);//Goomba1 is attacked
		assertEquals( "Is your turn", c.getPhase().toString());

		luis.increaseOrDecreaseFp(-2);
		goomba1.setHp(6); //Goomba1 15 HP -> 6 HP

		/* Enemies (trying to kill marcos)*/

		luis.setHp(17); //Luis: 22 HP -> 17 HP x boo
		marcos.setHp(15);// 25 HP -> 20 HP x goomba -> 15 HP x goomba1

		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba, goomba1)), c.getCharactersOfTheTurn());

		/* Players */

		c.getAttackableEnemies().get(0).setSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)

		c.tryToAttackPhase();//Wait Phase -> WWait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.MARTILLO);//Wait Attack Phase -> Wait Attackable Phase.
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(boo);//Boo is attacked but doges
		assertEquals( "Is your turn", c.getPhase().toString());

		marcos.increaseOrDecreaseFp(-2);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba, goomba1)), c.getCharactersOfTheTurn());

		c.getAttackableEnemies().get(0).setFailSeed(1212121); // MARTILLO has 0.25 probability to fail (Hit in this case)

		c.tryToAttackPhase();//Wait Phase -> Wait Attackable Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.MARTILLO);//Wait Attack Phase -> Wait Attackable Phase.
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(goomba);//Goomba die
		assertEquals( "Is your turn", c.getPhase().toString());

		goomba.setHp(6); //Goomba 15 HP -> 6 HP

		luis.increaseOrDecreaseFp(-2);

		/* Enemies (trying to kill marcos) */

		luis.setHp(12); //Luis: 17 HP -> 12 HP x boo
		marcos.setHp(5);//Marcos: 15 HP -> 10 HP x goomba -> 5 HP x goomba1

		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo,goomba, goomba1)), c.getCharactersOfTheTurn());

		/* Players */


		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO);//Wait Attack Phase -> Wait Attackable Phase.
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(goomba);//Goomba is attacked
		assertEquals( "Is your turn", c.getPhase().toString());

		marcos.increaseOrDecreaseFp(-1);
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba1)), c.getCharactersOfTheTurn());

		c.tryToWaitItemPhase();
		c.tryToUseItem(c.createHoneySyrupItem());
		c.tryToFinishItemTurn(1); //Luis apply item to himself, (not the best movement now)

		luis.increaseOrDecreaseFp(3);

		/* Enemies (trying to kill marcos) */

		luis.setHp(7); //Luis: 12 HP -> 7 HP x boo
		//Marcos die

		assertEquals(new ArrayList<>(List.of(luis)), c.getPlayersInTurn());

		/* Players */

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO);//Wait Attack Phase -> Wait Attackable Phase.
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(goomba1);//Goomba1 die
		assertEquals( "Is your turn", c.getPhase().toString());

		luis.increaseOrDecreaseFp(-1);

		/* Enemies */

		luis.setHp(2); //Luis: 7 HP -> 2 HP

		assertEquals(new ArrayList<>(Arrays.asList(luis, boo)), c.getCharactersOfTheTurn());

		/* Players */

		c.tryToAttackPhase();//Wait Phase -> Wait Attack Phase
		assertEquals( "Choose Attack", c.getPhase().toString());

		c.tryToSelectAttack(AttackType.SALTO);//Wait Attack Phase -> Wait Attackable Phase.
		assertEquals( "Choose an Enemy", c.getPhase().toString());

		c.tryToFinishAttackTurn(boo);//Luis can't attack boo, the turn still (not discount FP).
		assertEquals( "Is your turn", c.getPhase().toString());

		/* *************************************************************************
		 * Game over.
		 **************************************************************************/

		assertEquals(0, c.checkWins());//Before lose the battle
		assertEquals(0, c.getPassTurnTimes()); //The count starts again
		assertEquals("IN PROGRESS", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=3, Honey Syrup=2}", c.getItems());

		c.tryToPass();

		assertEquals(0, c.checkWins());//After lose the battle
		assertEquals(1, c.getPassTurnTimes());
		assertEquals("LOSE", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=3, Honey Syrup=2}", c.getItems());//Not add new items because the game ends
	}

	@RepeatedTest(10)
	public void passAndLoseTest() {
		c.setSeed(10101);

		c.tryToStart();

		c.getEnemiesInTurn().get(0).setSeed(10101);
		c.getEnemiesInTurn().get(1).setSeed(22222);
		c.getEnemiesInTurn().get(2).setSeed(22222);

		/* *************************************************************************
		 * Battle 1.
		 **************************************************************************/
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba, goomba1)), c.getCharactersOfTheTurn());

		c.tryToPass();//Marcos
		c.tryToPass();//Luis

		c.tryToPass();//Marcos
		c.tryToPass();//Luis

		c.tryToPass();//Marcos is the only one alive

		marcos.setHp(10);

		/* *************************************************************************
		 * Game over.
		 **************************************************************************/

		assertEquals(0, c.checkWins());//Before lose the battle
		assertEquals(5, c.getPassTurnTimes()); // 5 passed times
		assertEquals("IN PROGRESS", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=3, Honey Syrup=3}", c.getItems());
		assertEquals(marcos, c.getOwner());

		c.tryToPass();

		assertEquals(0, c.checkWins());//After lose the battle
		assertEquals(6, c.getPassTurnTimes()); //6 passed times -> players lose
		assertEquals("LOSE", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=3, Honey Syrup=3}", c.getItems());//Not add new items because the game ends
	}

	@Test
	public void insufficientItemTest() {
		c.setSeed(10101);

		c.tryToStart();

		c.getEnemiesInTurn().get(0).setSeed(10101);
		c.getEnemiesInTurn().get(1).setSeed(22222);
		c.getEnemiesInTurn().get(2).setSeed(22222);

		/* *************************************************************************
		 * Battle 1.
		 **************************************************************************/
		assertEquals(new ArrayList<>(Arrays.asList(marcos, luis, boo, goomba, goomba1)), c.getCharactersOfTheTurn());

		c.tryToWaitItemPhase();//Marcos
		c.tryToUseItem(c.createHoneySyrupItem());
		c.tryToFinishItemTurn(0);//To Marcos

		c.tryToWaitItemPhase();//Luis
		c.tryToUseItem(c.createHoneySyrupItem());
		c.tryToFinishItemTurn(0);//To Marcos

		c.tryToWaitItemPhase();//Marcos
		c.tryToUseItem(c.createHoneySyrupItem());
		c.tryToFinishItemTurn(0);//To Marcos

		assertEquals(0, c.getItemQuantity(c.createHoneySyrupItem()));

		c.tryToWaitItemPhase();//Luis
		c.tryToUseItem(c.createHoneySyrupItem()); //This item is not used because it does not remain in the inventory
		c.tryToFinishItemTurn(0);
		assertEquals( "Is your turn", c.getPhase().toString());

		c.tryToWaitItemPhase();//Luis is still in turn
		c.tryToUseItem(c.createRedMushroomItem());
		c.tryToFinishItemTurn(1); // For not killing luis (but its kill whatever)

		c.tryToWaitItemPhase();//Marcos
		c.tryToUseItem(c.createRedMushroomItem());
		c.tryToFinishItemTurn(1); // But luis is K.O

		c.tryToPass();
		c.tryToPass();

		marcos.setHp(5);

		/* *************************************************************************
		 * Game over.
		 **************************************************************************/

		assertEquals(0, c.checkWins());//Before lose the battle
		assertEquals(2, c.getPassTurnTimes()); // 5 passed times
		assertEquals("IN PROGRESS", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=2, Honey Syrup=0}", c.getItems());
		assertEquals(marcos, c.getOwner());

		c.tryToPass();

		assertEquals(0, c.checkWins());//After lose the battle
		assertEquals(3, c.getPassTurnTimes()); //6 passed times -> players lose
		assertEquals("LOSE", c.inProgressWinOrLose());//The game is still in progress
		assertEquals("{Red Mushroom=2, Honey Syrup=0}", c.getItems());//Not add new items because the game ends
	}

	@Test
	public void addHandlers(){
		//Only for testing the handlers adding functions
		c.addNewBattlePhaseNotification(newBattlePhaseHandler);
		c.addEndGamePhaseNotification(endGamePhaseHandler);
	}
}