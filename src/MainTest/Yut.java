package MainTest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Yut extends JFrame {
	BufferedImage img = null;

	public Yut() {
		setTitle("윷");
		setResizable(false);
		int yut_num = 1 + (int) (Math.random() * 4);
		if (yut_num == 1) { //switch로 하는 것이 깔끔하지 않을까?
			try {
				img = ImageIO.read(new File("source/도.png"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
		if (yut_num == 2) {
			try {
				img = ImageIO.read(new File("source/개.png"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
		if (yut_num == 3) {
			try {
				img = ImageIO.read(new File("source/걸.png"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
		if (yut_num == 4) {
			try {
				img = ImageIO.read(new File("source/윷.png"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
		if (yut_num == 5) {
			try {
				img = ImageIO.read(new File("source/모.png"));
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
		JPanel p = new Yut_Panel();
		add(p);
		pack();
		setVisible(true);

	}

	class Yut_Panel extends JPanel {

		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}

		public Dimension getPreferredSize() {
			if (img == null) {
				return new Dimension(100, 100);
			} else {
				return new Dimension(img.getWidth(null), img.getHeight(null));
			}
		}

	}
}
