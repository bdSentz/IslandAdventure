package edu.ycp.cs320.IslandAdventure.model;

public class Map {

	private Location[][][] map;
	//make a triple array here 
	public Map()
	{
		map = new Location[25][25][10];//will only allow for 10 players
	}

	public Location getMap(int a)
	{
		return map[a];
	}

	public void setMap(int a, Location loc)
	{
		map[a] = loc;
	}
	

}
