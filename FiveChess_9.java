import java.awt.*;
import java.awt.event.*;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class FiveChess {
	public static void main(String[] args){
		MainFrame game = new MainFrame();
		game.startGame();	//開始遊戲
	}
}

//****	五子棋遊戲	****//
class MainFrame {
	private static Frame frame;
	private static ChessBoard board;
	private static int length = 690;
	private static int width = 600;
	
	//****	遊戲畫面	****//
	public void startGame(){
		Panel panelL = new Panel();
		Panel panelM = new Panel();
	    Panel panelR = new Panel();
	    frame = new Frame("Five");	//建立新的介面frame title為Five
		frame.setSize(length, width);	//設置介面的大小
		frame.setResizable(false);	//固定介面大小
		frame.setLocation(10, 10);	//設置介面出現位置
		board = new ChessBoard();	//新增棋盤
		ControlPanelLeft leftControl = new ControlPanelLeft();	//新增 左邊的控制鍵
    	ControlPanelRight rightControl = new ControlPanelRight();	//新增 右邊的控制鍵
		panelL.add(leftControl, BorderLayout.WEST);
		panelM.add(board, BorderLayout.CENTER);
		panelR.add(rightControl, BorderLayout.EAST);
		frame.add(panelL, BorderLayout.WEST);	//左邊的控制鍵 加到介面左邊
		frame.add(board, BorderLayout.CENTER);	//棋盤 加到介面中央
		frame.add(panelR, BorderLayout.EAST);	//右邊的控制鍵 加到介面右邊
		
		frame.setVisible(true);	//顯示farme介面
		frame.addWindowListener(new AdapterDemo());	//按右上角叉叉能關閉frame
	}
	
	//****	設置主畫面長寬	****//
	public void setLW(int l, int w){
		length = l;
		width = w;
	}
	
	public Frame getFrame(){
		return frame;
	}
	public ChessBoard getBoard(){
		return board;
	}
}

//ChessActions
interface Actions{
	void setGameStatus(boolean theGameStatus);
	boolean getGameStatus();
	void check(int x, int y);
	void regret();
}

class ChessAction implements Actions {
	int ROWS;
	int COLS;
	private int[][] chess;
	private int[][] chessRegret;
	private int player = 1;
	private int count;
	private static boolean gameStatus;
	private int XX, YY, winStatus;
	
	public ChessAction(int rows,int cols){
		ROWS = rows;
		COLS = cols;
		reset();
	}
	
	public void setGameStatus(boolean theGameStatus){
		gameStatus = theGameStatus;
	}
	
	public int[][] getChess(){
		return chess;
	}
	public int[][] getChessRegret(){
		return chessRegret;
	}
	public int getPlayer(){
		return player;
	}
	public int getCount(){
		return count;
	}
	public boolean getGameStatus(){
		return gameStatus;
	}
	public int getXX(){
		return XX;
	}
	public int getYY(){
		return YY;
	}
	public int getWinStatus(){
		return winStatus;
	}
	
	//****	設置棋子	****//
	private void reset(){
		gameStatus = true;
		count = 0;
		chess = new int[ROWS+1][COLS+1];
		chessRegret = new int[ROWS+1][COLS+1];
		for(int i = 0; i<=ROWS; i++){
			for(int j = 0; j<=COLS; j++){
				chess[i][j] = 0;
				chessRegret[i][j] = -1;
			}
		}
	}
	
	//****	檢查是否以放棋子	****//
	public void check(int x, int y){
		if(!gameStatus) return;
		if(chess[x][y] > 0){
			new HasChess();
		}else if(chess[x][y] == 0){
			setChess(x, y);
		}
	}
	
	//****	放置棋子	****//
	private void setChess(int x, int y){
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		
		System.out.println(x +" "+ y);
		if(x > ROWS || y > COLS) return;
		chess[x][y] = player;
		chessRegret[x][y] = count;
		count++;
		if(checkWin()){
			new Win(player);
		}else{
			if(player == 1){				
				player = 2;
			}else if(player == 2){
				player = 1;
			}
		}
		if(count == (ROWS+1)*(COLS+1) && winStatus == 0){
			new Win(0);
		}
		Graphics g = board.getGraphics();
		board.update(g);	//更新畫面
	}

