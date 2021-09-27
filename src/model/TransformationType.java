package model;

/**
 * Enum representing image color transformations.
 */
public enum TransformationType {
  MONOCHROME, SEPIA;

  /**
   * Accesses an array corresponding with this specific filter.
   *
   * @return a 2-dimensional double array representing the filter.
   */
  public double[][] getArray() {
    double[][] transfArray = new double[3][3];
    switch (this) {
      case MONOCHROME:
        transfArray[0][0] = 0.2126;
        transfArray[1][0] = 0.7152;
        transfArray[2][0] = 0.0722;
        transfArray[0][1] = 0.2126;
        transfArray[1][1] = 0.7152;
        transfArray[2][1] = 0.0722;
        transfArray[0][2] = 0.2126;
        transfArray[1][2] = 0.7152;
        transfArray[2][2] = 0.0722;
        return transfArray;

      case SEPIA:
        transfArray[0][0] = 0.393;
        transfArray[1][0] = 0.769;
        transfArray[2][0] = 0.189;
        transfArray[0][1] = 0.349;
        transfArray[1][1] = 0.686;
        transfArray[2][1] = 0.168;
        transfArray[0][2] = 0.272;
        transfArray[1][2] = 0.534;
        transfArray[2][2] = 0.131;
        return transfArray;

      default:
        throw new IllegalArgumentException("Invalid TransformationType");
    }
  }

}
