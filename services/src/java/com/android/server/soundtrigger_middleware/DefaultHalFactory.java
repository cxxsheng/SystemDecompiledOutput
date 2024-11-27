package com.android.server.soundtrigger_middleware;

/* loaded from: classes2.dex */
class DefaultHalFactory implements com.android.server.soundtrigger_middleware.HalFactory {
    private static final java.lang.String TAG = "SoundTriggerMiddlewareDefaultHalFactory";
    private static final int USE_DEFAULT_HAL = 0;
    private static final int USE_MOCK_HAL_V2 = 2;
    private static final int USE_MOCK_HAL_V3 = 3;

    @android.annotation.NonNull
    private static final com.android.server.soundtrigger_middleware.ICaptureStateNotifier mCaptureStateNotifier = new com.android.server.soundtrigger_middleware.ExternalCaptureStateTracker();

    DefaultHalFactory() {
    }

    @Override // com.android.server.soundtrigger_middleware.HalFactory
    public com.android.server.soundtrigger_middleware.ISoundTriggerHal create() {
        try {
            int i = android.os.SystemProperties.getInt("debug.soundtrigger_middleware.use_mock_hal", 0);
            if (i == 0) {
                java.lang.String str = android.hardware.soundtrigger3.ISoundTriggerHw.class.getCanonicalName() + "/default";
                if (android.os.ServiceManager.isDeclared(str)) {
                    android.util.Slog.i(TAG, "Connecting to default soundtrigger3.ISoundTriggerHw");
                    return new com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat(android.os.ServiceManager.waitForService(str), new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.DefaultHalFactory$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.os.SystemProperties.set("sys.audio.restart.hal", "1");
                        }
                    });
                }
                android.util.Slog.i(TAG, "Connecting to default soundtrigger-V2.x.ISoundTriggerHw");
                return com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.create(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.getService(true), new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.DefaultHalFactory$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.os.SystemProperties.set("sys.audio.restart.hal", "1");
                    }
                }, mCaptureStateNotifier);
            }
            if (i == 2) {
                android.util.Slog.i(TAG, "Connecting to mock soundtrigger-V2.x.ISoundTriggerHw");
                android.os.HwBinder.setTrebleTestingOverride(true);
                try {
                    final android.hardware.soundtrigger.V2_0.ISoundTriggerHw service = android.hardware.soundtrigger.V2_0.ISoundTriggerHw.getService("mock", true);
                    return com.android.server.soundtrigger_middleware.SoundTriggerHw2Compat.create(service, new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.DefaultHalFactory$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.soundtrigger_middleware.DefaultHalFactory.lambda$create$2(android.hardware.soundtrigger.V2_0.ISoundTriggerHw.this);
                        }
                    }, mCaptureStateNotifier);
                } finally {
                    android.os.HwBinder.setTrebleTestingOverride(false);
                }
            }
            if (i == 3) {
                final java.lang.String str2 = android.hardware.soundtrigger3.ISoundTriggerHw.class.getCanonicalName() + "/mock";
                android.util.Slog.i(TAG, "Connecting to mock soundtrigger3.ISoundTriggerHw");
                return new com.android.server.soundtrigger_middleware.SoundTriggerHw3Compat(android.os.ServiceManager.waitForService(str2), new java.lang.Runnable() { // from class: com.android.server.soundtrigger_middleware.DefaultHalFactory$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.soundtrigger_middleware.DefaultHalFactory.lambda$create$3(str2);
                    }
                });
            }
            throw new java.lang.RuntimeException("Unknown HAL mock version: " + i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$create$2(android.hardware.soundtrigger.V2_0.ISoundTriggerHw iSoundTriggerHw) {
        try {
            iSoundTriggerHw.debug(null, new java.util.ArrayList<>(java.util.Arrays.asList("reboot")));
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to reboot mock HAL", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$create$3(java.lang.String str) {
        try {
            android.os.ServiceManager.waitForService(str).shellCommand(null, null, null, new java.lang.String[]{"reboot"}, null, null);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to reboot mock HAL", e);
        }
    }
}
