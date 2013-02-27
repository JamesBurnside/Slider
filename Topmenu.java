import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BoxLayout;

class Topmenu extends JMenuBar
{
	public Topmenu()
	{
		this.setLayout(new FlowLayout());

		JMenu optionsMenu = new JMenu("Options");

		JMenuItem restartItem = new JMenuItem("Restart");
		JMenuItem closeItem = new JMenuItem("Close");

		optionsMenu.add(restartItem);
		optionsMenu.add(closeItem);

		this.setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.BLACK));
		this.add(optionsMenu);
		this.add(restartItem);
		this.add(closeItem);
	}
}