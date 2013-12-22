import java.awt.*;
import java.awt.event.*;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.Timer;

public class FiveChess {
	public static void main(String[] args){
		MainFrame game = new MainFrame();
		game.startGame();	//�}�l�C��
	}
}

//****	���l�ѹC��	****//
class MainFrame {
	private static Frame frame;
	private static ChessBoard board;
	private static int length = 690;
	private static int width = 600;
	private static Message message;
	//****	�C���e��	****//
	public void startGame(){
		message = new Message();
		Panel panelL = new Panel();
		Panel panelM = new Panel();
	    Panel panelR = new Panel();
	    frame = new Frame("Five");	//�إ߷s������frame title��Five
		frame.setSize(length, width);	//�]�m�������j�p
		frame.setResizable(false);	//�T�w�����j�p
		frame.setLocation(10, 10);	//�]�m�����X�{��m
		board = new ChessBoard();	//�s�W�ѽL
		ControlPanelLeft leftControl = new ControlPanelLeft();	//�s�W ���䪺������
    	ControlPanelRight rightControl = new ControlPanelRight();	//�s�W �k�䪺������
		panelL.add(leftControl, BorderLayout.WEST);
		panelM.add(board, BorderLayout.CENTER);
		panelR.add(rightControl, BorderLayout.EAST);
		frame.add(panelL, BorderLayout.WEST);	//���䪺������ �[�줶������
		frame.add(board, BorderLayout.CENTER);	//�ѽL �[�줶������
		frame.add(panelR, BorderLayout.EAST);	//�k�䪺������ �[�줶���k��
		
		frame.setVisible(true);	//���farme����
		frame.addWindowListener(new MainFrameClose());	//���k�W���e�e������frame
	}
	
	//****	�]�m�D�e�����e	****//
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
	public Message getMessage(){
		return message;
	}
}

class ChessAction {
	private int count;
	private int XX, YY, winStatus;
	
	public int getCount(){
		return count;
	}
	public int getXX() {
		return XX;
	}
	public int getYY() {
		return YY;
	}
	public int getWinStatus(){
		return winStatus;
	}
	
	public void check(int x, int y){
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		Message message = mainFrame.getMessage();
		String s = "";
		
		if(!board.getGameStatus()) return;
		if(board.getChess(x, y) > 0){
			s = "("+ x +","+ y + ")�w��L�Ѥl\n";
			new HasChess();
		}else if(board.getChess(x, y) == 0){
			s = "Player"+ board.getPlayer() +": ��m("+ x +","+ y +")\n";
			setChess(x, y);
		}
		message.setText(s);
	}
	
	//****	��m�Ѥl	****//
	private void setChess(int x, int y){
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		int ROWS = board.getRows(), COLS = board.getCols();
		System.out.println(x +" "+ y);
		if(x > board.getRows() || y > board.getCols()) return;
		board.setChess(x, y, board.getPlayer());
		board.setChessRegret(x, y, count);
		count++;
		Graphics g = board.getGraphics();
		if(checkWin()){
			new Win(board.getPlayer());
		}else{
			board.setPlayer();
		}
		if(count == (ROWS+1)*(COLS+1) && winStatus == 0){
			new Win(0);
		}
		board.update(g);	//��s�e��
	}
	
	//****	����	****//
	public void regret(){
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		int ROWS = board.getRows(), COLS = board.getCols();
		if(count == 0) return;
		for(int i = 0; i <= ROWS; i++){
			for(int j = 0; j <= COLS; j++){
				if(board.getChessRegret(i, j) == (count-1)){
					board.setChess(i, j, 0);
					board.setChessRegret(i, j, -1);
					count--;
					Graphics g = board.getGraphics();
					board.update(g);
					board.setPlayer();
					return;
				}
			}
		}
	}
	
