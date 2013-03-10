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

public class JMyPanel extends JPanel
{
	private int currentPosC, currentPosR;
	public int initialC, initialR;
	public int correctC, correctR;

	public JMyPanel(int row, int col)
	{
		currentPosR = row;
		currentPosC = col;
	}

	public int row()
	{
		return currentPosR;
	}

	public int col() {
		return currentPosC;
		}

	public int initialC() {
		return initialC;
		}

	public int initialR() {
		return initialR;
		}

	void setinitialC(int ix) {
		initialC = ix;
		}

	void setinitialR(int iy) {
		initialR = iy;
		}

	void setrow(int iy) {
		currentPosR = iy;
		}

	void setcol(int ix) {
		currentPosC = ix;
	}


	}