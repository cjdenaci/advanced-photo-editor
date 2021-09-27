package model;

/**
 * Enum representing image filters.
 */
public enum FilterType {
  BLUR, SHARPEN;

  /**
   * Accesses an array corresponding with this specific filter.
   *
   * @return a 2-dimensional double array representing the filter.
   */
  public double[][] getArray() {
    double[][] filterArray;
    switch (this) {
      case BLUR:
        filterArray = new double[3][3];
        // Top row
        filterArray[0][0] = 0.0625;
        filterArray[1][0] = 0.125;
        filterArray[2][0] = 0.0625;
        // Middle row
        filterArray[0][1] = 0.125;
        filterArray[1][1] = 0.25;
        filterArray[2][1] = 0.125;
        // Bottom row
        filterArray[0][2] = 0.0625;
        filterArray[1][2] = 0.125;
        filterArray[2][2] = 0.0625;
        return filterArray;

      case SHARPEN:
        filterArray = new double[5][5];
        // Top & bottom rows
        for (int i = 0; i < 5; i++) {
          filterArray[i][0] = -0.125;
          filterArray[i][4] = -0.125;
        }
        // 2nd & 4th row, 1st and last elements
        filterArray[0][1] = -0.125;
        filterArray[4][1] = -0.125;
        filterArray[0][3] = -0.125;
        filterArray[4][3] = -0.125;
        // 2nd & 4th row, rest
        for (int i = 1; i < 4; i++) {
          filterArray[i][1] = 0.25;
          filterArray[i][3] = 0.25;
        }
        // 3rd row
        filterArray[0][2] = -0.125;
        filterArray[1][2] = 0.25;
        filterArray[2][2] = 1;
        filterArray[3][2] = 0.25;
        filterArray[4][2] = -0.125;
        return filterArray;

      default: throw new IllegalArgumentException("Invalid FilterType");
    }
  }

}
