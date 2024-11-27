package android.os;

/* loaded from: classes3.dex */
public class UpdateEngineStable {
    private static final java.lang.String TAG = "UpdateEngineStable";
    private static final java.lang.String UPDATE_ENGINE_STABLE_SERVICE = "android.os.UpdateEngineStableService";
    private android.os.IUpdateEngineStableCallback mUpdateEngineStableCallback = null;
    private final java.lang.Object mUpdateEngineStableCallbackLock = new java.lang.Object();
    private final android.os.IUpdateEngineStable mUpdateEngineStable = android.os.IUpdateEngineStable.Stub.asInterface(android.os.ServiceManager.getService(UPDATE_ENGINE_STABLE_SERVICE));

    public @interface ErrorCode {
    }

    public UpdateEngineStable() {
        if (this.mUpdateEngineStable == null) {
            throw new java.lang.IllegalStateException("Failed to find android.os.UpdateEngineStableService");
        }
    }

    public boolean bind(final android.os.UpdateEngineStableCallback updateEngineStableCallback, final android.os.Handler handler) {
        boolean bind;
        synchronized (this.mUpdateEngineStableCallbackLock) {
            this.mUpdateEngineStableCallback = new android.os.IUpdateEngineStableCallback.Stub() { // from class: android.os.UpdateEngineStable.1
                @Override // android.os.IUpdateEngineStableCallback
                public void onStatusUpdate(final int i, final float f) {
                    if (handler != null) {
                        handler.post(new java.lang.Runnable() { // from class: android.os.UpdateEngineStable.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                updateEngineStableCallback.onStatusUpdate(i, f);
                            }
                        });
                    } else {
                        updateEngineStableCallback.onStatusUpdate(i, f);
                    }
                }

                @Override // android.os.IUpdateEngineStableCallback
                public void onPayloadApplicationComplete(final int i) {
                    if (handler != null) {
                        handler.post(new java.lang.Runnable() { // from class: android.os.UpdateEngineStable.1.2
                            @Override // java.lang.Runnable
                            public void run() {
                                updateEngineStableCallback.onPayloadApplicationComplete(i);
                            }
                        });
                    } else {
                        updateEngineStableCallback.onPayloadApplicationComplete(i);
                    }
                }

                @Override // android.os.IUpdateEngineStableCallback
                public int getInterfaceVersion() {
                    return 2;
                }

                @Override // android.os.IUpdateEngineStableCallback
                public java.lang.String getInterfaceHash() {
                    return "ee2e6f0bd51391955f79f4d5eeeafc37c668cd40";
                }
            };
            try {
                bind = this.mUpdateEngineStable.bind(this.mUpdateEngineStableCallback);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return bind;
    }

    public boolean bind(android.os.UpdateEngineStableCallback updateEngineStableCallback) {
        return bind(updateEngineStableCallback, null);
    }

    public void applyPayloadFd(android.os.ParcelFileDescriptor parcelFileDescriptor, long j, long j2, java.lang.String[] strArr) {
        try {
            this.mUpdateEngineStable.applyPayloadFd(parcelFileDescriptor, j, j2, strArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean unbind() {
        synchronized (this.mUpdateEngineStableCallbackLock) {
            if (this.mUpdateEngineStableCallback == null) {
                return true;
            }
            try {
                boolean unbind = this.mUpdateEngineStable.unbind(this.mUpdateEngineStableCallback);
                this.mUpdateEngineStableCallback = null;
                return unbind;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
