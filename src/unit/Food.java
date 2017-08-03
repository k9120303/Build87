package unit;

public class Food extends Entity{

	private static final int hpUp = 200;
	public Food(int xpos, int ypos)
	{
		super(xpos, ypos, state.PLAIN);
	}
	
	public static int getHpUp()
	{
		return hpUp;
	}
	//獲得hp

}
