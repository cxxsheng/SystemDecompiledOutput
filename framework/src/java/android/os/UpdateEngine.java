package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class UpdateEngine {
    private static final java.lang.String TAG = "UpdateEngine";
    private static final java.lang.String UPDATE_ENGINE_SERVICE = "android.os.UpdateEngineService";
    private android.os.IUpdateEngineCallback mUpdateEngineCallback = null;
    private final java.lang.Object mUpdateEngineCallbackLock = new java.lang.Object();
    private final android.os.IUpdateEngine mUpdateEngine = android.os.IUpdateEngine.Stub.asInterface(android.os.ServiceManager.getService(UPDATE_ENGINE_SERVICE));

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ErrorCode {
    }

    public static final class ErrorCodeConstants {
        public static final int DEVICE_CORRUPTED = 61;
        public static final int DOWNLOAD_PAYLOAD_VERIFICATION_ERROR = 12;
        public static final int DOWNLOAD_TRANSFER_ERROR = 9;
        public static final int ERROR = 1;
        public static final int FILESYSTEM_COPIER_ERROR = 4;
        public static final int INSTALL_DEVICE_OPEN_ERROR = 7;
        public static final int KERNEL_DEVICE_OPEN_ERROR = 8;
        public static final int NOT_ENOUGH_SPACE = 60;
        public static final int PAYLOAD_HASH_MISMATCH_ERROR = 10;
        public static final int PAYLOAD_MISMATCHED_TYPE_ERROR = 6;
        public static final int PAYLOAD_SIZE_MISMATCH_ERROR = 11;
        public static final int PAYLOAD_TIMESTAMP_ERROR = 51;
        public static final int POST_INSTALL_RUNNER_ERROR = 5;
        public static final int SUCCESS = 0;
        public static final int UPDATED_BUT_NOT_ACTIVE = 52;
    }

    public static final class UpdateStatusConstants {
        public static final int ATTEMPTING_ROLLBACK = 8;
        public static final int CHECKING_FOR_UPDATE = 1;
        public static final int DISABLED = 9;
        public static final int DOWNLOADING = 3;
        public static final int FINALIZING = 5;
        public static final int IDLE = 0;
        public static final int REPORTING_ERROR_EVENT = 7;
        public static final int UPDATED_NEED_REBOOT = 6;
        public static final int UPDATE_AVAILABLE = 2;
        public static final int VERIFYING = 4;
    }

    public UpdateEngine() {
        if (this.mUpdateEngine == null) {
            throw new java.lang.IllegalStateException("Failed to find update_engine");
        }
    }

    public boolean bind(final android.os.UpdateEngineCallback updateEngineCallback, final android.os.Handler handler) {
        boolean bind;
        synchronized (this.mUpdateEngineCallbackLock) {
            this.mUpdateEngineCallback = new android.os.IUpdateEngineCallback.Stub() { // from class: android.os.UpdateEngine.1
                @Override // android.os.IUpdateEngineCallback
                public void onStatusUpdate(final int i, final float f) {
                    if (handler != null) {
                        handler.post(new java.lang.Runnable() { // from class: android.os.UpdateEngine.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                updateEngineCallback.onStatusUpdate(i, f);
                            }
                        });
                    } else {
                        updateEngineCallback.onStatusUpdate(i, f);
                    }
                }

                @Override // android.os.IUpdateEngineCallback
                public void onPayloadApplicationComplete(final int i) {
                    if (handler != null) {
                        handler.post(new java.lang.Runnable() { // from class: android.os.UpdateEngine.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                updateEngineCallback.onPayloadApplicationComplete(i);
                            }
                        });
                    } else {
                        updateEngineCallback.onPayloadApplicationComplete(i);
                    }
                }
            };
            try {
                bind = this.mUpdateEngine.bind(this.mUpdateEngineCallback);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return bind;
    }

    public boolean bind(android.os.UpdateEngineCallback updateEngineCallback) {
        return bind(updateEngineCallback, null);
    }

    public void applyPayload(java.lang.String str, long j, long j2, java.lang.String[] strArr) {
        try {
            this.mUpdateEngine.applyPayload(str, j, j2, strArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void applyPayload(android.content.res.AssetFileDescriptor assetFileDescriptor, java.lang.String[] strArr) {
        try {
            this.mUpdateEngine.applyPayloadFd(assetFileDescriptor.getParcelFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength(), strArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cancel() {
        try {
            this.mUpdateEngine.cancel();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void suspend() {
        try {
            this.mUpdateEngine.suspend();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resume() {
        try {
            this.mUpdateEngine.resume();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetStatus() {
        try {
            this.mUpdateEngine.resetStatus();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setShouldSwitchSlotOnReboot(java.lang.String str) {
        try {
            this.mUpdateEngine.setShouldSwitchSlotOnReboot(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void resetShouldSwitchSlotOnReboot() {
        try {
            this.mUpdateEngine.resetShouldSwitchSlotOnReboot();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setPerformanceMode(boolean z) {
        try {
            this.mUpdateEngine.setPerformanceMode(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean unbind() {
        synchronized (this.mUpdateEngineCallbackLock) {
            if (this.mUpdateEngineCallback == null) {
                return true;
            }
            try {
                boolean unbind = this.mUpdateEngine.unbind(this.mUpdateEngineCallback);
                this.mUpdateEngineCallback = null;
                return unbind;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean verifyPayloadMetadata(java.lang.String str) {
        try {
            return this.mUpdateEngine.verifyPayloadApplicable(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public static final class AllocateSpaceResult {
        private int mErrorCode;
        private long mFreeSpaceRequired;

        private AllocateSpaceResult() {
            this.mErrorCode = 0;
            this.mFreeSpaceRequired = 0L;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        public long getFreeSpaceRequired() {
            if (this.mErrorCode == 0) {
                return 0L;
            }
            if (this.mErrorCode == 60) {
                return this.mFreeSpaceRequired;
            }
            throw new java.lang.IllegalStateException(java.lang.String.format("getFreeSpaceRequired() is not available when error code is %d", java.lang.Integer.valueOf(this.mErrorCode)));
        }
    }

    public android.os.UpdateEngine.AllocateSpaceResult allocateSpace(java.lang.String str, java.lang.String[] strArr) {
        int i;
        android.os.UpdateEngine.AllocateSpaceResult allocateSpaceResult = new android.os.UpdateEngine.AllocateSpaceResult();
        try {
            allocateSpaceResult.mFreeSpaceRequired = this.mUpdateEngine.allocateSpaceForPayload(str, strArr);
            if (allocateSpaceResult.mFreeSpaceRequired == 0) {
                i = 0;
            } else {
                i = 60;
            }
            allocateSpaceResult.mErrorCode = i;
            return allocateSpaceResult;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        } catch (android.os.ServiceSpecificException e2) {
            allocateSpaceResult.mErrorCode = e2.errorCode;
            allocateSpaceResult.mFreeSpaceRequired = 0L;
            return allocateSpaceResult;
        }
    }

    private static class CleanupAppliedPayloadCallback extends android.os.IUpdateEngineCallback.Stub {
        private boolean mCompleted;
        private int mErrorCode;
        private java.lang.Object mLock;

        private CleanupAppliedPayloadCallback() {
            this.mErrorCode = 1;
            this.mCompleted = false;
            this.mLock = new java.lang.Object();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getResult() {
            int i;
            synchronized (this.mLock) {
                while (!this.mCompleted) {
                    try {
                        this.mLock.wait();
                    } catch (java.lang.InterruptedException e) {
                    }
                }
                i = this.mErrorCode;
            }
            return i;
        }

        @Override // android.os.IUpdateEngineCallback
        public void onStatusUpdate(int i, float f) {
        }

        @Override // android.os.IUpdateEngineCallback
        public void onPayloadApplicationComplete(int i) {
            synchronized (this.mLock) {
                this.mErrorCode = i;
                this.mCompleted = true;
                this.mLock.notifyAll();
            }
        }
    }

    public int cleanupAppliedPayload() {
        android.os.UpdateEngine.CleanupAppliedPayloadCallback cleanupAppliedPayloadCallback = new android.os.UpdateEngine.CleanupAppliedPayloadCallback();
        try {
            this.mUpdateEngine.cleanupSuccessfulUpdate(cleanupAppliedPayloadCallback);
            return cleanupAppliedPayloadCallback.getResult();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
