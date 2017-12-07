package net.thexceptionist.other;

import java.awt.Color;
import java.awt.Graphics;

import net.thexceptionist.main.GameEngine;
import net.thexceptionist.main.GameMain;

public class Ball extends GameObject{
	/**
	 * Todo: Set the color of the score blocks
	 * 		 Set the score of the score blocks
	 * 		 Set speed of the ball
	 */
	public static final int BALL_SPEED = 1;
	public static final int BALL_SPEED_MAX = 3;
	
	//the score the ball gives to the winning player
	private int score;
	//Color of the ball
	//Set using Color.BLACK or any other color
	private Color color;
	//Start position of the ball - shouldn't be anything other than the center of the screen
	private int startX, startY;
	
	/**
	 *  - Create a ball at position x,y 
	 *  -  with a size of width and height
	 *  -  with an id of BALL
	 * @param x - 
	 * @param y
	 * @param width
	 * @param height
	 * @param id
	 */
	public Ball(final int x, final int y, final int width, final int height, final int id) {
		super(x, y, width, height, id);
		
		this.startX = x;
		this.startY = y;
		
		score = 1;
		hits = 0;
		
		//Don't count this towards a player winning a round
		start(-1);
	}
	
	/**
	 * start to move the ball
	 * @param winner
	 */
	private void start(final int winner){
		x = startX;
		y = startY;
		velX = -Ball.BALL_SPEED;
		velY = -Ball.BALL_SPEED;
		
		if (GameEngine.PLAYER1_WIN == winner) GameEngine.addScore(score, GameEngine.PLAYER1_SCORE);
		if (GameEngine.PLAYER2_WIN == winner) GameEngine.addScore(score, GameEngine.PLAYER2_SCORE);
	}

	public void update(Graphics g){
		super.update(g);
		
		velX = GameObject.clamp(velX, BALL_SPEED_MAX, -BALL_SPEED_MAX);
		velY = GameObject.clamp(velY, BALL_SPEED_MAX, -BALL_SPEED_MAX);
	
		
		//If the ball reaches one of the ends - a player wins that round.
		if (x <= 0) {
			start(GameEngine.PLAYER2_WIN);		
		}
		if (x >= GameMain.WIDTH) {
			start(GameEngine.PLAYER1_WIN);
		}
		
		g.fillOval(x, y, width, height);
		g.setColor(color);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
