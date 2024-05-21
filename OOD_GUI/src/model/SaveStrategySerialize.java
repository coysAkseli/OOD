package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SaveStrategySerialize implements SaveStrategy, Serializable {
    private final Album root;

    /**
     * rotalbumet serialiseras. Referenser till subalbum och deras innehål sparas
     * */
    public SaveStrategySerialize(Album root) {
        this.root = root;
    }

    @Override
    /**
     * serialiserar rotAlbum objektet.
     */
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
