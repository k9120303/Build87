package unit;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;



import set.GameSet;
import unit.Character.DIRECTION;

public abstract class Player extends Character implements playerSet {
	public enum STATE {
		ALIVE, DEAD, INVINCIBLE
	};

	private STATE playerState = STATE.ALIVE;
	public String skillImgPath;
	private int xNext;
	private int yNext;
	private String name;
	private boolean moveable = true;
	private int HP = MAXHP;
	private static int MAXHP = 600;
	private boolean isConfuse = false;
	private boolean isHurt = true;
	protected String[] imgPath; // top down left right
	private int DestroyerWallSpeed = GameSet.destoryTime;
	private int ConstorWallSpeed = GameSet.constructTime;
	private ArrayList<PlayerWall> playerWallsCounter = new ArrayList<PlayerWall>();
	public static int skillTime;
	private int nowSkillTime;
	private javazoom.jl.player.Player music;
	

	
	public void playMusic(String sound){
			try
			{
				FileInputStream fis = new FileInputStream(sound +".mp3");
				BufferedInputStream bis = new BufferedInputStream(fis);
			    music = new javazoom.jl.player.Player(bis);
			    
			} catch (Exception e)
			{
				System.out.println("Problem playing file " + sound + ".mp3");
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
	
	public void setNowSkillTime(int i) {
		if (i < 0)
			nowSkillTime = 0;
		else
			nowSkillTime = i;
	}

	public int getNowSkillTime() {
		return nowSkillTime;
	}

	public void setSkillTime(int i) {
		
			skillTime = i;
	}

	public int getSkillTime() {
		return skillTime;
	}

	public boolean getIsConfuse() {
		return isConfuse;
	}

	public void setIsConfuse(boolean i) {
		isConfuse = i;
	}

	public void addPlayerWAllsCounter(int x, int y) {
		playerWallsCounter.add(new PlayerWall(x, y));
		
	}

	public void decreasePlayerWAllsCounter(int x, int y) {
		int index = 0;
		for (int i = 0; i < playerWallsCounter.size(); i++) {
			if (playerWallsCounter.get(i).getXpos() == x && playerWallsCounter.get(i).getYpos() == y) {
				index = i;
				break;
			}
		}
		playerWallsCounter.remove(index);
	}

	public ArrayList<PlayerWall> getPlayerWallsCounter() {
		return playerWallsCounter;
	}

	public STATE getPlayerState() {
		return playerState;
	}

	public void setPlayerState(STATE i) {
		playerState = i;
	}

	public int getConstorWallSpeed() {
	
		return ConstorWallSpeed;
	}

	public void setConstorWallSpeed(int i) {
		ConstorWallSpeed = i;
	}

	public int getDestroyerWallSpeed() {
		
		return DestroyerWallSpeed;
	}

	public void setDestroyerWallSpeed(int i) {
		DestroyerWallSpeed = i;
	}

	public boolean getIsHurt() {
		return isHurt;
	}

	public void setIsHurt(boolean i) {
		isHurt = i;
	}

	public Player(int xpos, int ypos) {
		super(xpos, ypos, state.PLAIN);
		imgPath = new String[5];
		setImgPath();
		speed = GameSet.PLAYERSPEED;
	}

	public Player(Point point) {
		super(point, state.PLAIN);
		imgPath = new String[5];
		setImgPath();
		speed = GameSet.PLAYERSPEED;
	}

	public void setPlayerName(String name) {
		this.name = name;
	}

	public String getPlayerName() {
		return name;
	}

	public String getImgPath() {
		if(getHP()==0)return imgPath[4];
		switch (direction) {
		case UP:
			return imgPath[0];
		case DOWN:
			return imgPath[1];
		case LEFT:
			return imgPath[2];
		case RIGHT:
			return imgPath[3];

		}
		return null;
	}

	public int getXNext() {
		return xNext;
	}

	public void setXNext(int i) {
		xNext += i;
	}

	public int getYNext() {
		return yNext;
	}

	public void setYNext(int i) {
		yNext += i;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int i) {
		if (i > HP) {
			if (i > MAXHP)
				HP = 600;
			else if (i < 0)
				HP = 0;
			else
				HP = i;
		} else {
			if (isHurt) {
				if (i < 0)
					HP = 0;
				else
					HP = i;
			}

		}
	}

	public void setMoveable(boolean isTrue) {
		moveable = isTrue;
	}

	public boolean getMoveable() {
		return moveable;
	}
	
	@Override
	public void setSkillPath()
	{
		skillImgPath =  "Player\\" + getPlayerName() + "\\skill.png";
		System.out.println(skillImgPath);
	}
	

}
