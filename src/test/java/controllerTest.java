import com.example.aventurasdemarcoyluis.controller.GameController;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.enemies.*;
import com.example.aventurasdemarcoyluis.model.characters.players.IGenericPlayer;
import com.example.aventurasdemarcoyluis.model.characters.players.IScaredPlayer;
import com.example.aventurasdemarcoyluis.model.characters.players.Luis;
import com.example.aventurasdemarcoyluis.model.characters.players.Marcos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class controllerTest {
	private GameController c;

	private Boo boo;
	private Goomba goomba;
	private Spiny spiny;

	private Marcos marcos;
	private Luis luis;

	@BeforeEach
	public void setUp() {
		c = new GameController();
		c.setSeed(123456789); //Special seed with 3 different enemies at the beginning
		c.emptyChest();
		boo = new Boo(5,8,11,8);
		goomba = new Goomba(7, 4,15, 8);
		spiny = new Spiny(8, 4, 5, 8);
		marcos = new Marcos(33, 9, 11, 5, 1);
		luis = new Luis(25, 10, 13, 6, 1);
	}

	@Test
	public void phasesTest(){
		//Turns are index from 0 (Seen by programmer)
		//To choose a player or an enemy to be attack with index, starts from 1 (Seen by client)

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

		c.startBattlePhase();  //Add players and random enemies to the turn
										// and reset life, but not relevant in first battle.
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
		c.attackTurn((IGenericPlayer) c.getOwner(), AttackType.SALTO, 1); //Enemy index 1 -> boo

		marcos.setFp(marcos.getFp()-1);//Marcos spent 1 FP;
		assertEquals(marcos, c.getCharactersOfTheTurn().get(1));//Second position

		boo.setHp(7);//Boo got damage: 11 HP -> 7 HP
		assertEquals(boo, c.getCharactersOfTheTurn().get(2));//Third position

		assertTrue(c.isPlayersTurn());
		assertEquals(1, c.getTurn());
		assertEquals(luis, c.getOwner());
		assertEquals(boo, c.getNextOwner());

		//The player choose the number of the enemy {1,2,3}
		//Marcos and Luis Flipped their turns now. Then, Luis is in second position
		c.attackTurn((IScaredPlayer) c.getOwner(), AttackType.MARTILLO, 2); //Enemy index 2 -> Goomba
		luis.setFp(luis.getFp()-2);//Luis spent 2 FP;
		assertEquals(luis, c.getCharactersOfTheTurn().get(1));//Second position

		goomba.setHp(6);//Goomba got damage: 15 HP -> 6 HP
		assertEquals(goomba, c.getCharactersOfTheTurn().get(3));//Forth position

		/* Attack Turn with Enemies: Round 1 */
		//First Enemy
		assertFalse(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(0, c.getTurn()); //Now is the turn of the first enemy
		assertEquals(boo, c.getOwner());
		assertEquals(goomba, c.getNextOwner());

		c.attackTurn((IEspectralEnemy) c.getOwner()); // Luis receives the attack (Boo only attack Luis after all)

		luis.setHp(10);//Luis got damage: 13 HP -> 10 HP
		assertEquals(luis, c.getCharactersOfTheTurn().get(1)); //Second position

		//Second Enemy
		assertFalse(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(1, c.getTurn()); //Now is the turn of the second enemy
		assertEquals(goomba, c.getOwner());
		assertEquals(spiny, c.getNextOwner());
		c.setSeed(987654321);//New seed for not repeat patterns
		c.attackTurn((IGenericEnemy) c.getOwner()); // Marcos receives the attack
		marcos.setHp(6);
		assertEquals(marcos, c.getCharactersOfTheTurn().get(0)); //Forth position

		//Third Enemy
		assertFalse(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(2, c.getTurn()); //Now is the turn of the third enemy
		assertEquals(spiny, c.getOwner());
		assertEquals(marcos, c.getNextOwner()); //In next turn, players will play
		c.attackTurn((IGenericEnemy) c.getOwner()); // Marcos receives the attack
		marcos.setHp(1);
		assertEquals(marcos, c.getCharactersOfTheTurn().get(0)); //First position

		/* Use item with Players: Round 2 */
		assertTrue(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(0, c.getTurn()); //Now is the turn of the first player again
		assertEquals(marcos, c.getOwner());
		assertEquals(luis, c.getNextOwner());
		c.useItemTurn(1, c.createRedMushroomItem()); //Marcos apply item to himself, first index (start by 1)
		assertEquals("{Red Mushroom=2, Honey Syrup=3}", c.getItems());
		marcos.setHp(2);
		assertEquals(marcos, c.getCharactersOfTheTurn().get(1)); //Second player in turn after turn (start by 0)

		/* Pass turn with Players */
		assertTrue(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(1, c.getTurn()); //Now is the turn of the second player
		assertEquals(luis, c.getOwner());
		assertEquals(boo, c.getNextOwner());

		c.passTurn();

		/* Attack Turn with Enemies: Round 2 */
		assertFalse(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(0, c.getTurn()); //Now is the turn of the first enemy
		assertEquals(boo, c.getOwner());
		assertEquals(goomba, c.getNextOwner());

		c.attackTurn((IEspectralEnemy) c.getOwner()); // Luis receives the attack (Boo only attack Luis after all)

		luis.setHp(7);//Luis got damage: 10 HP -> 7 HP
		assertEquals(luis, c.getCharactersOfTheTurn().get(1)); //Second position

		assertFalse(c.isPlayersTurn()); //  !(Players turn) <=> Enemies turn
		assertEquals(1, c.getTurn()); //Now is the turn of the second enemy
		assertEquals(goomba, c.getOwner());
		assertEquals(spiny, c.getNextOwner());

		/* State when First Player Die: Marcos: 2 HP XXX, Luis: 7 HP, Boo: 7 HP, Goomba: 6 HP, Spiny: 5 HP */
		c.attackTurn((IGenericEnemy) c.getOwner()); // Marcos receives the attack and become KO
		marcos.setHp(0);
		//Expected list of players of the game
		assertEquals( new ArrayList<>(Arrays.asList(marcos, luis)), c.getPlayers());
	}

	@Test
	public void attackableCharactersTest(){
		c.initialPhase();//Add players of the game and first items;
		c.startBattlePhase();  //Add players and random enemies to the turn

		System.out.println(c.charactersInTurn);
		System.out.println(c.getAttackableCharactersBy());//Marcos
		c.attackTurn(AttackType.MARTILLO, 2);
	}
}