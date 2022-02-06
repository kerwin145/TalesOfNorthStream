package com.game.scr.main;

public class Transaction {

	
	//add player speed upgrade
	//add fireball reload upgrade
	Game game;	
	Player p;
	
	int healthLevel = 1; 
	int maxHealthLevel = 16;
	int healthCost = (int) (Math.pow((double)healthLevel, 2) * 300 + 200);

	int fireballLevel = 1;
	int maxFireballLevel = 8;
	int fireballCost = (int)(Math.pow((double)fireballLevel, 3) * 200);
	int upgradeFireballSpeedModifier;
	
	int reloadLevel = 1;
	int maxReloadLevel = 7;
	int reloadCost = (int)(Math.pow((double)reloadLevel + 2, 2) * 1200);
	
	int pierceLevel = 1;
	int maxPierceLevel = 4;
	int pierceCost = (int)(Math.pow((double)pierceLevel + 1, 4) * 300 + 8000);
	
	Transaction(Game game, Player p){
		this.game = game;
		this.p = p;
	}
	
	
	public void purchaseHealthUpgrade() {
		
		if ((game.galaxyBux - healthCost >= 0) && (healthLevel  < maxHealthLevel)) {
			
			game.galaxyBux -= healthCost;
			
			healthLevel++;
			
			game.MAXHEALTH += 1;

			healthCost = (int) (Math.pow((double)healthLevel, 2) * 300);
		}
		
	}
	
	public void purchaseFireballUpgrade() {
		
		if ((game.galaxyBux - fireballCost >= 0) && (fireballLevel < maxFireballLevel)){
			
			game.galaxyBux -= fireballCost;
			
			fireballLevel++;
			
			upgradeFireballSpeedModifier = fireballLevel;

			fireballCost = (int)(Math.pow((double)fireballLevel, 3) * 200);
		}
		
	}
	
	public void purchaseReloadUpgrade() {
		
		if ((game.galaxyBux - reloadCost >= 0) && (reloadLevel < maxReloadLevel)){
			
			game.galaxyBux -= reloadCost;
			
			reloadLevel ++;
			
			game.setReloadTime(game.getReloadTime() - 25);
			
			reloadCost = (int)(Math.pow((double)reloadLevel + 2, 2) * 1200);
			
		}
		
	}

	public void purchasePierceUpgrade() {
		
		if ((game.galaxyBux - pierceCost >= 0) && (pierceLevel < maxPierceLevel)){
			
			game.galaxyBux -= pierceCost;
			
			pierceLevel++;
			
			pierceCost = (int)(Math.pow((double)pierceLevel + 1, 4) * 300);
			
			game.setPierce(pierceLevel);
			
		}
	}

	public void purchaseAbilityUpgrade(String abilityName){
		int cost;
		int galaxyBux = game.galaxyBux;
		int abilityIndex = 0;
		int abilityLvl, abilityMaxLvl;

		switch(abilityName){
			case "Heal":
				abilityIndex = 1;
				break;
			case "Invinciblity":
				abilityIndex = 2;
				break;
			case "InfinitePierce":
				abilityIndex = 3;
				break;
			case "OmniFireball":
				abilityIndex = 4;
				break;
			case "InstaReload":
				abilityIndex = 5;
				break;
			case "Shockwave":
				abilityIndex = 6;
				break;
		}

		cost = game.Abilities[abilityIndex].getCost();
		abilityLvl = game.Abilities[abilityIndex].getLevel();
		abilityMaxLvl = game.Abilities[abilityIndex].getMaxLevel();

		if(galaxyBux >= cost && abilityLvl < abilityMaxLvl){
			game.galaxyBux -= cost;
			game.Abilities[abilityIndex].upgrade();
 		}
	}

	//**************GETTERS**************//
	
	public int getHealthLevel() {
		return healthLevel;
	}
	
	public int getHealthCost() {
		return healthCost;
	}
	
	public int getMaxHealthLevel() {
		return maxHealthLevel;
	}
	public int getFireballLevel() {
		return fireballLevel;
	}
	
	public int getFireballCost() {
		return fireballCost;
	}
	
	public int getMaxFireballLevel() {
		return maxFireballLevel;
	}
	
	public int getupgradeFireballSpdMod() {
		return upgradeFireballSpeedModifier;
	}
	
	public int getReloadLevel() {
		return reloadLevel;
	}
	
	public int getReloadCost() {
		return reloadCost;
	}
	
	public int getMaxReloadLevel() {
		return maxReloadLevel;
	}
	
	public int getPierceLevel() {
		return pierceLevel;
	}
	
	public int getPierceCost() {
		return pierceCost;
	}
	
	public int getMaxPierceLevel() {
		return maxPierceLevel;
	}
}
