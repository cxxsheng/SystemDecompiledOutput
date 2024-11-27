package android.companion.virtual;

/* loaded from: classes.dex */
public final class VirtualDeviceManager {
    public static final java.lang.String ACTION_VIRTUAL_DEVICE_REMOVED = "android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED";
    public static final java.lang.String EXTRA_VIRTUAL_DEVICE_ID = "android.companion.virtual.extra.VIRTUAL_DEVICE_ID";

    @android.annotation.SystemApi
    public static final int LAUNCH_FAILURE_NO_ACTIVITY = 2;

    @android.annotation.SystemApi
    public static final int LAUNCH_FAILURE_PENDING_INTENT_CANCELED = 1;

    @android.annotation.SystemApi
    public static final int LAUNCH_SUCCESS = 0;

    @android.annotation.SystemApi
    public static final java.lang.String PERSISTENT_DEVICE_ID_DEFAULT = "default:0";
    private static final java.lang.String TAG = "VirtualDeviceManager";
    private final android.content.Context mContext;
    private final android.companion.virtual.IVirtualDeviceManager mService;
    private final java.util.List<android.companion.virtual.VirtualDeviceManager.VirtualDeviceListenerDelegate> mVirtualDeviceListeners = new java.util.ArrayList();

    @android.annotation.SystemApi
    public interface IntentInterceptorCallback {
        void onIntentIntercepted(android.content.Intent intent);
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_PARAMETER, java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PendingIntentLaunchStatus {
    }

    @android.annotation.SystemApi
    public interface SoundEffectListener {
        void onPlaySoundEffect(int i);
    }

    public VirtualDeviceManager(android.companion.virtual.IVirtualDeviceManager iVirtualDeviceManager, android.content.Context context) {
        this.mService = iVirtualDeviceManager;
        this.mContext = context;
    }

