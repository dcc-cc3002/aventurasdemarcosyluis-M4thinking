package com.example.aventurasdemarcoyluis.controller;

import com.example.aventurasdemarcoyluis.controller.handlers.EnemiesPhaseHandler;
import com.example.aventurasdemarcoyluis.controller.phases.Phase;
import com.example.aventurasdemarcoyluis.controller.phases.StartPhase;
import com.example.aventurasdemarcoyluis.controller.phases.exceptions.InvalidMovementException;
import com.example.aventurasdemarcoyluis.controller.phases.exceptions.InvalidTransitionException;
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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintStream;
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
	private int allyIndex;
	private int targetIndex;
	private int numOfWins;
	private int passTurnTimes;
	private int inProgressWinOrLose;

	private final PropertyChangeSupport enemiesPhaseNotification = new PropertyChangeSupport(this);
	private final PropertyChangeSupport endGamePhaseNotification = new PropertyChangeSupport(this);
	private final PropertyChangeSupport newBattlePhaseNotification = new PropertyChangeSupport(this);

	private long seed;

	private boolean inBattle;
	private boolean turnForPlayers;

	private Phase phase;
	private PrintStream errorStream;

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
	 *     <li>
	 *         The first phase of the controller and the error stream for the methods
	 *     </li>
	 * </ol>
	 * <p>
	 *     Finally, the observers necessary for the flow of the game are added.
	 * </p>
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
		phase = new Phase(); //Phase system to carry out the flow of the game
		this.setPhase(new StartPhase());
		this.setErrorStream(System.out);

		//Observers
		addEnemiesPhaseNotification(new EnemiesPhaseHandler(this));
	}

	/* *************************************************************************
	 * Phase set and phase transitioners for controller.
	 **************************************************************************/

	/**
	 * Allows you to set the stream for the errors generated by the controller.
	 * @param errorStream the {@code PrintStream} of errors.
	 */
	public void setErrorStream(PrintStream errorStream){
		this.errorStream = errorStream;
	}

	/**
	 * Allows you to get the current stream where the game errors are displayed.
	 * @return the {@code PrintStream} of errors.
	 */
	public PrintStream getErrorStream() {
		return errorStream;
	}

	/**
	 * Sets the current phase of the controller.
	 * @param aPhase phase to be established.
	 */
	public void setPhase(Phase aPhase){
		phase = aPhase;
		phase.setController(this);
	}

	/**
	 * Gets the current phase of the controller.
	 * @return the current phase.
	 */
	public Phase getPhase() {
		return phase;
	}

	/**
	 * Initialize the game. It can fail.
	 */
	public void tryToStart(){
		try {
			phase.toBattlePhase();
			phase.toWaitPhase();
		} catch (InvalidTransitionException e) {
			e.printStackTrace(errorStream);
		}
	}

	/**
	 * Start the attack waiting phase. It can fail.
	 * @return {@code true} if executed correctly and {@code false} if not.
	 */
	public boolean tryToAttackPhase() {
		try {
			phase.toWaitAttackPhase();
			return true;
		} catch (InvalidTransitionException e) {
			e.printStackTrace(errorStream);
			try {
				phase.toWaitPhase();
			} catch (InvalidTransitionException ex) {
				ex.printStackTrace(errorStream);
			}
			return false;
		}
	}

	/**
	 * Allows the current player's turn to pass. It can fail.
	 */
	public void tryToPass(){
		try {
			phase.passTurn();
		} catch (InvalidTransitionException e) {
			e.printStackTrace(errorStream);
			try {
				phase.toWaitPhase();
			} catch (InvalidTransitionException ex) {
				ex.printStackTrace(errorStream);
			}
		}
	}

	/**
	 * It leads to the waiting phase to choose an item. It can fail.
	 * @return {@code true} if executed correctly and {@code false} if not.
	 */
	public boolean tryToWaitItemPhase()  {
		try {
			phase.toWaitItemSelectPhase();
			return true;
		} catch (InvalidTransitionException e) {
			e.printStackTrace(errorStream);
			try {
				phase.toWaitPhase();
			} catch (InvalidTransitionException ex) {
				ex.printStackTrace(errorStream);
			}
			return false;
		}
	}

	/**
	 * Register the item you want to use before choosing the respective player. It can fail.
	 * @param anItem Item to be selected
	 * @return {@code true} if executed correctly and {@code false} if not
	 */
	public boolean tryToUseItem(IItem anItem) {
		try {
			phase.toWaitItemUsePhase(anItem);
			return true;
		} catch (InvalidTransitionException e) {
			e.printStackTrace(errorStream);
			return false;
		}
	}

	/**
	 * Select the attack that the player wants to perform. It can fail.
	 * @param anAttackType type of attack chosen.
	 * @return {@code true} if executed correctly and {@code false} if not.
	 */
	public boolean tryToSelectAttack(AttackType anAttackType){
		try {
			phase.toWaitAttackablePhase(anAttackType);
			return true;
		} catch (InvalidTransitionException e) {
			e.printStackTrace(errorStream);
			return false;
		}
	}

	/**
	 * End of current player's turn of attack. It can fail.
	 * @param anEnemy Enemy to attack.
	 */
	public void tryToFinishAttackTurn(IEnemy anEnemy) {
		try {
			targetIndex = getAttackableEnemies().indexOf(anEnemy);
			phase.endAttackTurn();
		} catch (InvalidTransitionException | InvalidMovementException e) {
			e.printStackTrace(errorStream);
		}
	}

	/**
	 * The turn of item use on any ally, ends. It can fail.
	 * @param allyIndex index of the ally on which to act the item.
	 */
	public void tryToFinishItemTurn(int allyIndex) {
		this.allyIndex = allyIndex;
		try {
			phase.endItemTurn();
		} catch (InvalidTransitionException | InvalidMovementException e) {
			e.printStackTrace(errorStream);
		}
	}

	/**
	 * Execute the entire enemy attack once.
	 */
	public void tryToEndEnemiesTurn() {
		int n = enemiesInTurn.size();
		for (int i = 0; i < n; i++) {
				attackTurn();
		}
	}

	/**
	 * Returns the index of the target enemy of the attack to be carried out.
	 * @return Index of the enemy in the list of attackable enemies by the player.
	 */
	public int getTargetIndex() {
		return targetIndex;
	}

	/**
	 * Provides the index of the ally that will be chosen to play the selected item.
	 * @return ally position in player list.
	 */
	public int getAllyIndex(){
		return allyIndex;
	}

	/**
	 * Adds an observer for the "Enemies phase" start property.
	 * @param listener Observer/listener who will receive the notification
	 */
	private void addEnemiesPhaseNotification(PropertyChangeListener listener){
		enemiesPhaseNotification.addPropertyChangeListener(listener);
	}

	/**
	 * Trigger a notification when you go to the enemies phase,
	 * this phase is virtual and only the observer can carry it out.
	 */
	private void fireEnemiesPhaseNotification(){
		enemiesPhaseNotification.firePropertyChange("To EnemiesPhase", false, true);
	}

	/**
	 * Add an observer for the "new battle property".
	 * @param listener Observer/listener who will receive the notification
	 */
	public void addNewBattlePhaseNotification(PropertyChangeListener listener) {
		newBattlePhaseNotification.addPropertyChangeListener(listener);
	}

	/**
	 * Trigger a new battle start notification to all observers on the "new battle property".
	 */
	private void fireNewBattlePhaseNotification(){
		newBattlePhaseNotification.firePropertyChange("New battle starts", false, true);
	}

	/**
	 * Add an observer for the "End game property".
	 * When the game is over the observer will be notified.
	 * @param listener Property observer/listener.
	 */
	public void addEndGamePhaseNotification(PropertyChangeListener listener){
		endGamePhaseNotification.addPropertyChangeListener(listener);
	}

	/**
	 * Trigger a notification to all observers of the "End game property".
	 * @param finalState Final state of the game when it ends.
	 */
	private void fireEndGamePhaseNotification(String finalState){
		endGamePhaseNotification.firePropertyChange("To EndGamePhase ", "IN PROGRESS", finalState);
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
	 * Create the main-characters/players.
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
	 * Create the chest of the main-characters/players and its functionalities.
	 **************************************************************************/

	/** It allows to initialize the items of the {@code chest} with quantity 0. */
	public void emptyChest() {
		chest.initializeEmpty();
	}

	/**
	 * Get the quantity of a certain item in the chest.
	 * @param anItem Item you want to know its quantity.
	 * @return Returns the amount that the chest has of that item.
	 */
	public int getItemQuantity(IItem anItem){
		return chest.getItemQuantity(anItem);
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
	public void attackTurn(@NotNull AttackType anAttack, int enemyIndex) throws InvalidMovementException {
		if (getPlayerOwner().getFp() >= anAttack.fpCost
				&& getAttackableEnemies().size()>enemyIndex
				&& enemyIndex>=0
				&& turnForPlayers){

			getPlayerOwner().attack(getAttackableEnemies().get(enemyIndex), anAttack);
			finishTurn();
		}
		else{
			throw new InvalidMovementException("You did a incorrect movement.");
		}
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
	public void useItemTurn(int playerIndex, IItem anItem) throws InvalidMovementException {
		IPlayer playerWhoWillUseTheItem = players.get(playerIndex - 1);
		if (!playerWhoWillUseTheItem.isKo() && chest.itemExists(anItem) && turnForPlayers){
			playerWhoWillUseTheItem.selectItem(anItem);
			finishTurn();
		} else{
			throw new InvalidMovementException("Not valid item or player.");
		}
	}

	/** Advance to the next turn by adding one to the count of times passed. */
	public void passTurn() {
		if(turnForPlayers){
			passTurnTimes++;
			finishTurn();
		}
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
			getEnemyOwner().attack(getAttackablePlayers().get(getEnemyOwner().dice(numberOfPlayers)));
		}
		finishTurn();
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
	public void finishTurn() {
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
					fireEnemiesPhaseNotification();
				} else{                             //Turn for next player
					setTurn(turn+1);
				}
				if (!playersInTurn.isEmpty()) {
					//Player Queue. Logic of turns
					IPlayer currentPlayer = playersInTurn.remove(0); //Pop first
					playersInTurn.add(currentPlayer); //Push to end
				}
			}
			else{
				if(turn == enemiesInTurn.size()-1){ //Last turn for enemies
					turnForPlayers = true;
					setTurn(0);
				} else{                             //Turn for next enemy
					setTurn(turn+1);
				}
				if (!enemiesInTurn.isEmpty()){
					//Enemy Queue. Logic of turns
					IEnemy currentEnemy = enemiesInTurn.remove(0); //Pop first
					enemiesInTurn.add(currentEnemy);//Push to end
				}
			}
		}
	}

	/* *************************************************************************
	 * Getters and setters.
	 **************************************************************************/

	/**
	 * Provides the hp or fp statistics for any of the game players
	 * @param playerPos player position in game player list, starts from zero.
	 * @param stat "Hp" or "Fp" to obtain either of these two statistics
	 * @return the player's statistics consulted
	 */
	public int getPlayerStats(int playerPos, String stat){
		if(Objects.equals(stat, "Hp")){
			return getPlayers().get(playerPos).getHp();
		}
		else{//"Fp"
			return getPlayers().get(playerPos).getFp();
		}
	}

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
		return playersInTurn.get(0);
	}

	/**
	 * Get the character that owns the next turn.
	 * @return The {@code ICharacter} who owns the next turn.
	 */
	public ICharacter getNextOwner() {
			if (turn == playersInTurn.size() - 1) { //Last turn for players
				return enemiesInTurn.get(0);
			} else {                                //Turn for next player
				return playersInTurn.get(1);
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
			phase.toEndGamePhase();
			fireEndGamePhaseNotification("LOSE");
		} else {
			this.numOfWins++;

			if(numOfWins == 5){
				inProgressWinOrLose = 1;
				phase.toEndGamePhase();
				fireEndGamePhaseNotification("WIN");
			} else{
				startNewBattle();
				fireNewBattlePhaseNotification();
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

