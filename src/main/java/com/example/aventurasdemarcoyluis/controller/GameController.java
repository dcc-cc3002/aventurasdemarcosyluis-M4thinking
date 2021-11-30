package com.example.aventurasdemarcoyluis.controller;

import com.example.aventurasdemarcoyluis.controller.phases.Phase;
import com.example.aventurasdemarcoyluis.controller.phases.StartPhase;
import com.example.aventurasdemarcoyluis.model.characters.ICharacter;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.enemies.*;
import com.example.aventurasdemarcoyluis.model.characters.enemies.enemiesfactory.BooFactory;
import com.example.aventurasdemarcoyluis.model.characters.enemies.enemiesfactory.EnemiesFactory;
import com.example.aventurasdemarcoyluis.model.characters.enemies.enemiesfactory.GoombaFactory;
import com.example.aventurasdemarcoyluis.model.characters.enemies.enemiesfactory.SpinyFactory;
import com.example.aventurasdemarcoyluis.model.characters.players.*;
import com.example.aventurasdemarcoyluis.model.itemsconfig.IItem;
import com.example.aventurasdemarcoyluis.model.itemsconfig.ItemBag;
import com.example.aventurasdemarcoyluis.model.itemsconfig.items.HoneySyrup;
import com.example.aventurasdemarcoyluis.model.itemsconfig.items.RedMushroom;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * <p>
 *     The controller is the intermediary between
 *     the objects of the model and the graphical
 *     interface of the application.
 * </p>
 * <p>
 *     The controller must be in charge of maintaining
 *     all the parameters necessary to implement the
 *     rules and flow of the game.
 * </p>
 * <p>
 *     The aim of the controller is to send messages
 *     to the model objects telling them what to do
 *     and the model objects communicate to the
 *     controller any changes that are relevant to
 *     the flow of the game and to the user.
 * </p>
 */
public class GameController {
	private static final List<Integer> enemiesPerLevel = new ArrayList<>(Arrays.asList(3, 3, 5, 5, 6));

	private final List<IPlayer> players;
	private final List<IEnemy> enemiesInTurn;
	private final List<IPlayer> playersInTurn;
	private final List<ICharacter> charactersInTurn;

	private static ItemBag chest;
	private static List<EnemiesFactory> enemiesFactories;

	private int turn;
	private int numOfWins;
	private int passTurnTimes;
	private int inProgressWinOrLose;

	private long seed;

	private boolean inBattle;
	private boolean turnForPlayers;

	private Phase phase;

	/**
	 * Constructor of the game controller,
	 * initializes all the lists of
	 * <ol>
	 *     <li>
	 *         {@code players}.
	 *     </li>
	 *     <li>
	 *         {@code playersInTurn}.
	 *     </li>
	 *     <li>
	 *         {@code enemiesInTurn}.
	 *     </li>
	 *     <li>
	 *         {@code charactersInTurn}.
	 *     </li>
	 *     <li>
	 *         {@code enemiesFactories} (In this case it is initialized with all factories).
	 *     </li>
	 * </ol>
	 * <p>
	 *     And all the variables that
	 * 	   determine the flow, such as:
	 * </p>
	 * <ol>
	 *     <li>
	 *         The {@code chest}.
	 *     </li>
	 *     <li>
	 *         The {@code turn}
	 *     </li>
	 *     <li>
	 *          if the players are {@code inBattle}.
	 *     </li>
	 *     <li>
	 *         The {@code numberOfWins} so far.
	 *     </li>
	 *     <li>
	 *         If the game is in progress, players win or lose ({@code inProgressWinOrLose}).
	 *     </li>
	 * </ol>
	 */
	public GameController() {
		//Lists for preserve states
		players = new LinkedList<>();
		charactersInTurn = new LinkedList<>();
		playersInTurn = new LinkedList<>();
		enemiesInTurn = new LinkedList<>();

		//Enemy Factories with enemies by default
		enemiesFactories = new LinkedList<>();
		addFactories();

		//Other important variables
		chest = ItemBag.instance(); //An empty chest is created by default
		turn = -1;
		seed = 0;
		numOfWins = 0;
		inProgressWinOrLose = 0;
		inBattle = false;
		this.setPhase(new StartPhase());
	}
	/* *************************************************************************
	 * Phases implementation for controller.
	 **************************************************************************/
	public void setPhase(Phase aPhase){
		phase = aPhase;
		phase.setController(this);
	}

	/* *************************************************************************
	 * Methods for adding characters to their specific turns.
	 **************************************************************************/

