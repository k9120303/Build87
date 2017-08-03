package unit;

public abstract class Wall extends Entity //虛擬類別，記錄摧毀所需時間
{
	private int destoryTime = 1500; // 1500 milliseconds
	
	Wall(int xpos, int ypos)
	{
		super(xpos, ypos,state.OBSTACLE);
	}
	
	
	

	
	
	
}
