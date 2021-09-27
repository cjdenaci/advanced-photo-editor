**SUPPORTED COMMANDS FOR GUI:**

There are buttons for the LOAD and SAVE commands, as well as an EXIT button. All other commands must be accessed via menu. A full list detailing each command can be found below. Note that if a command such as LOAD is used before a layer has been created, the program will throw an exception designating that a layer must be created first. Any of these exceptions are detailed in the list.

* load full [filename]
  * loads a layered project with given filename (add its extension). fyi if this is ran in the middle of the program, it'll replace everything.
* create layer [name]
  * used to create a layer with given name containing no information currently
* current [name]
  * switches the layer being currently modified to one of the specified name
* load [filename]
  * loads a specific file (add its extension) into the current layer, can't be used when no layers have been created
* save [filename]
  * saves topmost layer with given filename (add an extension)
* save full [filename]
  * saves layered project with given filename (do not add an extension)
* filter [filtertype]
  * performs filter operation of the specified type on the current layer, can't be used when no layers have been created. types available are blur and sharpen.
* transformation [transformationtype]
  * performs transformation operation of the specified type on the current layer, can't be used when no layers have been created. types available are sepia and monochrome
* adjustment [adjustmenttype]
  * performs adjustment operation of the specified type on the current layer, can't be used when no layers have been created. types available are downscale and mosaic.
* visible
  * makes current layer visible
* invisible
  * makes current layer invisible, and switches it to a visible layer if possible