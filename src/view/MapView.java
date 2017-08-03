package view;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import beforePlay.Frame;
import game.map.Map;
import set.GameSet;
import unit.AutomaticWall;
import unit.Border;
import unit.Entity;
import unit.GhostLair;

public class MapView  extends JPanel
{
	private JLabel temp[];
	
	public MapView(ArrayList<ArrayList<Entity>> location)
	{
		setSize(GameSet.viewWidth,GameSet.viewHeight);
		setLayout(new GridLayout(GameSet.border,GameSet.border));
		viewInit(location);
	}

	public void viewInit(ArrayList<ArrayList<Entity>> location)
	{
		temp = new JLabel[GameSet.border*GameSet.border];
		
		for(int i=0;i<GameSet.border;i++)
			for(int j=0;j<GameSet.border;j++)
			{
				temp[i*GameSet.border + j] = new JLabel();
				
				if(location.get(i).get(j) instanceof  AutomaticWall)
					setLabelIcon(i,j,"map/img/wall.png");
				else if(location.get(i).get(j) instanceof GhostLair)
					setLabelIcon(i,j,"map/img/ghostLair.png");
				else if(location.get(i).get(j) instanceof Border)
					setLabelIcon(i,j,"map/img/border.jpg");
				else 
					setLabelIcon(i,j,"map/img/floor.jpg");
				
				add(temp[i*GameSet.border + j]);
			}
		
	}
	/*初始化地圖介面*/
	public static ImageIcon changeImg(String path)
	{
		
		ImageIcon imageIcon = new ImageIcon(path);
		Image img = (imageIcon.getImage()).getScaledInstance(GameSet.viewWidth / GameSet.border, GameSet.viewHeight / GameSet.border, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}
	 /*將圖片的像素調成適合的像素*/
	public void setFoodView(int x,int y)
	{
		setLabelIcon(x,y,"map/img/food.png");
	} 
	/*設定食物圖片*/
	public void setFoodView(Point point)
	{
		setFoodView((int)point.getX(),(int)point.getX());
	} 
	/*設定食物圖片*/
	public void setLabelIcon(int i,int j,String path)
	{
		temp[i*GameSet.border + j].setIcon(changeImg(path));
	} 
	/*準備某格圖片*/
	public void setLabelIcon(Point point,String path)
	{
		temp[(int)(point.getX())*GameSet.border + (int)(point.getY())].setIcon(changeImg(path));
	}
	/*準備某格圖片*/
	public void DeleteFoodView(int x,int y)
	{
		setLabelIcon(x,y,"map/img/floor.jpg");
	}
	
	public static void main(String args[])
	{
		JFrame frame = new JFrame();
		frame.add(new MapView(new Map().getAllLocation()));
		frame.setSize(GameSet.viewWidth,GameSet.viewHeight);
		frame.setVisible(true);
	}
		
}
