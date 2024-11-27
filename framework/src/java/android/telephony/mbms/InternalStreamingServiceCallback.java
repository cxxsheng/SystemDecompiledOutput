package android.telephony.mbms;

/* loaded from: classes3.dex */
public class InternalStreamingServiceCallback extends android.telephony.mbms.IStreamingServiceCallback.Stub {
    private final android.telephony.mbms.StreamingServiceCallback mAppCallback;
    private final java.util.concurrent.Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalStreamingServiceCallback(android.telephony.mbms.StreamingServiceCallback streamingServiceCallback, java.util.concurrent.Executor executor) {
        this.mAppCallback = streamingServiceCallback;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IStreamingServiceCallback
    public void onError(final int i, final java.lang.String str) throws android.os.RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalStreamingServiceCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalStreamingServiceCallback.this.mAppCallback.onError(i, str);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IStreamingServiceCallback
    public void onStreamStateUpdated(final int i, final int i2) throws android.os.RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalStreamingServiceCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalStreamingServiceCallback.this.mAppCallback.onStreamStateUpdated(i, i2);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IStreamingServiceCallback
    public void onMediaDescriptionUpdated() throws android.os.RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalStreamingServiceCallback.3
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalStreamingServiceCallback.this.mAppCallback.onMediaDescriptionUpdated();
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IStreamingServiceCallback
    public void onBroadcastSignalStrengthUpdated(final int i) throws android.os.RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalStreamingServiceCallback.4
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalStreamingServiceCallback.this.mAppCallback.onBroadcastSignalStrengthUpdated(i);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IStreamingServiceCallback
    public void onStreamMethodUpdated(final int i) throws android.os.RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalStreamingServiceCallback.5
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalStreamingServiceCallback.this.mAppCallback.onStreamMethodUpdated(i);
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
