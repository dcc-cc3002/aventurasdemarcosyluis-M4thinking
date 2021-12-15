package com.example.aventurasdemarcoyluis.view.labels;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileNotFoundException;

/** Class that allows building a label with all its relevant characteristics for the game interface. */
public class LabelBuilder {
	private final Group group;
	private int xPos;
	private int yPos;
	private String font;
	private FontWeight fontWeight;
	private int size;
	private Color color;

	/**
	 * Label Builder class creator
	 * @param aGroup Group to which the label will belong
	 */
	public LabelBuilder(Group aGroup) {
		this.group = aGroup;
	}

	/**
	 * Set the position of the label in the group
	 * @param xPos position in x-axis
	 * @param yPos position in y-axis
	 * @return Return the same static node builder
	 */
	public LabelBuilder setPosition(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		return this;
	}

	/**
	 * Set the base style of the label font
	 * @param font The family or style of the font
	 * @return Return the same label builder
	 */
	public LabelBuilder setFont(String font) {
		this.font = font;
		return this;
	}

	/**
	 * Set the weight of the label font
	 * @param fontWeight The weight of the font
	 * @return Return the same label builder
	 */
	public LabelBuilder setFontWeight(FontWeight fontWeight) {
		this.fontWeight = fontWeight;
		return this;
	}

	/**
	 * Set the letter size of the label in the group
	 * @param size letter size
	 * @return Return the same label builder
	 */
	public LabelBuilder setSize(int size) {
		this.size = size;
		return this;
	}

	/**
	 * Set {@code Color} letter of the label
	 * @param color letter color
	 * @return Return the same label builder
	 */
	public LabelBuilder setColor(Color color) {
		this.color = color;
		return this;
	}

	/**
	 * Create the {@code Label} with all characteristics set at the moment
	 * @return Return a {@code Label}.
	 */
	public Label build() {
		Label label = new Label();
		label.setLayoutX(xPos);
		label.setLayoutY(yPos);
		label.setFont(Font.font(font, fontWeight, size));
		label.setTextFill(color);
		group.getChildren().add(label);
		return label;
	}
}
