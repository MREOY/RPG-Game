package dodngun.skill;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dodngun.assat.Assat;
import dodngun.entity.Entity;
import dodngun.item.Item;
import dodngun.map.Map;
import dodngun.quest.Quest;
import dodngun.setting.Setting;
import dodngun.utill.Utill;

public class Skill {
	public String skillname;
	public String details;
	public int dmg;
	public int needmp;
	public int colddown;
	public ImageIcon skillicon;
	public JButton jb;
	private boolean isCold = false;
	public int id;
	
	public static Skill arrow = new Skill("작은 화살", 10, 20, 2, Assat.arrowicon, "작은 화살, 작은 화살을 날린다.", 0);
	
	public static void Key(JFrame jf, JPanel jp) {
		jf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				try {
					if(keyCode == Setting.key[0]) Assat.player.skill[0].Cold(jp);	
					else if(keyCode == Setting.key[1]) Assat.player.skill[1].Cold(jp);
					else if(keyCode == Setting.key[2]) Assat.player.skill[2].Cold(jp);
					else if(keyCode == Setting.key[3]) Assat.player.skill[3].Cold(jp);
					else if(keyCode == Setting.key[4]) Item.UseItem();
					else if(keyCode == Setting.key[5]) Item.UseItem();
					else if(keyCode == Setting.key[6]) Item.UseItem(); 
					else if(keyCode == Setting.key[7]) Quest.HideQuest();
				} catch (Exception e2) {}
			}
		});
		jf.setFocusable(true);
		jf.requestFocus();
	}
	
	public static JPanel SkillBar(JFrame jf) {
		JPanel jp = new JPanel();
		jp.setBounds(jf.getContentPane().getWidth() / 2 - 175, jf.getContentPane().getHeight() - 60, 350, 50);
		jp.setLayout(new GridLayout(1, 7));
		for(int i = 0; i < Assat.player.skill.length; i++) {
			if(Assat.player.skill[i] == null) {
				JButton jb = new JButton(Assat.skillbar50_50);
				jb.setFocusable(false);
				jp.add(jb);
			}
			else {
				JButton jb = new JButton(Assat.player.skill[i].skillicon);
				Assat.player.skill[i].jb = jb;
				jb.setToolTipText(Assat.player.skill[i].skillname);
				jb.setFocusable(false);
				jp.add(jb);
			}
		}
		jp.setVisible(true);
		return jp;
	}
	
	public Skill(String name, int dmg, int mp, int cold, ImageIcon icon, String detail, int ide){
		skillname = name;
		this.dmg = dmg;
		needmp = mp;
		colddown = cold;
		skillicon = icon;
		details = detail;
		id = ide;
	}
	
	public void Cold(JPanel jp) {
		if(!isCold) {
			isCold = true;
			if(Assat.player.mp >= needmp) {
				Assat.player.mp -= needmp;
				ActiveSkill(jp);
				jb.setEnabled(false);
				Thread th = new Thread() {
					@Override
					public void run() {
						try {
							float coldtime = (float) (colddown - Assat.player.state[Entity.wis] * 0.05); 
							for(float i = 0; i < coldtime; i+=0.1) {
								isCold = true;
								sleep(100);
							}
							isCold = false;
							jb.setEnabled(true);
							interrupt();
						} catch (InterruptedException e) {}
					}
				};
				th.setName(id + "번 스킬 쿨타임");
				th.start();
			}
		}
	}
	
	private void ActiveSkill(JPanel jp) {
		switch (id) {
			case 0:{
				Small_Arrow();
				break;
			}
		}
	}
	
	private static void Small_Arrow() {
		JLabel jl = new JLabel(Assat.skill_arrow);
		Map.maps[Map.nowMap].jl.add(jl);

		int dx = Assat.player.dx;
		int dy = Assat.player.dy;
		
		if(dx == 0) if(dy == 1) jl.setIcon(Utill.ImageRotate(Assat.skill_arrow, 180)); 	// 아래
		if(dx == 1) {
			if(dy == 1) jl.setIcon(Utill.ImageRotate(Assat.skill_arrow, 135));			// 아래 우
			else if(dy == -1) jl.setIcon(Utill.ImageRotate(Assat.skill_arrow, 45));		// 위 우
			else jl.setIcon(Utill.ImageRotate(Assat.skill_arrow, 90));					// 우
		}
		if(dx == -1) {
			if(dy == 1) jl.setIcon(Utill.ImageRotate(Assat.skill_arrow, 225));			// 아래 좌
			else if(dy == -1) jl.setIcon(Utill.ImageRotate(Assat.skill_arrow, 315));	// 위 좌
			else jl.setIcon(Utill.ImageRotate(Assat.skill_arrow, 270));					// 좌
		}
		
		Thread th = new Thread() {
			@Override
			public void run() {
				int x = (int) Assat.player.x - jl.getIcon().getIconWidth() / 2;
				int y = (int) Assat.player.y - jl.getIcon().getIconHeight() / 2;
				
				jl.setBounds(x, y, jl.getIcon().getIconWidth(), jl.getIcon().getIconHeight());
				try {
					for(int i = 0; i < 250; i++) {
						jl.setLocation(x, y);
						x += dx * Assat.player.state[Entity.agi];
						y += dy * Assat.player.state[Entity.agi];
						OnHit(x, y, jl.getIcon().getIconWidth(), jl.getIcon().getIconHeight());
						sleep(20);
					}
				} catch (InterruptedException e) {interrupt();}
			}
		};
		th.setName("작은 화살");
		th.start();
	}
	
	private static void OnHit(int x, int y, int w, int h) {
		
	}
	
}