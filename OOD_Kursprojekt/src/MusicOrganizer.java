import java.io.File;
import java.util.ArrayList;

//Ett objekt av den här klassen är en kontroller i princip
public class MusicOrganizer {

    private Album rootAlbum; //idk

    //constructor
    public MusicOrganizer() {
        this.rootAlbum = new Album("All Sound Clips");
    }

    //skapar ett nytt subalbum med namned newAlbumName till föräldra-albumet parentAlbum
    public void createNewSubAlbum(String newAlbumName, Album parentAlbum) {
        Album newAlbum = new Album(newAlbumName, parentAlbum);
        parentAlbum.getSubAlbums().add(newAlbum);
    }

    // tar bort referensen från föräldraalbumet
    public void deleteSubAlbum(Album parentAlbum, Album subAlbum) {
        parentAlbum.getSubAlbums().remove(subAlbum);
    }

    //sound clip läggs först till albumet album, sedan till alla föräldraalbum uppåt i hierarkin inkl. rotalbum
    public void addSoundClip(Album album, File file) {

        while (album.getParentAlbum() != null) {
            album.getSoundClips().add(file);
            album = album.getParentAlbum();
        }
        album.getSoundClips().add(file);
    }

    // deletes sound clip from sub albums as well, NOT FROM PARENT ALBUMS
    // tar bort ljudfil från alla subalbum neråt i hierarkin, TAR INTE BORT FILEN FRÅN PARENT ALBUMS
    public void deleteSoundClip(Album album, File file) {
        album.getSoundClips().remove(file);

        for (Album x : album.getSubAlbums()) {
            if (containsSoundClip(x, file)) {
                deleteSoundClip(x, file);
            }
        }
    }

    //kollar om ett subalbum albumToFind finns i albumet album
    public boolean containsSubAlbum(Album album, Album albumToFind) {
        return album.getSubAlbums().contains(albumToFind);
    }

    //kollar om en ljudfil fileToFind finns i albumet album
    public boolean containsSoundClip(Album album, File fileToFind) {
        return album.getSoundClips().contains(fileToFind);
    }

    // visar ett albums subalbum och ljudfiler
    // då GUI skapas kommer implementationen vara mera strukturerad och snygg
    public void showContents(Album album) {
        for (Album x : album.getSubAlbums()) {
            System.out.println(x.getName());
        }

        System.out.println("-----------------");

        for (File x : album.getSoundClips())
            System.out.println(x.getName());
    }

    public Album getRootAlbum() {
        return rootAlbum;
    }
}