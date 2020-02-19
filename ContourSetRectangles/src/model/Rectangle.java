package model;

import java.util.HashMap;
import java.util.Map;

public class Rectangle {

	public static Map<Integer, Rectangle> map = new HashMap<Integer, Rectangle>();
	
	private int leftX;
	private int rightX;
	private int leftY;
	private int rightY;
	
	public Rectangle(int leftX, int rightX, int leftY, int rightY) {
		super();
		this.leftX = leftX;
		this.rightX = rightX;
		this.leftY = leftY;
		this.rightY = rightY;
	}

	public int getLeftX() {
		return leftX;
	}

	public int getRightX() {
		return rightX;
	}

	public int getLeftY() {
		return leftY;
	}

	public int getRightY() {
		return rightY;
	}
	
	@Override
	public String toString() {
		return leftX + " " + rightX + " " + leftY + " " + rightY;
	}
}
