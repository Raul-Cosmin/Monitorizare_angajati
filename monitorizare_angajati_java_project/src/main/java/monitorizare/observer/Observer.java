package monitorizare.observer;

public interface Observer {
    /**
     * function called by the observable object which this is attached to.
     * this should update any information that is changed
     */
    void update();
}
