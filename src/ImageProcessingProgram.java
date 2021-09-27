import controller.ImageModelScriptController;
import java.io.IOException;
import java.util.Scanner;
import model.LayeredImageModel;
import view.ImageModelGraphicsView;

/**
 * A class to contain the main method in order to run the full application.
 */
public class ImageProcessingProgram {

  /**
   * The main method.
   */
  public static void main(String[] args) throws IOException {
    System.out.println("Which version would you like to run? Your options are:");
    System.out.println("-script [path-of-script-file]");
    System.out.println("-text");
    System.out.println("-interactive");

    Scanner sc = new Scanner(System.in);
    String userInput = sc.nextLine();

    if (userInput.startsWith("-script")) {
      try {
        ImageModelScriptController.run(userInput.substring(userInput.indexOf(" ")));
      }
      catch (IOException e) {
        System.out.println("Couldn't find file.");
      }
    }
    else if (userInput.equals("-text")) {
      ImageModelScriptController.run();
    }
    else if (userInput.equals("-interactive")) {
      LayeredImageModel model = new LayeredImageModel();
      ImageModelGraphicsView view = new ImageModelGraphicsView(model);
      view.makeVisible();
    }
    else {
      System.out.println("That's not on the list. Pay attention next time.");
    }
  }
}
