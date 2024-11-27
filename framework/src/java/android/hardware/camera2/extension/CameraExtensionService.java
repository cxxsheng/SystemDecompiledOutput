package android.hardware.camera2.extension;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public abstract class CameraExtensionService extends android.app.Service {
    private static final java.lang.String TAG = "CameraExtensionService";
    private android.hardware.camera2.extension.CameraUsageTracker mCameraUsageTracker;
    private android.os.IBinder.DeathRecipient mDeathRecipient = new android.os.IBinder.DeathRecipient() { // from class: android.hardware.camera2.extension.CameraExtensionService.1
        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (android.hardware.camera2.extension.CameraExtensionService.mLock) {
                android.hardware.camera2.extension.CameraExtensionService.mInitializeCb = null;
            }
            if (android.hardware.camera2.extension.CameraExtensionService.this.mCameraUsageTracker != null) {
                android.hardware.camera2.extension.CameraExtensionService.this.mCameraUsageTracker.finishCameraOperation();
            }
        }
    };
    private static java.lang.Object mLock = new java.lang.Object();
    private static android.hardware.camera2.extension.IInitializeSessionCallback mInitializeCb = null;

    public abstract android.hardware.camera2.extension.AdvancedExtender onInitializeAdvancedExtension(int i);

    public abstract boolean onRegisterClient(android.os.IBinder iBinder);

    public abstract void onUnregisterClient(android.os.IBinder iBinder);

    private final class CameraTracker implements android.hardware.camera2.extension.CameraUsageTracker {
        private final android.app.AppOpsManager mAppOpsService;
        private final java.lang.String mAttributionTag;
        private final java.lang.String mPackageName;
        private int mUid;

        private CameraTracker() {
            this.mAppOpsService = (android.app.AppOpsManager) android.hardware.camera2.extension.CameraExtensionService.this.getApplicationContext().getSystemService(android.app.AppOpsManager.class);
            this.mPackageName = android.hardware.camera2.extension.CameraExtensionService.this.getPackageName();
            this.mAttributionTag = android.hardware.camera2.extension.CameraExtensionService.this.getAttributionTag();
            this.mUid = android.hardware.camera2.extension.CameraExtensionService.this.getApplicationInfo().uid;
        }

        @Override // android.hardware.camera2.extension.CameraUsageTracker
        public void startCameraOperation() {
            if (this.mAppOpsService != null) {
                this.mAppOpsService.startOp(android.app.AppOpsManager.OPSTR_CAMERA, this.mUid, this.mPackageName, this.mAttributionTag, "Camera extensions");
            }
        }

        @Override // android.hardware.camera2.extension.CameraUsageTracker
        public void finishCameraOperation() {
            if (this.mAppOpsService != null) {
                this.mAppOpsService.finishOp(android.app.AppOpsManager.OPSTR_CAMERA, this.mUid, this.mPackageName, this.mAttributionTag);
            }
        }
    }

    protected CameraExtensionService() {
    }

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        byte b = 0;
        if (this.mCameraUsageTracker == null) {
            this.mCameraUsageTracker = new android.hardware.camera2.extension.CameraExtensionService.CameraTracker();
        }
        return new android.hardware.camera2.extension.CameraExtensionService.CameraExtensionServiceImpl();
    }

    private class CameraExtensionServiceImpl extends android.hardware.camera2.extension.ICameraExtensionsProxyService.Stub {
        private CameraExtensionServiceImpl() {
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public boolean registerClient(android.os.IBinder iBinder) throws android.os.RemoteException {
            return android.hardware.camera2.extension.CameraExtensionService.this.onRegisterClient(iBinder);
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void unregisterClient(android.os.IBinder iBinder) throws android.os.RemoteException {
            android.hardware.camera2.extension.CameraExtensionService.this.onUnregisterClient(iBinder);
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public boolean advancedExtensionsSupported() throws android.os.RemoteException {
            return true;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void initializeSession(android.hardware.camera2.extension.IInitializeSessionCallback iInitializeSessionCallback) {
            boolean z;
            synchronized (android.hardware.camera2.extension.CameraExtensionService.mLock) {
                z = false;
                if (android.hardware.camera2.extension.CameraExtensionService.mInitializeCb == null) {
                    android.hardware.camera2.extension.CameraExtensionService.mInitializeCb = iInitializeSessionCallback;
                    try {
                        android.hardware.camera2.extension.CameraExtensionService.mInitializeCb.asBinder().linkToDeath(android.hardware.camera2.extension.CameraExtensionService.this.mDeathRecipient, 0);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.hardware.camera2.extension.CameraExtensionService.TAG, "Failure to register binder death notifier!");
                    }
                    z = true;
                }
            }
            try {
                if (z) {
                    iInitializeSessionCallback.onSuccess();
                } else {
                    iInitializeSessionCallback.onFailure();
                }
            } catch (android.os.RemoteException e2) {
                android.util.Log.e(android.hardware.camera2.extension.CameraExtensionService.TAG, "Client doesn't respond!");
            }
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public void releaseSession() {
            synchronized (android.hardware.camera2.extension.CameraExtensionService.mLock) {
                if (android.hardware.camera2.extension.CameraExtensionService.mInitializeCb != null) {
                    android.hardware.camera2.extension.CameraExtensionService.mInitializeCb.asBinder().unlinkToDeath(android.hardware.camera2.extension.CameraExtensionService.this.mDeathRecipient, 0);
                    android.hardware.camera2.extension.CameraExtensionService.mInitializeCb = null;
                }
            }
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public android.hardware.camera2.extension.IPreviewExtenderImpl initializePreviewExtension(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public android.hardware.camera2.extension.IImageCaptureExtenderImpl initializeImageExtension(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ICameraExtensionsProxyService
        public android.hardware.camera2.extension.IAdvancedExtenderImpl initializeAdvancedExtension(int i) throws android.os.RemoteException {
            android.hardware.camera2.extension.AdvancedExtender onInitializeAdvancedExtension = android.hardware.camera2.extension.CameraExtensionService.this.onInitializeAdvancedExtension(i);
            onInitializeAdvancedExtension.setCameraUsageTracker(android.hardware.camera2.extension.CameraExtensionService.this.mCameraUsageTracker);
            return onInitializeAdvancedExtension.getAdvancedExtenderBinder();
        }
    }
}
