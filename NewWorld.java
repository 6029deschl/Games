
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Simple 3D World Demo use of texture images on pre-defined shapes.
 * 
 * Based on code from JavaFX 8 Introduction by Example (code written by Sean
 * Phillips)
 * 
 * @author mike slattery Modified by Alex Gattone - april 2018
 */
public class NewWorld extends Application {

	final int FPS = 30; // frames per second

	private PerspectiveCamera camera;
	private Group cameraDolly;
	private final double cameraQuantity = 10.0;
	private final double sceneWidth = 800;
	private final double sceneHeight = 800;

	private double mousePosX;
	private double mousePosY;
	private double mouseOldX;
	private double mouseOldY;
	private double mouseDeltaX;
	private double mouseDeltaY;

	Sphere ball;
	Box rectangle;
	InstructionText text;
	static int bx = 0;
	static int by = 35;
	static int bz = 800;
	static int rx = 0;
	static int rz = -1300;

	Image grass = new Image("grass.jpg");
	Image bark = new Image("bark.jpg");
	Image leaves = new Image("grass.jpg");
	Image fur = new Image("bark.jpg");
	
	ArrayList<Group> looks = new ArrayList<Group>();
	
	//String[] lookFiles = {"Goal"};

	private void constructWorld(Group root) {

		PointLight pl = new PointLight();
		pl.setTranslateX(5000);
		pl.setTranslateY(-3000);
		pl.setTranslateZ(-2000);
		root.getChildren().add(pl);

		final PhongMaterial blackMaterial = new PhongMaterial();
		blackMaterial.setDiffuseColor(Color.FLORALWHITE);
		ball = new Sphere(10.0, 120);
		ball.setMaterial(blackMaterial);

		ball.setTranslateX(bx);
		ball.setTranslateY(by);
		ball.setTranslateZ(bz);

		Rotate r1 = new Rotate(90, Rotate.Y_AXIS);
		Rotate r2 = new Rotate(180, Rotate.Z_AXIS);
		ball.getTransforms().addAll(r1, r2);

		Group spinner = new Group();
		spinner.getChildren().add(ball);
		RotateTransition rt = new RotateTransition(Duration.millis(1000), spinner);

		rt.setByAngle(360);
		rt.setAxis(Rotate.X_AXIS);
		rt.setInterpolator(Interpolator.LINEAR);
		rt.setCycleCount(Animation.INDEFINITE);
		//rt.setAutoReverse(true);
		rt.play();

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseMap(grass);
		Box box3 = new Box(400, 25, 5000);
		box3.setMaterial(greenMaterial);

		box3.setTranslateX(0);
		box3.setTranslateY(60);
		box3.setTranslateZ(0);
		
		final PhongMaterial goalMaterial = new PhongMaterial();
		goalMaterial.setDiffuseColor(Color.BLUE);
		rectangle = new Box(35, 10, 10);
		rectangle.setMaterial(goalMaterial);

		rectangle.setTranslateX(rx);
		rectangle.setTranslateY(40);
		rectangle.setTranslateZ(rz);
		
		root.getChildren().addAll(spinner, box3, rectangle);// sphere3, sphere2, sphere, cylinder);
		
		
		/*
		 * Group goal1 = new Group(); ObjView drvr = new ObjView(); try {
		 * drvr.load(ClassLoader.getSystemResource("Goal.obj").toString()); } catch
		 * (IOException e) { System.out.println("Trouble loading model");
		 * e.printStackTrace(); } Group goal = drvr.getRoot(); goal.setScaleX(30);
		 * goal.setScaleY(-30); goal.setScaleZ(-30);
		 * //goal.setRotationAxis(Rotate.Y_AXIS); //goal.setRotate(90);
		 * goal.setTranslateX(400); goal.setTranslateY(-150); looks.add(goal);
		 * 
		 * goal1.getChildren().add(goal);
		 * 
		 * //root.getChildren().add(goal1);
		 */		}

	public static void rollBall(Sphere b) {
		b.setTranslateZ(bz -= 20);
		b.setTranslateX(bx);
		b.setTranslateY(by);
		collide(b);
	}
	
	public static void collide(Sphere b) {
		//if ((rz == bz+20) && (bx-10 >= rx-30) && (bx <= rx + 60)) {
		if ((rz >=bz-20) && (bx-10 >= rx-20) && (bx+10 <= rx + 20)) {
			bz = 800;
			if(Math.random() < 0.5) {
				//b.setTranslateX(bx + Math.random()*100);
				bx = (int) (Math.random() * 100);
			}
			else
			//	b.setTranslateX(bx + Math.random()*-100);
				bx = (int) (Math.random() * -100);
		} else if (bz <= -1500)
			bz = 800;
	}
	
