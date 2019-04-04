import java.lang.reflect.Array;

import javafx.scene.image.Image;

public class RockSprite extends Sprite{
	//controlled by the game
	public RockSprite(double x, double y, Image i)
	{
		super(x, y, i);
		resume();
	}
	
	
//	Integer[] rockYs = new Integer[300];
//	for(int i = 0; i <= 300; i++)
//		rockYs[i] = Math.random()* 800 + 10;
//	
	
//	public void updateSprite(int rockCount)
//	{
//		if (!active)
//			return;
//		if (alive)
//		{
//			updatePosition();
//			for (int i = 0; i < scenery.length; i++)
//			{
//				if (collision(scenery[i]))
//				{
//					// The tree is wood, hence floats on water
//					//if (scenery[i] instanceof LakeSprite)
//					//	continue;
//					// Hit something solid, back up and stop
//					x -= dx; y -= dy;
//					setVelocity(0.0, 0.0);
//				}
//			}
//		}
//		else
//		{
//			// We're on fire - wait a bit, then go away
//			counter--;
//			if (counter <= 0)
//				suspend();
//		}
//		
//		if(rockCount <= 5)
//			//generate a new rock on the right
//	}
}
