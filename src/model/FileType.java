package model;

/**
 * Representing the file types utilized in the image processing program.
 */
public enum FileType {
  JPEG, PNG, PPM;

  /**
   * Returns the valid enum.
   * @param str    representing the string input.
   */
  public static FileType getEnum(String str) {
    if (str.equalsIgnoreCase(".jpeg")) {
      return FileType.JPEG;
    }
    else if (str.equalsIgnoreCase(".png")) {
      return FileType.PNG;
    }
    else if (str.equalsIgnoreCase(".ppm")) {
      return FileType.PPM;
    }
    else {
      throw new IllegalArgumentException("Invalid file type: " + str);
    }
  }

}
