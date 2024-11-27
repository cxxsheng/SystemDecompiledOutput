package com.android.server.notification.toast;

/* loaded from: classes2.dex */
public class CustomToastRecord extends com.android.server.notification.toast.ToastRecord {
    private static final java.lang.String TAG = "NotificationService";
    public final android.app.ITransientNotification callback;

    public CustomToastRecord(com.android.server.notification.NotificationManagerService notificationManagerService, int i, int i2, java.lang.String str, boolean z, android.os.IBinder iBinder, android.app.ITransientNotification iTransientNotification, int i3, android.os.Binder binder, int i4) {
        super(notificationManagerService, i, i2, str, z, iBinder, i3, binder, i4);
        this.callback = (android.app.ITransientNotification) com.android.internal.util.Preconditions.checkNotNull(iTransientNotification);
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public boolean show() {
        if (com.android.server.notification.NotificationManagerService.DBG) {
            android.util.Slog.d("NotificationService", "Show pkg=" + this.pkg + " callback=" + this.callback);
        }
        try {
            this.callback.show(this.windowToken);
            return true;
        } catch (android.os.RemoteException e) {
            android.util.Slog.w("NotificationService", "Object died trying to show custom toast " + this.token + " in package " + this.pkg);
            this.mNotificationManager.keepProcessAliveForToastIfNeeded(this.pid);
            return false;
        }
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public void hide() {
        try {
            this.callback.hide();
        } catch (android.os.RemoteException e) {
            android.util.Slog.w("NotificationService", "Object died trying to hide custom toast " + this.token + " in package " + this.pkg);
        }
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public boolean keepProcessAlive() {
        return true;
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public boolean isAppRendered() {
        return true;
    }

    public java.lang.String toString() {
        return "CustomToastRecord{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.pid + ":" + this.pkg + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.UserHandle.formatUid(this.uid) + " isSystemToast=" + this.isSystemToast + " token=" + this.token + " callback=" + this.callback + " duration=" + getDuration() + "}";
    }
}
