package dodngun.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import dodngun.assat.Assat;
import dodngun.entity.Entity;
import dodngun.utill.Utill;
import dodngun.warp.Warp;

public class Map {
	public static Map[] maps = {new Map(Assat.testmap, "testingMap", 0, 0), new Map(Assat.field, "field", 0, 0)};
	public static int nowMap = 0;
	public static int wasMap = 0;
	public JLabel jl;
	public ImageIcon map;
	public Entity[] entitiy;
	public String name;
	public float x;
	public float y;
	public int id;
	private static int ide = 0;
	private static JLabel miniMap;
	
	public Map(ImageIcon map, String name, float x, float y) {
		jl = new JLabel(map);
		jl.setBounds((int)x, (int)y, map.getIconWidth(), map.getIconHeight());
		this.map = map;
		this.name = name;
		this.x = x;
		this.y = y;
		this.id = ide;
		ide++;
	}
	
	public static void InitMap(JPanel jp) {
		jp.remove(maps[wasMap].jl);
		
		maps[nowMap].jl.setBounds(0, 0, maps[nowMap].map.getIconWidth(), maps[nowMap].map.getIconHeight());
		jp.add(maps[nowMap].jl, jp.getComponentCount());
		
		Assat.player.setLocation((int)Assat.player.x, (int)Assat.player.y);
		maps[nowMap].jl.add(Assat.player, 0);
	}
	
	public static void MiniMap(JPanel jp) {
		JPanel buff = new JPanel(new BorderLayout());
		buff.setBounds(5, 5, 90, 90);
		
		miniMap = new JLabel(Utill.ImageResize(maps[nowMap].map, 90, 90));
		miniMap.setLayout(null);
		miniMap.setBorder(new LineBorder(Color.black));
		miniMap.setBounds(0, 0, 90, 90);
		buff.add(miniMap);
		
		Thread th = new Thread() {
			@Override
			public void run() {
				setName("미니맵");
				int nowmap = Map.nowMap;
				try {
					while(!isInterrupted()) {
						if(nowmap != nowMap) {
							miniMap.setIcon(Utill.ImageResize(maps[nowMap].map, 90, 90));
							InitMap(jp);
							buff.repaint();
							jp.repaint();
						}
						miniMap.removeAll();
						for (Component comp : maps[nowMap].jl.getComponents()) {
							if(comp instanceof Entity) {
								Entity entity = (Entity) comp;
								if(entity.isPlayer) Utill.DrawRect(miniMap, Color.blue, (int)(entity.x/ 17), (int)(entity.y/ 17), 5, 5);
								else Utill.DrawRect(miniMap, Color.red, (int)(entity.x/ 17), (int)(entity.y/ 17), 5, 5);
							}
							else if(comp instanceof Warp) {
								Warp warp = (Warp) comp;
								Utill.DrawRect(miniMap, Color.green, (int)(warp.wasPos.x/ 17), (int)(warp.wasPos.y/ 17), 5, 5);
							}
						}
						miniMap.repaint();
						nowmap = Map.nowMap;
						sleep(100);
					}
				} catch (InterruptedException e) {interrupt();}
			}
		};
		th.start();
		jp.add(buff, 0);
		jp.repaint();
	}
	
}
