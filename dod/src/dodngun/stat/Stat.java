package dodngun.stat;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import dodngun.assat.Assat;
import dodngun.Inventory.Inventory;
import dodngun.item.Item;
import dodngun.utill.Utill;

public class Stat {
	public static Thread th = new Thread();
	
	private static JFrame jf;
	private static JFrame setequip;
	private static JPanel equip = new JPanel(null);
	private static int buff;
	
	private static boolean isopen = false;
	
	private static boolean[] ishanded = new boolean[6];
	private static boolean isopened = false;
	
	public Stat() {
		if(!isopen) {
			isopen = true;	
			jf = new JFrame("스테이터스");
			jf.setSize(250, 300);
			jf.setResizable(false);
			jf.setLocationRelativeTo(null);
			
			jf.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					isopen = false;
				}
			});
			
			Equipment();
			
			jf.setVisible(true);
		}
	}
	
	private static void Equipment() {
		equip = new JPanel(null);
		
		JLabel equipment = new JLabel(Assat.equip);
		equipment.setBounds(0, 0, Assat.equip.getIconWidth(), Assat.equip.getIconHeight());
		equip.add(equipment);
		
		JLabel lvl = new JLabel("", JLabel.CENTER);
		lvl.setBounds(100, 0, 132, 50);
		lvl.setBorder(new LineBorder(Color.black));
		equip.add(lvl);
		
		JPanel upState = new JPanel(new GridLayout(6, 2));
		upState.setBounds(100, 50, 132, 180);
		jf.add(upState);
		
		String[] st = "str,agi,int,wis,luk,con".split(",");
		JLabel[] state = new JLabel[6];
		JButton[] jb = new JButton[6];
		JLabel jl = new JLabel("", JLabel.CENTER);
		jl.setBounds(100, 230, 135, 20);
		equip.add(jl);
		AddLab(state, st, jb, upState);
		
		JButton[] item = new JButton[6];
		Point[] pos = {new Point(35, 70), new Point(10, 150), new Point(60, 150), new Point(35, 120), new Point(35, 180), new Point(35, 230)};
		String[] itemName = "투구,무기,장갑,갑옷,바지,신발".split(",");
		AddBut(item, itemName, pos, equipment);
		
		th = new Thread() {
			@Override
			public void run() {
				setName("스테이터스 갱신");
				try {
					while(!isInterrupted()) {
						for(int i = 0; i < 6; i++) {
							state[i].setText(st[i] + " : " + Assat.player.state[i]);
						}
						lvl.setText("플레이어 레벨 : " + Assat.player.lvl);
						jl.setText("스탯 포인트 : " + Assat.player.stpoint);
						if(!isopen) interrupt();
						sleep(1000);
					}
				} catch (InterruptedException e) {interrupt();}
			}
		};
		th.start();
		
		jf.add(equip);
	}
	
	private static void AddBut(JButton[] item, String[] itemName, Point[] pos, JLabel equipment) {
		for(buff = 0; buff < item.length; buff++) {
			if(Assat.player.equipment[buff] != null) {
				ishanded[buff] = true;
				item[buff] = new JButton(Assat.player.equipment[buff].image);
				item[buff].setToolTipText(Utill.LabelHelper(itemName[buff] + " : " + Assat.player.equipment[buff].itemname + ", " 
						+ "con : " + Assat.player.equipment[buff].con + ", "
						+ "str : " + Assat.player.equipment[buff].str + ", "
						+ "agi : " + Assat.player.equipment[buff].agi + ", "
						+ "inte : " + Assat.player.equipment[buff].inte + ", "
						+ "wis : " + Assat.player.equipment[buff].wis + ", "
						+ "luk : " + Assat.player.equipment[buff].luk));
			}
			else {
				ishanded[buff] = false;
				item[buff] = new JButton();
				item[buff].setToolTipText(itemName[buff]);
			}
			int bu = buff;
			item[buff].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(ishanded[bu]) {
						item[bu].setIcon(null);
						item[bu].setToolTipText(itemName[bu]);
						Inventory.InvenAdd(Assat.player.equipment[bu]);
						Item.ItemRelease(Assat.player.equipment[bu]);
						ishanded[bu] = false;
					}else {
						if(!isopened) {
							isopened = true;
							setequip = new JFrame("아이템 장착");
							setequip.setLayout(new GridLayout(0, 5));
							setequip.setSize(250, 120);
							setequip.setLocationRelativeTo(null);
							setequip.setVisible(true);
							
							for(Item equip : Assat.player.inventory) {
									JButton jb = null;
									if(equip == null) continue;
									else if(equip.type == bu) {
									jb = new JButton(equip.image);
									jb.setToolTipText(Utill.LabelHelper(itemName[bu] + " : " + equip.itemname + ", " 
											+ "con : " + equip.con + ", "
											+ "str : " + equip.str + ", "
											+ "agi : " + equip.agi + ", "
											+ "inte : " + equip.inte + ", "
											+ "wis : " + equip.wis + ", "
											+ "luk : " + equip.luk));
									jb.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											ishanded[bu] = true;
											Item remove = new Item(equip, 1);
											item[bu].setToolTipText(Utill.LabelHelper(itemName[bu] + " : " + remove.itemname + ", " 
													+ "con : " + remove.con + ", "
													+ "str : " + remove.str + ", "
													+ "agi : " + remove.agi + ", "
													+ "inte : " + remove.inte + ", "
													+ "wis : " + remove.wis + ", "
													+ "luk : " + remove.luk));
											item[bu].setIcon(remove.image);
											Item.ItemEqipe(remove);
											Inventory.InvenRemove(remove);
											isopened = false;
											setequip.dispose();
										}
									});
									jb.setBorder(new LineBorder(Color.black));
									jb.setBackground(Color.white);
									setequip.add(jb);
								}
							}
						}
						
						setequip.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosing(WindowEvent e) {
								isopened = false;
							}
						});
					}	
					item[bu].repaint();
				}
			});
			
			item[buff].setOpaque(true);
			item[buff].setBackground(Color.white);
			item[buff].setSize(20, 20);
			item[buff].setLocation(pos[buff]);
			item[buff].setBorder(new LineBorder(Color.black));
			equipment.add(item[buff]);
		}
	}
	
	private static void AddLab(JLabel[] state, String[] st, JButton[] jb,JPanel upState){
		for(buff = 0; buff < 6; buff++) {
			state[buff] = new JLabel(st[buff] + " : " + Assat.player.state[buff], JLabel.CENTER);
			state[buff].setBorder(new LineBorder(Color.black));
			jb[buff] = new JButton("+1");
			jb[buff].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(Assat.player.stpoint > 0) {
						Assat.player.stpoint--;	
						Assat.player.state[buff]++;
					}
				}
			});
			upState.add(state[buff]);
			upState.add(jb[buff]);
		}
	}
}
