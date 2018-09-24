import java.awt.Point;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.Math;

public class DefaultPirate extends PirateShip{
	/*****************************
	 * DefaultPirate Constructor *
	 *****************************/
	DefaultPirate(Image shipImage, Image shipNoSailsImage, Point shipLoc){
		
		OceanMap oceanMap = OceanMap.getInstance();
		this.shipView = new ImageView(shipImage);
		this.noSailsImage = shipNoSailsImage;
		this.sailStrategy = new DefaultSail();
		this.decorator = new HasSailsDecorator(this.sailStrategy);
		
		/* Choose random point until random point is valid */
		Random rand = new Random();
		x = rand.nextInt(oceanMap.getDimension());
		y = rand.nextInt(oceanMap.getDimension());
		while(oceanMap.getState(x,y) > 0 || Math.abs((int)shipLoc.getX()-x) < 3 || Math.abs((int)shipLoc.getY()-y) < 3) {
			x = rand.nextInt(oceanMap.getDimension());
			y = rand.nextInt(oceanMap.getDimension());
		}
		
		/* Set ship view location */
		shipView.setX(x*oceanMap.getScale());
		shipView.setY(y*oceanMap.getScale());
	}

}
