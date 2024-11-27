package android.telephony.mbms;

/* loaded from: classes3.dex */
public class InternalDownloadProgressListener extends android.telephony.mbms.IDownloadProgressListener.Stub {
    private final android.telephony.mbms.DownloadProgressListener mAppListener;
    private final java.util.concurrent.Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalDownloadProgressListener(android.telephony.mbms.DownloadProgressListener downloadProgressListener, java.util.concurrent.Executor executor) {
        this.mAppListener = downloadProgressListener;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IDownloadProgressListener
    public void onProgressUpdated(final android.telephony.mbms.DownloadRequest downloadRequest, final android.telephony.mbms.FileInfo fileInfo, final int i, final int i2, final int i3, final int i4) throws android.os.RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.telephony.mbms.InternalDownloadProgressListener.1
                @Override // java.lang.Runnable
                public void run() {
                    android.telephony.mbms.InternalDownloadProgressListener.this.mAppListener.onProgressUpdated(downloadRequest, fileInfo, i, i2, i3, i4);
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
