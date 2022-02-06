package com.game.scr.main.Abilities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.game.scr.main.Game;

public class Invincibility extends Ability{

	
	public Invincibility(int id, String AbilityText, boolean equipped, int cost, Game game, long coolDownTime, long duration, int maxLevel) {
		super(id, AbilityText, equipped, cost, game, coolDownTime, duration, maxLevel);

	}
	
	public void execute() {
		
		if (ready) {			
			lastUse = game.tickNumber;	
			activeFor = 0;
			timesUsed++;
			ready = false;
		}

		System.out.println("READY: " + ready + ", Now: "+ now + ", lastUse :" + lastUse + " ,CoolDownTime: " + coolDownTime + "Duration: " + duration + "ActiveFor: " + activeFor);
		
	}
	
	public void tick() {
		now = game.tickNumber;
		activeFor++;
	//	System.out.println("PPOOPO" + (now - lastUse));
		
		if ((now - lastUse) >= coolDownTime) 
			ready = true;
		else 
			ready = false;
		
		
		if(activeFor < duration)
			game.damageRecieved = 0;
		
		else {
			game.damageRecieved = 1;
		}
	}
	
	public void render(Graphics g) {
		
	}

	public void upgrade(){
		if(level == 0){
			cost = 12000;
		}else{
			cost = (int)(cost * 1.2 + .2 * Math.pow(cost, 1.1));
			coolDownTime -= 120;
			System.out.println(cost);
		}
		level++;
	
	}
	

	public void prepareCooldowns(long currentTick) {
		lastUse = currentTick;
		now = currentTick;
		activeFor = duration;
	}
	
	public String coolDown() {
		if ((coolDownTime - (now - lastUse))/60 >= 0)
			return (int)((coolDownTime - (now - lastUse))/60) + " Sec left";
		else 
			return "Ready!";	}
	
}