    @android.annotation.SystemApi
    public android.companion.virtual.VirtualDeviceManager.VirtualDevice createVirtualDevice(int i, android.companion.virtual.VirtualDeviceParams virtualDeviceParams) {
        java.util.Objects.requireNonNull(virtualDeviceParams, "params must not be null");
        try {
            return new android.companion.virtual.VirtualDeviceManager.VirtualDevice(this.mService, this.mContext, i, virtualDeviceParams);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.companion.virtual.VirtualDevice> getVirtualDevices() {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return new java.util.ArrayList();
        }
        try {
            return this.mService.getVirtualDevices();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.companion.virtual.VirtualDevice getVirtualDevice(int i) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return null;
        }
        if (i == -1 || i == 0) {
            return null;
        }
        try {
            return this.mService.getVirtualDevice(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerVirtualDeviceListener(java.util.concurrent.Executor executor, android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener virtualDeviceListener) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to register listener; no virtual device manager service.");
            return;
        }
        android.companion.virtual.VirtualDeviceManager.VirtualDeviceListenerDelegate virtualDeviceListenerDelegate = new android.companion.virtual.VirtualDeviceManager.VirtualDeviceListenerDelegate((java.util.concurrent.Executor) java.util.Objects.requireNonNull(executor), (android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener) java.util.Objects.requireNonNull(virtualDeviceListener));
        synchronized (this.mVirtualDeviceListeners) {
            try {
                try {
                    this.mService.registerVirtualDeviceListener(virtualDeviceListenerDelegate);
                    this.mVirtualDeviceListeners.add(virtualDeviceListenerDelegate);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterVirtualDeviceListener(android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener virtualDeviceListener) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to unregister listener; no virtual device manager service.");
            return;
        }
        java.util.Objects.requireNonNull(virtualDeviceListener);
        synchronized (this.mVirtualDeviceListeners) {
            java.util.Iterator<android.companion.virtual.VirtualDeviceManager.VirtualDeviceListenerDelegate> it = this.mVirtualDeviceListeners.iterator();
            while (it.hasNext()) {
                android.companion.virtual.VirtualDeviceManager.VirtualDeviceListenerDelegate next = it.next();
                if (next.mListener == virtualDeviceListener) {
                    try {
                        this.mService.unregisterVirtualDeviceListener(next);
                        it.remove();
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
        }
    }

    public int getDevicePolicy(int i, int i2) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to retrieve device policy; no virtual device manager service.");
            return 0;
        }
        try {
            return this.mService.getDevicePolicy(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getDeviceIdForDisplayId(int i) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return 0;
        }
        try {
            return this.mService.getDeviceIdForDisplayId(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.lang.CharSequence getDisplayNameForPersistentDeviceId(java.lang.String str) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return null;
        }
        try {
            return this.mService.getDisplayNameForPersistentDeviceId((java.lang.String) java.util.Objects.requireNonNull(str));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.Set<java.lang.String> getAllPersistentDeviceIds() {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to retrieve persistent ids; no virtual device manager service.");
            return java.util.Collections.emptySet();
        }
        try {
            return new android.util.ArraySet(this.mService.getAllPersistentDeviceIds());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isValidVirtualDeviceId(int i) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return false;
        }
        try {
            return this.mService.isValidVirtualDeviceId(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAudioPlaybackSessionId(int i) {
        if (this.mService == null) {
            return 0;
        }
        try {
            return this.mService.getAudioPlaybackSessionId(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getAudioRecordingSessionId(int i) {
        if (this.mService == null) {
            return 0;
        }
        try {
            return this.mService.getAudioRecordingSessionId(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void playSoundEffect(int i, int i2) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to dispatch sound effect; no virtual device manager service.");
            return;
        }
        try {
            this.mService.playSoundEffect(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isVirtualDeviceOwnedMirrorDisplay(int i) {
        if (this.mService == null) {
            android.util.Log.w(TAG, "Failed to retrieve virtual devices; no virtual device manager service.");
            return false;
        }
        try {
            return this.mService.isVirtualDeviceOwnedMirrorDisplay(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public static class VirtualDevice implements java.lang.AutoCloseable {
        private final android.companion.virtual.VirtualDeviceInternal mVirtualDeviceInternal;

        private VirtualDevice(android.companion.virtual.IVirtualDeviceManager iVirtualDeviceManager, android.content.Context context, int i, android.companion.virtual.VirtualDeviceParams virtualDeviceParams) throws android.os.RemoteException {
            this.mVirtualDeviceInternal = new android.companion.virtual.VirtualDeviceInternal(iVirtualDeviceManager, context, i, virtualDeviceParams);
        }

        public int getDeviceId() {
            return this.mVirtualDeviceInternal.getDeviceId();
        }

        public java.lang.String getPersistentDeviceId() {
            return this.mVirtualDeviceInternal.getPersistentDeviceId();
        }

        public android.content.Context createContext() {
            return this.mVirtualDeviceInternal.createContext();
        }

        public java.util.List<android.companion.virtual.sensor.VirtualSensor> getVirtualSensorList() {
            return this.mVirtualDeviceInternal.getVirtualSensorList();
        }

        public void launchPendingIntent(int i, android.app.PendingIntent pendingIntent, java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
            java.util.Objects.requireNonNull(pendingIntent, "pendingIntent must not be null");
            java.util.Objects.requireNonNull(executor, "executor must not be null");
            java.util.Objects.requireNonNull(intConsumer, "listener must not be null");
            this.mVirtualDeviceInternal.launchPendingIntent(i, pendingIntent, executor, intConsumer);
        }

        @java.lang.Deprecated
        public android.hardware.display.VirtualDisplay createVirtualDisplay(int i, int i2, int i3, android.view.Surface surface, int i4, java.util.concurrent.Executor executor, android.hardware.display.VirtualDisplay.Callback callback) {
            android.hardware.display.VirtualDisplayConfig.Builder flags = new android.hardware.display.VirtualDisplayConfig.Builder("VirtualDevice_" + getDeviceId(), i, i2, i3).setFlags(i4);
            if (surface != null) {
                flags.setSurface(surface);
            }
            return this.mVirtualDeviceInternal.createVirtualDisplay(flags.build(), executor, callback);
        }

        public android.hardware.display.VirtualDisplay createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, java.util.concurrent.Executor executor, android.hardware.display.VirtualDisplay.Callback callback) {
            java.util.Objects.requireNonNull(virtualDisplayConfig, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualDisplay(virtualDisplayConfig, executor, callback);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            this.mVirtualDeviceInternal.close();
        }

        public void setDevicePolicy(int i, int i2) {
            this.mVirtualDeviceInternal.setDevicePolicy(i, i2);
        }

        public void addActivityPolicyExemption(android.content.ComponentName componentName) {
            this.mVirtualDeviceInternal.addActivityPolicyExemption((android.content.ComponentName) java.util.Objects.requireNonNull(componentName));
        }

        public void removeActivityPolicyExemption(android.content.ComponentName componentName) {
            this.mVirtualDeviceInternal.removeActivityPolicyExemption((android.content.ComponentName) java.util.Objects.requireNonNull(componentName));
        }

        public android.hardware.input.VirtualDpad createVirtualDpad(android.hardware.input.VirtualDpadConfig virtualDpadConfig) {
            java.util.Objects.requireNonNull(virtualDpadConfig, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualDpad(virtualDpadConfig);
        }

        public android.hardware.input.VirtualKeyboard createVirtualKeyboard(android.hardware.input.VirtualKeyboardConfig virtualKeyboardConfig) {
            java.util.Objects.requireNonNull(virtualKeyboardConfig, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualKeyboard(virtualKeyboardConfig);
        }

        @java.lang.Deprecated
        public android.hardware.input.VirtualKeyboard createVirtualKeyboard(android.hardware.display.VirtualDisplay virtualDisplay, java.lang.String str, int i, int i2) {
            return this.mVirtualDeviceInternal.createVirtualKeyboard(new android.hardware.input.VirtualKeyboardConfig.Builder().setVendorId(i).setProductId(i2).setInputDeviceName(str).setAssociatedDisplayId(virtualDisplay.getDisplay().getDisplayId()).build());
        }

        public android.hardware.input.VirtualMouse createVirtualMouse(android.hardware.input.VirtualMouseConfig virtualMouseConfig) {
            java.util.Objects.requireNonNull(virtualMouseConfig, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualMouse(virtualMouseConfig);
        }

        @java.lang.Deprecated
        public android.hardware.input.VirtualMouse createVirtualMouse(android.hardware.display.VirtualDisplay virtualDisplay, java.lang.String str, int i, int i2) {
            return this.mVirtualDeviceInternal.createVirtualMouse(new android.hardware.input.VirtualMouseConfig.Builder().setVendorId(i).setProductId(i2).setInputDeviceName(str).setAssociatedDisplayId(virtualDisplay.getDisplay().getDisplayId()).build());
        }

        public android.hardware.input.VirtualTouchscreen createVirtualTouchscreen(android.hardware.input.VirtualTouchscreenConfig virtualTouchscreenConfig) {
            java.util.Objects.requireNonNull(virtualTouchscreenConfig, "config must not be null");
            return this.mVirtualDeviceInternal.createVirtualTouchscreen(virtualTouchscreenConfig);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @java.lang.Deprecated
        public android.hardware.input.VirtualTouchscreen createVirtualTouchscreen(android.hardware.display.VirtualDisplay virtualDisplay, java.lang.String str, int i, int i2) {
            android.graphics.Point point = new android.graphics.Point();
            virtualDisplay.getDisplay().getSize(point);
            return this.mVirtualDeviceInternal.createVirtualTouchscreen(((android.hardware.input.VirtualTouchscreenConfig.Builder) ((android.hardware.input.VirtualTouchscreenConfig.Builder) ((android.hardware.input.VirtualTouchscreenConfig.Builder) ((android.hardware.input.VirtualTouchscreenConfig.Builder) new android.hardware.input.VirtualTouchscreenConfig.Builder(point.x, point.y).setVendorId(i)).setProductId(i2)).setInputDeviceName(str)).setAssociatedDisplayId(virtualDisplay.getDisplay().getDisplayId())).build());
        }

        public android.hardware.input.VirtualNavigationTouchpad createVirtualNavigationTouchpad(android.hardware.input.VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig) {
            return this.mVirtualDeviceInternal.createVirtualNavigationTouchpad(virtualNavigationTouchpadConfig);
        }

        public android.hardware.input.VirtualStylus createVirtualStylus(android.hardware.input.VirtualStylusConfig virtualStylusConfig) {
            return this.mVirtualDeviceInternal.createVirtualStylus(virtualStylusConfig);
        }

        public android.companion.virtual.audio.VirtualAudioDevice createVirtualAudioDevice(android.hardware.display.VirtualDisplay virtualDisplay, java.util.concurrent.Executor executor, android.companion.virtual.audio.VirtualAudioDevice.AudioConfigurationChangeCallback audioConfigurationChangeCallback) {
            java.util.Objects.requireNonNull(virtualDisplay, "display must not be null");
            return this.mVirtualDeviceInternal.createVirtualAudioDevice(virtualDisplay, executor, audioConfigurationChangeCallback);
        }

        public android.companion.virtual.camera.VirtualCamera createVirtualCamera(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) {
            if (!android.companion.virtual.flags.Flags.virtualCamera()) {
                throw new java.lang.UnsupportedOperationException("Flag is not enabled: %s".formatted(android.companion.virtual.flags.Flags.FLAG_VIRTUAL_CAMERA));
            }
            return this.mVirtualDeviceInternal.createVirtualCamera((android.companion.virtual.camera.VirtualCameraConfig) java.util.Objects.requireNonNull(virtualCameraConfig));
        }

        public void setShowPointerIcon(boolean z) {
            this.mVirtualDeviceInternal.setShowPointerIcon(z);
        }

        public void setDisplayImePolicy(int i, int i2) {
            if (android.companion.virtual.flags.Flags.vdmCustomIme()) {
                this.mVirtualDeviceInternal.setDisplayImePolicy(i, i2);
            }
        }

        public void addActivityListener(java.util.concurrent.Executor executor, android.companion.virtual.VirtualDeviceManager.ActivityListener activityListener) {
            this.mVirtualDeviceInternal.addActivityListener(executor, activityListener);
        }

        public void removeActivityListener(android.companion.virtual.VirtualDeviceManager.ActivityListener activityListener) {
            this.mVirtualDeviceInternal.removeActivityListener(activityListener);
        }

        public void addSoundEffectListener(java.util.concurrent.Executor executor, android.companion.virtual.VirtualDeviceManager.SoundEffectListener soundEffectListener) {
            this.mVirtualDeviceInternal.addSoundEffectListener(executor, soundEffectListener);
        }

        public void removeSoundEffectListener(android.companion.virtual.VirtualDeviceManager.SoundEffectListener soundEffectListener) {
            this.mVirtualDeviceInternal.removeSoundEffectListener(soundEffectListener);
        }

        public void registerIntentInterceptor(android.content.IntentFilter intentFilter, java.util.concurrent.Executor executor, android.companion.virtual.VirtualDeviceManager.IntentInterceptorCallback intentInterceptorCallback) {
            this.mVirtualDeviceInternal.registerIntentInterceptor(intentFilter, executor, intentInterceptorCallback);
        }

        public void unregisterIntentInterceptor(android.companion.virtual.VirtualDeviceManager.IntentInterceptorCallback intentInterceptorCallback) {
            this.mVirtualDeviceInternal.unregisterIntentInterceptor(intentInterceptorCallback);
        }
    }

    @android.annotation.SystemApi
    public interface ActivityListener {
        void onDisplayEmpty(int i);

        void onTopActivityChanged(int i, android.content.ComponentName componentName);

        default void onTopActivityChanged(int i, android.content.ComponentName componentName, int i2) {
        }
    }

    public interface VirtualDeviceListener {
        default void onVirtualDeviceCreated(int i) {
        }

        default void onVirtualDeviceClosed(int i) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class VirtualDeviceListenerDelegate extends android.companion.virtual.IVirtualDeviceListener.Stub {
        private final java.util.concurrent.Executor mExecutor;
        private final android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener mListener;

        private VirtualDeviceListenerDelegate(java.util.concurrent.Executor executor, android.companion.virtual.VirtualDeviceManager.VirtualDeviceListener virtualDeviceListener) {
            this.mExecutor = executor;
            this.mListener = virtualDeviceListener;
        }

        @Override // android.companion.virtual.IVirtualDeviceListener
        public void onVirtualDeviceCreated(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.VirtualDeviceManager$VirtualDeviceListenerDelegate$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.companion.virtual.VirtualDeviceManager.VirtualDeviceListenerDelegate.this.lambda$onVirtualDeviceCreated$0(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVirtualDeviceCreated$0(int i) {
            this.mListener.onVirtualDeviceCreated(i);
        }

        @Override // android.companion.virtual.IVirtualDeviceListener
        public void onVirtualDeviceClosed(final int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.VirtualDeviceManager$VirtualDeviceListenerDelegate$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.companion.virtual.VirtualDeviceManager.VirtualDeviceListenerDelegate.this.lambda$onVirtualDeviceClosed$1(i);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVirtualDeviceClosed$1(int i) {
            this.mListener.onVirtualDeviceClosed(i);
        }
    }
}
