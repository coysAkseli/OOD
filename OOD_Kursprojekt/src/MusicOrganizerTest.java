import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.io.File;

public class MusicOrganizerTest {
    @Test
    //Skaoar ett nytt subalbum och lägger till en ljudfil i det
    //Kollar att ljudfilen finns i subalbumet och i rootalbumet
    public void testAddSoundClip() {
        MusicOrganizer organizer = new MusicOrganizer();
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        organizer.addSoundClip(organizer.getRootAlbum().getSubAlbums().get(0), new File("SoundClip"));
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSoundClips().size(), 1);
        assertEquals(organizer.getRootAlbum().getSoundClips().size(), 1); 
    }

    @Test
    //Skapar ett nytt subalbum och lägger till en ljudfil i den
    //Kollar att ljudfilen finns i subalbumet
    public void testContainsSoundClip() {
        MusicOrganizer organizer = new MusicOrganizer();
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        File file = new File("SoundClip");
        organizer.addSoundClip(organizer.getRootAlbum().getSubAlbums().get(0), file);
        assertEquals(organizer.containsSoundClip(organizer.getRootAlbum().getSubAlbums().get(0), file), true);
    }

    @Test
    //Skapar ett nytt subalbum och kollar att det finns i rootalbumet
    public void testContainsSubAlbum() {
        MusicOrganizer organizer = new MusicOrganizer();
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        assertEquals(organizer.containsSubAlbum(organizer.getRootAlbum(), organizer.getRootAlbum().getSubAlbums().get(0)), true);
    }

    @Test
    public void testCreateNewSubAlbum() {
        // Skapar ett nytt subalbum och kollar att det finns i rootalbumet
        MusicOrganizer organizer = new MusicOrganizer();
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        assertEquals(organizer.getRootAlbum().getSubAlbums().size(), 1);
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getName(), "SubAlbum");
        // Skapar ett nytt subalbum och kollar att det finns i det första subalbumet
        organizer.createNewSubAlbum("SubSubAlbum", organizer.getRootAlbum().getSubAlbums().get(0));
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSubAlbums().size(), 1);
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSubAlbums().get(0).getName(), "SubSubAlbum");
        // Skapar ett annat subalbum och kollar att det finns också i det första subalbumet
        organizer.createNewSubAlbum("SubSubAlbum2", organizer.getRootAlbum().getSubAlbums().get(0));
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSubAlbums().size(), 2);
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSubAlbums().get(1).getName(), "SubSubAlbum2");
    }

    @Test
    public void testDeleteSoundClip() {
        MusicOrganizer organizer = new MusicOrganizer();
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        organizer.createNewSubAlbum("SubSubAlbum", organizer.getRootAlbum().getSubAlbums().get(0));
        File file = new File("SoundClip");
        File file2 = new File("SoundClip2");
        organizer.addSoundClip(organizer.getRootAlbum().getSubAlbums().get(0), file); //Lägger till ljudfil i SubAlbum
        organizer.addSoundClip(organizer.getRootAlbum().getSubAlbums().get(0).getSubAlbums().get(0), file2); //Lägger till ljudfil i SubSubAlbum
       // Tar bort filen från SubAlbum
        organizer.deleteSoundClip(organizer.getRootAlbum().getSubAlbums().get(0), file);
        // Rotalbumet ska ha 2 filer, SubAlbum ska ha 1 filer och SubSubAlbum ska ha 1 fil
        assertEquals(organizer.getRootAlbum().getSoundClips().size(), 2);
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSoundClips().size(), 1);
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSubAlbums().get(0).getSoundClips().size(), 1);
        // Tar bort filen från SubSubAlbum
        organizer.deleteSoundClip(organizer.getRootAlbum().getSubAlbums().get(0).getSubAlbums().get(0), file2);
        // Rotalbumet ska ha 2 filer, SubAlbum ska ha 1 fil och SubSubAlbum ska ha 0 filer
        assertEquals(organizer.getRootAlbum().getSoundClips().size(), 2);
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSoundClips().size(), 1);
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSubAlbums().get(0).getSoundClips().size(), 0);
        // Tar bort filen från file2 från rootalbumet
        organizer.deleteSoundClip(organizer.getRootAlbum(), file2);
        // Rotalbumet ska ha 1 fil, SubAlbum ska ha 0 filer och SubSubAlbum ska ha 0 filer
        assertEquals(organizer.getRootAlbum().getSoundClips().size(), 1);
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSoundClips().size(), 0);
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSubAlbums().get(0).getSoundClips().size(), 0);
        // Filen i rootalbumet ska vara file
        assertEquals(organizer.getRootAlbum().getSoundClips().get(0), file);
    }

    @Test
    public void testDeleteSubAlbum() {
        MusicOrganizer organizer = new MusicOrganizer();
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        organizer.createNewSubAlbum("SubSubAlbum", organizer.getRootAlbum().getSubAlbums().get(0));
        File file = new File("SoundClip");
        //Skapar 2 subalbum och lägger till en ljudfil i det första subalbumet
        organizer.addSoundClip(organizer.getRootAlbum().getSubAlbums().get(0), file);
        //Tar bort det första subalbumet som innehåller ljudfilen
        organizer.deleteSubAlbum(organizer.getRootAlbum(), organizer.getRootAlbum().getSubAlbums().get(0));
        //Rotalbumet ska ha 0 subalbum och 1 ljudfil
        assertEquals(organizer.getRootAlbum().getSubAlbums().size(), 0);
        assertEquals(organizer.getRootAlbum().getSoundClips().size(), 1);
        //laga tillbaka subalbumet
        organizer.createNewSubAlbum("SubAlbum", organizer.getRootAlbum());
        //SubSubAlbum skall inte finnas kvar
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSubAlbums().size(), 0);
        //Det skall inte finnas någon ljudfil i SubAlbum
        assertEquals(organizer.getRootAlbum().getSubAlbums().get(0).getSoundClips().size(), 0);
        // Det skall inte vara möjligt att ta bort rootalbumet
        organizer.deleteSubAlbum(organizer.getRootAlbum(), organizer.getRootAlbum()); 
        assertEquals(organizer.getRootAlbum().getSubAlbums().size(), 1); 
        // Eftersom testet gårt igenom så har rootalbumet inte tagits bort
          
    }

}
