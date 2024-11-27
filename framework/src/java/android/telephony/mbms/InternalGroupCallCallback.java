package android.telephony.mbms;

/* loaded from: classes3.dex */
public class InternalGroupCallCallback extends android.telephony.mbms.IGroupCallCallback.Stub {
    private final android.telephony.mbms.GroupCallCallback mAppCallback;
    private final java.util.concurrent.Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalGroupCallCallback(android.telephony.mbms.GroupCallCallback groupCallCallback, java.util.concurrent.Executor executor) {
        this.mAppCallback = groupCallCallback;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IGroupCallCallback
    public void onError(final int i, final java.lang.String str) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalGroupCallCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalGroupCallCallback.this.mAppCallback.onError(i, str);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IGroupCallCallback
    public void onGroupCallStateChanged(final int i, final int i2) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalGroupCallCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalGroupCallCallback.this.mAppCallback.onGroupCallStateChanged(i, i2);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IGroupCallCallback
    public void onBroadcastSignalStrengthUpdated(final int i) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalGroupCallCallback.3
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalGroupCallCallback.this.mAppCallback.onBroadcastSignalStrengthUpdated(i);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void stop() {
        this.mIsStopped = true;
    }
}
