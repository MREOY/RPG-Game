package dodngun.quest;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dodngun.assat.Assat;
import dodngun.Inventory.Inventory;
import dodngun.item.Item;
import dodngun.utill.Utill;

public class Quest {
	
	public static JPanel quest;
	public static JPanel quest1 = null;
	public static JPanel quest2 = null;
	public static JPanel quest3 = null;
	
	private static JPanel p;
	
	private static boolean isVisible = true;
	
	public static int RequestNewQuest(String questName, String questClear, Item[] reward, int exp, Item[] request) {
		JLabel jl = null;
		if(quest1 == null) {
			quest1 = Utill.SetImageBackground(Assat.questbar);
			jl = QuestJabel(questName, reward, request, exp);
			quest1.add(jl);
			quest.add(Quest.quest1);
			NewQuest(questName, questClear, reward, exp, request, jl, 0);
			return 1;
		}else if(quest2 == null) {
			quest2 = Utill.SetImageBackground(Assat.questbar);
			jl = QuestJabel(questName, reward, request, exp);
			quest2.add(jl);
			quest.add(Quest.quest2);
			NewQuest(questName, questClear, reward, exp, request, jl, 1);
			return 1;
		}else if(quest3 == null) {
			quest3 = Utill.SetImageBackground(Assat.questbar);
			jl = QuestJabel(questName, reward, request, exp);
			quest3.add(jl);
			quest.add(Quest.quest3);
			NewQuest(questName, questClear, reward, exp, request, jl, 2);
			return 1;
		}
		
		Utill.PopUp("퀘스트가 꽉차있습니다!", Assat.popup250_100);
		
		return 0;
	}
	
	public static void NewQuest(String questName, String questClear, Item[] reward, int exp, Item[] request, JLabel jl, int questnum){
		jl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Inventory.InvenReclear();
				if(QuestCheck(request)) {
					int check = Inventory.InvenCheck(reward);
					if(check == Inventory.lack || check == Inventory.full) 
						Utill.PopUp("인벤토리가 꽉 차있습니다.", Assat.popup250_100);
					else {
						Inventory.InvenRemove(request);
						Inventory.InvenAdd(reward);
						switch (questnum){
							case 0:
								quest1.removeAll();
								quest1.repaint();
								break;
							case 1:
								quest2.removeAll();
								quest2.repaint();
								break;
							case 2:
								quest3.removeAll();
								quest3.repaint();
								break;
						}
						if(questClear.equals("")) Utill.PopUp("퀘스트를 완료했습니다.", Assat.popup250_100);
						else Utill.PopUp(questClear, Assat.popup250_100);
						
						Assat.player.exp += exp;
						Inventory.InvenReload();
					}
				}else Utill.PopUp("퀘스트를 완료하지 못했습니다.", Assat.popup250_100);
			}
		});
	}
	
	public static void HideQuest() {
		if(isVisible) isVisible = false;
		else isVisible = true;
		p.setVisible(isVisible);
	}
	
	public static boolean QuestCheck(Item[] reqest) {
		Inventory.InvenReclear();
		int complete = 0;
		for(int i = 0; i < reqest.length; i++)
			for(int j = 0; j < Assat.player.inventory.length; j++)
				if(Assat.player.inventory[j] != null && Assat.player.inventory[j].id == reqest[i].id && Assat.player.inventory[j].count >= reqest[i].count)
					complete++;
		if(complete >= reqest.length) return true; 
		else return false;
	}
	
	public static JPanel QuestPane() {
		p = new JPanel(new BorderLayout());
		
		JScrollPane questpane = new JScrollPane();
		quest = Utill.SetImageBackground(Assat.questpanel);
		quest.setLayout(new GridLayout(3, 1));
		questpane.setViewportView(quest);
		
		p.setBounds(684, 100, 100, 300);
		p.add(questpane);
		return p;
	}
	
	private static JLabel QuestJabel(String questName, Item[] reward, Item[] request, int exp) {
		String test = "";
		JLabel jl = new JLabel();
		test += "<html><body><center>" + questName + "<br>필요 :";
		for(int i = 0; i < request.length; i++) {
			test += request[i].itemname + request[i].count + "개 ";
		}
		test += "<br>보상 :";
		for(int i = 0; i < reward.length; i++) {
			test += reward[i].itemname + reward[i].count + "개 ";
		}
		test += "<br>경험치 : " + exp;
		test += " </center></body></html>";
		jl.setText(test);
		return jl;
	}
}
