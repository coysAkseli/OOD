package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import model.*;
import view.AlbumContentsWindow;
import view.MusicOrganizerWindow;
import view.Observer;

public class MusicOrganizerController implements Subject {

	private MusicOrganizerWindow view;
	private AlbumContentsWindow albumView;
	private SoundClipBlockingQueue queue;
	private Album root;
	private ArrayList<Observer> observers;
	
	public MusicOrganizerController(int dummie) {
		// Create the root album for all sound clips
		if (dummie == 0) root = new Album("All Sound Clips");
		
		// Create the blocking queue
		queue = new SoundClipBlockingQueue();

		//innehåller alla öppna fönster förutom det upsprungliga MusicOrganizerWidnow
		observers = new ArrayList<Observer>();

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

	public void setRootAlbum(Album album) {
		this.root = album;
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
		view.onAlbumAdded(parentAlbum, newAlbum);
		
	}

	public void openAlbumContentsWindow(Album album) {
		AlbumContentsWindow contentsWindow = new AlbumContentsWindow(this, album);
		//observers.add(contentsWindow);
		registerObserver(contentsWindow);

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
		view.onAlbumRemoved(album);

		/**
		 * Måddes skapa en arraylist som sparar alla fönster som
		 * ska stängas.
		 * fungerar inte om man under rekursionen
		 * stänger fönstren eftersom HashSeten observers
		 * ändras efter varje rekursion och stängt fönster
		 */

		ArrayList<Observer> albumWindowsToClose = new ArrayList<>();
		flagWindowForClosure(album, albumWindowsToClose);

		// Här stängs fönstren.
		for (Observer window : albumWindowsToClose) {
			window.closeWindow();
		}
	}

	//detects windows that need to be closed
	public void flagWindowForClosure(Album album, ArrayList<Observer> albumWindowsToClose) {

		for (Observer o : observers) {
			if (o.getAlbum().equals(album)) {
				albumWindowsToClose.add(o);
				for (Album a : o.getAlbum().getSubAlbums()) {
					flagWindowForClosure(a, albumWindowsToClose);
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

		albumViewChanged();
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

		//soundclip has been removed, therefore album view changed
		albumViewChanged();
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
	public void playSoundClipsAlbumWindow(Observer o) {

		albumView = (AlbumContentsWindow) o;
		List<SoundClip> l = albumView.getSelectedSoundClips();
		queue.enqueue(l);
		for(int i=0;i<l.size();i++) {
			view.displayMessage("Playing " + l.get(i));
		}
	}

	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer o : observers) {
			o.update();
		}
	}

	public void albumViewChanged() {
		this.notifyObservers();
	}
}
