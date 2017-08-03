package controller;

import java.awt.FlowLayout;
import java.awt.Point;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import game.map.Map;
import set.GameSet;
import unit.Player;
import view.GameView;
import view.MapView;
import view.PlayerStateView;

public class MapController 
{
	protected Map map;
	protected MapView mapView;
	
	public MapController(Map map,MapView mapView)
	{
		this.map = map;
		this.mapView = mapView;
	}

	public void addPlayerWall(int i,int j,int speed)
	{
		map.setPlayerWall(i, j);
		Timer setImg = new Timer();
		setImg.schedule(new TimerTask()
				{

					@Override
					public void run() 
					{
						mapView.setLabelIcon(i, j, "map/img/playerWall3.gif");
					}
			
				},speed/ 3);
		setImg.schedule(new TimerTask()
		{

			@Override
			public void run() 
			{
				mapView.setLabelIcon(i, j, "map/img/playerWall2.gif");
			}
	
		},speed * 2 / 3);
		setImg.schedule(new TimerTask()
		{

			@Override
			public void run() 
			{
				mapView.setLabelIcon(i, j, "map/img/playerWall1.gif");
			}
	
		},speed);
		
	}
	/*建構一個玩家牆壁*/
	
	public void destoryPlayerWall(int i ,int j,int speed)
	{
		map.setBack(i, j);
		Timer setImg = new Timer();
		setImg.schedule(new TimerTask()
				{

					@Override
					public void run() 
					{
						mapView.setLabelIcon(i, j, "map/img/playerWall2.gif");
					}
			
				},speed / 3);
		setImg.schedule(new TimerTask()
		{

			@Override
			public void run() 
			{
				mapView.setLabelIcon(i, j, "map/img/playerWall3.gif");
			}
	
		},speed * 2 / 3);
		setImg.schedule(new TimerTask()
		{

			@Override
			public void run() 
			{
				mapView.setLabelIcon(i, j, "map/img/floor.jpg");
			}
	
		},speed);

	}
	/*摧毀一個玩家牆壁*/
	
	public void destoryWall(int i ,int j,int speed)
	{
		
		map.setBack(i, j);
		Timer setImg = new Timer();
		setImg.schedule(new TimerTask()
				{

					@Override
					public void run() 
					{
						mapView.setLabelIcon(i, j, "map/img/wall1.png");
					}
			
				},speed/ 3);
		setImg.schedule(new TimerTask()
		{

			@Override
			public void run() 
			{
				mapView.setLabelIcon(i, j, "map/img/wall2.png");
			}
	
		},speed * 2 / 3);
		setImg.schedule(new TimerTask()
		{

			@Override
			public void run() 
			{
				mapView.setLabelIcon(i, j, "map/img/floor.jpg");
			}
	
		},speed);

		
	}

	public Point FoodGenerate()
	{
		
		 int x;
		 int y;
		
		
			Random r = new Random();
			do
			{
				x = r.nextInt(GameSet.border);
				y = r.nextInt(GameSet.border);
			}while(map.isWall(x,y) == true);
			map.setFood(x,y);
			mapView.setFoodView(x, y);
			
			return new Point(x,y);
	}
	/*隨機生成一個食物*/
	
	public void FoodDelete(Point point)
	{
		int x = (int)point.getX();
		int y = (int)point.getY();
		if(map.isFood(x,y))
		{
			map.setBack(x,y);
			mapView.DeleteFoodView(x, y);
		}
	}
	
	public void FoodDelete(int x,int y)
	{
		if(map.isFood(x,y))
		{
			map.setBack(x,y);
			mapView.DeleteFoodView(x, y);
		}
	}
	/*刪除既有食物*/

	public static void main (String[] args)
	{
		Map map = new Map();
		MapView mapView = new MapView(map.getAllLocation());
		MapController test = new MapController(map,mapView);
	
		
		/*int goX[] = {1,0,0,-1};
		int goY[] = {0,1,-1,0};
		
			Thread tt = new Thread( new Runnable(){
				int nowX = 5;int nowY = 12;
				int now = 0;
				@Override
				public void run()
				{
					Thread t = new Thread();
					while(true)
					{
					while(test.map.isWall(nowX+goX[now%4],nowY+goY[now%4]) == true)
					{
						now = (int)Math.floor( Math.random()*4);
					}
					nowX+=goX[now%4];
					nowY+=goY[now%4];
					if(now == 3)
						test.mapView.setLabelIcon(new Point(nowX,nowY), "ghost/test2.png");
					else
						test.mapView.setLabelIcon(new Point(nowX,nowY), "ghost/test1.png");
			
					try {
						t.sleep(150);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					test.mapView.setLabelIcon(new Point(nowX,nowY), "map/img/floor.jpg");
				}
				}
			});
			
			Thread ss = new Thread( new Runnable(){
				int nowX = 21;int nowY = 12;
				int now = 0;
				@Override
				public void run()
				{
					Thread t = new Thread();
					while(true)
					{
					while(test.map.isWall(nowX+goX[now%4],nowY+goY[now%4]) == true)
					{
						now = (int)Math.floor( Math.random()*4);
					}
					nowX+=goX[now%4];
					nowY+=goY[now%4];
					if(now == 3)
						test.mapView.setLabelIcon(new Point(nowX,nowY), "ghost/test2.png");
					else
						test.mapView.setLabelIcon(new Point(nowX,nowY), "ghost/test1.png");
					try {
						t.sleep(150);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		
					test.mapView.setLabelIcon(new Point(nowX,nowY), "map/img/floor.jpg");
				}
				}
			
			});
			
			Thread oo = new Thread( new Runnable(){
				int nowX = 21;int nowY = 12;
				int now = 0;
				@Override
				public void run()
				{
					Thread t = new Thread();
					while(true)
					{
					while(test.map.isWall(nowX+goX[now%4],nowY+goY[now%4]) == true)
					{
						now = (int)Math.floor( Math.random()*4);
					}
					nowX+=goX[now%4];
					nowY+=goY[now%4];
					if(now == 3)
						test.mapView.setLabelIcon(new Point(nowX,nowY), "ghost/test2.png");
					else
						test.mapView.setLabelIcon(new Point(nowX,nowY), "ghost/test1.png");
					try {
						t.sleep(150);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		
					test.mapView.setLabelIcon(new Point(nowX,nowY), "map/img/floor.jpg");
				}
				}
			
			});
			
			Thread kk = new Thread( new Runnable(){
				int nowX = 21;int nowY = 12;
				int now = 0;
				@Override
				public void run()
				{
					Thread t = new Thread();
					while(true)
					{
					while(test.map.isWall(nowX+goX[now%4],nowY+goY[now%4]) == true)
					{
						now = (int)Math.floor( Math.random()*4);
					}
					nowX+=goX[now%4];
					nowY+=goY[now%4];
					if(now == 3)
						test.mapView.setLabelIcon(new Point(nowX,nowY), "ghost/test2.png");
					else
						test.mapView.setLabelIcon(new Point(nowX,nowY), "ghost/test1.png");
					try {
						t.sleep(150);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		
					test.mapView.setLabelIcon(new Point(nowX,nowY), "map/img/floor.jpg");
				}
				}
			
			});
			
				tt.start();
				ss.start();
				oo.start();
				kk.start();
			Timer f = new Timer();
			f.schedule(test.new FoodGenerate(),1000,25);
			
			Timer ff = new Timer();
			ff.schedule(test.new FoodGenerate(),500,25);
			*/
			
	


	}
}