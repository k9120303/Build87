package unit;

import java.awt.Point;

public abstract class Character extends Entity
{
	
	
	public enum DIRECTION { UP , DOWN , LEFT , RIGHT } ;
	
	protected int speed;
	protected DIRECTION direction;
	
	Character(int xpos, int ypos, state s) 
	{
		super(xpos, ypos, s);
		direction = DIRECTION.LEFT;
		// TODO Auto-generated constructor stub
	}
	
	Character(Point point, state s) 
	{
		super(point, s);
		direction = DIRECTION.LEFT;
		// TODO Auto-generated constructor stub
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	} 
	
	public int getSpeed()
	{
		return speed;
	}

	public void setDirection(DIRECTION direction)
	{
		this.direction = direction;
	}
	
	public DIRECTION getDirection()
	{
		return direction;
	}
	

}
