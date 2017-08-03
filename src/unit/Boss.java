package unit;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import controller.PlayerController;
import game.map.Map;
import set.GameSet;
import unit.Character.DIRECTION;
import unit.Ghost.TYPE;

public class Boss extends Ghost{
	private Player player1;
	private Player player2;
	private static final int RANGE = 3;
	
	public Boss(int xpos, int ypos, TYPE ghostType) {
		super(xpos, ypos, ghostType);
		// TODO Auto-generated constructor stub
	}

	public Boss(Point point,TYPE ghostType,PlayerController player1,PlayerController player2)
	{
		super(point, ghostType,player1,player2);
		setGhostType(ghostType);
		setSpeed(175);
		setImgPath("ghost/Boss");
		this.player1 = player1.getPlayer();
		this.player2 = player2.getPlayer();
	}
	
	@Override
	public void ghostSkill() {
		//if touch player then player die
				if(super.getXpos() == player2.getXpos() && super.getYpos() ==  player2.getYpos()){
					super.playerVoice("hurt");
					player2.setHP(player2.getHP() - 50);//reduce player's oxygen much
				}
				if(super.getXpos() == player1.getXpos() && super.getYpos() ==  player1.getYpos()){
					super.playerVoice("hurt");
					player1.setHP(player1.getHP() - 50);//reduce player's oxygen much
				}
				
		//in a certain range , player's oxygen reduce
				for(int i = super.getXpos() - RANGE;i < super.getXpos() + RANGE ; i++){
					for(int j = super.getYpos() - RANGE; j < super.getYpos() + RANGE ; j++){
						if(i == player2.getXpos() && j ==  player2.getYpos()){
							player2.setHP(player2.getHP() - 20);//reduce player's oxygen much
						}
						if(i == player1.getXpos() && j ==  player1.getYpos()){
							player1.setHP(player1.getHP() - 20);//reduce player's oxygen much
						}
					}
				}
				
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
			setImgPath("ghost/Boss");
		}
			
		setXpos(xNext);
		setYpos(yNext);
		ghostSkill();
	}
	
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
