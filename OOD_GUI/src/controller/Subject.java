package controller;

import view.Observer;

public interface Subject {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers();

    void playSoundClipsAlbumWindow(Observer o);
}
