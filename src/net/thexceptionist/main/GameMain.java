package net.thexceptionist.main;

import javax.swing.JFrame;

public class GameMain {
	//Initialize constants such as the grid tile size, width and height of the window
	public static final int WIDTH = 1000, HEIGHT = 600;
	public static final int TILE_SIZE = 16;
	//Title of the window
	public static final String title = "Pong";
	
	public static void main(String[] args){
		//Setup the window (called a JFrame in Java)
		JFrame window = new JFrame(GameMain.title);
		GameEngine game = new GameEngine();
		
		//Initialize some window stuff
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(GameMain.WIDTH, GameMain.HEIGHT);
		window.setVisible(true);
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.add(game);
		
		//Called the game object's start method
		game.start();
	}
}
