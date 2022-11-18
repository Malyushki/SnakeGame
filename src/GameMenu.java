import javax.swing.*;
import java.awt.*;

public class GameMenu extends JPanel {
 GameMenu() {
 }
final int SCREEN_WIDTH = GamePanel.SCREEN_WIDTH;
final int SCREEN_HEIGHT = GamePanel.SCREEN_HEIGHT;

 public void draw(Graphics g) {

     g.setColor(Color.ORANGE);
     g.fillRect(0, 0, SCREEN_WIDTH,SCREEN_HEIGHT);
     g.setColor(Color.BLACK);
     g.setFont(new Font("Sans Serif",Font.BOLD,50));
     g.drawString("START",((SCREEN_WIDTH-150)/2),SCREEN_HEIGHT/2);
     g.drawString("TO",((SCREEN_WIDTH-75)/2),SCREEN_HEIGHT/3+25);
     g.drawString("PRESS ENTER",((SCREEN_WIDTH-330)/2),SCREEN_HEIGHT/4);
 }


}