package model;

import view.MusicOrganizerWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SaveStrategySerialize implements SaveStrategy, Serializable {

    private MusicOrganizerWindow object;
    private Album root;

    public SaveStrategySerialize(MusicOrganizerWindow object, Album root) {
        this.object = object;
        this.root = root;
        this.root.setView(this.object);
    }

    @Override
    public void save(File selectedFile) {
        try{
            FileOutputStream fs = new FileOutputStream(selectedFile);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(root);
            os.close();
            fs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
