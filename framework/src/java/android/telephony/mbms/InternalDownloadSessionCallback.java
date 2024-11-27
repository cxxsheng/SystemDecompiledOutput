package android.telephony.mbms;

/* loaded from: classes3.dex */
public class InternalDownloadSessionCallback extends android.telephony.mbms.IMbmsDownloadSessionCallback.Stub {
    private final android.telephony.mbms.MbmsDownloadSessionCallback mAppCallback;
    private final java.util.concurrent.Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalDownloadSessionCallback(android.telephony.mbms.MbmsDownloadSessionCallback mbmsDownloadSessionCallback, java.util.concurrent.Executor executor) {
        this.mAppCallback = mbmsDownloadSessionCallback;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IMbmsDownloadSessionCallback
    public void onError(final int i, final java.lang.String str) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalDownloadSessionCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalDownloadSessionCallback.this.mAppCallback.onError(i, str);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsDownloadSessionCallback
    public void onFileServicesUpdated(final java.util.List<android.telephony.mbms.FileServiceInfo> list) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalDownloadSessionCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalDownloadSessionCallback.this.mAppCallback.onFileServicesUpdated(list);
                }
            });
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsDownloadSessionCallback
    public void onMiddlewareReady() {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalDownloadSessionCallback.3
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalDownloadSessionCallback.this.mAppCallback.onMiddlewareReady();
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
