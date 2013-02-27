import java.awt.*;
import java.awt.event.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.lang.Object;
import javax.imageio.ImageIO;
import java.io.*;

public class Slider
{
	JFrame frame;
	JPanel[][] panels;
	Topmenu menuBar;
	int cols, rows;
	Image buffer;
	Graphics2D g2d;

	public static void main(String args[])
	{
		Slider program = new Slider();
		program.run();
	}

	public void run()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);

		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());

		JPanel display_panel =  new JPanel();
		display_panel.setPreferredSize(new Dimension(1000,900));
		display_panel.setLayout(new BorderLayout());

		Topmenu menuBar = new Topmenu();

		//Have a start screen that asks for difficulty level
		//or set default
		cols = rows = 3;
		//for now set a default of 3x3
		panels = new JPanel[rows][cols];


		display_panel = loadImage("blah.jpg", panels);
		contentPane.add(menuBar, BorderLayout.NORTH);
		contentPane.add(display_panel, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	public JPanel loadImage(String filename, JPanel[][] panels)
	{
		JPanel display_panel = new JPanel();
		display_panel.setPreferredSize(new Dimension(900,900));

		BufferedImage fullimg = null;
		try
		{
    		fullimg = ImageIO.read(new File(filename));
		}
		catch (IOException e) {}

		//JLabel picLabel = new JLabel(new ImageIcon(fullimg));
		//panels.add( picLabel );

		BufferedImage[][] chops = new BufferedImage[rows][cols];
		JLabel chopLabel[][] = new JLabel[rows][cols];

		int chopwidth = 900/rows;
		int chopheight = 900/cols;

		for (int c=0; c<cols; c++)
			for(int r=0; r<rows; r++)
			{
				chops[r][c] = new BufferedImage(chopwidth, chopheight, BufferedImage.TYPE_INT_RGB);
				Graphics2D gr = chops[r][c].createGraphics();
                gr.drawImage(fullimg,
                	0, 0,
                	chopwidth, chopheight,
                	chopwidth * c, chopheight * r,
                	chopwidth * c + chopwidth, chopheight * r + chopheight,
                	null);
                gr.dispose();

                chopLabel[r][c] = new JLabel(new ImageIcon(chops[r][c]));
                panels[r][c] = new JPanel();
                panels[r][c].setPreferredSize(new Dimension(chopwidth, chopheight));
                panels[r][c].add(chopLabel[r][c]);
                display_panel.add(panels[r][c], BorderLayout.SOUTH);
			}

		return display_panel;
	}
}