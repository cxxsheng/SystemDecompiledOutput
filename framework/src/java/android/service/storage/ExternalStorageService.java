package android.service.storage;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class ExternalStorageService extends android.app.Service {
    public static final java.lang.String EXTRA_ERROR = "android.service.storage.extra.error";
    public static final java.lang.String EXTRA_PACKAGE_NAME = "android.service.storage.extra.package_name";
    public static final java.lang.String EXTRA_SESSION_ID = "android.service.storage.extra.session_id";
    public static final int FLAG_SESSION_ATTRIBUTE_INDEXABLE = 2;
    public static final int FLAG_SESSION_TYPE_FUSE = 1;
    public static final java.lang.String SERVICE_INTERFACE = "android.service.storage.ExternalStorageService";
    private final android.service.storage.ExternalStorageService.ExternalStorageServiceWrapper mWrapper = new android.service.storage.ExternalStorageService.ExternalStorageServiceWrapper();
    private final android.os.Handler mHandler = com.android.internal.os.BackgroundThread.getHandler();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SessionFlag {
    }

    public abstract void onEndSession(java.lang.String str) throws java.io.IOException;

    public abstract void onStartSession(java.lang.String str, int i, android.os.ParcelFileDescriptor parcelFileDescriptor, java.io.File file, java.io.File file2) throws java.io.IOException;

    public abstract void onVolumeStateChanged(android.os.storage.StorageVolume storageVolume) throws java.io.IOException;

    public void onFreeCache(java.util.UUID uuid, long j) throws java.io.IOException {
        throw new java.lang.UnsupportedOperationException("onFreeCacheRequested not implemented");
    }

    public void onAnrDelayStarted(java.lang.String str, int i, int i2, int i3) {
        throw new java.lang.UnsupportedOperationException("onAnrDelayStarted not implemented");
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return this.mWrapper;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ExternalStorageServiceWrapper extends android.service.storage.IExternalStorageService.Stub {
        private ExternalStorageServiceWrapper() {
        }

        @Override // android.service.storage.IExternalStorageService
        public void startSession(final java.lang.String str, final int i, final android.os.ParcelFileDescriptor parcelFileDescriptor, final java.lang.String str2, final java.lang.String str3, final android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
            android.service.storage.ExternalStorageService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.storage.ExternalStorageService$ExternalStorageServiceWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.storage.ExternalStorageService.ExternalStorageServiceWrapper.this.lambda$startSession$0(str, i, parcelFileDescriptor, str2, str3, remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startSession$0(java.lang.String str, int i, android.os.ParcelFileDescriptor parcelFileDescriptor, java.lang.String str2, java.lang.String str3, android.os.RemoteCallback remoteCallback) {
            try {
                android.service.storage.ExternalStorageService.this.onStartSession(str, i, parcelFileDescriptor, new java.io.File(str2), new java.io.File(str3));
                sendResult(str, null, remoteCallback);
            } catch (java.lang.Throwable th) {
                sendResult(str, th, remoteCallback);
            }
        }

        @Override // android.service.storage.IExternalStorageService
        public void notifyVolumeStateChanged(final java.lang.String str, final android.os.storage.StorageVolume storageVolume, final android.os.RemoteCallback remoteCallback) {
            android.service.storage.ExternalStorageService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.storage.ExternalStorageService$ExternalStorageServiceWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.storage.ExternalStorageService.ExternalStorageServiceWrapper.this.lambda$notifyVolumeStateChanged$1(storageVolume, str, remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyVolumeStateChanged$1(android.os.storage.StorageVolume storageVolume, java.lang.String str, android.os.RemoteCallback remoteCallback) {
            try {
                android.service.storage.ExternalStorageService.this.onVolumeStateChanged(storageVolume);
                sendResult(str, null, remoteCallback);
            } catch (java.lang.Throwable th) {
                sendResult(str, th, remoteCallback);
            }
        }

        @Override // android.service.storage.IExternalStorageService
        public void freeCache(final java.lang.String str, final java.lang.String str2, final long j, final android.os.RemoteCallback remoteCallback) {
            android.service.storage.ExternalStorageService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.storage.ExternalStorageService$ExternalStorageServiceWrapper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.storage.ExternalStorageService.ExternalStorageServiceWrapper.this.lambda$freeCache$2(str2, j, str, remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$freeCache$2(java.lang.String str, long j, java.lang.String str2, android.os.RemoteCallback remoteCallback) {
            try {
                android.service.storage.ExternalStorageService.this.onFreeCache(android.os.storage.StorageManager.convert(str), j);
                sendResult(str2, null, remoteCallback);
            } catch (java.lang.Throwable th) {
                sendResult(str2, th, remoteCallback);
            }
        }

        @Override // android.service.storage.IExternalStorageService
        public void endSession(final java.lang.String str, final android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
            android.service.storage.ExternalStorageService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.storage.ExternalStorageService$ExternalStorageServiceWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.storage.ExternalStorageService.ExternalStorageServiceWrapper.this.lambda$endSession$3(str, remoteCallback);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$endSession$3(java.lang.String str, android.os.RemoteCallback remoteCallback) {
            try {
                android.service.storage.ExternalStorageService.this.onEndSession(str);
                sendResult(str, null, remoteCallback);
            } catch (java.lang.Throwable th) {
                sendResult(str, th, remoteCallback);
            }
        }

        @Override // android.service.storage.IExternalStorageService
        public void notifyAnrDelayStarted(final java.lang.String str, final int i, final int i2, final int i3) throws android.os.RemoteException {
            android.service.storage.ExternalStorageService.this.mHandler.post(new java.lang.Runnable() { // from class: android.service.storage.ExternalStorageService$ExternalStorageServiceWrapper$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.service.storage.ExternalStorageService.ExternalStorageServiceWrapper.this.lambda$notifyAnrDelayStarted$4(str, i, i2, i3);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyAnrDelayStarted$4(java.lang.String str, int i, int i2, int i3) {
            try {
                android.service.storage.ExternalStorageService.this.onAnrDelayStarted(str, i, i2, i3);
            } catch (java.lang.Throwable th) {
            }
        }

        private void sendResult(java.lang.String str, java.lang.Throwable th, android.os.RemoteCallback remoteCallback) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(android.service.storage.ExternalStorageService.EXTRA_SESSION_ID, str);
            if (th != null) {
                bundle.putParcelable(android.service.storage.ExternalStorageService.EXTRA_ERROR, new android.os.ParcelableException(th));
            }
            remoteCallback.sendResult(bundle);
        }
    }
}
