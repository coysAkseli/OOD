import java.io.File;
import java.util.ArrayList;

public class Album {

    private String name;
    private Album parentAlbum;
    private ArrayList<Album> subAlbums;
    private ArrayList<SoundClip> soundClips;

    //constructor for the root album
    public Album(String albumName) {
        this.name = albumName;
        this.subAlbums = new ArrayList<>();
        this.soundClips = new ArrayList<>();
        this.parentAlbum = null;
    }

    //constructor for albums that are not the root album
    public Album(String albumName, Album parentAlbum) {
        this.name = albumName;
        this.parentAlbum = parentAlbum;
        this.subAlbums = new ArrayList<>();
        this.soundClips = new ArrayList<>();
    }
    //getters
    public ArrayList<Album> getSubAlbums() {
        return subAlbums;
    }

    public Album getParentAlbum() {
        return parentAlbum;
    }

    public String getName() {
        return name;
    }

    public ArrayList<SoundClip> getSoundClips() {
        return soundClips;
    }
}