import java.util.HashMap;
import java.util.ArrayList;

//An object of this class is a controller that does things
public class MusicOrganizer {

    private Album rootAlbum;
    private HashMap<Album, ArrayList<Album>> albumHierarchy;

    public MusicOrganizer() {
        this.rootAlbum = new Album("All Sound Clips");
        this.albumHierarchy = new HashMap<>();
    }

    public void createNewSubAlbum(String albumName, Album parentAlbum) {
        Album newAlbum = new Album(albumName, parentAlbum);
        ArrayList<Album> subAlbums = albumHierarchy.getOrDefault(parentAlbum, new ArrayList<>());
        subAlbums.add(newAlbum);
        albumHierarchy.put(parentAlbum, subAlbums);
    }

    public void deleteSubAlbum(Album subAlbum) {
        Album parentAlbum = subAlbum.getParentAlbum();
        if (parentAlbum != null) {
            ArrayList<Album> subAlbums = albumHierarchy.get(parentAlbum);
            if (subAlbums != null) {
                subAlbums.remove(subAlbum);
            }
        }
    }

    public void addSoundClip(String fileName) {
    }

    public void deleteSoundClip(String fileName) {
    }
}