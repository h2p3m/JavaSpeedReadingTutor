/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Courses;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import srt.SceneBuilder;

/**
 *
 * @author Hans-Peter
 */
public class SlideBar {

	public SlideBar(double width, double height, VBox vBox) {
		System.out.println(width);
		System.out.println(height);
		Rectangle r = new Rectangle(0, 0, width, height);
		r.setFill(Color.BLACK);
		r.setOpacity(100);
		
		vBox.getChildren().add(r);
	}
}
