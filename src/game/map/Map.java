package game.map;


import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import unit.AutomaticWall;
import unit.Border;
import unit.Entity;
import unit.GhostLair;
import unit.PlayerWall;
import unit.WalkField;
import unit.Wall;
import unit.Food;
import unit.Entity.state;


public class Map 
{
	private ArrayList<ArrayList<Entity>> location;
	static final int border = 26; //只能為偶數 
	private int wallNum = 0;
	private ArrayList<Point> wall; 
	private Random random;
	private Point Food[];
	
	public Map()
	{
		location = new ArrayList<ArrayList<Entity>>(border);
		wall = new ArrayList<Point>();
		random = new Random();
		

		mapFromFile();
		
	}
	
	
	
	public boolean isWall(int i,int j)
	{
		if(location.get(i).get(j).getState() == state.OBSTACLE)
			return true;
		else 
			return false;
	}
	
	public boolean isFood(int i,int j)
	{
		if(location.get(i).get(j) instanceof Food)
			return true;
		else 
			return false;
	}

	public boolean isDetachableWall(int x,int y)
	{
		if(location.get(x).get(y) instanceof Wall || location.get(x).get(y) instanceof PlayerWall)
			return true;
		else
			return false;
		
	}
	
	public boolean isPlayerWall(int x,int y)
	{
		if(location.get(x).get(y) instanceof PlayerWall)
			return true;
		else
			return false;
		
	}
	
	public void setWall(int i,int j)
	{
		location.get(i).set(j,new AutomaticWall(i,j));
	}
	
	public void setPlayerWall(int i,int j)
	{
		location.get(i).set(j,new PlayerWall(i,j));
	}
	
	public void setBack(int i,int j)
	{
		location.get(i).set(j,new WalkField(i,j));
	}

	public void setFood(int i,int j)
	{
		location.get(i).set(j,new Food(i,j));
	}
	
	void mapFromFile()
	{
		for(int i=0;i<border;i++)
		{
			ArrayList temp = new ArrayList<Entity>(border);
			location.add(temp);			
		}
		
		File f = new File("map/map" + (random.nextInt(3)+1) + ".txt");
		
		Scanner scan = null;
		
		try {
			 scan = new Scanner(f);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(int i=0;i<border;i++)
			for(int j=0;j<border;j++)
			{
				switch(scan.nextInt())
				{
					case 0:
						location.get(i).add(new WalkField(i,j));
						break;
					case 1:
						location.get(i).add(new GhostLair(i,j));
						wallNum++;
						wall.add(new Point(i,j));
						break;
					case 2:
						location.get(i).add(new AutomaticWall(i,j));
						break;
					case 3:
						location.get(i).add(new Border(i,j));
						break;
					default:
						break;
					
				}
			}
	}

	
	public ArrayList<ArrayList<Entity>> getAllLocation()
	{
		return location;
	}
	
	public Point randomWall()
	{
		int choose = random.nextInt(wall.size());
		return new Point ((int)wall.get(choose).getX(),(int)wall.get(choose).getY());
		
	}

	
	public static void main(String args[]) throws InterruptedException
	{
		Map test = new Map();
		
	}

}
