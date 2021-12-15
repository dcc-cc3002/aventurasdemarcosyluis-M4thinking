package com.example.aventurasdemarcoyluis.view.buttons;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/** Class that allows building a button with all its relevant characteristics for the game interface. */
public class ButtonBuilder {
	private final Group group;
	private int xPos;
	private int yPos;
	private int height;
	private int width;
	private String backgroundColor;
	private ImageView imageView;

	/**
	 * Button Builder class creator
	 * @param aGroup Group to which the node will belong
	 */
	public ButtonBuilder(Group aGroup) {
		this.group = aGroup;
	}

	/**
	 * Set the position of the button in the group
	 * @param xPos position in x-axis
	 * @param yPos position in y-axis
	 * @return Return the same static button builder
	 */
	public ButtonBuilder setPosition(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		return this;
	}

	/**
	 * Set the dimensions of the button in the group
	 * @param height Height of the button view
	 * @param width Width of the button view
	 * @return Return the same static button builder
	 */
	public ButtonBuilder setSize(int height, int width) {
		this.height = height;
		this.width = width;
		return this;
	}

	/**
	 * Set the background color for this button
	 * @param backgroundColor color input css format
	 * @return Return the same static button builder
	 */
	public ButtonBuilder setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
		return this;
	}

	/**
	 * Sets the visible image that the button will have
	 * @param imageView Image view to be set on the button
	 * @return Return the same static button builder
	 */
	public ButtonBuilder setImageView(ImageView imageView) {
		this.imageView = imageView;
		return this;
	}

	/**
	 * Create the {@code Button} with all characteristics set at the moment
	 * @return Return a {@code Button}.
	 */
	public Button build() {
		Button button = new Button();
		button.setLayoutX(xPos);
		button.setLayoutY(yPos);
		button.setMinHeight(height);
		button.setMinWidth(width);
		button.setStyle("-fx-background-color:" + backgroundColor +";");
		button.setGraphic(imageView);
		group.getChildren().add(button);
		return button;
	}
}
