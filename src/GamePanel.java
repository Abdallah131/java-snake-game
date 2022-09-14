import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	static final int width = 800;
	static final int height = 800;
	static final int size = 30;
	static final int units = (width*height)/(size*size);
	static final int delay = 60;
	final int x[] = new int[units];
	final int y[] = new int[units];
	int parts = 4;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Image apple;
	Timer timer;
	Random random;
	
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(width,height));
		this.setBackground(Color.gray);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(delay,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
		ImageIcon fruit = new ImageIcon("apple.png");
		apple = fruit.getImage();
		
		g.setColor(Color.black);
		g.drawRect(1, 1, 798, 798);
		
		if(running) {
			g.drawImage(apple,appleX, appleY, size, size,this);
		
			for(int i = 0; i< parts;i++) {
				if(i == 0) {
					g.setColor(Color.black);
					g.fillRect(x[i], y[i], size, size);
				}
				else {
					g.setColor(Color.black);
					g.fillRect(x[i], y[i], size, size);
				}			
			}
			g.setColor(Color.black);
			g.setFont( new Font("Times New Roman",Font.BOLD, 30));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (width - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
		
	}
	public void newApple(){
		appleX = random.nextInt((int)(width/size))*size;
		appleY = random.nextInt((int)(height/size))*size;
	}
	public void move(){
		for(int i = parts;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - size;
			break;
		case 'D':
			y[0] = y[0] + size;
			break;
		case 'L':
			x[0] = x[0] - size;
			break;
		case 'R':
			x[0] = x[0] + size;
			break;
		}
		
	}
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			parts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkCollisions() {
		for(int i = parts;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				running = false;
			}
		}
		if(x[0] < 0) {
			running = false;
		}
		if(x[0] > width) {
			running = false;
		}
		if(y[0] < 0) {
			running = false;
		}
		if(y[0] > height) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		g.setColor(Color.black);
		g.setFont( new Font("Times New Roman",Font.BOLD, 30));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (width - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		g.setColor(Color.black);
		g.setFont( new Font("Times New Roman",Font.BOLD, 40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (width - metrics2.stringWidth("Game Over"))/2, height/2);
		g.drawString("Space to restart", 270, 450);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
	public void keyPressed(KeyEvent e) {
			
			if (e.getKeyCode()== KeyEvent.VK_SPACE) {
				parts=4;
				applesEaten=0;
				repaint();
				startGame();
			}
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}