package view;

import controller.MusicOrganizerController;
import controller.SaveContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.SaveStrategySerialize;
import model.SoundClip;

import java.io.*;


public class MainMenu extends MenuBar {

    private MusicOrganizerController controller;
    private MusicOrganizerWindow view;
    private Menu fileMenu;
    private MenuItem saveAs;

    //constructor
    public MainMenu(MusicOrganizerController controller, MusicOrganizerWindow view) {
        super();
        this.controller = controller;
        this.view = view;

        fileMenu = createFileMenu();
        this.getMenus().add(fileMenu);
    }

    //skapar menyn
    private Menu createFileMenu() {

        //skapar "File" menu
        fileMenu = new Menu("File");

        //skapar menu items
        MenuItem loadHierarchy = new MenuItem("Load Hierarchy");
        MenuItem saveAs = new MenuItem("Save as");


        //då man trycker på Load Hierarchy
        loadHierarchy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Load hierarchy");
                //fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));

                File selectedFile = fileChooser.showOpenDialog(null);

                if (selectedFile != null) {
                    System.out.println("File selected: " + selectedFile.getAbsolutePath());
                }
                else System.out.println("User did not select a file");

                try {
                    Album newRoot = null;
                    FileInputStream fi = new FileInputStream(selectedFile);
                    ObjectInputStream oi = new ObjectInputStream(fi);
                    Object obj = oi.readObject();
                    newRoot = (Album) obj;
                    fi.close();
                    oi.close();

                    System.out.println("fileinputstream");

                    //controller.setRootAlbum(newRoot);
                    view.updateTreeView(newRoot);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //då man trycker på save as
        saveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                System.out.println("Saving as...");

                SaveContext saveContext = new SaveContext(controller);
                saveContext.saveAs();
            }
        });

        fileMenu.getItems().add(saveAs);
        fileMenu.getItems().add(loadHierarchy);

        return fileMenu;
    }
}
