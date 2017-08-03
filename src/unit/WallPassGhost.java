package unit;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import javax.security.auth.DestroyFailedException;

import controller.PlayerController;
import game.map.Map;
import set.GameSet;
import unit.Character.DIRECTION;
import unit.Ghost.TYPE;

public class WallPassGhost extends Ghost{
	private Player player1;
	private Player player2;
	
	public WallPassGhost(int xpos, int ypos, TYPE ghostType) {
		super(xpos, ypos, ghostType);
		// TODO Auto-generated constructor stub
	}

	public WallPassGhost(Point point,TYPE ghostType,PlayerController player1,PlayerController player2)
	{
		super(point, ghostType,player1,player2);
		setGhostType(ghostType);
		setImgPath("ghost/WallPassGhost");
		this.player1 = player1.getPlayer();
		this.player2 = player2.getPlayer();
	}
	
	@Override 
	public void autoMove(Map map){
		Vector<DIRECTION> Search = new Vector<DIRECTION>();
		Search.clear();
		Random randomNumber = new Random();
		int xNext = 0,yNext = 0;
		Boolean change = true;
		Search.add(DIRECTION.DOWN);
		Search.add(DIRECTION.UP);
		Search.add(DIRECTION.LEFT);
		Search.add(DIRECTION.RIGHT);
		for(int i = 0;i < 3;i++)	Search.add(super.getDirection());
		
		
		while (change){
			direction = Search.get(randomNumber.nextInt(Search.size()));
			
			xNext = getXpos();
			yNext = getYpos();
		
			/*若有玩家在視線範圍就要去追*/
			direction = searchIfPlayer(map,direction);
			//利用更改過後的方向進行移動
		
			System.out.print(direction);
			
			switch(direction)
				{
					case UP:
						if((yNext-1) > 0) { yNext -= 1; change = false;}
						break;
					case DOWN: 
						if((yNext+1) < 25) { yNext += 1; change = false;}
						break;
					case LEFT:
						if((xNext-1) > 0) { xNext -= 1; change = false;}
						break;
					default:
						if((xNext+1) < 25) { xNext += 1; change = false;}
						break;
				}
		}
			
		if(map.isWall(xNext,yNext)){
			setImgPath("ghost/WallPassGhost");
		}
			
		setXpos(xNext);
		setYpos(yNext);
		super.ghostSkill();
	}
	
	
	/*若視線範圍有玩家，回傳對應之移動方向*/
	@Override
	public DIRECTION searchIfPlayer(Map map,DIRECTION direction){
		
		System.out.printf("in\n");
		
		if(getYpos() == player1.getYpos()){//面向上或下時若左右有player1
			if(getXpos() > player1.getXpos())
				return DIRECTION.LEFT;
			if(getXpos() < player1.getXpos())
				return DIRECTION.RIGHT;	
		}
		
		if(getYpos() == player2.getYpos()){//面向上或下時若左右有player2
			if(getXpos() > player2.getXpos())
				return DIRECTION.LEFT;
			if(getXpos() < player2.getXpos())
				return DIRECTION.RIGHT;	
		}
		
		if(getXpos() == player2.getXpos()){//面向左或右時若上下有player2
			if(getYpos() > player2.getYpos())
				return DIRECTION.UP;
			if(getYpos() < player2.getYpos())
				return DIRECTION.DOWN;	
		}
		
		if(getXpos() == player1.getXpos()){//面向左或右時若上下有player1
			if(getYpos() > player1.getYpos())
				return DIRECTION.UP;
			if(getYpos() < player1.getYpos())
				return DIRECTION.DOWN;	
		}

		System.out.printf("out\n");
		
		return direction;
	}
	
}
