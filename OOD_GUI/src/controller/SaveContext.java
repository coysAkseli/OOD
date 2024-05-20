package controller;

import javafx.stage.FileChooser;
import model.SaveStrategy;
import model.SaveStrategyHTML;
import model.SaveStrategySerialize;
import view.MusicOrganizerWindow;

import java.io.File;

public class SaveContext {
    private MusicOrganizerWindow toSave;
    private SaveStrategy strategy;
    private MusicOrganizerController controller;

    public SaveContext(MusicOrganizerWindow toSave, MusicOrganizerController controller) {
        this.toSave = toSave;
        this.controller = controller;
    }

    public void setStrategy(SaveStrategy strategy) {
        this.strategy = strategy;
    }

    public void saveAs() {
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("Serialized (*.ser)", "*.ser");
        FileChooser.ExtensionFilter ex2 = new FileChooser.ExtensionFilter("HTML (*.html)", "*.html");

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Save as");
        fileChooser.setInitialDirectory(new File("C:/jonkler/OOD"));
        fileChooser.getExtensionFilters().addAll(ex1, ex2);

        File selectedFile = fileChooser.showSaveDialog(null);

        //snyggare med switch case kanske
        if (fileChooser.getSelectedExtensionFilter().equals(ex1)) {
            setStrategy(new SaveStrategySerialize(toSave, controller.getRootAlbum()));
        }
        else if (fileChooser.getSelectedExtensionFilter().equals(ex2)) {
            setStrategy((new SaveStrategyHTML(controller)));
        }

        strategy.save(selectedFile);
    }
}
