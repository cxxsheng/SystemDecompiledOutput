package android.app.backup;

/* loaded from: classes.dex */
public abstract class RestoreObserver {
    @android.annotation.SystemApi
    public void restoreSetsAvailable(android.app.backup.RestoreSet[] restoreSetArr) {
    }

    public void restoreStarting(int i) {
    }

    public void onUpdate(int i, java.lang.String str) {
    }

    public void restoreFinished(int i) {
    }
}
