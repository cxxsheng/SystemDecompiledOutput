package android.database;

/* loaded from: classes.dex */
public class ContentObservable extends android.database.Observable<android.database.ContentObserver> {
    @Override // android.database.Observable
    public void registerObserver(android.database.ContentObserver contentObserver) {
        super.registerObserver((android.database.ContentObservable) contentObserver);
    }

    @java.lang.Deprecated
    public void dispatchChange(boolean z) {
        dispatchChange(z, null);
    }

    public void dispatchChange(boolean z, android.net.Uri uri) {
        synchronized (this.mObservers) {
            java.util.Iterator it = this.mObservers.iterator();
            while (it.hasNext()) {
                android.database.ContentObserver contentObserver = (android.database.ContentObserver) it.next();
                if (!z || contentObserver.deliverSelfNotifications()) {
                    contentObserver.dispatchChange(z, uri);
                }
            }
        }
    }

    @java.lang.Deprecated
    public void notifyChange(boolean z) {
        synchronized (this.mObservers) {
            java.util.Iterator it = this.mObservers.iterator();
            while (it.hasNext()) {
                ((android.database.ContentObserver) it.next()).onChange(z, null);
            }
        }
    }
}
