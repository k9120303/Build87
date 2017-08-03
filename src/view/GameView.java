package view;


import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;


import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.MapController;
import controller.PlayerController;
import game.map.Map;
import set.GameSet;
import unit.AutomaticWall;
import unit.Border;
import unit.Entity;
import unit.GhostLair;
import unit.InvinciblePlayer;
import unit.JumperPlayer;
import unit.Player;


public class GameView extends JPanel
{
	
	public MapView mapView;
	private Player player1;
	private Player player2;
	
	
	public GameView(PlayerStateView playerStateView1,PlayerStateView playerStateView2,MapView mapView)
	{
		super();
		
		setSize(GameSet.playerViewWidth*2 + GameSet.viewWidth,GameSet.viewHeight + 25);
		setLayout(null);
		
		
		this.mapView = mapView;
		this.player1 = player1;
		this.player2 = player2;
		
	
		
		this.mapView.setLocation(GameSet.playerViewWidth,0);
		playerStateView2.setLocation(GameSet.playerViewWidth+GameSet.viewWidth,0);
		
		
		add(playerStateView1);
		add(this.mapView);
		add(playerStateView2);
		setVisible(true);
	}
	
	@Override
	public void paintComponents(Graphics g)
	{
		super.paintComponents(g);
	}


	public void setFoodView(int x,int y)
	{
		mapView.setFoodView(x, y);
	} 
	/*設定食物圖片*/
	
	public void setLabelIcon(int i,int j,String path)
	{
		mapView.setLabelIcon(i, j,path);
	} 
	/*準備某格圖片*/
	public void setLabelIcon(Point point,String path)
	{
		mapView.setLabelIcon(point,path);
	}
	/*準備某格圖片*/
	public static void main(String args[])
	{
		
		
		Player s = new JumperPlayer(2,2);
		Player t = new InvinciblePlayer(2,2);
		//GameView test = new GameView(s,t,mapView);
		
	}
	
	
};
