package dodngun.utill;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dodngun.assat.Assat;
import dodngun.entity.Entity;
import dodngun.Inventory.Inventory;
import dodngun.setting.Setting;
import dodngun.stat.Stat;

@SuppressWarnings("serial")
public class Utill {
	public static JFrame bjf;
	public static JPanel bjp;
	private static BufferedImage canvas;
	
	public static Thread hp;
	
	private static Point initialClick;
	private static int buff;
	
	public static void MenuBar(JFrame jf, JPanel jp) {
		JButton bag = new JButton(Assat.bag);
		bag.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Inventory();
				jf.setFocusable(true);
				jf.requestFocus();
			}
		});
		bag.setToolTipText("인벤토리");
		bag.setVisible(false);
		bag.setBounds(784 - Assat.bag.getIconWidth() - 50, 0, Assat.bag.getIconWidth(), Assat.bag.getIconHeight());
		jp.add(bag);
		
		JButton state = new JButton(Assat.state);
		state.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Stat();
				jf.setFocusable(true);
				jf.requestFocus();
			}
		});
		state.setToolTipText("능력치");
		state.setVisible(false);
		state.setBounds(784 - Assat.state.getIconWidth() - 100, 0, Assat.state.getIconWidth(), Assat.state.getIconHeight());
		jp.add(state);
		
		JButton setting = new JButton(Assat.setting);
		setting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Setting();
				jf.setFocusable(true);
				jf.requestFocus();
			}
		});
		setting.setToolTipText("설정");
		setting.setVisible(false);
		setting.setBounds(784 - Assat.setting.getIconWidth() - 150, 0, Assat.setting.getIconWidth(), Assat.setting.getIconHeight());
		jp.add(setting);
		
		JButton menu = new JButton(Assat.menu);
		menu.setToolTipText("메뉴");
		menu.setBounds(784 - Assat.menu.getIconWidth(), 0, Assat.menu.getIconWidth(), Assat.menu.getIconHeight());
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!bag.isVisible()) {
					setting.setVisible(true);
					state.setVisible(true);
					bag.setVisible(true);
				}
				else {
					setting.setVisible(false);
					state.setVisible(false);
					bag.setVisible(false);
				}
				jf.setFocusable(true);
				jf.requestFocus();
			}
		});
		jp.add(menu, 0);
	}
	
	public static void HpMpBar(JPanel jp) {
		hp = new Thread() {
			@Override
			public void run() {
				int time = 0;
				int hp = 0, mp = 0, exp = 0;
				try {
					Assat.hpbar.removeAll();
					Assat.mpbar.removeAll();
					Assat.expbar.removeAll();
					Assat.hpbar = Utill.SetImageBackground(Assat.bar210_20);
					Assat.mpbar = Utill.SetImageBackground(Assat.bar210_20);
					Assat.expbar = Utill.SetImageBackground(Assat.bar210_20);
					Assat.hpbar.setLayout(null);
					Assat.mpbar.setLayout(null);
					Assat.expbar.setLayout(null);
					Assat.expbar.setBounds(100, 70, Assat.bar210_20.getIconWidth(), Assat.bar210_20.getIconHeight());
					Assat.hpbar.setBounds(100, 10, Assat.bar210_20.getIconWidth(), Assat.bar210_20.getIconHeight());
					Assat.mpbar.setBounds(100, 40, Assat.bar210_20.getIconWidth(), Assat.bar210_20.getIconHeight());
					jp.add(Assat.expbar, 0);
					jp.add(Assat.hpbar, 0);
					jp.add(Assat.mpbar, 0);
					while(!interrupted()) {
						
						if(hp != (int) Assat.player.hp || mp != (int) Assat.player.mp || exp != (int) Assat.player.exp) {
							Assat.hpbar.removeAll();
							Assat.mpbar.removeAll();
							Assat.expbar.removeAll();
							Assat.hpbar = Utill.SetImageBackground(Assat.bar210_20);
							Assat.mpbar = Utill.SetImageBackground(Assat.bar210_20);
							Assat.expbar = Utill.SetImageBackground(Assat.bar210_20);
							Assat.hpbar.setToolTipText("HP : " + (int) Assat.player.hp + "/" + (int) Assat.player.maxHp);
							Assat.mpbar.setToolTipText("MP : " + (int) Assat.player.mp + "/" + (int) Assat.player.maxMp);
							Assat.expbar.setToolTipText("EXP : " + (int) Assat.player.exp + "/" + (int) Assat.player.maxExp);
							Assat.hpbar.setLayout(null);
							Assat.mpbar.setLayout(null);
							Assat.expbar.setLayout(null);
							Assat.expbar.setBounds(100, 70, Assat.bar210_20.getIconWidth(), Assat.bar210_20.getIconHeight());
							Assat.hpbar.setBounds(100, 10, Assat.bar210_20.getIconWidth(), Assat.bar210_20.getIconHeight());
							Assat.mpbar.setBounds(100, 40, Assat.bar210_20.getIconWidth(), Assat.bar210_20.getIconHeight());
							DrawRect(Assat.expbar, Color.green, 5, 5, (int)((Assat.player.exp / Assat.player.maxExp) * 200) + 1, 10);
							DrawRect(Assat.hpbar, Color.red, 5, 5, (int)((Assat.player.hp / Assat.player.maxHp) * 200) + 1, 10);
							DrawRect(Assat.mpbar, Color.blue, 5, 5, (int)((Assat.player.mp / Assat.player.maxMp) * 200) + 1, 10);
							jp.add(Assat.expbar, 0);
							jp.add(Assat.hpbar, 0);
							jp.add(Assat.mpbar, 0);
							jp.repaint();
						}
						hp = (int) Assat.player.hp;
						mp = (int) Assat.player.mp;
						exp = (int) Assat.player.exp;
						
						if(time == 20) {
							time = 0;
							if(Assat.player.hp < Assat.player.maxHp) {
								if(Assat.player.hp + Assat.player.state[Entity.con] > Assat.player.maxHp) {
									Assat.player.hp = Assat.player.maxHp;
								}else {
									Assat.player.hp += Assat.player.state[Entity.con];
								}
							}
							if(Assat.player.mp < Assat.player.maxMp) {
								if(Assat.player.mp + Assat.player.state[Entity.wis] > Assat.player.maxMp) {
									Assat.player.mp = Assat.player.maxMp;
								}else {
									Assat.player.mp += Assat.player.state[Entity.wis];
								}
							}
						}
						
						sleep(50);
						time++;
					}
				} catch (InterruptedException e) {interrupt();}
			}
		};
		hp.setName("채력바");
		hp.start();
	}
	
	public static ImageIcon ImageResize(ImageIcon icon, int w, int h) {
		Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}
	
	public static ImageIcon ImageVerSymm(ImageIcon icon) {
		canvas = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = canvas.createGraphics();
		
		g2.drawImage(icon.getImage(), icon.getIconWidth(), 0, -icon.getIconWidth(), icon.getIconHeight(), null);
		
		return new ImageIcon(canvas);
	}
	
	public static ImageIcon ImageRotate(ImageIcon icon, double rotate) {
		int s = icon.getIconWidth() < icon.getIconHeight() ? icon.getIconHeight() : icon.getIconWidth();
		canvas = new BufferedImage(s, s, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = canvas.createGraphics();
		
		g2.rotate(Math.toRadians(rotate), s / 2, s / 2);
		g2.drawImage(icon.getImage(), 0, 0, null);
		
		return new ImageIcon(canvas);
	}
	
	public static JLabel DrawRect(JPanel jp, Color color, int x, int y, int width, int height) {
		canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = canvas.createGraphics();
		
		g2.setColor(color);
		g2.fillRect(0, 0, width, height);
		
		JLabel jl = new JLabel(new ImageIcon(canvas));
		jl.setBounds(x, y, width, height);
		jp.add(jl);
		return jl;
	}
	
	public static JLabel DrawRect(JLabel jl, Color color, int x, int y, int width, int height) {
		canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = canvas.createGraphics();
		
		g2.setColor(color);
		g2.fillRect(0, 0, width, height);
		
		JLabel jlbuf = new JLabel(new ImageIcon(canvas));
		jlbuf.setBounds(x, y, width, height);
		jl.add(jlbuf);
		return jlbuf;
	}
	
	public static String LabelHelper(JLabel jl, String text) {
		String[] context = text.split(", ");
		String con = "<html><body><center>";
		for(buff = 0; buff < context.length; buff++) {
			con += context[buff] + "<br>";
		}
		con += "</center></body></html>";
		if(jl != null) jl.setText(con);
		return con;
	}
	
	public static String LabelHelper(String text) {
		String[] context = text.split(", ");
		String con = "<html><body><center>";
		for(buff = 0; buff < context.length; buff++) {
			con += context[buff] + "<br>";
		}
		con += "</center></body></html>";
		return con;
	}
	
	public static String StringHelper(String text) {
		String[] context = text.split(", ");
		String con = "<html><body><center>";
		for(buff = 0; buff < context.length; buff++) {
			con += context[buff] + "<br>";
		}
		con += "</center></body></html>";
		return con;
	}
	
	public static JFrame PopUp(String title, ImageIcon background){
		JFrame jf = new JFrame();
		jf.setSize(background.getIconWidth(), background.getIconHeight());
		jf.setLocationRelativeTo(null);
		JPanel jp = SetImageBackground(background);
		jf.add(jp);
		bjp = jp;
		Frame(jf, title);
		return jf;
	}
	
	public static JPanel SetImageBackground(ImageIcon img) {
		JPanel jp = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img.getImage(), 0, 0, null);
				setOpaque(false);
			}
		};
		return jp;
	}
	
	public static JPanel SetImageBackground(ImageIcon img, int x, int y) {
		JPanel jp = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img.getImage(), x, y, null);
				setOpaque(false);
			}
		};
		return jp;
	}
	
	public static JPanel SetImageBackground(ImageIcon img, ImageIcon im, int x, int y) {
		JPanel jp = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(img.getImage(), 0, 0, null);
				g.drawImage(im.getImage(), x, y, null);
				setOpaque(false);
			}
		};
		return jp;
	}
	
	public static void Frame(JFrame jf, String title){
		jf.setResizable(false);
		jf.setUndecorated(true);
		jf.setVisible(true);
		JPanel jp = bjp;
		JPanel bfp = new JPanel();
		bfp.setBackground(new Color(255, 0, 0, 0));
		jp.setLayout(new BorderLayout());
		bjp = bfp;
		
		JLabel tt = new JLabel(title, JLabel.CENTER);
		tt.setFont(new Font("", Font.ITALIC, 20));
		tt.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) 
			{ 
				initialClick = e.getPoint();
				jf.getComponentAt(initialClick);
			}
		});
		tt.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) 
			{
				int thisX = jf.getLocation().x;
				int thisY = jf.getLocation().y;
	            
				int xMoved = e.getX() - initialClick.x;
				int yMoved = e.getY() - initialClick.y;
	            
				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				jf.setLocation(X, Y);
			}
		});
		tt.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) jf.dispose();
			}
		});
		jp.add(tt, "North");
		jp.add(bfp, "Center");
		tt.setFocusable(true);
		tt.requestFocus();
		jf.setVisible(true);
	}
}