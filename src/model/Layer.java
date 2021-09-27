package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Representing a Layer utilized by LayeredImageModel.
 */
public class Layer implements ImageModel {

  private SingleImageModel image;
  private final String name;
  private boolean visible;

  protected Layer(SingleImageModel image, String name) {
    this.image = image;
    this.name = name;
    this.visible = true;
  }

  protected String getName() {
    return name;
  }

  protected Layer(String name) {
    this(null, name);
  }

  // Returns whether this layer is visible.
  protected boolean isVisible() {
    return visible;
  }

  // Flips this layer from visible -> invisible, or invisible -> visible.
  protected void flipVisibility() {
    visible = !visible;
  }

  // Returns whether this is an empty layer.
  protected boolean isEmpty() {
    return image == null;
  }

  // Adds or replaces this image's layer.
  protected void addImage(SingleImageModel newImage) {
    image = newImage;
  }

  // Applies a filter to this layer's image.
  @Override
  public void applyFilter(FilterType filter) {
    image.applyFilter(filter);
  }

  // Applies a transformation to this layer's image.
  @Override
  public void applyTransformation(TransformationType transf) {
    image.applyTransformation(transf);
  }

  // Applies a downscale to this layer's image.
  @Override
  public void applyDownscale(int widthNew, int heightNew) {
    image.applyDownscale(widthNew, heightNew);
  }

  // Applies a mosaic to this layer's image.
  @Override
  public void applyMosaic(int seed) {
    image.applyMosaic(seed);
  }

  // Exports this layer's image.
  @Override
  public void export(String filename, FileType fileType) throws IOException {
    image.export(filename, fileType);
  }

  public BufferedImage exportDISPLAY() {
    return image.exportDISPLAY();
  }

  // Gets the Color at the provided position in this layer's image.
  @Override
  public Color getColorAt(int x, int y) {
    return image.getColorAt(x, y);
  }

  protected String stringOutput(String filename) {
    return "\n" + filename + ".png" + "\n" + name;
  }

}
