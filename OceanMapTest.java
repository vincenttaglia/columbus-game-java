import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;

class OceanMapTest {

	@Test
	void testGetInstance() {
		OceanMap oceanMap1 = OceanMap.getInstance();
		OceanMap oceanMap2 = OceanMap.getInstance();
		if(oceanMap1 != oceanMap2)
			fail("getInstance() doesn't get same instance when used twice");
	}

	@Test
	void testSetMap() {
		OceanMap oceanMap = OceanMap.getInstance();
		int[][] map1 = oceanMap.getMap();
		oceanMap.setMap(20, 50);
		int[][] map2 = oceanMap.getMap();
		if(map2.equals(map1))
			fail("Map not changing when set");
	}

	@Test
	void testGetMap() {
		OceanMap oceanMap = OceanMap.getInstance();
		String mapClass = oceanMap.getMap().getClass().toString();
		if(!mapClass.equals("class [[I"))
			fail("Does not return 2D array");
	}

	@Test
	void testGetDimension() {
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.setMap(20, 50);
		if(oceanMap.getDimension()!=20)
			fail("Dimensions Aren't Equal");
	}

	@Test
	void testGetScale() {
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.setMap(20, 50);
		if(oceanMap.getScale()!=50)
			fail("Scales Aren't Equal");
	}

	@Test
	void testGetStateIntInt() {
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.setMap(20, 50);
		int[][] map = oceanMap.getMap();
		if(map[5][5]!= oceanMap.getState(5, 5))
				fail("State of Ints aren't equal");
	}

	@Test
	void testGetStateDoubleDouble() {
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.setMap(20, 50);
		int[][] map = oceanMap.getMap();
		if(map[5][5]!= oceanMap.getState(5.0,5.0))
			fail("State of Doubles aren't equal");
	}

	@Test
	void testGetStatePoint() {
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.setMap(20, 50);
		int[][] map = oceanMap.getMap();
		if(map[5][5]!= oceanMap.getState(new Point(5,5)))
			fail("State of Points aren't equal");
	}

	@Test
	void testChangePoint() {
		OceanMap oceanMap = OceanMap.getInstance();
		oceanMap.setMap(20, 50);
		int point = oceanMap.getState(5,5);
		oceanMap.changePoint(new Point(5,5), point+1);
		if(oceanMap.getState(5,5)== point)
			fail("Points Weren't Changes Correctly");
	}

}
