// Jamie Oesterle and Lauren Desch Rescue

import javafx.geometry.BoundingBox;
import javafx.scene.paint.Color;

public class WallSprite extends Sprite
{
	  double width, height;
	  Color color = Color.BLACK;

		public WallSprite(double x1, double y1, double w, double h)
		{
			super(x1, y1, null);  
			width = w;
			height = h;
			resume();
		}

		public BoundingBox getBoundingBox()
		{
			return new BoundingBox(x, y, width, height);
		}
	}

