package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Representing an array-based implementation of ImageModel.
 */
public class SingleImageModel implements ImageModel {

  private int width;
  private int height;
  private final int maxValue;
  private Pixel[][] pixelGrid;

  protected SingleImageModel(int width, int height, int maxValue, Pixel[][] pixelGrid) {
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.pixelGrid = pixelGrid;
  }

  /**
   * Getter method for the color of a pixel at a specified point.
   *
   * @param x int representing the x-index of the pixel
   * @param y int representing the y-index of the pixel
   * @return
   */
  public Color getColorAt(int x, int y) {
    Pixel nonCopy = pixelGrid[x][y];
    // Pixel's Color field is final so this may not be necessary
    return new Color(nonCopy.getRed(), nonCopy.getGreen(), nonCopy.getBlue());
  }

  /**
   * Applies a filter to this image.
   *
   * @param filter FilterType representing the specific type of filter to be applied
   */
  public void applyFilter(FilterType filter) {
    // Initializes an array for each of the 3 color channels
    int[][] oldReds = new int[width][height];
    int[][] oldGreens = new int[width][height];
    int[][] oldBlues = new int[width][height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        oldReds[j][i] = pixelGrid[j][i].getRed();
        oldGreens[j][i] = pixelGrid[j][i].getGreen();
        oldBlues[j][i] = pixelGrid[j][i].getBlue();
      }
    }
    // Applies the kernel to each individual channel
    int[][] newReds = applyKernel(filter, oldReds);
    int[][] newGreens = applyKernel(filter, oldGreens);
    int[][] newBlues = applyKernel(filter, oldBlues);
    // Mixes the channels back into one uniform set of colors
    Pixel[][] transformedPixels = new Pixel[width][height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        transformedPixels[j][i] = new Pixel(new Color(newReds[j][i],
            newGreens[j][i], newBlues[j][i]), new Point(j, i));
      }
    }
    this.pixelGrid = transformedPixels;
  }

  // Applies a kernel to all pixels within a channel
  private int[][] applyKernel(FilterType filter, int[][] oldValues) {
    double[][] fArray = filter.getArray();
    int n = fArray.length;
    int loopn = (n - 1) / 2;
    // Initializes a new array to later return
    int[][] newValues = new int[width][height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // Accumulator for each new position
        double acc = 0;
        for (int k = 0; k < n; k++) {
          for (int l = 0; l < n; l++) {
            // Math allows us to overlay each element and add the result
            if (l - loopn + j >= 0 && l - loopn + j <= width - 1
                && k - loopn + i >= 0 && k - loopn + i <= height - 1) {
              acc += oldValues[l - loopn + j][k - loopn + i] * fArray[l][k];
            }
          }
        }
        newValues[j][i] = minMaxRound(acc);
      }
    }
    return newValues;
  }

  /**
   * Applies a color transformation to this image.
   *
   * @param transf TransformationType representing the specific type of transformation to be
   *               applied
   */
  public void applyTransformation(TransformationType transf) {
    double[][] tArray = transf.getArray();
    Pixel[][] transformedPixels = new Pixel[width][height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // Get each of the old color values for a given index
        int oldR = pixelGrid[j][i].getRed();
        int oldG = pixelGrid[j][i].getGreen();
        int oldB = pixelGrid[j][i].getBlue();
        // Calculate the new values using matrix multiplication
        double dR = oldR * tArray[0][0] + oldG * tArray[1][0] + oldB * tArray[2][0];
        double dG = oldR * tArray[0][1] + oldG * tArray[1][1] + oldB * tArray[2][1];
        double dB = oldR * tArray[0][2] + oldG * tArray[1][2] + oldB * tArray[2][2];
        int r = minMaxRound(dR);
        int g = minMaxRound(dG);
        int b = minMaxRound(dB);
        // Use the new colors
        transformedPixels[j][i] = new Pixel(new Color(r, g, b), new Point(j, i));
      }
    }
    this.pixelGrid = transformedPixels;
  }

  /**
   * Creates a downscale of this image.
   *
   * @param widthNew     the new width
   * @param heightNew    the new height
   */
  public void applyDownscale(int widthNew, int heightNew) {
    Pixel[][] downsizedImage = new Pixel[widthNew][heightNew];
    // Loops through every pixel in the new image
    for (int i = 0; i < heightNew; i++) {
      for (int j = 0; j < widthNew; j++) {
        double xMap = ((double) (width * j)) / widthNew;
        double yMap = ((double) (height * i)) / heightNew;
        int xMapFloor = (int) Math.floor(xMap);
        int xMapCeil = (int) Math.ceil(xMap);
        int yMapFloor = (int) Math.floor(yMap);
        int yMapCeil = (int) Math.ceil(yMap);
        if (xMap == xMapFloor || yMap == yMapFloor) {
          downsizedImage[j][i] = new Pixel(pixelGrid[(int) xMap][(int) yMap].getColor(),
              new Point(j, i));
        }
        // STARTS FLOATING POINT
        else {
          ArrayList<Pixel> neighbors = new ArrayList<Pixel>();
          neighbors.add(pixelGrid[xMapFloor][yMapFloor]);
          neighbors.add(pixelGrid[xMapCeil][yMapFloor]);
          neighbors.add(pixelGrid[xMapFloor][yMapCeil]);
          neighbors.add(pixelGrid[xMapCeil][yMapCeil]);
          ArrayList<Integer> redVals = new ArrayList<Integer>();
          ArrayList<Integer> greenVals = new ArrayList<Integer>();
          ArrayList<Integer> blueVals = new ArrayList<Integer>();
          for (Pixel p : neighbors) {
            redVals.add(p.getRed());
            greenVals.add(p.getGreen());
            blueVals.add(p.getBlue());
          }
          downsizedImage[j][i] = new Pixel(new Color(downsizingColor(redVals, xMap, yMap, xMapFloor,
              xMapCeil, yMapFloor, yMapCeil), downsizingColor(greenVals, xMap, yMap, xMapFloor,
              xMapCeil, yMapFloor, yMapCeil), downsizingColor(blueVals, xMap, yMap, xMapFloor,
              xMapCeil, yMapFloor, yMapCeil)), new Point(j, i));
        }
        // ENDS FLOATING POINTS
      }
    }
    this.width = widthNew;
    this.height = heightNew;
    pixelGrid = downsizedImage;
  }

  /**
   * Creates a mosaic of this image, similar to a stained-glass image.
   *
   * @param seedNum int representing the number of seeds, or tiles, in this mosaic.
   */
  public void applyMosaic(int seedNum) {
    // Fills intGrid[][] with all 0's
    int[][] seedArray = new int[width][height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        seedArray[j][i] = 0;
      }
    }
    // Gets a list of all of the ints representing indices of seeds
    ArrayList<Integer> seedInts = new ArrayList<Integer>();
    int area = width * height;
    Random rand = new Random();
    for (int i = 0; i < seedNum; i++) {
      // Recursive helper method safeguards against duplicates
      seedInts.add(getRandomSeed(area, seedInts, rand));
    }
    // At this point, seedInts should contain (n = seedNum) elements, all unique
    Collections.sort(seedInts);

    // Now the seeds get assigned a value (1, 2, ..., seedNum) in seedArray
    // All other elements remain 0, and positions of seeds are recorded for later use
    ArrayList<Point> seedPoints = new ArrayList<Point>();
    int seedAcc = 1;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (seedInts.size() > 0) {
          if (seedInts.get(0) == j + (i * width)) {
            seedArray[j][i] = seedAcc;
            seedAcc++;
            seedInts.remove(0);
            seedPoints.add(new Point(j, i));
          }
        }
      }
    }
    // clusterArray differs from seedArray in that every 0-value from seedArray will be
    // replaced with an integer representing the closest seed to that former 0-value
    int[][] clusterArray = new int[width][height];
    // This method actually carries out the sorting of indices into different clusters,
    // mutating the clusterArray parameter
    clusterSeeds(clusterArray, seedArray, seedPoints);
    // Then, this method takes in the abstract representation of clusters and applies
    // it to the actual pixels within the original image
    pixelGrid = clustersToPixels(clusterArray, seedNum);
  }

  // Returns a random int in the bounds of the supplied area that isn't already
  // present within the supplied list of Points
  private Integer getRandomSeed(int area, ArrayList<Integer> seedInts, Random rand) {
    Integer newInt = rand.nextInt(area);
    if (seedInts.contains(newInt)) {
      return getRandomSeed(area, seedInts, rand);
    } else {
      return newInt;
    }
  }

  // Phase 2: Assign every formerly 0-value to a seed by making a mapped list of distances
  //          and finding the closest distance. Then assign the former 0-value to the index of
  //          the lowest distance that comes first in the array
  // * * We end with a 2D-array where every index contains an int 1, 2, ..., n representing
  // * * clusters called clusterArray[][]
  private void clusterSeeds(int[][] clusterArray, int[][] seedArray, ArrayList<Point> seedPoints) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // Loops through each seed to calculate distance between the 0-val (j, i) and the seed
        if (seedArray[j][i] == 0) {
          HashMap<Double, Point> hashDist = new HashMap<Double, Point>();
          for (Point p : seedPoints) {
            double xDelta = p.getX() - j;
            double yDelta = p.getY() - i;
            double dist = Math.hypot(xDelta, yDelta);
            if (hashDist.size() == 0) {
              hashDist.put(dist, p);
            } else {
              double oldDist = 0;
              for (Double d : hashDist.keySet()) {
                oldDist = d;
              }
              if (dist < oldDist) {
                hashDist.remove(oldDist);
                hashDist.put(dist, p);
              }
            }
          }
          Point shortPt = null;
          for (Point p : hashDist.values()) {
            shortPt = p;
          }
          clusterArray[j][i] = seedArray[shortPt.x][shortPt.y];
        } else {
          clusterArray[j][i] = seedArray[j][i];
        }
      }
    }
  }

  // Phase 3: Find the average RGB values of every pixel within a cluster, and assign them to
  //          a final Color[][] array where every pixel in that cluster has the averaged values
  // * * We end with a 2D-array representing our final image, which we can then reassign
  // * * pixelGrid to
  private Pixel[][] clustersToPixels(int[][] clusterArray, int seedNum) {
    Pixel[][] mosaicGrid = new Pixel[width][height];
    // Loops through each cluster, referenced by the int
    for (int i = 1; i <= seedNum; i++) {
      int numOfThisCluster = 0;
      int accRed = 0;
      int accGreen = 0;
      int accBlue = 0;
      for (int j = 0; j < height; j++) {
        for (int k = 0; k < width; k++) {
          if (clusterArray[k][j] == i) {
            numOfThisCluster++;
            accRed += pixelGrid[k][j].getRed();
            accGreen += pixelGrid[k][j].getGreen();
            accBlue += pixelGrid[k][j].getBlue();
          }
        }
      }
      int avgRed = Math.round(accRed / numOfThisCluster);
      int avgGreen = Math.round(accGreen / numOfThisCluster);
      int avgBlue = Math.round(accBlue / numOfThisCluster);
      Color avgCol = new Color(avgRed, avgGreen, avgBlue);
      for (int j = 0; j < height; j++) {
        for (int k = 0; k < width; k++) {
          if (clusterArray[k][j] == i) {
            mosaicGrid[k][j] = new Pixel(avgCol, new Point(k, j));
          }
        }
      }
    }
    return mosaicGrid;
  }

  private int downsizingColor(ArrayList<Integer> colorInts, double x, double y, int xFloor,
      int xCeil,
      int yFloor, int yCeil) {
    double m = colorInts.get(1) * (x - xFloor) + colorInts.get(0) * (xCeil - x);
    double n = colorInts.get(3) * (x - xFloor) + colorInts.get(2) * (xCeil - x);
    return (int) Math.round(n * (y - yFloor) + m * (yCeil - y));
  }

  // Converts the given double into an appropriate int to represent a color value
  private int minMaxRound(double d) {
    if (d < 0) {
      return 0;
    } else if (d > maxValue) {
      return maxValue;
    }
    // If 0 < d < maxVal
    else {
      return (int) Math.round(d);
    }
  }

  /**
   * Creates a file from this image.
   *
   * @param filename String representing the name of the file that this image should be saved to
   * @param fileType Type of file which this image should be saved to
   */
  public void export(String filename, FileType fileType) throws IOException {
    if (fileType == FileType.PNG || fileType == FileType.JPEG) {
      exportJPEGPNG(filename, fileType);
    } else {
      FileWriter fw = new FileWriter(filename);
      fw.write("P3" + "\n");
      fw.write(width + " " + height);
      fw.write("\n" + maxValue);
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          fw.write("\n" + pixelGrid[j][i].getRed());
          fw.write("\n" + pixelGrid[j][i].getGreen());
          fw.write("\n" + pixelGrid[j][i].getBlue());
        }
      }
      fw.flush();
    }
  }

  private void exportJPEGPNG(String filename, FileType fileType) throws IOException {
    BufferedImage exportImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgb = getColorAt(j, i).getRGB();
        exportImg.setRGB(j, i, rgb);
      }
    }
    File file = new File(filename);
    ImageIO.write(exportImg, fileType.toString(), file);
  }

  /**
   * Exports the display for the GUI.
   */
  public BufferedImage exportDISPLAY() {
    BufferedImage exportImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgb = getColorAt(j, i).getRGB();
        exportImg.setRGB(j, i, rgb);
      }
    }
    return exportImg;
  }

}
