package dodngun.item;

import javax.swing.ImageIcon;

import dodngun.assat.Assat;
import dodngun.entity.Entity;

public class Item {
	public static int head = 0;
	public static int weapon = 1;
	public static int hand = 2;
	public static int body = 3;
	public static int pants = 4;
	public static int shoes = 5;
	public static int assat = 6;
	
	public int con = 0;
	public int str = 0;
	public int agi = 0;
	public int inte = 0;
	public int wis = 0;
	public int luk = 0;
	
	public String itemname;
	public ImageIcon image;
	public int count;
	public int id;
	public int type;
	
	public static int idbuff = 0;
	
	public Item(String name, ImageIcon img, int type) {
		itemname = name;
		image = img;
		this.type = type;
		id = idbuff;
		idbuff++;
	}
	
	public Item(String name, ImageIcon img, int cnt, int type) {
		itemname = name;
		image = img;
		count = cnt;
		this.type = type;
		id = idbuff;
		idbuff++;
	}

	public Item(Item item, int cnt) {
		itemname = item.itemname;
		image = item.image;
		type = item.type;
		count = cnt;
		id = item.id;
		
		this.con = item.con;
		this.str = item.str;
		this.agi = item.agi;
		this.inte = item.inte;
		this.wis = item.wis;
		this.luk = item.luk;
	}

	public Item(Item item, int cnt, int con, int str, int agi, int inte, int wis, int luk) {
		itemname = item.itemname;
		image = item.image;
		type = item.type;
		count = cnt;
		id = item.id;
		
		this.con = con;
		this.str = str;
		this.agi = agi;
		this.inte = inte;
		this.wis = wis;
		this.luk = luk;
	}
	
	public static void UseItem() {
		
	}
	
	public static void ItemEqipe(Item item) {
		Assat.player.state[Entity.con] += item.con;
		Assat.player.state[Entity.agi] += item.agi;
		Assat.player.state[Entity.inte] += item.inte;
		Assat.player.state[Entity.luk] += item.luk;
		Assat.player.state[Entity.str] += item.str;
		Assat.player.state[Entity.wis] += item.wis;
		Assat.player.equipment[item.type] = item;
	}
	
	public static void ItemRelease(Item item) {
		Assat.player.state[Entity.con] -= item.con;
		Assat.player.state[Entity.agi] -= item.agi;
		Assat.player.state[Entity.inte] -= item.inte;
		Assat.player.state[Entity.luk] -= item.luk;
		Assat.player.state[Entity.str] -= item.str;
		Assat.player.state[Entity.wis] -= item.wis;
		Assat.player.equipment[item.type] = null;
	}
}
