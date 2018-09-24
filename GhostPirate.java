import java.awt.Point;
import java.util.Observable;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GhostPirate extends PirateShip{
	int prevState;

	/******************************************
	     *Constructor,for Ghost Pirate*
	*******************************************/
	GhostPirate(Image shipImage, Point shipLoc){
		OceanMap oceanMap = OceanMap.getInstance();
		this.shipView = new ImageView(shipImage);
		this.sailStrategy = new GhostSail();
		this.decorator = new HasSailsDecorator(this.sailStrategy);

		/* random used to place x and y dimensions, while random() is valid*/
		Random rand = new Random();
		x = rand.nextInt(oceanMap.getDimension());
		y = rand.nextInt(oceanMap.getDimension());
		while(oceanMap.getState(x,y) > 0 || Math.abs((int)shipLoc.getX()-x) < 4 || Math.abs((int)shipLoc.getY()-y) < 4) {
			x = rand.nextInt(oceanMap.getDimension());
			y = rand.nextInt(oceanMap.getDimension());
		}
		Point newPoint = new Point(x,y);
		shipView.setX(x*oceanMap.getScale());
		shipView.setY(y*oceanMap.getScale());

		/* prevState used to find the previous state of the ship*/
		prevState = oceanMap.getState(newPoint);
		oceanMap.changePoint(newPoint, 3);
	}

	/********************************************************
	 *  Notified location of the Ship, and moves towards it *
	 ********************************************************/
  @Override
  public void update(Observable o, Object arg){
    try{
      Ship ship = (Ship) o;
      decorator.sail(ship, this);
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }

	/*******************************************
	*Overrides the updateEverything() method in*
	*PirateShip, to get the new point of direction*
	*Due to the fact that the previous, and new*
	* point of a ghost ship, whether they are *
	*islands or water, doesn't affect the direction*
	          *it is going to next*
	*********************************************/
  @Override
  public void updateEverything(Point newPoint) {
	  OceanMap oceanMap = OceanMap.getInstance();
	  Point oldPoint = getLocation();
		if(oldPoint.getX()-newPoint.getX()== -1) {
			shipView.setScaleX(-1);
		}else if(oldPoint.getX()-newPoint.getX() == 1) {
			shipView.setScaleX(1);
		}
	  oceanMap.changePoint(oldPoint, prevState);
	  prevState = oceanMap.getState(newPoint);
	  oceanMap.changePoint(newPoint, 2);
	  setLocation(newPoint);
  }

	/**********************************************************************************
	 *  eatSails() has no effect of Ghost Ships because they are, indubitably, ghosts *
	 **********************************************************************************/
  @Override
  public void eatSails() {
	  //do nothing
  }
}
