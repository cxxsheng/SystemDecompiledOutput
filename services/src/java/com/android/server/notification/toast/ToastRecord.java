package com.android.server.notification.toast;

/* loaded from: classes2.dex */
public abstract class ToastRecord {
    public final int displayId;
    public final boolean isSystemToast;
    private int mDuration;
    protected final com.android.server.notification.NotificationManagerService mNotificationManager;
    public final int pid;
    public final java.lang.String pkg;
    public final android.os.IBinder token;
    public final int uid;
    public final android.os.Binder windowToken;

    public abstract void hide();

    public abstract boolean isAppRendered();

    public abstract boolean show();

    protected ToastRecord(com.android.server.notification.NotificationManagerService notificationManagerService, int i, int i2, java.lang.String str, boolean z, android.os.IBinder iBinder, int i3, android.os.Binder binder, int i4) {
        this.mNotificationManager = notificationManagerService;
        this.uid = i;
        this.pid = i2;
        this.pkg = str;
        this.isSystemToast = z;
        this.token = iBinder;
        this.windowToken = binder;
        this.displayId = i4;
        this.mDuration = i3;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void update(int i) {
        this.mDuration = i;
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str, com.android.server.notification.NotificationManagerService.DumpFilter dumpFilter) {
        if (dumpFilter != null && !dumpFilter.matches(this.pkg)) {
            return;
        }
        printWriter.println(str + this);
    }

    public boolean keepProcessAlive() {
        return false;
    }
}
