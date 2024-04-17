package dodngun.enemy;

import dodngun.assat.Assat;
import dodngun.entity.Entity;
import dodngun.item.Item;
import dodngun.map.Map;
import dodngun.utill.Utill;

public class Enemy {
	public Entity entity;
	public Thread th;
	
	public boolean attack = false;
	public boolean hurt = false;
	
	public static void Wolf(int x, int y, int nowMap) {
		Item[] rew = {new Item(Assat.GOLD, 10)};
		int[] per = {50};
		new Enemy(Assat.wolf, x, y, rew, per, 10, nowMap);
	}
	
	public Enemy(Entity enemy, int x, int y, Item[] rew, int[] per, int exp, int nowMap) {
		entity = enemy;
		entity.x = x;
		entity.y = y;
		
		th = new Thread() {
			@Override
			public void run() {
				setName(entity.name);
				
				Map.maps[nowMap].jl.add(entity);
				int dy = 1;
				
				try {
					while(!isInterrupted()) {
						int xGap = (int) (Assat.player.x - entity.x);
						int yGap = (int) (Assat.player.y - entity.y);
						int xFar = (int) (x - entity.x);
						int yFar = (int) (y - entity.y);
						
						if(entity.hp <= 0) {
							Map.maps[nowMap].jl.remove(entity);
							interrupt();
						}
						
						if(!attack) {
							if(xGap * xGap + yGap * yGap <= entity.actionRange * entity.actionRange || hurt) {
								entity.dx = xGap > 0 ? 1 : -1;
								entity.dy = yGap > 0 ? 1 : -1;
								
								entity.setLocation((int)entity.x, (int)entity.y);
								
								entity.x += 0.1 * entity.dx * entity.state[Entity.agi];
								entity.y += 0.1 * entity.dy * entity.state[Entity.agi];
							} else {
								if(x - 5 < entity.x && entity.x < x + 5 &&
									y - 5 < entity.y && entity.y < y + 5) {
									entity.dx = entity.dy = 0;
								}else {
									entity.dx = xFar > 0 ? 1 : -1;
									entity.dy = yFar > 0 ? 1 : -1;
								}
								
								entity.setLocation((int)entity.x, (int)entity.y);
								
								entity.x += 0.1 * entity.dx * entity.state[Entity.agi];
								entity.y += 0.1 * entity.dy * entity.state[Entity.agi];
							}
	
							if(dy != entity.dx && entity.dx != 0) {
								if(entity.dx == 1) enemy.setIcon(Utill.ImageVerSymm(entity.icon));
								else enemy.setIcon(entity.icon);
							}
							dy = entity.dx;
						}
						
						sleep(10);
					}
				} catch (InterruptedException e) {
					interrupt();
				}
			}
		};
		th.start();
	}
}
