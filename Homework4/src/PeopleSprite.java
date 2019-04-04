import javafx.scene.image.Image;

public class PeopleSprite extends ActorSprite
{
	BoatSprite boat;

	public PeopleSprite(double x1, double y1, Sprite s[], BoatSprite b, Image i, Image si)
	{
		super(x1, y1, s, i);
		boat = b;


	}

	public void updateSprite()
	{
		if(!active)
			return;
		if (alive) {

			if (collision(boat))
			{
				//boat.drown();
				alive = false;
				suspend();
			}
		}
		else
		{
			suspend();
		}
	}
}
