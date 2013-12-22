import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;


//****	五子棋遊戲	****//
public class FiveChess{
	public static void main(String[] args){
		startGame();
	}
	
	public static void startGame(){
		Frame frame = new Frame("Five");	//建立新的介面frame title為Five
		frame.setSize(750, 650);	//設置介面的大小
		
		ControlPanelLeft leftControl = new ControlPanelLeft();	//新增 左邊的控制鍵
    	ControlPanelRight rightControl = new ControlPanelRight();	//新增 右邊的控制鍵
		ChessBoard board = new ChessBoard();	//新增棋盤
		
		board.addMouseListener(board.new MouseClicked());	//board加入setChess()來讀取滑鼠按鍵
		frame.add(leftControl, BorderLayout.WEST);	//左邊的控制鍵 加到介面左邊
		frame.add(board, BorderLayout.CENTER);	//棋盤 加到介面中央
		frame.add(rightControl, BorderLayout.EAST);	//右邊的控制鍵 加到介面右邊
		
		frame.setVisible(true);	//顯示farme介面
		frame.addWindowListener(new AdapterDemo());	//按右上角叉叉能關閉frame
	}
}

//****	setChess	****//


//****	棋盤	****//
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
	Button bb = new Button("確定");
	Button ww = new Button("確定");
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
			JTextArea text = new JTextArea("不能放同一個地方!!");
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

//****	右控制鍵	****//
class ControlPanelRight extends Panel implements ActionListener {   
    CheckboxGroup cbg = new CheckboxGroup();  
    Button b0 = new Button("設  置");
    Button b1 = new Button("悔  棋"); 
    Button b2 = new Button("開  始");   
    Button b3 = new Button("重  新");   
    Button b4 = new Button("幫  助");   
    Button b5 = new Button("離  開"); 
    
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
    
	public void actionPerformed(ActionEvent e) {	//Exit跳出遊戲
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
		Frame frame = new Frame("幫  助");
		Button bb = new Button("確定");
		JTextArea text = new JTextArea(
				" 兩人對奕，先下手者持黑子，後下手者持白子。\n"
				+ " 以輪流方式將棋子置於棋盤點上，先將五個棋子連成一線為勝。\n"
				+ " 當棋盤點都下滿棋子，則為和棋。");
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

//****	左控制鍵	****//
class ControlPanelLeft extends Panel implements ActionListener {   
    CheckboxGroup cbg = new CheckboxGroup();   
    int tm_unit=200, tm_sum =0;
    int sec=0,tm_game =40;
    Checkbox cb1 = new Checkbox("Black first",cbg,true);   
    Checkbox cb2 = new Checkbox("White first",cbg,false);   
    JLabel steps = new JLabel("步數 = ");
    JLabel time = new JLabel("時間 = ");
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
			time.setText("時間= " + sec +"s");
		}
    }
	public void actionPerformed(ActionEvent e){
		timer_event();
	}
}

//****	叉叉關閉視窗		****//
class AdapterDemo extends WindowAdapter {
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }
}
