package dodngun.warp;

import java.awt.Point;

import javax.swing.JLabel;

import dodngun.assat.Assat;
import dodngun.map.Map;

@SuppressWarnings("serial")
public class Warp extends JLabel{
	public static Warp[] warps = {new Warp(new Point(10, 300), 1, new Point(500, 10), 0),
								new Warp(new Point(500, 10), 0, new Point(10, 300), 1)};
	
	public Point wasPos;
	public Point isPos;
	public int wasMap;
	public int isMap;
	
	public Thread th;
	
	public Warp(Point wasPos, int wasMap, Point isPos, int isMap) {
		this.wasPos = wasPos;
		this.wasMap = wasMap;
		this.isPos = isPos;
		this.isMap = isMap;
		
		setIcon(Assat.warp);
		setToolTipText("워프 : " + Map.maps[isMap].name);
		setBounds(wasPos.x, wasPos.y, Assat.warp.getIconWidth(), Assat.warp.getIconHeight());
		Map.maps[wasMap].jl.add(this);
		
		th = new Thread() {
			@Override
			public void run() {
				try {
					while (!isInterrupted()) {
						if(Assat.player.x > wasPos.x && Assat.player.x + Assat.player.icon.getIconWidth() - 25 < wasPos.x + getIcon().getIconWidth() &&
								Assat.player.y + Assat.player.icon.getIconHeight() - 25 > wasPos.y && Assat.player.y + Assat.player.icon.getIconHeight() - 20 < wasPos.y + getIcon().getIconHeight()) {
							Map.wasMap = Map.nowMap;
							Map.nowMap = isMap;
							Assat.player.x = isPos.x;
							Assat.player.y = isPos.y;
						}
						sleep(1000);
					}
				} catch (InterruptedException e) {interrupt();}
			}
		};
		th.setName("워프 : " + Map.maps[isMap].name);
		th.start();
	}
	
	public static void WarpPlace() {
		
	}
}
