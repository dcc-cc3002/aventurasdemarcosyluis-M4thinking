package com.example.aventurasdemarcoyluis.view.nodes;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StaticNode {
	private final int imgHeight;
	private final int imgWidth;
	private ImageView sprite;
	private final int xPos;
	private final int yPos;
	private final Group group;

	/**
	 * Creates a new static component from an image.
	 */
	public StaticNode(final @NotNull Group group, final int xPos, final int yPos,
	                  final int imgHeight, final int imgWidth, final String spritePath)
			throws FileNotFoundException {
		this.imgHeight = imgHeight;
		this.imgWidth = imgWidth;
		this.xPos = xPos;
		this.yPos = yPos;
		this.group = group;
		addSprite(spritePath);
	}

	private void addSprite(final String spritePath) throws FileNotFoundException {
		FileInputStream spriteImage = new FileInputStream(spritePath);
		sprite = new ImageView(new Image(spriteImage));
		sprite.setX(xPos);
		sprite.setY(yPos);
		sprite.setFitWidth(imgWidth);
		sprite.setFitHeight(imgHeight);
		group.getChildren().add(sprite);
	}

	/**
	 * Get the image view of the node
	 * @return Returns the sprite image
	 */
	public ImageView getNode() {
		return sprite;
	}
}

