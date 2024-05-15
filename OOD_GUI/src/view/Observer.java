package view;
import model.Album;
import model.SoundClip;

import java.util.HashSet;

public interface Observer {
    public void update();

    Album getAlbum();

    void closeWindow();
}
