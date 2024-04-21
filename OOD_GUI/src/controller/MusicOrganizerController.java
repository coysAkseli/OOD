package controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Album;
import model.SoundClip;
import model.SoundClipBlockingQueue;
import model.SoundClipLoader;
import model.SoundClipPlayer;
import view.MusicOrganizerWindow;

public class MusicOrganizerController {

	private MusicOrganizerWindow view;
	private SoundClipBlockingQueue queue;
	private Album root;
	
	public MusicOrganizerController() {

		// Create the root album for all sound clips
		root = new Album("All Sound Clips");
		
		// Create the blocking queue
		queue = new SoundClipBlockingQueue();
				
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

		/*for (SoundClip clip : clips) {
			parentAlbum.addSoundClip(clip);
		}*/

		view.onClipsUpdated();
		
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
		view.onClipsUpdated();
		
	}
	
	/**
	 * Puts the selected sound clips on the queue and lets
	 * the sound clip player thread play them. Essentially, when
	 * this method is called, the selected sound clips in the 
	 * SoundClipTable are played.
	 */
	public void playSoundClips(){
		List<SoundClip> l = view.getSelectedSoundClips();
		queue.enqueue(l);
		for(int i=0;i<l.size();i++) {
			view.displayMessage("Playing " + l.get(i));
		}
	}
}
