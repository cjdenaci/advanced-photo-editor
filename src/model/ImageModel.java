package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Representing an image to which filters and transformations may be applied.
 *
 */
public interface ImageModel {

  /**
   * Applies a filter to this image.
   *
   * @param filter  FilterType representing the specific type of filter to be applied
   */
  void applyFilter(FilterType filter);

  /**
   * Applies a color transformation to this image.
   *
   * @param transf  TransformationType representing the specific type of transformation
   *               to be applied
   */
  void applyTransformation(TransformationType transf);

  /**
   * Applies a downscale adjustment to this image.
   *
   * @param widthNew     the new width
   * @param heightNew    the new height
   */
  void applyDownscale(int widthNew, int heightNew);

  /**
   * Applies a mosaic adjustment to this image.
   *
   * @param seed    the specified seed
   */
  void applyMosaic(int seed);

  /**
   * Creates a file from this image.
   *
   * @param filename  String representing the name of the file that this image should be saved to
   */
  void export(String filename, FileType fileType) throws IOException;

  /**
   * Creates a BufferedImage from this image for display purposes.
   * @return
   */
  BufferedImage exportDISPLAY();

  /**
   * Getter method for the color of a pixel at a specified point.
   *
   * @param x  int representing the x-index of the pixel
   * @param y  int representing the y-index of the pixel
   * @return
   */
  Color getColorAt(int x, int y);
}