	/**
	 * Allows to add the {@code ICharacter} to the
	 * {@code charactersInTurn} list.
	 * @param aCharacter The enemy to add.
	 */
	private void addCharacterInTurn(ICharacter aCharacter) {
		charactersInTurn.add(aCharacter);
	}

	/**
	 * Allows to add the {@code IEnemy} to the
	 * {@code enemiesInTurn} list and the
	 * {@code charactersInTurn} list.
	 * @param aEnemy The enemy to add.
	 */
	private void addEnemyInTurn(IEnemy aEnemy) {
		enemiesInTurn.add(aEnemy);
		addCharacterInTurn(aEnemy);
	}

	/**
	 * Allows to add the {@code IPlayer} to the
	 * {@code playersInTurn} list and the
	 * {@code charactersInTurn} list.
	 * @param aPlayer The player to add.
	 */
	private void addPlayerInTurn(IPlayer aPlayer){
		playersInTurn.add(aPlayer);
		addCharacterInTurn(aPlayer);
	}

	/* *************************************************************************
	 * Create the main-characters/players. Factory Pattern in the future
	 **************************************************************************/

	/**
	 * Create a new {@code IPlayer} {@code Marcos},
	 * adding him to the list of player's of the game.
	 */
	private void createMarcos(int ATK, int DEF, int HP, int FP, int LVL) {
		Marcos marcos = new Marcos(ATK, DEF, HP, FP, LVL);
		players.add(marcos);
	}

	/**
	 * Create a new {@code IPlayer} {@code Marcos},
	 * adding him to the list of player's of the game.
	 */
	private void createLuis(int ATK, int DEF, int HP, int FP, int LVL) {
		Luis luis = new Luis(ATK, DEF, HP, FP, LVL);
		players.add(luis);
	}

	/* *************************************************************************
	 * Create enemies.
	 **************************************************************************/

	/** All enemy factories are added to the list of {@code enemiesFactories} with generic enemies of each type */
	private void addFactories(){
		enemiesFactories.add(new BooFactory(9, 5, 11, 8));
		enemiesFactories.add(new GoombaFactory(7, 4, 15, 8));
		enemiesFactories.add(new SpinyFactory(8, 4, 5, 8));
	}

	/**
	 * Add specific number of random enemies to
	 * {@code enemiesInTurn} and {@code charactersInTurn}. If
	 * the seed is nonzero, it becomes deterministic.
	 *
	 * @param numberOfRandomEnemies The number of enemies to be added.
	 */
	public void addRandomEnemiesToTheTurn(int numberOfRandomEnemies) {
		Random rand = new Random();
		for (int i = 0; i < numberOfRandomEnemies; i++) {
			if (seed != 0) {
				rand.setSeed(seed * i); //For this is better not to use the dice, the extended version is a mess.
			}
			int r = rand.nextInt(enemiesFactories.size()); //rand int in [0,size())
			addEnemyInTurn(enemiesFactories.get(r).create());
		}
	}

	/* *************************************************************************
	 * Create Items. Factory Pattern in the future
	 **************************************************************************/

	/**
	 * Create a new {@code IItem} {@code RedMushroom}
	 * @return the new {@code RedMushroom}
	 */
	public IItem createRedMushroomItem() {
		return new RedMushroom();
	}

	/**
	 * Create a new {@code IItem} {@code HoneySyrup}
	 * @return the new {@code HoneySyrup}
	 */
	public IItem createHoneySyrupItem() {
		return new HoneySyrup();
	}

	/**
	 * Add a specific quantity of {@code RedMushroom} item to the chest.
	 * @param quantity Quantity of the {@code IItem} to introduce.
	 */
	private void addRedMushroomItem(int quantity) {
		chest.addItems(quantity, createRedMushroomItem());
	}

	/**
	 * Add a specific quantity of {@code HoneySyrup} item to the chest.
	 * @param quantity Quantity of the {@code IItem} to introduce.
	 */
	private void addHoneySyrupItem(int quantity) {
		chest.addItems(quantity, createHoneySyrupItem());
	}

	/* *************************************************************************
	 * Create the chest of the main-characters/players.
	 **************************************************************************/

	/** It allows to initialize the items of the {@code chest} with quantity 0. */
	public void emptyChest() {
		chest.initializeEmpty();
	}

	/* *************************************************************************
	 * Implement turns: Turn for main-characters/players.
	 **************************************************************************/

	/**
	 * Delivers the list of players the current player can attack.
	 * @return A sublist of {@code IPlayer}s commonly called {@code attackablePlayers}.
	 */
	public List<IPlayer> getAttackablePlayers() {
		return getEnemyOwner().getAttackablePlayers(playersInTurn);
	}

