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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import sprites.Boat;
import sprites.People;
import sprites.Rock;
import sprites.Shark;
import sprites.Sprite;

public class RescueGame extends Application {

	final int FPS = 30;
	public static double WIDTH, HEIGHT;
	Sprite boat;
	People people;
	Shark shark;
	Rock rock;
	List<Sprite> sprites = new ArrayList<>();
	// count of sprites
	int count = 0;
	static final int PEOPLE_VALUE = 100;
	private int score;
	// state the game is in
	private boolean playing; // if game playing
	private int screen; // which screen to show
	static final int INTRO = 0; // intro screen
	static final int GAME_OVER = 1; // game over screen

	// Shark shark;

	public static float BBscale = 0.9f;

	public static boolean DEBUG = false;

	Font smallFont = Font.font("Helvetica", FontWeight.BOLD, 24);
	Font mediumFont = Font.font("Helvetica", FontWeight.BOLD, 28);
	Font bigFont = Font.font("Helvetica", FontWeight.BOLD, 36);

	String scoreString = "Score: ";
	int stringWidth;
	String introString[] = new String[8];

	public static void main(String[] args) {
		launch(args);
	}

	// updates the sprites in the world
	private void update() {

		// calls update in the Sprite class
		boat.update();
		for (Sprite sprite : sprites) {
			sprite.update();
			sprite.dx *= 1.01;
			// if (boat.x == sprite.x) { // collision
			// gameOver();
			// screen = GAME_OVER;
			// }
			if (sprite.collision(boat))
				if (Sprite.person == true) {
					incrementScore();
					sprite.x = -100;
				}

				else {
					gameOver();
				}

		}
		if (count++ % 40 == 0)
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

	public void incrementScore() {
		score += PEOPLE_VALUE;
	}

	public void gameOver() {
		if (playing) {
			playing = false;
			screen = GAME_OVER;
		}
	}

	private void render(GraphicsContext gc) {
		if (playing) {
			gc.setFill(Color.SKYBLUE);
			gc.fillRect(0, 0, WIDTH, HEIGHT);

			gc.setFont(smallFont);
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.RIGHT);
			gc.fillText(scoreString + score, WIDTH * 0.82, HEIGHT * 0.11);
			gc.setTextAlign(TextAlignment.LEFT);
			for (Sprite sprite : sprites)
				sprite.render(gc);
			boat.render(gc);
		} else if (screen == INTRO) {
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, WIDTH, HEIGHT);

			gc.setFont(bigFont);

			gc.setFill(Color.ROYALBLUE);
			gc.setFont(mediumFont);

			// draw instructions
			for (int i = 0; i < introString.length - 1; i++) {
				gc.fillText(introString[i], 26, (3 + i) * HEIGHT / 12);
			}
			gc.setFill(Color.WHITE);
			// System.out.println(introString);
			gc.fillText(introString[7], (WIDTH - stringWidth) / 2, HEIGHT * 11 / 12);

			gc.setFont(smallFont);
			gc.setFill(Color.WHITE);
			gc.setTextAlign(TextAlignment.RIGHT);
			gc.fillText(scoreString + score, WIDTH * 0.82, HEIGHT * 0.11);
			gc.setTextAlign(TextAlignment.LEFT);

		}
		// playing = true;
	}

	private void initialize() {
		boat = new Boat();
		boat.x = 50;
		boat.y = HEIGHT / 2;

		playing = false; // not playing
		screen = INTRO; // show intro screen

		stringWidth = 200;

		introString[0] = "          Welcome to";
		introString[1] = "      ~R~E~S~C~U~E~";
		introString[2] = "       save the people,";
		introString[3] = "avoid the sharks and rocks!";
		introString[4] = "                         ";
		introString[5] = "Use the UP and DOWN keys";
		introString[6] = "                to move";
		introString[7] = "CLICK SCREEN TO BEGIN";

	}

	// initialize params for new game
	public void newGame() {
		playing = true;
		score = 0; // no score
		update();
		// gm.newGame(); // call newGame in
		// um.newGame(); // manager classes
	}

	public void setHandlers(Scene scene) {
		scene.setOnMousePressed(e -> {
			switch (screen) {
			case INTRO:
				playing = true;
				newGame();
				break;
			case GAME_OVER:
				screen = INTRO;
				break;
			default:
				break;
			}
		});
		scene.setOnKeyPressed(e -> {
			if (!playing)
				return;
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
			if (!playing)
				return;
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

		WIDTH = size.getWidth() * 0.8;
		HEIGHT = size.getHeight() * 0.6;

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