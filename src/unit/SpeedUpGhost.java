package unit;

import java.awt.Point;

import controller.PlayerController;
import set.GameSet;
import unit.Ghost.TYPE;

public class SpeedUpGhost extends Ghost{

	
	public SpeedUpGhost(int xpos, int ypos, TYPE ghostType) {
		super(xpos, ypos, ghostType);
		super.setSpeed(175);
		// TODO Auto-generated constructor stub
	}
	
	public SpeedUpGhost(Point point,TYPE ghostType,PlayerController player1,PlayerController player2)
	{
		super(point, ghostType,player1,player2);
		setGhostType(ghostType);
		setSpeed(175);
		setImgPath("ghost/SpeedUpGhost");
	}

}
