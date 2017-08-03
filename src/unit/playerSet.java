package unit;

import java.util.ArrayList;

import controller.PlayerController;
import game.map.Map;

public interface playerSet
{
	void skill(Map map,Player player);
	void setSkillPath();
	void setImgPath();
	ArrayList<String> setIntroduction();
}
