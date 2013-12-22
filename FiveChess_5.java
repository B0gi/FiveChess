import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;


//****	五子棋遊戲	****//
public class FiveChess{
	static Frame frame;
	static ChessBoard board;
	static int length = 750;
	static int width = 650;
	public static void main(String[] args){
		startGame();	//開始遊戲
	}
	
	//****	遊戲畫面	****//
	public static void startGame(){
		Panel panelL = new Panel();
		Panel panelM = new Panel();
	    Panel panelR = new Panel();
	    frame = new Frame("Five");	//建立新的介面frame title為Five
		frame.setSize(length, width);	//設置介面的大小
		board = new ChessBoard();	//新增棋盤
		ChessBoard.ControlPanelLeft leftControl = board.new ControlPanelLeft();	//新增 左邊的控制鍵
    	ControlPanelRight rightControl = new ControlPanelRight();	//新增 右邊的控制鍵
		
		panelL.add(leftControl, BorderLayout.WEST);
		panelM.add(board, BorderLayout.CENTER);
		panelR.add(rightControl, BorderLayout.EAST);
		board.addMouseListener(board.new MouseClicked());	//board加入setChess()來讀取滑鼠按鍵
		frame.add(panelL, BorderLayout.WEST);	//左邊的控制鍵 加到介面左邊
		frame.add(board, BorderLayout.CENTER);	//棋盤 加到介面中央
		frame.add(panelR, BorderLayout.EAST);	//右邊的控制鍵 加到介面右邊
		
		frame.setVisible(true);	//顯示farme介面
		frame.addWindowListener(new AdapterDemo());	//按右上角叉叉能關閉frame
	}
	
