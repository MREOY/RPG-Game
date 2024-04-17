package dodngun.entity;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import dodngun.item.Item;
import dodngun.skill.Skill;

@SuppressWarnings("serial")
public class Entity extends JLabel{
	public static int str = 0;
	public static int agi = 1;
	public static int inte = 2;
	public static int wis = 3;
	public static int luk = 4;
	public static int con = 5;
	
	public String name;
	public ImageIcon icon;
	
	public Skill[] skill = new Skill[7];
	
	public Item[] inventory = new Item[30];
	
	public Item[] equipment = new Item[6];
	
	public int lvl;

	public float maxExp;
	public float exp;
	
	public float maxHp;
	public float hp;
	public float maxMp;
	public float mp;
	
	public int[] state = new int[6];
	
	public int actionRange;
	public int attackRange;
	
	public int stpoint = 0;
	
	public float x;
	public float y;
	
	public int dx = 0;
	public int dy = 0;
	
	public boolean isPlayer = false;
	
	public Entity(String name, ImageIcon icon, int str, int agi, int inte, int wis, int luk, int con, int actionRange, int attackRange) {
		
		setIcon(icon);
		setSize(icon.getIconWidth(), icon.getIconHeight());
		this.name = name;
		this.icon = icon;
		
		this.attackRange = attackRange;
		this.actionRange = actionRange;
		this.state[Entity.str] = str;
		this.state[Entity.agi] = agi;
		this.state[Entity.inte] = inte;
		this.state[Entity.wis] = wis;
		this.state[Entity.luk] = luk;
		this.state[Entity.con] = con;
		
		hp = maxHp = 100 + 100 * con;
		mp = maxMp = 100 + 50 * inte + wis;
	}
	
	public Entity(String name, ImageIcon icon, int lvl, int str, int agi, int inte, int wis, int luk, int con) {
		setIcon(icon);
		setSize(icon.getIconWidth(), icon.getIconHeight());
		this.name = name;
		this.icon = icon;
		
		this.state[Entity.str] = str;
		this.state[Entity.agi] = agi;
		this.state[Entity.inte] = inte;
		this.state[Entity.wis] = wis;
		this.state[Entity.luk] = luk;
		this.state[Entity.con] = con;
		
		this.lvl = lvl;
		maxExp = lvl * lvl * 100 + lvl * 20;
		exp = 0;
		
		hp = maxHp = 100 + 100 * con;
		mp = maxMp = 100 + 50 * inte + wis;
		
		isPlayer = true;
		
		Thread lvlup = new LvlUp(this);
		lvlup.start();
	}
}

class LvlUp extends Thread{
	Entity entity;
	public LvlUp(Entity ent) {
		entity = ent;
		setName("레벨업");
	}
	
	@Override
	public void run() {
		try {
			while (!interrupted()) {
				if(entity.exp >= entity.maxExp) {
					entity.lvl += 1;
					entity.maxExp = entity.lvl * entity.lvl * 100 + entity.lvl * 20;
					entity.exp = 0;
					
					entity.hp = entity.maxHp = 100 + 100 * entity.state[Entity.con];
					entity.mp = entity.maxMp = 100 + 50 * entity.state[Entity.inte] + entity.state[Entity.wis];
					
					entity.stpoint += 5;
				}
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			interrupt();
		}
	}
}
