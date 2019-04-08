import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyEventPostProcessor;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PlayGame extends JPanel {
	static int x2;
	static int pacmanh = 13,pacmanw = 19,eatnumber = 0,score = 0,life=2,time = 90,keycheck = 0,waitup =0,waitdown=0,waitleft =0,waitright=0,before_enemy=0,before_enemy2=0;
	static int enemy_x=0,enemy_y=0,time2 = 1,enemy_x2=0,enemy_y2=0,location_save=0,location_save1=0,real_location_save=1,real_location_save1=1; //location_save 0이 eat이고, 1이 empty
	static int difficulty = 8,difficulty2=1; //게임의 난이도로 0=easy, 1=normal, 2=difficult 3=crazy
	private JLabel lblNewLabel;
	static int boss_number=0;
	static int attack_x_up,attack_y_up,enemy_life=10;
	static int x_boss =50,y_boss=100;
	static int empty_wall=3;
	static int final_x=0,final_y=0,add_enemy_location = 1000;
	static int pacman_x=900,pacman_y=250,res=0,boss_stage=0;
	static int pacman_timer_check=0,end_enemy,cherry_location_x=0,cherry_location_y=0,eat_enemy=0;
	public static void main(String args[]) throws ClassNotFoundException, SQLException {

		Class.forName("org.sqlite.JDBC");
		Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db" );
		
		JFrame frame = new JFrame();
		
		ImageIcon easy = new ImageIcon("easy.png");
		ImageIcon normal = new ImageIcon("normal.png");
		ImageIcon hard = new ImageIcon("difficult.png");
		ImageIcon crazy = new ImageIcon("crazy.png");
		ImageIcon cherry = new ImageIcon("cherry.png");
		
		ImageIcon lifeImg = new ImageIcon("life.png");
		ImageIcon scoretext = new ImageIcon("score.png");
		ImageIcon one = new ImageIcon("one.png");
		ImageIcon two = new ImageIcon("two.png");
		ImageIcon three = new ImageIcon("three.png");
		ImageIcon four = new ImageIcon("four.png");
		ImageIcon five = new ImageIcon("five.png");
		ImageIcon six = new ImageIcon("six.png");
		ImageIcon seven = new ImageIcon("seven.png");
		ImageIcon eight = new ImageIcon("eight.png");
		ImageIcon nine = new ImageIcon("nine.png");
		ImageIcon zero = new ImageIcon("zero.png");
		
		ImageIcon wall = new ImageIcon("wall.png");
		ImageIcon empty = new ImageIcon("empty.png");
		ImageIcon empty2 = new ImageIcon("empty.png");
		ImageIcon eat = new ImageIcon("eat.png");
		ImageIcon pacman = new ImageIcon("pacman.png");
		ImageIcon pacmanleft = new ImageIcon("pacmanleft.png");
		ImageIcon pacmanright = new ImageIcon("pacmanright.png");
		ImageIcon pacmanup = new ImageIcon("pacmanup.png");
		ImageIcon pacmandown = new ImageIcon("pacmandown.png");
		ImageIcon enemy = new ImageIcon("redenemy.png");
		ImageIcon enemy_pink = new ImageIcon("pinkenemy.png");
		ImageIcon enemy_orange = new ImageIcon("orangeenemy.png");
		ImageIcon enemy_blue = new ImageIcon("blueenemy.png");
		ImageIcon boss = new ImageIcon("boss.png");
		ImageIcon boss_close = new ImageIcon("boss_close.png");
		ImageIcon boss_fire = new ImageIcon("boss_fire.png");
		
		JDialog dialog = new JDialog();
		JTextArea txtA = new JTextArea();
		JButton tImg = new JButton(new ImageIcon("timeover.png"));
			tImg.setBackground(Color.black);
			tImg.setBounds(120, 100, 735, 173);
			tImg.setBorderPainted(false);
			tImg.setFocusPainted(false);
		
			JButton gImg = new JButton(new ImageIcon("gameover.png"));
			gImg.setBackground(Color.black);
			gImg.setBounds(120, 100, 735, 173);
			gImg.setBorderPainted(false);
			gImg.setFocusPainted(false);
			
		JLabel[][] f = new JLabel[15][21];
		
		int map_i,map_j,map_x=0,map_y=0,map_rotate,size_x=14,size_y=20;
		
		int[][] emptylocation=new int[size_x][size_y];
		int[][] real_emptylocation=new int [size_x+1][size_y+1];
		
		for(map_i=0;map_i<size_x;map_i++) {
			for(map_j=0;map_j<size_y;map_j++) {
				emptylocation[map_i][map_j] = 0;
			}
		}
		
		
		emptylocation[map_x][map_y] = 1;
		
		for(map_i=0;map_i<100000;map_i++) {
			map_rotate = (int)(Math.random()*4)+1;
			
			if(map_rotate ==1) {
				if(map_y==0)
					continue;
				if(emptylocation[map_x][map_y-2]==0) {
					map_y--; emptylocation[map_x][map_y]=1;
					map_y--; emptylocation[map_x][map_y]=1;
				}
				else {
					map_y-=2;
				}
			}
			
			if(map_rotate ==2) {
				if(map_x == size_x-2 )
					continue;
				if(emptylocation[map_x+2][map_y]==0) {
					map_x++; emptylocation[map_x][map_y]=1;
					map_x++; emptylocation[map_x][map_y]=1;
				}
				else {
					map_x+=2;
				}
			}
			
			if(map_rotate ==3) {
				if(map_y == size_y-2)
					continue;
				if(emptylocation[map_x][map_y+2]==0) {
					map_y++; emptylocation[map_x][map_y]=1;
					map_y++; emptylocation[map_x][map_y]=1;
				}
				else {
					map_y+=2;
				}
			}
			
			if(map_rotate ==4) {
				if(map_x==0)
					continue;
				if(emptylocation[map_x-2][map_y]==0) {
					map_x--; emptylocation[map_x][map_y]=1;
					map_x--; emptylocation[map_x][map_y]=1;
				}
				else {
					map_x-=2;
				}
			}
		}
		
		enemy_x = (int)(Math.random()*11)+2;
		enemy_y = (int)(Math.random()*18)+2;
		
		while(emptylocation[enemy_x][enemy_y] == 0) {
			enemy_x = (int)(Math.random()*11)+2;
			enemy_y = (int)(Math.random()*18)+1;
		}
		
		enemy_x2 = (int)(Math.random()*11)+2;
		enemy_y2 = (int)(Math.random()*13)+2;
		
		while(emptylocation[enemy_x2][enemy_y2] == 0||enemy_x==enemy_x2||enemy_y==enemy_y2) {
			enemy_x2 = (int)(Math.random()*11)+2;
			enemy_y2 = (int)(Math.random()*13)+2;
		}
		
		cherry_location_x = (int)(Math.random()*9)+2;
		cherry_location_y = (int)(Math.random()*13)+2;
		
		while(emptylocation[enemy_x][enemy_y] == 0||enemy_x==cherry_location_x||enemy_y==cherry_location_y||enemy_x2==cherry_location_x||enemy_y2==cherry_location_y) {
			cherry_location_x = (int)(Math.random()*9)+2;
			cherry_location_x = (int)(Math.random()*13)+2;
		}
		
		
		
		
		
		
		for(int i=0;i<size_x;i++) {
			for(int j=0;j<size_y;j++) {
				
				real_emptylocation[i+1][j+1] = emptylocation[i][j];
			}
		}
		
		for(int i=0;i<size_y;i++) {
			real_emptylocation[0][i] = 5;
		}
		
		for(int i=0;i<size_x;i++) {
			real_emptylocation[i][0] = 0;
		}
		
		
		for(int i=0;i<f.length;i++) {
			for(int j=0;j<f[0].length;j++) {
				f[i][j] = new JLabel();
			}
		}
		
		
		
		JPanel start = new JPanel() {
			
			@Override
			public void setBackground(Color bg) {
				// TODO Auto-generated method stub
				super.setBackground(Color.black);
			}
			
	
		};
		
		JButton startImg = new JButton(new ImageIcon("pacmantitle.png"));
		startImg.setBackground(Color.black);
		startImg.setBounds(120, 100, 735, 173);
		startImg.setBorderPainted(false);
		startImg.setFocusPainted(false);
		start.add(startImg);
		
		
		
		start.setLayout(null);
		
		start.setPreferredSize(new Dimension(1000, 600));
		
		
		JButton btnNewButton = new JButton(new ImageIcon("start.png"));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
              start.setVisible(false);
              
              
              ImageIcon setting = new ImageIcon("setting.png");
              JPanel level = new JPanel() {
            	  @Override
        	        public void paintComponent(Graphics g) {
        	        	super.paintComponent(g);
        	        	Graphics2D g2d = (Graphics2D)g;
        	        	
        	        	g2d.drawImage(setting.getImage(), 165, 70, 651, 54, null);
        	        }
              };
              level.setPreferredSize(new Dimension(1000, 600));
              level.setBackground(Color.black);
              level.setLayout(null);
              
              
              
              JButton b1 = new JButton(new ImageIcon("easy.png"));
              JButton b2 = new JButton(new ImageIcon("normal.png"));
              JButton b3 = new JButton(new ImageIcon("difficult.png"));
              JButton b4 = new JButton(new ImageIcon("crazy.png"));
              JButton check = new JButton(new ImageIcon("select.png"));
              
              
              JButton littlepacman = new JButton(new ImageIcon("title.png"));
            
              littlepacman.setBackground(Color.black);
              littlepacman.setBounds(130, 210, 50, 50);
              littlepacman.setBorderPainted(false);
              littlepacman.setFocusPainted(false);	

              level.add(littlepacman);
              
              
              
              b1.setBackground(Color.black);
              b1.setBounds(180, 220, 144, 35);
              b1.setBorderPainted(false);
              b1.setFocusPainted(false);
             
              level.add(b1);
              
              b1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					difficulty2=1;
					difficulty = 8;
					littlepacman.setBounds(130, 210, 50, 50);
					
				}
			});
              
              b2.setBackground(Color.black);
              b2.setBorderPainted(false);
              b2.setFocusPainted(false);
              b2.setBounds(570, 220, 180, 35);
              level.add(b2);
              
              b2.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					difficulty2=2;
					difficulty =6;
					littlepacman.setBounds(510, 210, 50, 50);
					
				}
			});
              
              b3.setBackground(Color.black);
              b3.setBorderPainted(false);
              b3.setFocusPainted(false);
              b3.setBounds(180, 370, 270, 35);
              level.add(b3);
             
              b3.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					difficulty2=3;
					difficulty = 4;
					littlepacman.setBounds(120, 360, 50, 50);
				}
			});
              
              b4.setBackground(Color.black);
              b4.setBorderPainted(false);
              b4.setFocusPainted(false);
              b4.setBounds(570, 370, 144, 35);
              level.add(b4);
              
              b4.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					difficulty2=4;
					difficulty = 1;
					littlepacman.setBounds(510, 360, 50, 50);
				}
			});
              
              check.setBackground(Color.black);
              check.setBorderPainted(false);
              check.setFocusPainted(false);
              check.setBounds(380, 470, 244, 60);
              level.add(check);
              
              check.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					level.setVisible(false);
					
					 JPanel panel = new JPanel();
			            panel.setPreferredSize(new Dimension(1000, 600));
			      		panel.setBackground(Color.black);
			      		panel.setLayout(new GridLayout(f.length,f[0].length));
			      		
			      		 
			      		frame.requestFocus();		
			      		
			      		
			      		System.out.println(cherry_location_x+" "+cherry_location_y);
			      		
			      		f[enemy_x2][enemy_y2].setIcon(enemy_pink);
			      		
			      		for(int i=0; i<f.length; i++) {
			      			for(int j=0; j<f[0].length; j++) {
			      				
			      				if(real_emptylocation[i][j]==0) 
			      				f[i][j].setIcon(wall);
			      				
			      				else if(real_emptylocation[i][j] == 1) {
			      					f[i][j].setIcon(eat);
			      					eatnumber++;
			      				}
			      				
			      				else if(real_emptylocation[i][j] == 2)
			      					f[i][j].setIcon(empty);
			      				
			      				else if(real_emptylocation[i][j] == 3)
			      					f[i][j].setIcon(pacmanleft);
			      				else if(real_emptylocation[i][j]==4) 
			      					f[i][j].setIcon(enemy);
			      				else if(real_emptylocation[i][j]==6) 
			      					f[i][j].setIcon(enemy_pink);
			      					
			      				else if(real_emptylocation[i][j]==5)
			      					f[i][j].setIcon(empty2);
			      				else
			      					f[i][j].setIcon(lifeImg);
			      				
			      				panel.add(f[i][j]);
			      			}
			      		}
			      		f[cherry_location_x][cherry_location_y].setIcon(cherry);
			      		f[pacmanh][pacmanw].setIcon(pacmanleft);
			      		
			      		f[0][0].setIcon(scoretext);
			      		
			      		int enemy_location[][] = {{1,0},{-1,0},{0,1},{0,-1}};
			      		
			      		f[0][17].setIcon(lifeImg);
			      		f[0][18].setIcon(lifeImg);		      		
			      		
			      		Timer enemy_timer = new Timer();
			      			TimerTask enemy_task = new TimerTask() {

								@Override
								public void run() {
									int random = (int)(Math.random()*4);
									int random2 = (int)(Math.random()*4);
						      		

									if((f[enemy_x2+enemy_location[random2][0]][enemy_y2+enemy_location[random2][1]].getIcon()).equals(pacmanright)||(f[enemy_x2+enemy_location[random2][0]][enemy_y2+enemy_location[random2][1]].getIcon()).equals(pacmanleft)||(f[enemy_x2+enemy_location[random2][0]][enemy_y2+enemy_location[random2][1]].getIcon()).equals(pacmanup)||(f[enemy_x2+enemy_location[random2][0]][enemy_y2+enemy_location[random2][1]].getIcon()).equals(pacmandown)) {
										switch(life) {
										case 2: 
											--life;
											f[0][17].setIcon(empty);
											break;
										case 1: 
											f[0][18].setIcon(empty);
											--life;
											break;
										case 0: 
											System.out.println("넌 끝이야");
											--life;
											break;
										}
									}
									
									if(life ==0) {
										
										panel.setVisible(false);
										
										JPanel boss_gameover = new JPanel();
		          						
	      								
	      								boss_gameover.setLayout(null);
	      								
	      								JTextField textfield = new JTextField();
	      								textfield.setBounds(530,300,170,50);
	      								boss_gameover.add(textfield);
	      								
	      								boss_gameover.setPreferredSize(new Dimension(1000, 600));
	      								boss_gameover.setBackground(Color.black);
				      					
	      								JButton name = new JButton(new ImageIcon("name.png"));
	      								name.setBackground(Color.black);
	      								name.setBounds(380, 300, 150, 50);
	      								name.setBorderPainted(false);
	      								name.setFocusPainted(false);
			          					boss_gameover.add(name);
	      								
	      								JButton insert = new JButton(new ImageIcon("insert.png"));
	      								insert.setBackground(Color.black);
	      								insert.setBounds(400, 400, 244, 60);
	      								insert.setBorderPainted(false);
	      								insert.setFocusPainted(false);
			          					boss_gameover.add(insert);
	      								
			          					insert.addActionListener(new ActionListener() {
											
											@Override
											public void actionPerformed(ActionEvent e) {

												JFrame frame2;
												JPanel panel2 =new JPanel();
												JTextArea txtA = new JTextArea();
												
												
												
												panel2.setPreferredSize(new Dimension(1000, 600));
											frame2 = new JFrame();
											
											
											PreparedStatement ps;
											try {
												ps = connection.prepareStatement("INSERT INTO test VALUES(?,?)");
												ps.setString(1, textfield.getText().toString()); //0이 아니라 1부터 시작한다
												ps.setInt(2, score);
												
												int res = ps.executeUpdate();
												if(res ==1) {
													System.out.println("성공");
												}
												ps.close();

												
											} catch (SQLException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											};

											
											Statement s;
											
											try {
												s = connection.createStatement();
												ResultSet rs = s.executeQuery("select * from test");
												

												
												while(rs.next()) {
													
													String v1 = rs.getString("value1")+"님 ";
													txtA.append(v1);
													
													int v2 = rs.getInt("value2");
													String v3 = String.valueOf(v2)+"점 \n";
													
													txtA.append(v3);
													
													//int v4 = rs.getInt("value4");
													
													System.out.println(v1);
													System.out.println(v2);
												
													
												}
												
											} catch (SQLException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
											txtA.setEditable(false); 
											panel2.add(txtA);
											frame2.add(panel2);
											frame2.setBackground(Color.black);
											frame2.setSize(1000,600);
											frame2.setVisible(true);
											frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

											}
										});
			          					
			          					
			          					
	      								JButton gameover_img = new JButton(new ImageIcon("gameover.png"));
	      								gameover_img.setBackground(Color.black);
	      								gameover_img.setBounds(130, 80, 735, 173);
	      								gameover_img.setBorderPainted(false);
	      								gameover_img.setFocusPainted(false);
			          					boss_gameover.add(gameover_img);
				      					
				      					frame.add(boss_gameover);
				      					frame.pack();
				      					frame.setVisible(true);
				      					
									
									}
									
									if((f[enemy_x+enemy_location[random2][0]][enemy_y+enemy_location[random][1]].getIcon()).equals(pacmanright)||(f[enemy_x+enemy_location[random][0]][enemy_y+enemy_location[random][1]].getIcon()).equals(pacmanleft)||(f[enemy_x+enemy_location[random][0]][enemy_y+enemy_location[random][1]].getIcon()).equals(pacmanup)||(f[enemy_x+enemy_location[random][0]][enemy_y+enemy_location[random][1]].getIcon()).equals(pacmandown)) {
										switch(life) {
										case 2: 
											--life;
											f[0][17].setIcon(empty);
											break;
										case 1: 
											f[0][18].setIcon(empty);
											--life;
											break;
										case 0: 
											System.out.println("넌 끝이야");
											--life;
											break;
										}
									}
						      		
									
									while(enemy_x+enemy_location[random][0]>=2&&enemy_x+enemy_location[random][0]<=11&&enemy_y+enemy_location[random][1]>=1&&enemy_y+enemy_location[random][1]>=20) 
						      			random = (int)(Math.random()*4);
						      		
						      		while(enemy_x2+enemy_location[random2][0]>=2&&enemy_x2+enemy_location[random2][0]<=11&&enemy_y2+enemy_location[random2][1]>=1&&enemy_y2+enemy_location[random2][1]>=20) 
						      			random2 = (int)(Math.random()*4);
						      		
									while(!((f[enemy_x+enemy_location[random][0]][enemy_y+enemy_location[random][1]].getIcon()).equals(empty)||(f[enemy_x+enemy_location[random][0]][enemy_y+enemy_location[random][1]].getIcon()).equals(eat))) 
						      			random = (int)(Math.random()*4);

						      		while(!((f[enemy_x2+enemy_location[random2][0]][enemy_y2+enemy_location[random2][1]].getIcon()).equals(empty)||(f[enemy_x2+enemy_location[random2][0]][enemy_y2+enemy_location[random2][1]].getIcon()).equals(eat))) 
						      			random2 = (int)(Math.random()*4);
									
						      		if((f[enemy_x+enemy_location[random][0]][enemy_y+enemy_location[random][1]].getIcon()).equals(empty))
						      			location_save = 1;
						      		
						      		else if((f[enemy_x+enemy_location[random][0]][enemy_y+enemy_location[random][1]].getIcon()).equals(eat))
						      			location_save=0;
						      			
						      		if((f[enemy_x2+enemy_location[random2][0]][enemy_y2+enemy_location[random2][1]].getIcon()).equals(empty))
						      			location_save1 = 1;
						      		else if((f[enemy_x2+enemy_location[random2][0]][enemy_y2+enemy_location[random2][1]].getIcon()).equals(eat))
						      			location_save1=0;
						      		
						      		switch(real_location_save) {
						      		case 0:f[enemy_x][enemy_y].setIcon(eat);break;
						      		case 1:f[enemy_x][enemy_y].setIcon(empty);break;
						      		}
						      		
						      		before_enemy=random;
						      		before_enemy2 =random2;
						      		
						      		enemy_x+=enemy_location[random][0];
						      		enemy_y+=enemy_location[random][1];
						      		
						      		f[enemy_x][enemy_y].setIcon(enemy);
//						      		
						      		switch(real_location_save1) {
						      		case 0:f[enemy_x2][enemy_y2].setIcon(eat);break;
						      		case 1:f[enemy_x2][enemy_y2].setIcon(empty);break;
						      		}
							      	
						      		enemy_x2+=enemy_location[random2][0];
						      		enemy_y2+=enemy_location[random2][1];
						      		
						      		f[enemy_x2][enemy_y2].setIcon(enemy_pink);
//						      		
						      		real_location_save = location_save;
						      		real_location_save1 = location_save1;
						      		
								}
			      				
			      			};
			      		
			      		enemy_timer.schedule(enemy_task, 0,difficulty*60);
//			      		
			      		class KeyClick extends KeyAdapter {
			      			
			      			void showscore(int score) {
			      				
			      				int location,score2;
			      				if(score/10>=10) {
			      					location=1;
			      				}
			      				else if(score/10!=0) {
			      					location = 2;
			      				}
			      				else {
			      					location = 3;
			      				}
			      				
			      				while(location<=3) {
			      				if(location==1)
			      					score2=score/100;
			      					
			      					else if(location ==2)
			      						score2= score/10%10;
			      					else
			      						score2 = score%10;
			      				switch(score2) {
			      				case 0:
			      					f[0][location].setIcon(zero); break;
			      				case 1:
			      					f[0][location].setIcon(one); break;
			      				case 2:
			      					f[0][location].setIcon(two); break;
			      				case 3:
			      					f[0][location].setIcon(three); break;
			      				case 4:
			      					f[0][location].setIcon(four); break;
			      				case 5:
			      					f[0][location].setIcon(five); break;
			      				case 6:
			      					f[0][location].setIcon(six); break;
			      				case 7:
			      					f[0][location].setIcon(seven); break;
			      				case 8:
			      					f[0][location].setIcon(eight); break;
			      				case 9:
			      					f[0][location].setIcon(nine); break;
			      				}
			      				location++;
			      				}
			      				f[0][4].setIcon(zero);
			      				
			      			}
			      			
			      			
			      			public void score_check(int score) {
			      				if(score==237) {
			      					//line
			      					boss_stage=1;
			      					System.out.println(eatnumber);
			      					if(boss_number++==0) {
			      					JOptionPane.showMessageDialog(null, "모든 먹이를 다 먹었습니다! 최종 보스가 나옵니다", " ", JOptionPane.PLAIN_MESSAGE);
			      					
			      					}
	
			      					panel.setVisible(false);
			      					panel.removeAll();
			      					
			      					
			      					
			      					JPanel scorecheck = new JPanel(); 
			      					scorecheck.setLayout(null);
			      					
			      					
			      					JButton boss_heart1 = new JButton(new ImageIcon("boss_heart.png"));
			      					boss_heart1.setContentAreaFilled(false);
			      					boss_heart1.setBounds(750, 30, 50, 50);
			      					boss_heart1.setBorderPainted(false);
			      					boss_heart1.setFocusPainted(false);
		          					scorecheck.add(boss_heart1);
		          					
		          					JButton boss_heart2 = new JButton(new ImageIcon("boss_heart.png"));
		          					boss_heart2.setContentAreaFilled(false);
		          					boss_heart2.setBounds(800, 30, 50, 50);
		          					boss_heart2.setBorderPainted(false);
		          					boss_heart2.setFocusPainted(false);
		          					scorecheck.add(boss_heart2);
		          					
		          					JButton boss_heart3 = new JButton(new ImageIcon("boss_heart.png"));
		          					boss_heart3.setContentAreaFilled(false);
		          					boss_heart3.setBounds(850, 30, 50, 50);
		          					boss_heart3.setBorderPainted(false);
		          					boss_heart3.setFocusPainted(false);
		          					scorecheck.add(boss_heart3);
		          					
		          					JButton boss_heart4 = new JButton(new ImageIcon("boss_heart.png"));
		          					boss_heart4.setContentAreaFilled(false);
		          					boss_heart4.setBounds(900, 30, 50, 50);
		          					boss_heart4.setBorderPainted(false);
		          					boss_heart4.setFocusPainted(false);
		          					scorecheck.add(boss_heart4);
		          					
		          					JButton boss_heart5 = new JButton(new ImageIcon("boss_heart.png"));
		          					boss_heart5.setContentAreaFilled(false);
		          					boss_heart5.setBounds(950, 30, 50, 50);
		          					boss_heart5.setBorderPainted(false);
		          					boss_heart5.setFocusPainted(false);
		          					scorecheck.add(boss_heart5);
			      					
			      					
			      					
			      					
			      					JButton boss_pacman = new JButton(new ImageIcon("pacmanleft.png"));
			      					
			      					
			      					if(boss_number==1) {
		          					boss_pacman.setBounds(pacman_x, pacman_y, 50, 50);
			      					
			      					System.out.println("처음 ");
			      					}
			      					
			      					boss_pacman.setContentAreaFilled(false);
		          					boss_pacman.setBorderPainted(false);
		          					boss_pacman.setFocusPainted(false);
		          					scorecheck.add(boss_pacman);
		          					
		          					JButton boss_Img = new JButton(new ImageIcon("boss.png"));
		          					boss_Img.setContentAreaFilled(false);
		          					boss_Img.setBounds(x_boss, y_boss, 350, 350);
		          					boss_Img.setBorderPainted(false);
		          					boss_Img.setFocusPainted(false);
		          					scorecheck.add(boss_Img);
		          					
		          					JButton boss_acttack_eat = new JButton(new ImageIcon("eat.png"));
		          					boss_acttack_eat.setContentAreaFilled(false);
		          					boss_acttack_eat.setBorderPainted(false);
		          					boss_acttack_eat.setFocusPainted(false);
		          					scorecheck.add(boss_acttack_eat);
		          					
		          					
		          					JButton boss_wall1 = new JButton(new ImageIcon("wall.png"));
		          					boss_wall1.setContentAreaFilled(false);
		          					boss_wall1.setBorderPainted(false);
		          					boss_wall1.setFocusPainted(false);
		          					scorecheck.add(boss_wall1);
		          					
		          					JButton boss_wall2 = new JButton(new ImageIcon("wall.png"));
		          					boss_wall2.setContentAreaFilled(false);
		          					boss_wall2.setBorderPainted(false);
		          					boss_wall2.setFocusPainted(false);
		          					scorecheck.add(boss_wall2);
		          					
		          					JButton boss_wall3 = new JButton(new ImageIcon("wall.png"));
		          					boss_wall3.setContentAreaFilled(false);
		          					boss_wall3.setBorderPainted(false);
		          					boss_wall3.setFocusPainted(false);
		          					scorecheck.add(boss_wall3);
		          					
		          					JButton boss_wall4 = new JButton(new ImageIcon("wall.png"));
		          					boss_wall4.setContentAreaFilled(false);
		          					boss_wall4.setBorderPainted(false);
		          					boss_wall4.setFocusPainted(false);
		          					scorecheck.add(boss_wall4);
		          					
		          					JButton boss_wall5 = new JButton(new ImageIcon("wall.png"));
		          					boss_wall5.setContentAreaFilled(false);
		          					boss_wall5.setBorderPainted(false);
		          					boss_wall5.setFocusPainted(false);
		          					scorecheck.add(boss_wall5);
		          					
		          					JButton boss_wall6 = new JButton(new ImageIcon("wall.png"));
		          					boss_wall6.setContentAreaFilled(false);
		          					boss_wall6.setBorderPainted(false);
		          					boss_wall6.setFocusPainted(false);
		          					scorecheck.add(boss_wall6);
		          					
		          					JButton boss_wall7 = new JButton(new ImageIcon("wall.png"));
		          					boss_wall7.setContentAreaFilled(false);
		          					boss_wall7.setBorderPainted(false);
		          					boss_wall7.setFocusPainted(false);
		          					scorecheck.add(boss_wall7);
		          					
		          					class attack_boss{
		          						public attack_boss(int x, int y) {
											
		          							
		          							x2=x;
					      					
					      					Timer attack_boss_timer = new Timer();
					      					TimerTask attack_boss_task = new TimerTask() {
					      						
					      						@Override
					      						public void run() {
					      							
					      							
					      							
					      							
					      							x2-=100;
					      							
					      							 if(((pacman_y/50)-1) == (empty_wall+1)) {
				          									end_enemy=230;
				          									
							      							} 
							      							 
							      						else{
							      							end_enemy=320;
			 											}
					      							
					      							
					      							if(x2<=x_boss+end_enemy) {
					      								attack_boss_timer.cancel();
					      						
					      								if(end_enemy==230)
					      									enemy_life--;
					      							
					      								
					      								if(enemy_life==5) {

					      									
					      						
							          						JPanel boss_gameover = new JPanel();
							          						scorecheck.setVisible(false);
						      								scorecheck.removeAll();
						      								
						      								boss_gameover.setLayout(null);
						      								
						      								JTextField textfield = new JTextField();
						      								textfield.setBounds(530,300,170,50);
						      								boss_gameover.add(textfield);
						      								

						      								boss_gameover.setPreferredSize(new Dimension(1000, 600));
						      								boss_gameover.setBackground(Color.black);
									      					
						      								JButton name = new JButton(new ImageIcon("name.png"));
						      								name.setBackground(Color.black);
						      								name.setBounds(380, 300, 150, 50);
						      								name.setBorderPainted(false);
						      								name.setFocusPainted(false);
								          					boss_gameover.add(name);
						      								
						      								JButton insert = new JButton(new ImageIcon("insert.png"));
						      								insert.setBackground(Color.black);
						      								insert.setBounds(400, 400, 244, 60);
						      								insert.setBorderPainted(false);
						      								insert.setFocusPainted(false);
								          					boss_gameover.add(insert);
						      								
								          					insert.addActionListener(new ActionListener() {
																
																@Override
																public void actionPerformed(ActionEvent e) {

																	PreparedStatement ps;
																	try {
																		ps = connection.prepareStatement("INSERT INTO test VALUES(?,?)");
																		ps.setString(1, textfield.getText().toString()); //0이 아니라 1부터 시작한다
																		ps.setInt(2, score);
																		
																		int res = ps.executeUpdate();
																		if(res ==1) {
																			System.out.println("성공");
																		}
																		ps.close();

																		
																	} catch (SQLException e1) {
																		// TODO Auto-generated catch block
																		e1.printStackTrace();
																	};
																	
																	
																	JFrame frame2;
																	JPanel panel2 =new JPanel();
																	JTextArea txtA = new JTextArea();
																	
																	
																	
																	panel2.setPreferredSize(new Dimension(1000, 600));
																frame2 = new JFrame();
																
																
																

																
																Statement s;
																
																try {
																	s = connection.createStatement();
																	ResultSet rs = s.executeQuery("select * from test");
																	

																	
																	while(rs.next()) {
																		
																		String v1 = rs.getString("value1")+"님 ";
																		txtA.append(v1);
																		
																		int v2 = rs.getInt("value2");
																		String v3 = String.valueOf(v2)+"점 \n";
																		
																		txtA.append(v3);
																		
																		//int v4 = rs.getInt("value4");
																		
																		System.out.println(v1);
																		System.out.println(v2);
																	
																		
																	}
																	
																} catch (SQLException e1) {
																	// TODO Auto-generated catch block
																	e1.printStackTrace();
																}
																
																txtA.setEditable(false); 
																panel2.add(txtA);
																frame2.add(panel2);
																frame2.setBackground(Color.black);
																frame2.setSize(1000,600);
																frame2.setVisible(true);
																frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

																}
															});
								          					
								          					
								          					
						      								JButton gameover_img = new JButton(new ImageIcon("gamewin.jpg"));
						      								gameover_img.setBackground(Color.black);
						      								gameover_img.setBounds(130, 80, 735, 173);
						      								gameover_img.setBorderPainted(false);
						      								gameover_img.setFocusPainted(false);
								          					boss_gameover.add(gameover_img);
									      					
									      					frame.add(boss_gameover);
									      					frame.pack();
									      					frame.setVisible(true);
									      					
					      								}
					      							}
					      							
					      							else {
					      							boss_acttack_eat.setBounds(x2, y, 50, 50);
					      							}
					      							
					      							
					      							switch(enemy_life) {
							      					case 9: boss_heart1.setVisible(false); break;
							      					case 8: boss_heart2.setVisible(false); break;
							      					case 7: boss_heart3.setVisible(false); break;
							      					case 6: boss_heart4.setVisible(false); break;
							      					case 5: boss_heart5.setVisible(false); break;
							      					}
					      							
					      						}
					      					};
						      					attack_boss_timer.schedule(attack_boss_task, 0,100);
								
										}
		          					}

		          					Timer down_timer2 = new Timer();
			      					TimerTask down_task2 = new TimerTask() {
			      						@Override
			      						public void run() {
			      							boss_wall1.setVisible(true);
			      							boss_wall2.setVisible(true);
			      							boss_wall3.setVisible(true);
			      							boss_wall4.setVisible(true);
			      							boss_wall5.setVisible(true);
			      							boss_wall6.setVisible(true);
			      							boss_wall7.setVisible(true);
			      							
			      							empty_wall = (int)(Math.random()*7);
			      							
			      							switch(empty_wall) {
			      							case 0: boss_wall1.setVisible(false); break;
			      							case 1: boss_wall2.setVisible(false);break;
			      							case 2: boss_wall3.setVisible(false);break;
			      							case 3:boss_wall4.setVisible(false); break;
			      							case 4: boss_wall5.setVisible(false);break;
			      							case 5: boss_wall6.setVisible(false);break;
			      							case 6: boss_wall7.setVisible(false);break;
			      							}
			      							
			      							boss_Img.setBounds(x_boss, y_boss, 350, 350);
			      							boss_wall1.setBounds(x_boss+350, y_boss, 50, 50);
			      							boss_wall2.setBounds(x_boss+350, y_boss+50, 50, 50);
			      							boss_wall3.setBounds(x_boss+350, y_boss+100, 50, 50);
			      							boss_wall4.setBounds(x_boss+350, y_boss+150, 50, 50);
			      							boss_wall5.setBounds(x_boss+350, y_boss+200, 50, 50);
			      							boss_wall6.setBounds(x_boss+350, y_boss+250, 50, 50);
			      							boss_wall7.setBounds(x_boss+350, y_boss+300, 50, 50);
			      							
			      							if(x_boss==620) {
			      								System.out.println("잡힘");
			      								
			      								
			      								JPanel boss_gameover = new JPanel();
				          						scorecheck.setVisible(false);
			      								scorecheck.removeAll();
			      								
			      								boss_gameover.setLayout(null);
			      								
			      								JTextField textfield = new JTextField();
			      								textfield.setBounds(530,300,170,50);
			      								boss_gameover.add(textfield);
			      								

			      								boss_gameover.setPreferredSize(new Dimension(1000, 600));
			      								boss_gameover.setBackground(Color.black);
						      					
			      								JButton name = new JButton(new ImageIcon("name.png"));
			      								name.setBackground(Color.black);
			      								name.setBounds(380, 300, 150, 50);
			      								name.setBorderPainted(false);
			      								name.setFocusPainted(false);
					          					boss_gameover.add(name);
			      								
			      								JButton insert = new JButton(new ImageIcon("insert.png"));
			      								insert.setBackground(Color.black);
			      								insert.setBounds(400, 400, 244, 60);
			      								insert.setBorderPainted(false);
			      								insert.setFocusPainted(false);
					          					boss_gameover.add(insert);
			      								
					          					insert.addActionListener(new ActionListener() {
													
													@Override
													public void actionPerformed(ActionEvent e) {

														PreparedStatement ps;
														try {
															ps = connection.prepareStatement("INSERT INTO test VALUES(?,?)");
															ps.setString(1, textfield.getText().toString()); //0이 아니라 1부터 시작한다
															ps.setInt(2, score);
															
															int res = ps.executeUpdate();
															if(res ==1) {
																System.out.println("성공");
															}
															ps.close();

															
														} catch (SQLException e1) {
															// TODO Auto-generated catch block
															e1.printStackTrace();
														};
														
														
														JFrame frame2;
														JPanel panel2 =new JPanel();
														JTextArea txtA = new JTextArea();
														
														
														
														panel2.setPreferredSize(new Dimension(1000, 600));
													frame2 = new JFrame();
													
													
													

													
													Statement s;
													
													try {
														s = connection.createStatement();
														ResultSet rs = s.executeQuery("select * from test");
														

														
														while(rs.next()) {
															
															String v1 = rs.getString("value1")+"님 ";
															txtA.append(v1);
															
															int v2 = rs.getInt("value2");
															String v3 = String.valueOf(v2)+"점 \n";
															
															txtA.append(v3);
															
															//int v4 = rs.getInt("value4");
															
															System.out.println(v1);
															System.out.println(v2);
														
															
														}
														
													} catch (SQLException e1) {
														// TODO Auto-generated catch block
														e1.printStackTrace();
													}
													
													txtA.setEditable(false); 
													panel2.add(txtA);
													frame2.add(panel2);
													frame2.setBackground(Color.black);
													frame2.setSize(1000,600);
													frame2.setVisible(true);
													frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

													}
												});
					          					
					          					
					          					
			      								JButton gameover_img = new JButton(new ImageIcon("gameover.png"));
			      								gameover_img.setBackground(Color.black);
			      								gameover_img.setBounds(130, 80, 735, 173);
			      								gameover_img.setBorderPainted(false);
			      								gameover_img.setFocusPainted(false);
					          					boss_gameover.add(gameover_img);
						      					
						      					frame.add(boss_gameover);
						      					frame.pack();
						      					frame.setVisible(true);
			      							}
			      							x_boss+=10;
			      						}
			      					};
			      					down_timer2.schedule(down_task2, 0,1000);
			      					
			      					
			      					
			      					JButton attack = new JButton(new ImageIcon("eat.png"));
		          					
			      					class Key extends KeyAdapter{
			      						public void keyPressed(KeyEvent e) {
			      							
			      							switch(e.getKeyCode()) {
			      							case KeyEvent.VK_UP:
			      								
			      								pacman_timer_check=2;
						      							
						      							if(pacman_timer_check==1 || pacman_y<=100) {
						      								
						      							}
						      							else {
						      								pacman_y-=50;
						      								
						      							boss_pacman.setBounds(pacman_x, pacman_y, 50, 50);
						      							
						      							attack_boss a = new attack_boss(pacman_x,pacman_y);
						      							}

			      								
			      								break;
			      							case KeyEvent.VK_DOWN:
			      								pacman_timer_check=1;
						      							if(pacman_timer_check==2 ||pacman_y>=400) {

						      							}
						      							else {
						      								pacman_y+=50;
						      							boss_pacman.setBounds(pacman_x, pacman_y, 50, 50);
						      							
						      							attack_boss a = new attack_boss(pacman_x,pacman_y);
						      							}
			      							}
			      							
			      						};
			      						
			      					}
				      				
			      					Key keyclick2 = new Key();
						      		frame.addKeyListener(keyclick2);
			      					
			      					scorecheck.setPreferredSize(new Dimension(1000, 600));
			      					scorecheck.setBackground(Color.black);
			      					
			      					
			      					frame.add(scorecheck);
			      					frame.pack();
			      					frame.setVisible(true);
			      				}
			      			}
			      			
			      			
			      			
			      				
			      			@Override
			      			public void keyPressed(KeyEvent e) {
			      				int key = e.getKeyCode();
			      				
			      				if(boss_stage==0) {
			      				switch(key) {
			      				
			      				case KeyEvent.VK_UP:
			      					keycheck =1;
			      					if(waitup ==0) {
			      						
			      					Timer up_timer = new Timer();
			      					TimerTask up_task = new TimerTask() {
			      						@Override
			      						public void run() {
			      							
			      							if(keycheck !=1||(f[pacmanh-1][pacmanw].getIcon()).equals(wall)||pacmanh==1){
			      								waitup =0;
			      								up_timer.cancel();
			      							}
			      							else {
			      								waitup =1;
			      								
			      							if((f[pacmanh-1][pacmanw].getIcon()).equals(empty)||(f[pacmanh-1][pacmanw].getIcon()).equals(eat)) {
			      								
			      								if((f[pacmanh-1][pacmanw].getIcon()).equals(eat)) {
			      									score++;
			      									showscore(score);
			      									
			      								}
			      								f[pacmanh][pacmanw].setIcon(empty);
			      								pacmanh--;
			      								f[pacmanh][pacmanw].setIcon(pacmanup);
			      						}
			      							else if((f[pacmanh-1][pacmanw].getIcon()).equals(cherry)) {
			      								score+=100;
			      								showscore(score);
			      								
			      								f[pacmanh][pacmanw].setIcon(empty);
			      								pacmanh--;
			      								f[pacmanh][pacmanw].setIcon(pacmanup);
			      								
			      							}
			      							else {
			      								waitup =0;
			      							up_timer.cancel();
			      							
			      						}
			      							keycheck =1;
			      							}
			      							
			      							score_check(score);
			      						}
			      					};
			      					System.out.println(difficulty);
			      					up_timer.schedule(up_task, 0,200*difficulty2);
			      					
			      					}
			      				
			      					break;
			      					
			      				case KeyEvent.VK_DOWN:
			      					keycheck=2;
			      					if(waitdown ==0) {
			      					
			      					Timer down_timer = new Timer();
			      					TimerTask down_task = new TimerTask() {
			      						@Override
			      						public void run() {
			      							
			      							if(keycheck !=2||(f[pacmanh+1][pacmanw].getIcon()).equals(wall)){
			      								waitdown=0;
			      								down_timer.cancel();
			      							}
			      							
			      							else {
			      								
			      							if((f[pacmanh+1][pacmanw].getIcon()).equals(empty)||(f[pacmanh+1][pacmanw].getIcon()).equals(eat)) {
			      								waitdown=1;
			      								if((f[pacmanh+1][pacmanw].getIcon()).equals(eat)) {
			      									score++;
			      									showscore(score);
			      									score_check(score);
			      								}
			      								f[pacmanh][pacmanw].setIcon(empty);
			      								pacmanh++;
			      								f[pacmanh][pacmanw].setIcon(pacmandown);
			      						}
			      							else if((f[pacmanh+1][pacmanw].getIcon()).equals(cherry)) {
			      								score+=100;
			      								showscore(score);
			      								
			      								f[pacmanh][pacmanw].setIcon(empty);
			      								pacmanh++;
			      								f[pacmanh][pacmanw].setIcon(pacmanup);
			      								
			      							}
			      							else {
			      								waitdown=0;
			      								down_timer.cancel();
			      						}
			      							keycheck =2;
			      							}
			      						}
			      					};
			      					down_timer.schedule(down_task, 0,200*difficulty2);
			      					}
			      					break;
			      				case KeyEvent.VK_LEFT:
			      					keycheck =3;
			      					if(waitleft ==0) {
			      					
			      					Timer left_timer = new Timer();
			      					TimerTask left_task = new TimerTask() {
			      						@Override
			      						public void run() {
			      							if(keycheck !=3||(f[pacmanh][pacmanw-1].getIcon()).equals(wall)){
			      								waitleft=0;
			      								left_timer.cancel();
			      							}
			      							else {
			      								
			      							if((f[pacmanh][pacmanw-1].getIcon()).equals(empty)||(f[pacmanh][pacmanw-1].getIcon()).equals(eat)) {
			      								waitleft=1;
			      								if((f[pacmanh][pacmanw-1].getIcon()).equals(eat)) {
			      									score++;
			      									showscore(score);score_check(score);
			      								}
			      								f[pacmanh][pacmanw].setIcon(empty);
			      								pacmanw--;
			      								f[pacmanh][pacmanw].setIcon(pacmanleft);
			      						}
			      							
			      							else if((f[pacmanh][pacmanw-1].getIcon()).equals(cherry)) {
			      								score+=100;
			      								showscore(score);
			      								
			      								f[pacmanh][pacmanw].setIcon(empty);
			      								pacmanw--;
			      								f[pacmanh][pacmanw].setIcon(pacmanup);
			      								
			      							}
			      							else {
			      								waitleft=0;
			      								left_timer.cancel();
			      						}
			      							keycheck =3;
			      							}
			      						}
			      					};
			      					left_timer.schedule(left_task, 0,200*difficulty2);
			      					}
			      					break;
			      				case KeyEvent.VK_RIGHT:
			      					keycheck =4;
			      					if(waitright ==0) {
			      					
			      					Timer right_timer = new Timer();
			      					TimerTask right_task = new TimerTask() {
			      						@Override
			      						public void run() {
			      							if(keycheck !=4||(f[pacmanh][pacmanw+1].getIcon()).equals(wall)){
			      								waitright =0;
			      								right_timer.cancel();
			      							}
			      							else {
			      								
			      							if((f[pacmanh][pacmanw+1].getIcon()).equals(empty)||(f[pacmanh][pacmanw+1].getIcon()).equals(eat)) {
			      								waitright =1;
			      								if((f[pacmanh][pacmanw+1].getIcon()).equals(eat)) {
			      									score++;
			      									showscore(score);
			      									score_check(score);
			      								}
			      								f[pacmanh][pacmanw].setIcon(empty);
			      								pacmanw++;
			      								f[pacmanh][pacmanw].setIcon(pacmanright);
			      						}
			      							else if((f[pacmanh][pacmanw+1].getIcon()).equals(cherry)) {
			      								score+=100;
			      								showscore(score);
			      								
			      								f[pacmanh][pacmanw].setIcon(empty);
			      								pacmanw++;
			      								f[pacmanh][pacmanw].setIcon(pacmanup);
			      								
			      							}
			      							else {
			      								waitright =0;
			      								right_timer.cancel();
			      						}
			      							keycheck =4;
			      							}
			      						}
			      					};
			      					right_timer.schedule(right_task, 0,200*difficulty2);
			      					}
			      			}
			      		}
			      			}
			      		}
			      		
			      		Timer m_timer = new Timer();
			      		TimerTask m_task = new TimerTask() {
			      			
			      			@Override
			      			public void run() 
			      			{
			      				if(time ==0) {
			      					
			      					panel.setVisible(false);
			      					panel.removeAll();
			      					
			      					JPanel boss_gameover = new JPanel();
	          						
      								boss_gameover.setLayout(null);
      								
      								JTextField textfield = new JTextField();
      								textfield.setBounds(530,300,170,50);
      								boss_gameover.add(textfield);
      								

      								boss_gameover.setPreferredSize(new Dimension(1000, 600));
      								boss_gameover.setBackground(Color.black);
			      					
      								JButton name = new JButton(new ImageIcon("name.png"));
      								name.setBackground(Color.black);
      								name.setBounds(380, 300, 150, 50);
      								name.setBorderPainted(false);
      								name.setFocusPainted(false);
		          					boss_gameover.add(name);
      								
      								JButton insert = new JButton(new ImageIcon("insert.png"));
      								insert.setBackground(Color.black);
      								insert.setBounds(400, 400, 244, 60);
      								insert.setBorderPainted(false);
      								insert.setFocusPainted(false);
		          					boss_gameover.add(insert);
      								
		          					insert.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e) {

											PreparedStatement ps;
											try {
												ps = connection.prepareStatement("INSERT INTO test VALUES(?,?)");
												ps.setString(1, textfield.getText().toString()); //0이 아니라 1부터 시작한다
												ps.setInt(2, score);
												
												int res = ps.executeUpdate();
												if(res ==1) {
													System.out.println("성공");
												}
												ps.close();

												
											} catch (SQLException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											};
											
											
											JFrame frame2;
											JPanel panel2 =new JPanel();
											JTextArea txtA = new JTextArea();
											
											
											
											panel2.setPreferredSize(new Dimension(1000, 600));
										frame2 = new JFrame();
										
										
										

										
										Statement s;
										
										try {
											s = connection.createStatement();
											ResultSet rs = s.executeQuery("select * from test");
											

											
											while(rs.next()) {
												
												String v1 = rs.getString("value1")+"님 ";
												txtA.append(v1);
												
												int v2 = rs.getInt("value2");
												String v3 = String.valueOf(v2)+"점 \n";
												
												txtA.append(v3);
												
												//int v4 = rs.getInt("value4");
												
												System.out.println(v1);
												System.out.println(v2);
											
												
											}
											
										} catch (SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										
										txtA.setEditable(false); 
										panel2.add(txtA);
										frame2.add(panel2);
										frame2.setBackground(Color.black);
										frame2.setSize(1000,600);
										frame2.setVisible(true);
										frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

										}
									});
		          					
		          					
		          					
      								JButton gameover_img = new JButton(new ImageIcon("timeover.png"));
      								gameover_img.setBackground(Color.black);
      								gameover_img.setBounds(130, 80, 735, 173);
      								gameover_img.setBorderPainted(false);
      								gameover_img.setFocusPainted(false);
		          					boss_gameover.add(gameover_img);
			      					
			      					frame.add(boss_gameover);
			      					frame.pack();
			      					frame.setVisible(true);
			      					
			      					
			      					
			      					
			      				}
			      				int su = 0,icheck=0;
			      				
			      			
			      				icheck = (time>=10)?0:1;
			      				if(icheck ==1)
			      					f[0][19].setIcon(empty);
			      				
			      					for(int i=icheck;i<2;i++) {
			      						if(i==0) 
			      							su = time/10;
			      						else 
			      							su = time%10;
			      						
			      					switch(su) {
			      					case 0:
			      						f[0][19+i].setIcon(zero); break;
			      					case 1:
			      						f[0][19+i].setIcon(one); break;
			      					case 2:
			      						f[0][19+i].setIcon(two); break;
			      					case 3:
			      						f[0][19+i].setIcon(three); break;
			      					case 4:
			      						f[0][19+i].setIcon(four); break;
			      					case 5:
			      						f[0][19+i].setIcon(five); break;
			      					case 6:
			      						f[0][19+i].setIcon(six); break;
			      					case 7:
			      						f[0][19+i].setIcon(seven); break;
			      					case 8:
			      						f[0][19+i].setIcon(eight); break;
			      					case 9:
			      						f[0][19+i].setIcon(nine); break;
			      					
			      					}
			      					}
			      				time--;
			      				time2++;
			      			}
			      		};

			      		m_timer.schedule(m_task, 0,1000);
			      		
			      		KeyClick keyclick = new KeyClick();
			      		frame.addKeyListener(keyclick);

			      		frame.add(panel);

			      		frame.pack();
			      		frame.setVisible(true);
					
				}
			});
              
              frame.add(level);
              
            }
        });
              
              
        btnNewButton.setBackground(Color.black);
        btnNewButton.setBorderPainted(false);
       btnNewButton.setFocusPainted(false);
       btnNewButton.setBounds(410, 400, 166, 40);
       
        start.add(btnNewButton);
        
		frame.add(start);
		frame.setTitle(" 팩맨 게임");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Toolkit toolkit = frame.getToolkit();
		Image image = toolkit.createImage("pacmanright.png");
		frame.setIconImage(image);
		

		frame.pack();
		frame.setVisible(true);
	}
}