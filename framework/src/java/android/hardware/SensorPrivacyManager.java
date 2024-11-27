package android.hardware;

/* loaded from: classes.dex */
public final class SensorPrivacyManager {
    public static final int TOGGLE_TYPE_HARDWARE = 2;
    public static final int TOGGLE_TYPE_SOFTWARE = 1;
    private static android.hardware.SensorPrivacyManager sInstance;
    private final android.content.Context mContext;
    private final android.hardware.ISensorPrivacyManager mService;
    private static final java.lang.String LOG_TAG = android.hardware.SensorPrivacyManager.class.getSimpleName();
    public static final java.lang.String EXTRA_SENSOR = android.hardware.SensorPrivacyManager.class.getName() + ".extra.sensor";
    public static final java.lang.String EXTRA_NOTIFICATION_ID = android.hardware.SensorPrivacyManager.class.getName() + ".extra.notification_id";
    public static final java.lang.String EXTRA_ALL_SENSORS = android.hardware.SensorPrivacyManager.class.getName() + ".extra.all_sensors";
    public static final java.lang.String EXTRA_TOGGLE_TYPE = android.hardware.SensorPrivacyManager.class.getName() + ".extra.toggle_type";
    private static final java.lang.Object sInstanceLock = new java.lang.Object();
    private android.os.IBinder token = new android.os.Binder();
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.ArrayMap<android.util.Pair<java.lang.Integer, java.lang.Integer>, java.lang.Boolean> mToggleSupportCache = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener, java.util.concurrent.Executor> mToggleListeners = new android.util.ArrayMap<>();
    private final android.util.ArrayMap<android.util.Pair<java.lang.Integer, android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener>, android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener> mLegacyToggleListeners = new android.util.ArrayMap<>();
    private android.util.ArrayMap<java.lang.String, java.lang.Boolean> mCameraPrivacyAllowlist = null;
    private final android.hardware.ISensorPrivacyListener mIToggleListener = new android.hardware.SensorPrivacyManager.AnonymousClass1();
    private boolean mToggleListenerRegistered = false;
    private java.lang.Boolean mRequiresAuthentication = null;
    private final android.util.ArrayMap<android.hardware.SensorPrivacyManager.OnAllSensorPrivacyChangedListener, android.hardware.ISensorPrivacyListener> mListeners = new android.util.ArrayMap<>();

    public interface OnAllSensorPrivacyChangedListener {
        void onAllSensorPrivacyChanged(boolean z);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ToggleType {
    }

    public static class Sensors {
        public static final int CAMERA = 2;
        public static final int MICROPHONE = 1;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Sensor {
        }

        private Sensors() {
        }
    }

    public static class Sources {
        public static final int DIALOG = 3;
        public static final int OTHER = 5;
        public static final int QS_TILE = 1;
        public static final int SAFETY_CENTER = 6;
        public static final int SETTINGS = 2;
        public static final int SHELL = 4;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Source {
        }

        private Sources() {
        }
    }

    public static class StateTypes {
        public static final int AUTOMOTIVE_DRIVER_ASSISTANCE_APPS = 5;
        public static final int AUTOMOTIVE_DRIVER_ASSISTANCE_HELPFUL_APPS = 3;
        public static final int AUTOMOTIVE_DRIVER_ASSISTANCE_REQUIRED_APPS = 4;
        public static final int DISABLED = 2;
        public static final int ENABLED = 1;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface StateType {
        }

        private StateTypes() {
        }
    }

    @android.annotation.SystemApi
    public interface OnSensorPrivacyChangedListener {
        @java.lang.Deprecated
        void onSensorPrivacyChanged(int i, boolean z);

        default void onSensorPrivacyChanged(android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener.SensorPrivacyChangedParams sensorPrivacyChangedParams) {
            onSensorPrivacyChanged(sensorPrivacyChangedParams.mSensor, sensorPrivacyChangedParams.mEnabled);
        }

        public static class SensorPrivacyChangedParams {
            private boolean mEnabled;
            private int mSensor;
            private int mState;
            private int mToggleType;

            private SensorPrivacyChangedParams(int i, int i2, int i3) {
                this.mToggleType = i;
                this.mSensor = i2;
                this.mState = i3;
                if (i3 == 1) {
                    this.mEnabled = true;
                } else {
                    this.mEnabled = false;
                }
            }

            private SensorPrivacyChangedParams(int i, int i2, boolean z) {
                this.mToggleType = i;
                this.mSensor = i2;
                this.mEnabled = z;
            }

            public int getToggleType() {
                return this.mToggleType;
            }

            public int getSensor() {
                return this.mSensor;
            }

