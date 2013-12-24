import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;


//****	���l�ѹC��	****//
public class FiveChess{
	public static void main(String[] args){
		startGame();
	}
	
	public static void startGame(){
		Frame frame = new Frame("Five");	//�إ߷s������frame title��Five
		frame.setSize(750, 650);	//�]�m�������j�p
		
		ControlPanelLeft leftControl = new ControlPanelLeft();	//�s�W ���䪺������
    	ControlPanelRight rightControl = new ControlPanelRight();	//�s�W �k�䪺������
		ChessBoard board = new ChessBoard();	//�s�W�ѽL
		
		board.addMouseListener(board.new MouseClicked());	//board�[�JsetChess()��Ū���ƹ�����
		frame.add(leftControl, BorderLayout.WEST);	//���䪺������ �[�줶������
		frame.add(board, BorderLayout.CENTER);	//�ѽL �[�줶������
		frame.add(rightControl, BorderLayout.EAST);	//�k�䪺������ �[�줶���k��
		
		frame.setVisible(true);	//���farme����
		frame.addWindowListener(new AdapterDemo());	//���k�W���e�e������frame
	}
}

//****	setChess	****//


//****	�ѽL	****//
class ChessBoard extends JComponent implements ActionListener {
	public static final int MARGIN=30;
	public static final int GRID_SPAN=35;
	public static final int ROWS=15;
	public static final int COLS=15;
	private int[][] chess = new int[ROWS+1][COLS+1];
	private static int player = 1;
	int X;
	int Y;
	Frame hasChess = new Frame("");
	Frame win = new Frame("Win");
	Button bb = new Button("�T�w");
	Button ww = new Button("�T�w");
	public ChessBoard(){
		for(int i = 0; i<ROWS; i++)
			for(int j = 0; j<COLS; j++)
					chess[i][j] = 0;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		for(int i=0;i<=ROWS;i++){  
           g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);  
        }  
        for(int i=0;i<=COLS;i++){  
            g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);  
        }  
	}
	public void paintComponent(Graphics g){
		for(int i = 0; i<ROWS; i++){
			for(int j = 0; j<COLS; j++){
				if(chess[i][j] == 1){
					g.setColor(Color.pink);
					g.fillOval(i*GRID_SPAN+20,j*GRID_SPAN+20, 20, 20);
				}else if(chess[i][j] == 2){
					g.setColor(Color.BLACK);
					g.fillOval(i*GRID_SPAN+20,j*GRID_SPAN+20, 20, 20);
				}
			}
		}
	}
	public void setChess(int x, int y){
		if(chess[x][y] > 0){
			hasChess.setSize(200, 100);
			hasChess.setLocation(300, 300);
			JTextArea text = new JTextArea("�����P�@�Ӧa��!!");
			bb.addActionListener(this);
			hasChess.add(text, BorderLayout.NORTH);
			hasChess.add(bb, BorderLayout.SOUTH);
			hasChess.setVisible(true);
		}else if(chess[x][y] == 0){
			System.out.println(x +" "+ y);
			if(x > ROWS || y > COLS) return;
			chess[x][y] = player;
			Graphics g = getGraphics();
			update(g);
			if(checkWin()){
				win.setSize(200, 100);
				win.setLocation(300, 300);
				JTextArea text = new JTextArea("Winner is "+ player);
				ww.addActionListener(this);
				win.add(text, BorderLayout.NORTH);
				win.add(ww, BorderLayout.SOUTH);
				win.setVisible(true);
			}else{
				if(player == 1){				
					player = 2;
				}else if(player == 2){
					player = 1;
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		if(button == bb){
			hasChess.setVisible(false);
		}else if(button == ww){
			System.exit(0);
		}
	}

	class MouseClicked extends MouseAdapter{
		public MouseClicked(){}
			
		public void mousePressed(MouseEvent e){
			/*
		    System.out.print("screen x: " + e.getXOnScreen());
		    System.out.print(", y: " + e.getYOnScreen() + "\n");
		    */
		    if (e.getButton() == MouseEvent.BUTTON1){
		        //System.out.println("left button");
		        X = (e.getX()-MARGIN/2)/35;
		        Y = (e.getY()-MARGIN/2)/35;
		        setChess(X, Y);
		    }
		    if (e.getButton() == MouseEvent.BUTTON2){
		        System.out.println("middle button");
		    }
		    if (e.getButton() == MouseEvent.BUTTON3){
		        System.out.println("right button");
		    }
		    /*
		    System.out.println("mouse position: " + e.getPoint());
		    System.out.println("mouse screen position: " + e.getLocationOnScreen());
		    System.out.println("mouse clicks: " + e.getClickCount());
		    */
	    }
	}
	public boolean checkWin(){
		int win;
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				if(chess[i][j] > 0){
					switch(chess[i][j]){
						case 1:
							win = 0;
							for(int k = i; k<ROWS;k++){
								if(win == 5) return true;
								if(chess[k][j]==1){
									win++;
									continue;
								}else break;
							}
							win = 0;
							for(int l = j; j<COLS;l++){
								if(win == 5) return true;
								if(chess[i][l]==1){
									win++;
									continue;
								}else break;
							}
							win = 0;
							for(int k=i,l=j;k<ROWS&&j<COLS;k++,l++){
//System.out.println(k+" "+l+" "+win);
								if(win == 5) return true;
								if(chess[k][l]==1){
									win++;
									continue;
								}else break;
							}
							break;
						case 2:
							win = 0;
							for(int k = i;k<ROWS;k++){
								if(win == 5) return true;
								if(chess[k][j]==2){
									win++;
									continue;
								}else break;
							}
							win = 0;
							for(int l = j;j<COLS;l++){
								if(win == 5) return true;
								if(chess[i][l]==2){
									win++;
									continue;
								}else break;
							}
							win = 0;
							for(int k=i,l=j;k<ROWS&&j<COLS;k++,l++){
								if(win == 5) return true;
								if(chess[k][l]==2){
									win++;
									continue;
								}else break;
							}
							break;
					}
				}
			}
		}
		return false;
	}
}

