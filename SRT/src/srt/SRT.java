/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srt;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.itextpdf.text.pdf.hyphenation.*;

/**
 *
 * @author Hans-Peter
 */
public class SRT extends Application {

	private Scene scene;
	private BorderPane border;
	private TextArea inputText;
	private VBox vBoxCenter;
	private VBox vBoxLeft;
	private Button startSrt_btn;
	private Label label1;
	private Accordion accordion;

	@Override
	public void start(Stage primaryStage) {
		prepareScene();

		createNavi();

		setupStart();

		createStage(primaryStage);
//		Hyphenator h = new Hyphenator("de", "DR", 2, 2);
//		Hyphenation s = h.hyphenate("Dachlatte"); 
//		System.out.println(s);
	}

	private void createStage(Stage primaryStage) {
		//		primaryStage.setFullScreen(true);
		primaryStage.setMaximized(true);

		primaryStage.setTitle("jSRT");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void createNavi() {
		Button manualInput = new Button("Manual input");
		manualInput.setOnAction((ActionEvent event) -> {
			setupStart();
		});
		accordion = new Accordion();
		accordion.getPanes().addAll(
				new TitledPane("Start page", manualInput),
				new TitledPane("Training", new Label("bla bla")),
				new TitledPane("Exercise", new Label("aölsdad")),
				new TitledPane("Textengine", new Label("aölsdad")),
				new TitledPane("Newsreading", new Label("aölsdad")),
				new TitledPane("Options", new Label("aölsdad"))
		);
		accordion.setExpandedPane(accordion.getPanes().get(0));

		vBoxLeft.getChildren().setAll(accordion);
	}

	private void prepareScene() {
		vBoxCenter = new VBox(10);
		vBoxCenter.setAlignment(Pos.TOP_CENTER);
		vBoxLeft = new VBox(10);
		vBoxLeft.setAlignment(Pos.TOP_LEFT);

		border = new BorderPane();
		border.setCenter(vBoxCenter);
		border.setLeft(vBoxLeft);

		scene = new Scene(border, 300, 250);
	}

	private void setupStart() {
		inputText = new TextArea("Input here");
		inputText.setMinSize(300, 500);
		inputText.setMaxSize(800, 800);
//		inputText.setAlignment(Pos.TOP_CENTER);
		inputText.setPrefColumnCount(100);
		inputText.setWrapText(true);

		startSrt_btn = new Button();
		startSrt_btn.setText("Start");
		startSrt_btn.setOnAction((ActionEvent event) -> {
			StartSRT(inputText.getText());
		});

//		label1 = new Label("Manual input:");
		vBoxCenter.getChildren().setAll(inputText, startSrt_btn);
	}

	private void StartSRT(String input) {
		String output = "";
		ArrayList<String> wrappedText = new ArrayList<>(Arrays.asList(input.split(" ")));
//		wrappedText = Arrays.asList(input.split(" "));

		output = joinWordsToLine(wrappedText);
		
		Label text = new Label(output);
		text.setMinSize(300, 500);
		text.setMaxSize(800, 800);

		Button back = new Button("back");
		back.setOnAction((ActionEvent event) -> {
			setupStart();
		});
		vBoxCenter.getChildren().setAll(text, back);

	}

	private String joinWordsToLine(ArrayList<String> text) {
		String lines = "";
		String lineBuf = "";
		int maxCharCount = 100;
		
        Hyphenator h = new Hyphenator("de", "DR", 2, 2);
        Hyphenation s;
		String hyphText = "";
		boolean addHyph = false;
		String postHyphenAtNewLine = "";
		
		for (String word : text) {
			lineBuf += postHyphenAtNewLine;
			postHyphenAtNewLine = "";
			
			if (lineBuf.length() + word.length() + 1 < maxCharCount) {
				lineBuf += word + " ";
			} else if (lineBuf.length() + word.length() + 1 == maxCharCount) {
				lineBuf += word;
			} else if (lineBuf.length() + word.length() > maxCharCount) {
				//TODO: hyphenation				
				s = h.hyphenate(word);
				hyphText = "";
				if (s != null) {
					for (int i = 0; i < s.getHyphenationPoints().length; i++) {
						hyphText += s.getPreHyphenText(i);
						if (lineBuf.length() + hyphText.length() + 1 <= maxCharCount) {
							lineBuf += s.getPreHyphenText(i);
							addHyph = true;
						} else {
							break;
						}
					}
				}
				if (addHyph && s != null) {
					lineBuf += "-\n";
					postHyphenAtNewLine = s.getPostHyphenText(s.getHyphenationPoints().length -1);
					lines += lineBuf;
				} else {
					lineBuf += "\n";
					lines += lineBuf;
					lineBuf = "";
				}
			} else {
				lineBuf += "\n";
				lines += lineBuf;
				lineBuf = "";
			}
		}
		lines += lineBuf;

		return lines;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
