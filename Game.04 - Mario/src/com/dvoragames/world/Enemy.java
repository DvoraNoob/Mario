package com.dvoragames.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dvoragames.entities.Entity;
import com.dvoragames.main.Game;

public class Enemy extends Entity{
	
	public int gravity = 2;
	private boolean toRight = false,toLeft = true;
	private int countR = 0,countL = 0;
	
	public int vida = 2;

	public static BufferedImage enemyE[];
	public static BufferedImage enemyD = Game.spritesheet.getSprite(112, 80, 16, 16);
	
	private int frames = 0, maxFrames = 15,index = 0, maxIndex = 1;
	
	public Enemy(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		enemyE = new BufferedImage[2];
		for(int i = 0;i < 2; i++) {
			enemyE[i] = Game.spritesheet.getSprite(80 + (i*16), 80, 16, 16);
		}
	}
	
	public void tick() {
		if(World.isFree((int)x, (int)(y+gravity))) {
			y+=gravity;
		}
		
		isPatrolling();
		
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
	}
	
	public void isPatrolling() {
		if(toRight == true && World.isFree((int)(x+speed), this.getY())) {
			x+=speed;
			countR++;
			if((!World.isFree((int)(x+speed),  this.getY())) || countR == 300 || 
					World.isFree((int) x+16, this.getY()+1)) {
				toRight = false;
				toLeft = true;
				countR = 0;
			}
		}
		else if(toLeft == true && World.isFree((int)(x-speed), this.getY())) {
			x-=speed;
			countL++;
			if((!World.isFree((int)(x-speed),  this.getY())) || countL == 200|| 
					World.isFree((int) x-16, this.getY()+1)) {
				toRight = true;
				toLeft = false;
				countL = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		if(vida > 1) {
			g.drawImage(enemyE[index], getX() - Camera.x, getY() - Camera.y, null);
		}else {
			g.drawImage(enemyD, getX() - Camera.x, getY() - Camera.y, null);
		}
	}
}
