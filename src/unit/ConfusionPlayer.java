package unit;
// 技能 : 方向混淆  CD 15秒

import java.awt.Point;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import game.map.Map;

public class ConfusionPlayer extends Player
{


	public ConfusionPlayer(int xpos, int ypos) 
	{
		
		super(xpos, ypos);

		setPlayerName("G7");
		
		setSkillPath();
	}

	public ConfusionPlayer(Point point) 
	{
		super(point);

		setPlayerName("G7");
		setSkillPath();
	}

	@Override
	public void skill(Map map,Player otherplayer) {
		// TODO Auto-generated method stub
		this.setSkillTime(15);
		otherplayer.setIsConfuse(true);
		this.setNowSkillTime(this.getSkillTime());
		Timer Reborn = new Timer();
		Reborn.schedule(
				new TimerTask()
				{
					@Override
					public void run() 
					{
						otherplayer.setIsConfuse(false);
						}	
				},3000
			);
	}

	@Override
	public void setImgPath() 
	{
		imgPath[0] = ("Player\\G7\\背面.jpg");
		imgPath[1] = ("Player\\G7\\正面.jpg");
		imgPath[2] = ("Player\\G7\\左轉.jpg");
		imgPath[3] = ("Player\\G7\\右轉.jpg");
		imgPath[4] = ("Player\\G7\\死亡.jpg");
	}


	@Override
	public ArrayList<String> setIntroduction() {
		ArrayList<String> s = new ArrayList<String>();
		s.add("技能 : 方向混淆            CD : 15秒");
		s.add("持續時間 : 3秒");
		s.add("技能說明 : 餵別人吃FM2，讓別人失去方向感，上下左右全部混淆");
		return s;
	}


}
