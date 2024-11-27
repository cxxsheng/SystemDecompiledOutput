package com.android.server.audio;

/* loaded from: classes.dex */
class RotationHelper {
    private static final boolean DEBUG_ROTATION = false;
    private static final java.lang.String TAG = "AudioService.RotationHelper";
    private static android.content.Context sContext;
    private static com.android.server.audio.RotationHelper.AudioDisplayListener sDisplayListener;
    private static java.util.function.Consumer<java.lang.Boolean> sFoldStateCallback;
    private static android.hardware.devicestate.DeviceStateManager.FoldStateListener sFoldStateListener;
    private static android.os.Handler sHandler;
    private static java.util.function.Consumer<java.lang.Integer> sRotationCallback;
    private static final java.lang.Object sRotationLock = new java.lang.Object();
    private static final java.lang.Object sFoldStateLock = new java.lang.Object();
    private static java.lang.Integer sRotation = null;
    private static java.lang.Boolean sFoldState = null;

    RotationHelper() {
    }

    static void init(android.content.Context context, android.os.Handler handler, java.util.function.Consumer<java.lang.Integer> consumer, java.util.function.Consumer<java.lang.Boolean> consumer2) {
        if (context == null) {
            throw new java.lang.IllegalArgumentException("Invalid null context");
        }
        sContext = context;
        sHandler = handler;
        sDisplayListener = new com.android.server.audio.RotationHelper.AudioDisplayListener();
        sFoldStateListener = new android.hardware.devicestate.DeviceStateManager.FoldStateListener(sContext, new java.util.function.Consumer() { // from class: com.android.server.audio.RotationHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.audio.RotationHelper.updateFoldState(((java.lang.Boolean) obj).booleanValue());
            }
        });
        sRotationCallback = consumer;
        sFoldStateCallback = consumer2;
        enable();
    }

    static void enable() {
        ((android.hardware.display.DisplayManager) sContext.getSystemService("display")).registerDisplayListener(sDisplayListener, sHandler);
        updateOrientation();
        ((android.hardware.devicestate.DeviceStateManager) sContext.getSystemService(android.hardware.devicestate.DeviceStateManager.class)).registerCallback(new android.os.HandlerExecutor(sHandler), sFoldStateListener);
    }

    static void disable() {
        ((android.hardware.display.DisplayManager) sContext.getSystemService("display")).unregisterDisplayListener(sDisplayListener);
        ((android.hardware.devicestate.DeviceStateManager) sContext.getSystemService(android.hardware.devicestate.DeviceStateManager.class)).unregisterCallback(sFoldStateListener);
    }

    static void updateOrientation() {
        int i = android.hardware.display.DisplayManagerGlobal.getInstance().getDisplayInfo(0).rotation;
        synchronized (sRotationLock) {
            try {
                if (sRotation == null || sRotation.intValue() != i) {
                    sRotation = java.lang.Integer.valueOf(i);
                    publishRotation(sRotation.intValue());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static void publishRotation(int i) {
        int i2;
        switch (i) {
            case 0:
                i2 = 0;
                break;
            case 1:
                i2 = 90;
                break;
            case 2:
                i2 = 180;
                break;
            case 3:
                i2 = android.companion.virtualcamera.SensorOrientation.ORIENTATION_270;
                break;
            default:
                android.util.Log.e(TAG, "Unknown device rotation");
                i2 = -1;
                break;
        }
        if (i2 != -1) {
            sRotationCallback.accept(java.lang.Integer.valueOf(i2));
        }
    }

    static void updateFoldState(boolean z) {
        synchronized (sFoldStateLock) {
            try {
                if (sFoldState == null || sFoldState.booleanValue() != z) {
                    sFoldState = java.lang.Boolean.valueOf(z);
                    sFoldStateCallback.accept(java.lang.Boolean.valueOf(z));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    static void forceUpdate() {
        synchronized (sRotationLock) {
            sRotation = null;
        }
        updateOrientation();
        synchronized (sFoldStateLock) {
            try {
                if (sFoldState != null) {
                    sFoldStateCallback.accept(sFoldState);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    static final class AudioDisplayListener implements android.hardware.display.DisplayManager.DisplayListener {
        AudioDisplayListener() {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            com.android.server.audio.RotationHelper.updateOrientation();
        }
    }
}