//****	�k������	****//
class ControlPanelRight extends Panel implements ActionListener {   
    CheckboxGroup cbg = new CheckboxGroup();  
    Button b0 = new Button("�]  �m");
    Button b1 = new Button("��  ��"); 
    Button b2 = new Button("�}  �l");   
    Button b3 = new Button("��  �s");   
    Button b4 = new Button("��  �U");   
    Button b5 = new Button("��  �}"); 
    
    public ControlPanelRight(){
    	add(b0);
        setLayout(new GridLayout(14,1,10,5));   
        add(new Label());   
        add(new Label());   
        add(new Label());  
        add(b1);
        add(new Label());   
        add(new Label());   
        add(new Label());   
        add(b2);   
        add(b3);   
        add(b4);   
        add(b5);
        b0.addActionListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        setBounds(0,0,500,500);   
    }
    
	public void actionPerformed(ActionEvent e) {	//Exit���X�C��
		Button button = (Button) e.getSource();
		if(button == b0){
			
		}else if(button == b1){
			
		}else if(button == b2){
			
		}else if(button == b3){
			
		}else if(button == b4){
			new Help();
		}else if(button == b5){
			System.exit(0);
		}
	}
	
	class Help implements ActionListener{
		Frame frame = new Frame("��  �U");
		Button bb = new Button("�T�w");
		JTextArea text = new JTextArea(
				" ��H�﫳�A���U��̫��¤l�A��U��̫��դl�C\n"
				+ " �H���y�覡�N�Ѥl�m��ѽL�I�W�A���N���ӴѤl�s���@�u���ӡC\n"
				+ " ��ѽL�I���U���Ѥl�A�h���M�ѡC");
		public Help(){
			frame.setSize(400, 150);
			frame.setLocation(300, 300);
			bb.addActionListener(this);
			frame.add(text, BorderLayout.NORTH);
			frame.add(bb, BorderLayout.SOUTH);
			frame.setVisible(true);
		}
		public void actionPerformed(ActionEvent e){
			Button button = (Button) e.getSource();
			if(button == bb){
				frame.setVisible(false);
			}
		}
	}
}

//****	��������	****//
class ControlPanelLeft extends Panel implements ActionListener {   
    CheckboxGroup cbg = new CheckboxGroup();   
    int tm_unit=200, tm_sum =0;
    int sec=0,tm_game =40;
    Checkbox cb1 = new Checkbox("Black first",cbg,true);   
    Checkbox cb2 = new Checkbox("White first",cbg,false);   
    JLabel steps = new JLabel("�B�� = ");
    JLabel time = new JLabel("�ɶ� = ");
    Timer timer = new Timer(tm_unit, this);
    
    public ControlPanelLeft(){   
    	timer.restart();
        setLayout(new GridLayout(14,1,10,5));   
		add(cb1);   
        add(cb2);   
        add(new Label());   
        add(new Label());   
        add(new Label()); 
        add(new Label());   
        add(new Label());
        add(new Label());   
        add(new Label());
        add(steps);
        add(time);
    }   
    public void timer_event(){
		if ((tm_sum += tm_unit) >= 1000){
			tm_sum -= 1000;
			sec+=1;
			time.setText("�ɶ�= " + sec +"s");
		}
    }
	public void actionPerformed(ActionEvent e){
		timer_event();
	}
}

//****	�e�e��������		****//
class AdapterDemo extends WindowAdapter {
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }
}
