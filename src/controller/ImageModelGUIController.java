package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import model.FileType;
import model.FilterType;
import model.ImageModelCreator;
import model.LayeredImageModel;
import model.TransformationType;
import view.ImageModelGraphicsView;

/**
 * Representing a GUI controller for the image processing program.
 */
public abstract class ImageModelGUIController {

  /**
   * Processes a command and performs its specified operation on the image model.
   *
   * @param model    image model being used
   * @param view     view being used
   * @param input    the command
   * @throws IOException when any error is encountered while other methods are called.
   */
  public static void processCommand(LayeredImageModel model, ImageModelGraphicsView view,
      String input) throws IOException {

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
        String filetype = input.substring(input.indexOf(".", input.indexOf(".") + 1));
        model.loadToLayer(ImageModelCreator.load(FileType.getEnum(filetype), input.substring(5)));
      }
      catch (IOException | IllegalStateException | NullPointerException ie) {
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
    else {
      view.renderMessage("Unknown command %s" + input);
    }

    view.makeInvisible();
    ImageModelGraphicsView newView = new ImageModelGraphicsView(model);
    newView.makeVisible();
  }
}
