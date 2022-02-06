
package com.game.scr.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;

import com.game.scr.main.Abilities.Ability;
import com.game.scr.main.Abilities.Heal;
import com.game.scr.main.Abilities.InfinitePierce;
import com.game.scr.main.Abilities.InstaReload;
import com.game.scr.main.Abilities.Invincibility;
import com.game.scr.main.Abilities.NoAbility;
import com.game.scr.main.Abilities.OmniFireball;
import com.game.scr.main.Abilities.Shockwave;
import com.game.scr.main.classes.Boss;
import com.game.scr.main.classes.Foe;
import com.game.scr.main.classes.Friend;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 560;
	public static final int HEIGHT = WIDTH / 8 * 5;
	public static final int SCALE = 2;
	public  final String TITLE = "TALES OF NORTH STREAM";
	
	private boolean running = false;
	private Thread thread;
		
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);	
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	private Player p;
	private Controller c;
	private Textures tex;
	private Menu menu;
	private InGameMenu igm;
	private InformationPanel iPanel;
	private UpgradeMenu um;
	private Transaction trans;
	private Random r = new Random();
	private KeyInput key = new KeyInput(this);
	private MouseInput mouse = new MouseInput(this);
	private Spawner spawn;
	
	public LinkedList<Friend> Friends;
	public LinkedList<Foe> Foes;
	public LinkedList<Boss> Boss;
	
	static long AbilityHealCDT = 2 * 60; //CDT == cool down time 
	static long AbilityInvincibilityCDT = 25 * 60, AbilityInvincibilityDuration = 5 * 60; //25
	static long AbilityInfinitePiercCDT = 20 * 60, AbilityInfinitePierceDuration = 4 * 60; //20
	static long AbilityOmniFireballCDT = 20 * 60, AbilityOmniFireballDuration = 3 * 60; //20
	static long AbilityInstaReloadCDT = 18 * 60, AbilityInstaReloadDuration = 4 * 60; //18
	static long AbilityShockwaveCDT = 25 * 60; //25

	private int reloadTime = 300;  //will be set after the player object has been created. 

	//these will be the abilities that you can choose from 
	//the ability constructor: 	
	//public Ability(int id, String AbilityText, boolean equipped, int cost, Game game, long coolDownTime, long duration, int maxLevel, int upgradeCost) {
	public NoAbility NoAbility = new NoAbility(1, "NONE", false, 0, this, 0, 0, 0);
	public Heal AbilityHeal = new Heal(0, "HEAL", false, 10000, this, AbilityHealCDT, 0, 5);
	public Invincibility AbilityInvincibility = new Invincibility(0, "INVINCIBLE", false, 12500, this, AbilityInvincibilityCDT, AbilityInvincibilityDuration, 6);
	public InfinitePierce AbilityInfinitePierce = new InfinitePierce(0, Character.toString('\u221E') + " PIERCE", false, 15000, this, AbilityInfinitePiercCDT, AbilityInfinitePierceDuration, 6);	
	public OmniFireball AbilityOmniFireball = new OmniFireball(0, "OMNI-FB", false, 15000, this, AbilityOmniFireballCDT, AbilityOmniFireballDuration, 5);
	public InstaReload AbilityInstaReload = new InstaReload(0, "INSTA-RD", false, 18000, this, AbilityInstaReloadCDT, AbilityInstaReloadDuration, 4);
	public Shockwave AbilityShockwave = new Shockwave(0, "SHOCKWAVE", false, 25000, this, AbilityShockwaveCDT, 0, 4);

	int abilitySelected = 0;
	Ability[] Abilities = {NoAbility, AbilityHeal, AbilityInvincibility, AbilityInfinitePierce,
			AbilityOmniFireball, AbilityInstaReload, AbilityShockwave};
	Ability[] equippedAbilities = {NoAbility, NoAbility, NoAbility};

	//public ArrayList<Ability> Abilities;
	private boolean instaReload = false;
	private boolean doShockwave = false; //i shouldn't really do this, but putting the code in shockwave execute is leading to null pointers...idk y 
	
	public boolean equipSelected = false;
	//private Ability tempEquip = Ability0;
	
	public static long tickNumber = 0; //used to measure time between last ability use and now. 
		
	public static enum STATE{
		MENU, 
		GAME,
		UPGRADE
	}
	
	public static STATE State = STATE.MENU;
	
	boolean rightPressed = false;
	boolean leftPressed = false;
	boolean upPressed = false;
	boolean downPressed = false;
	
	private int direction = 1; //same thing as orientation in Fireball, but for this class. 
	private int modifierX = -10, modifierY = -22; 
	//to modify the co-ords where fireballs will appear since rotations are not centered. I will have to tinker with this. 
	// -8 and -15 are the same modifiers when player is at direction 1. Each direction will have different modifiers. 
	
