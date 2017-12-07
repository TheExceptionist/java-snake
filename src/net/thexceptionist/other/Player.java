package net.thexceptionist.other;

import java.awt.Color;
import java.awt.Graphics;

import net.thexceptionist.main.GameEngine;
import net.thexceptionist.main.GameMain;

public class Player extends GameObject{
	private static final int PLAYER_SPEED = 2;
	
	private Color color;
	private boolean isAI = false;
	public int[] direction;
	
	//number to tell us how munch range the ai is can detect the ball
	public int range = GameMain.WIDTH/2 + 20;
	
	public Player(final int x, final int y, final int width, final int height, final int id) {
		super(x, y, width, height, id);
		if(id == GameObject.PLAYER2_ID) isAI = true;
	}
	
	public void update(final Graphics g){
		super.update(g);
		
		direction = GameEngine.direction;
		
		if (!isAI) {
			if (direction[GameObject.KEY_UP] == GameObject.MOVE) velY = -PLAYER_SPEED;
			else if (direction[GameObject.KEY_DOWN] == GameObject.MOVE) velY = PLAYER_SPEED;
			else velY = 0; 
				
			
			//Tell the engine to stop moving the player
			//Without this the player would move in one direction indefinitely
			//GameEngine.direction = GameObject.STOP;
			
		} else {
			//Ai stuff here
			//Unnecessary since the players are only moving across the y axis
			int targetX = GameEngine.getBallPos()[GameEngine.BALL_X];
			int targetY = GameEngine.getBallPos()[GameEngine.BALL_Y];
			
			if (targetX > range){
				//Set the ai movement speed
				if (targetY < y) velY = -PLAYER_SPEED/2;
				else if (targetY > y) velY = PLAYER_SPEED/2;
				else velY = 0; 
			} else {
				//If the bal isn't int range the ai stops moving
				velY = 0;
			}
		}
		
		g.fillRect(x, y, width, height);
		g.setColor(color);
	}
}
