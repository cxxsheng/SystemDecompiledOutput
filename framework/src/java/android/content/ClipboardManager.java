package android.content;

/* loaded from: classes.dex */
public class ClipboardManager extends android.text.ClipboardManager {
    public static final java.lang.String DEVICE_CONFIG_ALLOW_VIRTUALDEVICE_SILOS = "allow_virtualdevice_silos";
    public static final boolean DEVICE_CONFIG_DEFAULT_ALLOW_VIRTUALDEVICE_SILOS = true;
    public static final boolean DEVICE_CONFIG_DEFAULT_SHOW_ACCESS_NOTIFICATIONS = true;
    public static final java.lang.String DEVICE_CONFIG_SHOW_ACCESS_NOTIFICATIONS = "show_access_notifications";
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final java.util.ArrayList<android.content.ClipboardManager.OnPrimaryClipChangedListener> mPrimaryClipChangedListeners = new java.util.ArrayList<>();
    private final android.content.IOnPrimaryClipChangedListener.Stub mPrimaryClipChangedServiceListener = new android.content.ClipboardManager.AnonymousClass1();
    private final android.content.IClipboard mService = android.content.IClipboard.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("clipboard"));

    public interface OnPrimaryClipChangedListener {
        void onPrimaryClipChanged();
    }

    /* renamed from: android.content.ClipboardManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.content.IOnPrimaryClipChangedListener.Stub {
        AnonymousClass1() {
        }

        @Override // android.content.IOnPrimaryClipChangedListener
        public void dispatchPrimaryClipChanged() {
            android.content.ClipboardManager.this.mHandler.post(new java.lang.Runnable() { // from class: android.content.ClipboardManager$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.content.ClipboardManager.AnonymousClass1.this.lambda$dispatchPrimaryClipChanged$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchPrimaryClipChanged$0() {
            android.content.ClipboardManager.this.reportPrimaryClipChanged();
        }
    }

    public ClipboardManager(android.content.Context context, android.os.Handler handler) throws android.os.ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mHandler = handler;
    }

    @android.annotation.SystemApi
    public boolean areClipboardAccessNotificationsEnabled() {
        try {
            return this.mService.areClipboardAccessNotificationsEnabledForUser(this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setClipboardAccessNotificationsEnabled(boolean z) {
        try {
            this.mService.setClipboardAccessNotificationsEnabledForUser(z, this.mContext.getUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPrimaryClip(android.content.ClipData clipData) {
        try {
            java.util.Objects.requireNonNull(clipData);
            clipData.prepareToLeaveProcess(true);
            this.mService.setPrimaryClip(clipData, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void setPrimaryClipAsPackage(android.content.ClipData clipData, java.lang.String str) {
        try {
            java.util.Objects.requireNonNull(clipData);
            java.util.Objects.requireNonNull(str);
            clipData.prepareToLeaveProcess(true);
            this.mService.setPrimaryClipAsPackage(clipData, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId(), str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearPrimaryClip() {
        try {
            this.mService.clearPrimaryClip(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.ClipData getPrimaryClip() {
        try {
            return this.mService.getPrimaryClip(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.ClipDescription getPrimaryClipDescription() {
        try {
            return this.mService.getPrimaryClipDescription(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasPrimaryClip() {
        try {
            return this.mService.hasPrimaryClip(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addPrimaryClipChangedListener(android.content.ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        synchronized (this.mPrimaryClipChangedListeners) {
            if (this.mPrimaryClipChangedListeners.isEmpty()) {
                try {
                    this.mService.addPrimaryClipChangedListener(this.mPrimaryClipChangedServiceListener, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            this.mPrimaryClipChangedListeners.add(onPrimaryClipChangedListener);
        }
    }

    public void removePrimaryClipChangedListener(android.content.ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChangedListener) {
        synchronized (this.mPrimaryClipChangedListeners) {
            this.mPrimaryClipChangedListeners.remove(onPrimaryClipChangedListener);
            if (this.mPrimaryClipChangedListeners.isEmpty()) {
                try {
                    this.mService.removePrimaryClipChangedListener(this.mPrimaryClipChangedServiceListener, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    @Override // android.text.ClipboardManager
    @java.lang.Deprecated
    public java.lang.CharSequence getText() {
        android.content.ClipData primaryClip = getPrimaryClip();
        if (primaryClip != null && primaryClip.getItemCount() > 0) {
            return primaryClip.getItemAt(0).coerceToText(this.mContext);
        }
        return null;
    }

    @Override // android.text.ClipboardManager
    @java.lang.Deprecated
    public void setText(java.lang.CharSequence charSequence) {
        setPrimaryClip(android.content.ClipData.newPlainText(null, charSequence));
    }

    @Override // android.text.ClipboardManager
    @java.lang.Deprecated
    public boolean hasText() {
        try {
            return this.mService.hasClipboardText(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getPrimaryClipSource() {
        try {
            return this.mService.getPrimaryClipSource(this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), this.mContext.getUserId(), this.mContext.getDeviceId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void reportPrimaryClipChanged() {
        synchronized (this.mPrimaryClipChangedListeners) {
            if (this.mPrimaryClipChangedListeners.size() <= 0) {
                return;
            }
            for (java.lang.Object obj : this.mPrimaryClipChangedListeners.toArray()) {
                ((android.content.ClipboardManager.OnPrimaryClipChangedListener) obj).onPrimaryClipChanged();
            }
        }
    }
}
