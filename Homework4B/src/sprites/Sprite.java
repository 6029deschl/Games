package sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.RescueGame;

public class Sprite {

	public double x, y;
	public double dx, dy;
	double scale = 1.0;

	private Image img;

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
		Sprite s = null;
		double x = (Math.random() * 10) + 1;
		if (x >= 0 && x < 5)
			s = new Sprite("shark.png", 0.7);
		if (x >= 5 && x <= 10)
			s = new Sprite("rock.png", 0.2);

		return s;
	}

	public Sprite(String imageName) {
		this(imageName, 1);
	}

	public void render(GraphicsContext gc) {
		gc.drawImage(img, x, y, img.getWidth() * scale, img.getHeight() * scale);
	}

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

		if (keyDown && (this.y < RescueGame.HEIGHT - 50))
			this.y = this.y + 10;
		if (keyUp && (this.y > 0))
			this.y = this.y - 10;
	}
}