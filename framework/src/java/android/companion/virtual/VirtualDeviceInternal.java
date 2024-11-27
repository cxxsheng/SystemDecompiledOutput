package android.companion.virtual;

/* loaded from: classes.dex */
public class VirtualDeviceInternal {
    private final android.content.Context mContext;
    private final android.companion.virtual.IVirtualDeviceManager mService;
    private android.companion.virtual.audio.VirtualAudioDevice mVirtualAudioDevice;
    private final android.companion.virtual.IVirtualDevice mVirtualDevice;
    private final java.lang.Object mActivityListenersLock = new java.lang.Object();
    private final android.util.ArrayMap<android.companion.virtual.VirtualDeviceManager.ActivityListener, android.companion.virtual.VirtualDeviceInternal.ActivityListenerDelegate> mActivityListeners = new android.util.ArrayMap<>();
    private final java.lang.Object mIntentInterceptorListenersLock = new java.lang.Object();
    private final android.util.ArrayMap<android.companion.virtual.VirtualDeviceManager.IntentInterceptorCallback, android.companion.virtual.VirtualDeviceInternal.IntentInterceptorDelegate> mIntentInterceptorListeners = new android.util.ArrayMap<>();
    private final java.lang.Object mSoundEffectListenersLock = new java.lang.Object();
    private final android.util.ArrayMap<android.companion.virtual.VirtualDeviceManager.SoundEffectListener, android.companion.virtual.VirtualDeviceInternal.SoundEffectListenerDelegate> mSoundEffectListeners = new android.util.ArrayMap<>();
    private final android.companion.virtual.IVirtualDeviceActivityListener mActivityListenerBinder = new android.companion.virtual.IVirtualDeviceActivityListener.Stub() { // from class: android.companion.virtual.VirtualDeviceInternal.1
        @Override // android.companion.virtual.IVirtualDeviceActivityListener
        public void onTopActivityChanged(int i, android.content.ComponentName componentName, int i2) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (android.companion.virtual.VirtualDeviceInternal.this.mActivityListenersLock) {
                    for (int i3 = 0; i3 < android.companion.virtual.VirtualDeviceInternal.this.mActivityListeners.size(); i3++) {
                        ((android.companion.virtual.VirtualDeviceInternal.ActivityListenerDelegate) android.companion.virtual.VirtualDeviceInternal.this.mActivityListeners.valueAt(i3)).onTopActivityChanged(i, componentName);
                        ((android.companion.virtual.VirtualDeviceInternal.ActivityListenerDelegate) android.companion.virtual.VirtualDeviceInternal.this.mActivityListeners.valueAt(i3)).onTopActivityChanged(i, componentName, i2);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.companion.virtual.IVirtualDeviceActivityListener
        public void onDisplayEmpty(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (android.companion.virtual.VirtualDeviceInternal.this.mActivityListenersLock) {
                    for (int i2 = 0; i2 < android.companion.virtual.VirtualDeviceInternal.this.mActivityListeners.size(); i2++) {
                        ((android.companion.virtual.VirtualDeviceInternal.ActivityListenerDelegate) android.companion.virtual.VirtualDeviceInternal.this.mActivityListeners.valueAt(i2)).onDisplayEmpty(i);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    };
    private final android.companion.virtual.IVirtualDeviceSoundEffectListener mSoundEffectListener = new android.companion.virtual.IVirtualDeviceSoundEffectListener.Stub() { // from class: android.companion.virtual.VirtualDeviceInternal.2
        @Override // android.companion.virtual.IVirtualDeviceSoundEffectListener
        public void onPlaySoundEffect(int i) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                synchronized (android.companion.virtual.VirtualDeviceInternal.this.mSoundEffectListenersLock) {
                    for (int i2 = 0; i2 < android.companion.virtual.VirtualDeviceInternal.this.mSoundEffectListeners.size(); i2++) {
                        ((android.companion.virtual.VirtualDeviceInternal.SoundEffectListenerDelegate) android.companion.virtual.VirtualDeviceInternal.this.mSoundEffectListeners.valueAt(i2)).onPlaySoundEffect(i);
                    }
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    };

    VirtualDeviceInternal(android.companion.virtual.IVirtualDeviceManager iVirtualDeviceManager, android.content.Context context, int i, android.companion.virtual.VirtualDeviceParams virtualDeviceParams) throws android.os.RemoteException {
        this.mService = iVirtualDeviceManager;
        this.mContext = context.getApplicationContext();
        this.mVirtualDevice = iVirtualDeviceManager.createVirtualDevice(new android.os.Binder(), this.mContext.getAttributionSource(), i, virtualDeviceParams, this.mActivityListenerBinder, this.mSoundEffectListener);
    }

    int getDeviceId() {
        try {
            return this.mVirtualDevice.getDeviceId();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    java.lang.String getPersistentDeviceId() {
        try {
            return this.mVirtualDevice.getPersistentDeviceId();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    android.content.Context createContext() {
        try {
            return this.mContext.createDeviceContext(this.mVirtualDevice.getDeviceId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    java.util.List<android.companion.virtual.sensor.VirtualSensor> getVirtualSensorList() {
        try {
            return this.mVirtualDevice.getVirtualSensorList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void launchPendingIntent(int i, android.app.PendingIntent pendingIntent, java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
        try {
            this.mVirtualDevice.launchPendingIntent(i, pendingIntent, new android.companion.virtual.VirtualDeviceInternal.AnonymousClass3(new android.os.Handler(android.os.Looper.getMainLooper()), executor, intConsumer));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* renamed from: android.companion.virtual.VirtualDeviceInternal$3, reason: invalid class name */
    class AnonymousClass3 extends android.os.ResultReceiver {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ java.util.function.IntConsumer val$listener;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(android.os.Handler handler, java.util.concurrent.Executor executor, java.util.function.IntConsumer intConsumer) {
            super(handler);
            this.val$executor = executor;
            this.val$listener = intConsumer;
        }

        @Override // android.os.ResultReceiver
        protected void onReceiveResult(final int i, android.os.Bundle bundle) {
            super.onReceiveResult(i, bundle);
            java.util.concurrent.Executor executor = this.val$executor;
            final java.util.function.IntConsumer intConsumer = this.val$listener;
            executor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    intConsumer.accept(i);
                }
            });
        }
    }

    android.hardware.display.VirtualDisplay createVirtualDisplay(android.hardware.display.VirtualDisplayConfig virtualDisplayConfig, java.util.concurrent.Executor executor, android.hardware.display.VirtualDisplay.Callback callback) {
        android.hardware.display.DisplayManagerGlobal.VirtualDisplayCallback virtualDisplayCallback = new android.hardware.display.DisplayManagerGlobal.VirtualDisplayCallback(callback, executor);
        try {
            return android.hardware.display.DisplayManagerGlobal.getInstance().createVirtualDisplayWrapper(virtualDisplayConfig, virtualDisplayCallback, this.mService.createVirtualDisplay(virtualDisplayConfig, virtualDisplayCallback, this.mVirtualDevice, this.mContext.getPackageName()));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void close() {
        try {
            this.mVirtualDevice.close();
            if (this.mVirtualAudioDevice != null) {
                this.mVirtualAudioDevice.close();
                this.mVirtualAudioDevice = null;
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setDevicePolicy(int i, int i2) {
        try {
            this.mVirtualDevice.setDevicePolicy(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void addActivityPolicyExemption(android.content.ComponentName componentName) {
        try {
            this.mVirtualDevice.addActivityPolicyExemption(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void removeActivityPolicyExemption(android.content.ComponentName componentName) {
        try {
            this.mVirtualDevice.removeActivityPolicyExemption(componentName);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    android.hardware.input.VirtualDpad createVirtualDpad(android.hardware.input.VirtualDpadConfig virtualDpadConfig) {
        try {
            android.os.Binder binder = new android.os.Binder("android.hardware.input.VirtualDpad:" + virtualDpadConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualDpad(virtualDpadConfig, binder);
            return new android.hardware.input.VirtualDpad(virtualDpadConfig, this.mVirtualDevice, binder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    android.hardware.input.VirtualKeyboard createVirtualKeyboard(android.hardware.input.VirtualKeyboardConfig virtualKeyboardConfig) {
        try {
            android.os.Binder binder = new android.os.Binder("android.hardware.input.VirtualKeyboard:" + virtualKeyboardConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualKeyboard(virtualKeyboardConfig, binder);
            return new android.hardware.input.VirtualKeyboard(virtualKeyboardConfig, this.mVirtualDevice, binder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    android.hardware.input.VirtualMouse createVirtualMouse(android.hardware.input.VirtualMouseConfig virtualMouseConfig) {
        try {
            android.os.Binder binder = new android.os.Binder("android.hardware.input.VirtualMouse:" + virtualMouseConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualMouse(virtualMouseConfig, binder);
            return new android.hardware.input.VirtualMouse(virtualMouseConfig, this.mVirtualDevice, binder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    android.hardware.input.VirtualTouchscreen createVirtualTouchscreen(android.hardware.input.VirtualTouchscreenConfig virtualTouchscreenConfig) {
        try {
            android.os.Binder binder = new android.os.Binder("android.hardware.input.VirtualTouchscreen:" + virtualTouchscreenConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualTouchscreen(virtualTouchscreenConfig, binder);
            return new android.hardware.input.VirtualTouchscreen(virtualTouchscreenConfig, this.mVirtualDevice, binder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    android.hardware.input.VirtualStylus createVirtualStylus(android.hardware.input.VirtualStylusConfig virtualStylusConfig) {
        try {
            android.os.Binder binder = new android.os.Binder("android.hardware.input.VirtualStylus:" + virtualStylusConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualStylus(virtualStylusConfig, binder);
            return new android.hardware.input.VirtualStylus(virtualStylusConfig, this.mVirtualDevice, binder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    android.hardware.input.VirtualNavigationTouchpad createVirtualNavigationTouchpad(android.hardware.input.VirtualNavigationTouchpadConfig virtualNavigationTouchpadConfig) {
        try {
            android.os.Binder binder = new android.os.Binder("android.hardware.input.VirtualNavigationTouchpad:" + virtualNavigationTouchpadConfig.getInputDeviceName());
            this.mVirtualDevice.createVirtualNavigationTouchpad(virtualNavigationTouchpadConfig, binder);
            return new android.hardware.input.VirtualNavigationTouchpad(virtualNavigationTouchpadConfig, this.mVirtualDevice, binder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    android.companion.virtual.audio.VirtualAudioDevice createVirtualAudioDevice(android.hardware.display.VirtualDisplay virtualDisplay, java.util.concurrent.Executor executor, android.companion.virtual.audio.VirtualAudioDevice.AudioConfigurationChangeCallback audioConfigurationChangeCallback) {
        android.content.Context context;
        if (this.mVirtualAudioDevice == null) {
            try {
                android.content.Context context2 = this.mContext;
                if (android.companion.virtualdevice.flags.Flags.deviceAwareRecordAudioPermission() && this.mVirtualDevice.getDevicePolicy(1) == 1) {
                    context = this.mContext.createDeviceContext(getDeviceId());
                } else {
                    context = context2;
                }
                this.mVirtualAudioDevice = new android.companion.virtual.audio.VirtualAudioDevice(context, this.mVirtualDevice, virtualDisplay, executor, audioConfigurationChangeCallback, new android.companion.virtual.audio.VirtualAudioDevice.CloseListener() { // from class: android.companion.virtual.VirtualDeviceInternal$$ExternalSyntheticLambda0
                    @Override // android.companion.virtual.audio.VirtualAudioDevice.CloseListener
                    public final void onClosed() {
                        android.companion.virtual.VirtualDeviceInternal.this.lambda$createVirtualAudioDevice$0();
                    }
                });
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mVirtualAudioDevice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createVirtualAudioDevice$0() {
        this.mVirtualAudioDevice = null;
    }

    android.companion.virtual.camera.VirtualCamera createVirtualCamera(android.companion.virtual.camera.VirtualCameraConfig virtualCameraConfig) {
        try {
            this.mVirtualDevice.registerVirtualCamera(virtualCameraConfig);
            return new android.companion.virtual.camera.VirtualCamera(this.mVirtualDevice, java.lang.Integer.toString(this.mVirtualDevice.getVirtualCameraId(virtualCameraConfig)), virtualCameraConfig);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setShowPointerIcon(boolean z) {
        try {
            this.mVirtualDevice.setShowPointerIcon(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void setDisplayImePolicy(int i, int i2) {
        try {
            this.mVirtualDevice.setDisplayImePolicy(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void addActivityListener(java.util.concurrent.Executor executor, android.companion.virtual.VirtualDeviceManager.ActivityListener activityListener) {
        android.companion.virtual.VirtualDeviceInternal.ActivityListenerDelegate activityListenerDelegate = new android.companion.virtual.VirtualDeviceInternal.ActivityListenerDelegate((android.companion.virtual.VirtualDeviceManager.ActivityListener) java.util.Objects.requireNonNull(activityListener), (java.util.concurrent.Executor) java.util.Objects.requireNonNull(executor));
        synchronized (this.mActivityListenersLock) {
            this.mActivityListeners.put(activityListener, activityListenerDelegate);
        }
    }

    void removeActivityListener(android.companion.virtual.VirtualDeviceManager.ActivityListener activityListener) {
        synchronized (this.mActivityListenersLock) {
            this.mActivityListeners.remove(java.util.Objects.requireNonNull(activityListener));
        }
    }

    void addSoundEffectListener(java.util.concurrent.Executor executor, android.companion.virtual.VirtualDeviceManager.SoundEffectListener soundEffectListener) {
        android.companion.virtual.VirtualDeviceInternal.SoundEffectListenerDelegate soundEffectListenerDelegate = new android.companion.virtual.VirtualDeviceInternal.SoundEffectListenerDelegate((java.util.concurrent.Executor) java.util.Objects.requireNonNull(executor), (android.companion.virtual.VirtualDeviceManager.SoundEffectListener) java.util.Objects.requireNonNull(soundEffectListener));
        synchronized (this.mSoundEffectListenersLock) {
            this.mSoundEffectListeners.put(soundEffectListener, soundEffectListenerDelegate);
        }
    }

    void removeSoundEffectListener(android.companion.virtual.VirtualDeviceManager.SoundEffectListener soundEffectListener) {
        synchronized (this.mSoundEffectListenersLock) {
            this.mSoundEffectListeners.remove(java.util.Objects.requireNonNull(soundEffectListener));
        }
    }

    void registerIntentInterceptor(android.content.IntentFilter intentFilter, java.util.concurrent.Executor executor, android.companion.virtual.VirtualDeviceManager.IntentInterceptorCallback intentInterceptorCallback) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(intentFilter);
        java.util.Objects.requireNonNull(intentInterceptorCallback);
        android.companion.virtual.VirtualDeviceInternal.IntentInterceptorDelegate intentInterceptorDelegate = new android.companion.virtual.VirtualDeviceInternal.IntentInterceptorDelegate(executor, intentInterceptorCallback);
        try {
            this.mVirtualDevice.registerIntentInterceptor(intentInterceptorDelegate, intentFilter);
            synchronized (this.mIntentInterceptorListenersLock) {
                this.mIntentInterceptorListeners.put(intentInterceptorCallback, intentInterceptorDelegate);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void unregisterIntentInterceptor(android.companion.virtual.VirtualDeviceManager.IntentInterceptorCallback intentInterceptorCallback) {
        android.companion.virtual.VirtualDeviceInternal.IntentInterceptorDelegate remove;
        java.util.Objects.requireNonNull(intentInterceptorCallback);
        synchronized (this.mIntentInterceptorListenersLock) {
            remove = this.mIntentInterceptorListeners.remove(intentInterceptorCallback);
        }
        if (remove != null) {
            try {
                this.mVirtualDevice.unregisterIntentInterceptor(remove);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ActivityListenerDelegate {
        private final android.companion.virtual.VirtualDeviceManager.ActivityListener mActivityListener;
        private final java.util.concurrent.Executor mExecutor;

        ActivityListenerDelegate(android.companion.virtual.VirtualDeviceManager.ActivityListener activityListener, java.util.concurrent.Executor executor) {
            this.mActivityListener = activityListener;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTopActivityChanged$0(int i, android.content.ComponentName componentName) {
            this.mActivityListener.onTopActivityChanged(i, componentName);
        }

        public void onTopActivityChanged(final int i, final android.content.ComponentName componentName) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.virtual.VirtualDeviceInternal.ActivityListenerDelegate.this.lambda$onTopActivityChanged$0(i, componentName);
                }
            });
        }

        public void onTopActivityChanged(final int i, final android.content.ComponentName componentName, final int i2) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.virtual.VirtualDeviceInternal.ActivityListenerDelegate.this.lambda$onTopActivityChanged$1(i, componentName, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTopActivityChanged$1(int i, android.content.ComponentName componentName, int i2) {
            this.mActivityListener.onTopActivityChanged(i, componentName, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayEmpty$2(int i) {
            this.mActivityListener.onDisplayEmpty(i);
        }

        public void onDisplayEmpty(final int i) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$ActivityListenerDelegate$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.virtual.VirtualDeviceInternal.ActivityListenerDelegate.this.lambda$onDisplayEmpty$2(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class IntentInterceptorDelegate extends android.companion.virtual.IVirtualDeviceIntentInterceptor.Stub {
        private final java.util.concurrent.Executor mExecutor;
        private final android.companion.virtual.VirtualDeviceManager.IntentInterceptorCallback mIntentInterceptorCallback;

        private IntentInterceptorDelegate(java.util.concurrent.Executor executor, android.companion.virtual.VirtualDeviceManager.IntentInterceptorCallback intentInterceptorCallback) {
            this.mExecutor = executor;
            this.mIntentInterceptorCallback = intentInterceptorCallback;
        }

        @Override // android.companion.virtual.IVirtualDeviceIntentInterceptor
        public void onIntentIntercepted(final android.content.Intent intent) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$IntentInterceptorDelegate$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.companion.virtual.VirtualDeviceInternal.IntentInterceptorDelegate.this.lambda$onIntentIntercepted$0(intent);
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onIntentIntercepted$0(android.content.Intent intent) {
            this.mIntentInterceptorCallback.onIntentIntercepted(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SoundEffectListenerDelegate {
        private final java.util.concurrent.Executor mExecutor;
        private final android.companion.virtual.VirtualDeviceManager.SoundEffectListener mSoundEffectListener;

        private SoundEffectListenerDelegate(java.util.concurrent.Executor executor, android.companion.virtual.VirtualDeviceManager.SoundEffectListener soundEffectListener) {
            this.mSoundEffectListener = soundEffectListener;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPlaySoundEffect$0(int i) {
            this.mSoundEffectListener.onPlaySoundEffect(i);
        }

        public void onPlaySoundEffect(final int i) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.companion.virtual.VirtualDeviceInternal$SoundEffectListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.companion.virtual.VirtualDeviceInternal.SoundEffectListenerDelegate.this.lambda$onPlaySoundEffect$0(i);
                }
            });
        }
    }
}
