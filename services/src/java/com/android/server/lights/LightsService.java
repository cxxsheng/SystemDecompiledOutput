package com.android.server.lights;

/* loaded from: classes2.dex */
public class LightsService extends com.android.server.SystemService {
    static final boolean DEBUG = false;
    static final java.lang.String TAG = "LightsService";
    private android.os.Handler mH;
    private final android.util.SparseArray<com.android.server.lights.LightsService.LightImpl> mLightsById;
    private final com.android.server.lights.LightsService.LightImpl[] mLightsByType;

    @com.android.internal.annotations.VisibleForTesting
    final com.android.server.lights.LightsService.LightsManagerBinderService mManagerService;
    private final com.android.server.lights.LightsManager mService;

    @android.annotation.Nullable
    private final java.util.function.Supplier<android.hardware.light.ILights> mVintfLights;

    static native void setLight_native(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    /* JADX INFO: Access modifiers changed from: private */
    final class LightsManagerBinderService extends android.hardware.lights.ILightsManager.Stub {

        @com.android.internal.annotations.GuardedBy({"LightsService.this"})
        private final java.util.List<com.android.server.lights.LightsService.LightsManagerBinderService.Session> mSessions;

        LightsManagerBinderService() {
            super(android.os.PermissionEnforcer.fromContext(com.android.server.lights.LightsService.this.getContext()));
            this.mSessions = new java.util.ArrayList();
        }

        private final class Session implements java.lang.Comparable<com.android.server.lights.LightsService.LightsManagerBinderService.Session> {
            final int mPriority;
            final android.util.SparseArray<android.hardware.lights.LightState> mRequests = new android.util.SparseArray<>();
            final android.os.IBinder mToken;

            Session(android.os.IBinder iBinder, int i) {
                this.mToken = iBinder;
                this.mPriority = i;
            }

            void setRequest(int i, android.hardware.lights.LightState lightState) {
                if (lightState != null) {
                    this.mRequests.put(i, lightState);
                } else {
                    this.mRequests.remove(i);
                }
            }

            @Override // java.lang.Comparable
            public int compareTo(com.android.server.lights.LightsService.LightsManagerBinderService.Session session) {
                return java.lang.Integer.compare(session.mPriority, this.mPriority);
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DEVICE_LIGHTS")
        public java.util.List<android.hardware.lights.Light> getLights() {
            java.util.ArrayList arrayList;
            getLights_enforcePermission();
            synchronized (com.android.server.lights.LightsService.this) {
                try {
                    arrayList = new java.util.ArrayList();
                    for (int i = 0; i < com.android.server.lights.LightsService.this.mLightsById.size(); i++) {
                        if (!((com.android.server.lights.LightsService.LightImpl) com.android.server.lights.LightsService.this.mLightsById.valueAt(i)).isSystemLight()) {
                            android.hardware.light.HwLight hwLight = ((com.android.server.lights.LightsService.LightImpl) com.android.server.lights.LightsService.this.mLightsById.valueAt(i)).mHwLight;
                            arrayList.add(new android.hardware.lights.Light(hwLight.id, hwLight.ordinal, hwLight.type));
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return arrayList;
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DEVICE_LIGHTS")
        public void setLightStates(android.os.IBinder iBinder, int[] iArr, android.hardware.lights.LightState[] lightStateArr) {
            setLightStates_enforcePermission();
            boolean z = true;
            com.android.internal.util.Preconditions.checkState(iArr.length == lightStateArr.length);
            synchronized (com.android.server.lights.LightsService.this) {
                try {
                    com.android.server.lights.LightsService.LightsManagerBinderService.Session sessionLocked = getSessionLocked((android.os.IBinder) com.android.internal.util.Preconditions.checkNotNull(iBinder));
                    if (sessionLocked == null) {
                        z = false;
                    }
                    com.android.internal.util.Preconditions.checkState(z, "not registered");
                    checkRequestIsValid(iArr);
                    for (int i = 0; i < iArr.length; i++) {
                        sessionLocked.setRequest(iArr[i], lightStateArr[i]);
                    }
                    invalidateLightStatesLocked();
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DEVICE_LIGHTS")
        @android.annotation.Nullable
        public android.hardware.lights.LightState getLightState(int i) {
            android.hardware.lights.LightState lightState;
            getLightState_enforcePermission();
            synchronized (com.android.server.lights.LightsService.this) {
                try {
                    com.android.server.lights.LightsService.LightImpl lightImpl = (com.android.server.lights.LightsService.LightImpl) com.android.server.lights.LightsService.this.mLightsById.get(i);
                    if (lightImpl == null || lightImpl.isSystemLight()) {
                        throw new java.lang.IllegalArgumentException("Invalid light: " + i);
                    }
                    lightState = new android.hardware.lights.LightState(lightImpl.getColor());
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return lightState;
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DEVICE_LIGHTS")
        public void openSession(final android.os.IBinder iBinder, int i) {
            openSession_enforcePermission();
            com.android.internal.util.Preconditions.checkNotNull(iBinder);
            synchronized (com.android.server.lights.LightsService.this) {
                try {
                    com.android.internal.util.Preconditions.checkState(getSessionLocked(iBinder) == null, "already registered");
                    try {
                        iBinder.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.lights.LightsService$LightsManagerBinderService$$ExternalSyntheticLambda0
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                com.android.server.lights.LightsService.LightsManagerBinderService.this.lambda$openSession$0(iBinder);
                            }
                        }, 0);
                        this.mSessions.add(new com.android.server.lights.LightsService.LightsManagerBinderService.Session(iBinder, i));
                        java.util.Collections.sort(this.mSessions);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.lights.LightsService.TAG, "Couldn't open session, client already died", e);
                        throw new java.lang.IllegalArgumentException("Client is already dead.");
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @android.annotation.EnforcePermission("android.permission.CONTROL_DEVICE_LIGHTS")
        public void closeSession(android.os.IBinder iBinder) {
            closeSession_enforcePermission();
            com.android.internal.util.Preconditions.checkNotNull(iBinder);
            lambda$openSession$0(iBinder);
        }

        protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
            if (com.android.internal.util.DumpUtils.checkDumpPermission(com.android.server.lights.LightsService.this.getContext(), com.android.server.lights.LightsService.TAG, printWriter)) {
                synchronized (com.android.server.lights.LightsService.this) {
                    try {
                        if (com.android.server.lights.LightsService.this.mVintfLights != null) {
                            printWriter.println("Service: aidl (" + com.android.server.lights.LightsService.this.mVintfLights.get() + ")");
                        } else {
                            printWriter.println("Service: hidl");
                        }
                        printWriter.println("Lights:");
                        for (int i = 0; i < com.android.server.lights.LightsService.this.mLightsById.size(); i++) {
                            com.android.server.lights.LightsService.LightImpl lightImpl = (com.android.server.lights.LightsService.LightImpl) com.android.server.lights.LightsService.this.mLightsById.valueAt(i);
                            printWriter.println(java.lang.String.format("  Light id=%d ordinal=%d color=%08x", java.lang.Integer.valueOf(lightImpl.mHwLight.id), java.lang.Integer.valueOf(lightImpl.mHwLight.ordinal), java.lang.Integer.valueOf(lightImpl.getColor())));
                        }
                        printWriter.println("Session clients:");
                        for (com.android.server.lights.LightsService.LightsManagerBinderService.Session session : this.mSessions) {
                            printWriter.println("  Session token=" + session.mToken);
                            for (int i2 = 0; i2 < session.mRequests.size(); i2++) {
                                printWriter.println(java.lang.String.format("    Request id=%d color=%08x", java.lang.Integer.valueOf(session.mRequests.keyAt(i2)), java.lang.Integer.valueOf(session.mRequests.valueAt(i2).getColor())));
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: closeSessionInternal, reason: merged with bridge method [inline-methods] */
        public void lambda$openSession$0(android.os.IBinder iBinder) {
            synchronized (com.android.server.lights.LightsService.this) {
                try {
                    com.android.server.lights.LightsService.LightsManagerBinderService.Session sessionLocked = getSessionLocked(iBinder);
                    if (sessionLocked != null) {
                        this.mSessions.remove(sessionLocked);
                        invalidateLightStatesLocked();
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        private void checkRequestIsValid(int[] iArr) {
            for (int i : iArr) {
                com.android.server.lights.LightsService.LightImpl lightImpl = (com.android.server.lights.LightsService.LightImpl) com.android.server.lights.LightsService.this.mLightsById.get(i);
                com.android.internal.util.Preconditions.checkState((lightImpl == null || lightImpl.isSystemLight()) ? false : true, "Invalid lightId " + i);
            }
        }

        private void invalidateLightStatesLocked() {
            int i;
            java.util.HashMap hashMap = new java.util.HashMap();
            int size = this.mSessions.size();
            while (true) {
                size--;
                i = 0;
                if (size < 0) {
                    break;
                }
                android.util.SparseArray<android.hardware.lights.LightState> sparseArray = this.mSessions.get(size).mRequests;
                while (i < sparseArray.size()) {
                    hashMap.put(java.lang.Integer.valueOf(sparseArray.keyAt(i)), sparseArray.valueAt(i));
                    i++;
                }
            }
            while (i < com.android.server.lights.LightsService.this.mLightsById.size()) {
                com.android.server.lights.LightsService.LightImpl lightImpl = (com.android.server.lights.LightsService.LightImpl) com.android.server.lights.LightsService.this.mLightsById.valueAt(i);
                if (!lightImpl.isSystemLight()) {
                    android.hardware.lights.LightState lightState = (android.hardware.lights.LightState) hashMap.get(java.lang.Integer.valueOf(lightImpl.mHwLight.id));
                    if (lightState != null) {
                        lightImpl.setColor(lightState.getColor());
                    } else {
                        lightImpl.turnOff();
                    }
                }
                i++;
            }
        }

        @android.annotation.Nullable
        private com.android.server.lights.LightsService.LightsManagerBinderService.Session getSessionLocked(android.os.IBinder iBinder) {
            for (int i = 0; i < this.mSessions.size(); i++) {
                if (iBinder.equals(this.mSessions.get(i).mToken)) {
                    return this.mSessions.get(i);
                }
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class LightImpl extends com.android.server.lights.LogicalLight {
        private int mBrightnessLevel;
        private int mBrightnessMode;
        private int mColor;
        private boolean mFlashing;
        private android.hardware.light.HwLight mHwLight;
        private boolean mInitialized;
        private int mLastBrightnessMode;
        private int mLastColor;
        private boolean mLocked;
        private int mMode;
        private boolean mModesUpdate;
        private int mOffMS;
        private int mOnMS;
        private boolean mUseLowPersistenceForVR;
        private boolean mVrModeEnabled;

        private LightImpl(android.content.Context context, android.hardware.light.HwLight hwLight) {
            this.mHwLight = hwLight;
        }

        @Override // com.android.server.lights.LogicalLight
        public void setBrightness(float f) {
            setBrightness(f, 0);
        }

        @Override // com.android.server.lights.LogicalLight
        public void setBrightness(float f, int i) {
            if (java.lang.Float.isNaN(f)) {
                android.util.Slog.w(com.android.server.lights.LightsService.TAG, "Brightness is not valid: " + f);
                return;
            }
            synchronized (this) {
                try {
                    if (i == 2) {
                        android.util.Slog.w(com.android.server.lights.LightsService.TAG, "setBrightness with LOW_PERSISTENCE unexpected #" + this.mHwLight.id + ": brightness=" + f);
                        return;
                    }
                    int brightnessFloatToInt = com.android.internal.display.BrightnessSynchronizer.brightnessFloatToInt(f) & 255;
                    setLightLocked((brightnessFloatToInt << 16) | android.hardware.audio.common.V2_0.AudioFormat.MAIN_MASK | (brightnessFloatToInt << 8) | brightnessFloatToInt, 0, 0, 0, i);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.lights.LogicalLight
        public void setColor(int i) {
            synchronized (this) {
                setLightLocked(i, 0, 0, 0, 0);
            }
        }

        @Override // com.android.server.lights.LogicalLight
        public void setFlashing(int i, int i2, int i3, int i4) {
            synchronized (this) {
                setLightLocked(i, i2, i3, i4, 0);
            }
        }

        @Override // com.android.server.lights.LogicalLight
        public void setModes(int i) {
            synchronized (this) {
                this.mBrightnessLevel = i;
                this.mModesUpdate = true;
            }
        }

        @Override // com.android.server.lights.LogicalLight
        public void pulse() {
            pulse(android.hardware.audio.common.V2_0.AudioFormat.SUB_MASK, 7);
        }

        @Override // com.android.server.lights.LogicalLight
        public void pulse(int i, int i2) {
            synchronized (this) {
                try {
                    if (this.mColor == 0 && !this.mFlashing) {
                        setLightLocked(i, 2, i2, 1000, 0);
                        this.mColor = 0;
                        com.android.server.lights.LightsService.this.mH.postDelayed(new java.lang.Runnable() { // from class: com.android.server.lights.LightsService$LightImpl$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                com.android.server.lights.LightsService.LightImpl.this.stopFlashing();
                            }
                        }, i2);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.lights.LogicalLight
        public void turnOff() {
            synchronized (this) {
                setLightLocked(0, 0, 0, 0, 0);
            }
        }

        @Override // com.android.server.lights.LogicalLight
        public void setVrMode(boolean z) {
            synchronized (this) {
                try {
                    if (this.mVrModeEnabled != z) {
                        this.mVrModeEnabled = z;
                        this.mUseLowPersistenceForVR = com.android.server.lights.LightsService.this.getVrDisplayMode() == 0;
                        if (shouldBeInLowPersistenceMode()) {
                            this.mLastBrightnessMode = this.mBrightnessMode;
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void stopFlashing() {
            synchronized (this) {
                setLightLocked(this.mColor, 0, 0, 0, 0);
            }
        }

        private void setLightLocked(int i, int i2, int i3, int i4, int i5) {
            int i6;
            if (shouldBeInLowPersistenceMode()) {
                i6 = 2;
            } else if (i5 != 2) {
                i6 = i5;
            } else {
                i6 = this.mLastBrightnessMode;
            }
            if (!this.mInitialized || i != this.mColor || i2 != this.mMode || i3 != this.mOnMS || i4 != this.mOffMS || this.mBrightnessMode != i6 || this.mModesUpdate) {
                this.mInitialized = true;
                this.mLastColor = this.mColor;
                this.mColor = i;
                this.mMode = i2;
                this.mOnMS = i3;
                this.mOffMS = i4;
                this.mBrightnessMode = i6;
                this.mModesUpdate = false;
                setLightUnchecked(i, i2, i3, i4, i6);
            }
        }

        private void setLightUnchecked(int i, int i2, int i3, int i4, int i5) {
            android.os.Trace.traceBegin(131072L, "setLightState(" + this.mHwLight.id + ", 0x" + java.lang.Integer.toHexString(i) + ")");
            try {
                try {
                    if (com.android.server.lights.LightsService.this.mVintfLights != null) {
                        android.hardware.light.HwLightState hwLightState = new android.hardware.light.HwLightState();
                        if (this.mBrightnessLevel > 0 && this.mBrightnessLevel <= 255) {
                            int i6 = i >>> 24;
                            if (i6 == 0) {
                                i6 = 255;
                            }
                            hwLightState.color = (((i6 * this.mBrightnessLevel) / 255) << 24) + (i & android.hardware.audio.common.V2_0.AudioFormat.SUB_MASK);
                        } else {
                            hwLightState.color = i;
                        }
                        hwLightState.flashMode = (byte) i2;
                        hwLightState.flashOnMs = i3;
                        hwLightState.flashOffMs = i4;
                        hwLightState.brightnessMode = (byte) i5;
                        ((android.hardware.light.ILights) com.android.server.lights.LightsService.this.mVintfLights.get()).setLightState(this.mHwLight.id, hwLightState);
                    } else {
                        com.android.server.lights.LightsService.setLight_native(this.mHwLight.id, i, i2, i3, i4, i5, this.mBrightnessLevel);
                    }
                } catch (android.os.RemoteException | java.lang.UnsupportedOperationException e) {
                    android.util.Slog.e(com.android.server.lights.LightsService.TAG, "Failed issuing setLightState", e);
                }
                android.os.Trace.traceEnd(131072L);
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(131072L);
                throw th;
            }
        }

        private boolean shouldBeInLowPersistenceMode() {
            return this.mVrModeEnabled && this.mUseLowPersistenceForVR;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isSystemLight() {
            return this.mHwLight.type >= 0 && this.mHwLight.type < 8;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getColor() {
            return this.mColor;
        }
    }

    public LightsService(android.content.Context context) {
        this(context, new com.android.server.lights.LightsService.VintfHalCache(), android.os.Looper.myLooper());
    }

    @com.android.internal.annotations.VisibleForTesting
    LightsService(android.content.Context context, java.util.function.Supplier<android.hardware.light.ILights> supplier, android.os.Looper looper) {
        super(context);
        this.mLightsByType = new com.android.server.lights.LightsService.LightImpl[8];
        this.mLightsById = new android.util.SparseArray<>();
        this.mService = new com.android.server.lights.LightsManager() { // from class: com.android.server.lights.LightsService.1
            @Override // com.android.server.lights.LightsManager
            public com.android.server.lights.LogicalLight getLight(int i) {
                if (com.android.server.lights.LightsService.this.mLightsByType != null && i >= 0 && i < com.android.server.lights.LightsService.this.mLightsByType.length) {
                    return com.android.server.lights.LightsService.this.mLightsByType[i];
                }
                return null;
            }
        };
        this.mH = new android.os.Handler(looper);
        this.mVintfLights = supplier.get() == null ? null : supplier;
        populateAvailableLights(context);
        this.mManagerService = new com.android.server.lights.LightsService.LightsManagerBinderService();
    }

    private void populateAvailableLights(android.content.Context context) {
        if (this.mVintfLights != null) {
            populateAvailableLightsFromAidl(context);
        } else {
            populateAvailableLightsFromHidl(context);
        }
        for (int size = this.mLightsById.size() - 1; size >= 0; size--) {
            com.android.server.lights.LightsService.LightImpl valueAt = this.mLightsById.valueAt(size);
            byte b = valueAt.mHwLight.type;
            if (b >= 0 && b < this.mLightsByType.length) {
                this.mLightsByType[b] = valueAt;
            }
        }
    }

    private void populateAvailableLightsFromAidl(android.content.Context context) {
        try {
            for (android.hardware.light.HwLight hwLight : this.mVintfLights.get().getLights()) {
                this.mLightsById.put(hwLight.id, new com.android.server.lights.LightsService.LightImpl(context, hwLight));
            }
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Unable to get lights from HAL", e);
        }
    }

    private void populateAvailableLightsFromHidl(android.content.Context context) {
        for (int i = 0; i < this.mLightsByType.length; i++) {
            android.hardware.light.HwLight hwLight = new android.hardware.light.HwLight();
            byte b = (byte) i;
            hwLight.id = b;
            hwLight.ordinal = 1;
            hwLight.type = b;
            this.mLightsById.put(hwLight.id, new com.android.server.lights.LightsService.LightImpl(context, hwLight));
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishLocalService(com.android.server.lights.LightsManager.class, this.mService);
        publishBinderService("lights", this.mManagerService);
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getVrDisplayMode() {
        return android.provider.Settings.Secure.getIntForUser(getContext().getContentResolver(), "vr_display_mode", 0, android.app.ActivityManager.getCurrentUser());
    }

    private static class VintfHalCache implements java.util.function.Supplier<android.hardware.light.ILights>, android.os.IBinder.DeathRecipient {

        @com.android.internal.annotations.GuardedBy({"this"})
        private android.hardware.light.ILights mInstance;

        private VintfHalCache() {
            this.mInstance = null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.function.Supplier
        public synchronized android.hardware.light.ILights get() {
            if (this.mInstance == null) {
                android.os.IBinder allowBlocking = android.os.Binder.allowBlocking(android.os.ServiceManager.waitForDeclaredService(android.hardware.light.ILights.DESCRIPTOR + "/default"));
                if (allowBlocking != null) {
                    this.mInstance = android.hardware.light.ILights.Stub.asInterface(allowBlocking);
                    try {
                        allowBlocking.linkToDeath(this, 0);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.lights.LightsService.TAG, "Unable to register DeathRecipient for " + this.mInstance);
                    }
                }
            }
            return this.mInstance;
        }

        @Override // android.os.IBinder.DeathRecipient
        public synchronized void binderDied() {
            this.mInstance = null;
        }
    }
}
