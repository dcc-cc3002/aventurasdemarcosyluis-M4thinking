package com.example.aventurasdemarcoyluis.view;

import com.example.aventurasdemarcoyluis.controller.GameController;
import com.example.aventurasdemarcoyluis.view.viewhandlers.EndGamePhaseHandler;
import com.example.aventurasdemarcoyluis.view.viewhandlers.NewBattlePhaseHandler;
import com.example.aventurasdemarcoyluis.model.characters.attackconfig.AttackType;
import com.example.aventurasdemarcoyluis.model.characters.enemies.IEnemy;
import com.example.aventurasdemarcoyluis.view.buttons.ButtonBuilder;
import com.example.aventurasdemarcoyluis.view.labels.LabelBuilder;
import com.example.aventurasdemarcoyluis.view.nodes.StaticNodeBuilder;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameGUI extends Application {
	private static final String RESOURCE_PATH = "src/main/resources/";
	private final Group root = new Group();
	private final Group startGroup = new Group();
	private final Group optionsGroup = new Group();
	private final Group enemiesGroup = new Group();
	private final Group itemGroup = new Group();
	private final Group attackGroup = new Group();
	private final Group staticGameGroup = new Group(); //Stats, title, players.

	private Label phaseLabel;
	private Label ownerLabel;
	private Label winnerLabel;
	private Label passTurnLabel;
	private Label victoriesLabel;
	private Label marcosHpLabel;
	private Label marcosFpLabel;
	private Label luisHpLabel;
	private Label luisFpLabel;
	private Label mushroomLabel;
	private Label honeySyrupLabel;

	private final Scene primaryScene = new Scene(root,1200,720);
	private final GameController c = new GameController();

	private final NewBattlePhaseHandler newBattlePhaseHandler = new NewBattlePhaseHandler(this);
	private final EndGamePhaseHandler endGamePhaseHandler = new EndGamePhaseHandler(this);

	/**
	 * The starting point of the application.
	 * @param args The input arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Method that initializes the application interface.
	 * @param primaryStage Main scene where all the content of the application will be shown.
	 * @throws FileNotFoundException <p>
	 * 	  	   If it does not find all the components
	 * 	  	   in the system, it will notify you that it
	 * 	  	   cannot find the corresponding file.
	 * 	  	  </p>
	 */
	@Override
	public void start(final @NotNull Stage primaryStage) throws FileNotFoundException {
		primaryStage.getIcons().add(new Image("file:"+RESOURCE_PATH+"applicationIcon.png"));
		primaryStage.setTitle("Las Flipantes Aventuras de Marco y Luis");
		primaryStage.setResizable(false);

		c.addNewBattlePhaseNotification(newBattlePhaseHandler);
		c.addEndGamePhaseNotification(endGamePhaseHandler);

		createNode(0, 720, 720, 1200, root, "backgroundResized.gif");
		createNode(150, 690, 40, 900, startGroup, "title.png");

		Button startButton = createButton(450, 450, 276, 274, startGroup, "pressStart.gif");
		startButton.setOnAction(event -> {
			try {
				startGroup.setVisible(false);
				c.tryToStart();
				addGameToPrimaryScene();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});

		root.getChildren().add(startGroup);
		primaryStage.setScene(primaryScene);
		primaryStage.show();
	}

	private void addGameToPrimaryScene() throws FileNotFoundException {
		//Title
		createNode(350,690,20,500, staticGameGroup,"title.png");

		//Marcos
		@NotNull Button marcosButton = createButton(60, 260, 120, 95, staticGameGroup, "marcos.png");
		marcosButton.setOnAction(event -> c.tryToFinishItemTurn(0));
		createNode(3,105,90,190, staticGameGroup,"lifeBarMarcos.png");

		//Luis
		@NotNull Button luisButton = createButton(230, 277, 120, 95, staticGameGroup, "luis.png");
		luisButton.setOnAction(event -> c.tryToFinishItemTurn(1));
		createNode(193,105,90,190, staticGameGroup,"lifeBarLuis.png");

		//Item Interface
		createNode(355, 170, 75, 75, itemGroup, "itemChest.png");
		createNode(422,170,75,75, itemGroup,"itemChest.png");

		@NotNull Button mushroomButton = createButton(363, 165, 45, 45, itemGroup, "mushroom.png");
		mushroomButton.setOnAction(event -> itemGroup.setVisible(!c.tryToUseItem(c.createRedMushroomItem())));

		@NotNull Button honeySyrupButton = createButton(431, 161, 45, 45, itemGroup, "honeySyrup.png");
		honeySyrupButton.setOnAction(event -> itemGroup.setVisible(!c.tryToUseItem(c.createHoneySyrupItem())));

		//Attack Interface
		createNode(420,490,170,247, attackGroup,"attackType.png");

		//Options buttons
		@NotNull Button itemButton = createButton(5, 540, 94, 80, optionsGroup, "itemIcon.png");
		itemButton.setOnAction(event -> {
			attackGroup.setVisible(false);
			itemGroup.setVisible(c.tryToWaitItemPhase());
			if(c.getItemQuantity(c.createRedMushroomItem())<=0){
				mushroomButton.setVisible(false);
				mushroomLabel.setVisible(false);
			}
			if(c.getItemQuantity(c.createHoneySyrupItem())<=0){
				honeySyrupButton.setVisible(false);
				honeySyrupLabel.setVisible(false);
			}
		});

		@NotNull Button attackButton = createButton(5, 449, 94, 80, optionsGroup, "soloIcon.png");
		attackButton.setOnAction(event -> {
			itemGroup.setVisible(false);
			attackGroup.setVisible(c.tryToAttackPhase());
		});

		@NotNull Button passButton = createButton(2, 360, 98, 84, optionsGroup, "runIcon.png");
		passButton.setOnAction(event -> {
			attackGroup.setVisible(false);
			itemGroup.setVisible(false);
			c.tryToPass();
		});

		//Stats Labels
		victoriesLabel  = createLabel(929,610,"Arial",20, Color.DARKSLATEBLUE, staticGameGroup);
		passTurnLabel   = createLabel(929,640,"Arial",20, Color.DARKSLATEBLUE, staticGameGroup);
		winnerLabel     = createLabel(929,700,"Arial",20, Color.DARKSLATEBLUE, staticGameGroup);
		ownerLabel      = createLabel(929,670,"Arial",20, Color.DARKSLATEBLUE, staticGameGroup);
		phaseLabel      = createLabel(520,80,"Comic Sans MS",30, Color.DARKSLATEBLUE, staticGameGroup);

		marcosHpLabel   = createLabel(30 ,110,"Comic Sans MS",30, Color.BLACK, staticGameGroup);
		marcosFpLabel   = createLabel(120,65,"Comic Sans MS",30, Color.BLACK, staticGameGroup);

		luisHpLabel     = createLabel(220,110,"Comic Sans MS",30, Color.BLACK, staticGameGroup);
		luisFpLabel     = createLabel(300,65,"Comic Sans MS",30, Color.BLACK, staticGameGroup);

		//Items Labels
		mushroomLabel   = createLabel(405,132,"Arial",15, Color.WHITE, itemGroup);
		honeySyrupLabel = createLabel(470,132,"Arial",15, Color.WHITE, itemGroup);

		//Keys
		primaryScene.setOnKeyPressed(this::setKeyboardTriggers);

		//Adding groups
		root.getChildren().add(optionsGroup);
		root.getChildren().add(itemGroup);
		root.getChildren().add(attackGroup);
		root.getChildren().add(enemiesGroup);
		root.getChildren().add(staticGameGroup);

		//Other starts conditions
		showEnemiesAtStart();
		itemGroup.setVisible(false);
		attackGroup.setVisible(false);

		//Start
		startAnimator();
	}

	private void startAnimator() {
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(final long now) {
				setLabelsText();
				optionsGroup.setVisible(c.isPlayersTurn() && //Isn't worth to do two observers for this (+ public methods).
						Objects.equals(c.inProgressWinOrLose(), "IN PROGRESS"));
			}
		};
		timer.start();
	}

	private @NotNull Label createLabel(int xPos, int yPos,String font,int size,Color color, Group aGroup) {
		return new LabelBuilder(aGroup)
				.setPosition(xPos,720-yPos)
				.setFont(font)
				.setFontWeight(FontWeight.BOLD)
				.setSize(size)
				.setColor(color)
				.build();
	}

	private @NotNull Button createButton( int xPos,int yPos,int height,int width, Group aGroup, String file) throws FileNotFoundException {
		var imageView = createNode(xPos, yPos, height, width, aGroup, file);
		return new ButtonBuilder(aGroup)
				.setPosition(xPos, 720-yPos)
				.setSize(height,width)
				.setBackgroundColor("transparent")
				.setImageView(imageView)
				.build();
	}


	private @NotNull ImageView createNode( int xPos, int yPos, int height, int width, Group aGroup, String file) throws FileNotFoundException {
		return new StaticNodeBuilder(aGroup)
				.setImagePath(RESOURCE_PATH + file)
				.setPosition(xPos, 720-yPos)
				.setSize(height, width)
				.build()
				.getNode();
	}

	private void setLabelsText(){
		//Game stats
		phaseLabel      .setText(""+ c.getPhase());
		ownerLabel      .setText("Turn: "+ c.getOwner().getName());
		winnerLabel     .setText("Game State: " + c.inProgressWinOrLose());
		victoriesLabel  .setText("Victories: "+ c.checkWins());
		passTurnLabel   .setText("Pass turn times: " + c.getPassTurnTimes());
		
		//Players stats
		marcosHpLabel   .setText(""+c.getPlayerStats(0,"Hp"));
		marcosFpLabel   .setText(""+c.getPlayerStats(0,"Fp"));
		luisHpLabel     .setText(""+c.getPlayerStats(1,"Hp"));
		luisFpLabel     .setText(""+c.getPlayerStats(1,"Fp"));
		
		//Items stats
		mushroomLabel   .setText("" + c.getItemQuantity(c.createRedMushroomItem()));
		honeySyrupLabel .setText("" + c.getItemQuantity(c.createHoneySyrupItem()));
	}

	private void setKeyboardTriggers(@NotNull KeyEvent event) {
		switch (event.getCode()) {
			case DIGIT1:
				if(c.tryToSelectAttack(AttackType.SALTO)){
					attackGroup.setVisible(false);
				}
				break;
			case DIGIT2:
				if(c.tryToSelectAttack(AttackType.MARTILLO)){
					attackGroup.setVisible(false);
				}
				break;
			default:
				break;
		}
	}

	/**
	 * Generate the buttons and their actions, associated
	 * with each enemy at the beginning of each battle.
	 * @throws FileNotFoundException <p>
	 * 	   If it does not find all the components
	 * 	   in the system, it will notify you that it
	 * 	   cannot find the corresponding file.
	 * 	  </p>
	 */
	public void showEnemiesAtStart() throws FileNotFoundException {
		List<IEnemy> enemiesInTurn = c.getEnemiesInTurn();
		int n = enemiesInTurn.size();
		int enemyDistance = (1200 - 500)/(n+1);
		for (int i = 0; i<n; i++) {
			Random rand = new Random();
			int r = rand.nextInt(3) - 1; //rand int in [-1,1]
			IEnemy currentEnemy = enemiesInTurn.get(i);
			Button enemyButton = createButton(
					500 + enemyDistance * (i + 1) + 5 * r,
					270 + 10 * r,
					100,
					100,
					enemiesGroup,
					currentEnemy.getName() + ".png");

			enemyButton.setOnAction(event -> {
					c.tryToFinishAttackTurn(currentEnemy);
					if (currentEnemy.isKo()) {
						enemiesGroup.getChildren().remove(enemyButton);
					}
			});
		}
	}

	/**
	 * Shows the components of the endgame scene,
	 * it can be a victory view or a defeat view.
	 *
	 * @param finalState victory or defeat status at the end of the game.
	 *
	 * @throws FileNotFoundException <p>
	 *   If it does not find all the components
	 *   in the system, it will notify you that it
	 * 	 cannot find the corresponding file.
	 * </p>
	 */
	public void showEndGame(String finalState) throws FileNotFoundException {
		staticGameGroup.setVisible(false);
		itemGroup.setVisible(false);
		optionsGroup.setVisible(false);
		staticGameGroup.setVisible(false);

		if (Objects.equals(finalState, "WIN")){
				createNode(250,700,200,800, root, "victory.png");
				createNode(350,400,440,500, root,"marcosLuis.gif");
			} else{ //LOSE
				createNode(130,480,480,480, root,"gameOver.gif");
			}
	}
}


