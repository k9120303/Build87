package beforePlay;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.GameSystem;
import javazoom.jl.decoder.JavaLayerException;
import set.GameSet;
import unit.ConfusionPlayer;
import unit.DestroyPlayer;
import unit.InvinciblePlayer;
import unit.JumperPlayer;
import unit.Player;
import unit.ReCoverPlayer;

public class Frame extends JFrame implements ActionListener
{

	ImageIcon mouseImg = new ImageIcon("mouse.png");
	ImageIcon mouseClickImg = new ImageIcon("mouseClick.png");
	Cursor cr = Toolkit.getDefaultToolkit().createCustomCursor(mouseImg.getImage(),new Point(0,0) ,"MyCursor" );
	private static final long serialVersionUID = 1L;
	JPanel panel1; //主畫面
	JPanel panel2; //遊戲說明
	JPanel panel3; //選角
	JPanel panel4; //人物說明
	JPanel panel5; //鬼說明
	
	JButton chooseCharacter; // 選擇腳色
	JButton back; //返回圖片
	JButton explain; //說明圖片
	JButton classic; // 經典圖片
	JButton live; // 死鬥圖片
	JButton gotoPlay; // 進入遊戲
	JButton leaveGame; // 離開圖片
	JButton page1;	//遊戲說明
	JButton page2;	//人物說明
	JButton page3;	//鬼說明
	JButton description1; //說明頁面1
	JButton description2; //說明頁面2
	JButton description3; //說明頁面3
	GameSystem gameSystem;
	
	
	Player player[] = new Player[2];
	private int choose = 0; //0代表P1 1代表P2
	
