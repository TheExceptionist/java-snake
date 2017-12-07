package net.thexceptionist.other;

import java.awt.Graphics;
import java.awt.Rectangle;

import net.thexceptionist.main.GameMain;


public class GameObject {
	//Initialize ids to tell objects apart from each other
	public static final int BALL_ID = 0;
	public static final int PLAYER1_ID = 1;
	public static final int PLAYER2_ID = 2;
	
	//Define constants to whether the player stops or moves 
	public static final int STOP = 0;
	public static final int MOVE = 1;
	
	//define keys
	public static final int KEY_UP = 0;
	public static final int KEY_DOWN = 1;
	public static final int KEY_LEFT = 2;
	public static final int KEY_RIGHT = 3;
	
	
	protected int x, y, width, height;
	//Velocity of the players and the ball
	protected int velX, velY;
	protected int id;
	
	//Number of hits on a ball
	protected int hits;
	
	public GameObject (final int x, final int y, final int width, final int height, final int id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
	}
	
	public void update(final Graphics g){
		//Constantly added the velocity to teh position of an object
		x += velX;
		y += velY;
		
		if (y <= 0) {
			y = 2;
			reboundY();		
		}
		//Adust so the ball rebounds once it reachs one edge of the screen
		if (y >= GameMain.HEIGHT - 32) {
			//SEt the y so the ball doesn't get stuck on one side of the screen
			y = GameMain.HEIGHT - 34;
			reboundY();
		}
	}

	public int getX() {
		return x;
	}

	public void setX(final int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return a Rectangle object to detect collision
	 */
	public Rectangle getBoxCollision(){	
		return new Rectangle(x, y, width, height);
	}

	/**
	 * Change X velocity of the ball
	 */
	public void reboundX() {
		velX *= -(Math.floor(Math.random() * 2) + 1);
		hits++;
	}
	
	/**
	 * Change Y velocity of the ball
	 */
	public void reboundY() {
		velY *= -1;
	}
	
	/**
	 * Prevent a int from going above or below the max and mins
	 */
	public static int clamp(int var, int max, int min){
		if (var > max) return max;
		else if (var < min) return min;
		else return var;
	}
	
}
