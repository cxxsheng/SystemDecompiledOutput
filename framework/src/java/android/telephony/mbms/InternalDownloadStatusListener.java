package android.telephony.mbms;

/* loaded from: classes3.dex */
public class InternalDownloadStatusListener extends android.telephony.mbms.IDownloadStatusListener.Stub {
    private final android.telephony.mbms.DownloadStatusListener mAppListener;
    private final java.util.concurrent.Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalDownloadStatusListener(android.telephony.mbms.DownloadStatusListener downloadStatusListener, java.util.concurrent.Executor executor) {
        this.mAppListener = downloadStatusListener;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IDownloadStatusListener
    public void onStatusUpdated(final android.telephony.mbms.DownloadRequest downloadRequest, final android.telephony.mbms.FileInfo fileInfo, final int i) throws android.os.RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalDownloadStatusListener.1
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalDownloadStatusListener.this.mAppListener.onStatusUpdated(downloadRequest, fileInfo, i);
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