//upgrades
	int fireballSpeedModifier = 0; //bullets will travel faster if player is moving
	private boolean reloadReady = false;
	private boolean omniDirection = false;
	
	public static int MAXHEALTH = 15;
	public static int HEALTH = 15;//MAXHEALTH;
	public static int damageRecieved = 1;
	
	private int pierce = 1;;
		
//information panel
	public static int round = 1;
	public int score = 0;
	public int highScore = 0;
	public int galaxyBux = 90000000; 
	
	boolean help = false; //help screen
	boolean gameSummary = false; //arghh y cant i use graphics in mouse listener
	boolean showHitBox = false;
	public boolean GAMEOVER = true;

	private int pointsGained;
	
	public void init() {
		//you don't have to click in the tab to start the game.
		requestFocus();
		 
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			
			spriteSheet = loader.loadImage("/PlayerConcept4.png");
			background = loader.loadImage("/Background.jpg");
			
		}catch(IOException e) {
			e.printStackTrace();
		}

		tex = new Textures(this);
		
		c = new Controller(this, p, tex);
		p = new Player(200,200, tex, this, c);

		Friends = c.getFriend();
		Foes = c.getFoe();
		Boss = c.getBoss();
		
		menu = new Menu(this);
		igm = new InGameMenu(this);
		iPanel = new InformationPanel(this);
		um = new UpgradeMenu(this, p);
		trans = new Transaction(this, p);
		spawn = new Spawner(this, c);

				 
		this.addKeyListener(key);
		this.addMouseListener(mouse);
		
				
		NoAbility.setTexture(tex.NoAbilityIcon);
		AbilityHeal.setTexture(tex.HealIcon);
		AbilityInvincibility.setTexture(tex.InvincibilityIcon);
		AbilityInfinitePierce.setTexture(tex.InfinitePierceIcon);
		AbilityOmniFireball.setTexture(tex.OmniFireballIcon);
		AbilityInstaReload.setTexture(tex.InstaReloadIcon);
		AbilityShockwave.setTexture(tex.ShockwaveIcon);

	}

	private synchronized void start() {
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop() {
		if (!running)
			return;
		
		running = false;
		try {thread.join();} 
		catch (InterruptedException e) 
			{e.printStackTrace();}
		
		//System.exit(1);
	}
	
	public void run() {
		
		init();
		
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0; //calculates time passed to catch up
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		long fireBallCoolDownTimer =  System.currentTimeMillis();
		
		
		//long fireBallCoolDownTimer = System.currentTimeMillis();
		
		//GAME LOOP!!!
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if (delta >= 1) { //if one sixtieth of a second has passed
				tick();
				updates++;
				tickNumber++;
				delta--; 
			}
			
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
			}
		if(!instaReload) {
			if (System.currentTimeMillis() - fireBallCoolDownTimer > reloadTime) {
				fireBallCoolDownTimer += reloadTime;
				reloadReady = true;
			}
		}
		else
			reloadReady = true;
			
		}//end running
		
		stop();
		
	}//end run()
	
	private void tick() {
		
		if(State == STATE.GAME) {
	
			p.tick();
			c.tick();
			
			if(!GAMEOVER) {		
				equippedAbilities[0].tick();
				equippedAbilities[1].tick();
				equippedAbilities[2].tick();			
			}
			//System.out.println("READIES + " + AbilityONE.getReady() + AbilityTWO.getReady() + AbilityTHREE.getReady());
			
			if (HEALTH <= 0) {
				round = 1;
				score = 0;
				
				while(0 != Foes.size())
					Foes.remove(0);
				while(0 != Boss.size())
					Boss.remove(0);
				
				GAMEOVER = true;
			}
			
			if (score > highScore)
				highScore = score;
			
			if ((Foes.size() == 0) && (!(round == 1)) && (Boss.size() == 0)) {
				spawn.normalWave();
				spawn.spawnBoss();
			}
		}
		
		if(State == STATE.UPGRADE) {
			um.tick();
		}

	}
	
	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) { //create bufferstrategy once
			createBufferStrategy(3); //loads three images at a time.
			return;
		}
		
		Graphics g = bs.getDrawGraphics(); //creates graphics context (draws out the buffers)
		
		//****************************---------****************************//
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(background, 0,0, null);
		
		if (State == STATE.UPGRADE) {
			um.render(g);
		}
		
		if(State == STATE.GAME) {
			p.render(g);
			c.render(g);
			igm.render(g);
			iPanel.render(g);
			
			equippedAbilities[0].render(g);
			equippedAbilities[1].render(g);
			equippedAbilities[2].render(g);
		}
		
		else if(State == STATE.MENU) {
			menu.render(g);
			if (help == true)
				help(g);
		}

		//****************************---------****************************//
		
		g.dispose();
		bs.show();
	}
		
	public static void main(String args[]) {
		System.out.println("Working");

		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack(); 
		frame.setLocationRelativeTo(null); //needs to be after pack
		frame.setVisible(true);
				
		game.start(); 
		
		//InputMap im = frame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

		
	}

	public void newGame(){
		reloadTime = 300;
		/*
		public NoAbility NoAbility = new NoAbility(1, "NONE", false, 0, this, 0, 0, 0);
		public Heal AbilityHeal = new Heal(0, "HEAL", false, 10000, this, AbilityHealCDT, 0, 5);
		public Invincibility AbilityInvincibility = new Invincibility(0, "INVINCIBLE", false, 12500, this, AbilityInvincibilityCDT, AbilityInvincibilityDuration, 6);
		public InfinitePierce AbilityInfinitePierce = new InfinitePierce(0, Character.toString('\u221E') + " PIERCE", false, 15000, this, AbilityInfinitePiercCDT, AbilityInfinitePierceDuration, 6);	
		public OmniFireball AbilityOmniFireball = new OmniFireball(0, "OMNI-FB", false, 15000, this, AbilityOmniFireballCDT, AbilityOmniFireballDuration, 5);
		public InstaReload AbilityInstaReload = new InstaReload(0, "INSTA-RD", false, 18000, this, AbilityInstaReloadCDT, AbilityInstaReloadDuration, 4);
		public Shockwave AbilityShockwave = new Shockwave(0, "SHOCKWAVE", false, 25000, this, AbilityShockwaveCDT, 0, 4);
		*/
		//player setup
	}

	public void continueGame(){
		//load data
	}


