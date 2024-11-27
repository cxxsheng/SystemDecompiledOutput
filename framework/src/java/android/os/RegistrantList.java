package android.os;

/* loaded from: classes3.dex */
public class RegistrantList {
    java.util.ArrayList registrants = new java.util.ArrayList();

    public synchronized void add(android.os.Handler handler, int i, java.lang.Object obj) {
        add(new android.os.Registrant(handler, i, obj));
    }

    public synchronized void addUnique(android.os.Handler handler, int i, java.lang.Object obj) {
        remove(handler);
        add(new android.os.Registrant(handler, i, obj));
    }

    public synchronized void add(android.os.Registrant registrant) {
        removeCleared();
        this.registrants.add(registrant);
    }

    public synchronized void removeCleared() {
        for (int size = this.registrants.size() - 1; size >= 0; size--) {
            if (((android.os.Registrant) this.registrants.get(size)).refH == null) {
                this.registrants.remove(size);
            }
        }
    }

    public synchronized void removeAll() {
        this.registrants.clear();
    }

    public synchronized int size() {
        return this.registrants.size();
    }

    public synchronized java.lang.Object get(int i) {
        return this.registrants.get(i);
    }

    private synchronized void internalNotifyRegistrants(java.lang.Object obj, java.lang.Throwable th) {
        int size = this.registrants.size();
        for (int i = 0; i < size; i++) {
            ((android.os.Registrant) this.registrants.get(i)).internalNotifyRegistrant(obj, th);
        }
    }

    public void notifyRegistrants() {
        internalNotifyRegistrants(null, null);
    }

    public void notifyException(java.lang.Throwable th) {
        internalNotifyRegistrants(null, th);
    }

    public void notifyResult(java.lang.Object obj) {
        internalNotifyRegistrants(obj, null);
    }

    public void notifyRegistrants(android.os.AsyncResult asyncResult) {
        internalNotifyRegistrants(asyncResult.result, asyncResult.exception);
    }

    public synchronized void remove(android.os.Handler handler) {
        int size = this.registrants.size();
        for (int i = 0; i < size; i++) {
            android.os.Registrant registrant = (android.os.Registrant) this.registrants.get(i);
            android.os.Handler handler2 = registrant.getHandler();
            if (handler2 == null || handler2 == handler) {
                registrant.clear();
            }
        }
        removeCleared();
    }
}
