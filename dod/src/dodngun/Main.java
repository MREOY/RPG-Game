package dodngun;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dodngun.assat.Assat;
import dodngun.enemy.Enemy;
import dodngun.entity.Entity;
import dodngun.item.Item;
import dodngun.map.Map;
import dodngun.quest.Quest;
import dodngun.skill.Skill;
import dodngun.utill.Utill;
import dodngun.warp.Warp;

@SuppressWarnings("serial")
public class Main extends JFrame{
	private int nowscreen = 1;
	private int job = 0;
	
	private static int screencX;
	private static int screencY;
	
	public Main() {
		DefultFrame();
		DefultUi();
		Assat.Init();
		System.out.println(getContentPane().getHeight());
		System.out.println(getContentPane().getWidth());
		screencX = getContentPane().getWidth() / 2;
		screencY = getContentPane().getHeight() / 2;
		setVisible(true);
	}
	
	private void SwitchScreen() {
		if(nowscreen == 1) DefultUi();
		else if(nowscreen == 2) StartPage();
		else if(nowscreen == 3) CharactCreate();
		else if(nowscreen == 4) GamePlay();
		else System.exit(0);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	








	private void GamePlay() {
		JPanel jp = new JPanel();
		jp.setVisible(true);
		jp.setLayout(null);
		
		jp.add(Quest.QuestPane(), 0);
		
		Item[] req = {new Item(Assat.GOLD, 1)};
		Item[] rew = {new Item(Assat.GOLD, 10)};
		Quest.RequestNewQuest("금화 1개를 가져오자", "", rew, 30, req);
		
		Assat.player.skill[0] = Skill.arrow;
		Item.ItemEqipe(Assat.BEGINNER_BOW);
		Assat.player.inventory[0] = new Item(Assat.GOLD, 1);
		
		// 스킬바 및 키리스너
		Skill.Key(this, jp);
		JPanel skillbar = Skill.SkillBar(this);
		jp.add(skillbar);
		
		// hp,mp,exp바 및 메뉴바
		Utill.HpMpBar(jp);
		Utill.MenuBar(this, jp);
		
		// 미니맵 및 맵 초기화
		Map.nowMap = 1;
		Map.InitMap(jp);
		Map.MiniMap(jp);
		
		// 플래이어 움직임 마우스 리스너
		MoveThread thread = new MoveThread();
		thread.start();
		
		Enemy.Wolf(0, 100, Map.nowMap);
		
		Warp.WarpPlace();
		
		jp.setVisible(true);
		add(jp);
	}

	private void CharactCreate() {		
		JPanel jp = new JPanel();
		jp.setLayout(null);
		
		JPanel statep = Utill.SetImageBackground(Assat.brown150_350);
		statep.setBounds(50, 50, 150, 350);
		JLabel state = new JLabel();
		Utill.LabelHelper(state, "사냥꾼, str : 6,  agi : 8, int : 2,  wis : 4, luk : 5, con : 5, 난이도 : 어려움");
		
		JTextField namet = new JTextField();
		namet.setBounds(getContentPane().getWidth() - 150, 80, 100, 50);
		jp.add(namet);
		
		JButton next = new JButton("캐릭터 생성");
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(namet.getText().equals("")) {
					Utill.PopUp("이름을 설정해주세요", Assat.popup250_100);
				}else {
					if(job == 0) {
						Assat.player = new Entity(namet.getText(), Assat.player1, 1, 6, 8, 2, 4, 5, 5);
					}
					
					Assat.player.x = screencX;
					Assat.player.y = screencY;
					jp.setVisible(false);
					nowscreen = 4;
					SwitchScreen();
				}
			}
		});
		next.setBounds(getContentPane().getWidth() - 150, getContentPane().getHeight() - 150, 100, 50);
		jp.add(next);
		
		JLabel job = new JLabel("사냥꾼", JLabel.CENTER);
		job.setBounds(getContentPane().getWidth() - 150, getContentPane().getHeight() - 250, 100, 50);
		jp.add(job);
		
		statep.add(state);
		
		jp.add(statep);
		jp.setVisible(true);
		add(jp);
	}

	private void StartPage() {
		int gap = 30;
		JPanel jp = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.fillRect(0, 0, gap, getHeight());
				g.fillRect(0, 0, getWidth(), gap);
				g.fillRect(0, getHeight() - gap, getWidth(), gap);
				g.fillRect(getWidth() - gap, 0, gap, getHeight());
				g.setColor(Color.gray);
				g.fillRect(gap, gap, getWidth() - gap * 2, getHeight() - gap * 2); 
			}
		};
		jp.setLayout(null);
		JButton jb = new JButton("새 게임");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jp.setVisible(false);
				nowscreen = 3;
				SwitchScreen();
			}
		});
		jb.setBounds(getContentPane().getWidth() / 2 - 100, 100, 200, 300);
		jp.add(jb);
		jp.setVisible(true);
		add(jp);
	}

	private void DefultUi() {
		JPanel jp = Utill.SetImageBackground(Assat.mainpage);
		jp.setLayout(null);
		JButton start = new JButton("시작");
		start.setBounds(getContentPane().getWidth() / 2 - 50, 300, 100, 50);
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jp.setVisible(false);
				nowscreen = 2;
				SwitchScreen();
			}
		});
		JButton back = new JButton("돌아가기");
		back.setBounds(getContentPane().getWidth() / 2 - 50, 380, 100, 50);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		jp.add(start);
		jp.add(back);
		add(jp);
		jp.setVisible(true);
	}

	private void DefultFrame(){
		setTitle("Dod&Gun");
		setSize(800, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JFrame jf = Utill.PopUp("종료하시겠습니까?", Assat.popup250_100);
				JPanel jp = Utill.bjp;
				JButton exit = new JButton("종료");
				exit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				JButton cancel = new JButton("취소");
				cancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						jf.dispose();
					}
				});
				jp.add(exit);
				jp.add(cancel);
				jp.setVisible(true);
				jf.setVisible(true);
			}
		});
	}
	
	public static void main(String[] args) {
		new Main();
	}
}

