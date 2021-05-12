package monitorizare.observer;

public interface Observable {
    /**
     * adds an observer to the notifiable observers
     * @param e - the given observer
     */
    void addObserver(Observer e);

    /**
     * removes an observer from the notifiable observers
     * @param e - the given observer
     */
    void removeObserver(Observer e);

    /**
     * notifies all observers that a change has been made
     */
    void notifyObservers();
}
