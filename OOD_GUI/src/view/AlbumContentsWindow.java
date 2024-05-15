package view;

import java.util.HashSet;
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
import model.Subject;


public class AlbumContentsWindow extends Stage implements Observer {

    private BorderPane bord;
    private SoundClipListView soundClipTable;
    private Subject controller;
    private Album album;

    private final AlbumContentsWindow albumContentsWindow;

    //constructor
    public AlbumContentsWindow(Subject controller, Album album) {

        this.controller = controller;
        this.album = album;
        soundClipTable = createSoundClipListView();
        albumContentsWindow = this;
        this.controller.registerObserver(this);

        setTitle(album.toString());
        this.show();
    }

    public void update() {
        onClipsUpdated();
    }

    //skapar vyn med ljudfilerna i det nya fönstret
    private SoundClipListView createSoundClipListView() {
        SoundClipListView v = new SoundClipListView();
        v.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        v.display((Album) album);

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

    //stänger fönstret och tar bort det som observer
    public void closeWindow() {
        controller.removeObserver(this);
        this.close();
    }

    //getter för album
    public Album getAlbum() {
        return album;
    }

    /**
     *     uppdaterar vyn för albumfönstret som är uppe
     */
    public void onClipsUpdated() {
        soundClipTable.display(album);
    }
}