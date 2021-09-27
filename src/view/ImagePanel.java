package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Representing an image panel for the GUI display.
 */
public class ImagePanel extends JPanel {
  private BufferedImage image;

  public ImagePanel(BufferedImage image) {
    this.image = image;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(image.getScaledInstance(-1, getHeight(), Image.SCALE_SMOOTH), 0, 0,
        this);
  }
}
