package com.android.server.wm;

/* loaded from: classes3.dex */
class DisplayRotationCoordinator {
    private static final java.lang.String TAG = "DisplayRotationCoordinator";
    private int mDefaultDisplayCurrentRotation;
    private int mDefaultDisplayDefaultRotation;

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    java.lang.Runnable mDefaultDisplayRotationChangedCallback;

    DisplayRotationCoordinator() {
    }

    void onDefaultDisplayRotationChanged(int i) {
        this.mDefaultDisplayCurrentRotation = i;
        if (this.mDefaultDisplayRotationChangedCallback != null) {
            this.mDefaultDisplayRotationChangedCallback.run();
        }
    }

    void setDefaultDisplayDefaultRotation(int i) {
        this.mDefaultDisplayDefaultRotation = i;
    }

    int getDefaultDisplayCurrentRotation() {
        return this.mDefaultDisplayCurrentRotation;
    }

    void setDefaultDisplayRotationChangedCallback(@android.annotation.NonNull java.lang.Runnable runnable) {
        if (this.mDefaultDisplayRotationChangedCallback != null) {
            throw new java.lang.UnsupportedOperationException("Multiple clients unsupported");
        }
        this.mDefaultDisplayRotationChangedCallback = runnable;
        if (this.mDefaultDisplayCurrentRotation != this.mDefaultDisplayDefaultRotation) {
            runnable.run();
        }
    }

    void removeDefaultDisplayRotationChangedCallback() {
        this.mDefaultDisplayRotationChangedCallback = null;
    }

    static boolean isSecondaryInternalDisplay(@android.annotation.NonNull com.android.server.wm.DisplayContent displayContent) {
        return (displayContent.isDefaultDisplay || displayContent.mDisplay == null || displayContent.mDisplay.getType() != 1) ? false : true;
    }
}