	//**** �P�_Ĺ	****//
	private boolean checkWin(){
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		int ROWS = board.getRows(), COLS = board.getCols();
		for(int i = 0; i <= ROWS; i++){
			XX = i;
			for(int j = 0; j <= COLS; j++){
				YY = j;
				if(board.getChess(i, j) > 0){
					switch(board.getChess(i, j)){
						case 1:
							if(chechWinStatus(i,j, 1)){
								count++;
								return true;
							}
							break;
						case 2:
							if(chechWinStatus(i,j, 2))
								return true;
							break;
					}
				}
			}
		}
		return false;
	}
	
	//**** �P�_Ĺ�����A	****//
	private boolean chechWinStatus(int i, int j, int player){
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		int ROWS = board.getRows(), COLS = board.getCols();
		int winCount;
		winCount = 0;
		for(int k = i; k<=ROWS;k++){
			if(board.getChess(k, j)==player){
				winCount++;
				if(winCount == 5)
					return true;
			}else break;
		}
		winCount = 0;
		winStatus = 2;
		for(int l = j; l<=COLS;l++){
			if(board.getChess(i, l)==player){
				winCount++;
				if(winCount == 5)
					return true;
			}else break;
		}
		winCount = 0;
		winStatus = 3;
		for(int k=i,l=j;k<=ROWS&&l<=COLS;k++,l++){
			if(board.getChess(k, l)==player){
				winCount++;
				if(winCount == 5) 
					return true;
			}else break;
		}
		winCount = 0;
		winStatus = 4;
		for(int k=i,l=j;k<=ROWS&&l>=0;k++,l--){
			if(board.getChess(k, l)==player){
				winCount++;
				if(winCount == 5) 
					return true;
			}else break;
		}
		winStatus = 0;
		return false;
	}
}
//****	�ѽL	****//
class ChessBoard extends JComponent{
	private final int MARGIN=30;
	private final int GRID_SPAN=35;
	private final int CIRCLE=20;
	private static int ROWS=14;
	private static int COLS=14;
	private int[][] chess;
	private static int[][] chessRegret;
	private int player = 1;
	private static boolean gameStatus;
	ChessAction chessAction = new ChessAction();
	
	public ChessBoard(){
		addMouseListener(new MouseClicked());
		reset();
	}

	//****	set	****//
	public void setPlayer(){
		if(player == 1){				
			player = 2;
		}else if(player == 2){
			player = 1;
		}
	}

	public void setChess(int x, int y, int player) {
		chess[x][y] = player;
	}
	public void setBoard(int rows, int cols){
		ROWS = rows;
		COLS = cols;
	}
	public void setGameStatus(boolean theGameStatus){
		gameStatus = theGameStatus;
	}
	public void setChessRegret(int x, int y, int count){
		chessRegret[x][y] = count;
	}
	
	//****	get	****//
	public int getChessRegret(int x, int y){
		return chessRegret[x][y];
	}
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
	public int getChess(int x, int y){
		return chess[x][y];
	}
	public int getPlayer(){
		return player;
	}

	public boolean getGameStatus(){
		return gameStatus;
	}
	public ChessAction getChessAction() {
		return chessAction;
	}
		
	//****	�]�m�Ѥl	****//
	private void reset(){
		gameStatus = true;
		chess = new int[ROWS+1][COLS+1];
		chessRegret = new int[ROWS+1][COLS+1];
		for(int i = 0; i<=ROWS; i++){
			for(int j = 0; j<=COLS; j++){
				chess[i][j] = 0;
				chessRegret[i][j] = -1;
			}
		}
	}
	
	//****	�e�X�ѽL	****//
	public void paint(Graphics g){
		regretChess(g);
		drawTable(g);
		putChess(g);
		showWin(g);
	}
	
	//****	���Ѯɥզ�n�л\	****//
	public void regretChess(Graphics g){
		for(int i = 0; i<=ROWS; i++){
			for(int j = 0; j<=COLS; j++){
				if(chess[i][j] == 0){
					g.setColor(Color.WHITE);
					g.fillOval(i*GRID_SPAN+CIRCLE,j*GRID_SPAN+CIRCLE, CIRCLE, CIRCLE);
				}
			}
		}
	}
	
