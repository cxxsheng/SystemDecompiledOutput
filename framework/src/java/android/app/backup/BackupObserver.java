package android.app.backup;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public abstract class BackupObserver {
    public void onUpdate(java.lang.String str, android.app.backup.BackupProgress backupProgress) {
    }

    public void onResult(java.lang.String str, int i) {
    }

    public void backupFinished(int i) {
    }
}
