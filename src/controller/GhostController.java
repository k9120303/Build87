package controller;
import unit.Ghost;

import java.awt.Event;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import game.map.Map;
import set.GameSet;
import unit.Boss;
import unit.Entity.state;
import unit.Ghost.TYPE;
import unit.OxygenGhost;
import unit.SpeedUpGhost;
import unit.WallPassGhost;
import view.GameView;
import view.MapView;

public class GhostController
{
	private MapController mapController;
	private Ghost ghost;
	private boolean movable=true;
	private javazoom.jl.player.Player music = null;
	 
	GhostController(MapController mapController,int num,PlayerController player1,PlayerController player2)
	{
		this.mapController = mapController;
		int randomNum = new Random().nextInt(GameSet.ghostLairLocation.length);
		switch(num){
			case 0:
				ghost = new Ghost(GameSet.ghostLairLocation[randomNum], TYPE.Normal,player1,player2);
				break;
			case 1:
				ghost = new OxygenGhost(GameSet.ghostLairLocation[randomNum], TYPE.Oxygen,player1,player2);
				ghostMusic("Normal");
				break;
			case 2:
				ghost = new SpeedUpGhost(GameSet.ghostLairLocation[randomNum], TYPE.SpeedUp,player1,player2);
				ghostMusic("SpeedUp");
				break;
			case 3:
				ghost = new WallPassGhost(GameSet.ghostLairLocation[randomNum], TYPE.WallPass,player1,player2);
				ghostMusic("WallPass");
				break;
			default:
				ghost = new Boss(GameSet.ghostLairLocation[randomNum], TYPE.Boss,player1,player2);
				ghostMusic("Boss");
				break;
		}
		mapController.mapView.setLabelIcon(ghost.getXpos(), ghost.getYpos(),ghost.getImgPath()+"Left.png");
		moveTimer();
	}
	
	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	private void moveTimer()
	{
		Timer timer = new Timer();
		GhostMove t = new GhostMove();
		timer.schedule(t ,10,300-ghost.getSpeed());
	}
	
	public class GhostMove extends TimerTask
	{
		@Override
		public void run()
		{
			if(mapController.map.isFood(ghost.getXpos(), ghost.getYpos()))
				mapController.mapView.setLabelIcon(ghost.getXpos(), ghost.getYpos(),"map/img/food.png");
			else if(!mapController.map.isWall(ghost.getXpos(), ghost.getYpos())) //如果不是牆壁設成地板
				mapController.mapView.setLabelIcon(ghost.getXpos(), ghost.getYpos(),"map/img/floor.jpg");
			else if(isReborn(ghost.getXpos(), ghost.getYpos())) //如果是重生點就設成重生點
				mapController.mapView.setLabelIcon(ghost.getXpos(), ghost.getYpos(),"map/img/ghostLair.png");
			
			else//設成牆壁，穿牆鬼和魔王專用	
				mapController.mapView.setLabelIcon(ghost.getXpos(), ghost.getYpos(),"map/img/wall.png");
			if(movable==true)
				ghost.autoMove(mapController.map);
			mapController.mapView.setLabelIcon(ghost.getXpos(), ghost.getYpos(),ghost.getImgPath()+"Left.png");
		
		}
		
		public Boolean isReborn(int x,int y){
			if(x == 1 && y == 1) return true;
			if(x == 1 && y == 24) return true;
			if(x == 24 && y == 1) return true;
			if(x == 24 && y == 24) return true;
			return false;
		}
	}
	
	public void ghostMusic(String ghostVoice){
		try
		{
			FileInputStream fis = new FileInputStream(ghostVoice +"Ghost.mp3");
			BufferedInputStream bis = new BufferedInputStream(fis);
		    music = new javazoom.jl.player.Player(bis);
		    
		} catch (Exception e)
		{
			System.out.println("Problem playing file " + ghostVoice + "Ghost.mp3");
			System.out.println(e);
		}

		// run in new thread to play in background
		Thread musicThread=new Thread() {
			public void run ()
			{
				try
				{
					music.play();
				} catch (Exception e)
				{
					System.out.println(e);
				}
			}
		};
		musicThread.start();
	}
	
	
}
