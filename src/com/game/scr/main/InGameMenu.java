package com.game.scr.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import stringMethods.stringGraphics;

public class InGameMenu {
	
	Game game;
	public InGameMenu(Game game) {
		this.game = game;
	}

	public Rectangle pauseButton = new Rectangle(Game.WIDTH*Game.SCALE - 60, 5, 50, 20);
	public Rectangle nextRoundButton = new Rectangle(Game.WIDTH*Game.SCALE - 170, 5, 100, 20);
	public Rectangle showHitBox = new Rectangle(Game.WIDTH*Game.SCALE - 280, 5, 100, 20);
	public Rectangle restartButton = new Rectangle(Game.WIDTH*Game.SCALE - 360, 5, 70, 20);
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		Font fnt0 = new Font("Arial", Font.BOLD, 10);
		g.setFont(fnt0);
		g.setColor(Color.white);

		g2d.draw(pauseButton);
		g2d.draw(nextRoundButton);
		g2d.draw(showHitBox);		
		g2d.draw(restartButton);
		
		stringGraphics.drawStringCentered("MENU", pauseButton, g);
		
		if (game.getRound() % 5 == 4)
			g.setColor(Color.orange);
		else
			g.setColor(Color.white);
		
		g.setColor(Color.white);
		stringGraphics.drawStringCentered("START ROUND " + game.round, nextRoundButton, g);
		stringGraphics.drawStringCentered("SHOW HIT BOXES", showHitBox, g);
		stringGraphics.drawStringCentered("END GAME", restartButton, g);

	}
	
}
