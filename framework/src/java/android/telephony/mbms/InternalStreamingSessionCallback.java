package android.telephony.mbms;

/* loaded from: classes3.dex */
public class InternalStreamingSessionCallback extends android.telephony.mbms.IMbmsStreamingSessionCallback.Stub {
    private final android.telephony.mbms.MbmsStreamingSessionCallback mAppCallback;
    private final java.util.concurrent.Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalStreamingSessionCallback(android.telephony.mbms.MbmsStreamingSessionCallback mbmsStreamingSessionCallback, java.util.concurrent.Executor executor) {
        this.mAppCallback = mbmsStreamingSessionCallback;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
    public void onError(final int i, final java.lang.String str) throws android.os.RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalStreamingSessionCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalStreamingSessionCallback.this.mAppCallback.onError(i, str);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
    public void onStreamingServicesUpdated(final java.util.List<android.telephony.mbms.StreamingServiceInfo> list) throws android.os.RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalStreamingSessionCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalStreamingSessionCallback.this.mAppCallback.onStreamingServicesUpdated(list);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
    public void onMiddlewareReady() throws android.os.RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalStreamingSessionCallback.3
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalStreamingSessionCallback.this.mAppCallback.onMiddlewareReady();
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
