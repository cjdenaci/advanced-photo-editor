package model;

import java.awt.Color;
import java.awt.Point;

/**
 * Representing a pixel in an image.
 */
public class Pixel {
  private final Color color;
  private final Point position;

  protected Pixel(Color color, Point position) {
    this.color = color;
    this.position = position;
  }

  /**
   * Returns the red value of this pixel.
   *
   * @return an int representing the red value of this pixel.
   */
  public int getRed() {
    return color.getRed();
  }

  /**
   * Returns the green value of this pixel.
   *
   * @return an int representing the green value of this pixel.
   */
  public int getGreen() {
    return color.getGreen();
  }

  /**
   * Returns the blue value of this pixel.
   *
   * @return an int representing the green value of this pixel.
   */
  public int getBlue() {
    return color.getBlue();
  }

  /**
   * Returns point in space that this pixel occupies.
   *
   * @return a Point representing this pixel's position
   */
  public Point getPosition() {
    return position;
  }

  /**
   * Returns color.
   *
   * @return color
   */
  public Color getColor() {
    return new Color(getRed(), getGreen(), getBlue());
  }
}
