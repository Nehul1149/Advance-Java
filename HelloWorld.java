// Experiment 4 – Write a Program to create a Hello World applet
import java.applet.Applet;
import java.awt.Graphics;

// HelloWorld class extends Applet
public class HelloWorld extends Applet {
    // Overriding paint() method
    @Override
    public void paint(Graphics g) {
        g.drawString("Hello World", 20, 20);
    }
}