	public Frame()
	{
		addMouseListener(new MouseAdapter()
		{
			
			@Override
			public void  mousePressed(MouseEvent e)
			{
				
				Cursor cr2 = Toolkit.getDefaultToolkit().createCustomCursor(mouseClickImg.getImage(),new Point(0,0) ,"MyCursor" );
				setCursor(cr2);
				
				
			}
			
			@Override
			public void  mouseReleased(MouseEvent e)
			{
				setCursor(cr);
			}
	
		});
		
		setCursor(cr);
		
		
		
		panel1 = new startMenu();
		panel2 = new explanation1();
		panel3 = new selectCharacter();
		panel4 = new explanation2();
		panel5 = new explanation3();
		
		
		add(panel1);
		addWindowListener(new WindowAdapter(){ // 關閉Frame出現警告
			public void windowClosing(WindowEvent e)
			{
				int confirmed = JOptionPane.showConfirmDialog(null,
						"你確定要關閉遊戲嗎？", "警告", JOptionPane.YES_NO_OPTION);
				if (confirmed == JOptionPane.YES_OPTION) {
					dispose();
				} else {
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
			}
		});
	}
	
	public void setBackAndLeaveButton(JButton jbutton)
	{
		jbutton.setContentAreaFilled(false);
		jbutton.setBorder(null);
	}

	public class ListenToExplanation implements ActionListener
	{
		public void actionPerformed(ActionEvent ae) {
			getContentPane().removeAll();
			panel2 = new explanation();
			getContentPane().add(panel2);// Adding to content pane, not to Frame
			repaint();
			printAll(getGraphics());// Extort print all content
		}
	}
	
	public class ListenToStartMenu implements ActionListener
	{
		public void actionPerformed(ActionEvent ae) {
			getContentPane().removeAll();
			panel1 = new startMenu();
			getContentPane().add(panel1);// Adding to content pane, not to Frame
			repaint();
			printAll(getGraphics());// Extort print all content
		}
	}
	
	public class ListenToGame implements ActionListener
	{
		
		public void actionPerformed(ActionEvent ae) {
			gameSystem=null;
			
			gameSystem = new GameSystem(player[0],player[1],back);
			
			getContentPane().setFocusable(false);
			gameSystem.mapView.setFocusable(true); 
			gameSystem.mapView.requestFocusInWindow();	
			
			getContentPane().removeAll();
			getContentPane().add(gameSystem.gameView);
			
			repaint();
			printAll(getGraphics());// Extort print all content
		}
	}
	
	public class ListenToSelectCharacter implements ActionListener
	{
		public void actionPerformed(ActionEvent ae) {
			getContentPane().removeAll();
			panel3 = new selectCharacter();
			
			getContentPane().add(panel3);// Adding to content pane, not to Frame
			repaint();
			printAll(getGraphics());// Extort print all content
		}
	}

	public class ListenToDescription1 implements ActionListener
	{
		public void actionPerformed(ActionEvent ae) {
			getContentPane().removeAll();
			getContentPane().add(panel2);// Adding to content pane, not to Frame
			repaint();
			printAll(getGraphics());// Extort print all content
		}
	}
	
	public class ListenToDescription2 implements ActionListener
	{
		public void actionPerformed(ActionEvent ae) {
			getContentPane().removeAll();
			getContentPane().add(panel4);// Adding to content pane, not to Frame
			repaint();
			printAll(getGraphics());// Extort print all content
		}
	}
	
	public class ListenToDescription3 implements ActionListener
	{
		public void actionPerformed(ActionEvent ae) {
			getContentPane().removeAll();
			getContentPane().add(panel5);// Adding to content pane, not to Frame
			repaint();
			printAll(getGraphics());// Extort print all content
		}
	}
	
	public class startMenu extends JPanel
	{
		private static final long serialVersionUID = 1L;
		private JLabel lab;   // 建築師巴戚圖片
		
		public startMenu() {
			Color color = Color.decode("0xCD751B");
			setBackground(color);
			
			
			lab = new JLabel(new ImageIcon("view/建築師巴戚.jpg"));
			explain = new JButton(new ImageIcon("view/說明.png"));
			classic = new JButton(new ImageIcon("view/經典.png"));
			live = new JButton(new ImageIcon("view/生存.png"));
			leaveGame = new JButton(new ImageIcon(("view/離開.png")));
			
			setLayout(null);
			add(lab);
			add(explain);
			add(classic);
			add(live);
			add(leaveGame);
			classic.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					classic.setIcon(new ImageIcon("view/經典.png"));
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					classic.setIcon(new ImageIcon("view/經典2.png"));
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			live.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					live.setIcon(new ImageIcon("view/生存.png"));
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					live.setIcon(new ImageIcon("view/生存2.png"));
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			lab.setBounds(0, 0, 1067, 372);
			explain.setBounds(5, 630, 127, 81);
			classic.setBounds(325, 372, 409, 144);
			live.setBounds(365, 540, 350, 131);
			leaveGame.setBounds(927, 630, 140, 61);
			
			setBackAndLeaveButton(explain);
			setBackAndLeaveButton(classic);
			setBackAndLeaveButton(live);
			setBackAndLeaveButton(leaveGame);
		
			explain.addActionListener(new ListenToDescription1());
			classic.addActionListener(new ListenToSelectCharacter());
			live.addActionListener(new ListenToSelectCharacter());
			leaveGame.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) // 出現警告
				{
					int confirmed = JOptionPane.showConfirmDialog(null,
							"你確定要關閉遊戲嗎？", "警告", JOptionPane.YES_NO_OPTION);
					if (confirmed == JOptionPane.YES_OPTION) {
						dispose();
					} else {
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 甚麼都不做
					}
				}
			});
			
		}
	}

	public class explanation extends JPanel
	{
		private static final long serialVersionUID = 1L;
		public explanation()
		{
			Color color = Color.decode("0xCD751B");
			setBackground(color);
			setLayout(null);
			
			back = new JButton(new ImageIcon(("view/返回.png")));
			add(back);
			back.setBounds(5, 630, 127, 61);
			setBackAndLeaveButton(back);
			back.addActionListener(new ListenToStartMenu());
		}
	}

	public class selectCharacter extends JPanel
	{
		private static final long serialVersionUID = 1L;
		private JLabel jumper = new JLabel();
		private JLabel recover = new JLabel();
		private JLabel invincible = new JLabel();
		private JLabel confusion = new JLabel();
		private JLabel destroy = new JLabel();
		private JLabel playerChoose[] = new JLabel[2];
		
		
		private DetailPanel detail;
		public ArrayList<String>  text = new ArrayList<String>();
		
		public class DetailPanel extends JPanel
		{
			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				setOpaque(false);
				g.setFont(new Font(Font.DIALOG, 0, 20));
				for(int i=0;i<text.size();i++)
					g.drawString(text.get(i),0 , 15+i*50);
			
			}
		}
		
		

		public selectCharacter()
		{
			playerChoose[0] = new JLabel();
			playerChoose[1] = new JLabel();
			Color color = Color.decode("0xCD751B");
			setBackground(color);
			setLayout(null);
			detail = new DetailPanel();
			detail.setBounds((GameSet.playerViewWidth *2+ GameSet.viewWidth )/2 - 300,480,600,200);
			detail.setBackground(Color.white);
			jumper.setIcon(new ImageIcon("chooseCharacter/子賢.png"));
			recover.setIcon(new ImageIcon("chooseCharacter/培倫.png"));
			invincible.setIcon(new ImageIcon("chooseCharacter/老大.png"));
			confusion.setIcon(new ImageIcon("chooseCharacter/G7.png"));
			destroy.setIcon(new ImageIcon("chooseCharacter/侑錦.png"));
			jumper.setName("jumper");
			recover.setName("recover");
			invincible.setName("invincible");
			confusion.setName("confusion");
			destroy.setName("destroy");
			add(detail);
			add(jumper);
			add(recover);
			add(invincible);
			add(confusion);
			add(destroy);
			add(playerChoose[0]);
			add(playerChoose[1]);
			
			jumper.setBounds((GameSet.playerViewWidth + GameSet.viewWidth )/ 2 - 360, 200, 200, 200);
			recover.setBounds((GameSet.playerViewWidth + GameSet.viewWidth )/ 2 - 180, 200, 200, 200);
			invincible.setBounds((GameSet.playerViewWidth + GameSet.viewWidth )/ 2, 200, 200, 200);
			confusion.setBounds((GameSet.playerViewWidth + GameSet.viewWidth )/ 2 + 180, 200, 200, 200);
			destroy.setBounds( (GameSet.playerViewWidth + GameSet.viewWidth )/ 2 + 360, 200, 200, 200);
			playerChoose[0].setBounds(0,0,100,50);
			playerChoose[1].setBounds(GameSet.playerViewWidth*2 + GameSet.viewWidth -100,0,100,50);
			back = new JButton(new ImageIcon("view/返回.png"));
			
			
			setBackAndLeaveButton(back);
			
			add(back);
		
			
			back.addActionListener(new ListenToStartMenu());
			
			back.setBounds(5, 630, 127, 61);
			
			addLabelListen();
			
			
		}
		
		
		public void addLabelListen()
		{
			jumper.addMouseListener(new chooseCharacter());
			recover.addMouseListener(new chooseCharacter());
			invincible.addMouseListener(new chooseCharacter());
			confusion.addMouseListener(new chooseCharacter());
			destroy.addMouseListener(new chooseCharacter());
		}
		
		public class chooseCharacter extends MouseAdapter
		{
			@Override 
			public void mouseClicked(MouseEvent e)
			{
				int playerNum = choose % 2 ;
				switch(((JLabel)e.getSource()).getName())
				{
					case "jumper":
						player[playerNum] = new JumperPlayer(3+playerNum * 19, 3 + playerNum * 19);
						break;
					case "recover":
						player[playerNum] = new ReCoverPlayer(3+playerNum * 19, 3 + playerNum * 19);
						break;
					case "invincible":
						player[playerNum] = new InvinciblePlayer(3+playerNum * 19, 3 + playerNum * 19);
						break;
					case "confusion":
						player[playerNum] = new ConfusionPlayer(3+playerNum * 19, 3 + playerNum * 19);
						break;
					case "destroy":
						player[playerNum] = new DestroyPlayer(3+playerNum * 19, 3 + playerNum * 19);
						break;
				}
				playerChoose[playerNum].setFont(new Font(Font.DIALOG, Font.BOLD, 35));
				playerChoose[playerNum].setForeground(Color.PINK);
				playerChoose[playerNum].setText(new String().format("player %d 選擇了 %s",playerNum+1,((JLabel)e.getSource()).getName()));
				
				if(playerNum==0)
					playerChoose[playerNum].setBounds(50,50,450,50);
				else
					playerChoose[playerNum].setBounds(GameSet.playerViewWidth*2 + GameSet.viewWidth -500,50,450,50);
				choose++;
				if(choose==2)
				{
					addButton();
					choose=0;
				}
			}
		
			@Override 
			public void mouseEntered(MouseEvent e)
			{
				switch(((JLabel)e.getSource()).getName())
				{
					case "jumper":
						playerDetailShow(new JumperPlayer(0,0));
						break;
					case "recover":
						playerDetailShow(new ReCoverPlayer(0,0));
						break;
					case "invincible":
						playerDetailShow(new InvinciblePlayer(0,0));
						break;
					case "confusion":
						playerDetailShow(new ConfusionPlayer(0,0));
						break;
					case "destroy":
						playerDetailShow(new DestroyPlayer(0,0));
						break;
				}
				 javazoom.jl.player.Player music = null;
				try {
					
					FileInputStream fis = new FileInputStream("mouseClick.mp3");
					BufferedInputStream bis = new BufferedInputStream(fis);
				    music = new javazoom.jl.player.Player(bis);
				   music.play();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			public void playerDetailShow(Player playerType)
			{
				
				text = playerType.setIntroduction();
				detail.repaint();
			
			}
			
		}
		public void addButton()
		{
			
			gotoPlay = new JButton(new ImageIcon("view/開始遊戲.png"));
			setBackAndLeaveButton(gotoPlay);
			gotoPlay.addActionListener(new ListenToGame());
			add(gotoPlay);
			gotoPlay.setBounds(875, 630, 219, 60);
		}
	}
	
	public class explanation1 extends JPanel
	{
		private static final long serialVersionUID = 1L;
		public explanation1()
		{
			Color color = Color.decode("0xCD751B");
			setBackground(color);
			setLayout(null);
			
			back = new JButton(new ImageIcon(("view/返回.png")));
			add(back);
			back.setBounds(5, 630, 127, 61);
			setBackAndLeaveButton(back);
			back.addActionListener(new ListenToStartMenu());
			
			description1 = new JButton(new ImageIcon(("view/說明頁面1.png")));
			add(description1);
			setBackAndLeaveButton(description1);
			description1.setBounds(50, 100, 1024, 497);
			
			page1 = new JButton(new ImageIcon("view/遊戲說明.png"));
			setBackAndLeaveButton(page1);
			add(page1);
			page1.setBounds(10, 10, 131, 45);
			page1.addActionListener(new ListenToDescription1());
			
			page2 = new JButton(new ImageIcon("view/人物說明.png"));
			setBackAndLeaveButton(page2);
			add(page2);
			page2.setBounds(150, 10, 131, 45);
			page2.addActionListener(new ListenToDescription2());
			
			page3 = new JButton(new ImageIcon("view/鬼說明.png"));
			setBackAndLeaveButton(page3);
			add(page3);
			page3.setBounds(290, 10, 106, 45);
			page3.addActionListener(new ListenToDescription3());
		}
	}
	
	public class explanation2 extends JPanel
	{
		private static final long serialVersionUID = 1L;
		public explanation2()
		{
			Color color = Color.decode("0xCD751B");
			setBackground(color);
			setLayout(null);
			
			back = new JButton(new ImageIcon(("view/返回.png")));
			add(back);
			back.setBounds(5, 630, 127, 61);
			setBackAndLeaveButton(back);
			back.addActionListener(new ListenToDescription1());
			
			description2 = new JButton(new ImageIcon(("view/說明頁面2.png")));
			add(description2);
			setBackAndLeaveButton(description2);
			description2.setBounds(100, 60, 972, 634);
			
			page1 = new JButton(new ImageIcon("view/遊戲說明.png"));
			setBackAndLeaveButton(page1);
			add(page1);
			page1.setBounds(10, 10, 131, 45);
			page1.addActionListener(new ListenToDescription1());
			
			page2 = new JButton(new ImageIcon("view/人物說明.png"));
			setBackAndLeaveButton(page2);
			add(page2);
			page2.setBounds(150, 10, 131, 45);
			page2.addActionListener(new ListenToDescription2());
			
			page3 = new JButton(new ImageIcon("view/鬼說明.png"));
			setBackAndLeaveButton(page3);
			add(page3);
			page3.setBounds(290, 10, 106, 45);
			page3.addActionListener(new ListenToDescription3());
		}
	}
	
	public class explanation3 extends JPanel
	{
		private static final long serialVersionUID = 1L;
		public explanation3()
		{
			Color color = Color.decode("0xCD751B");
			setBackground(color);
			setLayout(null);
			
			back = new JButton(new ImageIcon(("view/返回.png")));
			add(back);
			back.setBounds(5, 630, 127, 61);
			setBackAndLeaveButton(back);
			back.addActionListener(new ListenToDescription1());
			
			description3 = new JButton(new ImageIcon(("view/說明頁面3.png")));
			add(description3);
			setBackAndLeaveButton(description3);
			description3.setBounds(50, 100, 1024, 447);
			
			page1 = new JButton(new ImageIcon("view/遊戲說明.png"));
			setBackAndLeaveButton(page1);
			add(page1);
			page1.setBounds(10, 10, 131, 45);
			page1.addActionListener(new ListenToDescription1());
			
			page2 = new JButton(new ImageIcon("view/人物說明.png"));
			setBackAndLeaveButton(page2);
			add(page2);
			page2.setBounds(150, 10, 131, 45);
			page2.addActionListener(new ListenToDescription2());
			
			page3 = new JButton(new ImageIcon("view/鬼說明.png"));
			setBackAndLeaveButton(page3);
			add(page3);
			page3.setBounds(290, 10, 106, 45);
			page3.addActionListener(new ListenToDescription3());
		}
	}

	
	
	public static void main(String[] args)
	{
		Frame s = new Frame();
		s.setTitle("建築師巴戚");
		s.setSize(GameSet.playerViewWidth*2 + GameSet.viewWidth,GameSet.viewHeight + 25); // 設定視窗大小
		s.setLocationRelativeTo(null);
		s.setVisible(true);
		s.setResizable(false); // 不准調整視窗大小
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
	}
	
	
	
}