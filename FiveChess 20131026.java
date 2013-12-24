import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;

//****	���l�ѹC��	****//
public class FiveChess{
	private static Frame frame;
	private static ChessBoard board;
	private static int length = 690;
	private static int width = 600;

	public static void main(String[] args){
		startGame();	//�}�l�C��
	}
	
	//****	�C���e��	****//
	public static void startGame(){
		Panel panelL = new Panel();
		Panel panelM = new Panel();
	    Panel panelR = new Panel();
	    frame = new Frame("Five");	//�إ߷s������frame title��Five
		frame.setSize(length, width);	//�]�m�������j�p
		frame.setResizable(false);	//�T�w�����j�p
		frame.setLocation(10, 10);	//�]�m�����X�{��m
		board = new ChessBoard();	//�s�W�ѽL
		ChessBoard.ControlPanelLeft leftControl = board.new ControlPanelLeft();	//�s�W ���䪺������
    	ControlPanelRight rightControl = new ControlPanelRight();	//�s�W �k�䪺������
    	
		panelL.add(leftControl, BorderLayout.WEST);
		panelM.add(board, BorderLayout.CENTER);
		panelR.add(rightControl, BorderLayout.EAST);
		board.addMouseListener(board.new MouseClicked());	//board�[�JsetChess()��Ū���ƹ�����
		frame.add(panelL, BorderLayout.WEST);	//���䪺������ �[�줶������
		frame.add(board, BorderLayout.CENTER);	//�ѽL �[�줶������
		frame.add(panelR, BorderLayout.EAST);	//�k�䪺������ �[�줶���k��
		
		frame.setVisible(true);	//���farme����
		frame.addWindowListener(new AdapterDemo());	//���k�W���e�e������frame
	}
	
	public static void setLW(int l, int w){
		length = l;
		width = w;
	}
	
	//****	�k������	****//
	static class ControlPanelRight extends Panel implements ActionListener {
		Button b0 = new Button("�]  �m");
	    Button b1 = new Button("��  ��"); 
	    Button b2 = new Button("��  �s");   
	    Button b3 = new Button("��  �U");   
	    Button b4 = new Button("��  �}"); 
	    
	    public ControlPanelRight(){
	    	setLayout(new GridLayout(14,1,10,5));
	        add(new Label("           "));
	        add(new Label());
	    	add(b0);
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
	        b0.addActionListener(this);
	        b1.addActionListener(this);
	        b2.addActionListener(this);
	        b3.addActionListener(this);
	        b4.addActionListener(this);
	        setBounds(0,0,500,500);   
	    }
	    
		public void actionPerformed(ActionEvent e){
			Button button = (Button) e.getSource();
			if(button == b0){
				new Set();
			}else if(button == b1){
				if(board.gameEnd == true)
					board.hasChess();
				else
					board.regret();
			}else if(button == b2){
				new Renew();
			}else if(button == b3){
				new Help();
			}else if(button == b4){
				System.exit(0);
			}
		}
		
