package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * Representing a way to create instances of an ImageModel.
 */
public abstract class ImageModelCreator {

  /**
   * Creates a new ImageModel based on the .png or .jpeg file with the given filename.
   *
   * @param filename    String representing the filename
   * @return an ImageModel representing what the user wanted to create
   */
  public static LayeredImageModel createLayeredProject(String filename)
      throws IllegalArgumentException {
    return new LayeredImageModel();
  }

  /**
   * Creates a new ImageModel based on the .png or .jpeg file with the given filename.
   *
   * @param filename    String representing the filename
   * @return an ImageModel representing what the user wanted to create
   */
  // TODO: Loads the layered project, dependent on the design of the loaded layers
  public static LayeredImageModel loadLayeredProject(String filename)
      throws FileNotFoundException, IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Null Parameters");
    }
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (IOException e) {
      throw new FileNotFoundException("Cannot find file");
    }
    ArrayList<Layer> layers = new ArrayList<Layer>();
    StringBuilder sb = new StringBuilder();
    if (sc.hasNextLine()) {
      // Processes the blank first line
      String throwaway = sc.nextLine();
    }
    while (sc.hasNextLine()) {
      String fileName = sc.nextLine();
      String layerName = sc.nextLine();
      try {
        Scanner thisFileScan = new Scanner(new FileInputStream(fileName));
      }
      catch (IOException e) {
        throw new FileNotFoundException("Cannot find layer file");
      }
      layers.add(new Layer((loadJPEGPNG(fileName)), layerName));
    }
    return new LayeredImageModel(layers);
  }

  /**
   * Creates a new ImageModel based on the .png or .jpeg file with the given filename.
   *
   * @param filename    String representing the filename
   * @return an ImageModel representing what the user wanted to create
   */
  public static SingleImageModel load(FileType fileType, String filename)
      throws FileNotFoundException, IllegalArgumentException {
    if (fileType == null || filename == null) {
      throw new IllegalArgumentException("Null method parameters");
    }
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (IOException e) {
      throw new FileNotFoundException("File " + filename + " not found!");
    }
    switch (fileType) {
      case JPEG: return loadJPEGPNG(filename);
      case PNG: return loadJPEGPNG(filename);
      case PPM: return loadPPM(filename);
      default: throw new IllegalArgumentException("Invalid FileType");
    }
  }

  // Loads the .jpeg or .png file with the given filename
  private static SingleImageModel loadJPEGPNG(String filename)
      throws FileNotFoundException {
    File file = new File(filename);
    BufferedImage image;
    try {
      image = ImageIO.read(file);
    }
    catch (IOException e) {
      throw new FileNotFoundException("Cannot find file: " + filename);
    }
    int width = image.getWidth();
    int height = image.getHeight();
    Pixel[][] pixelArray = new Pixel[width][height];

    List<Integer> maxFinder = new ArrayList<Integer>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Color pix = new Color(image.getRGB(j, i));
        int r = pix.getRed();
        int g = pix.getGreen();
        int b = pix.getBlue();
        pixelArray[j][i] = new Pixel(new Color(r, g, b), new Point(j, i));
        maxFinder.add(r);
        maxFinder.add(g);
        maxFinder.add(b);
      }
    }

    int maxValue = Collections.max(maxFinder);
    return new SingleImageModel(width, height, maxValue, pixelArray);
  }

  // Loads the .ppm file with the given filename
  private static SingleImageModel loadPPM(String filename) throws FileNotFoundException,
      IllegalArgumentException {
    Scanner sc;
    sc = new Scanner(new FileInputStream(filename));
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    sc = new Scanner(builder.toString());
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    List<List<Pixel>> pixelGrid = new ArrayList<List<Pixel>>();
    // Initializes a list representing every x-index
    for (int i = 0; i < width; i++) {
      pixelGrid.add(new ArrayList<Pixel>());
    }
    // Fills the corresponding x-index lists with pixels
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixelGrid.get(j).add(new Pixel(new Color(r, g, b), new Point(j, i)));
      }
    }
    Pixel[][] pixelArray = new Pixel[width][height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixelArray[j][i] = pixelGrid.get(j).get(i);
      }
    }
    return new SingleImageModel(width, height, maxValue, pixelArray);
  }

  /**
   * Creates a new Checkerboard-type ImageModel based on the inputted specifications.
   *
   * @param numTiles   int representing the number of tiles on each axis
   * @param tileSize   int representing the size of each tile in pixel width
   * @param col1       Color representing a primary color for the checkerboard
   * @param col2       Color representing a secondary color for the checkerboard
   * @return an ImageModel representing what the user wanted to create
   */
  public static SingleImageModel drawCheckerboard(int numTiles, int tileSize, Color col1,
      Color col2) {
    // Get the max value
    int maxValue = Math.max(col1.getRed(), Math.max(col1.getGreen(), Math.max(col1.getBlue(),
        Math.max(col2.getRed(), Math.max(col2.getGreen(), col2.getBlue())))));
    //in the checkboard, width = height
    int width = numTiles * tileSize;
    Pixel[][] pixelArray = new Pixel[width][width];
    boolean tileSwitcher = false;
    for (int i = 0; i < numTiles; i++) { // HAS BEARING ON Y-AXIS
      for (int j = 0; j < tileSize; j++) { // HAS BEARING ON Y-AXIS, Y = (i * tileSize) + j
        for (int k = 0; k < numTiles; k++) { // HAS BEARING ON X-AXIS
          for (int l = 0; l < tileSize; l++) { // HAS BEARING ON X-AXIS, X = (k * tileSize) + l
            Color pixCol;
            int x = ((k * tileSize) + l);
            int y = ((i * tileSize) + j);
            if (tileSwitcher) {
              pixCol = col2;
            }
            else {
              pixCol = col1;
            }
            pixelArray[x][y] = new Pixel(pixCol, new Point(x, y));
          }
          // End of an l-loop, switches color
          tileSwitcher = !tileSwitcher;
        }
      }
      // End of an i-loop, switches to even/odd row pattern
      if (numTiles % 2 == 0) {
        tileSwitcher = !tileSwitcher;
      }
    }
    return new SingleImageModel(width, width, maxValue, pixelArray);
  }
}