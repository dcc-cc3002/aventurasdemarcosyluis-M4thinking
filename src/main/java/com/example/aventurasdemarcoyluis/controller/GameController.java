package com.example.aventurasdemarcoyluis.controller;

import com.example.aventurasdemarcoyluis.model.characters.ICharacter;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.enemies.*;
import com.example.aventurasdemarcoyluis.model.characters.players.*;
import com.example.aventurasdemarcoyluis.model.itemsconfig.IItem;
import com.example.aventurasdemarcoyluis.model.itemsconfig.ItemBag;
import com.example.aventurasdemarcoyluis.model.itemsconfig.items.HoneySyrup;
import com.example.aventurasdemarcoyluis.model.itemsconfig.items.RedMushroom;

import java.util.*;

public class GameController {
	private final List<IPlayer> players;
	public final List<ICharacter> charactersInTurn;
	//These lists will be used as queues, but they are lists to be able to search in them.
	private final List<IEnemy> enemiesInTurn;
	private final List<IPlayer> playersInTurn;

	private static final List<Integer> enemiesPerLevel =  new ArrayList<>(Arrays.asList(3, 3, 5, 5, 6));

	private static ItemBag chest;

	private int numOfWins;
	private int turn;
	private int inProgresWinOrLose; //{inProgres <-> 0, Win <-> 1, Lose <-> 0}

	private long seed;

	private boolean turnForPlayers;
	private boolean inBattle;


	public GameController() {
		players = new LinkedList<>();
		charactersInTurn = new LinkedList<>();
		playersInTurn = new LinkedList<>();
		enemiesInTurn = new LinkedList<>();
		chest = ItemBag.instance(); //An empty chest is created by default
		turn = -1;
		seed = 0;
		numOfWins = 0;
		inProgresWinOrLose = 0;
		inBattle = false;
	}

	private void add(ICharacter aCharacter){
		charactersInTurn.add(aCharacter);
	}

	public List<IPlayer> getPlayers() {
		return players;
	}

	public void setSeed(long seed){
		this.seed = seed;
	}

	/* *************************************************************************
	 * Create the main-characters/players.
	 **************************************************************************/

	public void addMarcos(int ATK, int DEF, int HP, int FP, int LVL){
		Marcos marcos = new Marcos(ATK, DEF, HP, FP, LVL);
		players.add(marcos);
		add(marcos);
	}

	public void addLuis(int ATK, int DEF, int HP, int FP, int LVL){
		Luis luis = new Luis(ATK, DEF, HP, FP, LVL);
		players.add(luis);
		add(luis);
	}

	public void runShift(ICharacter aCharacter){
	}

	/* *************************************************************************
	 * Create enemies.
	 **************************************************************************/

	private void addGoomba(){
		add(new Goomba(7,4,15,8));
	}

	private void addSpiny(){
		add(new Spiny(8,4,5,8));
	}

	private void addBoo(){
		add(new Boo(5,8,11,8));
	}

	public void addRandomEnemiesToTheTurn(int numberOfRandomEnemies){
		Random rand = new Random();
		for (int i = 0; i < numberOfRandomEnemies ; i++)
		{
			if (seed != 0) {
				rand.setSeed(seed*i);
			}
			int r = rand.nextInt(3); //rand int in [0,2]

			if (r == 0){
				addBoo();
			} else if (r == 1) {
				addGoomba();
			} else {
				addSpiny();
			}
		}
	}

	/* *************************************************************************
	 * Create Items.
	 **************************************************************************/

	private void addItems(int n, IItem anItem){
		for (int i=0; i< n; i++){
			chest.addItem(anItem);
		}
	}

	public IItem createRedMushroomItem(){
		return new RedMushroom();
	}

	public IItem createHoneySyrupItem(){
		return new HoneySyrup();
	}

	public void addRedMushroomItem(int quantity) {
		addItems(quantity,createRedMushroomItem());
	}

	public void addHoneySyrupItem(int quantity) {
		addItems(quantity,createHoneySyrupItem());
	}

	/* *************************************************************************
	 * Create the chest of the main-characters/players.
	 **************************************************************************/

	public void emptyChest() {
		chest.initializeEmpty();
	}

	/* *************************************************************************
	 * Implement shifts: Turn for main-characters/players.
	 **************************************************************************/

	/* Attack Turn With the restrictions */
	public List<ICharacter> getAttackableCharactersBy(){
		return getOwner().getAttackableCharacters(charactersInTurn);
	}

	public void attackTurn(AttackType anAttack, int enemyIndex){
		((IPlayer) getOwner()).attackFromList(anAttack, enemyIndex, getAttackableCharactersBy());
		finishTurn();
	}

	/* Turn to use an Item (A player can use an item of the chest) */

	public void useItemTurn(int playerIndex, IItem anItem){
		if(players.get(playerIndex - 1).canUseOrReceiveItemInBattle())
			players.get(playerIndex - 1).selectItem(anItem);
			finishTurn();
	}

