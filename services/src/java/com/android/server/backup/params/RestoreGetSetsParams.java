package com.android.server.backup.params;

/* loaded from: classes.dex */
public class RestoreGetSetsParams {
    public final com.android.server.backup.internal.OnTaskFinishedListener listener;
    public final com.android.server.backup.transport.TransportConnection mTransportConnection;
    public final android.app.backup.IBackupManagerMonitor monitor;
    public final android.app.backup.IRestoreObserver observer;
    public final com.android.server.backup.restore.ActiveRestoreSession session;

    public RestoreGetSetsParams(com.android.server.backup.transport.TransportConnection transportConnection, com.android.server.backup.restore.ActiveRestoreSession activeRestoreSession, android.app.backup.IRestoreObserver iRestoreObserver, android.app.backup.IBackupManagerMonitor iBackupManagerMonitor, com.android.server.backup.internal.OnTaskFinishedListener onTaskFinishedListener) {
        this.mTransportConnection = transportConnection;
        this.session = activeRestoreSession;
        this.observer = iRestoreObserver;
        this.monitor = iBackupManagerMonitor;
        this.listener = onTaskFinishedListener;
    }
}