// OOOOF GETTERS AND SETTERS :(	
	public BufferedImage getSpriteSheet() {return spriteSheet;}
	public Transaction getTransaction() {return trans;}
	public Player getPlayer() {return p;}
	public UpgradeMenu getUpgradeMenu() {return um;}
	public Controller getController() {return c;}
	public Textures getTex() {return tex;}
	public Spawner getSpawner() {return spawn;}
	
	public int getDirection() {return direction;}
	public void setDirection(int x) {direction = x;}
	
	public int getScore() {	return score;}
	public void setScore(int score) {this.score = score;}
	
	public int getHighScore() {return highScore;}

	public int getRound() {return round;}
	public void setRound(int round) {this.round = round;}
	public int getPointsGained() {return pointsGained;}
	public void setPointsGained(int pointsGained) {this.pointsGained = pointsGained;}

	public boolean getShowHitBox() {return showHitBox;}
	
	public boolean getGameSummary() {return gameSummary;}
	public void setGameSummary(boolean b) {gameSummary = b;}
	
	public boolean getReloadReady() {return reloadReady;}
	public void setReloadReady(boolean reloadReady) {this.reloadReady = reloadReady;}
	
	public int getReloadTime() {return reloadTime;}
	public void setReloadTime(int x) {reloadTime = x;}
	
	public LinkedList<Foe> getFoes() {return Foes;}
	public LinkedList<Friend> getFriends() {return Friends;}
	public LinkedList<Boss> getBoss(){return Boss;}
			
	public int getModifierX() {return modifierX;}
	public int getModifierY() {return modifierY;}
	public void setModifierX( int x) {modifierX = x;}
	public void setModifierY( int y) {modifierY = y;}
		
	//fireballspeedmodifiers. This is for upgrading speed of fireball. 
	public int getFireballSpeedModifier() {return fireballSpeedModifier;}
	public void setFireballSpeedModifier(int x) {fireballSpeedModifier = x;}
	
	public int getPierce() {return pierce;}
	public void setPierce(int pierce) {this.pierce = pierce;}
	
	public boolean getOmniDirection() {return omniDirection;}
	public void setOmniDirection(boolean hi) {omniDirection = hi;}
	
	public boolean getInstaReload() {return instaReload;}
	public void setInstaReload(boolean hello) {instaReload = hello;}
	
	public boolean getShockwave() {return doShockwave;}
	public void setShockwave(boolean goodDay) {doShockwave = goodDay;}
	
	//public ArrayList<Ability> getAbilities() {return Abilities;}
		
	public long getTickNumber() {return tickNumber;	}
	
	public Ability getAbilityONE() {return equippedAbilities[0];}
	public Ability getAbilityTWO() {return equippedAbilities[1];}
	public Ability getAbilityTHREE() {return equippedAbilities[2];}
	
	/*
	public Heal getAbilityHeal() {return AbilityHeal;}
	public Invincibility getAbilityInvincibility() {return AbilityInvincibility;}
	public InfinitePierce getAbilityInfinitePierce() {return AbilityInfinitePierce;}
	public OmniFireball getAbilityOmniFireball() {return AbilityOmniFireball;}
	public InstaReload getAbilityInstaReload() {return AbilityInstaReload;}
	public Shockwave getAbilityShockwave() {return AbilityShockwave;}
	*/		
