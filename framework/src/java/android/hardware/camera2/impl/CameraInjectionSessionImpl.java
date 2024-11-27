package android.hardware.camera2.impl;

/* loaded from: classes.dex */
public class CameraInjectionSessionImpl extends android.hardware.camera2.CameraInjectionSession implements android.os.IBinder.DeathRecipient {
    private static final java.lang.String TAG = "CameraInjectionSessionImpl";
    private final java.util.concurrent.Executor mExecutor;
    private android.hardware.camera2.ICameraInjectionSession mInjectionSession;
    private final android.hardware.camera2.CameraInjectionSession.InjectionStatusCallback mInjectionStatusCallback;
    private final android.hardware.camera2.impl.CameraInjectionSessionImpl.CameraInjectionCallback mCallback = new android.hardware.camera2.impl.CameraInjectionSessionImpl.CameraInjectionCallback();
    private final java.lang.Object mInterfaceLock = new java.lang.Object();

    public CameraInjectionSessionImpl(android.hardware.camera2.CameraInjectionSession.InjectionStatusCallback injectionStatusCallback, java.util.concurrent.Executor executor) {
        this.mInjectionStatusCallback = injectionStatusCallback;
        this.mExecutor = executor;
    }

    @Override // android.hardware.camera2.CameraInjectionSession, java.lang.AutoCloseable
    public void close() {
        synchronized (this.mInterfaceLock) {
            try {
                if (this.mInjectionSession != null) {
                    this.mInjectionSession.stopInjection();
                    this.mInjectionSession.asBinder().unlinkToDeath(this, 0);
                    this.mInjectionSession = null;
                }
            } catch (android.os.RemoteException e) {
            }
        }
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        synchronized (this.mInterfaceLock) {
            android.util.Log.w(TAG, "CameraInjectionSessionImpl died unexpectedly");
            if (this.mInjectionSession == null) {
                return;
            }
            java.lang.Runnable runnable = new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraInjectionSessionImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    android.hardware.camera2.impl.CameraInjectionSessionImpl.this.mInjectionStatusCallback.onInjectionError(1);
                }
            };
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(runnable);
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public android.hardware.camera2.impl.CameraInjectionSessionImpl.CameraInjectionCallback getCallback() {
        return this.mCallback;
    }

    public void setRemoteInjectionSession(android.hardware.camera2.ICameraInjectionSession iCameraInjectionSession) {
        synchronized (this.mInterfaceLock) {
            if (iCameraInjectionSession == null) {
                android.util.Log.e(TAG, "The camera injection session has encountered a serious error");
                scheduleNotifyError(0);
                return;
            }
            this.mInjectionSession = iCameraInjectionSession;
            android.os.IBinder asBinder = iCameraInjectionSession.asBinder();
            if (asBinder == null) {
                android.util.Log.e(TAG, "The camera injection session has encountered a serious error");
                scheduleNotifyError(0);
                return;
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                try {
                    asBinder.linkToDeath(this, 0);
                    this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.camera2.impl.CameraInjectionSessionImpl.2
                        @Override // java.lang.Runnable
                        public void run() {
                            android.hardware.camera2.impl.CameraInjectionSessionImpl.this.mInjectionStatusCallback.onInjectionSucceeded(android.hardware.camera2.impl.CameraInjectionSessionImpl.this);
                        }
                    });
                } catch (android.os.RemoteException e) {
                    scheduleNotifyError(0);
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void onInjectionError(int i) {
        android.util.Log.v(TAG, java.lang.String.format("Injection session error received, code %d", java.lang.Integer.valueOf(i)));
        synchronized (this.mInterfaceLock) {
            if (this.mInjectionSession == null) {
                return;
            }
            switch (i) {
                case 0:
                    scheduleNotifyError(0);
                    break;
                case 1:
                    scheduleNotifyError(1);
                    break;
                case 2:
                    scheduleNotifyError(2);
                    break;
                default:
                    android.util.Log.e(TAG, "Unknown error from injection session: " + i);
                    scheduleNotifyError(1);
                    break;
            }
        }
    }

    private void scheduleNotifyError(int i) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(com.android.internal.util.function.pooled.PooledLambda.obtainRunnable(new java.util.function.BiConsumer() { // from class: android.hardware.camera2.impl.CameraInjectionSessionImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                    ((android.hardware.camera2.impl.CameraInjectionSessionImpl) obj).notifyError(((java.lang.Integer) obj2).intValue());
                }
            }, this, java.lang.Integer.valueOf(i)).recycleOnUse());
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(int i) {
        if (this.mInjectionSession != null) {
            this.mInjectionStatusCallback.onInjectionError(i);
        }
    }

    public class CameraInjectionCallback extends android.hardware.camera2.ICameraInjectionCallback.Stub {
        public CameraInjectionCallback() {
        }

        @Override // android.hardware.camera2.ICameraInjectionCallback.Stub, android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.hardware.camera2.ICameraInjectionCallback
        public void onInjectionError(int i) {
            android.hardware.camera2.impl.CameraInjectionSessionImpl.this.onInjectionError(i);
        }
    }
}
