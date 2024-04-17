package dodngun.setting;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dodngun.assat.Assat;
import dodngun.utill.Utill;

public class Setting {
	public static int[] key = {KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D, KeyEvent.VK_H};
	
	private static JFrame jf;
	private static JPanel[] jp = new JPanel[1];
	private static int buff;
	
	private static boolean isopen = false;
	
	public Setting() {
		if(!isopen) {
			isopen = true;	
			jf = new JFrame("설정");
			jf.setSize(250, 300);
			jf.setLocationRelativeTo(null);
			jf.setResizable(true);
			
			jf.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					isopen = false;
				}
			});
			
			KeySetPanel();
			
			JMenuBar menubar = new JMenuBar();
			JMenu keySet = new JMenu("키 설정");
			
			
			menubar.add(keySet);
			
			jf.setJMenuBar(menubar);
			
			jf.setVisible(true);
		}
	}
	
	private static void KeySetPanel() {
		jp[0] = new JPanel(new GridLayout(8, 2));
		JButton[] k = new JButton[8];
		JLabel[] jl = new JLabel[8];
		String[] text = "스킬 1,스킬 2,스킬 3,스킬 4,아이템 1,아이템 2,아이템 3,퀘스트창 숨기기".split(",");
		
		for(buff = 0; buff < key.length; buff++) {
			k[buff] = new JButton(text[buff]);
			k[buff].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i = 0; i < key.length; i++) {
						k[i].setEnabled(false);
					}
					KeyPane(k, jl, buff - 8);
				}
			});
			jp[0].add(k[buff]);
			jl[buff] = new JLabel("현재 키 : " + KeyEvent.getKeyText(key[buff]), JLabel.CENTER);
			jp[0].add(jl[buff]);
		}
		jf.add(jp[0]);
	}
	
	private static void KeyPane(JButton[] k, JLabel[] jl, int keynum) {
		JFrame frame = new JFrame("키를 누르세요");
		frame.setSize(250, 100);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		JTextField jtf = new JTextField();
		jtf.setFont(new Font("", Font.BOLD, 30));
		jtf.setHorizontalAlignment(JTextField.CENTER);
		jtf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				boolean pass = true;
				for(int i = 0; i < key.length; i++) {
					if(key[i] == code) {
						Utill.PopUp("이미 지정되어 있습니다", Assat.popup250_100);
						pass = false;
						jtf.setText("");
					}
				}
				if(pass) {
					key[keynum] = e.getKeyCode();
					for(int i = 0; i < key.length; i++) {
						k[i].setEnabled(true);
					}
					frame.dispose();
				}
				for(int i = 0; i < jl.length; i++) {
					jl[i].setText("현재 키 : " + KeyEvent.getKeyText(key[i]));
				}
			}
		});
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				for(int i = 0; i < key.length; i++) {
					k[i].setEnabled(true);
				}
			}
		});
		
		frame.add(jtf);
		
		frame.setVisible(true);
	}
}
