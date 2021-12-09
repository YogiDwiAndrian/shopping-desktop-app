package tugasBesar.Gui;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;


public class View extends JFrame {

	public View() {
		getContentPane().setBackground(new Color(255, 255, 255));
		viewRefresh();
	}


	public void addPanel(JPanel panel) {
		getContentPane().add(panel);
	}


	public void removePanel(JPanel panel) {
		getContentPane().remove(panel);
	}


	public void viewRefresh() {
		getContentPane().repaint();
		getContentPane().revalidate();
	}
}
