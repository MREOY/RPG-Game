package dodngun.assat;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import dodngun.entity.Entity;
import dodngun.item.Item;

public class Assat {
	//assat
	public static ImageIcon popup250_100 = new ImageIcon("./src/dodngun/assat/popup250_100.png");
	public static ImageIcon mainpage = new ImageIcon("./src/dodngun/assat/mainpage.png");
	public static ImageIcon brown150_350 = new ImageIcon("./src/dodngun/assat/brown150_350.png");
	public static ImageIcon skillbar50_50 = new ImageIcon("./src/dodngun/assat/skillbar.png");
	public static ImageIcon questbar = new ImageIcon("./src/dodngun/assat/questbar.png");
	public static ImageIcon questpanel = new ImageIcon("./src/dodngun/assat/questpanel.png");
	public static ImageIcon warp = new ImageIcon("./src/dodngun/assat/warp.png");
	
	
	//stat
	public static ImageIcon equip = new ImageIcon("./src/dodngun/assat/equip.png");
	
	//icon
	public static ImageIcon menu = new ImageIcon("./src/dodngun/assat/menu.png");
	public static ImageIcon bag = new ImageIcon("./src/dodngun/assat/bag.png");
	public static ImageIcon setting = new ImageIcon("./src/dodngun/assat/setting.png");
	public static ImageIcon state = new ImageIcon("./src/dodngun/assat/state.png");
	
	
	//player
	public static ImageIcon player1 = new ImageIcon("./src/dodngun/assat/p1.png");
	public static ImageIcon player2 = new ImageIcon("./src/dodngun/assat/p2.png");
	public static ImageIcon player3 = new ImageIcon("./src/dodngun/assat/p3.png");
	public static ImageIcon player4 = new ImageIcon("./src/dodngun/assat/p4.png");
	public static ImageIcon player5 = new ImageIcon("./src/dodngun/assat/p5.png");
	public static ImageIcon player6 = new ImageIcon("./src/dodngun/assat/p6.png");
	public static ImageIcon player7 = new ImageIcon("./src/dodngun/assat/p7.png");
	public static ImageIcon player8 = new ImageIcon("./src/dodngun/assat/p8.png");
	
	
	//enemy
	public static ImageIcon wolf1 = new ImageIcon("./src/dodngun/assat/wolf.png");
	
	
	//hp, mp
	public static ImageIcon bar210_20 = new ImageIcon("./src/dodngun/assat/bar210_20.png");
	public static ImageIcon hp = new ImageIcon("./src/dodngun/assat/hp.png");
	public static ImageIcon mp = new ImageIcon("./src/dodngun/assat/mp.png");
	public static ImageIcon exp = new ImageIcon("./src/dodngun/assat/exp.png");
	public static JPanel hpbar = new JPanel();
	public static JPanel mpbar = new JPanel();
	public static JPanel expbar = new JPanel();
	
	//item
	public static ImageIcon gold = new ImageIcon("./src/dodngun/assat/gold.png");
	public static ImageIcon beginnerbow = new ImageIcon("./src/dodngun/assat/beginner_bow.png");
	
	public static Item GOLD = new Item("금화", Assat.gold, Item.assat);
	public static Item beginner_bow = new Item("초보자의 활", beginnerbow, Item.weapon);
	
	public static Item BEGINNER_BOW = new Item(beginner_bow, 1, 0, 1, 1, 0, 0, 0);
	
	
	//skill
	//arrow
	public static ImageIcon arrowicon = new ImageIcon("./src/dodngun/assat/arrowicon.png");
	public static ImageIcon skill_arrow = new ImageIcon("./src/dodngun/assat/arrow.png");
	
	
	//map
	public static ImageIcon testmap = new ImageIcon("./src/dodngun/assat/testmap.png");
	public static ImageIcon field = new ImageIcon("./src/dodngun/assat/field.png");
	
	
	//entity
	public static Entity player;
	public static Entity wolf = new Entity("늑대", wolf1, 4, 6, 1, 1, 4, 4, 200, 10);
	
	public static void Init() {};
}
