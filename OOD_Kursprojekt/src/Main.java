public class Main {
    public static void main(String[] args) {

        MusicOrganizer musicOrganizer = new MusicOrganizer();

        // The root album is created statically
        musicOrganizer.createNewSubAlbum("Music", Album.getRootAlbum());
    }
}