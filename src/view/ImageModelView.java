package view;

import java.io.IOException;

/**
 * Interface for the image model view.
 */
public interface ImageModelView {

  /**
   * Render a specific error message to the provided data destination.
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;
}
