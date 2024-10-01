package main;

import java.awt.Color;

public class Tetrimono {
public final  char[][] shape;
public final Color color;
public int xPosition;
public int yPosition;
public int score;
	public Tetrimono(char[][] shape, Color color) {
		this.shape = shape;
		this.color=color;
		
		// TODO Auto-generated constructor stub
	}
	public char[][] getShape() {
		return shape;
	}
	
	public Color getColor() {
		return color;
	}
	public int getxPosition() {
		return xPosition;
	}
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	

}
