import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Timer;

//****	五子棋遊戲	****//
public class FiveChess{
	public static void main(String[] args){
		Frame frame = new Frame("Five");	//建立新的介面frame title為Five
		frame.setSize(750, 650);	//設置介面的大小
		
		ControlPanelLeft leftControl = new ControlPanelLeft();	//新增 左邊的控制鍵
    	ControlPanelRight rightControl = new ControlPanelRight();	//新增 右邊的控制鍵
		ChessBoard board = new ChessBoard();	//新增棋盤
		
		board.addMouseListener(new setChess());	//board加入setChess()來讀取滑鼠按鍵
		
		frame.add(leftControl, BorderLayout.WEST);	//左邊的控制鍵 加到介面左邊
		frame.add(board, BorderLayout.CENTER);	//棋盤 加到介面中央
		frame.add(rightControl, BorderLayout.EAST);	//右邊的控制鍵 加到介面右邊
		
		frame.setVisible(true);	//顯示farme介面
		frame.addWindowListener(new AdapterDemo());	//按右上角叉叉能關閉frame
	}
}

//****	setChess	****//
class setChess extends MouseAdapter{
	public setChess(){}
	
	public void mousePressed(MouseEvent e){
        System.out.print("screen x: " + e.getXOnScreen());
        System.out.print(", y: " + e.getYOnScreen() + "\n");
        
        if (e.getButton() == MouseEvent.BUTTON1){
            System.out.println("left button");
        }
        if (e.getButton() == MouseEvent.BUTTON2){
            System.out.println("middle button");
        }
        if (e.getButton() == MouseEvent.BUTTON3){
            System.out.println("right button");
        }
        
        System.out.println("mouse position: " + e.getPoint());
        System.out.println("mouse screen position: " + e.getLocationOnScreen());
        System.out.println("mouse clicks: " + e.getClickCount());
    }
}

//****	棋盤	****//
class ChessBoard extends JComponent {
	public static final int MARGIN=30;
	public static final int GRID_SPAN=35;
	public static final int ROWS=15;
	public static final int COLS=15;
	public ChessBoard(){}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i=0;i<=ROWS;i++){  
           g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);  
        }  
        for(int i=0;i<=COLS;i++){  
            g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);  
             
        }  
	}
}

//****	右控制鍵	****//
class ControlPanelRight extends Panel implements ActionListener {   
    CheckboxGroup cbg = new CheckboxGroup();  
    Button button = new Button("設  置");
    Button b0 = new Button("悔  棋"); 
    Button b1 = new Button("開  始");   
    Button b2 = new Button("重  新");   
    Button b3 = new Button("幫  助");   
    Button b4 = new Button("離  開"); 
    
    public ControlPanelRight(){
    	add(button);
        setLayout(new GridLayout(14,1,10,5));   
        add(new Label());   
        add(new Label());   
        add(new Label());  
        add(b0);
        add(new Label());   
        add(new Label());   
        add(new Label());   
        add(b1);   
        add(b2);   
        add(b3);   
        add(b4);   
        b3.addActionListener(this);
        b4.addActionListener(this);
        setBounds(0,0,500,500);   
    }
    
	public void actionPerformed(ActionEvent e) {	//Exit跳出遊戲
		Button button = (Button) e.getSource();
		if(button == b3){
			new Help();
		}else if(button == b4){
			System.exit(0);
		}
	}
	
	class Help implements ActionListener{
		Frame frame = new Frame("Help");
		Button bb = new Button("yes");
		Label label = new Label("才不幫你咧~~");
		public Help(){
			frame.setSize(100, 100);
			bb.setBounds(20, 20, 20, 20);
			bb.addActionListener(this);
			frame.add(label, BorderLayout.NORTH);
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
