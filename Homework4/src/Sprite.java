
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Sprite
{
  double x,y;
  double dx,dy;

  Image image;

  boolean active=false, visible=false;

  public Sprite(double x1, double y1, Image i) {
	  x = x1;
	  y = y1;
	  image = i;
  }

  void updatePosition() {
  	x += dx;
  	y += dy;
  }

  void setPosition(double a, double b) {
  	x = a; y = b;
  }

  void setVelocity(double a, double b) {
    dx = a; dy = b;
  }

  boolean isActive() {
  	return active;
  }

  void suspend() {
    active = false; visible = false;
  }

  void resume() {
    active = true; visible = true;
  }

  void updateSprite() {}

  public void render(GraphicsContext gc) {
       if (visible) {
         gc.drawImage(image, x, y);
       }
  }

  public void render(GraphicsContext gc, boolean debug)
  	{
  		render(gc);
  		if (debug)
  		{
  			gc.setStroke(Color.BLACK);
  			BoundingBox bb = getBoundingBox();
  			gc.strokeRect(bb.getMinX(), bb.getMinY(), bb.getWidth()  , bb.getHeight());
  		}
	}

  public BoundingBox getBoundingBox()
  	{
  		double width = image.getWidth();
  		double height = image.getHeight();
  		double xoff = (width*(1.0f - Rescue.BBscale)/2.0f);
  		double yoff = (height*(1.0f - Rescue.BBscale)/2.0f);
  		double bbw = (width*Rescue.BBscale);
  		double bbh = (height*Rescue.BBscale);
  		return new BoundingBox(x+xoff, y+yoff, bbw, bbh);
	}
}
