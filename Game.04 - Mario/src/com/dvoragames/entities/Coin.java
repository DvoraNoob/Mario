package com.dvoragames.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.dvoragames.main.Game;
import com.dvoragames.world.Camera;

public class Coin extends Entity{
	
	private int frames = 0, maxFrames = 7,index = 0, maxIndex = 3;
	
	public BufferedImage[] coin;

	public Coin(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		coin = new BufferedImage[4];
		for(int i = 0; i < 4; i++) {
			coin[i] = Game.spritesheet.getSprite(80 + (i*16), 96, 16, 16);
		}
	}
	
	public void tick() {
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(coin[index], getX()-Camera.x, getY()-Camera.y, null);
	}
}
