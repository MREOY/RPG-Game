package dodngun.Inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import dodngun.assat.Assat;
import dodngun.item.Item;
import dodngun.utill.Utill;

public class Inventory{
	
	public static final int less = 0; // 아이템 부족
	public static final int all = 1;  // 아이템 전부 있음
	public static final int full = 2; // 가방이 꽉차있음
	public static final int lack = 3; // 아이템 없음
	
	private static JFrame jf;
	private static JPanel inven = new JPanel(new GridLayout(0, 5, 2, 2));
	
	private static boolean isopen = false;
	
	public Inventory() {
		if(!isopen) {
			isopen = true;	
			jf = new JFrame("인벤토리");
			jf.setSize(250, 300);
			jf.setResizable(false);
			jf.setLocationRelativeTo(null);
			
			jf.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					isopen = false;
				}
			});
			
			InvenReload();
			
			jf.setVisible(true);
		}
	}
	
	public static void InvenReload() {
		if(isopen) {
			JLabel name = new JLabel("플레이어 : " + Assat.player.name, JLabel.CENTER);
			jf.add(name, "North");
			
			inven.removeAll();
			
			InvenReclear();
			for(int i = 0; i < Assat.player.inventory.length; i++) {
				JPanel p = new JPanel(new BorderLayout());
				p.setBorder(new LineBorder(Color.black));
				if(Assat.player.inventory[i] != null) {
					JLabel jl = new JLabel(Assat.player.inventory[i].image);
					jl.setToolTipText(Assat.player.inventory[i].itemname + " " + Assat.player.inventory[i].count);
					p.add(jl, "Center");
					JLabel cotn = new JLabel(Assat.player.inventory[i].itemname + " " + Assat.player.inventory[i].count, JLabel.CENTER);
					p.add(cotn, "South");
				}
				p.setVisible(true);
				inven.add(p);
			}
			inven.setVisible(true);
			jf.add(inven, "Center");
			jf.setVisible(true);
		}
	}
	
	public static void InvenReclear() {
		for(int i = 1; i < Assat.player.inventory.length; i++) 
			if(Assat.player.inventory[i - 1] == null) 
				for(int j = i; j < Assat.player.inventory.length; j++) 
					if(Assat.player.inventory[j] != null) {
						Assat.player.inventory[i - 1] = Assat.player.inventory[j];
						break;
					}
	}
	
	public static int InvenCheck(Item check) {
		InvenReclear();
		int count = 0, itis = 0, itcomf = 0;
		for(int i = 0; i < Assat.player.inventory.length; i++) {
			if(Assat.player.inventory[i] != null) {
				count++;
				if(Assat.player.inventory[i].id == check.id) {
					itis++;
					if(Assat.player.inventory[i].count >= check.count) {
						itcomf++;
					}
				}
			}
		}
		
		if(count + 1 >= Assat.player.inventory.length) return full;
		else if(itis == 0 && itcomf == 0) return lack;
		else if(itis >= 0 && itcomf == 0) return less;
		else return all;
	}
	
	public static int InvenCheck(Item[] check) {
		int result;
		for(int i = 0; i < check.length; i++) {
			result = InvenCheck(check[i]);
			if(result == full) return full;
			else if(result == less) return less;
			else if(result == lack) return lack;
		}
		return all;
	}
	
	public static void InvenAdd(Item add) {
		InvenReclear();
		int check = InvenCheck(add);
		if(check == all || check == less) {
			for(int i = 0; i < Assat.player.inventory.length; i++) {
				if(Assat.player.inventory[i] == null) continue;
				else if(Assat.player.inventory[i].id == add.id) {
					Assat.player.inventory[i] = new Item(add, Assat.player.inventory[i].count + add.count);
					break;
				}
			}
		}else if(check == lack) {
			for(int i = 0; i < Assat.player.inventory.length; i++) {
				if(Assat.player.inventory[i] == null) {
					Assat.player.inventory[i] = new Item(add, add.count);
					break;
				}
			}
		}else Utill.PopUp("인벤토리가 꽉 차있습니다", Assat.popup250_100);
		
		InvenReload();
	}
	
	public static void InvenAdd(Item[] add) {
		InvenReclear();
		for(int i = 0; i < add.length; i++) {
			int check = InvenCheck(add);
			if(check == full) {
				Utill.PopUp("인벤토리가 꽉 차있습니다", Assat.popup250_100);
				break;
			}
		}
		for(int i = 0; i < add.length; i++) InvenAdd(add[i]);
	}
	
	
	public static void InvenRemove(Item[] remove) {
		InvenReclear();
		int check = InvenCheck(remove);
		if(check == all) {
			for(int i = 0; i < remove.length; i++) {
				for(int j = 0; j < Assat.player.inventory.length; j++) {
					if(Assat.player.inventory[j] == null) continue;
					if(Assat.player.inventory[j].id == remove[i].id) {
						if(Assat.player.inventory[j].count > remove[i].count) {
							Assat.player.inventory[j] = new Item(remove[i], Assat.player.inventory[j].count - remove[i].count);
							break;
						}
						else if(Assat.player.inventory[j].count == remove[i].count) {
							Assat.player.inventory[j] = null;
							break;
						}
					}
				}
			}
		}
		InvenReload();
	}
	
	public static void InvenRemove(Item remove) {
		Item[] item = {remove};
		InvenRemove(item);
	}
}