//Arggh i couldn't figure out how to get this to work in the controller...so i guess its now going into the main smh.
	public void shootBullet() {
		
		if(reloadReady){
			if(!omniDirection) {
				System.out.println("Pew Pew");
				Friends.add(new Fireball(p.getX(), p.getY(), direction, this, c, tex));
				if (score >= 20) {
					score -= 20;
				}
			}
			else {
				System.out.println("Pew Pew!!");
				direction = 1;
				Friends.add(new Fireball(p.getX(), p.getY(), 1, this, c, tex));
				direction = 2;
				Friends.add(new Fireball(p.getX(), p.getY(), 2, this, c, tex));
				direction = 3;
				Friends.add(new Fireball(p.getX(), p.getY(), 3, this, c, tex));
				direction = 4;
				Friends.add(new Fireball(p.getX(), p.getY(), 4, this, c, tex));
				if (score >= 80) {
					score -= 80;
				}
			}
			
			reloadReady = false;
			
		}
		
	}	
	
	

////////
	public void help(Graphics g) {
		
		Font helpFont = new Font("Times Roman", Font.PLAIN, 15);
		g.setFont(helpFont);
		g.setColor(Color.white);
		
		
		g.drawString("TALES OF NORTH STREAM", 50, 100);
		g.drawString("Welcome to Tales of North Stream!", 50, 120);
		g.drawString("This is a \"Space Shooter\" type game", 50, 140);
		g.drawString("where you try to shoot down enemy space ships!", 50, 160);
		g.drawString("To move around, use the arrow keys.", 50, 180);
		g.drawString("To shoot, press space. The direction of the bullet", 50, 200);
		g.drawString("will be the same as the direction you are going.", 50, 220);
		g.drawString("To manually set the directino of your bullets, use the WASD keys.", 50, 240);

		g.drawString("To start round, press the x key or click the Start Round Button", 50, 260);
		g.drawString("You can start round even if you have not finished the current one!", 50, 280);
		
		g.drawString("You will score points by shooting down the enemy Spaceship.", 50, 320);
		g.drawString("More points will be added the higher the round, and the faster the enemy, ", 50, 340);
		
		g.drawString("- A fireball to the right will grant normal points.", 50, 380);
		g.drawString("- A fireball going up and down will grant higher points.", 50, 400);
		g.drawString("- A fireball to the left will grant the most points.", 50, 420);
		
		g.drawString("Next, you can select up to three abilities to equip.", 50, 460);
		g.drawString("These will correspond to the 1, 2 and 3 keys on your keyboard!", 50, 480);
		g.drawString("Visit the \"Upgrades\" screen to see more.", 50, 500);

		g.drawString("You also have the option to call in the next round early.", 50, 540);
		g.drawString("This will give you a higher multiplier for the remaining ships.", 50, 560);
		g.drawString("Be careful though, you don't want to overwhelm yourself...", 50, 580);
		g.drawString("spaceships will get faster over time if they aren't killed!", 50, 600);
		
		g.drawString("That's all. Press Play to begin.", 50, 640);
		g.drawString("Click Menu or press P key to pause the game if you need a break!", 50, 660);
		
		
		int displayX = WIDTH*SCALE - 400;
		int displayY = HEIGHT * SCALE / 2 - 50;
		g.drawString("This will be your avatar", displayX, displayY) ;
		g.drawImage(tex.Player, displayX, displayY + 10, this);
		
		g.drawString("This is the Glorpnorp, your spaceship-enemy", displayX, displayY + 70);
		g.drawImage(tex.GlorpNorp, displayX, displayY + 80, this);
		
		g.drawString("This is the fireball, your weapon against the Glornorp!", displayX, displayY + 140);
		g.drawImage(tex.FireballUp, displayX, displayY + 150, this);
		
	}
	
}
