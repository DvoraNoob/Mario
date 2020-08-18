package com.dvoragames.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.dvoragames.entities.Player;
import com.dvoragames.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(new Color(255,255,255));
		g.setFont(new Font("ARIAL", Font.BOLD, 25));
		if(Player.lifeT < 10) {
			g.drawString("Life: 0" + Player.lifeT, 10, 30);
		}else {
			g.drawString("Life: " + Player.lifeT, 10, 30);
		}
		
		g.setColor(new Color(255,255,255));
		g.setFont(new Font("ARIAL", Font.BOLD, 25));
		if(Player.point == 0) {
			g.drawString("Points: " + Player.point, Game.WIDTH*Game.SCALE-109, 30);
		}else if(Player.point < 1000) {
			g.drawString("Points: " + Player.point, Game.WIDTH*Game.SCALE-136, 30);
		}else if(Player.point < 10000){
			g.drawString("Points: " + Player.point, Game.WIDTH*Game.SCALE-150, 30);
		}else {
			g.drawString("Points: " + Player.point, Game.WIDTH*Game.SCALE-177, 30);

		}
	}
}
