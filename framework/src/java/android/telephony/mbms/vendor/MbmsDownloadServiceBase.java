package android.telephony.mbms.vendor;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class MbmsDownloadServiceBase extends android.telephony.mbms.vendor.IMbmsDownloadService.Stub {
    private final java.util.Map<android.os.IBinder, android.telephony.mbms.DownloadStatusListener> mDownloadStatusListenerBinderMap = new java.util.HashMap();
    private final java.util.Map<android.os.IBinder, android.telephony.mbms.DownloadProgressListener> mDownloadProgressListenerBinderMap = new java.util.HashMap();
    private final java.util.Map<android.os.IBinder, android.os.IBinder.DeathRecipient> mDownloadCallbackDeathRecipients = new java.util.HashMap();

    private static abstract class VendorDownloadStatusListener extends android.telephony.mbms.DownloadStatusListener {
        private final android.telephony.mbms.IDownloadStatusListener mListener;

        protected abstract void onRemoteException(android.os.RemoteException remoteException);

        public VendorDownloadStatusListener(android.telephony.mbms.IDownloadStatusListener iDownloadStatusListener) {
            this.mListener = iDownloadStatusListener;
        }

        @Override // android.telephony.mbms.DownloadStatusListener
        public void onStatusUpdated(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.FileInfo fileInfo, int i) {
            try {
                this.mListener.onStatusUpdated(downloadRequest, fileInfo, i);
            } catch (android.os.RemoteException e) {
                onRemoteException(e);
            }
        }
    }

    private static abstract class VendorDownloadProgressListener extends android.telephony.mbms.DownloadProgressListener {
        private final android.telephony.mbms.IDownloadProgressListener mListener;

        protected abstract void onRemoteException(android.os.RemoteException remoteException);

        public VendorDownloadProgressListener(android.telephony.mbms.IDownloadProgressListener iDownloadProgressListener) {
            this.mListener = iDownloadProgressListener;
        }

        @Override // android.telephony.mbms.DownloadProgressListener
        public void onProgressUpdated(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.FileInfo fileInfo, int i, int i2, int i3, int i4) {
            try {
                this.mListener.onProgressUpdated(downloadRequest, fileInfo, i, i2, i3, i4);
            } catch (android.os.RemoteException e) {
                onRemoteException(e);
            }
        }
    }

    public int initialize(int i, android.telephony.mbms.MbmsDownloadSessionCallback mbmsDownloadSessionCallback) throws android.os.RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int initialize(final int i, final android.telephony.mbms.IMbmsDownloadSessionCallback iMbmsDownloadSessionCallback) throws android.os.RemoteException {
        if (iMbmsDownloadSessionCallback == null) {
            throw new java.lang.NullPointerException("Callback must not be null");
        }
        final int callingUid = android.os.Binder.getCallingUid();
        int initialize = initialize(i, new android.telephony.mbms.MbmsDownloadSessionCallback() { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.1
            @Override // android.telephony.mbms.MbmsDownloadSessionCallback
            public void onError(int i2, java.lang.String str) {
                try {
                    if (i2 == -1) {
                        throw new java.lang.IllegalArgumentException("Middleware cannot send an unknown error.");
                    }
                    iMbmsDownloadSessionCallback.onError(i2, str);
                } catch (android.os.RemoteException e) {
                    android.telephony.mbms.vendor.MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.MbmsDownloadSessionCallback
            public void onFileServicesUpdated(java.util.List<android.telephony.mbms.FileServiceInfo> list) {
                try {
                    iMbmsDownloadSessionCallback.onFileServicesUpdated(list);
                } catch (android.os.RemoteException e) {
                    android.telephony.mbms.vendor.MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.MbmsDownloadSessionCallback
            public void onMiddlewareReady() {
                try {
                    iMbmsDownloadSessionCallback.onMiddlewareReady();
                } catch (android.os.RemoteException e) {
                    android.telephony.mbms.vendor.MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }
        });
        if (initialize == 0) {
            iMbmsDownloadSessionCallback.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    android.telephony.mbms.vendor.MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }, 0);
        }
        return initialize;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int requestUpdateFileServices(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int setTempFileRootDirectory(int i, java.lang.String str) throws android.os.RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int addServiceAnnouncement(int i, byte[] bArr) {
        throw new java.lang.UnsupportedOperationException("addServiceAnnouncement not supported by this middleware.");
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int download(android.telephony.mbms.DownloadRequest downloadRequest) throws android.os.RemoteException {
        return 0;
    }

    public int addStatusListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.DownloadStatusListener downloadStatusListener) throws android.os.RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int addStatusListener(final android.telephony.mbms.DownloadRequest downloadRequest, final android.telephony.mbms.IDownloadStatusListener iDownloadStatusListener) throws android.os.RemoteException {
        final int callingUid = android.os.Binder.getCallingUid();
        if (downloadRequest == null) {
            throw new java.lang.NullPointerException("Download request must not be null");
        }
        if (iDownloadStatusListener == null) {
            throw new java.lang.NullPointerException("Callback must not be null");
        }
        android.telephony.mbms.vendor.MbmsDownloadServiceBase.VendorDownloadStatusListener vendorDownloadStatusListener = new android.telephony.mbms.vendor.MbmsDownloadServiceBase.VendorDownloadStatusListener(iDownloadStatusListener) { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.3
            @Override // android.telephony.mbms.vendor.MbmsDownloadServiceBase.VendorDownloadStatusListener
            protected void onRemoteException(android.os.RemoteException remoteException) {
                android.telephony.mbms.vendor.MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, downloadRequest.getSubscriptionId());
            }
        };
        int addStatusListener = addStatusListener(downloadRequest, vendorDownloadStatusListener);
        if (addStatusListener == 0) {
            android.os.IBinder.DeathRecipient deathRecipient = new android.os.IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.4
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    android.telephony.mbms.vendor.MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, downloadRequest.getSubscriptionId());
                    android.telephony.mbms.vendor.MbmsDownloadServiceBase.this.mDownloadStatusListenerBinderMap.remove(iDownloadStatusListener.asBinder());
                    android.telephony.mbms.vendor.MbmsDownloadServiceBase.this.mDownloadCallbackDeathRecipients.remove(iDownloadStatusListener.asBinder());
                }
            };
            this.mDownloadCallbackDeathRecipients.put(iDownloadStatusListener.asBinder(), deathRecipient);
            iDownloadStatusListener.asBinder().linkToDeath(deathRecipient, 0);
            this.mDownloadStatusListenerBinderMap.put(iDownloadStatusListener.asBinder(), vendorDownloadStatusListener);
        }
        return addStatusListener;
    }

    public int removeStatusListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.DownloadStatusListener downloadStatusListener) throws android.os.RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int removeStatusListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadStatusListener iDownloadStatusListener) throws android.os.RemoteException {
        if (downloadRequest == null) {
            throw new java.lang.NullPointerException("Download request must not be null");
        }
        if (iDownloadStatusListener == null) {
            throw new java.lang.NullPointerException("Callback must not be null");
        }
        android.os.IBinder.DeathRecipient remove = this.mDownloadCallbackDeathRecipients.remove(iDownloadStatusListener.asBinder());
        if (remove == null) {
            throw new java.lang.IllegalArgumentException("Unknown listener");
        }
        iDownloadStatusListener.asBinder().unlinkToDeath(remove, 0);
        android.telephony.mbms.DownloadStatusListener remove2 = this.mDownloadStatusListenerBinderMap.remove(iDownloadStatusListener.asBinder());
        if (remove2 == null) {
            throw new java.lang.IllegalArgumentException("Unknown listener");
        }
        return removeStatusListener(downloadRequest, remove2);
    }

    public int addProgressListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.DownloadProgressListener downloadProgressListener) throws android.os.RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int addProgressListener(final android.telephony.mbms.DownloadRequest downloadRequest, final android.telephony.mbms.IDownloadProgressListener iDownloadProgressListener) throws android.os.RemoteException {
        final int callingUid = android.os.Binder.getCallingUid();
        if (downloadRequest == null) {
            throw new java.lang.NullPointerException("Download request must not be null");
        }
        if (iDownloadProgressListener == null) {
            throw new java.lang.NullPointerException("Callback must not be null");
        }
        android.telephony.mbms.vendor.MbmsDownloadServiceBase.VendorDownloadProgressListener vendorDownloadProgressListener = new android.telephony.mbms.vendor.MbmsDownloadServiceBase.VendorDownloadProgressListener(iDownloadProgressListener) { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.5
            @Override // android.telephony.mbms.vendor.MbmsDownloadServiceBase.VendorDownloadProgressListener
            protected void onRemoteException(android.os.RemoteException remoteException) {
                android.telephony.mbms.vendor.MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, downloadRequest.getSubscriptionId());
            }
        };
        int addProgressListener = addProgressListener(downloadRequest, vendorDownloadProgressListener);
        if (addProgressListener == 0) {
            android.os.IBinder.DeathRecipient deathRecipient = new android.os.IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsDownloadServiceBase.6
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    android.telephony.mbms.vendor.MbmsDownloadServiceBase.this.onAppCallbackDied(callingUid, downloadRequest.getSubscriptionId());
                    android.telephony.mbms.vendor.MbmsDownloadServiceBase.this.mDownloadProgressListenerBinderMap.remove(iDownloadProgressListener.asBinder());
                    android.telephony.mbms.vendor.MbmsDownloadServiceBase.this.mDownloadCallbackDeathRecipients.remove(iDownloadProgressListener.asBinder());
                }
            };
            this.mDownloadCallbackDeathRecipients.put(iDownloadProgressListener.asBinder(), deathRecipient);
            iDownloadProgressListener.asBinder().linkToDeath(deathRecipient, 0);
            this.mDownloadProgressListenerBinderMap.put(iDownloadProgressListener.asBinder(), vendorDownloadProgressListener);
        }
        return addProgressListener;
    }

    public int removeProgressListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.DownloadProgressListener downloadProgressListener) throws android.os.RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public final int removeProgressListener(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.IDownloadProgressListener iDownloadProgressListener) throws android.os.RemoteException {
        if (downloadRequest == null) {
            throw new java.lang.NullPointerException("Download request must not be null");
        }
        if (iDownloadProgressListener == null) {
            throw new java.lang.NullPointerException("Callback must not be null");
        }
        android.os.IBinder.DeathRecipient remove = this.mDownloadCallbackDeathRecipients.remove(iDownloadProgressListener.asBinder());
        if (remove == null) {
            throw new java.lang.IllegalArgumentException("Unknown listener");
        }
        iDownloadProgressListener.asBinder().unlinkToDeath(remove, 0);
        android.telephony.mbms.DownloadProgressListener remove2 = this.mDownloadProgressListenerBinderMap.remove(iDownloadProgressListener.asBinder());
        if (remove2 == null) {
            throw new java.lang.IllegalArgumentException("Unknown listener");
        }
        return removeProgressListener(downloadRequest, remove2);
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public java.util.List<android.telephony.mbms.DownloadRequest> listPendingDownloads(int i) throws android.os.RemoteException {
        return null;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int cancelDownload(android.telephony.mbms.DownloadRequest downloadRequest) throws android.os.RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int requestDownloadState(android.telephony.mbms.DownloadRequest downloadRequest, android.telephony.mbms.FileInfo fileInfo) throws android.os.RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public int resetDownloadKnowledge(android.telephony.mbms.DownloadRequest downloadRequest) throws android.os.RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService
    public void dispose(int i) throws android.os.RemoteException {
    }

    public void onAppCallbackDied(int i, int i2) {
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService.Stub, android.os.IInterface
    @android.annotation.SystemApi
    public android.os.IBinder asBinder() {
        return super.asBinder();
    }

    @Override // android.telephony.mbms.vendor.IMbmsDownloadService.Stub, android.os.Binder
    @android.annotation.SystemApi
    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        return super.onTransact(i, parcel, parcel2, i2);
    }
}
