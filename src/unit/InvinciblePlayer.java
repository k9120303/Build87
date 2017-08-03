package unit;
//技能 : 無敵模式 CD 18秒

import java.awt.Point;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import game.map.Map;

public class InvinciblePlayer extends Player 
{

	public InvinciblePlayer(int xpos, int ypos) 
	{
		super(xpos, ypos);
		setPlayerName("老大");
		setSkillPath();
	}

	public InvinciblePlayer(Point point) 
	{
		super(point);
		setPlayerName("老大");
		setSkillPath();
		
	}

	Player getthis(){
		return this;
	}
	
	@Override
	public void skill(Map map,Player otherplayer) {
		// TODO Auto-generated method stub
		this.setSkillTime(18);
		this.setNowSkillTime(this.getSkillTime());

		this.setIsHurt(false);
		Timer Reborn = new Timer();
		Reborn.schedule(
				new TimerTask()
				{
					@Override
					public void run() 
					{
						getthis().setIsHurt(true);
						}	
				},3000
			);

	}

	@Override
	public void setImgPath() {
		imgPath[0] = ("Player\\老大\\背面.jpg");
		imgPath[1] = ("Player\\老大\\正面.jpg");
		imgPath[2] = ("Player\\老大\\左轉.jpg");
		imgPath[3] = ("Player\\老大\\右轉.jpg");
		imgPath[4] = ("Player\\老大\\死亡.jpg");
	}

	

	@Override
	public ArrayList<String> setIntroduction() 
	{
		ArrayList<String> s = new ArrayList<String>();
		s.add("技能 : 變形金剛            CD : 18秒");
		s.add("持續時間 : 3秒");
		s.add("技能說明 : 使用從香港帶過來的撒尿牛丸，獲得金剛不壞之身。");
		return s;
	}


}
