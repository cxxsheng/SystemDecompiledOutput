package android.database;

/* loaded from: classes.dex */
public class DataSetObservable extends android.database.Observable<android.database.DataSetObserver> {
    public void notifyChanged() {
        synchronized (this.mObservers) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((android.database.DataSetObserver) this.mObservers.get(size)).onChanged();
            }
        }
    }

    public void notifyInvalidated() {
        synchronized (this.mObservers) {
            for (int size = this.mObservers.size() - 1; size >= 0; size--) {
                ((android.database.DataSetObserver) this.mObservers.get(size)).onInvalidated();
            }
        }
    }
}