		class Set implements ActionListener {
			Panel p0 = new Panel();
			Panel p1 = new Panel();
			Panel p2 = new Panel();
			Panel p3 = new Panel();
			Frame frameSet = new Frame("�]  �m");
			Button b0 = new Button("15 x 15");
			Button b1 = new Button("17 x 17");
			Button b2 = new Button("19 x 19");
			JTextArea text = new JTextArea("�ѽL�j�p");
			public Set(){
				frameSet.setSize(200, 100);
				frameSet.setResizable(false);
				frameSet.setLocation(300, 300);
				p0.add(b0);
				p1.add(b1);
				p2.add(b2);
				p3.add(text, BorderLayout.CENTER);
				b0.addActionListener(this);
				b1.addActionListener(this);
				b2.addActionListener(this);
				frameSet.add(p3, BorderLayout.NORTH);
				frameSet.add(p0, BorderLayout.WEST);
				frameSet.add(p1);
				frameSet.add(p2, BorderLayout.EAST);
				frameSet.setVisible(true);
				frameSet.addWindowListener(new AdapterDemo());
			}
			public void actionPerformed(ActionEvent e){
				Button button = (Button) e.getSource();
				if(button == b0){
					setLW(690, 600);
					board.setBoard(14, 14);
				}else if(button == b1){
					setLW(760, 650);
					board.setBoard(16, 16);
				}else if(button == b2){
					setLW(830, 750);
					board.setBoard(18, 18);
				}
				frame.dispose();
				frameSet.dispose();
				startGame();
			}
		}
		class Renew implements ActionListener{
			Panel p0 = new Panel();
			Panel p1 = new Panel();
			Panel p3 = new Panel();
			Frame frameRenew = new Frame("��  �s");
			Button b0 = new Button("��  ��");
			Button b1 = new Button("�T  �w");
			JTextArea text = new JTextArea("�T�w�n���s�}�l?");
			public Renew(){
				frameRenew.setSize(200, 100);
				frameRenew.setResizable(false);
				frameRenew.setLocation(300, 300);
				p0.add(b0);
				p1.add(b1);
				p3.add(text, BorderLayout.CENTER);
				b0.addActionListener(this);
				b1.addActionListener(this);
				frameRenew.add(p3, BorderLayout.NORTH);
				frameRenew.add(p0, BorderLayout.WEST);
				frameRenew.add(p1, BorderLayout.EAST);
				frameRenew.setVisible(true);
				frameRenew.addWindowListener(new AdapterDemo());
			}
			public void actionPerformed(ActionEvent e){
				Button button = (Button) e.getSource();
				if(button == b0){
					frameRenew.dispose();
				}else if(button == b1){
					frameRenew.dispose();
					frame.dispose();
					startGame();
				}
			}
		}
		//****	���U�e��	****//
		class Help implements ActionListener {
			Frame frameHelp = new Frame("��  �U");
			Button bb = new Button("�T�w");
			JTextArea text = new JTextArea(
					" ��H�﫳�A���U��̫��¤l�A��U��̫����l�C\n"
					+ " �H���y�覡�N�Ѥl�m��ѽL�I�W�A���N���ӴѤl�s���@�u���ӡC\n"
					+ " ��ѽL�I���U���Ѥl�B�LĹ�a�A�h���M�ѡC");
			public Help(){
				frameHelp.setSize(400, 150);
				frameHelp.setResizable(false);
				frameHelp.setLocation(300, 300);
				bb.addActionListener(this);
				frameHelp.add(text, BorderLayout.NORTH);
				frameHelp.add(bb, BorderLayout.SOUTH);
				frameHelp.setVisible(true);
				frameHelp.addWindowListener(new AdapterDemo());
			}
			public void actionPerformed(ActionEvent e){
				Button button = (Button) e.getSource();
				if(button == bb){
					frameHelp.dispose();
				}
			}
		}
	}
}

//****	�ѽL	****//
class ChessBoard extends JComponent implements ActionListener {
	private static final int MARGIN=30;
	private static final int GRID_SPAN=35;
	private static int ROWS=14;
	private static int COLS=14;
	private static int[][] chess;
	private static int[][] chessRegret;
	private static int player = 1;
	private int XX,YY,AA;
	private int X;
	private int Y;
	private int count;
	boolean gameEnd;
	private Frame hasChess = new Frame("");
	private Frame win = new Frame("Win");
	private Button bb = new Button("�T�w");
	private Button ww = new Button("�T�w");
	
	public ChessBoard(){
		chess = new int[ROWS+1][COLS+1];
		chessRegret = new int[ROWS+1][COLS+1];
		reset();
	}
	
	//****	�]�m�ѽL	****//
	public void setBoard(int rows, int cols){
		ROWS = rows;
		COLS = cols;
	}
	
	//****	�]�m�Ѥl	****//
	public void reset(){
		gameEnd = false;
		count = 0;
		for(int i = 0; i<=ROWS; i++){
			for(int j = 0; j<=COLS; j++){
				chess[i][j] = 0;
				chessRegret[i][j] = -1;
			}
		}
	}
	
