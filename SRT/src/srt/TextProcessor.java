/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srt;

import java.util.ArrayList;

/**
 *
 * @author Hans-Peter
 */
public class TextProcessor {

	private String wrappedText = "";
	private String tempLine = "";
	private int maxLineWidth = 100;

	public String TextToLines(ArrayList<String> rawText) {
		rawText.stream().forEach((word) -> {
			if (tempLine.length() + word.trim().length() <= maxLineWidth) {
				AddToLine(word);
			} else {
				Hyphenate(word);
			}
		});

		return null;
	}

	private void AddToLine(String word) {
		if (tempLine.length() + word.trim().length() + 1 < maxLineWidth) {
			tempLine += word + " ";
		} else if (tempLine.length() + word.trim().length() +4 <= maxLineWidth) {
			tempLine += word + "\n";
		} 
	}

	private void Hyphenate(String word) {

	}
}
