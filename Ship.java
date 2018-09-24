import java.awt.Point;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.scene.image.ImageView;

public class Ship extends Observable {
	private int x;
	private int y;
	private int scale;
	private int dimension;
	private ImageView shipView;
	private LinkedList<Observer> observers = new LinkedList<Observer>();
	
	Ship(ImageView shipView){
		OceanMap oceanMap = OceanMap.getInstance();
		this.shipView = shipView;
		this.dimension = oceanMap.getDimension();
		this.scale = oceanMap.getScale();
		Random rand = new Random();
		
		x = rand.nextInt(dimension);
		y = rand.nextInt(dimension);
		while(oceanMap.getState(x,y) != 0 || nearTreasures(new Point(x,y))) {
			x = rand.nextInt(dimension);
			y = rand.nextInt(dimension);
		}
		shipView.setX(x*scale);
		shipView.setY(y*scale);
	}
	
	/****************************************************************
	 * Returns true if ship is within 3 spaces of the winning space *
	 ****************************************************************/
	private boolean nearTreasures(Point point) {
		OceanMap oceanMap = OceanMap.getInstance();
		LinkedList<Point> treasures = new LinkedList<Point>();
		for(int gx=0;gx<oceanMap.getDimension();gx++) {
			for(int gy=0;gy<oceanMap.getDimension();gy++) {
				if(oceanMap.getState(gx,gy)==3) {
					treasures.add(new Point(gx,gy));
				}
			}
		}
		
		for(Point treasure : treasures) {
			int diffX = Math.abs((int)point.getX() - (int)treasure.getX());
			int diffY = Math.abs((int)point.getY() - (int)treasure.getY());
			if(diffX < oceanMap.getDimension()/2 || diffY < oceanMap.getDimension()/2) {
				return true;
			}
		}
		return false;
	}
	
	/*********************
	 * Returns ship view *
	 *********************/
	public ImageView getView() {
		return shipView;
	}
	
	/****************************************************
	 * Adds linkedlist of observers to ship's observers *
	 ****************************************************/
	public void registerObservers(LinkedList<Observer> observers) {
		for(Observer observer : observers) {
			this.observers.add(observer);
		}
	}
	
	/*********************************************
	 * Calls observer.update() for all observers *
	 *********************************************/
	public void notifyObservers() {
		for(Observer observer : observers) {
			observer.update(this, null);
		}
	}
	
	/****************************
	 * Returns a point of (x,y) *
	 ****************************/
	public Point getLocation(){
		return new Point(this.x, this.y);
	}
	
	/************************************************
	 * Attempts to go west, then notifies observers *
	 ************************************************/
	public void goWest(){
		OceanMap oceanMap = OceanMap.getInstance();
		shipView.setScaleX(1);
		if(x-1 >= 0 && oceanMap.getState(x-1,y) != 1) {
			x = x-1;
			notifyObservers();
		}
	}
	
	/************************************************
	 * Attempts to go east, then notifies observers *
	 ************************************************/
	public void goEast(){
		OceanMap oceanMap = OceanMap.getInstance();
		shipView.setScaleX(-1);
		if(x+1 <=dimension-1 && oceanMap.getState(x+1,y) != 1) {
			x = x+1;
			notifyObservers();
		}
		
	}
	
	/*************************************************
	 * Attempts to go north, then notifies observers *
	 *************************************************/
	public void goNorth() {
		OceanMap oceanMap = OceanMap.getInstance();
		if(y-1 >= 0 && oceanMap.getState(x,y-1) != 1) {
			y = y-1;
			notifyObservers();
		}
	}
	
	/*************************************************
	 * Attempts to go south, then notifies observers *
	 *************************************************/
	public void goSouth() {
		OceanMap oceanMap = OceanMap.getInstance();
		if(y+1 <= dimension-1 && oceanMap.getState(x,y+1) != 1) {
			y = y+1;
			notifyObservers();
		}
	}
	
}