	/**
	 * Delivers the list of enemies the current player can attack.
	 * @return A sublist of {@code IEnemy}s commonly called {@code attackableEnemies}.
	 */
	public List<IEnemy> getAttackableEnemies() {//Maybe not useful because player choose himself
		return getPlayerOwner().getAttackableEnemies(enemiesInTurn);
	}

	/**
	 * In an attack turn for main-characters/players the following actions are performed:
	 * <ol>
	 *     <li>
	 *         Select some {@code AttackType} (SALTO or MARTILLO).
	 *     </li>
	 *     <li>
	 *         Select an {@code IEnemy} to execute the attack, spending FP according to the {@code AttackType} and its cost.
	 *     </li>
	 *     <li>
	 *         Finish turn.
	 *     </li>
	 * </ol>
	 * <p>
	 *     Observation: The turn will not end if a valid move is not made.
	 * </p>
	 * @param anAttack Type of attack to be executed.
	 * @param enemyIndex Index starting from one, from the list {@code attackableEnemies}.
	 */
	public void attackTurn(@NotNull AttackType anAttack, int enemyIndex) {
		if (getPlayerOwner().getFp() >= anAttack.fpCost && getAttackableEnemies().size()>=enemyIndex && turnForPlayers){
			getPlayerOwner().attack(getAttackableEnemies().get(enemyIndex-1), anAttack);
			finishTurn();
		}
		//AssertionError here in the future
	}

	/**
	 * In the turn to use an Item, the steps are as follows:
	 * <ol>
	 *     <li>
	 *         Choose an Item from those available in the item chest.
	 *     </li>
	 *     <li>
	 *         Select the main-character/player on which the {@code IItem} will have effect (not KO).
	 *     </li>
	 *     <li>
	 *         Finish turn.
	 *     </li>
	 * </ol>
	 * <p>
	 *     Observation: The turn will not end if a valid move is not made
	 * </p>
	 * @param playerIndex Index starting from 1, over all existing players in the game (including KOs).
	 * @param anItem Item to play on the selected player.
	 */
	public void useItemTurn(int playerIndex, IItem anItem) {
		IPlayer playerWhoWillUseTheItem = players.get(playerIndex - 1);
		if (!playerWhoWillUseTheItem.isKo() && chest.itemExists(anItem) && turnForPlayers){
			playerWhoWillUseTheItem.selectItem(anItem);
			finishTurn();
		}
		//AssertionError here in the future
	}

	/** Advance to the next turn by adding one to the count of times passed. */
	public void passTurn() {
		if(turnForPlayers){
			passTurnTimes++;
			finishTurn();
		}
		//AssertionError here in the future
	}

	/* *************************************************************************
	 * Implement turns: Turn for Enemies.
	 **************************************************************************/

	/**
	 * Perform a normal attack on a random {@code IPlayer}
	 * within that enemy's {@code attackablePlayers} list,
	 * if there are none, the enemy's turn ends.
	 */
	public void attackTurn() {
		int numberOfPlayers = getAttackablePlayers().size();
		if(numberOfPlayers > 0){
			int r = dice(numberOfPlayers);
			getEnemyOwner().attack(getAttackablePlayers().get(r));
		}
		finishTurn();
	}

	/**
	 * Abstraction of a dice with homogeneous probability
	 * distribution with respect to its faces, is
	 * deterministic if the current seed is different from zero.
	 * @param numberOfFaces Number of faces of the dice.
	 * @return Face result indexed from zero.
	 */
	private int dice(int numberOfFaces) {
		Random rand = new Random();
		if (seed != 0) {
			rand.setSeed(seed);
		}
		return rand.nextInt(numberOfFaces);
	}

	/* *************************************************************************
	 * Finish turn for all characters.
	 **************************************************************************/

