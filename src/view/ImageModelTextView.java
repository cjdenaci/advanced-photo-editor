package view;

import java.io.IOException;
import model.ImageModel;

/**
 * Representing a simple text visualization of an Image Model's state.
 */
public class ImageModelTextView implements ImageModelView {
  ImageModel model;
  Appendable ap;

  /**
   * Constructs a ImageModelTextView to represent the model's state.
   *
   * @param model     the model of the image
   */
  public ImageModelTextView(ImageModel model) {
    this.model = model;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.ap.append(message);
  }
}
