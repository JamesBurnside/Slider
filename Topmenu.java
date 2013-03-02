import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.border.*;

class Topmenu extends JMenuBar
{
	public Topmenu()
	{
		this.setLayout(new FlowLayout());

		JGradientButton btnNew = new JGradientButton("Load New Image");
		JGradientButton btnShuffle = new JGradientButton("Re-Shuffle");
		JGradientButton btnCompleted = new JGradientButton("View Completed");
		JGradientButton btnExit = new JGradientButton("Exit");

		btnExit = setBtnExitClick(btnExit);

		this.add(btnNew);
		this.add(btnShuffle);
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

        private void MouseEntered(java.awt.event.MouseEvent evt)
        {
      		setBackground(Color.red);
    	}

    /*private void jButton2MouseExited(java.awt.event.MouseEvent evt) {
       this.jButton2.setBackground(Color.lightGray);
       this.Button.setForeground(Color.lightGray);
    }  */
}