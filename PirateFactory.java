import java.awt.Point;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PirateFactory {

/***********************************************
		  * Creates Pirate Ships randomly *
	* by the range of 0-3 to determine pirate*
					   * ship type *
************************************************/
	public PirateShip createPirate(Point shipLoc) {
		OceanMap oceanMap = OceanMap.getInstance();
		int scale = oceanMap.getScale();
		PirateShip pirateShip = null;
		Random rand = new Random();
		Image pirateShipImage = null;
		Image pirateShipImageNoSails = null;
		switch(rand.nextInt(3)){
			case 0:
				pirateShipImage = new Image("/pirateShip.png",scale,scale,true,true);
				pirateShipImageNoSails = new Image("/pirateShip_nosails.png",scale,scale,true,true);
				pirateShip = new DefaultPirate(pirateShipImage, pirateShipImageNoSails, shipLoc);
				break;
			case 1:
				pirateShipImage = new Image("/drunkpirate.png",scale,scale,true,true);
				pirateShipImageNoSails = new Image("/drunkpirate_nosails.png",scale,scale,true,true);
				pirateShip = new DrunkPirate(pirateShipImage, pirateShipImageNoSails, shipLoc);
				break;
			case 2:
				pirateShipImage = new Image("/ghostpirate.png", scale, scale, true, true);
				pirateShip = new GhostPirate(pirateShipImage, shipLoc);
				break;
		}

		return pirateShip;
	}
}
