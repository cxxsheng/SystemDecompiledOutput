package android.hardware.devicestate;

/* loaded from: classes2.dex */
public final class DeviceStateManagerGlobal {
    private static final java.lang.String TAG = "DeviceStateManagerGlobal";
    private static android.hardware.devicestate.DeviceStateManagerGlobal sInstance;
    private android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateManagerCallback mCallback;
    private final android.hardware.devicestate.IDeviceStateManager mDeviceStateManager;
    private android.hardware.devicestate.DeviceStateInfo mLastReceivedInfo;
    private static final boolean DEBUG = android.os.Build.IS_DEBUGGABLE;
    private static int[] sFoldedDeviceStates = new int[0];
    private final java.lang.Object mLock = new java.lang.Object();
    private final java.util.ArrayList<android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper> mCallbacks = new java.util.ArrayList<>();
    private final android.util.ArrayMap<android.os.IBinder, android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateRequestWrapper> mRequests = new android.util.ArrayMap<>();

    public static android.hardware.devicestate.DeviceStateManagerGlobal getInstance() {
        android.hardware.devicestate.DeviceStateManagerGlobal deviceStateManagerGlobal;
        android.os.IBinder service;
        instantiateFoldedStateArray();
        synchronized (android.hardware.devicestate.DeviceStateManagerGlobal.class) {
            if (sInstance == null && (service = android.os.ServiceManager.getService(android.content.Context.DEVICE_STATE_SERVICE)) != null) {
                sInstance = new android.hardware.devicestate.DeviceStateManagerGlobal(android.hardware.devicestate.IDeviceStateManager.Stub.asInterface(service));
            }
            deviceStateManagerGlobal = sInstance;
        }
        return deviceStateManagerGlobal;
    }

    private static void instantiateFoldedStateArray() {
        android.app.Application currentApplication = android.app.ActivityThread.currentApplication();
        if (currentApplication != null) {
            sFoldedDeviceStates = currentApplication.getResources().getIntArray(com.android.internal.R.array.config_foldedDeviceStates);
        }
    }

    public DeviceStateManagerGlobal(android.hardware.devicestate.IDeviceStateManager iDeviceStateManager) {
        this.mDeviceStateManager = iDeviceStateManager;
        registerCallbackIfNeededLocked();
    }

