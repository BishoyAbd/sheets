package application;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	BorderPane root = new BorderPane();
	Group g = new Group();
	Scene scene = new Scene(root, 400, 400);
	Circle circle = new Circle(10);
	@Override
	public void start(Stage stage) { 
		try {
			
			initScene(stage);
			
			scene.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				Path p = drawSinPath(g);
				PathTransition animation = animatePath(g, p, circle);

				@Override
				public void handle(MouseEvent event) {
					animatColor(circle);
					MouseButton button = event.getButton();

					if (button == MouseButton.SECONDARY) {

						
						animation.pause();

					} else if (button == MouseButton.PRIMARY) {

						animation.play();

					}

				}

			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initScene(Stage stage) {
		g.getChildren().add(circle);
		g.getChildren().add(drawSinPath(g));
		root.getChildren().add(g);

		stage.setScene(scene);
		stage.show();

		
	}

	public PathTransition animatePath(Group g, Path path, Shape shape) {
		PathTransition pt = new PathTransition();
		pt = new PathTransition(Duration.millis(3000), drawSinPath(g), shape);
		pt.setCycleCount(Animation.INDEFINITE);
		pt.setAutoReverse(false);
		return pt;

	}

	public void animatColor(Shape shape) {
		FillTransition ft = new FillTransition(Duration.millis(3000), shape, Color.RED, Color.YELLOW);
		ft.setCycleCount(4);
		ft.setAutoReverse(true);
		ft.play();

	}

	public Path drawSinPath(Group root) {
		int xBase = 10;
		int top = 10;
		int yScale = 100;
		int xAxis = 360;

		int yBase = top + yScale;
		int x, y;

		root.getChildren().add(new Line(xBase, top, xBase, top + 2 * yScale));
		root.getChildren().add(new Line(xBase, yBase, xBase + xAxis, yBase));

		MoveTo moveTo = null;
		LineTo lineTo = null;
		Path path = new Path();
		
		moveTo = new MoveTo(10 , top + yScale - Math.sin(Math.toRadians(0))* yScale);

		path.getElements().add(moveTo);
		for (int i = 0; i < xAxis; i++) {
			x = xBase + i;
			y = (int) (yBase - Math.sin(Math.toRadians(i)) * yScale);

			lineTo = new LineTo(x, y);

			path.getElements().add(lineTo);

		}
		return path;

	}

	public static void main(String[] args) {
		launch(args);
	}
}
