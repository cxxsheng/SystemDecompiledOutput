package android.hardware.input;

/* loaded from: classes2.dex */
public final class InputManagerGlobal {
    private static android.hardware.input.InputManagerGlobal sInstance;
    private android.util.SparseArray<android.hardware.input.InputManagerGlobal.RegisteredBatteryListeners> mBatteryListeners;
    private final android.hardware.input.IInputManager mIm;
    private android.hardware.input.IInputDeviceBatteryListener mInputDeviceBatteryListener;
    private android.hardware.input.InputDeviceSensorManager mInputDeviceSensorManager;
    private android.util.SparseArray<android.view.InputDevice> mInputDevices;
    private android.hardware.input.InputManagerGlobal.InputDevicesChangedListener mInputDevicesChangedListener;
    private android.hardware.input.IKeyboardBacklightListener mKeyboardBacklightListener;
    private java.util.ArrayList<android.hardware.input.InputManagerGlobal.KeyboardBacklightListenerDelegate> mKeyboardBacklightListeners;
    private android.hardware.input.IStickyModifierStateListener mStickyModifierStateListener;
    private java.util.ArrayList<android.hardware.input.InputManagerGlobal.StickyModifierStateListenerDelegate> mStickyModifierStateListeners;
    private final java.lang.String mVelocityTrackerStrategy;
    private static final java.lang.String TAG = "InputManagerGlobal";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private final java.util.ArrayList<android.hardware.input.InputManagerGlobal.InputDeviceListenerDelegate> mInputDeviceListeners = new java.util.ArrayList<>();
    private final java.util.ArrayList<android.hardware.input.InputManagerGlobal.OnTabletModeChangedListenerDelegate> mOnTabletModeChangedListeners = new java.util.ArrayList<>();
    private final java.lang.Object mBatteryListenersLock = new java.lang.Object();
    private final java.lang.Object mKeyboardBacklightListenerLock = new java.lang.Object();
    private final java.lang.Object mStickyModifierStateListenerLock = new java.lang.Object();

    public interface TestSession extends java.lang.AutoCloseable {
        @Override // java.lang.AutoCloseable
        void close();
    }

    public InputManagerGlobal(android.hardware.input.IInputManager iInputManager) {
        java.lang.String str;
        this.mIm = iInputManager;
        try {
            str = this.mIm.getVelocityTrackerStrategy();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Could not get VelocityTracker strategy: " + e);
            str = null;
        }
        this.mVelocityTrackerStrategy = str;
    }

    public static android.hardware.input.InputManagerGlobal getInstance() {
        android.hardware.input.InputManagerGlobal inputManagerGlobal;
        android.os.IBinder service;
        synchronized (android.hardware.input.InputManagerGlobal.class) {
            if (sInstance == null && (service = android.os.ServiceManager.getService("input")) != null) {
                sInstance = new android.hardware.input.InputManagerGlobal(android.hardware.input.IInputManager.Stub.asInterface(service));
            }
            inputManagerGlobal = sInstance;
        }
        return inputManagerGlobal;
    }

    public android.hardware.input.IInputManager getInputManagerService() {
        return this.mIm;
    }

    public static android.hardware.input.InputManagerGlobal.TestSession createTestSession(android.hardware.input.IInputManager iInputManager) {
        android.hardware.input.InputManagerGlobal.TestSession testSession;
        synchronized (android.hardware.input.InputManagerGlobal.class) {
            final android.hardware.input.InputManagerGlobal inputManagerGlobal = sInstance;
            sInstance = new android.hardware.input.InputManagerGlobal(iInputManager);
            testSession = new android.hardware.input.InputManagerGlobal.TestSession() { // from class: android.hardware.input.InputManagerGlobal$$ExternalSyntheticLambda0
                @Override // android.hardware.input.InputManagerGlobal.TestSession, java.lang.AutoCloseable
                public final void close() {
                    android.hardware.input.InputManagerGlobal.sInstance = android.hardware.input.InputManagerGlobal.this;
                }
            };
        }
        return testSession;
    }

    public java.lang.String getVelocityTrackerStrategy() {
        return this.mVelocityTrackerStrategy;
    }

