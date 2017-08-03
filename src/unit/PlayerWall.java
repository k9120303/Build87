package unit;

import unit.Entity.state;

public class PlayerWall extends Wall
{
	private static final int MAX_WALL = 3 ;
	
	public PlayerWall(int xpos, int ypos)
	{
		super(xpos, ypos);
	}

	public static int getMaxWall() {
		return MAX_WALL;
	}
	

}