	//****	悔棋	****//
	public void regret(){
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		
		if(count == 0) return;
		for(int i = 0; i <= ROWS; i++){
			for(int j = 0; j <= COLS; j++){
				if(chessRegret[i][j] == (count-1)){
					chess[i][j] = 0;
					chessRegret[i][j] = -1;
					count--;
					Graphics g = board.getGraphics();
					board.update(g);
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
	
	//**** 判斷贏	****//
	private boolean checkWin(){
		for(int i = 0; i <= ROWS; i++){
			XX = i;
			for(int j = 0; j <= COLS; j++){
				YY = j;
				if(chess[i][j] > 0){
					switch(chess[i][j]){
						case 1:
							if(chechWinStatus(i, j, 1)){
								count++;
								return true;
							}
							break;
						case 2:
							if(chechWinStatus(i, j, 2))
								return true;
							break;
					}
				}
			}
		}
		return false;
	}
	
	//**** 判斷贏的型態	****//
	private boolean chechWinStatus(int i, int j, int player){
		int winCount;
		winCount = 0;
		winStatus = 1;
		for(int k = i; k<=ROWS;k++){
			if(chess[k][j]==player){
				winCount++;
				if(winCount == 5)
					return true;
			}else break;
		}
		winCount = 0;
		winStatus = 2;
		for(int l = j; l<=COLS;l++){
			if(chess[i][l]==player){
				winCount++;
				if(winCount == 5)
					return true;
			}else break;
		}
		winCount = 0;
		winStatus = 3;
		for(int k=i,l=j;k<=ROWS&&l<=COLS;k++,l++){
			if(chess[k][l]==player){
				winCount++;
				if(winCount == 5) 
					return true;
			}else break;
		}
		winCount = 0;
		winStatus = 4;
		for(int k=i,l=j;k<=ROWS&&l>=0;k++,l--){
			if(chess[k][l]==player){
				winCount++;
				if(winCount == 5) 
					return true;
			}else break;
		}
		winStatus = 0;
		return false;
	}
}

//****	棋盤	****//
class ChessBoard extends JComponent{
	private final int MARGIN=30;
	private final int GRID_SPAN=35;
	protected static int ROWS=14;
	protected static int COLS=14;
	ChessAction chessAction = new ChessAction(ROWS, COLS);
	
	public ChessBoard(){
		addMouseListener(new MouseClicked());
	}
	
	//****	set	****//
	public void setBoard(int rows, int cols){
		ROWS = rows;
		COLS = cols;
	}
	
	//****	get	****//
	public int getMargin(){
		return MARGIN;
	}
	public int getGridSpan(){
		return GRID_SPAN;
	}
	public int getRows(){
		return ROWS;
	}
	public int getCols(){
		return COLS;
	}
	public ChessAction getChessAction(){
		return chessAction;
	}

	//****	畫出棋盤	****//
	public void paint(Graphics g){
		int[][] chess = chessAction.getChess();
		
		regretChess(g, chess);
		drawTable(g);
		putChess(g, chess);
		if(chessAction.getGameStatus() == false)
			showWin(g, chessAction.getWinStatus(), chessAction.getXX(), chessAction.getYY());
	}
	
	//****	悔棋時白色要覆蓋	****//
	public void regretChess(Graphics g, int[][] chess){
		for(int i = 0; i<=ROWS; i++){
			for(int j = 0; j<=COLS; j++){
				if(chess[i][j] == 0){
					g.setColor(Color.WHITE);
					g.fillOval(i*GRID_SPAN+20,j*GRID_SPAN+20, 20, 20);
				}
			}
		}
	}
	
	//****	畫出格子	****//
	public void drawTable(Graphics g){
		g.setColor(Color.BLACK);
		for(int i=0;i<=ROWS;i++){  
	        g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);  
	    }  
	    for(int i=0;i<=COLS;i++){  
	        g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);  
	    }
	}
	
	//****	棋子顏色與放置	****//
	public void putChess(Graphics g, int[][] chess){
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
	}
	
	//****	顯示贏	****//
	public void showWin(Graphics g, int winStatus, int XX, int YY){
		g.setColor(Color.RED);
		if(winStatus == 1){
			g.drawLine(MARGIN+XX*GRID_SPAN, MARGIN+YY*GRID_SPAN, MARGIN+(XX+4)*GRID_SPAN, MARGIN+YY*GRID_SPAN);
		}else if(winStatus == 2){
			g.drawLine(MARGIN+XX*GRID_SPAN, MARGIN+YY*GRID_SPAN, MARGIN+XX*GRID_SPAN, MARGIN+(YY+4)*GRID_SPAN);
		}else if(winStatus == 3){
			g.drawLine(MARGIN+XX*GRID_SPAN, MARGIN+YY*GRID_SPAN, MARGIN+(XX+4)*GRID_SPAN, MARGIN+(YY+4)*GRID_SPAN);
		}else if(winStatus == 4){
			g.drawLine(MARGIN+XX*GRID_SPAN, MARGIN+YY*GRID_SPAN, MARGIN+(XX+4)*GRID_SPAN, MARGIN+(YY-4)*GRID_SPAN);
		}
	}

}

//****	左控制鍵	****//
class ControlPanelLeft extends Panel implements ActionListener {  
    int tm_unit=200;
    int tm_sum =0;
    int sec=0;
    private JLabel color = new JLabel("    黑子");
    private JLabel steps = new JLabel("  下子數 = ");
    private JLabel time = new JLabel("  時間 = ");
    private Timer timer = new Timer(tm_unit, this);
    MainFrame mainFrame = new MainFrame();
	ChessBoard board = mainFrame.getBoard();
	ChessAction chessAction = board.getChessAction();
    public ControlPanelLeft(){   
    	timer.restart();
        setLayout(new GridLayout(14,1,10,20));
        add(new Label("                      ")); 
        add(color);  
        add(new Label());
        add(new Label());
        add(new Label());
        add(new Label());
        add(new Label());
        add(new Label());
        add(steps);
        add(time);
    }
    public void paint(Graphics g){
		super.paint(g);
		if(chessAction.getPlayer() == 1)
			g.setColor(Color.BLACK);
		else
			g.setColor(Color.ORANGE);
		g.fillOval(55, 45, 15, 15);
    }
    private void player_event(){
    	if(chessAction.getPlayer() == 1)
    		color.setText("    黑子下");
    	else
    		color.setText("    黃子下");
    }
    private void steps_event(){
    	if(chessAction.getPlayer() == 1)
    		steps.setText("  黑子數 = "+ chessAction.getCount()/2);
    	else
    		steps.setText("  黃子數 = "+ chessAction.getCount()/2);
    }
    private void timer_event(){
		if ((tm_sum += tm_unit) >= 1000 && chessAction.getGameStatus() == true){
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

//****	右控制鍵	****//
class ControlPanelRight extends Panel implements ActionListener {
	Button b0 = new Button("設  置");
    Button b1 = new Button("悔  棋"); 
    Button b2 = new Button("重  新");   
    Button b3 = new Button("幫  助");   
    Button b4 = new Button("離  開"); 
    
    public ControlPanelRight(){
    	setLayout(new GridLayout(0, 1, 0, 5));
        add(new Label());
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
    }
    
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		/*
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		*/
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		ChessAction chessAction = board.getChessAction();
		
		if(button == b0){
			new Set();
		}else if(button == b1){
			if(chessAction.getGameStatus() == true)
				chessAction.regret();
		}else if(button == b2){
			new Renew();
		}else if(button == b3){
			new Help();
		}else if(button == b4){
			new Exit();
		}
	}
}

//****	設置畫面	****//
class Set implements ActionListener {
	Panel p0 = new Panel();
	Panel p1 = new Panel();
	Panel p2 = new Panel();
	Panel p3 = new Panel();
	Frame frameSet = new Frame("設  置");
	Button b0 = new Button("15 x 15");
	Button b1 = new Button("17 x 17");
	Button b2 = new Button("19 x 19");
	JTextArea text = new JTextArea("棋盤大小");
	
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
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		Frame frame = mainFrame.getFrame();
		
		if(button == b0){
			mainFrame.setLW(690, 600);
			board.setBoard(14, 14);
		}else if(button == b1){
			mainFrame.setLW(760, 650);
			board.setBoard(16, 16);
		}else if(button == b2){
			mainFrame.setLW(830, 750);
			board.setBoard(18, 18);
		}
		frame.dispose();
		frameSet.dispose();
		mainFrame.startGame();
	}
}

//****	重新開始	****//
class Renew implements ActionListener {
	Panel p0 = new Panel();
	Panel p1 = new Panel();
	Panel p3 = new Panel();
	Frame frameRenew = new Frame("重  新");
	Button b0 = new Button("取  消");
	Button b1 = new Button("確  定");
	JTextArea text = new JTextArea("確定要重新開始?");
	
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
		frameRenew.add(p0, BorderLayout.EAST);
		frameRenew.add(p1, BorderLayout.WEST);
		frameRenew.setVisible(true);
		frameRenew.addWindowListener(new AdapterDemo());
	}
	
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		MainFrame mainFrame = new MainFrame();
		Frame frame = mainFrame.getFrame();
		
		if(button == b0){
			frameRenew.dispose();
		}else if(button == b1){
			frameRenew.dispose();
			frame.dispose();
			mainFrame.startGame();
		}
	}
}

//****	幫助畫面	****//
class Help implements ActionListener {
	Frame frameHelp = new Frame("幫  助");
	Button b0 = new Button("確定");
	JTextArea text = new JTextArea(
			" 兩人對奕，先下手者持黑子，後下手者持黃子。\n"
			+ " 以輪流方式將棋子置於棋盤點上，先將五個棋子連成一線為勝。\n"
			+ " 當棋盤點都下滿棋子且無贏家，則為和棋。");
	