    public int[] getSupportedStates() {
        android.hardware.devicestate.DeviceStateInfo deviceStateInfo;
        int[] copyOf;
        synchronized (this.mLock) {
            if (this.mLastReceivedInfo != null) {
                deviceStateInfo = this.mLastReceivedInfo;
            } else {
                try {
                    deviceStateInfo = this.mDeviceStateManager.getDeviceStateInfo();
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            copyOf = java.util.Arrays.copyOf(deviceStateInfo.supportedStates, deviceStateInfo.supportedStates.length);
        }
        return copyOf;
    }

    public java.util.List<android.hardware.devicestate.DeviceState> getSupportedDeviceStates() {
        android.hardware.devicestate.DeviceStateInfo deviceStateInfo;
        java.util.List<android.hardware.devicestate.DeviceState> createDeviceStateList;
        synchronized (this.mLock) {
            if (this.mLastReceivedInfo != null) {
                deviceStateInfo = this.mLastReceivedInfo;
            } else {
                try {
                    deviceStateInfo = this.mDeviceStateManager.getDeviceStateInfo();
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            createDeviceStateList = createDeviceStateList(deviceStateInfo.supportedStates);
        }
        return createDeviceStateList;
    }

    public void requestState(android.hardware.devicestate.DeviceStateRequest deviceStateRequest, java.util.concurrent.Executor executor, android.hardware.devicestate.DeviceStateRequest.Callback callback) {
        android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateRequestWrapper deviceStateRequestWrapper = new android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateRequestWrapper(deviceStateRequest, callback, executor);
        synchronized (this.mLock) {
            if (findRequestTokenLocked(deviceStateRequest) != null) {
                return;
            }
            android.os.Binder binder = new android.os.Binder();
            this.mRequests.put(binder, deviceStateRequestWrapper);
            try {
                this.mDeviceStateManager.requestState(binder, deviceStateRequest.getState(), deviceStateRequest.getFlags());
            } catch (android.os.RemoteException e) {
                this.mRequests.remove(binder);
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void cancelStateRequest() {
        synchronized (this.mLock) {
            try {
                try {
                    this.mDeviceStateManager.cancelStateRequest();
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void requestBaseStateOverride(android.hardware.devicestate.DeviceStateRequest deviceStateRequest, java.util.concurrent.Executor executor, android.hardware.devicestate.DeviceStateRequest.Callback callback) {
        android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateRequestWrapper deviceStateRequestWrapper = new android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateRequestWrapper(deviceStateRequest, callback, executor);
        synchronized (this.mLock) {
            if (findRequestTokenLocked(deviceStateRequest) != null) {
                return;
            }
            android.os.Binder binder = new android.os.Binder();
            this.mRequests.put(binder, deviceStateRequestWrapper);
            try {
                this.mDeviceStateManager.requestBaseStateOverride(binder, deviceStateRequest.getState(), deviceStateRequest.getFlags());
            } catch (android.os.RemoteException e) {
                this.mRequests.remove(binder);
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void cancelBaseStateOverride() {
        synchronized (this.mLock) {
            try {
                try {
                    this.mDeviceStateManager.cancelBaseStateOverride();
                } catch (android.os.RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void registerDeviceStateCallback(android.hardware.devicestate.DeviceStateManager.DeviceStateCallback deviceStateCallback, java.util.concurrent.Executor executor) {
        synchronized (this.mLock) {
            if (findCallbackLocked(deviceStateCallback) != -1) {
                return;
            }
            android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper deviceStateCallbackWrapper = new android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper(deviceStateCallback, executor);
            this.mCallbacks.add(deviceStateCallbackWrapper);
            if (this.mLastReceivedInfo != null) {
                int[] copyOf = java.util.Arrays.copyOf(this.mLastReceivedInfo.supportedStates, this.mLastReceivedInfo.supportedStates.length);
                deviceStateCallbackWrapper.notifySupportedStatesChanged(copyOf);
                deviceStateCallbackWrapper.notifySupportedDeviceStatesChanged(createDeviceStateList(copyOf));
                deviceStateCallbackWrapper.notifyBaseStateChanged(this.mLastReceivedInfo.baseState);
                deviceStateCallbackWrapper.notifyStateChanged(this.mLastReceivedInfo.currentState);
                deviceStateCallbackWrapper.notifyDeviceStateChanged(createDeviceState(this.mLastReceivedInfo.currentState));
            }
        }
    }

    public void unregisterDeviceStateCallback(android.hardware.devicestate.DeviceStateManager.DeviceStateCallback deviceStateCallback) {
        synchronized (this.mLock) {
            int findCallbackLocked = findCallbackLocked(deviceStateCallback);
            if (findCallbackLocked != -1) {
                this.mCallbacks.remove(findCallbackLocked);
            }
        }
    }

    public void onStateRequestOverlayDismissed(boolean z) {
        try {
            this.mDeviceStateManager.onStateRequestOverlayDismissed(z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private void registerCallbackIfNeededLocked() {
        if (this.mCallback == null) {
            this.mCallback = new android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateManagerCallback();
            try {
                this.mDeviceStateManager.registerCallback(this.mCallback);
            } catch (android.os.RemoteException e) {
                this.mCallback = null;
                throw e.rethrowFromSystemServer();
            }
        }
    }

    private int findCallbackLocked(android.hardware.devicestate.DeviceStateManager.DeviceStateCallback deviceStateCallback) {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            if (this.mCallbacks.get(i).mDeviceStateCallback.equals(deviceStateCallback)) {
                return i;
            }
        }
        return -1;
    }

    private android.os.IBinder findRequestTokenLocked(android.hardware.devicestate.DeviceStateRequest deviceStateRequest) {
        for (int i = 0; i < this.mRequests.size(); i++) {
            if (this.mRequests.valueAt(i).mRequest.equals(deviceStateRequest)) {
                return this.mRequests.keyAt(i);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDeviceStateInfoChanged(android.hardware.devicestate.DeviceStateInfo deviceStateInfo) {
        android.hardware.devicestate.DeviceStateInfo deviceStateInfo2;
        java.util.ArrayList arrayList;
        synchronized (this.mLock) {
            deviceStateInfo2 = this.mLastReceivedInfo;
            this.mLastReceivedInfo = deviceStateInfo;
            arrayList = new java.util.ArrayList(this.mCallbacks);
        }
        int diff = deviceStateInfo2 == null ? -1 : deviceStateInfo.diff(deviceStateInfo2);
        if ((diff & 1) > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                int[] copyOf = java.util.Arrays.copyOf(deviceStateInfo.supportedStates, deviceStateInfo.supportedStates.length);
                ((android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper) arrayList.get(i)).notifySupportedStatesChanged(copyOf);
                ((android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper) arrayList.get(i)).notifySupportedDeviceStatesChanged(createDeviceStateList(copyOf));
            }
        }
        if ((diff & 2) > 0) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                ((android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper) arrayList.get(i2)).notifyBaseStateChanged(deviceStateInfo.baseState);
            }
        }
        if ((diff & 4) > 0) {
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                ((android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper) arrayList.get(i3)).notifyStateChanged(deviceStateInfo.currentState);
                ((android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper) arrayList.get(i3)).notifyDeviceStateChanged(createDeviceState(deviceStateInfo.currentState));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRequestActive(android.os.IBinder iBinder) {
        android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateRequestWrapper deviceStateRequestWrapper;
        synchronized (this.mLock) {
            deviceStateRequestWrapper = this.mRequests.get(iBinder);
        }
        if (deviceStateRequestWrapper != null) {
            deviceStateRequestWrapper.notifyRequestActive();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleRequestCanceled(android.os.IBinder iBinder) {
        android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateRequestWrapper remove;
        synchronized (this.mLock) {
            remove = this.mRequests.remove(iBinder);
        }
        if (remove != null) {
            remove.notifyRequestCanceled();
        }
    }

    private android.hardware.devicestate.DeviceState createDeviceState(int i) {
        java.util.HashSet hashSet = new java.util.HashSet();
        if (com.android.internal.util.ArrayUtils.contains(sFoldedDeviceStates, i)) {
            hashSet.add(11);
        } else {
            hashSet.add(12);
        }
        return new android.hardware.devicestate.DeviceState(i, "", hashSet);
    }

    private java.util.List<android.hardware.devicestate.DeviceState> createDeviceStateList(int[] iArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i : iArr) {
            arrayList.add(createDeviceState(i));
        }
        return arrayList;
    }

    private final class DeviceStateManagerCallback extends android.hardware.devicestate.IDeviceStateManagerCallback.Stub {
        private DeviceStateManagerCallback() {
        }

        @Override // android.hardware.devicestate.IDeviceStateManagerCallback
        public void onDeviceStateInfoChanged(android.hardware.devicestate.DeviceStateInfo deviceStateInfo) {
            android.hardware.devicestate.DeviceStateManagerGlobal.this.handleDeviceStateInfoChanged(deviceStateInfo);
        }

        @Override // android.hardware.devicestate.IDeviceStateManagerCallback
        public void onRequestActive(android.os.IBinder iBinder) {
            android.hardware.devicestate.DeviceStateManagerGlobal.this.handleRequestActive(iBinder);
        }

        @Override // android.hardware.devicestate.IDeviceStateManagerCallback
        public void onRequestCanceled(android.os.IBinder iBinder) {
            android.hardware.devicestate.DeviceStateManagerGlobal.this.handleRequestCanceled(iBinder);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class DeviceStateCallbackWrapper {
        private final android.hardware.devicestate.DeviceStateManager.DeviceStateCallback mDeviceStateCallback;
        private final java.util.concurrent.Executor mExecutor;

        DeviceStateCallbackWrapper(android.hardware.devicestate.DeviceStateManager.DeviceStateCallback deviceStateCallback, java.util.concurrent.Executor executor) {
            this.mDeviceStateCallback = deviceStateCallback;
            this.mExecutor = executor;
        }

        void notifySupportedStatesChanged(final int[] iArr) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateCallbackWrapper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper.this.lambda$notifySupportedStatesChanged$0(iArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifySupportedStatesChanged$0(int[] iArr) {
            this.mDeviceStateCallback.onSupportedStatesChanged(iArr);
        }

        void notifySupportedDeviceStatesChanged(final java.util.List<android.hardware.devicestate.DeviceState> list) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateCallbackWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper.this.lambda$notifySupportedDeviceStatesChanged$1(list);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifySupportedDeviceStatesChanged$1(java.util.List list) {
            this.mDeviceStateCallback.onSupportedStatesChanged((java.util.List<android.hardware.devicestate.DeviceState>) list);
        }

        void notifyBaseStateChanged(final int i) {
            execute("notifyBaseStateChanged", new java.lang.Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateCallbackWrapper$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper.this.lambda$notifyBaseStateChanged$2(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyBaseStateChanged$2(int i) {
            this.mDeviceStateCallback.onBaseStateChanged(i);
        }

        void notifyStateChanged(final int i) {
            execute("notifyStateChanged", new java.lang.Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateCallbackWrapper$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper.this.lambda$notifyStateChanged$3(i);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyStateChanged$3(int i) {
            this.mDeviceStateCallback.onStateChanged(i);
        }

        void notifyDeviceStateChanged(final android.hardware.devicestate.DeviceState deviceState) {
            execute("notifyDeviceStateChanged", new java.lang.Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateCallbackWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper.this.lambda$notifyDeviceStateChanged$4(deviceState);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyDeviceStateChanged$4(android.hardware.devicestate.DeviceState deviceState) {
            this.mDeviceStateCallback.onDeviceStateChanged(deviceState);
        }

        private void execute(final java.lang.String str, final java.lang.Runnable runnable) {
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateCallbackWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateCallbackWrapper.this.lambda$execute$5(str, runnable);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$execute$5(java.lang.String str, java.lang.Runnable runnable) {
            if (android.hardware.devicestate.DeviceStateManagerGlobal.DEBUG) {
                android.os.Trace.beginSection(this.mDeviceStateCallback.getClass().getSimpleName() + "#" + str);
            }
            try {
                runnable.run();
            } finally {
                if (android.hardware.devicestate.DeviceStateManagerGlobal.DEBUG) {
                    android.os.Trace.endSection();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class DeviceStateRequestWrapper {
        private final android.hardware.devicestate.DeviceStateRequest.Callback mCallback;
        private final java.util.concurrent.Executor mExecutor;
        private final android.hardware.devicestate.DeviceStateRequest mRequest;

        DeviceStateRequestWrapper(android.hardware.devicestate.DeviceStateRequest deviceStateRequest, android.hardware.devicestate.DeviceStateRequest.Callback callback, java.util.concurrent.Executor executor) {
            validateRequestWrapperParameters(callback, executor);
            this.mRequest = deviceStateRequest;
            this.mCallback = callback;
            this.mExecutor = executor;
        }

        void notifyRequestActive() {
            if (this.mCallback == null) {
                return;
            }
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateRequestWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateRequestWrapper.this.lambda$notifyRequestActive$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyRequestActive$0() {
            this.mCallback.onRequestActivated(this.mRequest);
        }

        void notifyRequestCanceled() {
            if (this.mCallback == null) {
                return;
            }
            this.mExecutor.execute(new java.lang.Runnable() { // from class: android.hardware.devicestate.DeviceStateManagerGlobal$DeviceStateRequestWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.hardware.devicestate.DeviceStateManagerGlobal.DeviceStateRequestWrapper.this.lambda$notifyRequestCanceled$1();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyRequestCanceled$1() {
            this.mCallback.onRequestCanceled(this.mRequest);
        }

        private void validateRequestWrapperParameters(android.hardware.devicestate.DeviceStateRequest.Callback callback, java.util.concurrent.Executor executor) {
            if (callback == null && executor != null) {
                throw new java.lang.IllegalArgumentException("Callback must be supplied with executor.");
            }
            if (executor == null && callback != null) {
                throw new java.lang.IllegalArgumentException("Executor must be supplied with callback.");
            }
        }
    }
}
