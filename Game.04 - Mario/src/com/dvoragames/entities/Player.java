package com.dvoragames.entities;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dvoragames.main.Game;
import com.dvoragames.world.Camera;
import com.dvoragames.world.Enemy;
import com.dvoragames.world.World;


public class Player extends Entity{
	
	public static boolean moved;
	public boolean right,left,jump,down;
	
	public boolean isJump, isJumpR, isJumpL;
	public double jumpHeight = 20;
	public double jumpTime = 0;
	public boolean isHold;
	
	public static int lifeA = 2;
	public static int lifeT = 5;
	public boolean timeLife;
	public int timeL = 0;
	
	public static int point = 0;
	
	public int d_none = 0, d_r = 1, d_l = 2, d_u = 3, d_d = 4,d_ur = 5,d_ul = 6;
	public int dir = d_none;
	
	private int frames = 0, maxFrames = 15,index = 0, maxIndex = 3;
	private int frames1 = 0, maxFrames1 = 20,index1 = 0, maxIndex1 = 1;

	private double gravity = 2;
	
	public BufferedImage[] playerStop;
	public BufferedImage[] playerR;
	public BufferedImage[] playerL;
	public BufferedImage[] playerU;
	public BufferedImage[] playerD;

	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		playerStop = new BufferedImage[2];
		playerR = new BufferedImage[4];
		playerL = new BufferedImage[4];
		playerU = new BufferedImage[3];
		playerD = new BufferedImage[1];

		
		for(int i = 0; i < 2; i++) {
			playerStop[i] = Game.spritesheet.getSprite(0 + (i*16), 80, 16, 16);
		}
		
		for(int i = 0; i < 4; i++) {
			playerR[i] = Game.spritesheet.getSprite(0 + (i*16), 96, 16, 16);
		}
		
		for(int i = 0; i < 4; i++) {
			playerL[i] = Game.spritesheet.getSprite(0 + (i*16), 112, 16, 16);
		}
		
		playerU[0] = Game.spritesheet.getSprite(48, 80, 16, 16);
		playerU[1] = Game.spritesheet.getSprite(64, 96, 16, 16);
		playerU[2] = Game.spritesheet.getSprite(64, 112, 16, 16);
		
		playerD[0] = Game.spritesheet.getSprite(32, 80, 16, 16);

	}
	
	public void tick(){
		depth = 2;
		moved = false;
		dir = d_none;
		
		for(int i = 0; i < Game.entities.size();i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Coin) {
				if(Entity.isColidding(this, e)) {
					point+=1000;
					Game.entities.remove(i);
				}
			}
		}
		
		if(World.isFree((int)x, (int)(y+gravity)) && isJump == false) {
			y+=gravity;
			for(int i = 0; i < Game.entities.size();i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
					if(Entity.isColidding(this, e)) {
						isJump = true;
						((Enemy) e).vida--;
					}
						
					if(((Enemy) e).vida == 0) {
						Game.entities.remove(i);
						point+=100;
						break;
					}
				}
			}
		}else {
			for(int i = 0; i < Game.entities.size();i++) {
				Entity e = Game.entities.get(i);
				if(e instanceof Enemy) {
					if(timeLife == false) {
						if(Entity.isColidding(this, e)) {
							lifeA--;
							timeLife = true;
							if(lifeA == 0) {
								lifeT--;
								World.restartLevel("level1.png");
								if(lifeT == 0) {
									World.restartGame("level1.png");
								}
							}
						}
					}
				}
			}
		}
		
		if(timeLife == true) {
			timeL ++;
			if(timeL == 10) {
				timeLife = false;
				timeL = 0;
			}
		}
		
		if(right && World.isFree((int)(x+speed), (int)y)) {
			moved = true;
			dir = d_r;
			x+=speed;
		}else if(left && World.isFree((int)(x-speed), (int)y)) {
			moved = true;
			dir = d_l;
			x-=speed;
		}else if(down) {
			moved = false;
			dir = d_d;
		}
		
		else if(jump) {
			if(!World.isFree(this.getX(), this.getY()+1)) {
				isJump = true;
			}else {
				jump=false;
			}
		}
		if(jump && right) {
			if(!World.isFree(this.getX(), this.getY()+1)) {
				isJumpR = true;
			}else {
				jump = false;
			}
		}else if(jump && left) {
			if(!World.isFree(this.getX()-1, this.getY()+1)) {
				isJumpL = true;
			}else {
				jump = false;
			}
		}
		
		if(isJump) {
			if(World.isFree(this.getX(), this.getY()-2) && isHold == true) {
				moved = false;
				dir = d_u;
				y-=2;
				jumpTime+=1;
				if(jumpTime == jumpHeight) {
					isJump = false;
					isJumpR = false;
					isJumpL = false;
					jump = false;
					jumpTime = 0;
				}
			}else {
				isJump = false;
				isJumpR = false;
				isJumpL = false;
				jump = false;
				jumpTime = 0;
			}
		}
		if(isJumpR ) {
			if(World.isFree(this.getX(), this.getY()-2) && isHold == true) {
				isJump = true;
				moved = true;
				dir = d_ur;
			}else {
				isJump = false;
				isJumpR = false;
				isJumpL = false;
				jump = false;
				jumpTime = 0;
			}
		}if(isJumpL) {
			if(World.isFree(this.getX(), this.getY()-2) && isHold == true) {
				isJump = true;
				moved = true;
				dir = d_ul;
			}else {
				isJump = false;
				isJumpR = false;
				isJumpL = false;
				jump = false;
				jumpTime = 0;
			}
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}else if(!moved) {
			frames1++;
			if(frames1 == maxFrames1) {
				frames1 = 0;
				index1++;
				if (index1 > maxIndex1) {
					index1 = 0;
				}
			}
		}
		
		Camera.x = Camera.clamp((int) (x - Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp((int) (y - Game.HEIGHT), 0, World.HEIGHT*16 - Game.HEIGHT);
	}

	public void render(Graphics g) {
		if(lifeA > 1) {
			if(!moved) {
				if(dir == d_none) {
					g.drawImage(playerStop[index1], this.getX()-Camera.x, this.getY()-Camera.y, null);	
				}
				if(dir == d_u ) {
					g.drawImage(playerU[0], this.getX()-Camera.x, this.getY()-Camera.y, null);	
				}else if(dir == d_d) {
					g.drawImage(playerD[0], this.getX()-Camera.x, this.getY()-Camera.y, null);
				}
			}else {
				if(dir == d_r) {
					g.drawImage(playerR[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
				}else if(dir == d_l) {
					g.drawImage(playerL[index], this.getX()-Camera.x, this.getY()-Camera.y, null);
				}
				
				if(dir == d_ur) {
					g.drawImage(playerU[1], this.getX()-Camera.x, this.getY()-Camera.y, null);
				}else if(dir == d_ul) {
					g.drawImage(playerU[2], this.getX()-Camera.x, this.getY()-Camera.y, null);
				}
			}
		}else {
			g.drawImage(playerD[0], this.getX()-Camera.x, this.getY()-Camera.y, null);
		}
	}
}
