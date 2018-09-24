import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class ShipTest {

	@Test
	void testGetView() {
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.setMap(20, 50);
		ImageView shipImageView = new ImageView();

		Ship ship = new Ship(shipImageView);
		if(!ship.getView().equals(shipImageView))
			fail("Ship.getView() not returning correct view");
	}

	@Test
	void testGetLocation() {
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.setMap(20, 50);
		ImageView shipImageView = new ImageView();

		Ship ship = new Ship(shipImageView);
		if(ship.getLocation()==ship.getLocation())
			fail("Location of Ships aren't equalling up");
	}

	@Test
	void testGoWest() {
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.setMap(20, 50);
		ImageView shipImageView = new ImageView();

		Ship ship = new Ship(shipImageView);
		Point shipPoint = ship.getLocation();
		int newState = oceanMap.getState(shipPoint.getX()-1, shipPoint.getY());
		ship.goWest();
		if(newState==0){
			if(!ship.getLocation().equals(new Point((int)shipPoint.getX()-1, (int)shipPoint.getY()))){
				fail("Ship Should move West");
			}
		}else{
			if(!ship.getLocation().equals(shipPoint)){
				fail("Ship Shouldn't move West");
			}
		}

	}

	@Test
	void testGoEast() {
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.setMap(20, 50);
		ImageView shipImageView = new ImageView();

		Ship ship = new Ship(shipImageView);
		Point shipPoint = ship.getLocation();
		int newState = oceanMap.getState(shipPoint.getX()+1, shipPoint.getY());
		ship.goEast();
		if(newState==0){
			if(!ship.getLocation().equals(new Point((int)shipPoint.getX()+1, (int)shipPoint.getY()))){
				fail("Ship Should move East");
			}
		}else{
			if(!ship.getLocation().equals(shipPoint)){
				fail("Ship Shouldn't move East");
			}
		}
	}

	@Test
	void testGoNorth() {
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.setMap(20, 50);
		ImageView shipImageView = new ImageView();

		Ship ship = new Ship(shipImageView);
		Point shipPoint = ship.getLocation();
		int newState = oceanMap.getState(shipPoint.getX(), shipPoint.getY()-1);
		ship.goNorth();
		if(newState==0){
			if(!ship.getLocation().equals(new Point((int)shipPoint.getX(), (int)shipPoint.getY()-1))){
				fail("Ship Should move North");
			}
		}else{
			if(!ship.getLocation().equals(shipPoint)){
				fail("Ship Shouldn't move North");
			}
		}
	}

	@Test
	void testGoSouth() {
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.setMap(20, 50);
		ImageView shipImageView = new ImageView();

		Ship ship = new Ship(shipImageView);
		Point shipPoint = ship.getLocation();
		int newState = oceanMap.getState(shipPoint.getX(), shipPoint.getY()+1);
		ship.goSouth();
		if(newState==0){
			if(!ship.getLocation().equals(new Point((int)shipPoint.getX(), (int)shipPoint.getY()+1))){
				fail("Ship Should move South");
			}
		}else{
			if(!ship.getLocation().equals(shipPoint)){
				fail("Ship Shouldn't move South");
			}
		}	}

}
