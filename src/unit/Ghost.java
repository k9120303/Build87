package unit;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Random;
import java.util.Vector;

import controller.PlayerController;
import game.map.Map;
import set.GameSet;

//the class of Normal ghhost or can be override by diff kind
public class Ghost extends Character{
	
	public enum TYPE{Normal,WallPass,Oxygen,SpeedUp,Boss};	
	private TYPE ghostType;
	private int xNext;
	private int yNext;
	private String imgPath;
	private DIRECTION preDir = super.getDirection();
	private Player player1;
	private Player player2;
	private javazoom.jl.player.Player music = null;
	/*
	private int []histroyX = new int[10];
	private int []histroyY = new int[10];
	private int count = 0;//記錄數到第幾個history
	private boolean asc = true;
	*/
	
	public Ghost(int xpos, int ypos,TYPE ghostType)
	{
		super(xpos, ypos, state.PLAIN);
		speed = GameSet.GHOSTSPEED;
		this.ghostType = ghostType;
		setImgPath("ghost/Normal");
	}
	
	public Ghost(Point point,TYPE ghostType,PlayerController player1,PlayerController player2)
	{
		super(point, state.PLAIN);
		this.ghostType = ghostType;
		setImgPath("ghost/Normal");
		this.player1 = player1.getPlayer();
		this.player2 = player2.getPlayer();
	}
	
	public void setImgPath(String type)
	{
		imgPath = type;
	}
	
	public String getImgPath()
	{
		return imgPath;
	}
	
	
	
	public void setGhostType(TYPE ghostType)
	{
		this.ghostType = ghostType;
	}

	public TYPE getGhostType()
	{
		return ghostType;
	}
	
	/*自動走路*/
	public void autoMove(Map map)
	{
		Vector<DIRECTION> afterSearch = searchFourDirection(map);
	
		xNext = getXpos();
		yNext = getYpos();
		
		/*如果正在走的路前面不是障礙物，增加走到這條路上的可能性*/
		if(searchFaceIsWall(map,direction)==false)
		{
			for(int i=0;i<10;i++)//數字表所佔的比例
				afterSearch.add(direction);
		}
		//如果可以走的話沿著原本方向走
		if(direction == preDir && !map.isWall(xNext, yNext) && !searchFaceIsWall(map, direction));
		else	direction = afterSearch.get(new Random().nextInt(afterSearch.size()));
		//在可以移動的面相隨機找一個
			
		//preDir 紀錄上一次的方向
		preDir = direction ;
		
		/*若有玩家在視線範圍就要去追*/
		direction = searchIfPlayer(map,direction);
		if(!map.isWall(xNext, yNext) && !searchFaceIsWall(map, direction));
		else	direction = preDir;
		
		preDir = direction ;
		
			
		//利用更改過後的方向進行移動
		switch(direction)
		{
			case UP:
				yNext -= 1;
				break;
			case DOWN: 
				yNext += 1;
				break;
			case LEFT:
				xNext -= 1;
				break;
			default:
				xNext += 1;
				break;
		}
		
		/*
		if(isDeadLock(count)){
			count = (asc) ? count - 1 :count + 1;
			setXpos(histroyX[count]);
			setXpos(histroyY[count]);
		}else{
			histroyX[count] = xNext;
			histroyY[count] = yNext;
			if(count == 9) asc = false;
			if (count == 0) asc = true;
			count = (asc) ? count + 1 :count - 1;
			setXpos(xNext);
			setYpos(yNext);
		}*/
		setXpos(xNext);
		setYpos(yNext);
		ghostSkill();
	}
	
