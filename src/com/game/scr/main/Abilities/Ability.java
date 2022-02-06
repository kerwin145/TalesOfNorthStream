package com.game.scr.main.Abilities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.game.scr.main.Game;

public abstract class Ability {

	int id = 0;
	String AbilityText;
	boolean equipped; 
	boolean selected = false;
	Game game;
	int cost;
	BufferedImage texture;
	
	long coolDownTime;
	boolean ready = false;
	
	long lastUse = 0;
	long now = 0;
	long activeFor = 0;
	long duration = 0;
	int timesUsed = 0;
	
	int level = 0;
	int maxLevel;
	
	public Ability(int id, String AbilityText, boolean equipped, int cost, Game game, long coolDownTime, long duration, int maxLevel) {
		this.id = id;
		this.AbilityText = AbilityText;
		this.equipped = equipped;
		this.cost = cost;
		this.game = game;
		this.coolDownTime = coolDownTime;
		this.duration = duration;
		this.maxLevel = maxLevel;
	}
	
	public abstract void execute();
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public abstract void upgrade();
		/*
		//responsible for incrementing level and updating cost and duration when needed
		
		format: 
		if (level == 0){ //this is the initial buy
			cost = //this is the first upgrade cost
		}else{
			//subsequent upgrade scaling
			//other traits in accordance to ability (CDT)
		}
		*/
	

	public BufferedImage getTexture() {
		return texture;
	}
	
	public void setTexture(BufferedImage tex) {
		texture = tex;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAbilityText() {
		return AbilityText;
	}
	
	public boolean isEquipped() {return equipped;}
	public void setEquipped(boolean hi) {equipped = hi;}
	
	public void setReady(boolean ready) { this.ready = ready;}
	public boolean getReady() {return ready;}
	
	public void setCoolDownTime(long x) {coolDownTime = x;}
	public long getCoolDownTime() {return coolDownTime;}

	public int getCost(){return cost;}
	
	public void setSelected(boolean selected) {this.selected = selected;}
	public boolean getSelected() {return selected;}
	
	public long getDuration() {return duration;}
	public long getActiveFor() {return activeFor;}
	
	public int getTimesUsed() {return timesUsed;}
	
	public int getMaxLevel() {return maxLevel;}
	public int getLevel() {return level;}
	public void setLevel(int level) {this.level = level;}

	public void prepareCooldowns(long currentTick) {
		lastUse = currentTick;
		now = currentTick;
		activeFor = duration;
	}
	
	public String coolDown() {
		if ((coolDownTime - (now - lastUse))/60 >= 0)
			return (int)((coolDownTime - (now - lastUse))/60) + " Sec left";
		else 
			return "0 Sec left";
	}
	//YOU CAN ONLY EQUIP THREE ABILITIES. Duration will be in TICKS. 
	
	//no reload time. Upgrade: Duration, cool down time  DONE

	//all direction bullets. Upgrade: Duration, cool down time DONE
	
	//shock waves (percentage of enemies defeated). Upgrade: percentage
	
	//infinite pierce. Upgrade: Duration DONE
	
	//heal. Upgrade: Amount Healed DONE
	
	//Invincibility. Upgrade: Duration, Cooldown (Separate) DONE

}
