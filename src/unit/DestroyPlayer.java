package unit;
//技能 : 自我回復  CD 15秒   能夠回復已失去生命的70%

import java.awt.Point;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import game.map.Map;

public class DestroyPlayer extends Player 
{

	public DestroyPlayer(int xpos, int ypos) 
	{
		super(xpos, ypos);
		setPlayerName("又緊");
		setSkillPath();
		
	}

	public DestroyPlayer(Point point) 
	{
		super(point);
		
		setPlayerName("又緊");
		setSkillPath();
	}
	Player getplayer()
	{
		return this;
	}
	
	@Override
	public void skill(Map map,Player otherplayer) {
		this.setSkillTime(15);
		setDestroyerWallSpeed( getDestroyerWallSpeed() / 5);
		setConstorWallSpeed( getConstorWallSpeed() / 5);
		this.setNowSkillTime(this.getSkillTime());
		Timer Reborn = new Timer();
		Reborn.schedule(
				new TimerTask()
				{
					@Override
					public void run() 
					{
						getplayer().setDestroyerWallSpeed(getplayer().getDestroyerWallSpeed()*5);
						getplayer().setConstorWallSpeed(getplayer().getConstorWallSpeed()*5);
						}	
				},5000
			);
	}

	@Override
	public void setImgPath() 
	{
		imgPath[0] = ("Player\\又緊\\背面.jpg");
		imgPath[1] = ("Player\\又緊\\正面.jpg");
		imgPath[2] = ("Player\\又緊\\左轉.jpg");
		imgPath[3] = ("Player\\又緊\\右轉.jpg");
		imgPath[4] = ("Player\\又緊\\死亡.jpg");
	}

	@Override
	public ArrayList<String> setIntroduction() {
		ArrayList<String> s = new ArrayList<String>();
		s.add("技能 : 爐心超載           CD : 15秒");
		s.add("持續時間 : 5秒");
		s.add("技能說明 : 吃下自己特製的威而鋼，使用神秘的硬梆梆武器拆牆");
		return s;
	}


}
