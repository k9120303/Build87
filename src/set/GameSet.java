package set;
import java.awt.Point;

public abstract class GameSet
{
	public static int border = 26;
	public static int viewWidth = 700;
	public static int viewHeight = 700;
	public static int playerViewWidth = 200;
	public static int playerViewHeight = 700;
	public static int hpHeight = 10;
	public static int sectionHeight = 20;
	public static final int PLAYERSPEED = 100;
	public static final int GHOSTSPEED = 150; 
	public static final int GHOSTNUMBER = 50;
	public static final int constructTime = 1500; // 1500 milliseconds
	public static final int destoryTime = 2000; // 2000 milliseconds
	public static Point[] ghostLairLocation = {new Point (1,1),new Point (1,border-2),
						new Point (border-2,border-2),new Point (border-2,1),new Point(border/2 -1,border/2 -1)}; 
}
