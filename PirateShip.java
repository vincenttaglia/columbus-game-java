import java.awt.Point;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.Math;

abstract public class PirateShip implements Observer{
	
	int x;
	int y;
	ImageView shipView;
	Image noSailsImage;
	SailStrategy sailStrategy;
	SailStrategy decorator;
	
	/*****************************
	 * Returns location as point *
	 *****************************/
	public Point getLocation() {
		return new Point(x,y);
	}
	
	/*************************************
	 * Updates pirate ship view location *
	 *************************************/
	public void updateView() {
		OceanMap oceanMap = OceanMap.getInstance();
		shipView.setX(getLocation().getX()*oceanMap.getScale());
		shipView.setY(getLocation().getY()*oceanMap.getScale());
		
	}
	
	/****************************************
	 * Change decorator to NoSailsDecorator *
	 ****************************************/
	public void eatSails() {
		decorator = new NoSailsDecorator(sailStrategy);
		shipView.setImage(noSailsImage);
	}
	
	/****************************************************
	 * Set location with passed Point, then update view *
	 ****************************************************/
	public void setLocation(Point point) {
		x = (int)point.getX();
		y = (int)point.getY();
		updateView();
	}
	
	/*****************************
	 * Returns ImageView of Ship *
	 *****************************/
	public ImageView getView() {
		return shipView;
	}
	
	/***********************************************************
	 * Sets the state of the current location to water,        *
	 * sets the state of the newPoint location to pirate ship, *
	 * calls setLocation(newPoint);                            *
	 ***********************************************************/
	public void updateEverything(Point newPoint) {
		Point oldPoint = getLocation();
		if(oldPoint.getX()-newPoint.getX()== -1) {
			shipView.setScaleX(-1);
		}else if(oldPoint.getX()-newPoint.getX() == 1) {
			shipView.setScaleX(1);
		}
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.changePoint(oldPoint, 0);
	    oceanMap.changePoint(newPoint, 2);
	    setLocation(newPoint);
	}
	
	/**************************
	 * Calls decorator.sail() *
	 **************************/
	@Override
	public void update(Observable o, Object arg) {
		try {
			Ship ship = (Ship)o;
			decorator.sail(ship, this);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}

}
