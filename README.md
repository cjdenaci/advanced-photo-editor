**<u>A+ Image Processor v3.0</u>**

*Aidan Johansson*

*Christopher Denaci*

In v3.0, a new controller ImageModelGUIController and a new view ImageModelGraphicsView were added to support the new interactive iteration of the software: the GUI. This new view class utilizes Java Swing in order to construct and update the visualization for the user's convenience, and the new controller class merely reads and processes the inputs given from the user and through the GUI. No changes had to be made to prior pieces of code in order to implement this correctly.

The application now supports ADJUSTMENTS, which consist of downscaling the image and "mosaicing" the image. The previous script and text iterations of this program were updated in order to support this new type of editing images, and the new GUI iteration obviously supports it as well. This required the creation of the new methods applyDownscale() and applyMosaic() in the ImageModel interface, but no other radical changes were made.

The main method can be found in the class ImageProcessingProgram, which asks the user whether they want the script, textual or interactive version of our image processor. Based on the input, the proper iteration of the software is executed.
Note that the desired input is exactly as communicated by the program, with the "-" included

Our test class from the previous assignment can still be found in test/, but it will only run if it has access to the proper files that it calls.

-Mosaic-
The bulk of the code involved for the mosaic effect is in the SingleImageModel class. In LayeredImageModel, the mosaic effect is applied to the respective layer.

-Downsize-
Similarly to mosaic, the actual pixel processing occurs in SingleImageModel, which is then called in LayeredImageModel.

*The photographs "sadie.png," "jake.jpeg," and "anthony.png" were supplied by programmers Aidan Johansson and Christopher Denaci respectively, both of whom authorize their uses in the project.*