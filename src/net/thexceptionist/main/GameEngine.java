package net.thexceptionist.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import net.thexceptionist.other.Ball;
import net.thexceptionist.other.GameObject;
import net.thexceptionist.other.Player;
import net.thexceptionist.other.TextDisplay;

public class GameEngine extends Canvas implements Runnable{
	//Constants to the number of cols and rows of the grid. For ease
	public static final int ROWS = GameMain.WIDTH/GameMain.TILE_SIZE;
	public static final int COLS = (GameMain.HEIGHT/GameMain.TILE_SIZE) - 1;
	
	//Game Score
	private static int[] score = new int[2];
	//Constant to determine the score the player gets for hitting each block
	public static final int scorePerBlock = 5;
	
	//More Constants
	public static final int PLAYER1_WIN = 0;
	public static final int PLAYER2_WIN = 1;
	
	//More Constants
	public static final int PLAYER1_SCORE = 0;
	public static final int PLAYER2_SCORE = 1;
	
	//Determines if the game is running or not
	private boolean running = false;
	private Thread thread;
	
	//Displays the text at the top of the window
	private TextDisplay textDisplay;

	//Create the grid
	private int[][] grid = new int[GameEngine.ROWS][GameEngine.COLS];
	
	//Object manages the game
	private GameManager manager;
	
	//determines the direction the player is heading
	//array of 4 ints for the four directions
	public static int[] direction = {GameObject.STOP,GameObject.STOP,GameObject.STOP,GameObject.STOP};
	
	//Get the keys
	private KeyListener listener = new KeyListener(){

		@Override
		public void keyPressed(KeyEvent event) {
			if(event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_UP) direction[GameObject.KEY_UP] = GameObject.MOVE;
			if(event.getKeyCode() == KeyEvent.VK_S || event.getKeyCode() == KeyEvent.VK_DOWN) direction[GameObject.KEY_DOWN] = GameObject.MOVE;
			if(event.getKeyCode() == KeyEvent.VK_A || event.getKeyCode() == KeyEvent.VK_LEFT) direction[GameObject.KEY_LEFT] = GameObject.MOVE;
			if(event.getKeyCode() == KeyEvent.VK_D || event.getKeyCode() == KeyEvent.VK_RIGHT) direction[GameObject.KEY_RIGHT] = GameObject.MOVE;
			
			if(!GameEngine.roundStart) GameEngine.roundStart = true;
		}

		@Override
		public void keyReleased(KeyEvent event) {
			//Stop moving for all the keys once released
			if(event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_UP) direction[0] = GameObject.STOP;
			if(event.getKeyCode() == KeyEvent.VK_S || event.getKeyCode() == KeyEvent.VK_DOWN) direction[1] = GameObject.STOP;
			if(event.getKeyCode() == KeyEvent.VK_A || event.getKeyCode() == KeyEvent.VK_LEFT) direction[2] = GameObject.STOP;
			if(event.getKeyCode() == KeyEvent.VK_D || event.getKeyCode() == KeyEvent.VK_RIGHT) direction[3] = GameObject.STOP;
		}

		//These are both required, but not needed
		@Override
		public void keyTyped(KeyEvent event) {

		}
		
	};
	
	//Store the ball position for the AI
	private static int[] ballPos = new int[2];
	
	//More constants
	public static final int BALL_X = 0;
	public static final int BALL_Y = 1;
	
	//To determine when to start moving the ball
	public static boolean roundStart = false;
	
	//Init all resources need at the beginning
	public void start() {
		//Set running to true
		if (!running) running = true;
		thread = new Thread(this);
		//Create game manager
		manager = new GameManager();
		textDisplay = new TextDisplay();
		
		this.addKeyListener(listener);
		
		manager.addObject(new Ball(GameMain.WIDTH/2, GameMain.HEIGHT/3, 6, 6, 0));
		manager.addObject(new Player(16, 50, 16, 32, 1));
		manager.addObject(new Player(GameMain.WIDTH - 32, 50, 16, 32, 2));
		
		//Calls the run method below
		thread.start();
	}

	//Ignore this method as well
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		long lastTimer1 = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (unprocessed >= 1) {
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				update();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
			}
		}
	}
	/**
	 * Don't worry about anything in this method 
	 */
	private void update(){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
	
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, GameMain.WIDTH, GameMain.HEIGHT);
		
		textDisplay.update(g);
		
		//Only update th emanager if the round has started
		
		if(GameEngine.roundStart){
			manager.update(g);
		} else {
			textDisplay.renderStart(g);
		}
		
		ballPos = manager.getBallPos();
		
		g.dispose();
		bs.show();
	}

	/**
	 * 
	 * @return current score of the game
	 */
	public static int[] getScore() {
		return score;
	}

	/**
	 * 
	 * @param score - integer to set the current score to 
	 * @param player - the player to set the score to 
	 */
	public static void setScore(final int score, final int player) {
		GameEngine.score[player] = score;
	}

	/**
	 * 
	 * @param i - amount to add to the current score
	 * @param player - the player to set the score to.
	 * 
	 * Also ends the current round
	 */
	public static void addScore(final int i, final int player) {
		GameEngine.score[player] += i;	
		stopGame();
	}
	
	public static void stopGame(){
		GameEngine.roundStart = false;
	}

	public static int[] getBallPos() {
		return ballPos;
	}

	public void setBallPos(int[] ballPos) {
		this.ballPos = ballPos;
	}
}
