package android.media;

/* loaded from: classes2.dex */
public class Spatializer {

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public static final int HEAD_TRACKING_MODE_DISABLED = -1;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public static final int HEAD_TRACKING_MODE_OTHER = 0;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public static final int HEAD_TRACKING_MODE_RELATIVE_DEVICE = 2;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public static final int HEAD_TRACKING_MODE_RELATIVE_WORLD = 1;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public static final int HEAD_TRACKING_MODE_UNSUPPORTED = -2;
    public static final int SPATIALIZER_IMMERSIVE_LEVEL_MCHAN_BED_PLUS_OBJECTS = 2;
    public static final int SPATIALIZER_IMMERSIVE_LEVEL_MULTICHANNEL = 1;
    public static final int SPATIALIZER_IMMERSIVE_LEVEL_NONE = 0;
    public static final int SPATIALIZER_IMMERSIVE_LEVEL_OTHER = -1;
    private static final java.lang.String TAG = "Spatializer";
    private final android.media.AudioManager mAm;
    private android.media.Spatializer.SpatializerOutputDispatcherStub mOutputDispatcher;
    private android.media.CallbackUtil.ListenerInfo<android.media.Spatializer.OnSpatializerOutputChangedListener> mOutputListener;
    private android.media.Spatializer.SpatializerPoseDispatcherStub mPoseDispatcher;
    private android.media.CallbackUtil.ListenerInfo<android.media.Spatializer.OnHeadToSoundstagePoseUpdatedListener> mPoseListener;
    private final android.media.CallbackUtil.LazyListenerManager<android.media.Spatializer.OnSpatializerStateChangedListener> mStateListenerMgr = new android.media.CallbackUtil.LazyListenerManager<>();
    private final android.media.CallbackUtil.LazyListenerManager<android.media.Spatializer.OnHeadTrackingModeChangedListener> mHeadTrackingListenerMgr = new android.media.CallbackUtil.LazyListenerManager<>();
    private final android.media.CallbackUtil.LazyListenerManager<android.media.Spatializer.OnHeadTrackerAvailableListener> mHeadTrackerListenerMgr = new android.media.CallbackUtil.LazyListenerManager<>();
    private final java.lang.Object mPoseListenerLock = new java.lang.Object();
    private final java.lang.Object mOutputListenerLock = new java.lang.Object();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HeadTrackingMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HeadTrackingModeSet {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HeadTrackingModeSupported {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImmersiveAudioLevel {
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public interface OnHeadToSoundstagePoseUpdatedListener {
        void onHeadToSoundstagePoseUpdated(android.media.Spatializer spatializer, float[] fArr);
    }

    public interface OnHeadTrackerAvailableListener {
        void onHeadTrackerAvailableChanged(android.media.Spatializer spatializer, boolean z);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public interface OnHeadTrackingModeChangedListener {
        void onDesiredHeadTrackingModeChanged(android.media.Spatializer spatializer, int i);

        void onHeadTrackingModeChanged(android.media.Spatializer spatializer, int i);
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public interface OnSpatializerOutputChangedListener {
        void onSpatializerOutputChanged(android.media.Spatializer spatializer, int i);
    }

    public interface OnSpatializerStateChangedListener {
        void onSpatializerAvailableChanged(android.media.Spatializer spatializer, boolean z);

        void onSpatializerEnabledChanged(android.media.Spatializer spatializer, boolean z);
    }

    protected Spatializer(android.media.AudioManager audioManager) {
        this.mAm = (android.media.AudioManager) java.util.Objects.requireNonNull(audioManager);
    }

    public boolean isEnabled() {
        try {
            return android.media.AudioManager.getService().isSpatializerEnabled();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error querying isSpatializerEnabled, returning false", e);
            return false;
        }
    }

    public boolean isAvailable() {
        try {
            return android.media.AudioManager.getService().isSpatializerAvailable();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error querying isSpatializerAvailable, returning false", e);
            return false;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public boolean isAvailableForDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        try {
            return android.media.AudioManager.getService().isSpatializerAvailableForDevice(audioDeviceAttributes);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public boolean hasHeadTracker(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        try {
            return android.media.AudioManager.getService().hasHeadTracker(audioDeviceAttributes);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void setHeadTrackerEnabled(boolean z, android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        try {
            android.media.AudioManager.getService().setHeadTrackerEnabled(z, audioDeviceAttributes);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public boolean isHeadTrackerEnabled(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        try {
            return android.media.AudioManager.getService().isHeadTrackerEnabled(audioDeviceAttributes);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public boolean isHeadTrackerAvailable() {
        try {
            return android.media.AudioManager.getService().isHeadTrackerAvailable();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return false;
        }
    }

    public void addOnHeadTrackerAvailableListener(java.util.concurrent.Executor executor, android.media.Spatializer.OnHeadTrackerAvailableListener onHeadTrackerAvailableListener) {
        this.mHeadTrackerListenerMgr.addListener(executor, onHeadTrackerAvailableListener, "addOnHeadTrackerAvailableListener", new java.util.function.Supplier() { // from class: android.media.Spatializer$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.media.CallbackUtil.DispatcherStub lambda$addOnHeadTrackerAvailableListener$0;
                lambda$addOnHeadTrackerAvailableListener$0 = android.media.Spatializer.this.lambda$addOnHeadTrackerAvailableListener$0();
                return lambda$addOnHeadTrackerAvailableListener$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.media.CallbackUtil.DispatcherStub lambda$addOnHeadTrackerAvailableListener$0() {
        return new android.media.Spatializer.SpatializerHeadTrackerAvailableDispatcherStub();
    }

    public void removeOnHeadTrackerAvailableListener(android.media.Spatializer.OnHeadTrackerAvailableListener onHeadTrackerAvailableListener) {
        this.mHeadTrackerListenerMgr.removeListener(onHeadTrackerAvailableListener, "removeOnHeadTrackerAvailableListener");
    }

    public static final java.lang.String headtrackingModeToString(int i) {
        switch (i) {
            case -2:
                return "HEAD_TRACKING_MODE_UNSUPPORTED";
            case -1:
                return "HEAD_TRACKING_MODE_DISABLED";
            case 0:
                return "HEAD_TRACKING_MODE_OTHER";
            case 1:
                return "HEAD_TRACKING_MODE_RELATIVE_WORLD";
            case 2:
                return "HEAD_TRACKING_MODE_RELATIVE_DEVICE";
            default:
                return "head tracking mode unknown " + i;
        }
    }

    public int getImmersiveAudioLevel() {
        try {
            return android.media.AudioManager.getService().getSpatializerImmersiveAudioLevel();
        } catch (java.lang.Exception e) {
            return 0;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void setEnabled(boolean z) {
        try {
            android.media.AudioManager.getService().setSpatializerEnabled(z);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling setSpatializerEnabled", e);
        }
    }

    public boolean canBeSpatialized(android.media.AudioAttributes audioAttributes, android.media.AudioFormat audioFormat) {
        try {
            return android.media.AudioManager.getService().canBeSpatialized((android.media.AudioAttributes) java.util.Objects.requireNonNull(audioAttributes), (android.media.AudioFormat) java.util.Objects.requireNonNull(audioFormat));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error querying canBeSpatialized for attr:" + audioAttributes + " format:" + audioFormat + " returning false", e);
            return false;
        }
    }

    public void addOnSpatializerStateChangedListener(java.util.concurrent.Executor executor, android.media.Spatializer.OnSpatializerStateChangedListener onSpatializerStateChangedListener) {
        this.mStateListenerMgr.addListener(executor, onSpatializerStateChangedListener, "addOnSpatializerStateChangedListener", new java.util.function.Supplier() { // from class: android.media.Spatializer$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.media.CallbackUtil.DispatcherStub lambda$addOnSpatializerStateChangedListener$1;
                lambda$addOnSpatializerStateChangedListener$1 = android.media.Spatializer.this.lambda$addOnSpatializerStateChangedListener$1();
                return lambda$addOnSpatializerStateChangedListener$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.media.CallbackUtil.DispatcherStub lambda$addOnSpatializerStateChangedListener$1() {
        return new android.media.Spatializer.SpatializerInfoDispatcherStub();
    }

    public void removeOnSpatializerStateChangedListener(android.media.Spatializer.OnSpatializerStateChangedListener onSpatializerStateChangedListener) {
        this.mStateListenerMgr.removeListener(onSpatializerStateChangedListener, "removeOnSpatializerStateChangedListener");
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public java.util.List<android.media.AudioDeviceAttributes> getCompatibleAudioDevices() {
        try {
            return android.media.AudioManager.getService().getSpatializerCompatibleAudioDevices();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error querying getSpatializerCompatibleAudioDevices(),  returning empty list", e);
            return new java.util.ArrayList(0);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void addCompatibleAudioDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        try {
            android.media.AudioManager.getService().addSpatializerCompatibleAudioDevice((android.media.AudioDeviceAttributes) java.util.Objects.requireNonNull(audioDeviceAttributes));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling addSpatializerCompatibleAudioDevice(), ", e);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void removeCompatibleAudioDevice(android.media.AudioDeviceAttributes audioDeviceAttributes) {
        try {
            android.media.AudioManager.getService().removeSpatializerCompatibleAudioDevice((android.media.AudioDeviceAttributes) java.util.Objects.requireNonNull(audioDeviceAttributes));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling removeSpatializerCompatibleAudioDevice(), ", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SpatializerInfoDispatcherStub extends android.media.ISpatializerCallback.Stub implements android.media.CallbackUtil.DispatcherStub {
        private SpatializerInfoDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    android.media.AudioManager unused = android.media.Spatializer.this.mAm;
                    android.media.AudioManager.getService().registerSpatializerCallback(this);
                } else {
                    android.media.AudioManager unused2 = android.media.Spatializer.this.mAm;
                    android.media.AudioManager.getService().unregisterSpatializerCallback(this);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.ISpatializerCallback
        public void dispatchSpatializerEnabledChanged(final boolean z) {
            android.media.Spatializer.this.mStateListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.Spatializer$SpatializerInfoDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    android.media.Spatializer.SpatializerInfoDispatcherStub.this.lambda$dispatchSpatializerEnabledChanged$0(z, (android.media.Spatializer.OnSpatializerStateChangedListener) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchSpatializerEnabledChanged$0(boolean z, android.media.Spatializer.OnSpatializerStateChangedListener onSpatializerStateChangedListener) {
            onSpatializerStateChangedListener.onSpatializerEnabledChanged(android.media.Spatializer.this, z);
        }

        @Override // android.media.ISpatializerCallback
        public void dispatchSpatializerAvailableChanged(final boolean z) {
            android.media.Spatializer.this.mStateListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.Spatializer$SpatializerInfoDispatcherStub$$ExternalSyntheticLambda1
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    android.media.Spatializer.SpatializerInfoDispatcherStub.this.lambda$dispatchSpatializerAvailableChanged$1(z, (android.media.Spatializer.OnSpatializerStateChangedListener) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchSpatializerAvailableChanged$1(boolean z, android.media.Spatializer.OnSpatializerStateChangedListener onSpatializerStateChangedListener) {
            onSpatializerStateChangedListener.onSpatializerAvailableChanged(android.media.Spatializer.this, z);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public int getHeadTrackingMode() {
        try {
            return android.media.AudioManager.getService().getActualHeadTrackingMode();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getActualHeadTrackingMode", e);
            return -2;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public int getDesiredHeadTrackingMode() {
        try {
            return android.media.AudioManager.getService().getDesiredHeadTrackingMode();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getDesiredHeadTrackingMode", e);
            return -2;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public java.util.List<java.lang.Integer> getSupportedHeadTrackingModes() {
        try {
            int[] supportedHeadTrackingModes = android.media.AudioManager.getService().getSupportedHeadTrackingModes();
            java.util.ArrayList arrayList = new java.util.ArrayList(0);
            for (int i : supportedHeadTrackingModes) {
                arrayList.add(java.lang.Integer.valueOf(i));
            }
            return arrayList;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getSupportedHeadTrackModes", e);
            return new java.util.ArrayList(0);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void setDesiredHeadTrackingMode(int i) {
        try {
            android.media.AudioManager.getService().setDesiredHeadTrackingMode(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling setDesiredHeadTrackingMode to " + i, e);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void recenterHeadTracker() {
        try {
            android.media.AudioManager.getService().recenterHeadTracker();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling recenterHeadTracker", e);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void addOnHeadTrackingModeChangedListener(java.util.concurrent.Executor executor, android.media.Spatializer.OnHeadTrackingModeChangedListener onHeadTrackingModeChangedListener) {
        this.mHeadTrackingListenerMgr.addListener(executor, onHeadTrackingModeChangedListener, "addOnHeadTrackingModeChangedListener", new java.util.function.Supplier() { // from class: android.media.Spatializer$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.media.CallbackUtil.DispatcherStub lambda$addOnHeadTrackingModeChangedListener$2;
                lambda$addOnHeadTrackingModeChangedListener$2 = android.media.Spatializer.this.lambda$addOnHeadTrackingModeChangedListener$2();
                return lambda$addOnHeadTrackingModeChangedListener$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.media.CallbackUtil.DispatcherStub lambda$addOnHeadTrackingModeChangedListener$2() {
        return new android.media.Spatializer.SpatializerHeadTrackingDispatcherStub();
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void removeOnHeadTrackingModeChangedListener(android.media.Spatializer.OnHeadTrackingModeChangedListener onHeadTrackingModeChangedListener) {
        this.mHeadTrackingListenerMgr.removeListener(onHeadTrackingModeChangedListener, "removeOnHeadTrackingModeChangedListener");
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void setOnHeadToSoundstagePoseUpdatedListener(java.util.concurrent.Executor executor, android.media.Spatializer.OnHeadToSoundstagePoseUpdatedListener onHeadToSoundstagePoseUpdatedListener) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onHeadToSoundstagePoseUpdatedListener);
        synchronized (this.mPoseListenerLock) {
            if (this.mPoseListener != null) {
                throw new java.lang.IllegalStateException("Trying to overwrite existing listener");
            }
            this.mPoseListener = new android.media.CallbackUtil.ListenerInfo<>(onHeadToSoundstagePoseUpdatedListener, executor);
            this.mPoseDispatcher = new android.media.Spatializer.SpatializerPoseDispatcherStub();
            try {
                android.media.AudioManager.getService().registerHeadToSoundstagePoseCallback(this.mPoseDispatcher);
            } catch (android.os.RemoteException e) {
                this.mPoseListener = null;
                this.mPoseDispatcher = null;
            }
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void clearOnHeadToSoundstagePoseUpdatedListener() {
        synchronized (this.mPoseListenerLock) {
            if (this.mPoseDispatcher == null) {
                throw new java.lang.IllegalStateException("No listener to clear");
            }
            try {
                android.media.AudioManager.getService().unregisterHeadToSoundstagePoseCallback(this.mPoseDispatcher);
            } catch (android.os.RemoteException e) {
            }
            this.mPoseListener = null;
            this.mPoseDispatcher = null;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void setGlobalTransform(float[] fArr) {
        if (((float[]) java.util.Objects.requireNonNull(fArr)).length != 6) {
            throw new java.lang.IllegalArgumentException("transform array must be of size 6, was " + fArr.length);
        }
        try {
            android.media.AudioManager.getService().setSpatializerGlobalTransform(fArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling setGlobalTransform", e);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void setEffectParameter(int i, byte[] bArr) {
        java.util.Objects.requireNonNull(bArr);
        try {
            android.media.AudioManager.getService().setSpatializerParameter(i, bArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling setEffectParameter", e);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void getEffectParameter(int i, byte[] bArr) {
        java.util.Objects.requireNonNull(bArr);
        try {
            android.media.AudioManager.getService().getSpatializerParameter(i, bArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getEffectParameter", e);
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public int getOutput() {
        try {
            return android.media.AudioManager.getService().getSpatializerOutput();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "Error calling getSpatializerOutput", e);
            return 0;
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void setOnSpatializerOutputChangedListener(java.util.concurrent.Executor executor, android.media.Spatializer.OnSpatializerOutputChangedListener onSpatializerOutputChangedListener) {
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onSpatializerOutputChangedListener);
        synchronized (this.mOutputListenerLock) {
            if (this.mOutputListener != null) {
                throw new java.lang.IllegalStateException("Trying to overwrite existing listener");
            }
            this.mOutputListener = new android.media.CallbackUtil.ListenerInfo<>(onSpatializerOutputChangedListener, executor);
            this.mOutputDispatcher = new android.media.Spatializer.SpatializerOutputDispatcherStub();
            try {
                android.media.AudioManager.getService().registerSpatializerOutputCallback(this.mOutputDispatcher);
                this.mOutputDispatcher.dispatchSpatializerOutputChanged(getOutput());
            } catch (android.os.RemoteException e) {
                this.mOutputListener = null;
                this.mOutputDispatcher = null;
            }
        }
    }

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.PRIVILEGED_APPS)
    public void clearOnSpatializerOutputChangedListener() {
        synchronized (this.mOutputListenerLock) {
            if (this.mOutputDispatcher == null) {
                throw new java.lang.IllegalStateException("No listener to clear");
            }
            try {
                android.media.AudioManager.getService().unregisterSpatializerOutputCallback(this.mOutputDispatcher);
            } catch (android.os.RemoteException e) {
            }
            this.mOutputListener = null;
            this.mOutputDispatcher = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SpatializerHeadTrackingDispatcherStub extends android.media.ISpatializerHeadTrackingModeCallback.Stub implements android.media.CallbackUtil.DispatcherStub {
        private SpatializerHeadTrackingDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                if (z) {
                    android.media.AudioManager unused = android.media.Spatializer.this.mAm;
                    android.media.AudioManager.getService().registerSpatializerHeadTrackingCallback(this);
                } else {
                    android.media.AudioManager unused2 = android.media.Spatializer.this.mAm;
                    android.media.AudioManager.getService().unregisterSpatializerHeadTrackingCallback(this);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.ISpatializerHeadTrackingModeCallback
        public void dispatchSpatializerActualHeadTrackingModeChanged(final int i) {
            android.media.Spatializer.this.mHeadTrackingListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.Spatializer$SpatializerHeadTrackingDispatcherStub$$ExternalSyntheticLambda1
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    android.media.Spatializer.SpatializerHeadTrackingDispatcherStub.this.lambda$dispatchSpatializerActualHeadTrackingModeChanged$0(i, (android.media.Spatializer.OnHeadTrackingModeChangedListener) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchSpatializerActualHeadTrackingModeChanged$0(int i, android.media.Spatializer.OnHeadTrackingModeChangedListener onHeadTrackingModeChangedListener) {
            onHeadTrackingModeChangedListener.onHeadTrackingModeChanged(android.media.Spatializer.this, i);
        }

        @Override // android.media.ISpatializerHeadTrackingModeCallback
        public void dispatchSpatializerDesiredHeadTrackingModeChanged(final int i) {
            android.media.Spatializer.this.mHeadTrackingListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.Spatializer$SpatializerHeadTrackingDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    android.media.Spatializer.SpatializerHeadTrackingDispatcherStub.this.lambda$dispatchSpatializerDesiredHeadTrackingModeChanged$1(i, (android.media.Spatializer.OnHeadTrackingModeChangedListener) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchSpatializerDesiredHeadTrackingModeChanged$1(int i, android.media.Spatializer.OnHeadTrackingModeChangedListener onHeadTrackingModeChangedListener) {
            onHeadTrackingModeChangedListener.onDesiredHeadTrackingModeChanged(android.media.Spatializer.this, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SpatializerHeadTrackerAvailableDispatcherStub extends android.media.ISpatializerHeadTrackerAvailableCallback.Stub implements android.media.CallbackUtil.DispatcherStub {
        private SpatializerHeadTrackerAvailableDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                android.media.AudioManager unused = android.media.Spatializer.this.mAm;
                android.media.AudioManager.getService().registerSpatializerHeadTrackerAvailableCallback(this, z);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.ISpatializerHeadTrackerAvailableCallback
        public void dispatchSpatializerHeadTrackerAvailable(final boolean z) {
            android.media.Spatializer.this.mHeadTrackerListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.Spatializer$SpatializerHeadTrackerAvailableDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    android.media.Spatializer.SpatializerHeadTrackerAvailableDispatcherStub.this.lambda$dispatchSpatializerHeadTrackerAvailable$0(z, (android.media.Spatializer.OnHeadTrackerAvailableListener) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchSpatializerHeadTrackerAvailable$0(boolean z, android.media.Spatializer.OnHeadTrackerAvailableListener onHeadTrackerAvailableListener) {
            onHeadTrackerAvailableListener.onHeadTrackerAvailableChanged(android.media.Spatializer.this, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SpatializerPoseDispatcherStub extends android.media.ISpatializerHeadToSoundStagePoseCallback.Stub {
        private SpatializerPoseDispatcherStub() {
        }

        @Override // android.media.ISpatializerHeadToSoundStagePoseCallback
        public void dispatchPoseChanged(final float[] fArr) {
            final android.media.CallbackUtil.ListenerInfo listenerInfo;
            synchronized (android.media.Spatializer.this.mPoseListenerLock) {
                listenerInfo = android.media.Spatializer.this.mPoseListener;
            }
            if (listenerInfo == null) {
                return;
            }
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                listenerInfo.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.Spatializer$SpatializerPoseDispatcherStub$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.Spatializer.SpatializerPoseDispatcherStub.this.lambda$dispatchPoseChanged$0(listenerInfo, fArr);
                    }
                });
                if (create != null) {
                    create.close();
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchPoseChanged$0(android.media.CallbackUtil.ListenerInfo listenerInfo, float[] fArr) {
            ((android.media.Spatializer.OnHeadToSoundstagePoseUpdatedListener) listenerInfo.mListener).onHeadToSoundstagePoseUpdated(android.media.Spatializer.this, fArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class SpatializerOutputDispatcherStub extends android.media.ISpatializerOutputCallback.Stub {
        private SpatializerOutputDispatcherStub() {
        }

        @Override // android.media.ISpatializerOutputCallback
        public void dispatchSpatializerOutputChanged(final int i) {
            final android.media.CallbackUtil.ListenerInfo listenerInfo;
            synchronized (android.media.Spatializer.this.mOutputListenerLock) {
                listenerInfo = android.media.Spatializer.this.mOutputListener;
            }
            if (listenerInfo == null) {
                return;
            }
            android.media.permission.SafeCloseable create = android.media.permission.ClearCallingIdentityContext.create();
            try {
                listenerInfo.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.Spatializer$SpatializerOutputDispatcherStub$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.Spatializer.SpatializerOutputDispatcherStub.this.lambda$dispatchSpatializerOutputChanged$0(listenerInfo, i);
                    }
                });
                if (create != null) {
                    create.close();
                }
            } catch (java.lang.Throwable th) {
                if (create != null) {
                    try {
                        create.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dispatchSpatializerOutputChanged$0(android.media.CallbackUtil.ListenerInfo listenerInfo, int i) {
            ((android.media.Spatializer.OnSpatializerOutputChangedListener) listenerInfo.mListener).onSpatializerOutputChanged(android.media.Spatializer.this, i);
        }
    }
}
