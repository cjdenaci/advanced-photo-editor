package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import model.FileType;
import model.FilterType;
import model.ImageModelCreator;
import model.LayeredImageModel;
import model.TransformationType;
import view.ImageModelTextView;
import view.ImageModelView;

/**
 * Representing a script controller for the image processing program.
 */
public abstract class ImageModelScriptController {

  /**
   * Calls the command processor so that the user inputs commands interactively.
   */
  public static void run() throws IOException {
    System.out.println("Only one command per line:"
        + "\nload full [filename] // create layer [name] // current [name] // load [filename] // "
        + "save [filename]\nsave full [filename] // filter [filtertype] // "
        + "transformation [transformationtype] // adjustment downscale [width]&[height] // "
        + "adjustment mosaic [seed] // visible // invisible // exit");
    processCommands(new Scanner(System.in));
  }

  /**
   * Calls the command processor so that the user imports a file containing the commands.
   *
   * @param filename    name of file with commands
   * @throws FileNotFoundException if a file with the designated filename is not found.
   */
  public static void run(String filename) throws IOException {
    try {
      processCommands(new Scanner(ImageModelScriptController.class.getResourceAsStream(
          "/res/" + filename)));
    }
    catch (FileNotFoundException fnfe) {
      throw new FileNotFoundException("File " + filename + " not found!");
    }
  }

  /**
   * Processes the commands and performs its specified operations on the image model.
   *
   * @param sc    scanner being used
   * @throws IOException when any error is encountered while other methods are called.
   */
  private static void processCommands(Scanner sc) throws IOException {
    LayeredImageModel model = new LayeredImageModel();
    ImageModelView view = new ImageModelTextView(model);
    String input;

    while (sc.hasNextLine()) {
      input = sc.nextLine();
      if (input.startsWith("load full")) {
        try {
          model = ImageModelCreator.loadLayeredProject(input.substring(9));
        }
        catch (FileNotFoundException | IllegalArgumentException | NullPointerException fnfe) {
          view.renderMessage(fnfe.getMessage());
        }
      }
      else if (input.startsWith("create layer")) {
        try {
          model.newLayer(input.substring(13));
        }
        catch (IllegalArgumentException | NullPointerException iae) {
          view.renderMessage(iae.getMessage());
        }
      }
      else if (input.startsWith("current")) {
        try {
          model.current(input.substring(8));
        }
        catch (IllegalArgumentException | NullPointerException iae) {
          view.renderMessage(iae.getMessage());
        }
      }
      else if (input.startsWith("load")) {
        try {
          String filetype = input.substring(input.indexOf("."));
          model.loadToLayer(ImageModelCreator.load(FileType.getEnum(filetype), input.substring(5)));
        }
        catch (IOException | NullPointerException ie) {
          view.renderMessage(ie.getMessage());
        }
      }
      else if (input.startsWith("save full")) {
        try {
          model.exportFullProject(input.substring(9));
        }
        catch (NullPointerException npe) {
          view.renderMessage(npe.getMessage());
        }
      }
      else if (input.startsWith("save")) {
        try {
          String filetype = input.substring(input.indexOf("."));
          model.export(input.substring(5), FileType.getEnum(filetype));
        }
        catch (IOException | NullPointerException ie) {
          view.renderMessage(ie.getMessage());
        }
      }
      else if (input.startsWith("filter")) {
        try {
          String filtertype = input.substring(7);
          model.applyFilter(FilterType.valueOf(filtertype.toUpperCase()));
        }
        catch (IllegalArgumentException | NullPointerException iae) {
          view.renderMessage(iae.getMessage());
        }
      }
      else if (input.startsWith("transformation")) {
        try {
          String transformationtype = input.substring(15);
          model.applyTransformation(TransformationType.valueOf(transformationtype.toUpperCase()));
        }
        catch (IllegalArgumentException | NullPointerException iae) {
          view.renderMessage(iae.getMessage());
        }
      }
      else if (input.startsWith("adjustment downscale")) {
        try {
          int width = Integer.parseInt(input.substring(21));
          int height = Integer.parseInt(input.substring(input.indexOf("&")));
          model.applyDownscale(width, height);
        }
        catch (IllegalArgumentException | NullPointerException iae) {
          view.renderMessage(iae.getMessage());
        }
      }
      else if (input.startsWith("adjustment mosaic")) {
        try {
          int seed = Integer.parseInt(input.substring(18));
          model.applyMosaic(seed);
        }
        catch (IllegalArgumentException | NullPointerException iae) {
          view.renderMessage(iae.getMessage());
        }
      }
      else if (input.startsWith("visible")) {
        try {
          model.visible();
        }
        catch (NullPointerException npe) {
          view.renderMessage(npe.getMessage());
        }
      }
      else if (input.startsWith("invisible")) {
        try {
          model.invisible();
        }
        catch (NullPointerException npe) {
          view.renderMessage(npe.getMessage());
        }
      }
      else if (input.startsWith("exit")) {
        return;
      }
      else {
        System.out.println(String.format("Unknown command %s", input));
      }
    }
  }
}