	/**
	 * The defeat or victory conditions of the battle are checked,
	 * if the battle continues, the KO characters are eliminated
	 * in the relevant turn lists {@code playersInTurn} and
	 * {@code enemiesInTurn}, then the change is made from the
	 * respective list (queue) depending on the turn for {@code IPlayer}s
	 * or for {@code IEnemy}s.
	 */
	private void finishTurn() {
		//Filter list of turns
		playersInTurn.removeIf(IPlayer::isKo);
		enemiesInTurn.removeIf(IEnemy::isKo);

		//Players can't pass more than 5 times in a Battle
		if(playersInTurn.size() == 0 || enemiesInTurn.size() == 0 || passTurnTimes>5){
			endOfBattlePhase();
		} else {
			if(turnForPlayers){
				if(turn == playersInTurn.size()-1){ //Last turn for players
					turnForPlayers = false;
					setTurn(0);
				} else{                             //Turn for next player
					setTurn(turn+1);
				}
				//Player Queue. Logic of turns
				IPlayer currentPlayer = playersInTurn.remove(0); //Pop first
				playersInTurn.add(currentPlayer); //Push to end
			}
			else{
				if(turn == enemiesInTurn.size()-1){ //Last turn for enemies
					turnForPlayers = true;
					setTurn(0);
				} else{                             //Turn for next enemy
					setTurn(turn+1);
				}
				//Enemy Queue. Logic of turns
				IEnemy currentEnemy = enemiesInTurn.remove(0); //Pop first
				enemiesInTurn.add(currentEnemy);//Push to end
			}
		}
	}

	/* *************************************************************************
	 * Getters and setters.
	 **************************************************************************/

	/**
	 * Get the set of items with their quantity from the chest.
	 * @return String form of the set of items.
	 */
	public String getItems() {
		return chest.toString();
	}

	/**
	 * Get the character that owns the current turn.
	 * @return The {@code ICharacter} who owns the turn.
	 */
	public ICharacter getOwner() {
		if (turnForPlayers) {
			return playersInTurn.get(0);
		} else {
			return enemiesInTurn.get(0);
		}
	}

	/**
	 * Get the character that owns the next turn.
	 * @return The {@code ICharacter} who owns the next turn.
	 */
	public ICharacter getNextOwner() {
		if (turnForPlayers) {
			if (turn == playersInTurn.size() - 1) { //Last turn for players
				return enemiesInTurn.get(0);
			} else {                                //Turn for next player
				return playersInTurn.get(1);
			}
		} else { //Turn for enemies
			if (turn == enemiesInTurn.size() - 1) { //Last turn for enemies
				return playersInTurn.get(0);
			} else {                                //Turn for next enemy
				return enemiesInTurn.get(1);
			}
		}
	}

	/**
	 * Get all characters that stay alive (not KO) in the current battle or "the current characters of the turn".
	 * @return List of current {@code ICharacter}s of the turn.
	 */
	public List<ICharacter> getCharactersOfTheTurn() {
		//Remaining characters, regarding the current state of the battle
		charactersInTurn.removeIf(ICharacter::isKo);
		return charactersInTurn;
	}

	/**
	 * Delivers the list of generic game players
	 * @return list of players, with current stats (including KO).
	 */
	public List<IPlayer> getPlayers() {
		return players;
	}

	/**
	 * Delivers the list of players in the current turn.
	 * @return list of {@code IPlayer}, with current stats (not including KO).
	 */
	public List<IPlayer> getPlayersInTurn() {
		return playersInTurn;
	}

	/**
	 * Delivers the list of enemies in the current turn.
	 * @return list of {@code IEnemy}, with current stats (not including KO).
	 */
	public List<IEnemy> getEnemiesInTurn() {
		return enemiesInTurn;
	}

	/**
	 * Check if the battle is in progress or not
	 * @return {@code true} if characters are in battle, {@code false} if not.
	 */
	public boolean inBattle() {
		return inBattle;
	}

	/**
	 * Check if it is the turn of the players,
	 * if not, it is understood that it is the
	 * turn of the enemies.
	 * @return {@code true} if is the turn for {@code playersInTurn},
	 *                 {@code false} if is the turn for {@code enemiesInTurn}.
	 */
	public boolean isPlayersTurn() {
		return turnForPlayers;
	}

	/**
	 * Get the current {@code IPlayer} or {@code IEnemy}'s turn.
	 * @return Index that starts from 0 and refers to the {@code IPlayer}s
	 *                 in {@code playersInTurn} and {@code IEnemy}s in {@code enemiesInTurn},
	 *                 depending on the value of the {@code boolean} {@code turnForPlayers}.
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * <p>
	 *     Get the number of passes so far,
	 * once a battle is over this number will
	 * become 0 again unless the game is over.
	 * </p>
	 * <p>
	 * Observation: If there are more than 5
	 * {@code passTurnTimes} the game will end
	 * and players lose.
	 * </p>
	 * @return The number of times passed.
	 */
	public int getPassTurnTimes() {
		return passTurnTimes;
	}

	/**
	 * Allows you to set the seed to the
	 * {@code addRandomEnemiesToTheTurn()}
	 * and {@code attackTurn()} methods,
	 * any non-zero seed will have the desired
	 * effect, otherwise it will follow randomly.
	 * @param seed Any number of type {@code long}
	 */
	public void setSeed(long seed) {
		this.seed = seed;
	}

