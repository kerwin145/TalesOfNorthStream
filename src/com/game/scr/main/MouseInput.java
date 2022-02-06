package com.game.scr.main;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import com.game.scr.main.Game.STATE;

public class MouseInput implements MouseListener{
	
	Game Game;

	public MouseInput(Game Game) {
		this.Game = Game;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX(); 
		int my = e.getY();
	
		//IN MENU
		if (Game.State == STATE.MENU) {
		//play Button
			if((mx >= Game.WIDTH*Game.SCALE/2 - 75)&&(mx <= Game.WIDTH*Game.SCALE/2 + 75)) {
				if((my >= 150)&&(my <= 200)){
					//Pressed Play Button
					Game.State = STATE.GAME; 
					Game.help = false;
				}
				
				else if ((my >= 250)&&(my <= 300)) {
					Game.help = true;
				}
				
				else if ((my >= 350)&&(my <= 400)&& (Game.Foes.size() == 0)) {
					Game.State = STATE.UPGRADE; 
				}
				
				else if ((my >= 450)&&(my <= 500)) {
					//Pressed Quit Button
					System.exit(1);
				}
				
			}
		}//IN MENU

		//IN GAME MENU
		else if(Game.State == STATE.GAME) {
			if ((my >= 5)&&(my <= 25)){
				//pause button
				if((mx >= Game.WIDTH*Game.SCALE - 60)&&(mx <= Game.WIDTH*Game.SCALE - 10)) {
					Game.State = STATE.MENU;
					Game.gameSummary = false;
				}
				
				//next round button
				else if((mx >= Game.WIDTH*Game.SCALE - 170)&&(mx <= Game.WIDTH*Game.SCALE - 70) && Game.Boss.size() == 0) {
					Game.getSpawner().normalWave();
					Game.getSpawner().spawnBoss();
				}
				
				//show hitbox button
				else if((mx >= Game.WIDTH*Game.SCALE - 280)&&(mx <= Game.WIDTH*Game.SCALE - 180)) {
					if (Game.showHitBox == true)
						Game.showHitBox = false;
					else
						Game.showHitBox = true;
				}
				
				//end game button
				else if((mx >= Game.WIDTH*Game.SCALE - 360)&&(mx <= Game.WIDTH*Game.SCALE - 290)) {
	
					while(0 != Game.Foes.size())
						Game.Foes.remove(0);
					while(0 != Game.Boss.size())
						Game.Boss.remove(0);
					
					Game.round = 1;
					Game.gameSummary = true;
					
				}
			}		
		}//IN GAME MENU
	
		//UPGRADE MENU
		else if(Game.State == STATE.UPGRADE){
			//return to menu
			if (((my >= 10)&&(my <= 35)) && ((mx >= Game.WIDTH*Game.SCALE - 140)&&(mx <= Game.WIDTH*Game.SCALE - 10)))
				Game.State = STATE.MENU;
			
			//x coords of upgrade buttons
			if((mx >= 200) && (mx <= 350)) {
				if ((my >= 100) && (my <= 140)) {
					Game.getTransaction().purchaseHealthUpgrade();
				}
				
				if ((my >= 170) && (my <= 210)) {
					Game.getTransaction().purchaseFireballUpgrade();
				}
				
				if ((my >= 240) && (my <= 280)) {
					Game.getTransaction().purchaseReloadUpgrade();
				}
				
				if ((my >= 310) && (my <= 350)) {
					Game.getTransaction().purchasePierceUpgrade();
				}
				
			}
			
			//row of buy buttons
			if(inBounds(e, Game.getUpgradeMenu().buyHeal)){
				Game.getTransaction().purchaseAbilityUpgrade("Heal");
			}
			else if(inBounds(e, Game.getUpgradeMenu().buyInvincibility)){
				Game.getTransaction().purchaseAbilityUpgrade("Invinciblity");
			}
			else if(inBounds(e, Game.getUpgradeMenu().buyInfinitePierce)){
				Game.getTransaction().purchaseAbilityUpgrade("InfinitePierce");
			}
			else if(inBounds(e, Game.getUpgradeMenu().buyOmniFireball)){
				Game.getTransaction().purchaseAbilityUpgrade("OmniFireball");
			}
			else if(inBounds(e, Game.getUpgradeMenu().buyInstaReload)){
				Game.getTransaction().purchaseAbilityUpgrade("InstaReload");
			}
			else if(inBounds(e, Game.getUpgradeMenu().buyShockwave)){
				Game.getTransaction().purchaseAbilityUpgrade("Shockwave");
			}
			//row of equip buttons. 
			if(inBounds(e, Game.getUpgradeMenu().equipHeal)){
				if (Game.AbilityHeal.getLevel() > 0) {
					if(!Game.AbilityHeal.isEquipped()) {
						Game.equipSelected = true;
						Game.abilitySelected = 1;
					}
					else if (Game.AbilityHeal.isEquipped()){
						unequipAbility(1);
					}
				}
			}
			else if(inBounds(e, Game.getUpgradeMenu().equipInvincibility)){
				if (Game.AbilityInvincibility.getLevel() > 0) {
					if(!Game.AbilityInvincibility.isEquipped()) {
						Game.equipSelected = true;
						Game.abilitySelected = 2;
					}
					else if (Game.AbilityInvincibility.isEquipped()){
						unequipAbility(2);
					}
				}
			}

			else if(inBounds(e, Game.getUpgradeMenu().equipInfinitePierce)){
				if (Game.AbilityInfinitePierce.getLevel() > 0) {
					if(!Game.AbilityInfinitePierce.isEquipped()) {
						Game.equipSelected = true;
						Game.abilitySelected = 3;
					}
					else if (Game.AbilityInvincibility.isEquipped()){
						unequipAbility(3);
					}
				}
			}
			else if(inBounds(e, Game.getUpgradeMenu().equipOmniFireball)){
				if (Game.AbilityOmniFireball.getLevel() > 0) {
					if(!Game.AbilityOmniFireball.isEquipped()) {
						Game.equipSelected = true;
						Game.abilitySelected = 4;
					}
					else if (Game.AbilityOmniFireball.isEquipped()){
						unequipAbility(4);
					}
				}
			}
			else if(inBounds(e, Game.getUpgradeMenu().equipInstaReload)){
				if (Game.AbilityInstaReload.getLevel() > 0) {
					if(!Game.AbilityInstaReload.isEquipped()) {
						Game.equipSelected = true;
						Game.abilitySelected = 5;
					}
					else if (Game.AbilityInstaReload.isEquipped()){
						unequipAbility(5);
					}
				}
			}
			else if(inBounds(e, Game.getUpgradeMenu().equipShockwave)){
				if (Game.AbilityShockwave.getLevel() > 0) {
					if(!Game.AbilityShockwave.isEquipped()) {
						Game.equipSelected = true;
						Game.abilitySelected = 6;
					}
					else if (Game.AbilityShockwave.isEquipped()){
						unequipAbility(6);
					}
				}
			}			
			
			//row of ability buttons. Here, you assign the ability you equip to a number on your keyboard (1 2 or 3)
			if ((my >= 570) && (my <= 610)) {
				//ability 1
				if ((mx >= 20) && (mx <= 120)) {
					if((Game.equipSelected)&&(!Game.equippedAbilities[0].isEquipped())){ //if you clicked the equip button, and the ability is not equipped
						Game.equippedAbilities[0] = Game.Abilities[Game.abilitySelected];
						Game.equippedAbilities[0].setId(Game.abilitySelected);
						Game.equippedAbilities[0].setEquipped(true);
						
					}
					else {
						Game.equippedAbilities[0].setId(0);
						Game.equippedAbilities[0].setEquipped(false);
						Game.equippedAbilities[0] = Game.NoAbility;
					}
					
					Game.equipSelected = false;
				}
				//ability 2
				if ((mx >= 140) && (mx <= 240)) {
					//equip  
					if((Game.equipSelected)&&(!Game.equippedAbilities[1].isEquipped())){ //if you clicked the equip button, and the ability is not equipped
						Game.equippedAbilities[1] = Game.Abilities[Game.abilitySelected];
						Game.equippedAbilities[1].setId(Game.abilitySelected);
						Game.equippedAbilities[1].setEquipped(true);
					}
					else {
						Game.equippedAbilities[1].setId(0);
						Game.equippedAbilities[1].setEquipped(false);
						Game.equippedAbilities[1] = Game.NoAbility;
					}
					Game.equipSelected = false;						
				}

				//ability 3
				if ((mx >= 260) && (mx <= 360)) {
					if((Game.equipSelected)&&(!Game.equippedAbilities[2].isEquipped())){ //if you clicked the equip button, and the ability is not equipped
						Game.equippedAbilities[2] = Game.Abilities[Game.abilitySelected];
						Game.equippedAbilities[2].setId(Game.abilitySelected);
						Game.equippedAbilities[2].setEquipped(true);
					}
					else {
						Game.equippedAbilities[2].setId(0);
						Game.equippedAbilities[2].setEquipped(false);
						Game.equippedAbilities[2] = Game.NoAbility;
					}
					
					Game.equipSelected = false;
				}
	
			}
		}//State Upgrade
				
	}
		
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	public void unequipAbility(int abilityId){
		for(int i = 0; i < Game.equippedAbilities.length; i++){
			if (Game.equippedAbilities[i].getId() == abilityId){
				Game.equippedAbilities[i].setEquipped(false);
				Game.equippedAbilities[i].setId(0);
				Game.equippedAbilities[i] = Game.NoAbility;
			}
		}
	}

	private boolean inBounds(MouseEvent e, Rectangle Rect){
		if(e.getX() >= Rect.x && e.getX() <= Rect.x + Rect.width
			&& e.getY() >= Rect.y && e.getY() <= Rect.y + Rect.height)
			return true;
		else return false;
	}
	
}
