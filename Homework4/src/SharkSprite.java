// Jamie Oesterle and Lauren Desch

import javafx.scene.image.Image;

public class SharkSprite extends Sprite
{
	BoatSprite boat;
	Image sunkenBoatImage;

	static final int PERSISTENCE = 20;

	SharkSprite(double x1, double y1,  Image i)
	{
		super(x1, y1, i);
		resume();
	}

	public void updateSprite()
	{
		if (!active)
			return;
		
		if (active)
		{
			updatePosition();

		}
		else
		{
			suspend();
		}
	}
}