	/* Turn to pass */

	public void passTurn(){finishTurn();}

	/* *************************************************************************
	 * Implement shifts: Turn for Enemies.
	 **************************************************************************/

	public List<IScaredPlayer> getScaredPlayer(List<IPlayer> listOfPlayers){
		List<IScaredPlayer> scaredPlayers = new LinkedList<>();
		for(IPlayer p: listOfPlayers){
			if(p instanceof IScaredPlayer && !p.isKo()) {
				scaredPlayers.add((IScaredPlayer) p);
			}
		}
		return scaredPlayers;
	}

	public void attackTurn(){
		((IEnemy) getOwner()).attackFromList(r, getAttackableCharactersBy());
		finishTurn();
	}

	public int dice(int numberOfFaces){
		Random rand = new Random();
		if (seed != 0) {
			rand.setSeed(seed);
		}
		return rand.nextInt(numberOfFaces);
	}

	/* *************************************************************************
	 * Getters.
	 **************************************************************************
	 /
	/* Get the items from the chest */

	public String getItems(){
		return chest.toString();
	}

	/* Get all the characters of the turn */
	public List<ICharacter> getCharactersOfTheTurn() {
		return charactersInTurn;
	}

	/* Get the character that owns the current turn */
	public ICharacter getOwner() {
		return charactersInTurn.get(0);
	}

	/* Know when the main-characters/players win or lose */
	public boolean checkWin(){
		return true;
	}


	/* Get the character of the next turn */
	public ICharacter getNextOwner() {
		if(charactersInTurn.size()>1){
			return charactersInTurn.get(1);
		}
		else{
			return getOwner();
		}
	}

	/**
	 * when is no longer the turn of  player
	 * we use this method to change the value of turn
	 * @param turn new value
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}

	/* End the current character's turn */
	private void push(IPlayer aPlayer){
		if(!aPlayer.isKo()) {playersInTurn.add(aPlayer);}
	}

	private void push(IEnemy aEnemy){
		if(!aEnemy.isKo()) {enemiesInTurn.add(aEnemy);}
	}

	/* Remove a character from 'turn' when is KO */

	private void finishTurn() {
		if(playersInTurn.size() == 0 || enemiesInTurn.size() == 0){
			endOfBattlePhase();
		} else {
			if(turnForPlayers){
				if(turn == playersInTurn.size()-1){ //Last turn for players
					turnForPlayers = false;
					setTurn(0);
				} else{                             //Turn for next player
					setTurn(turn+1);
				}
				//Pop first
				IPlayer currentPlayer = playersInTurn.remove(0);
				//Push to end if not KO
				push(currentPlayer);
				System.out.println( currentPlayer.getClass()+ ": "+ currentPlayer.getHp());
			}
			else{
				if(turn == enemiesInTurn.size()-1){ //Last turn for enemies
					turnForPlayers = true;
					setTurn(0);
				} else{                             //Turn for next enemy
					setTurn(turn+1);
				}
				//Pop first
				IEnemy currentEnemy = enemiesInTurn.remove(0);
				//Push to end if not KO
				push(currentEnemy);
			}
		}
	}

	public void initialPhase(){
		//Players of the game
		addMarcos(33, 9, 11, 5, 1);
		addLuis(25, 10, 13, 6, 1);
		//Chest is empty at start
		emptyChest();
		//Items at the beginning
		addHoneySyrupItem(3);
		addRedMushroomItem(3);
	}

	public void startBattlePhase(){
		for (IPlayer player: players){
			player.setFp(player.getMaxFp());
			player.setHp(player.getMaxHp());
			add(player);
		}
		//Enemies (se agregan los enemigos aleatorios que corresponden al nivel a los enemigos del turno)
		addRandomEnemiesToTheTurn(enemiesPerLevel.get(numOfWins+1));
		this.inBattle = true;
		this.turnForPlayers = true;
		setTurn(0);

	}

	private void endOfBattlePhase() {
		this.inBattle = false;
		if(playersInTurn.size() == 0){
			inProgresWinOrLose = -1;
		} else {
			this.numOfWins++;

			if(numOfWins == 5){
				inProgresWinOrLose = 1;
			} else{
				startNewBattle();
			}
		}
	}

	private void startNewBattle() {
		enemiesInTurn.clear();
		playersInTurn.clear();
		chest.addItem(createHoneySyrupItem());
		chest.addItem(createRedMushroomItem());
		for(IPlayer p: players){
			p.levelUp();
		}
		startBattlePhase();
	}

	public List<IPlayer> getPlayersInTurn() {
		return playersInTurn;
	}

	public boolean inBattle() {
		return inBattle;
	}

	public boolean isPlayersTurn() {
		return turnForPlayers;
	}

	public int getTurn() {
		return turn;
	}

	public List<IEnemy> getEnemiesInTurn() {
		return enemiesInTurn;
	}
}

