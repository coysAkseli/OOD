package model;


import java.util.HashSet;

public class Album {
    private String name;
    private Album parentAlbum;
    private HashSet<Album> subAlbums;
    private HashSet<SoundClip> soundClips;

    public Album(String albumName) {
        this.name = albumName;
        this.subAlbums = new HashSet<>();
        this.soundClips = new HashSet<>();
        this.parentAlbum = null;
    }

    public Album(String albumName, Album parentAlbum) {
        this.name = albumName;
        this.parentAlbum = parentAlbum;
        parentAlbum.getSubAlbums().add(this);
        this.subAlbums = new HashSet<>();
        this.soundClips = new HashSet<>();
    }

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
    }*/ //prolly not needed

    public void deleteSubAlbum(Album subAlbum) {
        this.getSubAlbums().remove(subAlbum);
    }

    public void addSoundClip(SoundClip file) {
  
        Album album = parentAlbum;
        //while (album.getParentAlbum() != null) {
        while (album != null) {
            album.getSoundClips().add(file);
            //soundClips.add(file);
            album = album.getParentAlbum();
        }
        //album.getSoundClips().add(file);
        soundClips.add(file);


        
    }

    public void deleteSoundClip(SoundClip file) {
        this.getSoundClips().remove(file);

        for (Album x : this.subAlbums) {
            if (x.containsSoundClip(file)) {
                x.deleteSoundClip(file);
            }
        }
    }

    public boolean containsSubAlbum(Album albumToFind) {
        return this.getSubAlbums().contains(albumToFind);
    }

    public boolean containsSoundClip(SoundClip fileToFind) {
        return this.getSoundClips().contains(fileToFind);
    }
}