	public void paint(Graphics g){
		super.paint(g);
		//****	���Ѯɥզ�n�л\	****//
		for(int i = 0; i<=ROWS; i++){
			for(int j = 0; j<=COLS; j++){
				if(chess[i][j] == 0){
					g.setColor(Color.WHITE);
					g.fillOval(i*GRID_SPAN+20,j*GRID_SPAN+20, 20, 20);
				}
			}
		}
		//****	�e�X�ѽL	****//
		g.setColor(Color.BLACK);
		for(int i=0;i<=ROWS;i++){  
	        g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);  
	    }  
	    for(int i=0;i<=COLS;i++){  
	        g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);  
	    }
		//****	�Ѥl�C��P��m	****//
	    for(int i = 0; i<=ROWS; i++){
			for(int j = 0; j<=COLS; j++){
				if(chess[i][j] == 1){
					g.setColor(Color.BLACK);
					g.fillOval(i*GRID_SPAN+20,j*GRID_SPAN+20, 20, 20);
				}else if(chess[i][j] == 2){
					g.setColor(Color.ORANGE);
					g.fillOval(i*GRID_SPAN+20,j*GRID_SPAN+20, 20, 20);
				}
			}
		}
	    //****	���Ĺ	****//
	    if(gameEnd == true){
			g.setColor(Color.RED);
			if(AA == 1){
				g.drawLine(MARGIN+XX*GRID_SPAN, MARGIN+YY*GRID_SPAN, MARGIN+(XX+4)*GRID_SPAN, MARGIN+YY*GRID_SPAN);
			}else if(AA == 2){
				g.drawLine(MARGIN+XX*GRID_SPAN, MARGIN+YY*GRID_SPAN, MARGIN+XX*GRID_SPAN, MARGIN+(YY+4)*GRID_SPAN);
			}else if(AA == 3){
				g.drawLine(MARGIN+XX*GRID_SPAN, MARGIN+YY*GRID_SPAN, MARGIN+(XX+4)*GRID_SPAN, MARGIN+(YY+4)*GRID_SPAN);
			}else if(AA == 4){
				g.drawLine(MARGIN+XX*GRID_SPAN, MARGIN+YY*GRID_SPAN, MARGIN+(XX+4)*GRID_SPAN, MARGIN+(YY-4)*GRID_SPAN);
			}
		}
	}

	//****	�ˬd�O�_�H��Ѥl	****//
	public void check(int x, int y){
		if(gameEnd) return;
		if(chess[x][y] > 0){
			hasChess();
		}else if(chess[x][y] == 0){
			setChess(x, y);
		}
	}
	
	//****	��m�Ѥl	****//
	public void setChess(int x, int y){
		System.out.println(x +" "+ y);
		if(x > ROWS || y > COLS) return;
		chess[x][y] = player;
		chessRegret[x][y] = count;
		count++;
		Graphics g = getGraphics();
		update(g);	//��s�e��
		if(checkWin()){
			win();
		}else{
			if(player == 1){				
				player = 2;
			}else if(player == 2){
				player = 1;
			}
		}
		if(count == (ROWS+1)*(COLS+1) && AA == 0){
			win();
		}
	}
	//****	����	****//
	public void regret() {
		for(int i = 0; i <= ROWS; i++){
			for(int j = 0; j <= COLS; j++){
				if(chessRegret[i][j] == (count-1)){
					chess[i][j] = 0;
					chessRegret[i][j] = -1;
					count--;
					Graphics g = getGraphics();
					update(g);
					if(player == 1){				
						player = 2;
					}else if(player == 2){
						player = 1;
					}
					return;
				}
			}
		}
	}
	//****	���Ѥl�ɷ|�]�X�Ӫ��e��	****//
	JTextArea textHasChess = new JTextArea("");
	public void hasChess(){
		hasChess.setSize(200, 100);
		hasChess.setResizable(false);
		hasChess.setLocation(300, 300);
		if(gameEnd == true){
			textHasChess.setText("�C�������F!!");
		}else{
			textHasChess.setText("�����P�@�Ӧa��!!");
		}
		bb.addActionListener(this);
		hasChess.add(textHasChess, BorderLayout.NORTH);
		hasChess.add(bb, BorderLayout.SOUTH);
		hasChess.setVisible(true);
		hasChess.addWindowListener(new AdapterDemo());
	}
	//****	���HĹ�|�]�X�Ӫ��e��	****//
	JTextArea textWin = new JTextArea("");
	public void win(){
		Panel p = new Panel();
		gameEnd = true;
		Graphics g = getGraphics();
		update(g);
		win.setSize(200, 100);
		win.setResizable(false);
		win.setLocation(300, 300);
		if(count == (ROWS+1)*(COLS+1) && AA == 0){
			textWin = new JTextArea("!! �M      �� !!");
		}else{
			textWin = new JTextArea("Winner is "+ player);
		}
		p.add(textWin, BorderLayout.CENTER);
		ww.addActionListener(this);
		win.add(p, BorderLayout.NORTH);
		win.add(ww, BorderLayout.SOUTH);
		win.setVisible(true);
		win.addWindowListener(new AdapterDemo());
	}
	//**** �������s	****//
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		if(button == bb){
			hasChess.dispose();
		}else if(button == ww){
			win.dispose();
		}
	}
	//**** �P�_Ĺ	****//
	public boolean checkWin(){
		for(int i = 0; i <= ROWS; i++){
			XX = i;
			for(int j = 0; j <= COLS; j++){
				YY = j;
				if(chess[i][j] > 0){
					switch(chess[i][j]){
						case 1:
							if(chechWin(i,j, 1))
								return true;
							break;
						case 2:
							if(chechWin(i,j, 2))
								return true;
							break;
					}
				}
			}
		}
		return false;
	}
	private boolean chechWin(int i, int j, int player){
		int winCount;
		winCount = 0;
		AA = 1;
		for(int k = i; k<=ROWS;k++){
			if(chess[k][j]==player){
				winCount++;
				if(winCount == 5)
					return true;
			}else break;
		}
		winCount = 0;
		AA = 2;
		for(int l = j; l<=COLS;l++){
			if(chess[i][l]==player){
				winCount++;
				if(winCount == 5)
					return true;
			}else break;
		}
		winCount = 0;
		AA = 3;
		for(int k=i,l=j;k<=ROWS&&l<=COLS;k++,l++){
			if(chess[k][l]==player){
				winCount++;
				if(winCount == 5) 
					return true;
			}else break;
		}
		winCount = 0;
		AA = 4;
		for(int k=i,l=j;k<=ROWS&&l>=0;k++,l--){
			if(chess[k][l]==player){
				winCount++;
				if(winCount == 5) 
					return true;
			}else break;
		}
		AA = 0;
		return false;
	}
	
	//****	�����ƹ��I��	****//
	class MouseClicked extends MouseAdapter {
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
		        check(X, Y);	//�ˬd
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
	
	//****	��������	****//
	class ControlPanelLeft extends Panel implements ActionListener {  
	    int tm_unit=200;
	    int tm_sum =0;
	    int sec=0;
	    private JLabel play = new JLabel("    ���a1");
	    private JLabel steps = new JLabel("  �U�l�� = ");
	    private JLabel time = new JLabel("  �ɶ� = ");
	    private Timer timer = new Timer(tm_unit, this);
	    
	    public ControlPanelLeft(){   
	    	timer.restart();
	        setLayout(new GridLayout(14,1,10,20));
	        add(new Label("                      ")); 
	        add(play);  
	        add(new Label());
	        add(new Label());
	        add(new Label());
	        add(new Label());
	        add(new Label());
	        add(new Label());
	        add(steps);
	        add(time);
	    }
	    public void player_event(){
	    	play.setText("    ���a"+ player);
	    }
	    public void steps_event(){
	    	if(player == 1)
	    		steps.setText("  �¤l�� = "+ count/2);
	    	else
	    		steps.setText("  ���l�� = "+ count/2);
	    }
	    public void timer_event(){
			if ((tm_sum += tm_unit) >= 1000 && gameEnd == false){
				tm_sum -= 1000;
				sec+=1;
				time.setText("  �ɶ�= " + sec +"s");
			}
	    }
		public void actionPerformed(ActionEvent e){
			player_event();
			timer_event();
			steps_event();
		}
	}
}

//****	�e�e��������		****//
class AdapterDemo extends WindowAdapter {
    public void windowClosing(WindowEvent e){
    	e.getWindow().dispose();
    }
}
