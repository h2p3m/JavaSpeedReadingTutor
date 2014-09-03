/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srt;

import com.itextpdf.text.pdf.hyphenation.Hyphenation;
import com.itextpdf.text.pdf.hyphenation.Hyphenator;
import java.util.ArrayList;

/**
 *
 * @author Hans-Peter
 */
public class TextProcessor {

	private final int MAXLINEWIDTH = 90;

	private final ArrayList<String> text = new ArrayList<>();
	private int lastLine = 0;

	public TextProcessor() {
		text.add("");
	}

	public ArrayList<String> TextToLines(ArrayList<String> rawText) {
		for (String word : rawText) {
			if (text.get(text.size() - 1).length() + word.trim().length() <= MAXLINEWIDTH) {
				AddToLine(word);
			} else {
				Hyphenate(word);
			}
		}

		return text;
	}

	private void AddToLine(String word) {
		if (text.get(lastLine).length() + word.trim().length() + 1 < MAXLINEWIDTH) {
			text.set(lastLine, text.get(lastLine) + word + " ");
		} else if (text.get(lastLine).length() + word.trim().length() == MAXLINEWIDTH) {
			text.set(lastLine, text.get(lastLine) + word);
			text.add("");
			lastLine++;
		} else if (text.get(lastLine).length() + word.trim().length() + 3 >= MAXLINEWIDTH) {
			text.set(lastLine, text.get(lastLine) + word);
			text.add("");
			lastLine++;
		}
	}

	private void Hyphenate(String word) {
		Hyphenator h = new Hyphenator("de", "DR", 2, 2);
		Hyphenation s = h.hyphenate(word);

		int freeLineSpace = MAXLINEWIDTH - text.get(text.size() - 1).length();

		if (s != null && freeLineSpace >= 3) {
			String hyphText = "";
			boolean addHyph = false;
			String postHyphenAtNewLine = "";
			int l = 0;
			String hyphenedWord = "";
			int hPos = 0;

			for (int hPoints : s.getHyphenationPoints()) {
				if (l + hPoints < freeLineSpace) {
					l += hPoints;
					hyphenedWord += s.getPreHyphenText(hPos++) + "-";
				}
			}

			text.set(lastLine, text.get(lastLine) + hyphenedWord);
			text.add(s.getPostHyphenText(s.getHyphenationPoints().length - 1) + " ");
			lastLine++;
		} else {
			text.set(lastLine, text.get(lastLine));
			text.add("");
			lastLine++;
			text.set(lastLine, text.get(lastLine) + word + " ");
		}
	}

	public ArrayList<String> getText() {
		return text;
	}
}
