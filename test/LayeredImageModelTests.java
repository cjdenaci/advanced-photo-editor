package test;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.io.FileNotFoundException;
import model.FileType;
import model.ImageModelCreator;
import model.LayeredImageModel;
import model.SingleImageModel;
import org.junit.Test;

/**
 * Representing tests for the LayeredImageModel implementation of ImageModel.
 */
public class LayeredImageModelTests {

  static SingleImageModel readModelSadie;
  static SingleImageModel readModelAnthony;
  static SingleImageModel drawModel;
  static LayeredImageModel catDog;

  private static void initLayeredModel() throws FileNotFoundException {
    initReadModel();
    initDrawModel();
    catDog = ImageModelCreator.loadLayeredProject("catdogLayeredProject.txt");
    catDog.newLayer("checkerboard");
    catDog.current("checkerboard");
    catDog.loadToLayer(drawModel);
  }

  private static void initReadModel() {
    try {
      readModelSadie = ImageModelCreator.load(FileType.PNG, "sadie.png");
      readModelAnthony = ImageModelCreator.load(FileType.PNG, "anthony.png");
    }
    catch (FileNotFoundException e) {
      System.out.println("Couldn't find designated files");
    }
  }

  private static void initDrawModel() {
    drawModel = ImageModelCreator.drawCheckerboard(3, 1, Color.CYAN, Color.PINK);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidCurrent() throws FileNotFoundException {
    initLayeredModel();
    this.catDog.current("turkey");
  }

  @Test (expected = IllegalArgumentException.class)
  public void testInvalidNewLayer() throws FileNotFoundException {
    initLayeredModel();
    this.catDog.newLayer("checkerboard");
  }

  @Test (expected = IllegalStateException.class)
  public void testInvalidInvisible() throws FileNotFoundException {
    initLayeredModel();
    this.catDog.newLayer("null");
    this.catDog.current("null");
    this.catDog.invisible();
  }

  @Test (expected = IllegalStateException.class)
  public void testInvalidVisible() throws FileNotFoundException {
    initLayeredModel();
    this.catDog.newLayer("null");
    this.catDog.current("null");
    this.catDog.visible();
  }

  //ensures the loadLayeredProject() and loadToLayer() calls from the initializer worked properly
  @Test
  public void testLoading() throws FileNotFoundException {
    initLayeredModel();
    this.catDog.current("checkerboard");
    assertEquals(this.catDog.getColorAt(2, 2), this.drawModel.getColorAt(2, 2));
    this.catDog.current("sadie");
    assertEquals(this.catDog.getColorAt(2, 2), this.readModelSadie.getColorAt(2, 2));
  }
}
