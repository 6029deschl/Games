package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sprites.Boat;
import sprites.Sprite;

public class RescueGame extends Application {

	final int FPS = 30;
	public static double WIDTH, HEIGHT;
	Sprite boat;
	List<Sprite> sprites = new ArrayList<>();
	// count of sprites
	int count = 0;

	public static void main(String[] args) {
		launch(args);
	}

	// updates the sprites in the world
	private void update() {

		// calls update in the Sprite class
		boat.update();
		for (Sprite sprite : sprites)
			sprite.update();
		if (count++ % 50 == 0)
			spawn();
	}

	// spawns a new sprite to move towards the boat and gives it properties
	private void spawn() {
		Sprite newSprite = Sprite.random();

		newSprite.x = WIDTH;
		newSprite.dx = Math.random() * -5;
		newSprite.y = Math.random() * HEIGHT;
		sprites.add(newSprite);

	}

	private void render(GraphicsContext gc) {
		gc.setFill(Color.SKYBLUE);
		gc.fillRect(0, 0, WIDTH, HEIGHT);

		for (Sprite sprite : sprites)
			sprite.render(gc);
		boat.render(gc);
	}

	private void initialize() {
		boat = new Boat();
		boat.x = 50;
		boat.y = HEIGHT / 2;
	}

	public void setHandlers(Scene scene) {
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case UP:
				boat.setUpKey(true);
				break;
			case DOWN:
				boat.setDownKey(true);
				break;
			default:
				break;
			}
		});
		scene.setOnKeyReleased(e -> {
			switch (e.getCode()) {
			case UP:
				boat.setUpKey(false);
				break;
			case DOWN:
				boat.setDownKey(false);
				break;
			default:
				break;
			}
		});
	}

	@Override
	public void start(Stage theStage) throws Exception {

		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

		WIDTH = size.getWidth() - 100;
		HEIGHT = size.getHeight() - 100;

		theStage.setTitle("");

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		initialize();
		setHandlers(theScene);

		Timeline mainLoop = new Timeline(new KeyFrame(Duration.millis(1000 / FPS), e -> {
			update();
			render(gc);
		}));

		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();
	}

}
