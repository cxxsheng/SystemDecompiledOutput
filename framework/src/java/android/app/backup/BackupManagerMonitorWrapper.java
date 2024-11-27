package android.app.backup;

/* loaded from: classes.dex */
public class BackupManagerMonitorWrapper extends android.app.backup.IBackupManagerMonitor.Stub {
    private final android.app.backup.BackupManagerMonitor mMonitor;

    public BackupManagerMonitorWrapper(android.app.backup.BackupManagerMonitor backupManagerMonitor) {
        this.mMonitor = backupManagerMonitor;
    }

    @Override // android.app.backup.IBackupManagerMonitor
    public void onEvent(android.os.Bundle bundle) throws android.os.RemoteException {
        if (this.mMonitor == null) {
            return;
        }
        this.mMonitor.onEvent(bundle);
    }
}
