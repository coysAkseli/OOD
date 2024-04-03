import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Album {

    private String name;
    private Album parentAlbum;
    private ArrayList<Album> subAlbums;
    private ArrayList<File> soundClips;

    public Album(String albumName) {
        this.name = albumName;
        this.subAlbums = new ArrayList<>();
        this.soundClips = new ArrayList<>();
        this.parentAlbum = null;
    }

    public Album(String albumName, Album parentAlbum) {
        this.name = albumName;
        this.parentAlbum = parentAlbum;
        this.subAlbums = new ArrayList<>();
        this.soundClips = new ArrayList<>();
    }

    public ArrayList<Album> getSubAlbums() {
        return subAlbums;
    }

    public Album getParentAlbum() {
        return parentAlbum;
    }

    public String getName() {
        return name;
    }

    public ArrayList<File> getSoundClips() {
        return soundClips;
    }
}