import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AlbumTest {
    @Test
    public void testGetName() {
        Album album = new Album("Album Name"); 
        assertEquals("Album Name", album.getName());
    }

    @Test
    public void testGetParentAlbum() {
        Album parentAlbum = new Album("Parent Album");
        Album subAlbum = new Album("Sub Album", parentAlbum);
        assertEquals(parentAlbum, subAlbum.getParentAlbum());

    }

    @Test
    public void testGetSubAlbums() {
      
    }
}
