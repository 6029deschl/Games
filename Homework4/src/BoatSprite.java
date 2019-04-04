import javafx.scene.image.Image;

public class BoatSprite extends ActorSprite
{
	Image sunkenBoatImage;//sunken boat // not used in first draft but will be used later
	PeopleSprite person;

	double HEIGHT = 2000;
	double WIDTH = 1600;


	
	boolean upKey = false, downKey = false, rightKey = false, leftKey = false;

	BoatSprite(int x1, int y1, Sprite s[], Image i, Image bi)
	{
		super(x1, y1, s, i);
		//sunkenBoatImage = bi;
		setVelocity(10, 0);
		speed = 5.0;
	}

	public void setUpKey(Boolean val){
		   upKey = val;
	   }

	   public void setDownKey(Boolean val){
		   downKey = val;
	   }
	   
	 public void setRightKey(Boolean val)
	 {
		 rightKey = val;
	 }
	 
	 public void setLeftKey(Boolean val)
	 {
		 leftKey = val;
	 }
	 
	 public void move(){
			if (upKey && getTop()>0)
				y -= 3;
			if (downKey && getBottom()<HEIGHT)
				y += 3;
			if (leftKey && getLeft()>0)
				x -= 3;
			if (rightKey && getRight()<WIDTH)
				x += 3;
			else {
				x += 0;
				y += 0;
			}
	}
	 
	 public double getTop(){
			return y;
		}

		public double getBottom(){
			return y;
		}
		public double getLeft(){
			return x;
		}
		public double getRight(){
			return x;
	}
	
	public void updateSprite()
	{
		if (!active)
			return;
		if (alive)
		{
			updatePosition();
			for (int i = 0; i < scenery.length; i++)
			{
				if (collision(scenery[i]))
				{
					// The tree is wood, hence floats on water
					//if (scenery[i] instanceof LakeSprite)
					//	continue;
					// Hit something solid, back up and stop
					x -= dx; y -= dy;
					setVelocity(0.0, 0.0);
				}
			}
		}
		else
		{
			// We're on fire - wait a bit, then go away
			counter--;
			if (counter <= 0)
				suspend();
		}
	}

	//public void burn()
	//{
	//	setVelocity(0.0, 0.0);
		//alive = false;
		//image = burnImage;
		//counter = BigForest.DYING;
	//}
}