	public Help(){
		frameHelp.setSize(400, 150);
		frameHelp.setResizable(false);
		frameHelp.setLocation(300, 300);
		b0.addActionListener(this);
		frameHelp.add(text, BorderLayout.NORTH);
		frameHelp.add(b0, BorderLayout.SOUTH);
		frameHelp.setVisible(true);
		frameHelp.addWindowListener(new AdapterDemo());
	}
	
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		
		if(button == b0){
			frameHelp.dispose();
		}
	}
}

//**** 離開畫面	****//
class Exit implements ActionListener {
	Panel p0 = new Panel();
	Panel p1 = new Panel();
	Panel p3 = new Panel();
	Frame frameExit = new Frame("離  開");
	Button b0 = new Button("取  消");
	Button b1 = new Button("確  定");
	JTextArea text = new JTextArea("確定要離開?");
	
	public Exit(){
		frameExit.setSize(200, 100);
		frameExit.setResizable(false);
		frameExit.setLocation(300, 300);
		p0.add(b0);
		p1.add(b1);
		p3.add(text, BorderLayout.CENTER);
		b0.addActionListener(this);
		b1.addActionListener(this);
		frameExit.add(p3, BorderLayout.NORTH);
		frameExit.add(p0, BorderLayout.EAST);
		frameExit.add(p1, BorderLayout.WEST);
		frameExit.setVisible(true);
		frameExit.addWindowListener(new AdapterDemo());
	}
	
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		
		if(button == b0){
			frameExit.dispose();
		}else if(button == b1){
			System.exit(0);
		}
	}
}

