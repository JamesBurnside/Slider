import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.border.*;
import javax.swing.JFileChooser.*;
import javax.swing.filechooser.FileFilter.*;
import java.io.File;

class Topmenu extends JMenuBar
{
	JGradientButton btnNew;
	JGradientButton btnShuffle;
	JGradientButton btnInc;
	JGradientButton btnDec;
	JGradientButton btnCompleted;
	JGradientButton btnExit;

	public Topmenu()
	{

		this.setLayout(new FlowLayout());

		btnNew = new JGradientButton("Load New Image");
		btnShuffle = new JGradientButton("Re-Shuffle");
		btnInc = new JGradientButton("Increase Difficulty");
		btnDec = new JGradientButton("Decrease Difficulty");
		btnCompleted = new JGradientButton("View Completed");
		btnExit = new JGradientButton("Exit");

		btnExit = setBtnExitClick(btnExit);

		this.add(btnNew);
		this.add(btnShuffle);
		this.add(btnInc);
		this.add(btnDec);
		this.add(btnCompleted);
		this.add(btnExit);
	}

	@Override
    protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setBackground(new Color(0x111111));
		g2.fillRect(0, 0, getWidth(), getHeight());
	}



	JGradientButton setBtnExitClick(JGradientButton btnExit)
	{
		btnExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		return btnExit;
	}


}

class JGradientButton extends JButton
{
        JGradientButton(String name)
        {
            super(name);
            setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setFocusPainted(false);
            //setBorderPainted(false);
            setForeground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setPaint(new GradientPaint(
                    new Point(0, 0),
                    new Color(0x0044cc),
                    new Point(0, getHeight()),
                    new Color(0x0088cc)));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();

            super.paintComponent(g);
        }
}

class JFileFilter extends javax.swing.filechooser.FileFilter {
	protected String description;
	protected ArrayList exts = new ArrayList();

	public void addType(String s) {
		exts.add(s);
	}

	/** Return true if the given file is accepted by this filter. */
	public boolean accept(File f) {
		// Little trick: if you don't do this, only directory names
		// ending in one of the extentions appear in the window.
		if (f.isDirectory()) {
			return true;

		} else if (f.isFile()) {
			Iterator it = exts.iterator();
			while (it.hasNext()) {
				if (f.getName().endsWith((String)it.next()))
					return true;
			}
		}

		// A file that didn't match, or a weirdo (e.g. UNIX device file?).
		return false;
	}

	/** Set the printable description of this filter. */
	public void setDescription(String s) {
		description = s;
	}
	/** Return the printable description of this filter. */
	public String getDescription() {
		return description;
	}
}