package unit;

import java.awt.Point;
import set.GameSet;



public  class Player  extends Character{
	
	public enum PLAYER_TYPE{子賢,老大,培倫,俊棋,侑錦};	
	private PLAYER_TYPE playerType;
	private int xNext;
	private int yNext;
	private boolean moveable=true;
	private boolean isHert=true;
	private int HP=100;
	private String imgPath;
	public Player(int xpos, int ypos,PLAYER_TYPE playerType)
	{
		super(xpos, ypos, state.PLAIN);
		speed = GameSet.PLAYERSPEED;
		this.playerType = playerType;
		setImgPath();
	}
	
	public Player(Point point,PLAYER_TYPE playerType)
	{
		super(point, state.PLAIN);
		this.playerType = playerType;
		setImgPath();
	}
	
	public void setImgPath()
	{
		imgPath = "Player/"+playerType+"/";
	}
	
	public String getImgPath()
	{
		return imgPath;
	}
	
	public void setplayerType(PLAYER_TYPE playerType)
	{
		this.playerType = playerType;
	}

	public PLAYER_TYPE getplayerType()
	{
		return playerType;
	}
	
	public int getXNext()
	{
		return xNext;
	}
	public void setXNext(int i){
		xNext+=i;
	}
	public int getYNext()
	{
		return yNext;
	}
	public void setYNext(int i){
		yNext+=i;
	}
	public int getHP(){
		return HP;
	}
	public void setHP(int i){
		HP+=i;
	}
	public void setMoveable(boolean i){
		System.out.print(i);
		moveable=i;
	}
	public boolean getMoveable(){
		return moveable;
	}
	public void setIshurt(boolean i){
		moveable=i;
	}
	public boolean getIshurt(){
		return moveable;
	}
}