	/*將四個面向裡不是牆壁的塞進選擇列表裡*/ 
	public Vector<DIRECTION> searchFourDirection(Map map)
	{
		Vector<DIRECTION> temp = new Vector<DIRECTION>();
		temp.clear();
		if(searchFaceIsWall(map,DIRECTION.UP)==false)
			if(preDir!=DIRECTION.UP)
				temp.add(DIRECTION.UP);
		if(searchFaceIsWall(map,DIRECTION.DOWN)==false)
			if(preDir!=DIRECTION.DOWN)
				temp.add(DIRECTION.DOWN);
		if(searchFaceIsWall(map,DIRECTION.LEFT)==false)
			if(preDir!=DIRECTION.LEFT)
				temp.add(DIRECTION.LEFT);
		if(searchFaceIsWall(map,DIRECTION.RIGHT)==false)
			if(preDir!=DIRECTION.RIGHT)
				temp.add(DIRECTION.RIGHT);
		
		return temp;
	}
	
	
	/*回傳傳入的面向是不是牆壁*/
	public boolean searchFaceIsWall(Map map,DIRECTION direction)
	{
		int xtemp = getXpos();
		int ytemp = getYpos();
		switch(direction)
		{
			case UP:
				ytemp-=1;
				break;
			case DOWN: 
				ytemp+=1;
				break;
			case LEFT:
				xtemp-=1;
				break;
			default:
				xtemp+=1;
				break;
		}
		if(map.isWall(xtemp,ytemp)==true)
		{
			return true;
		}
		return false;
		
	}
	
		
	public void ghostSkill() {
		if(super.getXpos() == player2.getXpos() && super.getYpos() ==  player2.getYpos()){
			player2.setHP(player2.getHP() - 50);//reduce player's oxygen much
			playerVoice("hurt");
		}
		if(super.getXpos() == player1.getXpos() && super.getYpos() ==  player1.getYpos()){
			player1.setHP(player1.getHP() - 50);//reduce player's oxygen much
			playerVoice("hurt");
		}
	}
	
	/*若視線範圍有玩家，回傳對應之移動方向*/
	public DIRECTION searchIfPlayer(Map map,DIRECTION direction){
		
		if(getYpos() == player1.getYpos()){//面向上或下時若左右有player1
			if(getXpos() > player1.getXpos()  && !searchFaceIsWall(map,DIRECTION.DOWN))
				return DIRECTION.LEFT;
			if(getXpos() < player1.getXpos() && !searchFaceIsWall(map,DIRECTION.UP))
				return DIRECTION.RIGHT;	
		}
		
		if(getYpos() == player2.getYpos()){//面向上或下時若左右有player2
			if(getXpos() > player2.getXpos()  && !searchFaceIsWall(map,DIRECTION.DOWN))
				return DIRECTION.LEFT;
			if(getXpos() < player2.getXpos() && !searchFaceIsWall(map,DIRECTION.UP))
				return DIRECTION.RIGHT;	
		}
		
		if(getXpos() == player2.getXpos()){//面向左或右時若上下有player2
			if(getYpos() > player2.getYpos() && !searchFaceIsWall(map,DIRECTION.LEFT))
				return DIRECTION.UP;
			if(getYpos() < player2.getYpos() && !searchFaceIsWall(map,DIRECTION.RIGHT))
				return DIRECTION.DOWN;	
		}
		
		if(getXpos() == player1.getXpos()){//面向左或右時若上下有player1
			if(getYpos() > player1.getYpos() && !searchFaceIsWall(map,DIRECTION.LEFT))
				return DIRECTION.UP;
			if(getYpos() < player1.getYpos() && !searchFaceIsWall(map,DIRECTION.RIGHT))
				return DIRECTION.DOWN;	
		}

		return direction;
	}
	
	public int getXNext()
	{
		return xNext;
	}
	
	public int getYNext()
	{
		return yNext;
	}
	
	/*
	public Boolean isDeadLock(int count){
		int equalTimes = 0;
		count = (asc) ? count : 10-count;
		System.out.print(count + "\n");
		for(int i = 0;i < count; i++){
			for(int j = i+1;j < count; j++){
				if(histroyX[i] == histroyX[j] && histroyY[i] == histroyY[j]) equalTimes++;
			}
		}
		return (equalTimes>2) ? true : false; 
	}*/
	
	public void playerVoice(String voice){
		try
		{
			FileInputStream fis = new FileInputStream(voice +".mp3");
			BufferedInputStream bis = new BufferedInputStream(fis);
		    music = new javazoom.jl.player.Player(bis);
		    
		} catch (Exception e)
		{
			System.out.println("Problem playing file " + voice + ".mp3");
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
