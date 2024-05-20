package view;
	
import java.io.Serializable;
import java.util.*;

import com.sun.source.tree.Tree;
import controller.MusicOrganizerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Album;
import model.SoundClip;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;



public class MusicOrganizerWindow extends Application implements Serializable {
	
	private transient BorderPane bord;
	private transient BorderPane root;
	private static MusicOrganizerController controller;
	private transient TreeItem<Album> rootNode;
	private transient TreeView<Album> tree;
	private transient ButtonPaneHBox buttons;
	private transient MainMenu menuBar;
	private transient SoundClipListView soundClipTable;
	private transient TextArea messages;
	//private Subject album;
	
	public static void main(String[] args) {
		controller = new MusicOrganizerController(0);
		if (args.length == 0) {
			controller.loadSoundClips("C:/jonkler/OOD/OOD_GUI/sample-sound");
		} else if (args.length == 1) {
			controller.loadSoundClips(args[0]);
		} else {
			System.err.println("too many command-line arguments");
			System.exit(0);
		}
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {


		try {
			controller.registerView(this);
			primaryStage.setTitle("Music Organizer");
			
			bord = new BorderPane();

			// Create buttons in the top of the GUI
			buttons = new ButtonPaneHBox(controller, this);
			bord.setTop(buttons);

			// Create the tree in the left of the GUI
			tree = createTreeView();
			bord.setLeft(tree);
			
			// Create the list in the right of the GUI
			soundClipTable = createSoundClipListView();
			bord.setCenter(soundClipTable);
						
			// Create the text area in the bottom of the GUI
			bord.setBottom(createBottomTextArea());

			menuBar = new MainMenu(controller, this, controller.getRootAlbum());

			root = new BorderPane();
			root.setTop(menuBar);
			root.setCenter(bord);

			Scene scene = new Scene(root);

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.sizeToScene();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent arg0) {
					Platform.exit();
					System.exit(0);
					
				}
			});

			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	//skapar strukturen för albumen i guin
	private TreeView<Album> createTreeView(){
		rootNode = new TreeItem<>(controller.getRootAlbum());
		TreeView<Album> v = new TreeView<>(rootNode);
		
		v.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if(e.getClickCount()==2) {
					// This code gets invoked whenever the user double clicks in the TreeView
					Album selectedAlbum = getSelectedAlbum();
					if(selectedAlbum != null) {
						soundClipTable.display(selectedAlbum);
						//controller.openAlbumContentsWindow(selectedAlbum);
					}
				}
			}
		});

		return v;
	}

	public void updateTreeView(Album newRoot) {
		rootNode = new TreeItem<>(newRoot);
		tree.setRoot(rootNode);
		updateTreeItems(rootNode);
	}

	public void updateTreeItems(TreeItem<Album> node) {
		Album album = node.getValue();

		for (Album subAlbum : album.getSubAlbums()) {
			TreeItem<Album> childNode = new TreeItem<>(subAlbum);
			node.getChildren().add(childNode);
			updateTreeItems(childNode);
		}
	}

	// skapar vyn för ljudfilerna
	private SoundClipListView createSoundClipListView() {
		SoundClipListView v = new SoundClipListView();
		v.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		v.display(controller.getRootAlbum());
		
		v.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if(e.getClickCount() == 2) {
					// This code gets invoked whenever the user double clicks in the sound clip table
					controller.playSoundClipsMusicOrganizerWindow();
				}
			}
		});
		
		return v;
	}

	//skapar textfältet nere i mujsicorganizer
	private ScrollPane createBottomTextArea() {
		messages = new TextArea();
		messages.setPrefRowCount(3);
		messages.setWrapText(true);
		messages.prefWidthProperty().bind(bord.widthProperty());
		messages.setEditable(false); // don't allow user to edit this area
		messages.setEditable(false); // don't allow user to edit this area

		// Wrap the TextArea in a ScrollPane, so that the user can scroll the 
		// text area up and down
		ScrollPane sp = new ScrollPane(messages);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		
		return sp;
	}
	
	/**
	 * Displays the message in the text area at the bottom of the GUI
	 * @param message the message to display
	 */
	public void displayMessage(String message) {
		messages.appendText(message + "\n");
	}
	
	public Album getSelectedAlbum() {
		TreeItem<Album> selectedItem = getSelectedTreeItem();
		return selectedItem == null ? null : selectedItem.getValue();
	}

	private TreeItem<Album> getSelectedTreeItem(){
		return tree.getSelectionModel().getSelectedItem();
	}
	
	
	
	/**
	 * Pop up a dialog box prompting the user for a name for a new album.
	 * Returns the name, or null if the user pressed Cancel
	 */
	public String promptForAlbumName() {
		TextInputDialog dialog = new TextInputDialog();

		dialog.setTitle("Enter album name");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter the name for the album");
		Optional<String> result = dialog.showAndWait();

		if(result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}
	
	/**
	 * Return all the sound clips currently selected in the clip table.
	 */
	public List<SoundClip> getSelectedSoundClips(){
		return soundClipTable.getSelectedClips();
	}
	
	
	
	/**
	 * *****************************************************************
	 * Methods to be called in response to events in the Music Organizer
	 * *****************************************************************
	 */	
	
	
	
	/**
	 * Updates the album hierarchy with a new album
	 * @param newAlbum
	 */

	public void onAlbumAdded(Album parent, Album newAlbum){

		TreeItem<Album> root = tree.getRoot();
		TreeItem<Album> parentNode = findAlbumNode(parent, root);

		parentNode.getChildren().add(new TreeItem<>(newAlbum));
		parentNode.setExpanded(true); // automatically expand the parent node in the tree
	}

	public void onAlbumRemoved(Album toRemove){

		TreeItem<Album> root = tree.getRoot();

		TreeItem<Album> nodeToRemove = findAlbumNode(toRemove, root);
		nodeToRemove.getParent().getChildren().remove(nodeToRemove);

	}

	private TreeItem<Album> findAlbumNode(Album albumToFind, TreeItem<Album> root) {

		// recursive method to locate a node that contains a specific album in the TreeView

		if(root.getValue().equals(albumToFind)) {
			return root;
		}

		for(TreeItem<Album> node : root.getChildren()) {
			TreeItem<Album> item = findAlbumNode(albumToFind, node);
			if(item != null)
				return item;
		}

		return null;
	}

	/**
	 * Refreshes the clipTable in response to the event that clips have
	 * been modified in an album
	 */

	public void onClipsUpdated(){
		if (getSelectedAlbum() != null) {
			Album a = getSelectedAlbum();
			soundClipTable.display(a);
		}
		else soundClipTable.display(controller.getRootAlbum());
	}

	public TreeView<Album> getTree() {
		return tree;
	}
}
