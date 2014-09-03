/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Courses;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author Hans-Peter
 */
public class RowRunner {

	private Rectangle r;
	private Timeline rowTimeLine;

	private final int lineHeight = 27;
	private int currentLine = 0;
	private int linesCount;

	private SimpleIntegerProperty x = new SimpleIntegerProperty(0);
	private SimpleIntegerProperty y = new SimpleIntegerProperty(0);

	public RowRunner(double width, double height, VBox vBox, String lineHeight) {
		r = new Rectangle(0, 0, width, height);
		r.setStyle("-fx-height: " + lineHeight);
		r.setFill(Color.BLACK);
		r.setOpacity(100);

		r.xProperty().bind(x);
		r.yProperty().bind(y);

		vBox.getChildren().get(0).setClip(r);

		linesCount = vBox.heightProperty().intValue() / r.heightProperty().intValue();
	}

	public void run() {
		int from;
		int to;

		rowTimeLine = new Timeline();

		for (int i = 0; i < linesCount; i++) {
			from = 500 * i;
			to = from + 500;

			rowTimeLine.getKeyFrames().add(
					new KeyFrame(
							new Duration(from),
							new KeyValue(x, 0),
							new KeyValue(y, currentLine)));
			rowTimeLine.getKeyFrames().add(
					new KeyFrame(
							new Duration(to),
							new KeyValue(x, 800),
							new KeyValue(y, currentLine)));

			currentLine += lineHeight;
		}
		rowTimeLine.play();
	}
}