	public void moveRectLeft(Boolean b) {
//		if (b)
//		{
			if(rx >= -100) {
				rectangle.setTranslateX(rx -= 3);
			}
			else
				rectangle.setTranslateX(rx = -100);
//		}
//		else
//			rectangle.setTranslateX(rx = 0);
	}
	public void moveRectRight(Boolean b) {
//		if(b)
//		{
			if(rx <= 100) {
				rectangle.setTranslateX(rx += 3);
			}
//		}
//		else
//			rectangle.setTranslateX(rx = 100);
	}

	public void setHandlers(Scene scene) {
		
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case LEFT:
				moveRectLeft(true);
				break;
			case RIGHT:
				moveRectRight(true);
				break;
			default:
				break;
			}

		});

//		scene.setOnKeyReleased(e -> {
//			switch (e.getCode()) {
//			case RIGHT:
//				moveRectRight(false);
//				break;
//			case LEFT:
//				moveRectLeft(false);
//				break;
//			default:
//				break;
//			}
//		});
	}
	
	
	@Override
	public void start(Stage primaryStage) {

		// Build your Scene and Camera
		Group sceneRoot = new Group();
		constructWorld(sceneRoot);

		// Fourth parameter to indicate 3D world:
		Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight, true);
		scene.setFill(Color.LIGHTBLUE);
		camera = new PerspectiveCamera(true);
		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		scene.setCamera(camera);
		// translations through dolly
		cameraDolly = new Group();
		cameraDolly.setTranslateZ(-1600);
		cameraDolly.setTranslateX(0);
		cameraDolly.setTranslateY(-15);
		cameraDolly.getChildren().add(camera);
		sceneRoot.getChildren().add(cameraDolly);
		// rotation transforms
		Rotate xRotate = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
		Rotate yRotate = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
		camera.getTransforms().addAll(xRotate, yRotate);

		
		// Use keyboard to control camera position
		scene.setOnKeyPressed(event -> {
			double change = cameraQuantity;
			// What key did the user press?
			KeyCode keycode = event.getCode();

			Point3D delta = null;
			if (keycode == KeyCode.COMMA) {
				delta = new Point3D(0, 0, change * 2);
			}
			if (keycode == KeyCode.PERIOD) {
				delta = new Point3D(0, 0, -change * 2);
			}
			if (keycode == KeyCode.A) {
				delta = new Point3D(-change * 2, 0, 0);
			}
			if (keycode == KeyCode.D) {
				delta = new Point3D(change * 2, 0, 0);
			}
			if (keycode == KeyCode.W) {
				delta = new Point3D(0, -change * 2, 0);
			}
			if (keycode == KeyCode.S) {
				delta = new Point3D(0, change * 2, 0);
			}
			//if (keycode == KeyCode.RIGHT) {
				//moveRectRight(rectangle);
				
			//}
			//if (keycode == KeyCode.LEFT) {
				//moveRectLeft(rectangle);
				
			//}
			if (delta != null) {
				Point3D delta2 = camera.localToParent(delta);
				cameraDolly.setTranslateX(cameraDolly.getTranslateX() + delta2.getX());
				cameraDolly.setTranslateY(cameraDolly.getTranslateY() + delta2.getY());
				cameraDolly.setTranslateZ(cameraDolly.getTranslateZ() + delta2.getZ());

			}
		});

		// Use mouse to control camera rotation
		scene.setOnMousePressed(me -> {
			mousePosX = me.getSceneX();
			mousePosY = me.getSceneY();
		});

		scene.setOnMouseDragged(me -> {
			mouseOldX = mousePosX;
			mouseOldY = mousePosY;
			mousePosX = me.getSceneX();
			mousePosY = me.getSceneY();
			mouseDeltaX = (mousePosX - mouseOldX);
			mouseDeltaY = (mousePosY - mouseOldY);

			yRotate.setAngle(((yRotate.getAngle() - mouseDeltaX * 0.2) % 360 + 540) % 360 - 180); // +
			xRotate.setAngle(((xRotate.getAngle() + mouseDeltaY * 0.2) % 360 + 540) % 360 - 180); // -
		});

		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS), e -> {
			// update position
			rollBall(ball);
			//collide();
		});
		setHandlers(scene);
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		primaryStage.setTitle("World1");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}