	//****	�e�X��l	****//
	public void drawTable(Graphics g){
		g.setColor(Color.BLACK);
		for(int i=0;i<=ROWS;i++){  
	        g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);  
	    }  
	    for(int i=0;i<=COLS;i++){  
	        g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);  
	    }
	}
	
	//****	�Ѥl�C��P��m	****//
	public void putChess(Graphics g){
	    for(int i = 0; i<=ROWS; i++){
			for(int j = 0; j<=COLS; j++){
				if(chess[i][j] == 1){
					g.setColor(Color.BLACK);
					g.fillOval(i*GRID_SPAN+CIRCLE,j*GRID_SPAN+CIRCLE, CIRCLE, CIRCLE);
				}else if(chess[i][j] == 2){
					g.setColor(Color.ORANGE);
					g.fillOval(i*GRID_SPAN+CIRCLE,j*GRID_SPAN+CIRCLE, CIRCLE, CIRCLE);
				}
			}
		}
	}
	
	//****	���Ĺ	****//
	public void showWin(Graphics g){
		int winStatus = chessAction.getWinStatus();
		int XX = chessAction.getXX(), YY = chessAction.getYY();
	    if(gameStatus == false){
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
}

//****	��������	****//
class ControlPanelLeft extends Panel implements ActionListener {  
    int tm_unit=200;
    int tm_sum =0;
    int sec=0;
    private JLabel color = new JLabel("    �¤l");
    private JLabel steps = new JLabel("  �U�l�� = ");
    private JLabel time = new JLabel("  �ɶ� = ");
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
		if(board.getPlayer() == 1)
			g.setColor(Color.BLACK);
		else
			g.setColor(Color.ORANGE);
		g.fillOval(55, 45, 15, 15);
    }
    private void player_event(){
    	if(board.getPlayer() == 1)
    		color.setText("    �¤l�U");
    	else
    		color.setText("    ���l�U");
    }
    private void steps_event(){
    	if(board.getPlayer() == 1)
    		steps.setText("  �¤l�� = "+ chessAction.getCount()/2);
    	else
    		steps.setText("  ���l�� = "+ chessAction.getCount()/2);
    }
    private void timer_event(){
		if ((tm_sum += tm_unit) >= 1000 && board.getGameStatus() == true){
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

//****	�k������	****//
class ControlPanelRight extends Panel implements ActionListener {
	Button b0 = new Button("�]  �m");
    Button b1 = new Button("��  ��"); 
    Button b2 = new Button("��  �s");   
    Button b3 = new Button("��  �U");   
    Button b4 = new Button("��  �}"); 
    
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
		MainFrame mainFrame = new MainFrame();
		ChessBoard board = mainFrame.getBoard();
		ChessAction chessAction = board.getChessAction();
		if(button == b0){
			new Set();
		}else if(button == b1){
			if(board.getGameStatus() == true)
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

//****	�]�m�e��	****//
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
		frameSet.addWindowListener(new CloseWindow());
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

//****	���s�}�l	****//
class Renew implements ActionListener {
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
		frameRenew.add(p0, BorderLayout.EAST);
		frameRenew.add(p1, BorderLayout.WEST);
		frameRenew.setVisible(true);
		frameRenew.addWindowListener(new CloseWindow());
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

//****	���U�e��	****//
class Help implements ActionListener {
	Frame frameHelp = new Frame("��  �U");
	Button b0 = new Button("�T�w");
	JTextArea text = new JTextArea(
			" ��H�﫳�A���U��̫��¤l�A��U��̫����l�C\n"
			+ " �H���y�覡�N�Ѥl�m��ѽL�I�W�A���N���ӴѤl�s���@�u���ӡC\n"
			+ " ��ѽL�I���U���Ѥl�B�LĹ�a�A�h���M�ѡC");
	
	public Help(){
		frameHelp.setSize(400, 150);
		frameHelp.setResizable(false);
		frameHelp.setLocation(300, 300);
		b0.addActionListener(this);
		frameHelp.add(text, BorderLayout.NORTH);
		frameHelp.add(b0, BorderLayout.SOUTH);
		frameHelp.setVisible(true);
		frameHelp.addWindowListener(new CloseWindow());
	}
	
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		
		if(button == b0){
			frameHelp.dispose();
		}
	}
}

//**** ���}�e��	****//
class Exit implements ActionListener {
	Panel p0 = new Panel();
	Panel p1 = new Panel();
	Panel p3 = new Panel();
	Frame frameExit = new Frame("��  �}");
	Button b0 = new Button("��  ��");
	Button b1 = new Button("�T  �w");
	JTextArea text = new JTextArea("�T�w�n���}?");
	
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
		frameExit.addWindowListener(new CloseWindow());
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

//**** ��Ĺ�ܭ� ****//
class Win implements ActionListener {
	ChessBoard game = new ChessBoard();
	private Frame win = new Frame("Win");
	private Button enter = new Button("�T�w");
	private JTextArea textWin = new JTextArea("");
	public Win(int status){
		Panel p = new Panel();
		game.setGameStatus(false);
		win.setSize(200, 100);
		win.setResizable(false);
		win.setLocation(300, 300);
		if(status == 0)
			textWin = new JTextArea("!! �M      �� !!");
		else if(status == 1)
			textWin = new JTextArea("�� �l �� !!");
		else
			textWin = new JTextArea("�� �l �� !!");
		
		p.add(textWin, BorderLayout.CENTER);
		enter.addActionListener(this);
		win.add(p, BorderLayout.NORTH);
		win.add(enter, BorderLayout.SOUTH);
		win.setVisible(true);
		win.addWindowListener(new CloseWindow());
	}

	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		
		if(button == enter){
			win.dispose();
		}
	}
}

//**** ���X�l�e�� ****//
class HasChess implements ActionListener {
	private Frame hasChess = new Frame("");
	private static JTextArea textHasChess = new JTextArea("�����P�@�Ӧa��!!");
	private Button enter = new Button("�T�w");
	
	public HasChess(){
		hasChess.setSize(200, 100);
		hasChess.setResizable(false);
		hasChess.setLocation(300, 300);
		enter.addActionListener(this);
		hasChess.add(textHasChess, BorderLayout.NORTH);
		hasChess.add(enter, BorderLayout.SOUTH);
		hasChess.setVisible(true);
		hasChess.addWindowListener(new CloseWindow());
	}
	
	public void actionPerformed(ActionEvent e){
		Button button = (Button) e.getSource();
		
		if(button == enter){
			hasChess.dispose();
		}
	}
}

class Message {
	private Frame frame;
	private JTextPane text;
	private String s = "";
	
	public Message(){
		frame = new Frame();
		ScrollPane JSP = new ScrollPane();
		Panel panel = new Panel();
		text = new JTextPane();
	    frame = new Frame("Message");
		frame.setSize(280, 450);
		frame.setResizable(false);
		frame.setLocation(1000, 10);
		panel.add(text);
		JSP.add(panel);
		frame.add(JSP);
		frame.setVisible(true);
		frame.addWindowListener(new CloseWindow());
	}
	
	public void setText(String s){
		this.s = this.s + s;
		text.setText(this.s);
	}

	public void dispose(){
		frame.dispose();
	}
}

//****	�����ƹ��I��	****//
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
		    chessAction.check(X, Y);	//�ˬd
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
//****	�e�e��������		****//
class CloseWindow extends WindowAdapter {
	public void windowClosing(WindowEvent e){
		e.getWindow().dispose();
	}
}

class MainFrameClose extends WindowAdapter {
	public void windowClosing(WindowEvent e){
		System.exit(0);
	}
}
