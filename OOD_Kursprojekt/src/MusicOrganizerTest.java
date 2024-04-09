import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.io.File;

public class MusicOrganizerTest {
    @Test
    public void testAddSoundClip() {
        MusicOrganizer organizer = new MusicOrganizer();
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        organizer.addSoundClip(organizer.getRootAlbum().getSubAlbums().get(0), new File("SoundClip"));
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSoundClips().size(), 1);
        assertEquals(organizer.getRootAlbum().getSoundClips().size(), 1); // what the heeell
    }

    @Test
    public void testContainsSoundClip() {
        MusicOrganizer organizer = new MusicOrganizer();
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        File file = new File("SoundClip");
        organizer.addSoundClip(organizer.getRootAlbum().getSubAlbums().get(0), file);
        assertEquals(organizer.containsSoundClip(organizer.getRootAlbum().getSubAlbums().get(0), file), true);
    }

    @Test
    public void testContainsSubAlbum() {
        MusicOrganizer organizer = new MusicOrganizer();
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        assertEquals(organizer.containsSubAlbum(organizer.getRootAlbum(), organizer.getRootAlbum().getSubAlbums().get(0)), true);
    }

    @Test
    public void testCreateNewSubAlbum() {
        MusicOrganizer organizer = new MusicOrganizer();
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        organizer.createNewSubAlbum("SubSubAlbum", organizer.getRootAlbum().getSubAlbums().get(0));
        assertEquals(organizer.getRootAlbum().getSubAlbums().size(), 1);
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSubAlbums().size(), 1);
    }

    @Test
    public void testDeleteSoundClip() {
        MusicOrganizer organizer = new MusicOrganizer();
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        organizer.createNewSubAlbum("SubSubAlbum", organizer.getRootAlbum().getSubAlbums().get(0));
        File file = new File("SoundClip");
        organizer.addSoundClip(organizer.getRootAlbum().getSubAlbums().get(0), file);
        organizer.deleteSoundClip(organizer.getRootAlbum().getSubAlbums().get(0), file);
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSoundClips().size(), 0);
        assertEquals(organizer.getRootAlbum().getSoundClips().size(), 1); 
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSubAlbums().get(0).getSoundClips().size(), 0);      
    }

    @Test
    public void testDeleteSubAlbum() {
        MusicOrganizer organizer = new MusicOrganizer();
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        organizer.createNewSubAlbum("SubSubAlbum", organizer.getRootAlbum().getSubAlbums().get(0));
        File file = new File("SoundClip");
        organizer.addSoundClip(organizer.getRootAlbum().getSubAlbums().get(0), file);
        organizer.deleteSubAlbum(organizer.getRootAlbum(), organizer.getRootAlbum().getSubAlbums().get(0));
        assertEquals(organizer.getRootAlbum().getSubAlbums().size(), 0);
        assertEquals(organizer.getRootAlbum().getSoundClips().size(), 1);       
    }

}
