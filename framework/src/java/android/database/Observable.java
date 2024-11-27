package android.database;

/* loaded from: classes.dex */
public abstract class Observable<T> {
    protected final java.util.ArrayList<T> mObservers = new java.util.ArrayList<>();

    public void registerObserver(T t) {
        if (t == null) {
            throw new java.lang.IllegalArgumentException("The observer is null.");
        }
        synchronized (this.mObservers) {
            if (this.mObservers.contains(t)) {
                throw new java.lang.IllegalStateException("Observer " + t + " is already registered.");
            }
            this.mObservers.add(t);
        }
    }

    public void unregisterObserver(T t) {
        if (t == null) {
            throw new java.lang.IllegalArgumentException("The observer is null.");
        }
        synchronized (this.mObservers) {
            int indexOf = this.mObservers.indexOf(t);
            if (indexOf == -1) {
                throw new java.lang.IllegalStateException("Observer " + t + " was not registered.");
            }
            this.mObservers.remove(indexOf);
        }
    }

    public void unregisterAll() {
        synchronized (this.mObservers) {
            this.mObservers.clear();
        }
    }
}
