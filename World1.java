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
import javafx.scene.shape.Cylinder;
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
public class World1 extends Application {

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
	int bx = 180;
	int by = 5;
	static int bz = 200;

	Image grass = new Image("grass.jpg");
	Image bark = new Image("bark.jpg");
	Image leaves = new Image("leaves.jpg");
	Image fur = new Image("fur.jpg");

	private void constructWorld(Group root) {

		PointLight pl = new PointLight();
		pl.setTranslateX(5000);
		pl.setTranslateY(-3000);
		pl.setTranslateZ(-2000);
		root.getChildren().add(pl);

		final PhongMaterial g2Material = new PhongMaterial();
		g2Material.setDiffuseMap(leaves);
		final Sphere sphere = new Sphere(70);
		sphere.setMaterial(g2Material);

		sphere.setTranslateZ(-20);
		sphere.setTranslateY(-160);
		sphere.setTranslateX(5);

		final Sphere sphere2 = new Sphere(70);
		sphere2.setMaterial(g2Material);

		sphere2.setTranslateZ(50);
		sphere2.setTranslateY(-160);
		sphere2.setTranslateX(-15);

		final Sphere sphere3 = new Sphere(70);
		sphere3.setMaterial(g2Material);

		sphere3.setTranslateZ(15);
		sphere3.setTranslateY(-200);
		sphere3.setTranslateX(-10);

		final PhongMaterial brownMaterial = new PhongMaterial();
		brownMaterial.setDiffuseMap(bark);
		Cylinder cylinder = new Cylinder(40, 180);
		cylinder.setMaterial(brownMaterial);

		cylinder.setTranslateX(-10);
		cylinder.setTranslateY(-60);
		cylinder.setTranslateZ(20);

		final PhongMaterial blackMaterial = new PhongMaterial();
		blackMaterial.setDiffuseMap(fur);
		ball = new Sphere(40.0, 120);
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
		rt.setAutoReverse(true);
		rt.play();

		final PhongMaterial greenMaterial = new PhongMaterial();
		greenMaterial.setDiffuseMap(grass);
		Box box3 = new Box(1000, 50, 1000);
		box3.setMaterial(greenMaterial);

		box3.setTranslateX(0);
		box3.setTranslateY(60);
		box3.setTranslateZ(0);

		root.getChildren().addAll(spinner, box3, sphere3, sphere2, sphere, cylinder);
	}

	public static void rollBall(Sphere b) {
		b.setTranslateZ(bz -= 3);
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
		cameraDolly.setTranslateZ(-1000);
		cameraDolly.setTranslateX(200);
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
		});
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
