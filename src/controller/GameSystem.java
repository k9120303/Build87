package controller;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import game.map.Map;
import set.GameSet;
import unit.Ghost;
import unit.Player;
import unit.Character.DIRECTION;
import unit.Ghost.TYPE;
import unit.InvinciblePlayer;
import unit.JumperPlayer;
import view.GameView;
import view.MapView;
import view.PlayerStateView;

public class GameSystem
{
	private Map map;
	public MapView mapView;
	private MapController mapController;
	
	private Player player1;
	private Player player2;
	private PlayerController playerController1;
	private PlayerController playerController2;
	private Thread timeThread;
	
	private Ghost ghost[];
	private GhostController ghostController[];
	
	public  GameView gameView;
	private PlayerStateView playerStateView1;
	private PlayerStateView playerStateView2;
	private javazoom.jl.player.Player music = null;
	private JButton back;
	private JLabel whoWin = new JLabel();
	private static int num = 0;
	
	public GameSystem(Player player1,Player player2,JButton back)
	{
		map = new Map();
		mapView = new MapView(map.getAllLocation());
		mapController = new MapController(map,mapView);
		
		
		this.back=back;
		playerStateView1 = new PlayerStateView("player1",player1);
		playerStateView2 = new PlayerStateView("player2",player2);
		gameView = new GameView(playerStateView1,playerStateView2,mapView);
		this.player1 = player1;
		this.player2 = player2;
		playerController1 = new PlayerController(player1,player2,mapController,1);
		playerController2 = new PlayerController(player2,player1,mapController,2);
		gameStart();
		
	
	}
	
    public void gameStart(){
    	timeThread=new Thread(){
    		Timer victory = new Timer();
    		
			@Override
			public void run() {
	
				Thread ghostTime = new Thread(){
					public void run(){
						if(player1.getHP()!=0&&player2.getHP()!=0){
						ghostController = new GhostController[GameSet.GHOSTNUMBER];
						ghost = new Ghost[GameSet.GHOSTNUMBER];	
						}
						for(int i=0;i<num;i++){
							ghostController[i].setMovable(false);
						}
						
					}
				};
							
				ghostTime.start();
				try
				{
					
					FileInputStream fis = new FileInputStream("Naruto.mp3");
					BufferedInputStream bis = new BufferedInputStream(fis);
				    music = new javazoom.jl.player.Player(bis);
				    
				} catch (Exception e)
				{
					System.out.println("Problem playing file " + "Naruto.mp3");
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

			
			
			Timer FoodGenerate1 = new Timer();
			Timer FoodGenerate2 = new Timer();

			FoodGenerate1.schedule(
					new TimerTask()
					{

						@Override
						public void run() {
							Point point = mapController.FoodGenerate();
							try {
								Thread.sleep(10000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							mapController.FoodDelete(point);
							
						}
						
						
					},5000,15000);
			
			FoodGenerate2.schedule(
					new TimerTask()
					{
						@Override
						public void run() {
							Point point = mapController.FoodGenerate();
							try {
								Thread.sleep(10000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							mapController.FoodDelete(point);
							
						}
						
						
					},10000,15000);
			
			
			Timer Reborn = new Timer();
			Reborn.schedule(
					new TimerTask()
					{
						@Override
						public void run() 
						{
							if(player1.getHP()>0&&player2.getHP()>0){
							int randomNum = new Random().nextInt(GameSet.ghostLairLocation.length);
							ghostController[num] = new GhostController(mapController,num,playerController1,playerController2);
							ghostController[num].setMovable(true);
							num++;
							}
							else{
								
								try {
									ghostTime.sleep(99999999);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
							
					},10,20000
				);
			
		
			
			Timer refresh = new Timer();
			refresh.schedule(
					new TimerTask()
					{

						@Override
						public void run() {
							playerStateView1.repaint();
							playerStateView2.repaint();
						}
						
					},0,50);
	victory.schedule(new TimerTask() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					// TODO Auto-generated method stub
				    //System.out.println(player1.getHP());
				    //System.out.println(player2.getHP());
				    if(player1.getHP()==0){
				    	whoWin.setText("玩家2勝利");
				    	System.out.println("玩家2勝利");
				    	FoodGenerate1.cancel();
				    	FoodGenerate2.cancel();
				    	ghostTime.interrupt();
				    	musicThread.interrupt();
				    	//refresh.cancel();
				    	for(int i=0;i<num;i++){
				    		ghostController[i].setMovable(false);
				    	}
				    	Reborn.cancel();
				    	musicThread.stop();
				    	cancel();
				    	num=0;
				    	playerStateView1.add(back);
				    	playerStateView1.add(whoWin);
				    	timeThread.stop();
				    	stop();
				    }
				    else if(player2.getHP()==0){
				    	whoWin.setText("玩家2勝利");
				    	System.out.println("玩家1勝利");
				    	FoodGenerate1.cancel();
				    	FoodGenerate2.cancel();
				    	ghostTime.interrupt();
				    	musicThread.stop();
				    	for(int i=0;i<num;i++){
				    		ghostController[i].setMovable(false);
				    	}
				    	Reborn.cancel();
				    	
				    	cancel();
				    	//refresh.cancel();
				    	
				    	num=0;
				    	playerStateView2.add(back);
				    	playerStateView2.add(whoWin);
				    	timeThread.stop();
				    	
				    	stop();
				    	
				    }
				    //playerStateView1.add(back);
				}
			},1,1000 );
			
			};
			
		};
		
		Thread thread=new Thread(){
			@Override
			public void run(){
				try {
					timeThread.sleep(3000);
					timeThread.start();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//sgameSystem.timeThread.start();
			}
		};
		try {
			thread.sleep(1000);
			thread.run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
    }
	public void randomSetWall()
	{
		
		int i,j;
		do
		{
			i = new Random().nextInt(GameSet.border);
			j = new Random().nextInt(GameSet.border);
		}
		while(mapController.map.isWall(i,j)==true);
		mapController.addPlayerWall(i,j,GameSet.constructTime);
		final int ii = i;
		final int jj = j;
		Timer destory = new Timer();
		destory.schedule(
				new TimerTask()
				{
					@Override
					public void run()
					{
						mapController.destoryPlayerWall(ii, jj,GameSet.constructTime);
					}
				}
				,3000);
		
	}
	
	public static void main(String args[])
	{
		JFrame s  = new JFrame();
		//GameSystem gameSystem = new GameSystem(new JumperPlayer(3,3),new InvinciblePlayer(22,22));
		//s.setSize(GameSet.playerViewWidth*2 + GameSet.viewWidth,GameSet.viewHeight + 25);
		//s.add(gameSystem.gameView);
		s.setResizable(false);
		s.setVisible(true);
		
		
	}
}
