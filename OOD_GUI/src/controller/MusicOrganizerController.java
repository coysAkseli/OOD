package controller;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Album;
import model.SoundClip;
import model.SoundClipBlockingQueue;
import model.SoundClipLoader;
import model.SoundClipPlayer;
import view.AlbumContentsWindow;
import view.MusicOrganizerWindow;

public class MusicOrganizerController {

	private MusicOrganizerWindow view;
	private AlbumContentsWindow albumView;
	private SoundClipBlockingQueue queue;
	private Album root;
	private HashSet<AlbumContentsWindow> openWindows; //observers
	
	public MusicOrganizerController() {
		// Create the root album for all sound clips
		root = new Album("All Sound Clips");
		
		// Create the blocking queue
		queue = new SoundClipBlockingQueue();

		openWindows = new HashSet<AlbumContentsWindow>();
				
		// Create a separate thread for the sound clip player and start it
		
		(new Thread(new SoundClipPlayer(queue))).start();
	}
	
	/**
	 * Load the sound clips found in all subfolders of a path on disk. If path is not
	 * an actual folder on disk, has no effect.
	 */
	public Set<SoundClip> loadSoundClips(String path) {
		Set<SoundClip> clips = SoundClipLoader.loadSoundClips(path);
		for (SoundClip clip : clips) {
			root.addSoundClip(clip);
		}
		

		return clips;
	}
	
	public void registerView(MusicOrganizerWindow view) {
		this.view = view;
	}

	public void registerView(AlbumContentsWindow view) {
		this.albumView = view;
	}
	
	/**
	 * Returns the root album
	 */
	public Album getRootAlbum(){
		return root;
	}
	
	/**
	 * Adds an album to the Music Organizer
	 */
	public void addNewAlbum(){ 
		Album parentAlbum = view.getSelectedAlbum();
		if (parentAlbum == null) {
			view.displayMessage("Please select an album to add a new album to.");
			return;
		}
		String albumName = view.promptForAlbumName();
		if (albumName == null) {
			view.displayMessage("Please enter a valid album name.");
			return;
		}
		Album newAlbum = new Album(albumName, parentAlbum);
		view.onAlbumAdded(newAlbum);
		
	}

	public void openAlbumContentsWindow(Album album) {
		AlbumContentsWindow contentsWindow = new AlbumContentsWindow(this, album);
		openWindows.add(contentsWindow);

		//controller.registerView(contentsWindow);
		contentsWindow.show();
	}
	
	/**
	 * Removes an album from the Music Organizer
	 */
	public void deleteAlbum(){
		Album album = view.getSelectedAlbum();
		if (album == null) {
			view.displayMessage("Please select an album to delete.");
			return;
		}
		Album parentAlbum = album.getParentAlbum();
		if (album == root) {
			view.displayMessage("Cannot delete the root album.");
			return;
		}
		parentAlbum.deleteSubAlbum(album);
		view.onAlbumRemoved();

		closeWindowsAfterDeletion(album);
	}

	//closes windows for all deleted albums and subalbums
	public void closeWindowsAfterDeletion(Album album) {

		for (AlbumContentsWindow x : openWindows) {
			if (x.getAlbum().equals(album)) {
				x.closeWindow();
				for (Album a : x.getAlbum().getSubAlbums()) {
					closeWindowsAfterDeletion(a);
				}
			}
		}
	}
	
	/**
	 * Adds sound clips to an album
	 */

	public void addSoundClips(){ 
		Album album = view.getSelectedAlbum();
		if (album == null) {
			view.displayMessage("Please select an album to add sound clips to.");
			return;
		}
		List<SoundClip> clips = view.getSelectedSoundClips();
		Album parentAlbum = album.getParentAlbum();

		for (SoundClip clip : clips) {
			album.addSoundClip(clip);
		}

		// in the MusicOrganizer window
		view.onClipsUpdated();

		// in the open windows
		for (AlbumContentsWindow a : openWindows) {
			a.onClipsUpdated();
		}
		
	}
	
	/**
	 * Removes sound clips from an album
	 */
	public void removeSoundClips(){ 
		Album album = view.getSelectedAlbum();
		if (album == null) {
			view.displayMessage("Please select an album to remove sound clips from.");
			return;
		}
		List<SoundClip> clips = view.getSelectedSoundClips();
		for (SoundClip clip : clips) {
			album.deleteSoundClip(clip);
		}


		//in the MusicOrganizer window
		view.onClipsUpdated();

		// in the open windows
		for (AlbumContentsWindow a : openWindows) {
			a.onClipsUpdated();
		}
	}
	
	/**
	 * Puts the selected sound clips on the queue and lets
	 * the sound clip player thread play them. Essentially, when
	 * this method is called, the selected sound clips in the 
	 * SoundClipTable are played.
	 */
	public void playSoundClipsMusicOrganizerWindow(){

		List<SoundClip> l = view.getSelectedSoundClips();
		queue.enqueue(l);
		for(int i=0;i<l.size();i++) {
			view.displayMessage("Playing " + l.get(i));
		}
	}

	//spelar ljudfil i en annan window än musicorganizer
	public void playSoundClipsAlbumWindow(AlbumContentsWindow albumContentsWindow) {

		albumView = albumContentsWindow;
		List<SoundClip> l = albumView.getSelectedSoundClips();
		queue.enqueue(l);
		for(int i=0;i<l.size();i++) {
			view.displayMessage("Playing " + l.get(i));
		}
	}

}
