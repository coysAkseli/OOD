package model;

import java.io.Serializable;
import java.util.HashSet;

public class Album implements Serializable {
    private String name;
    private Album parentAlbum;
    private HashSet<Album> subAlbums;
    private HashSet<SoundClip> soundClips;

    // Constructor for rootalbum
    public Album(String albumName) {
        this.name = albumName;
        this.subAlbums = new HashSet<>();
        this.soundClips = new HashSet<>();
        this.parentAlbum = null;
        //this.observers = new ArrayList<Observer>();
    }

    // Constructor for sub album
    public Album(String albumName, Album parentAlbum) {
        this.name = albumName;
        this.parentAlbum = parentAlbum;
        parentAlbum.getSubAlbums().add(this);
        this.subAlbums = new HashSet<Album>();
        this.soundClips = new HashSet<>();
        //this.observers = new ArrayList<Observer>();
    }

    // Getters
    public HashSet<Album> getSubAlbums() {
        return subAlbums;
    }

    public Album getParentAlbum() {
        return parentAlbum;
    }

    public String toString() {
        return name;
    }

    public HashSet<SoundClip> getSoundClips() {
        return soundClips;
    }

    // Delete sub album from the album
    public void deleteSubAlbum(Album subAlbum) {
        this.getSubAlbums().remove(subAlbum);
    }

    // Add sound clip to the album
    public void addSoundClip(SoundClip file) {
  
        Album album = parentAlbum;
        while (album != null) {
            album.getSoundClips().add(file);
            album = album.getParentAlbum();
        }
        soundClips.add(file);
    }

    // Delete sound clip from the album
    public void deleteSoundClip(SoundClip file) {
        this.getSoundClips().remove(file);

        for (Album x : this.subAlbums) {
            if (x.containsSoundClip(file)) {
                x.deleteSoundClip(file);
            }
        }
    }
    // Check if the album contains a sub album
    public boolean containsSubAlbum(Album albumToFind) {
        return this.getSubAlbums().contains(albumToFind);
    }
    // Check if the album contains a sound clip
    public boolean containsSoundClip(SoundClip fileToFind) {
        return this.getSoundClips().contains(fileToFind);
    }
}