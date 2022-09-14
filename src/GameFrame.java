import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameFrame extends JFrame{

	GameFrame(){
			
		ImageIcon image= new ImageIcon("logo.png");
		
		this.add(new GamePanel());
		this.setIconImage(image.getImage());
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
}