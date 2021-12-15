package com.example.aventurasdemarcoyluis.view.nodes;

import javafx.scene.Group;
import java.io.FileNotFoundException;

public class StaticNodeBuilder {
	private final Group group;
	private int xPos;
	private int yPos;
	private int height;
	private int width;
	private String imagePath;

	/**
	 * Static Node Builder class creator
	 * @param aGroup Group to which the node will belong
	 */
	public StaticNodeBuilder(Group aGroup) {
		this.group = aGroup;
	}

	/**
	 * Set the position of the node in the group
	 * @param xPos position in x-axis
	 * @param yPos position in y-axis
	 * @return Return the same static node builder
	 */
	public StaticNodeBuilder setPosition(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		return this;
	}

	/**
	 * Set the dimensions of the node in the group
	 * @param height Height of the node view
	 * @param width Width of the node view
	 * @return Return the same static node builder
	 */
	public StaticNodeBuilder setSize(int height, int width) {
		this.height = height;
		this.width = width;
		return this;
	}

	/**
	 * Set the image path for the node
	 * @param path the path of the image that will be shown for this node
	 * @return Return the same static node builder
	 */
	public StaticNodeBuilder setImagePath(String path) {
		this.imagePath = path;
		return this;
	}

	/**
	 * Create the {@code StaticNode} with all characteristics set at the moment
	 * @return Return a {@code StaticNode}.
	 * @throws FileNotFoundException <p>
	 *     if the path is not found it will throw an exception
	 * </p>
	 */
	public StaticNode build() throws FileNotFoundException {
		return new StaticNode(group, xPos, yPos, height, width, imagePath);
	}
}
