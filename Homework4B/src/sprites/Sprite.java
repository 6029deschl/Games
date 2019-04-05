package sprites;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.RescueGame;

public class Sprite {

	public double x, y;
	public double dx, dy;
	double scale = 1.0;

	Image img;
	RescueGame rescue;
	Sprite sprite;
	public static int activesprite;
	public static boolean person;
	public static boolean maybe;

	private boolean keyDown = false;
	private boolean keyUp = false;

	// Sprite constructor
	public Sprite(String imageName, double scale) {
		this.img = new Image(imageName);
		this.scale = scale;
	}

	// spawn sharks, rocks, and people from the right side to move across
	// the screen to the left side towards the boat
	public static Sprite random() {
		// TODO write a function that returns a random sprite that can emerge from the
		// right side of the screen
		// public int activesprite = 0;
		activesprite = 0;
		// person = false;
		Sprite s = null;
		double x = (Math.random() * 10) + 1;
		if (x >= 0 && x < 3) {
			s = new Sprite("shark.png", 0.7);
			activesprite = 1;
			person = true;
		}
		if (x >= 3 && x <= 6) {
			s = new Sprite("rock.png", 0.2);
			activesprite = 2;
			person = true;
		}
		if (x > 6 && x <= 8) {
			s = new Sprite("person2.png", 1);
			activesprite = 3;
			person = true;
		}
		if (x > 8 && x <= 10) {
			s = new Sprite("rock2.png", 0.5);
			activesprite = 4;
			person = true;
		}
		return s;
	}

	public Sprite(String imageName) {
		this(imageName, 1);
	}

	public void render(GraphicsContext gc) {
		gc.drawImage(img, x, y, img.getWidth() * scale, img.getHeight() * scale);

		// if (RescueGame.DEBUG) {
		// BoundingBox bb = getBoundingBox();
		// gc.setStroke(Color.WHITE);

		// gc.strokeRect(bb.getMinX(), bb.getMinY(), bb.getMaxX() - bb.getMinX(),
		// bb.getMaxY() - bb.getMinY());

	}

	// }

	public void setUpKey(boolean keyUp) {
		this.keyUp = keyUp;
	}

	public void setDownKey(boolean keyDown) {
		this.keyDown = keyDown;
	}

	// updates the position of the different sprites
	public void update() {
		this.x += this.dx;
		this.y += this.dy;

		if ((keyDown) && (this.y < RescueGame.HEIGHT))
			this.y = this.y + 10;
		if ((keyUp) && (this.y > 0))
			this.y = this.y - 10;

		/*
		 * if (boat != null && this.collision(boat)) { boat.dx = 0; boat.dy = 0;
		 * 
		 * System.out.println("Game Over!"); rescue.gameOver(); }
		 */

		// if (boat.x == shark.x)
		// rescue.gameOver();
	}

	public BoundingBox getBoundingBox() {
		double width = img.getWidth() * scale;
		double height = img.getHeight() * scale;
		// double xoff = (width * (1.0f - RescueGame.BBscale) / 2.0f);
		// double yoff = (height * (1.0f - RescueGame.BBscale) / 2.0f);
		// double bbw = (width * RescueGame.BBscale);
		// double bbh = (height * RescueGame.BBscale);
		return new BoundingBox(x, y, width, height);// xoff, y + yoff, bbw, bbh);
	}

	public boolean collision(Sprite h) {
		return getBoundingBox().intersects(h.getBoundingBox());
	}
}