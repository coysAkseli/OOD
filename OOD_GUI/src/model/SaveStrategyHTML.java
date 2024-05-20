package model;

import javafx.scene.control.TreeView;
import view.MusicOrganizerWindow;
import controller.MusicOrganizerController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class SaveStrategyHTML implements SaveStrategy {

    private MusicOrganizerController controller;

    public SaveStrategyHTML(MusicOrganizerController controller) {
        this.controller = controller;
    }

    public String paragraph(SoundClip clip) {
        return "<p>" + clip.toString() + "</p>\n";
    }

    public StringBuilder loopThrough(Album album, StringBuilder str) {

        for (Album a : album.getSubAlbums()) {
            str.append("<h3>").append(a.toString()).append("</h3>\n");
            for (SoundClip sc : a.getSoundClips()) {
                str.append(paragraph(sc));
            }
            loopThrough(a, str);
        }

        return str;
    }

    @Override
    public void save(File file) {
        Album album = controller.getRootAlbum();
        StringBuilder str = new StringBuilder("<h3>All Sound Clips</h3>\n");

        for (SoundClip clip : album.getSoundClips()) {
            str.append(paragraph(clip));
        }

        loopThrough(album, str);


        String theWholeAssHTMLFile = String.format(
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
            bw.write(theWholeAssHTMLFile);
            bw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
