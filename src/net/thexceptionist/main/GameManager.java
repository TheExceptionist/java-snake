package net.thexceptionist.main;

import java.awt.Graphics;
import java.util.LinkedList;

import net.thexceptionist.other.Ball;
import net.thexceptionist.other.GameObject;
import net.thexceptionist.other.Player;

public class GameManager {
	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	
	private int[] ballPos = new int[2];

	public void addObject(final GameObject object){
		objects.add(object);
	}
	
	public void removeObject(final GameObject object){
		objects.remove(object);
	}

	public void update(final Graphics g) {
		for (GameObject object : objects) {
			object.update(g);
			
			if (object instanceof Player) {
				//Unnecessary for now
				Player playerObject = (Player)object;
				
				for(GameObject ball : objects){
					if (ball instanceof Ball){
						//Set the pall position to pass to the game engine
						ballPos[GameEngine.BALL_X] = ball.getX();
						ballPos[GameEngine.BALL_Y] = ball.getY();
						
						if(ball.getBoxCollision().intersects(object.getBoxCollision())) {
							ball.reboundX();
						}
					}
				}
			}
		}
	}

	public int[] getBallPos() {
		return ballPos;
	}
}
