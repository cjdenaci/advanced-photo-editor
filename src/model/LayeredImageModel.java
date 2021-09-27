package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Representing a multi-layered implementation of ImageModel.
 *
 */
public class LayeredImageModel implements ImageModel {
  private final List<Layer> layers;
  private int currentPosn;

  // Constructor for a new LayeredImageModel from scratch, before any loading/creating
  public LayeredImageModel() {
    this(new ArrayList<Layer>());
  }

  // Constructor for a LayeredImageModel with pre-existing layers
  protected LayeredImageModel(List<Layer> layers) {
    this.layers = layers;
    this.currentPosn = 0;
  }

  public String getName() {
    return layers.get(currentPosn).getName();
  }

  public int getSize() {
    return this.layers.size();
  }

  public boolean isEmpty() {
    return this.layers.isEmpty();
  }

  /**
   * Determines whether or not a layer is empty.
   */
  public boolean layerIsEmpty() {
    if (!this.layers.isEmpty()) {
      return this.layers.get(currentPosn).isEmpty();
    }
    return true;
  }

  /**
   * Switches the current layer to the given layer name.
   *
   * @param name    Representing which layer is to be set as current.
   */
  public void current(String name) throws IllegalArgumentException {
    for (int i = 0; i < layers.size(); i++) {
      if (layers.get(i).getName().equals(name)) {
        currentPosn = i;
        return;
      }
    }
    throw new IllegalArgumentException("No layer with such name exists.");
  }

  /**
   * Adds a new empty layer to this layered model.
   *
   * @param name    Representing the name of this new layer.
   */
  public void newLayer(String name) throws IllegalArgumentException {
    for (int i = 0; i < layers.size(); i++) {
      if (layers.get(i).getName() == name) {
        throw new IllegalArgumentException("A layer already exists with the given name.");
      }
    }
    this.layers.add(new Layer(name));
  }

  /**
   * Sets this layer to be a given image.
   *
   *@param image  Representing the image to import into this layer.
   */
  public void loadToLayer(SingleImageModel image) throws IllegalStateException {
    if (layers.isEmpty()) {
      throw new IllegalStateException("There is no current layer.");
    }
    layers.get(currentPosn).addImage(image);
  }

  /**
   * Makes the current layer invisible, and switches to a visible one if possible.
   *
   */
  public void invisible() throws IllegalStateException {
    if (layers.get(currentPosn).isEmpty()) {
      throw new IllegalStateException("This layer is currently empty.");
    }
    Layer curLayer = layers.get(currentPosn);
    // If this layer is already invisible, do nothing
    if (!curLayer.isVisible()) {
      throw new IllegalStateException("This layer is already invisible.");
    }
    // If there is a visible layer somewhere else
    // Switch to the first visible layer possible
    else {
      curLayer.flipVisibility();
      for (int i = currentPosn; i > -1; i--) {
        if (layers.get(i).isVisible()) {
          currentPosn = i;
          return;
        }
      }
      for (int i = currentPosn; i < layers.size(); i++) {
        if (layers.get(i).isVisible()) {
          currentPosn = i;
          return;
        }
      }
    }
  }

  /**
   * Makes the current layer visible.
   *
   */
  public void visible() throws IllegalStateException {
    if (layers.get(currentPosn).isEmpty()) {
      throw new IllegalStateException("This layer is currently empty.");
    }
    Layer curLayer = layers.get(currentPosn);
    if (curLayer.isVisible()) {
      return;
    }
    else {
      curLayer.flipVisibility();
    }
  }

  /**
   * Applies a filter to the top layer of this model.
   *
   * @param filter  FilterType representing the specific type of filter to be applied
   */
  @Override
  public void applyFilter(FilterType filter) throws IllegalStateException {
    if (layers.get(currentPosn).isEmpty()) {
      throw new IllegalStateException("This layer is currently empty.");
    }
    layers.get(currentPosn).applyFilter(filter);
  }

  /**
   * Applies a color transformation to the top layer of this model.
   *
   * @param transf  TransformationType representing the specific type of transformation
   *                to be applied
   */
  @Override
  public void applyTransformation(TransformationType transf) throws IllegalStateException {
    if (layers.get(currentPosn) == null) {
      throw new IllegalStateException("This layer is currently empty.");
    }
    layers.get(currentPosn).applyTransformation(transf);
  }

  @Override
  public void applyDownscale(int widthNew, int heightNew) {
    if (layers.get(currentPosn) == null) {
      throw new IllegalStateException("This layer is currently empty.");
    }
    layers.get(currentPosn).applyDownscale(widthNew, heightNew);
  }

  @Override
  public void applyMosaic(int seed) {
    if (layers.get(currentPosn) == null) {
      throw new IllegalStateException("This layer is currently empty.");
    }
    layers.get(currentPosn).applyMosaic(seed);
  }

  /**
   * Creates a file from the top layer of this model.
   *
   * @param filename  String representing the name of the file that the image should be saved to
   * @param fileType  Type of file which the image should be saved to
   */
  @Override
  public void export(String filename, FileType fileType) throws IllegalStateException, IOException {
    if (layers.get(currentPosn) == null) {
      throw new IllegalStateException("This layer is currently empty.");
    }
    layers.get(currentPosn).export(filename, fileType);
  }

  /**
   * Creates a BufferedImage for display purposes.
   */
  public BufferedImage exportDISPLAY() throws IllegalStateException {
    if (layers.get(currentPosn) == null || layers.isEmpty()) {
      throw new IllegalStateException("This layer is currently empty.");
    }
    return layers.get(currentPosn).exportDISPLAY();
  }

  /**
   * Creates a file from all the layers of this model.
   *
   */
  public void exportFullProject(String referenceName) throws IOException {
    FileWriter fw = new FileWriter(referenceName + "LayeredProject.txt");
    for (int i = 0; i < layers.size(); i++) {
      fw.write(layers.get(i).stringOutput(layers.get(i).getName()));
      layers.get(i).export(layers.get(i).getName() + ".png", FileType.PNG);
    }
    fw.flush();
  }

  /**
   * Getter method for the color of a pixel at a specified point in the top layer.
   *
   * @param x  int representing the x-index of the pixel
   * @param y  int representing the y-index of the pixel
   * @return
   */
  @Override
  public Color getColorAt(int x, int y) {
    return layers.get(currentPosn).getColorAt(x, y);
  }
}
