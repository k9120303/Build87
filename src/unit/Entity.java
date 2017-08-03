package unit;

import java.awt.Point;

public abstract class Entity //地圖裡所有物件的父類別 
{
	private int xpos; //在地圖的x座標
	private int ypos; //在地圖的y座標
	private state s; //該位置是否可以被走
	
	Entity(int xpos,int ypos,state s)
	{
		this.xpos = xpos;
		this.ypos = ypos;
		this.s = s;
	}
	
	Entity(Point point,state s)
	{
		this.xpos = (int)point.getX();
		this.ypos = (int)point.getY();
		this.s = s;
	}
	
	public int getXpos()
	{
		return xpos;
	}
	
	public int getYpos()
	{
		return ypos;
	}
	
	public void setXpos(int xpos)
	{
		this.xpos = xpos;
	}
	
	public void setYpos(int ypos)
	{
		this.ypos = ypos;
	}
	public state getState()
	{
		return s;
	} 
	//得到該點是否可以通行
	public enum state
	{
		OBSTACLE,PLAIN;
	}
	//obstacle代表不能走,plain代表可以走


}
