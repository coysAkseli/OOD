import java.io.File;
import java.util.ArrayList;

public class Album {

    private String name;
    private Album parentAlbum;

    ArrayList<File> soundClips = new ArrayList<>();
    ArrayList<Album> subAlbums = new ArrayList<>();

    public ArrayList<Album> getSubAlbums() {
        return subAlbums;
    }

    public Album getParentAlbum() {
        return parentAlbum;
    }

    public Album(String albumName) {
        this.name = albumName;

    }
}