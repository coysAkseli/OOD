package view;

import controller.MusicOrganizerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;


public class MainMenu extends MenuBar {

    private MusicOrganizerController controller;
    private MusicOrganizerWindow view;

    private Menu fileMenu;

    public MainMenu(MusicOrganizerController controller, MusicOrganizerWindow view) {
        super();
        this.controller = controller;
        this.view = view;

        fileMenu = createFileMenu();
        this.getMenus().add(fileMenu);
    }

    private Menu createFileMenu() {

        fileMenu = new Menu("File");

        MenuItem loadHierarchy = new MenuItem("Load Hierarchy");

        loadHierarchy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                System.out.println("loading hierarchy...");
            }
        });

        MenuItem saveAs = new MenuItem("Save as");
        saveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                //temp
                System.out.println("Saving as...");
            }
        });

        fileMenu.getItems().add(saveAs);
        fileMenu.getItems().add(loadHierarchy);

        return fileMenu;
    }
}
