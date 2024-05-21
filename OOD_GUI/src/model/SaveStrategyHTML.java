package model;

import controller.MusicOrganizerController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static java.lang.Integer.valueOf;

public class SaveStrategyHTML implements SaveStrategy {

    private MusicOrganizerController controller;

    /**
     * controller behövs för att accessera rootAlbum
     */
    public SaveStrategyHTML(MusicOrganizerController controller) {
        this.controller = controller;
    }

    /**
     * skriver ljudfilerna
     */
    public String writeAlbumContents(SoundClip clip, int padding) {
        return "<p style=\"padding-left: " + padding + "px;\">" + clip.toString() + "</p>\n";
    }

    /**
     * padding inkrementeras och dekrementeras under rekursionen
     */
    public void writeAlbumHeaderAndCallWriteAlbumContents(Album album, StringBuilder str, int padding) {
        for (Album a : album.getSubAlbums()) {
            padding+=40;
            str.append("<h3 style=\"padding-left: " + padding + "px;\">").append(a.toString()).append("</h3>\n");
            for (SoundClip sc : a.getSoundClips()) {
                str.append(writeAlbumContents(sc, padding));
            }
            writeAlbumHeaderAndCallWriteAlbumContents(a, str, padding);
            padding-=40;
        }
    }

    @Override
    /**
     * sparar filen som HTML
     */
    public void save(File file) {
        Album album = controller.getRootAlbum();
        StringBuilder str = new StringBuilder("<h2>All Sound Clips</h2>\n");

        /**
         * padding är padding-left, det gör att man kan lättare se
         * albumhierarkin i HTML dokumentet
         */
        int padding = 0;
        for (SoundClip clip : album.getSoundClips()) {
            str.append(writeAlbumContents(clip, padding));
        }

        writeAlbumHeaderAndCallWriteAlbumContents(album, str, padding);

        //skriver hela html dokumentet mha stirng formatting

        String HTMLFileString = String.format(
            """
                <!DOCTYPE html>
                <html>
                   <head>
                   </head>
                   
                    <body>
                        %s
                    </body>
                    
                </html>
                    """, str);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(HTMLFileString);
            bw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
