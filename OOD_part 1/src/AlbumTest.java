import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.io.File;


//Testar Album-klassen och alla dess metoder
public class AlbumTest {
    @Test
    public void testGetName() {
        Album album = new Album("Test Album");
        assertEquals(album.getName(), "Test Album");
    }

    @Test
    public void testGetParentAlbum() {
        Album parentAlbum = new Album("Parent Album");
        Album SubAlbum = new Album("SubAlbum", parentAlbum);
        Album SubSubAlbum = new Album("SubSubAlbum", SubAlbum);
        assertEquals(SubAlbum.getParentAlbum(), parentAlbum);
        assertEquals(SubSubAlbum.getParentAlbum(), SubAlbum);

    }

    @Test
    public void testGetSoundClips() {
        Album album = new Album("Test Album");
        assertEquals(album.getSoundClips().size(), 0);
        album.getSoundClips().add(new SoundClip(new File("SoundClip")));
        assertEquals(album.getSoundClips().size(), 1);
    }

    @Test
    public void testGetSubAlbums() {
        Album album = new Album("Test Album");
        assertEquals(album.getSubAlbums().size(), 0);
        album.getSubAlbums().add(new Album("SubAlbum", album));
        assertEquals(album.getSubAlbums().size(), 1);
    }
}
