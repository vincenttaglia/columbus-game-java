


import java.awt.Point;
import java.util.LinkedList;
import java.util.Observer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class OceanExplorer extends Application{
	/***********************************************************
	 * Application Settings:                                   *
	 *                                                         *
	 * dimension: number of cells in a row                     *
	 * scale: how big to make cells                            *
	 * numPirates: number of pirates on the board              *
	 * numMonsterGroups: number of monster groups on the board *
	 ***********************************************************/
	final int dimension = 25;
	final int scale = 30;
	final int numPirates = 5;
	final int numMonsterGroups = 3;
	
	OceanMap oceanMap;
	Ship ship;
	PirateFactory pirateFactory = new PirateFactory();
	LinkedList<PirateShip> pirateShips = new LinkedList<PirateShip>();
	LinkedList<MonsterGroup> monsterGroups = new LinkedList<MonsterGroup>();
	AnchorPane window;
	Stage stage;
	Scene scene;
	
	
	public static void main(String[] args) {
		launch(args);
		

	}
	
	/*****************************************
	 * Start method called from launch(args) *
	 *****************************************/
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		/* Create grid and ship until game is winnable */
		startBeg();
		
		/* Generate grid view */
		for(int x = 0; x < dimension;x++) {
			for(int y = 0; y < dimension; y++) {
				Rectangle rect = new Rectangle(x*scale,y*scale,scale,scale);
				rect.setStroke(Color.BLACK);
				if(oceanMap.getState(x,y) == 1){
					rect.setFill(Color.GREEN);
				}else if(oceanMap.getState(x,y) == 3){
					rect.setFill(Color.GOLD);
				}else{
					rect.setFill(Color.PALETURQUOISE);
				}
				window.getChildren().add(rect);
			}
		}
		
		/* Show user ship on top of grid view */
		window.getChildren().add(ship.getView());
		
		/* Generate pirates based on set number of pirates */
		for(int i=0; i<numPirates; i++) {
			PirateShip pirateShip = pirateFactory.createPirate(ship.getLocation());
			window.getChildren().add(pirateShip.getView());
			pirateShips.add(pirateShip);
		}
		
		/* Generate monster groups based on set of number of monsters */
		for(int i=0; i<numMonsterGroups; i++) {
			MonsterGroup monsterGroup = new MonsterGroup();
			
			for(ImageView view : monsterGroup.getViews()) {
				window.getChildren().add(view);
			}
			monsterGroups.add(monsterGroup);
		}
		
		/* Add PirateShips to Ship observers */
		LinkedList<Observer> observers = new LinkedList<Observer>();
		for(PirateShip pirateShip : pirateShips) {
			observers.add(pirateShip);
		}
		ship.registerObservers(observers);
		
		startSailing(scene);
	}
	
	/********************************************
	 * Generate new game until game is winnable *
	 ********************************************/
	private void startBeg() {
		oceanMap = OceanMap.getInstance();
		oceanMap.setMap(dimension, scale);
		window = new AnchorPane();
		scene = new Scene(window,dimension*scale,dimension*scale);
		stage.setScene(scene);
		stage.setTitle("Columbus Game");
		
		/* Create User Ship */
		Image shipImage = new Image("/ship.png",scale,scale,true,true);
		ImageView shipImageView = new ImageView(shipImage);
		ship = new Ship(shipImageView);
		
		/* Check if it's possible to get to the winning space */
		
		
		boolean restart = true;
		LinkedList<Point> adj = BFS.getAdj(ship.getLocation());
		Point endGame = null;
		
		/* Find winning space */
		for(int x=0;x<dimension;x++) {
			for(int y=0;y<dimension;y++) {
				if(oceanMap.getState(x,y) == 3) {
					endGame = new Point(x,y);
				}
			}
		}
		
		/* Get BFS */
		int[][] bfsMap = BFS.getPath(ship.getLocation(), endGame);
		for(Point ad : adj) {
			
			/* If an adj is set lower than MAX_VALUE, path is possible, no need to restart */
			if(bfsMap[(int)ad.getX()][(int)ad.getY()] < Integer.MAX_VALUE) {
				restart = false;
			}
		}
		
		/* If no possible path exists, re-generate */
		if(restart) {
			startBeg();
		}else {
			stage.show();
		}
	}
	
	/**************************
	 * Start keypress handler *
	 **************************/
	private void startSailing(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			/********************
			 * Keypress handler *
			 ********************/
			@Override
			public void handle(KeyEvent key) {
				Point oldpos = ship.getLocation();
				switch(key.getCode()) {
				case RIGHT:
					ship.goEast();
					break;
				case LEFT:
					ship.goWest();
					break;
				case UP:
					ship.goNorth();
					break;
				case DOWN:
					ship.goSouth();
					break;
				default:
					break;
				}
				Point shippos = ship.getLocation();
				
				/* If user ship moved, set user ship view location and check for end of game */
				if(!oldpos.equals(shippos)) {
					ship.getView().setX(shippos.x*scale);
					ship.getView().setY(shippos.y*scale);
					checkFinish();
				}
			}
		});
		
	}
	
	/*******************************************************************
	 * Check for game finish (Win or killed by pirate/stalled by birds *
	 *******************************************************************/
	private void checkFinish() {
		
		/* Check if won */
		if(oceanMap.getState(ship.getLocation()) == 3){
			endGame(3);
		}
		
		
		/* Check if any pirate locations are the same as the user ship location */
		for(PirateShip pirateShip : pirateShips) {
			if(ship.getLocation().equals(pirateShip.getLocation())) {
				endGame(1);
			}
		}
		
		/* Check if ships moved into birds */
		checkMonsters();
		
		/* Move monsters to new position */
		for(MonsterGroup monsterGroup : monsterGroups) {
			monsterGroup.move();
		}
		
		/* Check if birds moved into ships */
		checkMonsters();
	}
	
	/************************************************************
	 * Check if any monster locations are the same as any ships *
	 ************************************************************/
	private void checkMonsters() {
		for(MonsterGroup monsterGroup : monsterGroups) {
			if(monsterGroup.hasMonster(ship.getLocation())) {
				/* If a user ship, end game */
				endGame(2);
			}
			for(PirateShip pirateShip : pirateShips) {
				if(monsterGroup.hasMonster(pirateShip.getLocation())) {
					/* If a pirate ship, change decorator */
					pirateShip.eatSails();
				}
			}
		}
	}
	
	/**********************************
	 * End the game with a text alert *
	 **********************************/
	private void endGame(int reason) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Columbus Game");
		Text text = new Text("");
		switch(reason) {
		case 1:
			alert.setHeaderText("Sorry, you lose");
			text = new Text("\nA pirate ship pulled up and boarded you! No one on your crew survived.");
			break;
		case 2:
			alert.setHeaderText("Sorry, you lose");
			text = new Text("\nMonster birds ate your sails and now you are stranded in the middle of the ocean! It's just a matter of time before a pirate comes to plunder and kill your crew.");
			break;
		case 3:
			alert.setHeaderText("Congratulations! You win!");
			text = new Text("\nYou successfully evaded multiple pirates to discover America! (but not really)");
			break;
		}
		text.setTextAlignment(TextAlignment.CENTER);
		text.setWrappingWidth(300);
		alert.getDialogPane().setContent(text);
		
		alert.showAndWait();
		System.exit(0);
	}

}