class MoveThread extends Thread{
	Point p = new Point(461, 784);
	Map map;
	public MoveThread() {
		map = Map.maps[Map.nowMap];
		map.jl.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3) {
					p = e.getPoint();
					if(Assat.player.x > p.x) Assat.player.dx = -1;
					else if(Assat.player.x < p.x) Assat.player.dx = 1;
					if(Assat.player.y > p.y) Assat.player.dy = -1;
					else if(Assat.player.y < p.y) Assat.player.dy = 1;
				}
			}
		});
		setName("움직임");
	}
	@Override
	public void run() {
		try {
			int nowmap = Map.nowMap;
			while(!isInterrupted()) {
				if(nowmap != Map.nowMap) {
					map = Map.maps[Map.nowMap];
					map.jl.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							if(e.getButton() == MouseEvent.BUTTON3) {
								p = e.getPoint();
								if(Assat.player.x > p.x) Assat.player.dx = -1;
								else if(Assat.player.x < p.x) Assat.player.dx = 1;
								if(Assat.player.y > p.y) Assat.player.dy = -1;
								else if(Assat.player.y < p.y) Assat.player.dy = 1;
							}
						}
					});
				}
				nowmap = Map.nowMap;
				
				if(!(Assat.player.x < p.x + 5 && Assat.player.x > p.x - 5 &&
						Assat.player.y < p.y + 5 && Assat.player.y > p.y - 5)) {
					
					if(!(Assat.player.x < p.x + 5 && Assat.player.x > p.x - 5)) {
						
						if(Assat.player.x > 584 && Assat.player.dx == 1 && map.jl.getX() + map.jl.getWidth() >= 784) {
							map.x -= 0.1 * Assat.player.dx * Assat.player.state[Entity.agi];
							p.x -= 0.1 * Assat.player.dx * Assat.player.state[Entity.agi];
						}
						else if(Assat.player.x < 200 && Assat.player.dx == -1 && map.jl.getX() <= 0) {
							map.x -= 0.1 * Assat.player.dx * Assat.player.state[Entity.agi];
							p.x -= 0.1 * Assat.player.dx * Assat.player.state[Entity.agi];
						}
						Assat.player.x += 0.1 * Assat.player.dx * Assat.player.state[Entity.agi];
					} 
					else Assat.player.dx = 0;
					
					if(!(Assat.player.y < p.y + 5 && Assat.player.y > p.y - 5)) {
						
						if(Assat.player.y > 330 && Assat.player.dy == 1 && map.jl.getY() + map.jl.getHeight() >= 461) {
							map.y -= 0.1 * Assat.player.dy * Assat.player.state[Entity.agi];
							p.y -= 0.1 * Assat.player.dy * Assat.player.state[Entity.agi];
						}else if(Assat.player.y + map.jl.getY() < 130 && Assat.player.dy == -1 && map.jl.getY() <= 0) {
							map.y -= 0.1 * Assat.player.dy * Assat.player.state[Entity.agi];
							p.y -= 0.1 * Assat.player.dy * Assat.player.state[Entity.agi];
						}
						Assat.player.y += 0.1 * Assat.player.dy * Assat.player.state[Entity.agi];
					} 
					else Assat.player.dy = 0;
					
					if(Assat.player.dx == -1) {
						if(Assat.player.dy == 1) Assat.player.setIcon(Assat.player7);
						else if(Assat.player.dy == -1) Assat.player.setIcon(Assat.player5);
						else Assat.player.setIcon(Assat.player1);
					}
					else if(Assat.player.dx == 1) {
						if(Assat.player.dy == 1) Assat.player.setIcon(Assat.player8);
						else if(Assat.player.dy == -1) Assat.player.setIcon(Assat.player6);
						else Assat.player.setIcon(Assat.player2);
					}
					else {
						if(Assat.player.dy == 1) Assat.player.setIcon(Assat.player4);
						else if(Assat.player.dy == -1) Assat.player.setIcon(Assat.player3);
					}
					
					Assat.player.setLocation((int)Assat.player.x - Assat.player.icon.getIconWidth() / 2, (int)Assat.player.y - Assat.player.icon.getIconHeight() / 2);
					map.jl.setLocation((int)map.x, (int)map.y);
					
					sleep(10);
				}
			}
		} catch (InterruptedException e) {
			interrupt();
		}
	}
}
