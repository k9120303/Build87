package unit;
//技能 : 自我回復  CD 15秒

import java.awt.Point;
import java.util.ArrayList;

import game.map.Map;

public class ReCoverPlayer extends Player
{

	public ReCoverPlayer(int xpos, int ypos) 
	{
		super(xpos, ypos);
		setPlayerName("培倫");
		setSkillPath();
		
	}

	public ReCoverPlayer(Point point) 
	{
		super(point);
		setPlayerName("培倫");
		setSkillPath();
	}

	@Override
	public void skill(Map map,Player otherplayer) {
		this.setSkillTime(15);
		this.setNowSkillTime(this.getSkillTime());
		int DreaseHP=(600-this.getHP());
		double getHP=(double)DreaseHP*0.7;
		this.setHP(this.getHP()+(int)getHP);
	}

	@Override
	public void setImgPath() 
	{
		imgPath[0] = ("Player\\培倫\\背面.jpg");
		imgPath[1] = ("Player\\培倫\\正面.jpg");
		imgPath[2] = ("Player\\培倫\\左轉.jpg");
		imgPath[3] = ("Player\\培倫\\右轉.jpg");
		
	}


	@Override
	public ArrayList<String> setIntroduction() {
		ArrayList<String> s = new ArrayList<String>();
		s.add("技能 : 自我療癒           CD : 15秒");
		s.add("持續時間 : 無");
		s.add("技能說明 : 用刀割自己的肥肉，吃下去獲得營養。");
		return s;
	}

	

}
