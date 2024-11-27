package android.service.dataloader;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class DataLoaderService extends android.app.Service {
    private static final java.lang.String TAG = "DataLoaderService";
    private final android.service.dataloader.DataLoaderService.DataLoaderBinderService mBinder = new android.service.dataloader.DataLoaderService.DataLoaderBinderService();

    @android.annotation.SystemApi
    public interface DataLoader {
        boolean onCreate(android.content.pm.DataLoaderParams dataLoaderParams, android.service.dataloader.DataLoaderService.FileSystemConnector fileSystemConnector);

        boolean onPrepareImage(java.util.Collection<android.content.pm.InstallationFile> collection, java.util.Collection<java.lang.String> collection2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean nativeCreateDataLoader(int i, android.content.pm.FileSystemControlParcel fileSystemControlParcel, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener);

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean nativeDestroyDataLoader(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean nativePrepareImage(int i, android.content.pm.InstallationFileParcel[] installationFileParcelArr, java.lang.String[] strArr);

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean nativeStartDataLoader(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native boolean nativeStopDataLoader(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeWriteData(long j, java.lang.String str, long j2, long j3, android.os.ParcelFileDescriptor parcelFileDescriptor);

    @android.annotation.SystemApi
    public android.service.dataloader.DataLoaderService.DataLoader onCreateDataLoader(android.content.pm.DataLoaderParams dataLoaderParams) {
        return null;
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        return this.mBinder;
    }

    private class DataLoaderBinderService extends android.content.pm.IDataLoader.Stub {
        private DataLoaderBinderService() {
        }

        /* JADX WARN: Finally extract failed */
        @Override // android.content.pm.IDataLoader
        public void create(int i, android.content.pm.DataLoaderParamsParcel dataLoaderParamsParcel, android.content.pm.FileSystemControlParcel fileSystemControlParcel, android.content.pm.IDataLoaderStatusListener iDataLoaderStatusListener) throws java.lang.RuntimeException {
            try {
                try {
                    android.service.dataloader.DataLoaderService.this.nativeCreateDataLoader(i, fileSystemControlParcel, dataLoaderParamsParcel, iDataLoaderStatusListener);
                    if (fileSystemControlParcel.incremental != null) {
                        libcore.io.IoUtils.closeQuietly(fileSystemControlParcel.incremental.cmd);
                        libcore.io.IoUtils.closeQuietly(fileSystemControlParcel.incremental.pendingReads);
                        libcore.io.IoUtils.closeQuietly(fileSystemControlParcel.incremental.log);
                        libcore.io.IoUtils.closeQuietly(fileSystemControlParcel.incremental.blocksWritten);
                    }
                } catch (java.lang.Exception e) {
                    android.util.Slog.e(android.service.dataloader.DataLoaderService.TAG, "Failed to create native loader for " + i, e);
                    destroy(i);
                    throw new java.lang.RuntimeException(e);
                }
            } catch (java.lang.Throwable th) {
                if (fileSystemControlParcel.incremental != null) {
                    libcore.io.IoUtils.closeQuietly(fileSystemControlParcel.incremental.cmd);
                    libcore.io.IoUtils.closeQuietly(fileSystemControlParcel.incremental.pendingReads);
                    libcore.io.IoUtils.closeQuietly(fileSystemControlParcel.incremental.log);
                    libcore.io.IoUtils.closeQuietly(fileSystemControlParcel.incremental.blocksWritten);
                }
                throw th;
            }
        }

        @Override // android.content.pm.IDataLoader
        public void start(int i) {
            if (!android.service.dataloader.DataLoaderService.this.nativeStartDataLoader(i)) {
                android.util.Slog.e(android.service.dataloader.DataLoaderService.TAG, "Failed to start loader: " + i);
            }
        }

        @Override // android.content.pm.IDataLoader
        public void stop(int i) {
            if (!android.service.dataloader.DataLoaderService.this.nativeStopDataLoader(i)) {
                android.util.Slog.w(android.service.dataloader.DataLoaderService.TAG, "Failed to stop loader: " + i);
            }
        }

        @Override // android.content.pm.IDataLoader
        public void destroy(int i) {
            if (!android.service.dataloader.DataLoaderService.this.nativeDestroyDataLoader(i)) {
                android.util.Slog.w(android.service.dataloader.DataLoaderService.TAG, "Failed to destroy loader: " + i);
            }
        }

        @Override // android.content.pm.IDataLoader
        public void prepareImage(int i, android.content.pm.InstallationFileParcel[] installationFileParcelArr, java.lang.String[] strArr) {
            if (!android.service.dataloader.DataLoaderService.this.nativePrepareImage(i, installationFileParcelArr, strArr)) {
                android.util.Slog.w(android.service.dataloader.DataLoaderService.TAG, "Failed to prepare image for data loader: " + i);
            }
        }
    }

    @android.annotation.SystemApi
    public static final class FileSystemConnector {
        private final long mNativeInstance;

        FileSystemConnector(long j) {
            this.mNativeInstance = j;
        }

        public void writeData(java.lang.String str, long j, long j2, android.os.ParcelFileDescriptor parcelFileDescriptor) throws java.io.IOException {
            try {
                android.service.dataloader.DataLoaderService.nativeWriteData(this.mNativeInstance, str, j, j2, parcelFileDescriptor);
            } catch (java.lang.RuntimeException e) {
                android.util.ExceptionUtils.maybeUnwrapIOException(e);
                throw e;
            }
        }
    }
}
