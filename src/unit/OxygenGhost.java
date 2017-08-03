package unit;

import java.awt.Point;

import controller.PlayerController;
import unit.Entity.state;
import unit.Ghost.TYPE;

public class OxygenGhost extends Ghost{
	
	private Player player1;
	private Player player2;
	private static final int RANGE = 3;

	
	public OxygenGhost(int xpos, int ypos, TYPE ghostType) {
		super(xpos, ypos, ghostType);
		// TODO Auto-generated constructor stub
	}
	
	public OxygenGhost(Point point,TYPE ghostType,PlayerController player1,PlayerController player2)
	{
		super(point, ghostType,player1,player2);
		setGhostType(ghostType);
		setImgPath("ghost/OxygenGhost");
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
	
}
