import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;

public class ActorSprite extends Sprite{
	Sprite scenery[];
	boolean alive = true;
	double speed = 0.0;  // This will be reset in subclasses
	int counter;

	ActorSprite(double x1, double y1, Sprite s[], Image i)
	{
		super(x1, y1, i);
		scenery = s;
		setVelocity(0.0, 0.0);
		resume();
	}

	public void headTo(double x1, double y1)
	{
		double vx = x1 - x;
		double vy = y1 - y;
		double len = Math.sqrt(vx*vx+vy*vy);
		setVelocity(vx*speed/len, vy*speed/len);
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public boolean isAlive()
	{
		return alive;
	}

	public boolean collision(Sprite h)
	{
		BoundingBox bb = getBoundingBox();
		return bb.intersects(h.getBoundingBox());
	}

}
