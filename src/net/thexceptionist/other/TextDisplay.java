package net.thexceptionist.other;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Scanner;

import net.thexceptionist.main.GameEngine;
import net.thexceptionist.main.GameMain;

public class TextDisplay {
	
	
	//Font to set
	private Font f = new Font("Papyrus", 5, 20);
	
	//2nd Font to set
	private Font f2 = new Font("Helvetica", 5, 40);
	
	//Color to set
	//Used Color.BLACK to set a color
	private Color color = Color.WHITE;
	
	//Draw to the window
	public void update(final Graphics g){
		g.setColor(color);
		g.setFont(f);
		g.drawString(GameMain.player1 + " || Score: "+GameEngine.getScore()[GameEngine.PLAYER1_SCORE], 150, 20);
		g.drawString("Opponent || Score: "+GameEngine.getScore()[GameEngine.PLAYER2_SCORE], GameMain.WIDTH - 330, 20);
		g.fillRect(GameMain.WIDTH/2 - 10, 0, 20, GameMain.HEIGHT);
	}

	public void renderStart(final Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.RED);
		g.setFont(f2);
		g.drawString("Ready to play?", GameMain.WIDTH/2 - 140, GameMain.HEIGHT/2 - 10);
	}
}