//**** 輸贏話面 ****//
class Win implements ActionListener {
	MainFrame mainFrame = new MainFrame();
	ChessBoard board = mainFrame.getBoard();
	ChessAction chessAction = board.getChessAction();
	private Frame win = new Frame("Win");
	private Button enter = new Button("確定");
	private JTextArea textWin = new JTextArea("");
	public Win(int status){
		Panel p = new Panel();
		chessAction.setGameStatus(false);
		win.setSize(200, 100);
		win.setResizable(false);
		win.setLocation(300, 300);
		if(status == 0)
			textWin = new JTextArea("!! 和      局 !!");
		else if(status == 1)
			textWin = new JTextArea("黑 子 勝 !!");
		else
			textWin = new JTextArea("黃 子 勝 !!");
		
		p.add(textWin, BorderLayout.CENTER);
		enter.addActionListener(this);
		win.add(p, BorderLayout.NORTH);
		win.add(enter, BorderLayout.SOUTH);
		win.setVisible(true);
		win.addWindowListener(new AdapterDemo());
	}

	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		
		if(button == enter){
			win.dispose();
		}
	}
}

//**** 有旗子畫面 ****//
class HasChess implements ActionListener {
	ChessBoard game = new ChessBoard();
	private Frame hasChess = new Frame("");
	private static JTextArea textHasChess = new JTextArea("不能放同一個地方!!");
	private Button enter = new Button("確定");
	
	public HasChess(){
		hasChess.setSize(200, 100);
		hasChess.setResizable(false);
		hasChess.setLocation(300, 300);
		enter.addActionListener(this);
		hasChess.add(textHasChess, BorderLayout.NORTH);
		hasChess.add(enter, BorderLayout.SOUTH);
		hasChess.setVisible(true);
		hasChess.addWindowListener(new AdapterDemo());
	}
	
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		
		if(button == enter){
			hasChess.dispose();
		}
	}
}

//****	接收滑鼠點擊	****//
class MouseClicked extends MouseAdapter {
	private int X;
	private int Y;
	public MouseClicked(){}
		
	public void mousePressed(MouseEvent e){
		/*
	    System.out.print("screen x: " + e.getXOnScreen());
	    System.out.print(", y: " + e.getYOnScreen() + "\n");
	    */
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		ChessAction chessAction = board.getChessAction();
	    if (e.getButton() == MouseEvent.BUTTON1){
	        //System.out.println("left button");
	        X = (e.getX()-board.getMargin()/2)/board.getGridSpan();
	        Y = (e.getY()-board.getMargin()/2)/board.getGridSpan();
	        chessAction.check(X, Y);	//檢查
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

//****	叉叉關閉視窗		****//
class AdapterDemo extends WindowAdapter {
    public void windowClosing(WindowEvent e){
    	e.getWindow().dispose();
    }
}
