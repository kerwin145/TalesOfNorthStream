package com.game.scr.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import stringMethods.stringGraphics;


public class Menu {
	
	Game game;
	
	Menu(Game game){
		this.game = game;
	}

	public Rectangle playButton = new Rectangle(Game.WIDTH*Game.SCALE/2 - 75, 150, 150, 50);
	public Rectangle helpButton = new Rectangle(Game.WIDTH*Game.SCALE/2 - 75, 250, 150, 50);
	public Rectangle upgradeButton = new Rectangle(Game.WIDTH*Game.SCALE/2 - 75, 350, 150, 50);
	public Rectangle quitButton = new Rectangle(Game.WIDTH*Game.SCALE/2 -75, 450, 150, 50);

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		Font fnt0 = new Font("Arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("Tales of North Stream", Game.WIDTH/2, 100);
		
		Font fnt1 = new Font("Arial", Font.BOLD, 25);
		g.setFont(fnt1);
		
		g.setColor(Color.orange);
		g2d.draw(playButton);
		
		g.setColor(Color.cyan);
		g2d.draw(helpButton);
		
		g.setColor(Color.red);
		g2d.draw(quitButton);
		
		if(game.Foes.size() == 0)
			g.setColor(Color.green);
		else
			g.setColor(Color.red);

		g2d.draw(upgradeButton);
		
		g.setColor(Color.white);
		stringMethods.stringGraphics.drawStringCentered("Play", playButton, g);
		stringMethods.stringGraphics.drawStringCentered("Help", helpButton, g);
		stringMethods.stringGraphics.drawStringCentered("Upgrades", upgradeButton, g);
		stringMethods.stringGraphics.drawStringCentered("Quit", quitButton, g);


	}
	
}
