// Experiment 5 – Write a Program to Set the color of the applet and draws a fill oval
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

/*
<applet code = "FillOval" width = 200 height = 200></applet>
*/
public class FillOval extends Applet {
    public void paint(Graphics g) {
        g.setcolor(Color.red);
        g.fillOval(20, 20, 60, 60);
    }
}
