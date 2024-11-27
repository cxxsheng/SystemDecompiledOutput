package com.android.server.notification.toast;

/* loaded from: classes2.dex */
public class TextToastRecord extends com.android.server.notification.toast.ToastRecord {
    private static final java.lang.String TAG = "NotificationService";

    @android.annotation.Nullable
    private final android.app.ITransientNotificationCallback mCallback;

    @android.annotation.Nullable
    private final com.android.server.statusbar.StatusBarManagerInternal mStatusBar;
    public final java.lang.CharSequence text;

    public TextToastRecord(com.android.server.notification.NotificationManagerService notificationManagerService, @android.annotation.Nullable com.android.server.statusbar.StatusBarManagerInternal statusBarManagerInternal, int i, int i2, java.lang.String str, boolean z, android.os.IBinder iBinder, java.lang.CharSequence charSequence, int i3, android.os.Binder binder, int i4, @android.annotation.Nullable android.app.ITransientNotificationCallback iTransientNotificationCallback) {
        super(notificationManagerService, i, i2, str, z, iBinder, i3, binder, i4);
        this.mStatusBar = statusBarManagerInternal;
        this.mCallback = iTransientNotificationCallback;
        this.text = (java.lang.CharSequence) com.android.internal.util.Preconditions.checkNotNull(charSequence);
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public boolean show() {
        if (com.android.server.notification.NotificationManagerService.DBG) {
            android.util.Slog.d("NotificationService", "Show pkg=" + this.pkg + " text=" + ((java.lang.Object) this.text));
        }
        if (this.mStatusBar == null) {
            android.util.Slog.w("NotificationService", "StatusBar not available to show text toast for package " + this.pkg);
            return false;
        }
        this.mStatusBar.showToast(this.uid, this.pkg, this.token, this.text, this.windowToken, getDuration(), this.mCallback, this.displayId);
        return true;
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public void hide() {
        com.android.internal.util.Preconditions.checkNotNull(this.mStatusBar, "Cannot hide toast that wasn't shown");
        this.mStatusBar.hideToast(this.pkg, this.token);
    }

    @Override // com.android.server.notification.toast.ToastRecord
    public boolean isAppRendered() {
        return false;
    }

    public java.lang.String toString() {
        return "TextToastRecord{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.pid + ":" + this.pkg + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.UserHandle.formatUid(this.uid) + " isSystemToast=" + this.isSystemToast + " token=" + this.token + " text=" + ((java.lang.Object) this.text) + " duration=" + getDuration() + "}";
    }
}
