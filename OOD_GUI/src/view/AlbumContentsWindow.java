package view;

import java.util.List;
import java.util.Optional;

import controller.MusicOrganizerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Album;
import model.SoundClip;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


public class AlbumContentsWindow extends Stage {

    private BorderPane bord;
    private SoundClipListView soundClipTable;
    private final MusicOrganizerController controller;
    private final Album album;
    private final AlbumContentsWindow albumContentsWindow;

    //constructor
    public AlbumContentsWindow(MusicOrganizerController controller, Album album) {

        this.controller = controller;
        this.album = album;
        soundClipTable = createSoundClipListView();
        albumContentsWindow = this;

        setTitle(album.toString());
        this.show();
    }

    //skapar vyn med ljudfilerna i det nya fönstret
    private SoundClipListView createSoundClipListView() {
        SoundClipListView v = new SoundClipListView();
        v.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        v.display(album);

        VBox root = new VBox(v);

        v.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
                System.out.println("Mouse clicked");
                if (e.getClickCount() == 2) {
                    System.out.println("double click");
                    controller.playSoundClipsAlbumWindow(albumContentsWindow);
                }
            }
        });

        Scene scene = new Scene(root,400,300);
        setScene(scene);
        this.show();

        return v;
    }

    // getter för ljudfilen man har tryckt på
    public List<SoundClip> getSelectedSoundClips() {
        return soundClipTable.getSelectedClips();
    }

    //stänger fönstret
    public void closeWindow() {
        this.close();
    }

    //getter för album
    public Album getAlbum() {
        return album;
    }

    // då ljudfilen har förändrat anropas dethär, det uppdaterar alla
    // öppnade fönster som kan tänkas som prenumererare i Observer pattern
    public void onClipsUpdated() {
        soundClipTable.display(album);
    }
}