	/**
	 * Get the number of times main-characters/players have won a battle
	 * @return number of wins
	 */
	public int checkWins() {
		return numOfWins;
	}

	/**
	 * Check if the game is in progress or if the main-characters/players won or lost until now.
	 * @return "IN PROGRESS" if the game is in progress, "WIN" if players won and "LOSE" is players lose.
	 */
	public String inProgressWinOrLose(){
		if(inProgressWinOrLose == 0){
			return "IN PROGRESS";
		} else if (inProgressWinOrLose == 1){
			return "WIN";
		} else{
			return "LOSE";
		}
	}

	/* Private getters and setters */

	/**
	 * Get the current owner of type {@code IPlayer}, it only makes sense if it's the players' turn.
	 * @return The {@code IPlayer} that owns the turn.
	 */
	private IPlayer getPlayerOwner() {
		return playersInTurn.get(0);
	}

	/**
	 * Get the current owner of type {@code IEnemy}, it only makes sense if it's the enemies' turn.
	 * @return The {@code IEnemy} that owns the turn.
	 */
	private IEnemy getEnemyOwner() {
		return enemiesInTurn.get(0);
	}

	/**
	 * Set the current {@code IPlayer} or {@code IEnemy}'s turn.
	 * @param turn  The turn of the current {@code IPlayer} or
	 * {@code IEnemy} according to the state of the game.
	 */
	private void setTurn(int turn) {
		this.turn = turn;
	}

	/* *************************************************************************
	 * Phases.
	 **************************************************************************/

	/**
	 * In the initial phase:
	 * <ol>
	 *     <li>
	 *         Main characters are added to the game.
	 *     </li>
	 *     <li>
	 *         3 {@code RedMushroom} and 3 {@code HoneySyrup} are given away and
	 *         are stored in the chest of the main-characters/players.
	 *     </li>
	 * </ol>
	 */
	public void initialPhase(){
		//Players of the game
		createMarcos(33, 9, 25, 5, 1);
		createLuis(25, 10, 22, 6, 1);
		//Chest is empty at start
		emptyChest();
		//Items at the beginning
		addHoneySyrupItem(3);
		addRedMushroomItem(3);
	}

	/**
	 * The {@code HP} of the players and their {@code FP} are
	 * reset and the turns are organized to start the battle,
	 * adding the players and the random enemies on the turn lists.
	 */
	public void startBattlePhase(){
		for (IPlayer player: players){
			player.restoreDynamicStats();
			player.restoreDynamicStats();
			addPlayerInTurn(player);
		}
		//Random enemies that correspond to the level, are added to the enemies of the turn
		addRandomEnemiesToTheTurn(enemiesPerLevel.get(numOfWins));
		this.inBattle = true;
		this.turnForPlayers = true;
		setTurn(0);
	}

	/**
	 * A battle ends if any of the following occur:
	 * <ol>
	 *     <li>
	 *         When all enemies are knocked out, or else.
	 *     </li>
	 *     <li>
	 *         When all the main-characters/players are K.O.
	 *     </li>
	 * </ol>
	 * <p>
	 *     In the first case the main characters win and here are two cases:
	 *     <ul>
	 *     <li>
	 *         If it's their 5th win, they win the game and the game is over.
	 *     </li>
	 *     <li>
	 *         If they still don't complete 5 wins, increase their level by 1 by increasing their stats.
	 *     </li>
	 *     </ul>
	 * <p>
	 *     Observation: if the players pass turns more than 5 times in a battle they will lose.
	 * </p>
	 */
	public void endOfBattlePhase() {
		this.inBattle = false;
		if(playersInTurn.size() == 0 || passTurnTimes>5){
			inProgressWinOrLose = -1;
		} else {
			this.numOfWins++;

			if(numOfWins == 5){
				inProgressWinOrLose = 1;
			} else{
				startNewBattle();
			}
		}
	}

	/**
	 * After {@code endOfBattlePhase} and the game does not end,
	 * the number of passed turns starts at 0, the characters
	 * that remained in the turn lists are deleted, the
	 * {@code Item}s are added, the players are leveled up and
	 * the battle begins one more time.
	 */
	private void startNewBattle() {
		passTurnTimes=0;
		enemiesInTurn.clear();
		playersInTurn.clear();
		charactersInTurn.clear();
		addRedMushroomItem(1);
		addHoneySyrupItem(1);
		for(IPlayer p: players){
			p.levelUp();
		}
		startBattlePhase();
	}
}

