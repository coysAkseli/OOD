import org.junit.Test;
import static org.junit.Assert.assertEquals;

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
    }

    @Test
    public void testGetSubAlbums() {
        Album album = new Album("Test Album");
        assertEquals(album.getSubAlbums().size(), 0);
    }
}
