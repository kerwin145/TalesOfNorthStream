package com.game.scr.main.Abilities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.scr.main.Game;

public class Heal extends Ability{

	int healAmount = 1;
	Random r = new Random();
	
	public Heal(int id, String AbilityText, boolean equipped, int cost, Game game, 	long coolDownTime, long duration, int maxLevel) {
		super(id, AbilityText, equipped, cost, game, coolDownTime, duration, maxLevel);	
		this.coolDownTime = coolDownTime;
		this.duration = duration;
	}
	
	public void execute() {
		
		if (Game.HEALTH < Game.MAXHEALTH) {
			if (ready) {			
				int heal = r.nextInt(level) + 1;
				
				for (int i = 0; i < heal; i++) {
					if (Game.HEALTH < Game.MAXHEALTH) {
						Game.HEALTH += 1;	
						System.out.println("Heal: " + heal);
					}
				}
				lastUse = game.tickNumber;	
			}
		}
		
		System.out.println("READY: " + ready + ", Now: "+ now + ", lastUse :" + lastUse + " ,CoolDownTime: " + coolDownTime);

		
	}
	
	public void tick() {
		now = game.tickNumber;
	//	System.out.println("PPOOPO" + (now - lastUse));
		
		if ((now - lastUse) >= coolDownTime) 
			ready = true;
		else
			ready = false;
	}
	public void render(Graphics g) {
		
	}

	public void upgrade(){
		if(level == 0){
			cost = 8000;
		}else{
			cost = (int)(cost * 1.8 + 1000);
		}
		level++;
	
	}
	
	public void prepareCooldowns(long currentTick) {
		lastUse = currentTick;
		now = currentTick;
	}
	
	public String coolDown() {
		if ((coolDownTime - (now - lastUse))/60 >= 0)
			return (int)((coolDownTime - (now - lastUse))/60) + " Sec left";
		else 
			return "Ready!";	
	}
	
}


