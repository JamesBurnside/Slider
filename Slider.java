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
	Container contentPane;
	JPanel[][] panels;
	JPanel display_panel;
	Topmenu menuBar;
	int cols, rows;
	Image buffer;
	Graphics2D g2d;
	int pictureDimension;
	String imagePath;

	public static void main(String args[])
	{
		Slider program = new Slider();
		program.run();
	}

	public void run()
	{
		imagePath = "blah.jpg";
		frame = new JFrame();
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
		pictureDimension = 900;

		contentPane = frame.getContentPane();
		contentPane.setBackground(Color.BLACK);
		contentPane.setLayout(new BorderLayout());

		display_panel =  new JPanel();

		Topmenu menuBar = new Topmenu();
		menuBar.btnNew = setBtnNewClick(menuBar.btnNew);
		menuBar.btnInc = setBtnIncClick(menuBar.btnInc);

		//Have a start screen that asks for difficulty level
		//or set default
		cols = rows = 3;
		//for now set a default of 3x3
		panels = new JPanel[rows][cols];

		display_panel = loadImage(panels);
		contentPane.add(display_panel, BorderLayout.CENTER);
		contentPane.add(menuBar, BorderLayout.NORTH);

		frame.setVisible(true);
	}

	void resetImage()
	{
		frame = new JFrame();
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.setSize(1000,1000);
		pictureDimension = 900;

		contentPane = frame.getContentPane();
		contentPane.setBackground(Color.BLACK);
		contentPane.setLayout(new BorderLayout());

		display_panel =  new JPanel();

		Topmenu menuBar = new Topmenu();
		menuBar.btnNew = setBtnNewClick(menuBar.btnNew);
		menuBar.btnInc = setBtnIncClick(menuBar.btnInc);

		//for now set a default of 3x3
		panels = new JPanel[rows][cols];

		display_panel = loadImage(panels);
		contentPane.add(display_panel, BorderLayout.CENTER);
		contentPane.add(menuBar, BorderLayout.NORTH);

		frame.setVisible(true);
	}

	public JPanel loadImage(JPanel[][] panels)
	{
		GridLayout gridLayout = new GridLayout(rows,cols);
		JPanel display_panel = new JPanel();
		display_panel.setBackground(new Color(0x333333));
		display_panel.setPreferredSize(new Dimension(pictureDimension,pictureDimension));
		display_panel.setLayout(gridLayout);

		BufferedImage fullimg = null;
		try
		{
    		fullimg = ImageIO.read(new File(imagePath));
		}
		catch (IOException e) {}

		//JLabel picLabel = new JLabel(new ImageIcon(fullimg));
		//panels.add( picLabel );

		BufferedImage[][] chops = new BufferedImage[rows][cols];
		JLabel chopLabel[][] = new JLabel[rows][cols];

		int chopwidth = pictureDimension/rows;
		int chopheight = pictureDimension/cols;

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
                panels[r][c].setBackground(new Color(0x333333));
                panels[r][c].setPreferredSize(new Dimension(chopwidth, chopheight));
                if( !(r==rows-1 && c==cols-1))
                	panels[r][c].add(chopLabel[r][c]);

			}

		//put panels onto display panel in (order to start)
		for (int c=0; c<cols; c++)
			for(int r=0; r<rows; r++)
				if( !(r==rows-1 && c==cols-1))
					display_panel.add(panels[c][r], BorderLayout.CENTER);

		return display_panel;
	}



	//BUTTON EVENTS
	JGradientButton setBtnNewClick(JGradientButton btnNew)
	{
		btnNew.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();

				JFileFilter filter = new JFileFilter();
				filter.addType("jpg");
				filter.addType("JPG");
				filter.setDescription("JPG Images");
				chooser.setFileFilter(filter);

				int returnVal = chooser.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					imagePath = chooser.getSelectedFile().getName();
					resetImage();
    			}

			}
		});

		return btnNew;
	}

	JGradientButton setBtnIncClick(JGradientButton btnInc)
	{
		btnInc.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				rows++;
				cols++;
				resetImage();
			}
		});

		return btnInc;
	}
}