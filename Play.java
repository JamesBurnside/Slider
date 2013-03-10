import java.awt.*;
import java.awt.event.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.lang.Object;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Play
{
	JFrame frame;
	Container contentPane;
	JMyPanel[][] panels;
	JPanel display_panel;
	Topmenu menuBar;
	int cols, rows;
	Image buffer;
	Graphics2D g2d;
	int pictureDimension;
	String imagePath;
	int blankr, blankc;
	JLabel chopLabel[][];
	int currentX, currentY, oldX, oldY;


	public static void main(String args[])
	{
		Play program = new Play();
		program.run();
	}

	public void run()
	{
		//Picks a random image
		Random randfile = new Random();
		int rfile = randfile.nextInt(1) + 1;
		imagePath = rfile + ".jpg";

		//Frame set-up
		frame = new JFrame("Slider");
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(930,1000);
		frame.setResizable( false );
		pictureDimension = 900;

		//Content pane set-up
		contentPane = frame.getContentPane();
		contentPane.setBackground(Color.BLACK);
		contentPane.setLayout(new BorderLayout());

		display_panel =  new JPanel();

		//Menu bar set-up
		Topmenu menuBar = new Topmenu();
		menuBar.btnNew = setBtnNewClick(menuBar.btnNew);
		menuBar.btnInc = setBtnIncClick(menuBar.btnInc);
		menuBar.btnShuffle = setBtnShuffleClick(menuBar.btnShuffle);
		menuBar.btnCompleted = setBtnCompletedClick(menuBar.btnCompleted);
		menuBar.btnDec = setBtnDecClick(menuBar.btnDec);
		contentPane.add(menuBar, BorderLayout.NORTH);
		frame.setVisible(true);

		int choice = 0, choice2 = 1;

		//choose difficulty
		Object[] options = {"Basic",
                    		"Difficult",
                    		"Insane"};

		choice = JOptionPane.showOptionDialog(
			frame,
		    "Choose your difficulty",
		    "Choose Difficulty",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[0]);

		//would you like to choose your own image?
		Object[] options2 = {"Yes", "Use Default"};

		choice2 = JOptionPane.showOptionDialog(
			frame,
		    "Would you like to choose your own image?",
		    "Choose Image",
		    JOptionPane.YES_NO_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options2,
		    options2[0]);

		if(choice2 == 0)
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
					imagePath = chooser.getSelectedFile().getAbsolutePath();
    			}
		}


		cols = rows = (choice+1)*3;
		blankr = blankc = cols-1;

		panels = new JMyPanel[rows][cols];
		display_panel = loadImage(panels, false);
		for (int c=0; c<cols; c++)
			for(int r=0; r<rows; r++)
				setPanelClick(panels[r][c]);
		contentPane.add(display_panel, BorderLayout.CENTER);


		frame.setVisible(true);
	}

	void resetImage(Boolean completed)
	{
		frame.getContentPane().removeAll();

		contentPane = frame.getContentPane();
		contentPane.setBackground(Color.BLACK);
		contentPane.setLayout(new BorderLayout());

		display_panel =  new JPanel();

		Topmenu menuBar = new Topmenu();
		menuBar.btnNew = setBtnNewClick(menuBar.btnNew);
		menuBar.btnInc = setBtnIncClick(menuBar.btnInc);
		menuBar.btnDec = setBtnDecClick(menuBar.btnDec);
		menuBar.btnCompleted = setBtnCompletedClick(menuBar.btnCompleted);
		menuBar.btnShuffle = setBtnShuffleClick(menuBar.btnShuffle);

		panels = new JMyPanel[rows][cols];
				blankr = blankc = cols - 1;

		display_panel = loadImage(panels, completed);
		contentPane.add(display_panel, BorderLayout.CENTER);
		contentPane.add(menuBar, BorderLayout.NORTH);
		for (int c=0; c<cols; c++)
			for(int r=0; r<rows; r++)
				setPanelClick(panels[r][c]);
		frame.setVisible(true);
	}

	void redraw()
	{
		int initialC, initialR, correctR, correctC;
		frame.getContentPane().removeAll();

		contentPane = frame.getContentPane();
		contentPane.setBackground(Color.BLACK);
		contentPane.setLayout(new BorderLayout());

		GridLayout gridLayout = new GridLayout(rows,cols);
		JMyPanel display_panel = new JMyPanel(1,1);
		display_panel.setBackground(new Color(0x333333));
		display_panel.setPreferredSize(new Dimension(pictureDimension,pictureDimension));
		display_panel.setLayout(gridLayout);

		Topmenu menuBar = new Topmenu();
		menuBar.btnNew = setBtnNewClick(menuBar.btnNew);
		menuBar.btnInc = setBtnIncClick(menuBar.btnInc);
		menuBar.btnDec = setBtnDecClick(menuBar.btnDec);
		menuBar.btnCompleted = setBtnCompletedClick(menuBar.btnCompleted);
		menuBar.btnShuffle = setBtnShuffleClick(menuBar.btnShuffle);

		BufferedImage fullimg = null;
		try
		{
    		fullimg = ImageIO.read(new File(imagePath));
		}
		catch (IOException e) {}

		BufferedImage[][] chops = new BufferedImage[rows][cols];
		JLabel chopLabel[][] = new JLabel[rows][cols];

		int chopwidth = pictureDimension/rows;
		int chopheight = pictureDimension/cols;

		for (int r=0; r<rows; r++)
			for(int c=0; c<cols; c++)
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
			}

		//make a set of old panels
		JMyPanel[][] oldPanels = new JMyPanel[rows][cols];
		for(int x=0; x<rows; x++)
			for(int y=0; y<cols; y++)
			{
				oldPanels[x][y] = new JMyPanel(x,y);
				oldPanels[x][y] = panels[x][y];
			}

		//for each panel, check each panel to be drawn's co-ordinates match the panel to be drawn onto
		// and arent blank
		for(int r=0; r<rows; r++)
			for (int c=0; c<cols; c++)
			{
				panels[r][c] = new JMyPanel(r, c);

				panels[r][c].initialC = oldPanels[r][c].initialC;
                panels[r][c].initialR = oldPanels[r][c].initialR;
                panels[r][c].correctC = oldPanels[r][c].correctR;
                panels[r][c].correctR = oldPanels[r][c].correctC;

                panels[r][c].setBackground(new Color(0x333333));

                if (!(r == blankr && c == blankc))
					panels[r][c].add(chopLabel[panels[r][c].initialR][panels[r][c].initialC]);
				else
				{
					panels[r][c].setPreferredSize(new Dimension(chopwidth, chopheight));
				}
			}


		for (int r=0; r < cols; r++)
			for(int c=0 ; c < rows; c++)
					display_panel.add(panels[r][c], BorderLayout.CENTER);

		contentPane.add(display_panel, BorderLayout.CENTER);
		contentPane.add(menuBar, BorderLayout.NORTH);
		for (int c=0; c<cols; c++)
			for(int r=0; r<rows; r++)
				panels[r][c] = setPanelClick(panels[r][c]);
		frame.setVisible(true);
	}

	public JMyPanel loadImage(JMyPanel[][] panels, Boolean completed)
	{
		GridLayout gridLayout = new GridLayout(rows,cols);
		JMyPanel display_panel = new JMyPanel(1,1);
		display_panel.setBackground(new Color(0x333333));
		display_panel.setPreferredSize(new Dimension(pictureDimension,pictureDimension));
		display_panel.setLayout(gridLayout);

		BufferedImage fullimg = null;
		try
		{
    		fullimg = ImageIO.read(new File(imagePath));
		}
		catch (IOException e) {}

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
                panels[r][c] = new JMyPanel(r, c);
                panels[r][c].setBackground(new Color(0x333333));
                panels[r][c].correctC = c;
                panels[r][c].correctR = r;
			}

			Random rand = new Random();
			boolean[][] positions = new boolean[rows][cols];

			//set all positions to false
	        for (int x = 0; x<rows; x++)
	            for (int y = 0; y<cols; y++)
	                positions[x][y] = false;


			for (int c=0; c<cols; c++)
				for(int r=0; r<rows; r++)
				{
	            	int randomRow = rand.nextInt(rows);
	            	int randomCol = rand.nextInt(cols);
	            	while (positions[randomRow][randomCol])
	            	{
	                	randomRow = rand.nextInt(rows);
	               		randomCol = rand.nextInt(cols);
	            	}

                	panels[r][c].setPreferredSize(new Dimension(chopwidth, chopheight));

                	if(positions[randomRow][randomCol] == false)
                	{
                		if (!completed)
                		{
							panels[r][c].add(chopLabel[randomRow][randomCol]);
						}
						else
						{
							panels[r][c].add(chopLabel[r][c]);
						}

	                    positions[randomRow][randomCol] = true;
						panels[r][c].setinitialC(randomCol);
						panels[r][c].setinitialR(randomRow);
						panels[r][c].setcol(randomCol);
						panels[r][c].setrow(randomRow);
	                }
	            }

		for (int c=0; c < cols; c++)
			for(int r=0 ; r < rows; r++)
				if( !(r == rows-1 && c==cols-1))
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
					imagePath = chooser.getSelectedFile().getAbsolutePath();
					resetImage(false);
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
				resetImage(false);
			}
		});

		return btnInc;
	}

	JGradientButton setBtnShuffleClick(JGradientButton btnShuffle)
	{
		btnShuffle.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				resetImage(false);
				display_panel.remove(panels[1][1]);
				display_panel.add(panels[1][1], BorderLayout.CENTER);

			}
		});

		return btnShuffle;
	}

		JGradientButton setBtnCompletedClick(JGradientButton btnCompleted)
	{
		btnCompleted.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				resetImage(true);
			}
		});

		return btnCompleted;
	}

	JGradientButton setBtnDecClick(JGradientButton btnDec)
	{
		btnDec.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(rows > 3 && cols > 3)
				{
					rows--;
					cols--;
					resetImage(false);
					//apply shuffle
				}
				else if(rows==3 && cols ==3)
					JOptionPane.showMessageDialog(frame, "Already on easiest difficulty");

			}
		});

		return btnDec;
	}

	boolean checkComplete()
	{
        boolean temp = true;
        for (int c=0; c<cols; c++)
            for(int r=0; r<rows; r++)
                    if ((r != panels[r][c].correctR) || (c != panels[r][c].correctC))
                    {
                        return false;
                	}

        return true;
    }

	JMyPanel setPanelClick(final JMyPanel panel){
            final int panelCol = panel.col();
			final int panelRow = panel.row();
			final int initialC = panel.initialC();
			final int initialR = panel.initialR();
    panel.addMouseListener(new MouseAdapter()
    {
        public void mousePressed(MouseEvent e)
        {
        	JMyPanel tempPanel;
			if ((blankr == panelRow))
			{
				if (blankc == panelCol + 1)
				{

					tempPanel = new JMyPanel(panelRow, panelCol);

					blankc = blankc - 1;
					tempPanel = panels[panelRow][panelCol];
					panels[panelRow][panelCol] = panels[panelRow][panelCol+1];
					panels[panelRow][panelCol+1] = tempPanel;

				}
				else if (blankc == panelCol - 1)
				{

					tempPanel = new JMyPanel(panelRow, panelCol);

					blankc = blankc + 1;
					tempPanel = panels[panelRow][panelCol];
					panels[panelRow][panelCol] = panels[panelRow][panelCol-1];
					panels[panelRow][panelCol-1] = tempPanel;
				}
			}
			else if (blankc == panelCol)
			{
				if (blankr == panelRow + 1)
				{

					tempPanel = new JMyPanel(panelRow, panelCol);

					blankr = blankr - 1;
					tempPanel = panels[panelRow][panelCol];
					panels[panelRow][panelCol] = panels[panelRow+1][panelCol];
					panels[panelRow+1][panelCol] = tempPanel;
				}
				else if (blankr == panelRow - 1)
				{

					tempPanel = new JMyPanel(panelRow, panelCol);

					blankr = blankr + 1;
					tempPanel = panels[panelRow][panelCol];
					panels[panelRow][panelCol] = panels[panelRow-1][panelCol];
					panels[panelRow-1][panelCol] = tempPanel;
				}
			}


			redraw();


			//if (checkComplete())
            //	JOptionPane.showMessageDialog(frame, "You've won!");
        }
    });

    return panel;
	}


}
