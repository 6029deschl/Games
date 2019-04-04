// Programming Computer Games
// Jamie Oesterle and Lauren Desch

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Rescue extends Application {
	final String appName = "Rescue";
	final int FPS = 30; // frames per second

	
	SharkSprite shark;
	BoatSprite boat;
	PeopleSprite people;
	RockSprite rock;
	Sprite scenery[];
	
	boolean boxDebug = false;
	Image rockImage, wave1Image, wave2Image;
	Image boatImage, personImage, sharkImage;
	Image sunkenBoatImage;//not used yet

	public static float BBscale = 1.0f;

	Font font = Font.font("TimesRoman", FontPosture.ITALIC, 60.0);
	
	public static double view_x=0, view_y=0;
	final static double VIEW_WIDTH = 1200;
	final static double VIEW_HEIGHT = 800;
	final static double WORLD_WIDTH = 1600;
	final static double WORLD_HEIGHT = 2000;
	final static double SCROLL_X = 200;
	final static double SCROLL_Y = 200;
	static boolean world_coords = false;
	static int rockCount = 0;
	
	
	void initialize()
	{
		wave1Image = new Image("waves1.png");
		wave2Image = new Image("waves2.png");
		boatImage = new Image("boat.png");
		personImage = new Image("person2.png");
		sharkImage = new Image("shark.png");//this picture is way too big and will be edited later
		rockImage = new Image("rock2.png");//the rock pic i chose was too large and I couldn't find a smaller one so I used the pic from BigForect, but will change this!
		sunkenBoatImage = new Image("sunkenboat.png");
		setLevel1();//will hopefully add more levels to this later
	}
	
	
	void setHandlers(Scene scene)
	{	
		scene.setOnKeyPressed(
				e -> {
					KeyCode c = e.getCode();
					switch (c) {
						case J: boat.setUpKey(true);
									break;
						case N: boat.setDownKey(true);
									break;
						case M: boat.setRightKey(true);
								break;
						case B: boat.setLeftKey(true);
								break;
						default:
									break;
					}
				}
			);
			
			scene.setOnKeyReleased(
					e -> {
						KeyCode c = e.getCode();
						switch (c) {
							case J: boat.setUpKey(false);
										break;
							case N: boat.setDownKey(false);
										break;
							case M: boat.setRightKey(false);
									break;
							case B: boat.setLeftKey(false);
									break;
					
							default:
										break;
						}
					}
				);
		}
	
	public void update()
	{
		boat.updateSprite();
		people.updateSprite();
		boat.move();
		checkScrolling(boat.getBoundingBox());
		//rock update? 
	}

	public void checkScrolling(BoundingBox r)
	{
		
		if (r.getMinX() < view_x + SCROLL_X)
		{
			view_x = r.getMinX() - SCROLL_X;
			if (view_x < 0)
				view_x = 0;
		}
		else if (r.getMaxX() > view_x + VIEW_WIDTH - SCROLL_X)
		{
			view_x = r.getMaxX() - VIEW_WIDTH + SCROLL_X;
			if (view_x +VIEW_WIDTH > WORLD_WIDTH)
				view_x = WORLD_WIDTH - VIEW_WIDTH;
		}
		
		if (r.getMinY() < view_y + SCROLL_Y)
		{
			view_y = r.getMinY() - SCROLL_Y;
			if (view_y < 0)
				view_y = 0;
		}
		else if (r.getMaxY() > view_y + VIEW_HEIGHT - SCROLL_Y)
		{
			view_y = r.getMaxY() - VIEW_HEIGHT + SCROLL_Y;
			if (view_y +VIEW_HEIGHT > WORLD_HEIGHT)
				view_y = WORLD_HEIGHT - VIEW_HEIGHT;
		}
	}
	
	
	public boolean lostGame()
	{
		return !boat.isAlive();
	}

	public boolean wonGame()
	{
		return !people.isAlive() && boat.isAlive();
	}
	
	static void useWorldCoords(GraphicsContext gc)
	{
		if (!world_coords)
		{
			gc.translate(-view_x, -view_y);
			world_coords = true;
		}
	}

	static void useScreenCoords(GraphicsContext gc)
	{
		if (world_coords)
		{
			gc.setTransform(new Affine());
			world_coords = false;
		}
	}
	
	public void setLevel1()
	{
		scenery = new Sprite[]{
			new RockSprite(300,200,rockImage),
			new RockSprite(60,400,rockImage),
			new RockSprite(1500,1600,rockImage),
			new SharkSprite(640,400,sharkImage),
			new SharkSprite(1040,400,sharkImage),
			new SharkSprite(1340,700,sharkImage),
			new SharkSprite(640,1000,sharkImage),
			new RockSprite(1040,80,rockImage),
			
			new WallSprite(0,0,10,WORLD_HEIGHT-1),
			new WallSprite(0,0,WORLD_WIDTH-1,10),
			new WallSprite(0,WORLD_HEIGHT-10,WORLD_WIDTH-1,10),
			new WallSprite(WORLD_WIDTH-10,0,10,WORLD_HEIGHT-1)
		};

		boat = new BoatSprite(50,50,scenery,boatImage,sunkenBoatImage);
		people = new PeopleSprite(200,1200,scenery,boat,personImage,personImage);
		
	
	}
	
	
	void render(GraphicsContext gc) {
		useScreenCoords(gc);
		gc.setFill(Color.SKYBLUE);
		gc.fillRect(0.0, 0.0, VIEW_WIDTH, VIEW_HEIGHT);
		useWorldCoords(gc);
		for (Sprite s: scenery)
		{
			s.render(gc, boxDebug);
		}
		boat.render(gc, boxDebug);
		people.render(gc, boxDebug);
		useScreenCoords(gc);

		gc.setFill(Color.BLUE);
		gc.setFont(font);
		
		if (wonGame())
		{
			gc.fillText("Winner!",250,200);
		}

		if (lostGame())
		{
			gc.fillText("LOSER",250,200);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage theStage) {
		theStage.setTitle(appName);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(VIEW_WIDTH, VIEW_HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Initial setup
		initialize();
		setHandlers(theScene);
		
		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS),
				e -> {
					// update position
					update();
					// draw frame
					render(gc);
				}
			);
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();
	}
}