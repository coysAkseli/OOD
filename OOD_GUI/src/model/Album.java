package model;


import view.Observer;

import java.util.ArrayList;
import java.util.HashSet;

public class Album {
    private String name;
    private Album parentAlbum;
    private HashSet<Album> subAlbums;
    private HashSet<SoundClip> soundClips;

    //private ArrayList<Observer> observers;

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

    // trodde album skulle vara Subjet, kändes mera
    // passligt att det var MusicOrganizerController
    /*
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(subAlbums, soundClips);
        }
    }*/

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

    /*public void createNewSubAlbum(String newAlbumName) {
        Album newAlbum = new Album(newAlbumName, this);
        this.subAlbums.add(newAlbum);
    }*/ //This method is not used

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