package unit;
//技能 : 翻牆  CD 10秒

import java.awt.Point;
import java.util.ArrayList;

import game.map.Map;

public class JumperPlayer extends Player
{

	public JumperPlayer(int xpos, int ypos) 
	{
		super(xpos, ypos);
		setPlayerName("子賢");
		setSkillPath();
	}

	public JumperPlayer(Point point) 
	{
		super(point);
		setPlayerName("子賢");
		setSkillPath();
	}

	@Override
	public void skill(Map map,Player otherplayer) {
		this.setSkillTime(10);
		// TODO Auto-generated method stub
		DIRECTION direction = this.getDirection();
		if (direction.equals(DIRECTION.UP)) {
			if(map.isWall(this.getXpos() - 1,this.getYpos())==true && map.isWall(this.getXpos() - 2,this.getYpos())==false)
				{this.setXNext(-2);this.setNowSkillTime(this.getSkillTime());}
		} else if (direction.equals(DIRECTION.DOWN)) {
			if(map.isWall(this.getXpos() + 1,this.getYpos())==true && map.isWall(this.getXpos() + 2,this.getYpos())==false)
			{this.setXNext(2);this.setNowSkillTime(this.getSkillTime());}
		} else if (direction.equals(DIRECTION.LEFT)) {
			if(map.isWall(this.getXpos(),this.getYpos()-1)==true && map.isWall(this.getXpos(),this.getYpos()-2)==false)
			{this.setYNext(-2);this.setNowSkillTime(this.getSkillTime());}
		} else if (direction.equals(DIRECTION.RIGHT)) {
			if(map.isWall(this.getXpos(),this.getYpos()+1)==true && map.isWall(this.getXpos(),this.getYpos()+2)==false)
			{this.setYNext(2);this.setNowSkillTime(this.getSkillTime());}
		} else {
			this.setXNext(this.getXpos());
			this.setYNext(this.getYpos());
		}
	}


	@Override
	public void setImgPath() {
		imgPath[0] = ("Player\\子賢\\背面.jpg");
		imgPath[1] = ("Player\\子賢\\正面.jpg");
		imgPath[2] = ("Player\\子賢\\左轉.jpg");
		imgPath[3] = ("Player\\子賢\\右轉.jpg");
		imgPath[4] = ("Player\\子賢\\死亡.jpg");
	}



	@Override
	public ArrayList<String> setIntroduction() {
		ArrayList<String> s = new ArrayList<String>();
		s.add("技能 : 狗急跳牆            CD : 10秒");
		s.add("持續時間 : 無");
		s.add("技能說明 : 只要是狗，都會跳牆，尤其是馬子狗。因為腿短，只能跳一格牆。");
		return s;
	}



}
