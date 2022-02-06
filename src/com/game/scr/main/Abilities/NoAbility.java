package com.game.scr.main.Abilities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.game.scr.main.Game;

public class NoAbility extends Ability{

	
	public NoAbility(int id, String AbilityText, boolean equipped, int cost, Game game, long coolDownTime, long duration, int maxLevel) {
		super(id, AbilityText, equipped, cost, game, coolDownTime, duration, maxLevel);
		
	}
	
	public void execute() {

	}
	
	public void tick() {
		
	}

	
	public void upgrade() {

	}
	
	public void render(Graphics g) {
		
	}
}