	//****	右控制鍵	****//
	static class ControlPanelRight extends Panel implements ActionListener {
		Button b0 = new Button("設  置");
	    Button b1 = new Button("悔  棋"); 
	    Button b2 = new Button("重  新");   
	    Button b3 = new Button("幫  助");   
	    Button b4 = new Button("離  開"); 
	    
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
				
			}else if(button == b2){
				frame.dispose();
				startGame();
			}else if(button == b3){
				new Help();
			}else if(button == b4){
				System.exit(0);
			}
		}
		
		class Set implements ActionListener {
			Panel p0 = new Panel();
			Panel p1 = new Panel();
			Panel p3 = new Panel();
			Frame frameSet = new Frame("設  置");
			Button b0 = new Button("13 x 13");
			Button b1 = new Button("15 x 15");
			JTextArea text = new JTextArea("棋盤大小");
			public Set(){
				frameSet.setSize(200, 100);
				frameSet.setLocation(300, 300);
				p0.add(b0);
				p1.add(b1);
				p3.add(text, BorderLayout.CENTER);
				b0.addActionListener(this);
				b1.addActionListener(this);
				frameSet.add(p3, BorderLayout.NORTH);
				frameSet.add(p0, BorderLayout.WEST);
				frameSet.add(p1, BorderLayout.EAST);
				frameSet.setVisible(true);
				frameSet.addWindowListener(new AdapterDemo());
			}
			public void actionPerformed(ActionEvent e){
				Button button = (Button) e.getSource();
				if(button == b0){
					length = 660;
					width = 550;
					board = new ChessBoard(13, 13);
				}else if(button == b1){
					length = 750;
					width = 650;
					board = new ChessBoard(15, 15);
				}
				frame.dispose();
				frameSet.dispose();
				startGame();
			}
		}
		
		//****	幫助畫面	****//
		class Help implements ActionListener {
			Frame frameHelp = new Frame("幫  助");
			Button bb = new Button("確定");
			JTextArea text = new JTextArea(
					" 兩人對奕，先下手者持黑子，後下手者持白子。\n"
					+ " 以輪流方式將棋子置於棋盤點上，先將五個棋子連成一線為勝。\n"
					+ " 當棋盤點都下滿棋子，則為和棋。");
			public Help(){
				frameHelp.setSize(400, 150);
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

//****	棋盤	****//
class ChessBoard extends JComponent implements ActionListener {
	private static final int MARGIN=30;
	private static final int GRID_SPAN=35;
	private static int ROWS=15;
	private static int COLS=15;
	private static int[][] chess = new int[ROWS+1][COLS+1];
	private static int player = 1;
	private int XX,YY,AA;
	private int X;
	private int Y;
	private int count;
	
	private Frame hasChess = new Frame("");
	private Frame win = new Frame("Win");
	private Button bb = new Button("確定");
	private Button ww = new Button("確定");
	
	public ChessBoard(){
		setBoard(0);
	}
	public ChessBoard(int rows, int cols){
		ROWS = rows;
		COLS = cols;
		setBoard(0);
	}
	
	//****	設置棋子	****//
	public void setBoard(int num){
		for(int i = 0; i<=ROWS; i++)
			for(int j = 0; j<=COLS; j++)
					chess[i][j] = num;
		count = 0;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		//****	畫出棋盤	****//
		g.setColor(Color.BLACK);
		for(int i=0;i<=ROWS;i++){  
	        g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);  
	    }  
	    for(int i=0;i<=COLS;i++){  
	        g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);  
	    }
		//****	棋子顏色與放置	****//
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
	    //****	顯示贏	****//
	    if(chess[0][0] == 3){
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

	//****	檢查是否以放棋子	****//
	public void check(int x, int y){
		if(chess[x][y] > 0){
			hasChess();
		}else if(chess[x][y] == 0){
			setChess(x, y);
		}
	}
	
	//****	放置棋子	****//
	public void setChess(int x, int y){
		System.out.println(x +" "+ y);
		if(x > ROWS || y > COLS) return;
		chess[x][y] = player;
		count++;
		Graphics g = getGraphics();
		update(g);	//更新化面
		if(checkWin()){
			win();
		}else{
			if(player == 1){				
				player = 2;
			}else if(player == 2){
				player = 1;
			}
		}
	}
	
	//****	有棋子時會跑出來的畫面	****//
	JTextArea text = new JTextArea("");
	public void hasChess(){
		hasChess.setSize(200, 100);
		hasChess.setLocation(300, 300);
		if(chess[0][0] == 3){
			text.setText("遊戲結束了!!");
		}else{
			text.setText("不能放同一個地方!!");
		}
		bb.addActionListener(this);
		hasChess.add(text, BorderLayout.NORTH);
		hasChess.add(bb, BorderLayout.SOUTH);
		hasChess.setVisible(true);
		hasChess.addWindowListener(new AdapterDemo());
	}
	//****	有人贏會跑出來的畫面	****//
	public void win(){
		setBoard(3);
		Graphics g = getGraphics();
		update(g);
		win.setSize(200, 100);
		win.setLocation(300, 300);
		JTextArea text = new JTextArea("Winner is "+ player);
		ww.addActionListener(this);
		win.add(text, BorderLayout.NORTH);
		win.add(ww, BorderLayout.SOUTH);
		win.setVisible(true);
		win.addWindowListener(new AdapterDemo());
	}
	//**** 接收按鈕	****//
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		if(button == bb){
			hasChess.dispose();
		}else if(button == ww){
			win.dispose();
		}
	}
	//**** 判斷贏	****//
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
		AA = 0;
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
		return false;
	}
	
	//****	接收滑鼠點擊	****//
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
		        check(X, Y);	//檢查
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
	
	//****	左控制鍵	****//
	class ControlPanelLeft extends Panel implements ActionListener {  
	    int tm_unit=200;
	    int tm_sum =0;
	    int sec=0;
	    private JLabel play = new JLabel("    玩家1");
	    private JLabel steps = new JLabel("  下子數 = ");
	    private JLabel time = new JLabel("  時間 = ");
	    private Timer timer = new Timer(tm_unit, this);
	    
	    public ControlPanelLeft(){   
	    	timer.restart();
	        setLayout(new GridLayout(14,1,10,20));
	        add(new Label("                    ")); 
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
	    	play.setText("    玩家"+ player);
	    }
	    public void steps_event(){
	    	if(player == 1)
	    		steps.setText("  黑子數 = "+ count/2);
	    	else
	    		steps.setText("  黃子數 = "+ count/2);
	    }
	    public void timer_event(){
			if ((tm_sum += tm_unit) >= 1000 && chess[0][0] != 3){
				tm_sum -= 1000;
				sec+=1;
				time.setText("  時間= " + sec +"s");
			}
	    }
		public void actionPerformed(ActionEvent e){
			player_event();
			timer_event();
			steps_event();
		}
	}
}

//****	叉叉關閉視窗		****//
class AdapterDemo extends WindowAdapter {
    public void windowClosing(WindowEvent e){
    	e.getWindow().dispose();
    }
}