            public boolean isEnabled() {
                return this.mEnabled;
            }

            public int getState() {
                return this.mState;
            }
        }
    }

    /* renamed from: android.hardware.SensorPrivacyManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.hardware.ISensorPrivacyListener.Stub {
        AnonymousClass1() {
        }

        @Override // android.hardware.ISensorPrivacyListener
        public void onSensorPrivacyChanged(final int i, final int i2, final boolean z) {
            synchronized (android.hardware.SensorPrivacyManager.this.mLock) {
                for (int i3 = 0; i3 < android.hardware.SensorPrivacyManager.this.mToggleListeners.size(); i3++) {
                    final android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener = (android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener) android.hardware.SensorPrivacyManager.this.mToggleListeners.keyAt(i3);
                    if (com.android.internal.camera.flags.Flags.cameraPrivacyAllowlist()) {
                        final int i4 = z ? 1 : 2;
                        ((java.util.concurrent.Executor) android.hardware.SensorPrivacyManager.this.mToggleListeners.valueAt(i3)).execute(new java.lang.Runnable() { // from class: android.hardware.SensorPrivacyManager$1$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener.this.onSensorPrivacyChanged(new android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener.SensorPrivacyChangedParams(i, i2, i4));
                            }
                        });
                    } else {
                        ((java.util.concurrent.Executor) android.hardware.SensorPrivacyManager.this.mToggleListeners.valueAt(i3)).execute(new java.lang.Runnable() { // from class: android.hardware.SensorPrivacyManager$1$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener.this.onSensorPrivacyChanged(new android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener.SensorPrivacyChangedParams(i, i2, z));
                            }
                        });
                    }
                }
            }
        }

        @Override // android.hardware.ISensorPrivacyListener
        public void onSensorPrivacyStateChanged(final int i, final int i2, final int i3) {
            synchronized (android.hardware.SensorPrivacyManager.this.mLock) {
                for (int i4 = 0; i4 < android.hardware.SensorPrivacyManager.this.mToggleListeners.size(); i4++) {
                    final android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener = (android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener) android.hardware.SensorPrivacyManager.this.mToggleListeners.keyAt(i4);
                    ((java.util.concurrent.Executor) android.hardware.SensorPrivacyManager.this.mToggleListeners.valueAt(i4)).execute(new java.lang.Runnable() { // from class: android.hardware.SensorPrivacyManager$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener.this.onSensorPrivacyChanged(new android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener.SensorPrivacyChangedParams(i, i2, i3));
                        }
                    });
                }
            }
        }
    }

    private SensorPrivacyManager(android.content.Context context, android.hardware.ISensorPrivacyManager iSensorPrivacyManager) {
        this.mContext = context;
        this.mService = iSensorPrivacyManager;
    }

    public static android.hardware.SensorPrivacyManager getInstance(android.content.Context context) {
        android.hardware.SensorPrivacyManager sensorPrivacyManager;
        synchronized (sInstanceLock) {
            if (sInstance == null) {
                try {
                    sInstance = new android.hardware.SensorPrivacyManager(context, android.hardware.ISensorPrivacyManager.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow(android.content.Context.SENSOR_PRIVACY_SERVICE)));
                } catch (android.os.ServiceManager.ServiceNotFoundException e) {
                    throw new java.lang.IllegalStateException(e);
                }
            }
            sensorPrivacyManager = sInstance;
        }
        return sensorPrivacyManager;
    }

    public static android.hardware.SensorPrivacyManager getInstance(android.content.Context context, android.hardware.ISensorPrivacyManager iSensorPrivacyManager) {
        android.hardware.SensorPrivacyManager sensorPrivacyManager;
        synchronized (sInstanceLock) {
            sInstance = new android.hardware.SensorPrivacyManager(context, iSensorPrivacyManager);
            sensorPrivacyManager = sInstance;
        }
        return sensorPrivacyManager;
    }

    public boolean supportsSensorToggle(int i) {
        return supportsSensorToggle(1, i);
    }

    public boolean supportsSensorToggle(int i, int i2) {
        boolean booleanValue;
        try {
            android.util.Pair<java.lang.Integer, java.lang.Integer> pair = new android.util.Pair<>(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            synchronized (this.mLock) {
                java.lang.Boolean bool = this.mToggleSupportCache.get(pair);
                if (bool == null) {
                    bool = java.lang.Boolean.valueOf(this.mService.supportsSensorToggle(i, i2));
                    this.mToggleSupportCache.put(pair, bool);
                }
                booleanValue = bool.booleanValue();
            }
            return booleanValue;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public void addSensorPrivacyListener(int i, android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        addSensorPrivacyListener(i, this.mContext.getMainExecutor(), onSensorPrivacyChangedListener);
    }

    public void addSensorPrivacyListener(int i, int i2, android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        addSensorPrivacyListener(i, this.mContext.getMainExecutor(), onSensorPrivacyChangedListener);
    }

    @android.annotation.SystemApi
    public void addSensorPrivacyListener(final int i, java.util.concurrent.Executor executor, final android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        android.util.Pair<java.lang.Integer, android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener> pair = new android.util.Pair<>(java.lang.Integer.valueOf(i), onSensorPrivacyChangedListener);
        android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener2 = new android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener() { // from class: android.hardware.SensorPrivacyManager.2
            @Override // android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener
            public void onSensorPrivacyChanged(android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener.SensorPrivacyChangedParams sensorPrivacyChangedParams) {
                if (sensorPrivacyChangedParams.getSensor() == i) {
                    onSensorPrivacyChangedListener.onSensorPrivacyChanged(sensorPrivacyChangedParams);
                }
            }

            @Override // android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener
            public void onSensorPrivacyChanged(int i2, boolean z) {
            }
        };
        synchronized (this.mLock) {
            this.mLegacyToggleListeners.put(pair, onSensorPrivacyChangedListener2);
            addSensorPrivacyListenerLocked(executor, onSensorPrivacyChangedListener2);
        }
    }

    @android.annotation.SystemApi
    public void addSensorPrivacyListener(android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        addSensorPrivacyListener(this.mContext.getMainExecutor(), onSensorPrivacyChangedListener);
    }

    @android.annotation.SystemApi
    public void addSensorPrivacyListener(java.util.concurrent.Executor executor, android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        synchronized (this.mLock) {
            addSensorPrivacyListenerLocked(executor, onSensorPrivacyChangedListener);
        }
    }

    private void addSensorPrivacyListenerLocked(java.util.concurrent.Executor executor, android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        if (!this.mToggleListenerRegistered) {
            try {
                this.mService.addToggleSensorPrivacyListener(this.mIToggleListener);
                this.mToggleListenerRegistered = true;
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
        if (this.mToggleListeners.containsKey(onSensorPrivacyChangedListener)) {
            throw new java.lang.IllegalArgumentException("listener is already registered");
        }
        this.mToggleListeners.put(onSensorPrivacyChangedListener, executor);
    }

    @android.annotation.SystemApi
    public void removeSensorPrivacyListener(int i, android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        android.util.Pair pair = new android.util.Pair(java.lang.Integer.valueOf(i), onSensorPrivacyChangedListener);
        synchronized (this.mLock) {
            android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener remove = this.mLegacyToggleListeners.remove(pair);
            if (remove != null) {
                removeSensorPrivacyListenerLocked(remove);
            }
        }
    }

    @android.annotation.SystemApi
    public void removeSensorPrivacyListener(android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        synchronized (this.mLock) {
            removeSensorPrivacyListenerLocked(onSensorPrivacyChangedListener);
        }
    }

    private void removeSensorPrivacyListenerLocked(android.hardware.SensorPrivacyManager.OnSensorPrivacyChangedListener onSensorPrivacyChangedListener) {
        this.mToggleListeners.remove(onSensorPrivacyChangedListener);
        if (this.mToggleListeners.size() == 0) {
            try {
                this.mService.removeToggleSensorPrivacyListener(this.mIToggleListener);
                this.mToggleListenerRegistered = false;
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean isSensorPrivacyEnabled(int i) {
        return isSensorPrivacyEnabled(1, i);
    }

    @android.annotation.SystemApi
    public boolean isSensorPrivacyEnabled(int i, int i2) {
        try {
            return this.mService.isToggleSensorPrivacyEnabled(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean areAnySensorPrivacyTogglesEnabled(int i) {
        try {
            return this.mService.isCombinedToggleSensorPrivacyEnabled(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public int getSensorPrivacyState(int i, int i2) {
        try {
            return this.mService.getToggleSensorPrivacyState(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public boolean isCameraPrivacyEnabled(java.lang.String str) {
        try {
            return this.mService.isCameraPrivacyEnabled(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public java.util.Map<java.lang.String, java.lang.Boolean> getCameraPrivacyAllowlist() {
        android.util.ArrayMap<java.lang.String, java.lang.Boolean> arrayMap;
        synchronized (this.mLock) {
            if (this.mCameraPrivacyAllowlist == null) {
                this.mCameraPrivacyAllowlist = new android.util.ArrayMap<>();
                try {
                    for (android.hardware.CameraPrivacyAllowlistEntry cameraPrivacyAllowlistEntry : this.mService.getCameraPrivacyAllowlist()) {
                        this.mCameraPrivacyAllowlist.put(cameraPrivacyAllowlistEntry.packageName, java.lang.Boolean.valueOf(cameraPrivacyAllowlistEntry.isMandatory));
                    }
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            arrayMap = this.mCameraPrivacyAllowlist;
        }
        return arrayMap;
    }

    @android.annotation.SystemApi
    public void setSensorPrivacy(int i, boolean z) {
        setSensorPrivacy(resolveSourceFromCurrentContext(), i, z, -2);
    }

    private int resolveSourceFromCurrentContext() {
        if (java.util.Objects.equals(this.mContext.getOpPackageName(), this.mContext.getPackageManager().getPermissionControllerPackageName())) {
            return 6;
        }
        return 5;
    }

    @android.annotation.SystemApi
    public void setSensorPrivacyState(int i, int i2) {
        setSensorPrivacyState(resolveSourceFromCurrentContext(), i, i2);
    }

    public void setSensorPrivacy(int i, int i2, boolean z) {
        setSensorPrivacy(i, i2, z, -2);
    }

    public void setSensorPrivacy(int i, int i2, boolean z, int i3) {
        try {
            this.mService.setToggleSensorPrivacy(i3, i, i2, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSensorPrivacyState(int i, int i2, int i3) {
        try {
            this.mService.setToggleSensorPrivacyState(this.mContext.getUserId(), i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSensorPrivacyForProfileGroup(int i, int i2, boolean z) {
        setSensorPrivacyForProfileGroup(i, i2, z, -2);
    }

    public void setSensorPrivacyForProfileGroup(int i, int i2, boolean z, int i3) {
        try {
            this.mService.setToggleSensorPrivacyForProfileGroup(i3, i, i2, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setSensorPrivacyStateForProfileGroup(int i, int i2, int i3) {
        try {
            this.mService.setToggleSensorPrivacyStateForProfileGroup(this.mContext.getUserId(), i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void suppressSensorPrivacyReminders(int i, boolean z) {
        suppressSensorPrivacyReminders(i, z, -2);
    }

    public void suppressSensorPrivacyReminders(int i, boolean z, int i2) {
        try {
            this.mService.suppressToggleSensorPrivacyReminders(i2, i, this.token, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requiresAuthentication() {
        if (this.mRequiresAuthentication == null) {
            try {
                this.mRequiresAuthentication = java.lang.Boolean.valueOf(this.mService.requiresAuthentication());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        return this.mRequiresAuthentication.booleanValue();
    }

    public void showSensorUseDialog(int i) {
        try {
            this.mService.showSensorUseDialog(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(LOG_TAG, "Received exception while trying to show sensor use dialog", e);
        }
    }

    public void setAllSensorPrivacy(boolean z) {
        try {
            this.mService.setSensorPrivacy(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addAllSensorPrivacyListener(final android.hardware.SensorPrivacyManager.OnAllSensorPrivacyChangedListener onAllSensorPrivacyChangedListener) {
        synchronized (this.mListeners) {
            android.hardware.ISensorPrivacyListener iSensorPrivacyListener = this.mListeners.get(onAllSensorPrivacyChangedListener);
            if (iSensorPrivacyListener == null) {
                iSensorPrivacyListener = new android.hardware.ISensorPrivacyListener.Stub() { // from class: android.hardware.SensorPrivacyManager.3
                    @Override // android.hardware.ISensorPrivacyListener
                    public void onSensorPrivacyChanged(int i, int i2, boolean z) {
                        onAllSensorPrivacyChangedListener.onAllSensorPrivacyChanged(z);
                    }

                    @Override // android.hardware.ISensorPrivacyListener
                    public void onSensorPrivacyStateChanged(int i, int i2, int i3) {
                    }
                };
                this.mListeners.put(onAllSensorPrivacyChangedListener, iSensorPrivacyListener);
            }
            try {
                this.mService.addSensorPrivacyListener(iSensorPrivacyListener);
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void removeAllSensorPrivacyListener(android.hardware.SensorPrivacyManager.OnAllSensorPrivacyChangedListener onAllSensorPrivacyChangedListener) {
        synchronized (this.mListeners) {
            android.hardware.ISensorPrivacyListener iSensorPrivacyListener = this.mListeners.get(onAllSensorPrivacyChangedListener);
            if (iSensorPrivacyListener != null) {
                this.mListeners.remove(iSensorPrivacyListener);
                try {
                    this.mService.removeSensorPrivacyListener(iSensorPrivacyListener);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public boolean isAllSensorPrivacyEnabled() {
        try {
            return this.mService.isSensorPrivacyEnabled();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