    public android.view.InputDevice getInputDevice(int i) {
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            int indexOfKey = this.mInputDevices.indexOfKey(i);
            if (indexOfKey < 0) {
                return null;
            }
            android.view.InputDevice valueAt = this.mInputDevices.valueAt(indexOfKey);
            if (valueAt == null) {
                try {
                    valueAt = this.mIm.getInputDevice(i);
                    if (valueAt != null) {
                        this.mInputDevices.setValueAt(indexOfKey, valueAt);
                    }
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            return valueAt;
        }
    }

    private void populateInputDevicesLocked() {
        if (this.mInputDevicesChangedListener == null) {
            android.hardware.input.InputManagerGlobal.InputDevicesChangedListener inputDevicesChangedListener = new android.hardware.input.InputManagerGlobal.InputDevicesChangedListener();
            try {
                this.mIm.registerInputDevicesChangedListener(inputDevicesChangedListener);
                this.mInputDevicesChangedListener = inputDevicesChangedListener;
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        if (this.mInputDevices == null) {
            try {
                int[] inputDeviceIds = this.mIm.getInputDeviceIds();
                this.mInputDevices = new android.util.SparseArray<>();
                for (int i : inputDeviceIds) {
                    this.mInputDevices.put(i, null);
                }
            } catch (android.os.RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
    }

    private final class InputDevicesChangedListener extends android.hardware.input.IInputDevicesChangedListener.Stub {
        private InputDevicesChangedListener() {
        }

        @Override // android.hardware.input.IInputDevicesChangedListener
        public void onInputDevicesChanged(int[] iArr) throws android.os.RemoteException {
            android.hardware.input.InputManagerGlobal.this.onInputDevicesChanged(iArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInputDevicesChanged(int[] iArr) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Received input devices changed.");
        }
        synchronized (this.mInputDeviceListeners) {
            int size = this.mInputDevices.size();
            while (true) {
                size--;
                if (size <= 0) {
                    break;
                }
                int keyAt = this.mInputDevices.keyAt(size);
                if (!containsDeviceId(iArr, keyAt)) {
                    if (DEBUG) {
                        android.util.Log.d(TAG, "Device removed: " + keyAt);
                    }
                    this.mInputDevices.removeAt(size);
                    if (this.mInputDeviceSensorManager != null) {
                        this.mInputDeviceSensorManager.onInputDeviceRemoved(keyAt);
                    }
                    sendMessageToInputDeviceListenersLocked(2, keyAt);
                }
            }
            for (int i = 0; i < iArr.length; i += 2) {
                int i2 = iArr[i];
                int indexOfKey = this.mInputDevices.indexOfKey(i2);
                if (indexOfKey >= 0) {
                    android.view.InputDevice valueAt = this.mInputDevices.valueAt(indexOfKey);
                    if (valueAt != null) {
                        if (valueAt.getGeneration() != iArr[i + 1]) {
                            if (DEBUG) {
                                android.util.Log.d(TAG, "Device changed: " + i2);
                            }
                            this.mInputDevices.setValueAt(indexOfKey, null);
                            if (this.mInputDeviceSensorManager != null) {
                                this.mInputDeviceSensorManager.onInputDeviceChanged(i2);
                            }
                            sendMessageToInputDeviceListenersLocked(3, i2);
                        }
                    }
                } else {
                    if (DEBUG) {
                        android.util.Log.d(TAG, "Device added: " + i2);
                    }
                    this.mInputDevices.put(i2, null);
                    if (this.mInputDeviceSensorManager != null) {
                        this.mInputDeviceSensorManager.onInputDeviceAdded(i2);
                    }
                    sendMessageToInputDeviceListenersLocked(1, i2);
                }
            }
        }
    }

    private static final class InputDeviceListenerDelegate extends android.os.Handler {
        static final int MSG_DEVICE_ADDED = 1;
        static final int MSG_DEVICE_CHANGED = 3;
        static final int MSG_DEVICE_REMOVED = 2;
        public final android.hardware.input.InputManager.InputDeviceListener mListener;

        InputDeviceListenerDelegate(android.hardware.input.InputManager.InputDeviceListener inputDeviceListener, android.os.Handler handler) {
            super(handler != null ? handler.getLooper() : android.os.Looper.myLooper());
            this.mListener = inputDeviceListener;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    this.mListener.onInputDeviceAdded(message.arg1);
                    break;
                case 2:
                    this.mListener.onInputDeviceRemoved(message.arg1);
                    break;
                case 3:
                    this.mListener.onInputDeviceChanged(message.arg1);
                    break;
            }
        }
    }

    private static boolean containsDeviceId(int[] iArr, int i) {
        for (int i2 = 0; i2 < iArr.length; i2 += 2) {
            if (iArr[i2] == i) {
                return true;
            }
        }
        return false;
    }

    private void sendMessageToInputDeviceListenersLocked(int i, int i2) {
        int size = this.mInputDeviceListeners.size();
        for (int i3 = 0; i3 < size; i3++) {
            android.hardware.input.InputManagerGlobal.InputDeviceListenerDelegate inputDeviceListenerDelegate = this.mInputDeviceListeners.get(i3);
            inputDeviceListenerDelegate.sendMessage(inputDeviceListenerDelegate.obtainMessage(i, i2, 0));
        }
    }

    public void registerInputDeviceListener(android.hardware.input.InputManager.InputDeviceListener inputDeviceListener, android.os.Handler handler) {
        java.util.Objects.requireNonNull(inputDeviceListener, "listener must not be null");
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            if (findInputDeviceListenerLocked(inputDeviceListener) < 0) {
                this.mInputDeviceListeners.add(new android.hardware.input.InputManagerGlobal.InputDeviceListenerDelegate(inputDeviceListener, handler));
            }
        }
    }

    public void unregisterInputDeviceListener(android.hardware.input.InputManager.InputDeviceListener inputDeviceListener) {
        if (inputDeviceListener == null) {
            throw new java.lang.IllegalArgumentException("listener must not be null");
        }
        synchronized (this.mInputDeviceListeners) {
            int findInputDeviceListenerLocked = findInputDeviceListenerLocked(inputDeviceListener);
            if (findInputDeviceListenerLocked >= 0) {
                this.mInputDeviceListeners.get(findInputDeviceListenerLocked).removeCallbacksAndMessages(null);
                this.mInputDeviceListeners.remove(findInputDeviceListenerLocked);
            }
        }
    }

    private int findInputDeviceListenerLocked(android.hardware.input.InputManager.InputDeviceListener inputDeviceListener) {
        int size = this.mInputDeviceListeners.size();
        for (int i = 0; i < size; i++) {
            if (this.mInputDeviceListeners.get(i).mListener == inputDeviceListener) {
                return i;
            }
        }
        return -1;
    }

    public int[] getInputDeviceIds() {
        int[] iArr;
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            int size = this.mInputDevices.size();
            iArr = new int[size];
            for (int i = 0; i < size; i++) {
                iArr[i] = this.mInputDevices.keyAt(i);
            }
        }
        return iArr;
    }

    public boolean isInputDeviceEnabled(int i) {
        try {
            return this.mIm.isInputDeviceEnabled(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Could not check enabled status of input device with id = " + i);
            throw e.rethrowFromSystemServer();
        }
    }

    public void enableInputDevice(int i) {
        try {
            this.mIm.enableInputDevice(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Could not enable input device with id = " + i);
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableInputDevice(int i) {
        try {
            this.mIm.disableInputDevice(i);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Could not disable input device with id = " + i);
            throw e.rethrowFromSystemServer();
        }
    }

    android.view.InputDevice getInputDeviceByDescriptor(java.lang.String str) {
        java.util.Objects.requireNonNull(str, "descriptor must not be null.");
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            int size = this.mInputDevices.size();
            for (int i = 0; i < size; i++) {
                android.view.InputDevice valueAt = this.mInputDevices.valueAt(i);
                if (valueAt == null) {
                    try {
                        valueAt = this.mIm.getInputDevice(this.mInputDevices.keyAt(i));
                        if (valueAt == null) {
                            continue;
                        } else {
                            this.mInputDevices.setValueAt(i, valueAt);
                        }
                    } catch (android.os.RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
                if (str.equals(valueAt.getDescriptor())) {
                    return valueAt;
                }
            }
            return null;
        }
    }

    android.hardware.input.HostUsiVersion getHostUsiVersion(android.view.Display display) {
        java.util.Objects.requireNonNull(display, "display should not be null");
        synchronized (this.mInputDeviceListeners) {
            populateInputDevicesLocked();
            for (int i = 0; i < this.mInputDevices.size(); i++) {
                android.view.InputDevice inputDevice = getInputDevice(this.mInputDevices.keyAt(i));
                if (inputDevice != null && inputDevice.getAssociatedDisplayId() == display.getDisplayId() && inputDevice.getHostUsiVersion() != null) {
                    return inputDevice.getHostUsiVersion();
                }
            }
            try {
                return this.mIm.getHostUsiVersionFromDisplayConfig(display.getDisplayId());
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTabletModeChanged(long j, boolean z) {
        if (DEBUG) {
            android.util.Log.d(TAG, "Received tablet mode changed: whenNanos=" + j + ", inTabletMode=" + z);
        }
        synchronized (this.mOnTabletModeChangedListeners) {
            int size = this.mOnTabletModeChangedListeners.size();
            for (int i = 0; i < size; i++) {
                this.mOnTabletModeChangedListeners.get(i).sendTabletModeChanged(j, z);
            }
        }
    }

    private final class TabletModeChangedListener extends android.hardware.input.ITabletModeChangedListener.Stub {
        private TabletModeChangedListener() {
        }

        @Override // android.hardware.input.ITabletModeChangedListener
        public void onTabletModeChanged(long j, boolean z) {
            android.hardware.input.InputManagerGlobal.this.onTabletModeChanged(j, z);
        }
    }

    private static final class OnTabletModeChangedListenerDelegate extends android.os.Handler {
        private static final int MSG_TABLET_MODE_CHANGED = 0;
        public final android.hardware.input.InputManager.OnTabletModeChangedListener mListener;

        OnTabletModeChangedListenerDelegate(android.hardware.input.InputManager.OnTabletModeChangedListener onTabletModeChangedListener, android.os.Handler handler) {
            super(handler != null ? handler.getLooper() : android.os.Looper.myLooper());
            this.mListener = onTabletModeChangedListener;
        }

        public void sendTabletModeChanged(long j, boolean z) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.argi1 = (int) j;
            obtain.argi2 = (int) (j >> 32);
            obtain.arg1 = java.lang.Boolean.valueOf(z);
            obtainMessage(0, obtain).sendToTarget();
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            if (message.what == 0) {
                this.mListener.onTabletModeChanged((r6.argi1 & 4294967295L) | (r6.argi2 << 32), ((java.lang.Boolean) ((com.android.internal.os.SomeArgs) message.obj).arg1).booleanValue());
            }
        }
    }

    void registerOnTabletModeChangedListener(android.hardware.input.InputManager.OnTabletModeChangedListener onTabletModeChangedListener, android.os.Handler handler) {
        java.util.Objects.requireNonNull(onTabletModeChangedListener, "listener must not be null");
        synchronized (this.mOnTabletModeChangedListeners) {
            if (this.mOnTabletModeChangedListeners == null) {
                initializeTabletModeListenerLocked();
            }
            if (findOnTabletModeChangedListenerLocked(onTabletModeChangedListener) < 0) {
                this.mOnTabletModeChangedListeners.add(new android.hardware.input.InputManagerGlobal.OnTabletModeChangedListenerDelegate(onTabletModeChangedListener, handler));
            }
        }
    }

    void unregisterOnTabletModeChangedListener(android.hardware.input.InputManager.OnTabletModeChangedListener onTabletModeChangedListener) {
        java.util.Objects.requireNonNull(onTabletModeChangedListener, "listener must not be null");
        synchronized (this.mOnTabletModeChangedListeners) {
            int findOnTabletModeChangedListenerLocked = findOnTabletModeChangedListenerLocked(onTabletModeChangedListener);
            if (findOnTabletModeChangedListenerLocked >= 0) {
                this.mOnTabletModeChangedListeners.remove(findOnTabletModeChangedListenerLocked).removeCallbacksAndMessages(null);
            }
        }
    }

    private void initializeTabletModeListenerLocked() {
        try {
            this.mIm.registerTabletModeChangedListener(new android.hardware.input.InputManagerGlobal.TabletModeChangedListener());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private int findOnTabletModeChangedListenerLocked(android.hardware.input.InputManager.OnTabletModeChangedListener onTabletModeChangedListener) {
        int size = this.mOnTabletModeChangedListeners.size();
        for (int i = 0; i < size; i++) {
            if (this.mOnTabletModeChangedListeners.get(i).mListener == onTabletModeChangedListener) {
                return i;
            }
        }
        return -1;
    }

    private static final class RegisteredBatteryListeners {
        final java.util.List<android.hardware.input.InputManagerGlobal.InputDeviceBatteryListenerDelegate> mDelegates;
        android.hardware.input.IInputDeviceBatteryState mInputDeviceBatteryState;

        private RegisteredBatteryListeners() {
            this.mDelegates = new java.util.ArrayList();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class InputDeviceBatteryListenerDelegate {
        final java.util.concurrent.Executor mExecutor;
        final android.hardware.input.InputManager.InputDeviceBatteryListener mListener;

        InputDeviceBatteryListenerDelegate(android.hardware.input.InputManager.InputDeviceBatteryListener inputDeviceBatteryListener, java.util.concurrent.Executor executor) {
            this.mListener = inputDeviceBatteryListener;
            this.mExecutor = executor;
        }

        void notifyBatteryStateChanged(final android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.input.InputManagerGlobal$InputDeviceBatteryListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.input.InputManagerGlobal.InputDeviceBatteryListenerDelegate.this.lambda$notifyBatteryStateChanged$0(iInputDeviceBatteryState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyBatteryStateChanged$0(android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState) {
            this.mListener.onBatteryStateChanged(iInputDeviceBatteryState.deviceId, iInputDeviceBatteryState.updateTime, new android.hardware.input.InputManagerGlobal.LocalBatteryState(iInputDeviceBatteryState.isPresent, iInputDeviceBatteryState.status, iInputDeviceBatteryState.capacity));
        }
    }

    public void addInputDeviceBatteryListener(int i, java.util.concurrent.Executor executor, android.hardware.input.InputManager.InputDeviceBatteryListener inputDeviceBatteryListener) {
        java.util.Objects.requireNonNull(executor, "executor should not be null");
        java.util.Objects.requireNonNull(inputDeviceBatteryListener, "listener should not be null");
        synchronized (this.mBatteryListenersLock) {
            byte b = 0;
            if (this.mBatteryListeners == null) {
                this.mBatteryListeners = new android.util.SparseArray<>();
                this.mInputDeviceBatteryListener = new android.hardware.input.InputManagerGlobal.LocalInputDeviceBatteryListener();
            }
            android.hardware.input.InputManagerGlobal.RegisteredBatteryListeners registeredBatteryListeners = this.mBatteryListeners.get(i);
            if (registeredBatteryListeners == null) {
                registeredBatteryListeners = new android.hardware.input.InputManagerGlobal.RegisteredBatteryListeners();
                this.mBatteryListeners.put(i, registeredBatteryListeners);
                try {
                    this.mIm.registerBatteryListener(i, this.mInputDeviceBatteryListener);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } else {
                int size = registeredBatteryListeners.mDelegates.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (java.util.Objects.equals(inputDeviceBatteryListener, registeredBatteryListeners.mDelegates.get(i2).mListener)) {
                        throw new java.lang.IllegalArgumentException("Attempting to register an InputDeviceBatteryListener that has already been registered for deviceId: " + i);
                    }
                }
            }
            android.hardware.input.InputManagerGlobal.InputDeviceBatteryListenerDelegate inputDeviceBatteryListenerDelegate = new android.hardware.input.InputManagerGlobal.InputDeviceBatteryListenerDelegate(inputDeviceBatteryListener, executor);
            registeredBatteryListeners.mDelegates.add(inputDeviceBatteryListenerDelegate);
            if (registeredBatteryListeners.mInputDeviceBatteryState != null) {
                inputDeviceBatteryListenerDelegate.notifyBatteryStateChanged(registeredBatteryListeners.mInputDeviceBatteryState);
            }
        }
    }

    void removeInputDeviceBatteryListener(int i, android.hardware.input.InputManager.InputDeviceBatteryListener inputDeviceBatteryListener) {
        java.util.Objects.requireNonNull(inputDeviceBatteryListener, "listener should not be null");
        synchronized (this.mBatteryListenersLock) {
            if (this.mBatteryListeners == null) {
                return;
            }
            android.hardware.input.InputManagerGlobal.RegisteredBatteryListeners registeredBatteryListeners = this.mBatteryListeners.get(i);
            if (registeredBatteryListeners == null) {
                return;
            }
            java.util.List<android.hardware.input.InputManagerGlobal.InputDeviceBatteryListenerDelegate> list = registeredBatteryListeners.mDelegates;
            int i2 = 0;
            while (i2 < list.size()) {
                if (java.util.Objects.equals(inputDeviceBatteryListener, list.get(i2).mListener)) {
                    list.remove(i2);
                } else {
                    i2++;
                }
            }
            if (list.isEmpty()) {
                this.mBatteryListeners.remove(i);
                try {
                    this.mIm.unregisterBatteryListener(i, this.mInputDeviceBatteryListener);
                    if (this.mBatteryListeners.size() == 0) {
                        this.mBatteryListeners = null;
                        this.mInputDeviceBatteryListener = null;
                    }
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    private class LocalInputDeviceBatteryListener extends android.hardware.input.IInputDeviceBatteryListener.Stub {
        private LocalInputDeviceBatteryListener() {
        }

        @Override // android.hardware.input.IInputDeviceBatteryListener
        public void onBatteryStateChanged(android.hardware.input.IInputDeviceBatteryState iInputDeviceBatteryState) {
            synchronized (android.hardware.input.InputManagerGlobal.this.mBatteryListenersLock) {
                if (android.hardware.input.InputManagerGlobal.this.mBatteryListeners == null) {
                    return;
                }
                android.hardware.input.InputManagerGlobal.RegisteredBatteryListeners registeredBatteryListeners = (android.hardware.input.InputManagerGlobal.RegisteredBatteryListeners) android.hardware.input.InputManagerGlobal.this.mBatteryListeners.get(iInputDeviceBatteryState.deviceId);
                if (registeredBatteryListeners == null) {
                    return;
                }
                registeredBatteryListeners.mInputDeviceBatteryState = iInputDeviceBatteryState;
                int size = registeredBatteryListeners.mDelegates.size();
                for (int i = 0; i < size; i++) {
                    registeredBatteryListeners.mDelegates.get(i).notifyBatteryStateChanged(registeredBatteryListeners.mInputDeviceBatteryState);
                }
            }
        }
    }

    public android.hardware.BatteryState getInputDeviceBatteryState(int i, boolean z) {
        if (!z) {
            return new android.hardware.input.InputManagerGlobal.LocalBatteryState();
        }
        try {
            android.hardware.input.IInputDeviceBatteryState batteryState = this.mIm.getBatteryState(i);
            return new android.hardware.input.InputManagerGlobal.LocalBatteryState(batteryState.isPresent, batteryState.status, batteryState.capacity);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static final class LocalBatteryState extends android.hardware.BatteryState {
        private final float mCapacity;
        private final boolean mIsPresent;
        private final int mStatus;

        LocalBatteryState() {
            this(false, 1, Float.NaN);
        }

        LocalBatteryState(boolean z, int i, float f) {
            this.mIsPresent = z;
            this.mStatus = i;
            this.mCapacity = f;
        }

        @Override // android.hardware.BatteryState
        public boolean isPresent() {
            return this.mIsPresent;
        }

        @Override // android.hardware.BatteryState
        public int getStatus() {
            return this.mStatus;
        }

        @Override // android.hardware.BatteryState
        public float getCapacity() {
            return this.mCapacity;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class KeyboardBacklightListenerDelegate {
        final java.util.concurrent.Executor mExecutor;
        final android.hardware.input.InputManager.KeyboardBacklightListener mListener;

        KeyboardBacklightListenerDelegate(android.hardware.input.InputManager.KeyboardBacklightListener keyboardBacklightListener, java.util.concurrent.Executor executor) {
            this.mListener = keyboardBacklightListener;
            this.mExecutor = executor;
        }

        void notifyKeyboardBacklightChange(final int i, final android.hardware.input.IKeyboardBacklightState iKeyboardBacklightState, final boolean z) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.input.InputManagerGlobal$KeyboardBacklightListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.input.InputManagerGlobal.KeyboardBacklightListenerDelegate.this.lambda$notifyKeyboardBacklightChange$0(i, iKeyboardBacklightState, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyKeyboardBacklightChange$0(int i, android.hardware.input.IKeyboardBacklightState iKeyboardBacklightState, boolean z) {
            this.mListener.onKeyboardBacklightChanged(i, new android.hardware.input.InputManagerGlobal.LocalKeyboardBacklightState(iKeyboardBacklightState.brightnessLevel, iKeyboardBacklightState.maxBrightnessLevel), z);
        }
    }

    private class LocalKeyboardBacklightListener extends android.hardware.input.IKeyboardBacklightListener.Stub {
        private LocalKeyboardBacklightListener() {
        }

        @Override // android.hardware.input.IKeyboardBacklightListener
        public void onBrightnessChanged(int i, android.hardware.input.IKeyboardBacklightState iKeyboardBacklightState, boolean z) {
            synchronized (android.hardware.input.InputManagerGlobal.this.mKeyboardBacklightListenerLock) {
                if (android.hardware.input.InputManagerGlobal.this.mKeyboardBacklightListeners == null) {
                    return;
                }
                int size = android.hardware.input.InputManagerGlobal.this.mKeyboardBacklightListeners.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((android.hardware.input.InputManagerGlobal.KeyboardBacklightListenerDelegate) android.hardware.input.InputManagerGlobal.this.mKeyboardBacklightListeners.get(i2)).notifyKeyboardBacklightChange(i, iKeyboardBacklightState, z);
                }
            }
        }
    }

    private static final class LocalKeyboardBacklightState extends android.hardware.input.KeyboardBacklightState {
        private final int mBrightnessLevel;
        private final int mMaxBrightnessLevel;

        LocalKeyboardBacklightState(int i, int i2) {
            this.mBrightnessLevel = i;
            this.mMaxBrightnessLevel = i2;
        }

        @Override // android.hardware.input.KeyboardBacklightState
        public int getBrightnessLevel() {
            return this.mBrightnessLevel;
        }

        @Override // android.hardware.input.KeyboardBacklightState
        public int getMaxBrightnessLevel() {
            return this.mMaxBrightnessLevel;
        }
    }

    void registerKeyboardBacklightListener(java.util.concurrent.Executor executor, android.hardware.input.InputManager.KeyboardBacklightListener keyboardBacklightListener) throws java.lang.IllegalArgumentException {
        java.util.Objects.requireNonNull(executor, "executor should not be null");
        java.util.Objects.requireNonNull(keyboardBacklightListener, "listener should not be null");
        synchronized (this.mKeyboardBacklightListenerLock) {
            if (this.mKeyboardBacklightListener == null) {
                this.mKeyboardBacklightListeners = new java.util.ArrayList<>();
                this.mKeyboardBacklightListener = new android.hardware.input.InputManagerGlobal.LocalKeyboardBacklightListener();
                try {
                    this.mIm.registerKeyboardBacklightListener(this.mKeyboardBacklightListener);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            int size = this.mKeyboardBacklightListeners.size();
            for (int i = 0; i < size; i++) {
                if (this.mKeyboardBacklightListeners.get(i).mListener == keyboardBacklightListener) {
                    throw new java.lang.IllegalArgumentException("Listener has already been registered!");
                }
            }
            this.mKeyboardBacklightListeners.add(new android.hardware.input.InputManagerGlobal.KeyboardBacklightListenerDelegate(keyboardBacklightListener, executor));
        }
    }

    void unregisterKeyboardBacklightListener(final android.hardware.input.InputManager.KeyboardBacklightListener keyboardBacklightListener) {
        java.util.Objects.requireNonNull(keyboardBacklightListener, "listener should not be null");
        synchronized (this.mKeyboardBacklightListenerLock) {
            if (this.mKeyboardBacklightListeners == null) {
                return;
            }
            this.mKeyboardBacklightListeners.removeIf(new java.util.function.Predicate() { // from class: android.hardware.input.InputManagerGlobal$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return android.hardware.input.InputManagerGlobal.lambda$unregisterKeyboardBacklightListener$1(android.hardware.input.InputManager.KeyboardBacklightListener.this, (android.hardware.input.InputManagerGlobal.KeyboardBacklightListenerDelegate) obj);
                }
            });
            if (this.mKeyboardBacklightListeners.isEmpty()) {
                try {
                    this.mIm.unregisterKeyboardBacklightListener(this.mKeyboardBacklightListener);
                    this.mKeyboardBacklightListeners = null;
                    this.mKeyboardBacklightListener = null;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    static /* synthetic */ boolean lambda$unregisterKeyboardBacklightListener$1(android.hardware.input.InputManager.KeyboardBacklightListener keyboardBacklightListener, android.hardware.input.InputManagerGlobal.KeyboardBacklightListenerDelegate keyboardBacklightListenerDelegate) {
        return keyboardBacklightListenerDelegate.mListener == keyboardBacklightListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class StickyModifierStateListenerDelegate {
        final java.util.concurrent.Executor mExecutor;
        final android.hardware.input.InputManager.StickyModifierStateListener mListener;

        StickyModifierStateListenerDelegate(android.hardware.input.InputManager.StickyModifierStateListener stickyModifierStateListener, java.util.concurrent.Executor executor) {
            this.mListener = stickyModifierStateListener;
            this.mExecutor = executor;
        }

        void notifyStickyModifierStateChange(final int i, final int i2) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.input.InputManagerGlobal$StickyModifierStateListenerDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.input.InputManagerGlobal.StickyModifierStateListenerDelegate.this.lambda$notifyStickyModifierStateChange$0(i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyStickyModifierStateChange$0(int i, int i2) {
            this.mListener.onStickyModifierStateChanged(new android.hardware.input.InputManagerGlobal.LocalStickyModifierState(i, i2));
        }
    }

    private class LocalStickyModifierStateListener extends android.hardware.input.IStickyModifierStateListener.Stub {
        private LocalStickyModifierStateListener() {
        }

        @Override // android.hardware.input.IStickyModifierStateListener
        public void onStickyModifierStateChanged(int i, int i2) {
            synchronized (android.hardware.input.InputManagerGlobal.this.mStickyModifierStateListenerLock) {
                if (android.hardware.input.InputManagerGlobal.this.mStickyModifierStateListeners == null) {
                    return;
                }
                int size = android.hardware.input.InputManagerGlobal.this.mStickyModifierStateListeners.size();
                for (int i3 = 0; i3 < size; i3++) {
                    ((android.hardware.input.InputManagerGlobal.StickyModifierStateListenerDelegate) android.hardware.input.InputManagerGlobal.this.mStickyModifierStateListeners.get(i3)).notifyStickyModifierStateChange(i, i2);
                }
            }
        }
    }

    private static final class LocalStickyModifierState extends android.hardware.input.StickyModifierState {
        private final int mLockedModifierState;
        private final int mModifierState;

        LocalStickyModifierState(int i, int i2) {
            this.mModifierState = i;
            this.mLockedModifierState = i2;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isShiftModifierOn() {
            return (this.mModifierState & 1) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isShiftModifierLocked() {
            return (this.mLockedModifierState & 1) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isCtrlModifierOn() {
            return (this.mModifierState & 4096) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isCtrlModifierLocked() {
            return (this.mLockedModifierState & 4096) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isMetaModifierOn() {
            return (this.mModifierState & 65536) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isMetaModifierLocked() {
            return (this.mLockedModifierState & 65536) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isAltModifierOn() {
            return (this.mModifierState & 16) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isAltModifierLocked() {
            return (this.mLockedModifierState & 16) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isAltGrModifierOn() {
            return (this.mModifierState & 32) != 0;
        }

        @Override // android.hardware.input.StickyModifierState
        public boolean isAltGrModifierLocked() {
            return (this.mLockedModifierState & 32) != 0;
        }
    }

    void registerStickyModifierStateListener(java.util.concurrent.Executor executor, android.hardware.input.InputManager.StickyModifierStateListener stickyModifierStateListener) throws java.lang.IllegalArgumentException {
        java.util.Objects.requireNonNull(executor, "executor should not be null");
        java.util.Objects.requireNonNull(stickyModifierStateListener, "listener should not be null");
        synchronized (this.mStickyModifierStateListenerLock) {
            if (this.mStickyModifierStateListener == null) {
                this.mStickyModifierStateListeners = new java.util.ArrayList<>();
                this.mStickyModifierStateListener = new android.hardware.input.InputManagerGlobal.LocalStickyModifierStateListener();
                try {
                    this.mIm.registerStickyModifierStateListener(this.mStickyModifierStateListener);
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            int size = this.mStickyModifierStateListeners.size();
            for (int i = 0; i < size; i++) {
                if (this.mStickyModifierStateListeners.get(i).mListener == stickyModifierStateListener) {
                    throw new java.lang.IllegalArgumentException("Listener has already been registered!");
                }
            }
            this.mStickyModifierStateListeners.add(new android.hardware.input.InputManagerGlobal.StickyModifierStateListenerDelegate(stickyModifierStateListener, executor));
        }
    }

    void unregisterStickyModifierStateListener(final android.hardware.input.InputManager.StickyModifierStateListener stickyModifierStateListener) {
        java.util.Objects.requireNonNull(stickyModifierStateListener, "listener should not be null");
        synchronized (this.mStickyModifierStateListenerLock) {
            if (this.mStickyModifierStateListeners == null) {
                return;
            }
            this.mStickyModifierStateListeners.removeIf(new java.util.function.Predicate() { // from class: android.hardware.input.InputManagerGlobal$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return android.hardware.input.InputManagerGlobal.lambda$unregisterStickyModifierStateListener$2(android.hardware.input.InputManager.StickyModifierStateListener.this, (android.hardware.input.InputManagerGlobal.StickyModifierStateListenerDelegate) obj);
                }
            });
            if (this.mStickyModifierStateListeners.isEmpty()) {
                try {
                    this.mIm.unregisterStickyModifierStateListener(this.mStickyModifierStateListener);
                    this.mStickyModifierStateListeners = null;
                    this.mStickyModifierStateListener = null;
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    static /* synthetic */ boolean lambda$unregisterStickyModifierStateListener$2(android.hardware.input.InputManager.StickyModifierStateListener stickyModifierStateListener, android.hardware.input.InputManagerGlobal.StickyModifierStateListenerDelegate stickyModifierStateListenerDelegate) {
        return stickyModifierStateListenerDelegate.mListener == stickyModifierStateListener;
    }

    public android.hardware.input.KeyboardLayout[] getKeyboardLayoutsForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) {
        try {
            return this.mIm.getKeyboardLayoutsForInputDevice(inputDeviceIdentifier);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) {
        java.util.Objects.requireNonNull(inputDeviceIdentifier, "identifier must not be null");
        java.util.Objects.requireNonNull(str, "keyboardLayoutDescriptor must not be null");
        try {
            this.mIm.setCurrentKeyboardLayoutForInputDevice(inputDeviceIdentifier, str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.SensorManager getInputDeviceSensorManager(int i) {
        android.hardware.SensorManager sensorManager;
        synchronized (this.mInputDeviceListeners) {
            if (this.mInputDeviceSensorManager == null) {
                this.mInputDeviceSensorManager = new android.hardware.input.InputDeviceSensorManager(this);
            }
            sensorManager = this.mInputDeviceSensorManager.getSensorManager(i);
        }
        return sensorManager;
    }

    android.hardware.input.InputSensorInfo[] getSensorList(int i) {
        try {
            return this.mIm.getSensorList(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean enableSensor(int i, int i2, int i3, int i4) {
        try {
            return this.mIm.enableSensor(i, i2, i3, i4);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void disableSensor(int i, int i2) {
        try {
            this.mIm.disableSensor(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean flushSensor(int i, int i2) {
        try {
            return this.mIm.flushSensor(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean registerSensorListener(android.hardware.input.IInputSensorEventListener iInputSensorEventListener) {
        try {
            return this.mIm.registerSensorListener(iInputSensorEventListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void unregisterSensorListener(android.hardware.input.IInputSensorEventListener iInputSensorEventListener) {
        try {
            this.mIm.unregisterSensorListener(iInputSensorEventListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.hardware.lights.LightsManager getInputDeviceLightsManager(int i) {
        return new android.hardware.input.InputDeviceLightsManager(i);
    }

    java.util.List<android.hardware.lights.Light> getLights(int i) {
        try {
            return this.mIm.getLights(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    android.hardware.lights.LightState getLightState(int i, android.hardware.lights.Light light) {
        try {
            return this.mIm.getLightState(i, light.getId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void requestLights(int i, android.hardware.lights.LightsRequest lightsRequest, android.os.IBinder iBinder) {
        try {
            java.util.List<java.lang.Integer> lights = lightsRequest.getLights();
            int size = lights.size();
            int[] iArr = new int[size];
            for (int i2 = 0; i2 < size; i2++) {
                iArr[i2] = lights.get(i2).intValue();
            }
            this.mIm.setLightStates(i, iArr, (android.hardware.lights.LightState[]) lightsRequest.getLightStates().toArray(new android.hardware.lights.LightState[0]), iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void openLightSession(int i, java.lang.String str, android.os.IBinder iBinder) {
        try {
            this.mIm.openLightSession(i, str, iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void closeLightSession(int i, android.os.IBinder iBinder) {
        try {
            this.mIm.closeLightSession(i, iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.os.Vibrator getInputDeviceVibrator(int i, int i2) {
        return new android.hardware.input.InputDeviceVibrator(i, i2);
    }

    public android.os.VibratorManager getInputDeviceVibratorManager(int i) {
        return new android.hardware.input.InputDeviceVibratorManager(i);
    }

    int[] getVibratorIds(int i) {
        try {
            return this.mIm.getVibratorIds(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void vibrate(int i, android.os.VibrationEffect vibrationEffect, android.os.IBinder iBinder) {
        try {
            this.mIm.vibrate(i, vibrationEffect, iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void vibrate(int i, android.os.CombinedVibration combinedVibration, android.os.IBinder iBinder) {
        try {
            this.mIm.vibrateCombined(i, combinedVibration, iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    void cancelVibrate(int i, android.os.IBinder iBinder) {
        try {
            this.mIm.cancelVibrate(i, iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean isVibrating(int i) {
        try {
            return this.mIm.isVibrating(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean registerVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) {
        try {
            return this.mIm.registerVibratorStateListener(i, iVibratorStateListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    boolean unregisterVibratorStateListener(int i, android.os.IVibratorStateListener iVibratorStateListener) {
        try {
            return this.mIm.unregisterVibratorStateListener(i, iVibratorStateListener);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean[] deviceHasKeys(int[] iArr) {
        return deviceHasKeys(-1, iArr);
    }

    public boolean[] deviceHasKeys(int i, int[] iArr) {
        boolean[] zArr = new boolean[iArr.length];
        try {
            this.mIm.hasKeys(i, -256, iArr, zArr);
            return zArr;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getKeyCodeForKeyLocation(int i, int i2) {
        try {
            return this.mIm.getKeyCodeForKeyLocation(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.view.KeyCharacterMap getKeyCharacterMap(android.hardware.input.KeyboardLayout keyboardLayout) {
        if (keyboardLayout == null) {
            return android.view.KeyCharacterMap.load(-1);
        }
        try {
            return this.mIm.getKeyCharacterMap(keyboardLayout.getDescriptor());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean injectInputEvent(android.view.InputEvent inputEvent, int i, int i2) {
        java.util.Objects.requireNonNull(inputEvent, "event must not be null");
        if (i != 0 && i != 2 && i != 1) {
            throw new java.lang.IllegalArgumentException("mode is invalid");
        }
        try {
            return this.mIm.injectInputEventToTarget(inputEvent, i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean injectInputEvent(android.view.InputEvent inputEvent, int i) {
        return injectInputEvent(inputEvent, i, -1);
    }

    public void setPointerIconType(int i) {
        try {
            this.mIm.setPointerIconType(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setCustomPointerIcon(android.view.PointerIcon pointerIcon) {
        try {
            this.mIm.setCustomPointerIcon(pointerIcon);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setPointerIcon(android.view.PointerIcon pointerIcon, int i, int i2, int i3, android.os.IBinder iBinder) {
        try {
            return this.mIm.setPointerIcon(pointerIcon, i, i2, i3, iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestPointerCapture(android.os.IBinder iBinder, boolean z) {
        try {
            this.mIm.requestPointerCapture(iBinder, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.view.InputMonitor monitorGestureInput(java.lang.String str, int i) {
        try {
            return this.mIm.monitorGestureInput(new android.os.Binder(), str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void addUniqueIdAssociation(java.lang.String str, java.lang.String str2) {
        try {
            this.mIm.addUniqueIdAssociation(str, str2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeUniqueIdAssociation(java.lang.String str) {
        try {
            this.mIm.removeUniqueIdAssociation(str);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.lang.String getInputDeviceBluetoothAddress(int i) {
        try {
            return this.mIm.getInputDeviceBluetoothAddress(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void cancelCurrentTouch() {
        try {
            this.mIm.cancelCurrentTouch();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void pilferPointers(android.os.IBinder iBinder) {
        try {
            this.mIm.pilferPointers(iBinder);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
