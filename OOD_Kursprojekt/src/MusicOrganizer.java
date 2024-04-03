import java.io.File;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.ArrayList;

//An object of this class is a controller that does things
public class MusicOrganizer {

    private Album rootAlbum;
    private HashMap<Album, ArrayList<Album>> albumHierarchyAlbums;
    private HashMap<Album, ArrayList<File>> albumHierarchySoundClips;

    public MusicOrganizer() {
        this.rootAlbum = new Album("All Sound Clips");
        this.albumHierarchyAlbums = new HashMap<>();
        this.albumHierarchySoundClips = new HashMap<>();
    }

    // creates a new sub album to the album parentAlbum

    public void createNewSubAlbum(String albumName, Album parentAlbum) {
        Album newAlbum = new Album(albumName, parentAlbum);
        albumHierarchyAlbums.put(parentAlbum, new ArrayList<Album>());
        albumHierarchySoundClips.put(parentAlbum, new ArrayList<File>());
    }

    // deletes the subalbum key and thus it's value arraylist and the reference to the
    // arraylist to the subalbum from the parent album
    public void deleteSubAlbum(Album subAlbum, Album parentAlbum) {
        albumHierarchyAlbums.remove(subAlbum);
        albumHierarchyAlbums.get(parentAlbum).remove(parentAlbum);
    }

    // sound clip is added to album and every album in the hierarchy up until root album
    public void addSoundClip(File file, Album parentAlbum) {

        // this while loop adds the file to every album in the hierarchy
        while (notAtRootAlbum(parentAlbum)) {
            albumHierarchySoundClips.get(parentAlbum).add(file);
            parentAlbum = parentAlbum.getParentAlbum();
        }
    }

    public void deleteSoundClip(File file, Album parentAlbum) {

        while (notAtRootAlbum(parentAlbum)) {
            albumHierarchySoundClips.get(parentAlbum).remove(file);
        }
    }

    public boolean notAtRootAlbum(Album parentAlbum) {
        return parentAlbum.getParentAlbum() != null;
    }
}