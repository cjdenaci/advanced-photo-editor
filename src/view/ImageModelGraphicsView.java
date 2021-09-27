package view;

import controller.ImageModelGUIController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.LayeredImageModel;

/**
 * Representing a GUI visualization of an Image Model's state.
 */
public class ImageModelGraphicsView extends JFrame implements ImageModelView {
  private ImagePanel imagePanel;
  private String input;

  /**
   * Constructs an ImageModelGraphicsView to represent the model's state.
   *
   * @param m     the model of the image
   */
  public ImageModelGraphicsView(LayeredImageModel m) throws IOException {
    super();

    // declare all variables
    JPanel buttonPanel;
    JButton loadButton;
    JButton saveButton;
    JButton quitButton;

    JMenuBar menuBar;

    JMenu fileMenu;
    JMenuItem load;
    JMenuItem loadAll;
    JMenuItem save;
    JMenuItem saveAll;

    JMenu layerMenu;
    JMenuItem newLayer;
    JMenuItem setCurrentLayer;
    JMenuItem visible;
    JMenuItem invisible;

    JMenu filterMenu;
    JMenuItem blur;
    JMenuItem sharpen;

    JMenu transformationMenu;
    JMenuItem monochrome;
    JMenuItem sepia;

    JMenu adjustmentMenu;
    JMenuItem downscale;
    JMenuItem mosaic;

    // initial setup
    LayeredImageModel model = m;
    this.setTitle("A+ Image Processor");
    this.setSize(750, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // border layout
    this.setLayout(new BorderLayout());
    if (!model.isEmpty() && !model.layerIsEmpty()) {
      imagePanel = new ImagePanel(model.exportDISPLAY());
    }
    else {
      imagePanel = new ImagePanel(new BufferedImage(750, 500,
          BufferedImage.TYPE_INT_RGB));
    }
    imagePanel.setPreferredSize(new Dimension(750, 500));
    this.add(imagePanel, BorderLayout.CENTER);

    // button panel
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    this.add(buttonPanel, BorderLayout.SOUTH);
    loadButton = new JButton("Load");
    loadButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Which file would you like to load?");
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          input = "load " + chooser.getSelectedFile().toString();
        }
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    saveButton = new JButton("Save");
    saveButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "save " + JOptionPane.showInputDialog("What would you like to name your file?");
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    quitButton = new JButton("Quit");
    quitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
    buttonPanel.add(loadButton);
    buttonPanel.add(saveButton);
    buttonPanel.add(quitButton);

    // file dropdown
    fileMenu = new JMenu("File");
    load = new JMenuItem("Load");
    load.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Which file would you like to load?");
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          input = "load " + chooser.getSelectedFile().toString();
        }
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    loadAll = new JMenuItem("Load All");
    loadAll.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Which project would you like to load?");
        int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          input = "load full " + chooser.getSelectedFile().toString();
        }
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    save = new JMenuItem("Save");
    save.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "save " + JOptionPane.showInputDialog("What would you like to name your file?");
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    saveAll = new JMenuItem("Save All");
    saveAll.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "save full " + JOptionPane.showInputDialog("What would you like to name your "
            + "project?");
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    fileMenu.add(load);
    fileMenu.add(loadAll);
    fileMenu.add(save);
    fileMenu.add(saveAll);

    // layer dropdown
    layerMenu = new JMenu("Layer");
    newLayer = new JMenuItem("New Layer");
    newLayer.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "create layer " + JOptionPane.showInputDialog("What would you like to name your "
            + "layer?");
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    setCurrentLayer = new JMenuItem("Set Current Layer");
    setCurrentLayer.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "current " + JOptionPane.showInputDialog("What would you like the current layer to "
            + "be?");
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    visible = new JMenuItem("Set Visible");
    visible.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "visible";
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    invisible = new JMenuItem("Set Invisible");
    invisible.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "invisible";
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    layerMenu.add(newLayer);
    layerMenu.add(setCurrentLayer);
    layerMenu.add(visible);
    layerMenu.add(invisible);

    // filter dropdown
    filterMenu = new JMenu("Filter");
    blur = new JMenuItem("Blur");
    blur.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "filter blur";
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    sharpen = new JMenuItem("Sharpen");
    sharpen.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "filter sharpen";
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    filterMenu.add(blur);
    filterMenu.add(sharpen);

    // transformation dropdown
    transformationMenu = new JMenu("Transformation");
    monochrome = new JMenuItem("Monochrome");
    monochrome.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "transformation monochrome";
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    sepia = new JMenuItem("Sepia");
    sepia.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "transformation sepia";
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    transformationMenu.add(monochrome);
    transformationMenu.add(sepia);

    // adjustment dropdown
    adjustmentMenu = new JMenu("Adjustment");
    downscale = new JMenuItem("Downscale");
    downscale.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "adjustment downscale "
            + JOptionPane.showInputDialog("Enter in the new width, followed by &, then height.");
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    mosaic = new JMenuItem("Mosaic");
    mosaic.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        input = "adjustment mosaic " + JOptionPane.showInputDialog("Enter in the seed.");
        try {
          ImageModelGUIController.processCommand(model, ImageModelGraphicsView.this, input);
        }
        catch (IOException ioException) {
          ioException.printStackTrace();
        }
      }
    });
    adjustmentMenu.add(downscale);
    adjustmentMenu.add(mosaic);

    // initialize menu bar
    menuBar = new JMenuBar();
    menuBar.add(fileMenu);
    menuBar.add(layerMenu);
    menuBar.add(filterMenu);
    menuBar.add(transformationMenu);
    menuBar.add(adjustmentMenu);
    this.setJMenuBar(menuBar);

    this.pack();
  }

  /**
   * Sets the GUI as visible to the user.
   */
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Sets the GUI as invisible to the user.
   */
  public void makeInvisible() {
    this.setVisible(false);
  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error",
        JOptionPane.ERROR_MESSAGE);
  }
}
