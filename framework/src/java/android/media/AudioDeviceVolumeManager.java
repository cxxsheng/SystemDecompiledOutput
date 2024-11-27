package android.media;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class AudioDeviceVolumeManager {
    public static final int ADJUST_MODE_END = 2;
    public static final int ADJUST_MODE_NORMAL = 0;
    public static final int ADJUST_MODE_START = 1;
    private static final java.lang.String TAG = "AudioDeviceVolumeManager";
    private static android.media.IAudioService sService;
    private android.media.AudioDeviceVolumeManager.DeviceVolumeDispatcherStub mDeviceVolumeDispatcherStub;
    private java.util.ArrayList<android.media.AudioDeviceVolumeManager.ListenerInfo> mDeviceVolumeListeners;
    private final java.lang.String mPackageName;
    private final java.lang.Object mDeviceVolumeListenerLock = new java.lang.Object();
    private final android.media.CallbackUtil.LazyListenerManager<android.media.AudioDeviceVolumeManager.OnDeviceVolumeBehaviorChangedListener> mDeviceVolumeBehaviorChangedListenerMgr = new android.media.CallbackUtil.LazyListenerManager<>();

    public interface OnAudioDeviceVolumeChangedListener {
        void onAudioDeviceVolumeAdjusted(android.media.AudioDeviceAttributes audioDeviceAttributes, android.media.VolumeInfo volumeInfo, int i, int i2);

        void onAudioDeviceVolumeChanged(android.media.AudioDeviceAttributes audioDeviceAttributes, android.media.VolumeInfo volumeInfo);
    }

    public interface OnDeviceVolumeBehaviorChangedListener {
        void onDeviceVolumeBehaviorChanged(android.media.AudioDeviceAttributes audioDeviceAttributes, int i);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VolumeAdjustmentMode {
    }

    public AudioDeviceVolumeManager(android.content.Context context) {
        java.util.Objects.requireNonNull(context);
        this.mPackageName = context.getApplicationContext().getOpPackageName();
    }

    static class ListenerInfo {
        final android.media.AudioDeviceAttributes mDevice;
        final java.util.concurrent.Executor mExecutor;
        final boolean mHandlesVolumeAdjustment;
        final android.media.AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener mListener;

        ListenerInfo(android.media.AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, java.util.concurrent.Executor executor, android.media.AudioDeviceAttributes audioDeviceAttributes, boolean z) {
            this.mListener = onAudioDeviceVolumeChangedListener;
            this.mExecutor = executor;
            this.mDevice = audioDeviceAttributes;
            this.mHandlesVolumeAdjustment = z;
        }
    }

    final class DeviceVolumeDispatcherStub extends android.media.IAudioDeviceVolumeDispatcher.Stub {
        DeviceVolumeDispatcherStub() {
        }

        public void register(boolean z, android.media.AudioDeviceAttributes audioDeviceAttributes, java.util.List<android.media.VolumeInfo> list, boolean z2, int i) {
            try {
                android.media.AudioDeviceVolumeManager.getService().registerDeviceVolumeDispatcherForAbsoluteVolume(z, this, android.media.AudioDeviceVolumeManager.this.mPackageName, (android.media.AudioDeviceAttributes) java.util.Objects.requireNonNull(audioDeviceAttributes), (java.util.List) java.util.Objects.requireNonNull(list), z2, i);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IAudioDeviceVolumeDispatcher
        public void dispatchDeviceVolumeChanged(final android.media.AudioDeviceAttributes audioDeviceAttributes, final android.media.VolumeInfo volumeInfo) {
            java.util.ArrayList arrayList;
            synchronized (android.media.AudioDeviceVolumeManager.this.mDeviceVolumeListenerLock) {
                arrayList = (java.util.ArrayList) android.media.AudioDeviceVolumeManager.this.mDeviceVolumeListeners.clone();
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                final android.media.AudioDeviceVolumeManager.ListenerInfo listenerInfo = (android.media.AudioDeviceVolumeManager.ListenerInfo) it.next();
                if (listenerInfo.mDevice.equalTypeAddress(audioDeviceAttributes)) {
                    listenerInfo.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.AudioDeviceVolumeManager$DeviceVolumeDispatcherStub$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.AudioDeviceVolumeManager.ListenerInfo.this.mListener.onAudioDeviceVolumeChanged(audioDeviceAttributes, volumeInfo);
                        }
                    });
                }
            }
        }

        @Override // android.media.IAudioDeviceVolumeDispatcher
        public void dispatchDeviceVolumeAdjusted(final android.media.AudioDeviceAttributes audioDeviceAttributes, final android.media.VolumeInfo volumeInfo, final int i, final int i2) {
            java.util.ArrayList arrayList;
            synchronized (android.media.AudioDeviceVolumeManager.this.mDeviceVolumeListenerLock) {
                arrayList = (java.util.ArrayList) android.media.AudioDeviceVolumeManager.this.mDeviceVolumeListeners.clone();
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                final android.media.AudioDeviceVolumeManager.ListenerInfo listenerInfo = (android.media.AudioDeviceVolumeManager.ListenerInfo) it.next();
                if (listenerInfo.mDevice.equalTypeAddress(audioDeviceAttributes)) {
                    listenerInfo.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.AudioDeviceVolumeManager$DeviceVolumeDispatcherStub$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.media.AudioDeviceVolumeManager.ListenerInfo.this.mListener.onAudioDeviceVolumeAdjusted(audioDeviceAttributes, volumeInfo, i, i2);
                        }
                    });
                }
            }
        }
    }

    public void setDeviceAbsoluteVolumeBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes, android.media.VolumeInfo volumeInfo, java.util.concurrent.Executor executor, android.media.AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList(1);
        arrayList.add(volumeInfo);
        setDeviceAbsoluteMultiVolumeBehavior(audioDeviceAttributes, arrayList, executor, onAudioDeviceVolumeChangedListener, z);
    }

    public void setDeviceAbsoluteMultiVolumeBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes, java.util.List<android.media.VolumeInfo> list, java.util.concurrent.Executor executor, android.media.AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, boolean z) {
        baseSetDeviceAbsoluteMultiVolumeBehavior(audioDeviceAttributes, list, executor, onAudioDeviceVolumeChangedListener, z, 3);
    }

    public void setDeviceAbsoluteVolumeAdjustOnlyBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes, android.media.VolumeInfo volumeInfo, java.util.concurrent.Executor executor, android.media.AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, boolean z) {
        java.util.ArrayList arrayList = new java.util.ArrayList(1);
        arrayList.add(volumeInfo);
        setDeviceAbsoluteMultiVolumeAdjustOnlyBehavior(audioDeviceAttributes, arrayList, executor, onAudioDeviceVolumeChangedListener, z);
    }

    public void setDeviceAbsoluteMultiVolumeAdjustOnlyBehavior(android.media.AudioDeviceAttributes audioDeviceAttributes, java.util.List<android.media.VolumeInfo> list, java.util.concurrent.Executor executor, android.media.AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, boolean z) {
        baseSetDeviceAbsoluteMultiVolumeBehavior(audioDeviceAttributes, list, executor, onAudioDeviceVolumeChangedListener, z, 5);
    }

    private void baseSetDeviceAbsoluteMultiVolumeBehavior(final android.media.AudioDeviceAttributes audioDeviceAttributes, java.util.List<android.media.VolumeInfo> list, java.util.concurrent.Executor executor, android.media.AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, boolean z, int i) {
        java.util.Objects.requireNonNull(audioDeviceAttributes);
        java.util.Objects.requireNonNull(list);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(onAudioDeviceVolumeChangedListener);
        android.media.AudioDeviceVolumeManager.ListenerInfo listenerInfo = new android.media.AudioDeviceVolumeManager.ListenerInfo(onAudioDeviceVolumeChangedListener, executor, audioDeviceAttributes, z);
        synchronized (this.mDeviceVolumeListenerLock) {
            if (this.mDeviceVolumeListeners == null) {
                this.mDeviceVolumeListeners = new java.util.ArrayList<>();
            }
            if (this.mDeviceVolumeListeners.size() == 0) {
                if (this.mDeviceVolumeDispatcherStub == null) {
                    this.mDeviceVolumeDispatcherStub = new android.media.AudioDeviceVolumeManager.DeviceVolumeDispatcherStub();
                }
            } else {
                this.mDeviceVolumeListeners.removeIf(new java.util.function.Predicate() { // from class: android.media.AudioDeviceVolumeManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean equalTypeAddress;
                        equalTypeAddress = ((android.media.AudioDeviceVolumeManager.ListenerInfo) obj).mDevice.equalTypeAddress(android.media.AudioDeviceAttributes.this);
                        return equalTypeAddress;
                    }
                });
            }
            this.mDeviceVolumeListeners.add(listenerInfo);
            this.mDeviceVolumeDispatcherStub.register(true, audioDeviceAttributes, list, z, i);
        }
    }

    public void addOnDeviceVolumeBehaviorChangedListener(java.util.concurrent.Executor executor, android.media.AudioDeviceVolumeManager.OnDeviceVolumeBehaviorChangedListener onDeviceVolumeBehaviorChangedListener) throws java.lang.SecurityException {
        this.mDeviceVolumeBehaviorChangedListenerMgr.addListener(executor, onDeviceVolumeBehaviorChangedListener, "addOnDeviceVolumeBehaviorChangedListener", new java.util.function.Supplier() { // from class: android.media.AudioDeviceVolumeManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.media.CallbackUtil.DispatcherStub lambda$addOnDeviceVolumeBehaviorChangedListener$1;
                lambda$addOnDeviceVolumeBehaviorChangedListener$1 = android.media.AudioDeviceVolumeManager.this.lambda$addOnDeviceVolumeBehaviorChangedListener$1();
                return lambda$addOnDeviceVolumeBehaviorChangedListener$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.media.CallbackUtil.DispatcherStub lambda$addOnDeviceVolumeBehaviorChangedListener$1() {
        return new android.media.AudioDeviceVolumeManager.DeviceVolumeBehaviorDispatcherStub();
    }

    public void removeOnDeviceVolumeBehaviorChangedListener(android.media.AudioDeviceVolumeManager.OnDeviceVolumeBehaviorChangedListener onDeviceVolumeBehaviorChangedListener) {
        this.mDeviceVolumeBehaviorChangedListenerMgr.removeListener(onDeviceVolumeBehaviorChangedListener, "removeOnDeviceVolumeBehaviorChangedListener");
    }

    @android.annotation.SystemApi
    public void setDeviceVolume(android.media.VolumeInfo volumeInfo, android.media.AudioDeviceAttributes audioDeviceAttributes) {
        try {
            getService().setDeviceVolume(volumeInfo, audioDeviceAttributes, this.mPackageName);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public android.media.VolumeInfo getDeviceVolume(android.media.VolumeInfo volumeInfo, android.media.AudioDeviceAttributes audioDeviceAttributes) {
        try {
            return getService().getDeviceVolume(volumeInfo, audioDeviceAttributes, this.mPackageName);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return android.media.VolumeInfo.getDefaultVolumeInfo();
        }
    }

    public static java.lang.String volumeBehaviorName(int i) {
        switch (i) {
            case 0:
                return "DEVICE_VOLUME_BEHAVIOR_VARIABLE";
            case 1:
                return "DEVICE_VOLUME_BEHAVIOR_FULL";
            case 2:
                return "DEVICE_VOLUME_BEHAVIOR_FIXED";
            case 3:
                return "DEVICE_VOLUME_BEHAVIOR_ABSOLUTE";
            case 4:
                return "DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_MULTI_MODE";
            case 5:
                return "DEVICE_VOLUME_BEHAVIOR_ABSOLUTE_ADJUST_ONLY";
            default:
                return "invalid volume behavior " + i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class DeviceVolumeBehaviorDispatcherStub extends android.media.IDeviceVolumeBehaviorDispatcher.Stub implements android.media.CallbackUtil.DispatcherStub {
        private DeviceVolumeBehaviorDispatcherStub() {
        }

        @Override // android.media.CallbackUtil.DispatcherStub
        public void register(boolean z) {
            try {
                android.media.AudioDeviceVolumeManager.getService().registerDeviceVolumeBehaviorDispatcher(z, this);
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }

        @Override // android.media.IDeviceVolumeBehaviorDispatcher
        public void dispatchDeviceVolumeBehaviorChanged(final android.media.AudioDeviceAttributes audioDeviceAttributes, final int i) {
            android.media.AudioDeviceVolumeManager.this.mDeviceVolumeBehaviorChangedListenerMgr.callListeners(new android.media.CallbackUtil.CallbackMethod() { // from class: android.media.AudioDeviceVolumeManager$DeviceVolumeBehaviorDispatcherStub$$ExternalSyntheticLambda0
                @Override // android.media.CallbackUtil.CallbackMethod
                public final void callbackMethod(java.lang.Object obj) {
                    ((android.media.AudioDeviceVolumeManager.OnDeviceVolumeBehaviorChangedListener) obj).onDeviceVolumeBehaviorChanged(android.media.AudioDeviceAttributes.this, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.media.IAudioService getService() {
        if (sService != null) {
            return sService;
        }
        sService = android.media.IAudioService.Stub.asInterface(android.os.ServiceManager.getService("audio"));
        return sService;
    }
}
